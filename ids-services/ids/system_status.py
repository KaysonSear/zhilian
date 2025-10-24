import psutil
import time
import random
import json
import websockets.sync.client

# 获取本机状态
def get_system_status():
    status = {}

    # 获取 CPU 详情
    cpu_times = psutil.cpu_times()
    cpu_percent = psutil.cpu_percent(interval=1)
    status['CPU'] = {
        'user': cpu_times.user,  # 用户模式时间
        'system': cpu_times.system,  # 内核模式时间
        'idle': cpu_times.idle,  # 空闲时间
        'percent': cpu_percent  # CPU 使用率百分比
    }

    # 获取内存详情
    virtual_memory = psutil.virtual_memory()
    status['Memory'] = {
        'total': virtual_memory.total,  # 总内存
        'available': virtual_memory.available,  # 可用内存
        'percent': virtual_memory.percent,  # 内存使用率百分比
        'used': virtual_memory.used,  # 已使用内存
        'free': virtual_memory.free  # 空闲内存
    }

    # 获取磁盘详情
    disk_usage = psutil.disk_usage('/')
    status['Disk'] = {
        'total': disk_usage.total,  # 磁盘总空间
        'used': disk_usage.used,  # 已使用空间
        'free': disk_usage.free,  # 空闲空间
        'percent': disk_usage.percent  # 磁盘使用率百分比
    }

    # 获取网络详情
    net_io = psutil.net_io_counters()
    status['Network'] = {
        'bytes_sent': net_io.bytes_sent,  # 发送的字节数
        'bytes_recv': net_io.bytes_recv,  # 接收的字节数
        'packets_sent': net_io.packets_sent,  # 发送的数据包数
        'packets_recv': net_io.packets_recv  # 接收的数据包数
    }
    return status


def hello(message):
    # 生成一个三位数的随机数作为userId
    user_id = random.randint(100, 999)
    
    message_content = message  
    
    message_info = {
        'userId': "1",
        'message': message_content
    }
    
    json_message = json.dumps(message_info, ensure_ascii=False)
    
    # WebSocket的URI，包含随机生成的userId
    uri = f"ws://127.0.0.1:9090/websocket/{user_id}/{user_id}"
    
    # 使用websockets库连接到WebSocket服务器并发送消息
    with websockets.sync.client.connect(uri) as websocket:
        websocket.send(json_message)
        print(f"发送: {json_message}")
        # 如果需要接收响应，可以取消下面一行的注释
        # response = websocket.recv()
        # print(f"接收: {response}")
        websocket.close()  # 显式关闭连接

def main():
    while True:
        # 获取系统状态
        status = get_system_status()
        print(status)  # 或者其他处理逻辑
        hello(status)
        # 等待30秒
        time.sleep(5)

if __name__ == "__main__":
    main()
