<template>
  <div class="device-list" ref="deviceList">
    <div v-for="(device, i) in devices" :key="i" class="device-item">
      <div style="display: flex;width: 100%;">
        <div
            style="display: flex;flex-direction: column;justify-content:center;width: 5%;margin-right: 5%; font-size: 17px;color: #04b4f8;font-weight: bold;text-align: center">
          {{ i + 1 }}
        </div>
        <div style="width: 89%;">
          <div class="device-header">
            <span class="device-id">IP：{{ device.srcIp }}</span>
            <span class="device-time">时间：{{ device.timestamp }}</span>
            <span :class="['device-status', device.status === '正常' ? 'online' : 'offline']">
              {{ device.status }}
            </span>
          </div>
          <div style="display: flex;width: 100%;">
            <div class="device-port">
              源端口：{{ device.srcPort }}
            </div>
            <div class="device-address">
              协议：{{ device.protocol == null ? 'TCP' : getProtocolName(device.protocol)}}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      events: [],
      eventSource: null,
      devices: [
        {id: 1, srcIp: '192.15.42.15', srcPort: '80', timestamp: '1994-12-01 21:53:20', address: '广东深圳', status: '正常', protocol:6},
        {id: 2, srcIp: '14.15.212.15', srcPort: '47752', timestamp: '2009-05-28 08:06:07', address: '江苏南京', status: '正常', protocol:6},
        {id: 3, srcIp: '192.185.111.222', srcPort: '1544', timestamp: '2008-03-10 11:55:35', address: '北京', status: '正常', protocol:6},
      ]
    };
  },
  mounted() {
    // Start adding new devices every 2 seconds
    this.connectToSSE();
    // this.connectEventSource();

  },
  beforeDestroy() {
    // this.disconnectEventSource();
  },

  methods: {
    // connectEventSource() {
    //   this.eventSource = new EventSource('http://192.168.31.82:9000/events');
    //   this.eventSource.onmessage = this.handleMessage;
    //   this.eventSource.onerror = this.handleError;
    // },
    // disconnectEventSource() {
    //   if (this.eventSource) {
    //     this.eventSource.close();
    //     this.eventSource = null;
    //   }
    // },
    // handleMessage(event) {
    //   console.log('Received event:', event.data);
    //   this.events.push(event.data);
    // },
    // handleError(error) {
    //   console.error('EventSource error:', error);
    //   // Optionally handle error display or reconnection logic
    // },
    connectToSSE() {
      const data = [{
        "srcIp": "144.17.148.97",
        "srcPort": 24396,
        "dstIp": "192.168.31.1",
        "dstPort": 2023,
        "protocol": 6,
        "label": "Pass Ripper",
        "timestamp": "05/07/2024 16:23",
        "confidence": "1.0",
        "ruleOrAi": "1",
        "packetLengthMean": "1.479"
      }]
      const vm  = this
      // 模拟数据
      setInterval(() => {
        const arr = this.generateRandomDevice()
        // console.log(arr)
        console.log(this.devices.length)
        if(this.devices.length>8){
          arr.status = '异常'
        }
        this.addDevice(arr);

      }, 2000);

      // const eventSource = new EventSource('http://192.168.31.82:9000/events');
      //
      // eventSource.onmessage = (event) => {
      //   console.log(event)
      // };
      //
      // eventSource.onerror = (error) => {
      //   console.error('EventSource error:', error);
      // };
    },


// 示例调用函数

    generateRandomDevice() {
      const srcIp = `${Math.floor(Math.random() * 256)}.${Math.floor(Math.random() * 256)}.${Math.floor(Math.random() * 256)}.${Math.floor(Math.random() * 256)}`;
      // const srcIp = this.generateChineseIPAddress()
      const srcPort = Math.floor(Math.random() * 65535).toString();
      const dstIp = `192.168.${Math.floor(Math.random() * 256)}.${Math.floor(Math.random() * 256)}`;
      const dstPort = Math.floor(Math.random() * 65535).toString();
      const numbers = ["6", "17", "1", "80", "443", "21", "22", "53", "25", "0", "000", "000", "000", "000"];
      const protocol = numbers[Math.floor(Math.random() * 20)];
      const timestamp = new Date().toISOString().slice(0, 19).replace('T', ' ');
      const packetLengthMean = Math.floor(Math.random() * (1000 - 100 + 1)) + 100;
      const address = `地址${this.devices.length + 1}`;
      const status = Math.random() > 0.05 ? '正常' : '异常';
      return { srcIp, srcPort,dstIp,dstPort,protocol, timestamp,packetLengthMean, address, status};
    },
    getProtocolName(protocolNumber) {
      const protocolMap = {
        "6": "TCP",
        "17": "UDP",
        "1": "ICMP",
        "80": "HTTP",
        "443": "HTTPS",
        "21": "FTP",
        "22": "SSH",
        "53": "DNS",
        "25": "SMTP",
        "0": "其他"
      };
      return protocolMap[protocolNumber.toString()] || "未知协议";
    },
    addDevice(newDevice) {
      this.devices.push(newDevice);

      // 在 DOM 更新后执行滚动操作
      // 使用 setTimeout 确保在 Vue 更新 DOM 后执行滚动操作
      setTimeout(() => {
        const list = this.$refs.deviceList;
        if (list && typeof list.scrollTo === 'function') {
          list.scrollTo({
            top: list.scrollHeight,
            behavior: 'smooth'  // 如果需要平滑滚动
          });
        }
      }, 0);
    }

  }
};
</script>

<style scoped>
.device-list {
  overflow: auto;
  height: 85%;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE/Edge */
  box-sizing: border-box;
  width: 92%;
  margin: 0 auto;
  font-family: Arial, sans-serif;
  color: #fff;
  scroll-behavior: smooth; /* Smooth scrolling */
}

.device-item {
  border-top: 1px dashed #00f0ff;
  padding: 10px 0;
  font-size: 13px;
}

.device-header {
  display: flex;
  margin-bottom: 5px;
}

.device-id,
.device-port,
.device-time {
  color: #CECECE;
}

.device-id {
  width: 40%;
}

.device-port {
  width: 40%;
}

.device-time {
  width: 50%;
}

.device-status {
  width: 10%;
  font-weight: bold;
  text-align: right;
}

.device-status.online {
  color: #24c024;
}

.device-status.offline {
  color: #c72020;
}

.device-address {
  color: #CECECE;
}
</style>
