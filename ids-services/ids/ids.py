#coding:UTF-8

from scapy.all import *
import re
import collections
from scapy.all import rdpcap

from scapy.all import IP
import requests
import os

import sys
import datetime

from flask import Flask, request, jsonify
from werkzeug.utils import secure_filename


# 根据可疑端口判断是否有木马病毒
def port_warning(PCAPS, host_ip):
    print("----------------端口匹配-----------------") 
    # 获取目录路径，生成环境或开发环境均可使用以下方式
    if getattr(sys, 'frozen', False):
        bundle_dir = sys._MEIPASS
    else:
        bundle_dir = os.path.dirname(os.path.abspath(__file__))

    # 匹配的对应规则库
    warn_path = os.path.join(bundle_dir, 'protocol', 'WARN')

    with open(warn_path, 'r', encoding='UTF-8') as f:
        warns = f.readlines()

    # 获取规则
    WARN_DICT = {int(warn.split(':')[0]): warn.split(':')[1].strip() for warn in warns}

    # 警告列表
    portwarn_list = []
    # PCAPS为传入的流量 Ether / IP / TCP 192.168.56.129:ssh > 192.168.56.1:61967 PA / Raw
    for pcap in PCAPS:
        if pcap.haslayer(TCP):
            # 解析对应流量的字段
            tcp = pcap.getlayer(TCP)
            src = pcap.getlayer(IP).src
            dst = pcap.getlayer(IP).dst
            sport = tcp.sport
            dport = tcp.dport
            ip = dst if src == host_ip else src
            port = sport if sport in WARN_DICT or src == host_ip else dport
            if port in WARN_DICT:
                portwarn_list.append({
                    'srcIp': pcap.getlayer(IP).src,
                    'srcPort': pcap.getlayer(TCP).sport,
                    'dstIp': pcap.getlayer(IP).dst,
                    'dstPort': pcap.getlayer(TCP).dport,
                    'protocol': pcap.getlayer(IP).proto,
                    'label': WARN_DICT[port],
                    'timestamp': datetime.datetime.fromtimestamp(float(PCAPS[0].time.to_eng_string())).strftime('%d/%m/%Y %H:%M'),
                    'confidence': '1.0',
                    'ruleOrAi': '1',
                    'packetLengthMean': f"{sum([len(corrupt_bytes(PCAPS[i])) for i in range(len(PCAPS))]) / 1024.0:.3f}",
                })
                
                # insert_warnings(portwarn_list)

    return portwarn_list

# 根据WEB内容来匹配常见WEB攻击,SQL注入，XSS，暴力破解，目录遍历，任意文件下载，木马
def web_warning(PCAPS, host_ip):
    print("--------------------------web内容匹配------------------------")
    if getattr(sys, 'frozen', False):
        bundle_dir = sys._MEIPASS
    else:
        bundle_dir = os.path.dirname(os.path.abspath(__file__))

    attack_path = os.path.join(bundle_dir, 'warning', 'HTTP_ATTACK')

    with open(attack_path, 'r', encoding='UTF-8') as f:
        attacks = f.readlines()

    ATTACK_DICT = {attack.split(' : ')[0].strip(): attack.split(' : ')[1].strip() for attack in attacks}

    webdata = web_data(PCAPS, host_ip)
    webwarn_list = []
    webbur_list = []
    web_warn = []
    web_patternu = re.compile(r'((txtUid|username|user|name)=(.*?))&', re.I)
    web_patternp = re.compile(r'((txtPwd|password|pwd|passwd)=(.*?))&', re.I)
    tomcat_pattern = re.compile(r'Authorization: Basic (.*)')

    for web in webdata:
        data = web['data']
        username = web_patternu.findall(data)
        password = web_patternp.findall(data)
        tomcat = tomcat_pattern.findall(data)
        if username or password or tomcat:
            webbur_list.append(web['ip_port'].split(':')[0])
        for pattn, attk in ATTACK_DICT.items():
            if pattn.upper() in data.upper():
                webwarn_list.append({
                    'srcIp': web['src_ip'],
                    'srcPort': web['src_port'],
                    'dstIp': web['dst_ip'],
                    'dstPort': web['dst_port'],
                    'protocol': web['protocol'],
                    'label': attk,
                    'timestamp': datetime.datetime.fromtimestamp(float(PCAPS[0].time.to_eng_string())).strftime('%d/%m/%Y %H:%M'),
                    'confidence': '1.0',
                    'ruleOrAi': '1',
                    'packetLengthMean': web['lens']
                })
                web_warn.append({
                    'srcIp': web['src_ip'],
                    'srcPort': web['src_port'],
                    'dstIp': web['dst_ip'],
                    'dstPort': web['dst_port'],
                    'protocol': web['protocol'],
                    'label': attk,
                    'timestamp': datetime.datetime.fromtimestamp(float(PCAPS[0].time.to_eng_string())).strftime('%d/%m/%Y %H:%M'),
                    'confidence': '1.0',
                    'ruleOrAi': '1',
                    'packetLengthMean': web['lens']
                })

    ip_count = collections.Counter(webbur_list)
    warn_ip = {k: y for k, y in ip_count.items() if y > 10}
    for ip, count in warn_ip.items():
        webwarn_list.append({'ip_port': ip, 'warn': 'HTTP暴力破解', 'time': str(count), 'data': None})
        # insert_warnings(web_warn)

    return webwarn_list

