from flask import Flask, jsonify, request
import xml.etree.ElementTree as ET
import subprocess
import re

app = Flask(__name__)

# 从XML文件加载规则的函数
def load_rules_from_xml(xml_file):
    tree = ET.parse(xml_file)
    root = tree.getroot()
    rules = {}
    for group in root.findall('group'):
        for rule in group.findall('rule'):
            rule_id = rule.get('id')
            match = rule.find('match').text if rule.find('match') is not None else ''
            description = rule.find('description').text if rule.find('description') is not None else ''
            rules[rule_id] = {
                'regex': match,
                'description': description
            }
    return rules

# 根据规则处理日志行的函数
def process_log_line(line, rules):
    alerts = []
    for rule_id, rule in rules.items():
        if re.search(rule['regex'], line):
            alerts.append({
                'rule_id': rule_id,
                'description': rule['description'],
                'log': line.strip()
            })
    return alerts

# 监控日志的函数
def monitor_logs(log_file, rules):
    alerts = []
    with subprocess.Popen(['tail', log_file], stdout=subprocess.PIPE, universal_newlines=True) as process:
        line = process.stdout.readline()
        if line:
            new_alerts = process_log_line(line, rules)
            if new_alerts:
                alerts.extend(new_alerts)
    return alerts

# 路由用于触发日志监控和系统文件检查
@app.route('/check_logs', methods=['GET'])
def check_logs():
    log_file = request.args.get('log_file')
    if not log_file:
        return jsonify({'error': 'No log file path was provided'}), 400
    
    rules = load_rules_from_xml('/home/user/ids/safe/rules/syslog_rules.xml')
    alerts = monitor_logs(log_file, rules)
    return jsonify(alerts)

if __name__ == "__main__":
    app.run(debug=True, port=5002, host='0.0.0.0')
