from flask import Flask, Response
from scapy.all import sniff, wrpcap, IP, TCP, UDP
from datetime import datetime
import os
import threading
import time
import queue
import json
from flask_cors import CORS

from scapy.all import *
import re
import collections
from scapy.all import rdpcap

import requests

import sys

from flask import Flask, request, jsonify
from werkzeug.utils import secure_filename



app = Flask(__name__)
CORS(app)

# 全局变量用于数据包捕获
output_directory = "./pcap_files"
current_date = datetime.now().strftime("%Y-%m-%d")
pcap_filename = os.path.join(output_directory, f"{current_date}.pcap")
packet_count = 0

# 使用队列来在数据包捕获线程和SSE线程之间传递数据包
packet_queue = queue.Queue()



# SSE事件生成器
def generate_sse():
    while True:
        try:
            # 从队列中获取数据包
            packet = packet_queue.get(timeout=1)
            # print(get_host_ip([packet]))
            # 进行警告分析
            warnings = exception_warning([packet], "192.168.32.136")
            print("warnings>>>",warnings)
            for warning in warnings:
                yield f"data: {json.dumps(warning)}\n\n"

        except queue.Empty:
            # 如果队列为空，继续等待
            continue
        except Exception as e:
            print(f"Error in SSE generation: {e}")
            continue

        # time.sleep(1)

@app.route('/events')
def events():
    return Response(generate_sse(), mimetype='text/event-stream')

# 数据包捕获函数
def packet_sniffer():
    global current_date, pcap_filename, packet_count

    if not os.path.exists(output_directory):
        os.makedirs(output_directory)

    print("开始收集流量>>>>")
    # 设置捕获条件和回调函数
    sniff(prn=packet_callback, store=False, filter="", iface="ens33")

# 数据包回调函数
def packet_callback(packet):
    global current_date, pcap_filename, packet_count
    if IP in packet and (TCP in packet or UDP in packet):
        # 将捕获到的数据包放入队列
        packet_queue.put(packet)

        # 检查日期是否变化，如果是则创建新的 pcap 文件
        new_date = datetime.now().strftime("%Y-%m-%d")
        if new_date != current_date:
            current_date = new_date
            pcap_filename = os.path.join(output_directory, f"{current_date}.pcap")
            packet_count = 0

        # 将数据包写入当前的 pcap 文件
        wrpcap(pcap_filename, packet, append=True)
        packet_count += 1





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
                    'timestamp': time.strftime('%d/%m/%Y %H:%M', time.localtime(pcap.time)),
                    'confidence': '1.0',
                    'ruleOrAi': '1',
                    'packetLengthMean': f"{sum([len(corrupt_bytes(PCAPS[i])) for i in range(len(PCAPS))]) / 1024.0:.3f}",
                })
                print(portwarn_list)

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
                    'timestamp': time.strftime('%d/%m/%Y %H:%M', time.localtime(PCAPS[0].time)),
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
                    'timestamp': time.strftime('%d/%m/%Y %H:%M', time.localtime(PCAPS[0].time)),
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

            timestamp = time.strftime('%d/%m/%Y %H:%M', time.localtime(float(pcap.time)))

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







def main():
    # 启动数据包捕获的线程
    packet_sniff_thread = threading.Thread(target=packet_sniffer)
    packet_sniff_thread.start()

    # 运行Flask应用程序
    app.run(debug=True, host='0.0.0.0')

if __name__ == "__main__":
    main()