# 警告聚合
def exception_warning(PCAPS, host_ip):
    warn_list = list()
    port_list = port_warning(PCAPS, host_ip)
    web_list = web_warning(PCAPS, host_ip)

    if web_list:
        warn_list.extend(web_list)

    if port_list:
        warn_list.append(port_list)

    return warn_list

def web_data(PCAPS, host_ip):
    ip_port_data_list = []
    id = 0
    for pcap in PCAPS:
        # print()
        if pcap.haslayer(TCP) and hasattr(pcap.payload, 'load'):
            src = pcap.getlayer(IP).src
            dst = pcap.getlayer(IP).dst
            sport = pcap.sport
            dport = pcap.dport

            # Simplified check, assuming all TCP traffic is HTTP
            if src == host_ip:
                ip = dst
                port = dport
            else:
                ip = src
                port = sport

            timestamp = datetime.datetime.fromtimestamp(float(pcap.time)).strftime('%d/%m/%Y %H:%M')

            ip_port = f"{ip}:{port}:HTTP"
            if ip_port not in ip_port_data_list:
                raw_data = pcap.payload.load
                # Handling encoding issues
                try:
                    data = raw_data.decode('UTF-8', 'ignore')
                except UnicodeDecodeError:
                    data = raw_data.decode('GBK', 'ignore')
                
                ip_port_data_list.append({
                    'data_id': id,
                    'ip_port': ip_port,
                    'data': data,
                    'raw_data': raw_data,
                    'lens': f"{sum([len(corrupt_bytes(PCAPS[i])) for i in range(len(PCAPS))]) / 1024.0:.3f}",
                    'src_ip': src,
                    'dst_ip': dst,
                    'src_port': sport,
                    'dst_port': dport,
                    'protocol': pcap.getlayer(IP).proto,
                    'timestamp': timestamp
                })

            id += 1
    
    return ip_port_data_list

#获取抓包主机的IP
def get_host_ip(PCAPS):
    ip_list = []
    for pcap in PCAPS:
        if pcap.haslayer(IP):
            ip_list.append(pcap.getlayer(IP).src)
            ip_list.append(pcap.getlayer(IP).dst)
    if ip_list:
        host_ip = collections.Counter(ip_list).most_common(1)[0][0]
        return host_ip
    else:
        return None

#获取本机外网IP
def getmyip():
    try:
        headers = {'User-Agent': 'Baiduspider+(+http://www.baidu.com/search/spider.htm'}
        ip = requests.get('http://icanhazip.com', headers=headers).text
        return ip.strip()
    except Exception as e:
        print(f"An error occurred: {e}")
        return None


app = Flask(__name__)
UPLOAD_FOLDER = '/home/user/ids/safe/pcap_files'
ALLOWED_EXTENSIONS = {'pcap','pcapng'}

app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

# 函数检查文件扩展名是否允许
def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


# 处理文件上传和分析的路由
@app.route('/upload', methods=['POST'])
def upload_file():
    if 'file' not in request.files:
        return jsonify({'error': 'No file part'})
    
    file = request.files['file']

    if file.filename == '':
        return jsonify({'error': 'No selected file'})

    if file and allowed_file(file.filename):
        filename = secure_filename(file.filename)
        file_path = os.path.join(app.config['UPLOAD_FOLDER'], filename)
        file.save(file_path)

        # 分析上传的pcap文件
        PCAPS = rdpcap(file_path)
        host_ip = get_host_ip(PCAPS)
        warnings = exception_warning(PCAPS, host_ip)

        # 将分析结果以JSON格式返回
        return jsonify({'warnings': warnings})

    return jsonify({'error': 'File format not supported'})

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port='5001')