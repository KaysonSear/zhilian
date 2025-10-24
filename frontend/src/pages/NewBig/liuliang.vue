<template>
        <div class="frosted__glass">
          <table>
            <thead>
            <tr>
              <th>序号</th>
              <th>时间</th>
              <th>源IP</th>
              <th>目标IP地址</th>
              <th>源端口</th>
              <th>目标端口</th>
              <th>协议</th>
              <th>流量大小</th>
              <th>流量包数</th>
              <th>流量类型</th>
              <th>持续时间</th>
              <th>地理位置</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in repeatedData" :key="index">
              <td>{{ index + 1 }}</td>
              <td>{{ item.timestamp }}</td>
              <td>{{ item.sourceIP }}</td>
              <td>{{ item.destinationIP }}</td>
              <td>{{ item.sp }}</td>
              <td>{{ item.dp }}</td>
              <td>{{ item.protocol }}</td>
              <td>{{ item.bytesTransferred }}</td>
              <td>{{ item.packetsTransferred }}</td>
              <td>{{ item.trafficType }}</td>
              <td>{{ item.duration }}</td>
              <td>{{ item.geoLocation }}</td>
            </tr>
            </tbody>
          </table>
        </div>
</template>

<script>
export default {
  data() {
    return {
      data: [
        {
          timestamp: '2024-06-13 00:05',
          sourceIP: '192.168.0.10',
          destinationIP: '203.0.113.11',
          sp:'8080',
          dp:'22',
          protocol: 'TCP',
          bytesTransferred: 1500,
          packetsTransferred: 10,
          trafficType: 'HTTP',
          duration: '30s',
          geoLocation: '上海, 中国'
        },
        {
          timestamp: '2024-06-13 00:02',
          sourceIP: '198.51.100.1',
          destinationIP: '36.213.9.209',
          sp:'156',
          dp:'22',
          protocol: 'TCP',
          bytesTransferred: 1024,
          packetsTransferred: 8,
          trafficType: 'SSH',
          duration: '45s',
          geoLocation: '深圳, 中国'
        },
        {
          timestamp: '2024-06-13 00:03',
          sourceIP: '203.0.113.12',
          destinationIP: '203.0.113.13',
          sp:'80',
          dp:'44',
          protocol: 'TCP',
          bytesTransferred: 5000,
          packetsTransferred: 15,
          trafficType: 'HTTPS',
          duration: '20s',
          geoLocation: '北京, 中国'
        },
        {
          timestamp: '2024-06-13 00:03',
          sourceIP: '203.0.113.12',
          destinationIP: '203.0.113.13',
          sp:'80',
          dp:'44',
          protocol: 'TCP',
          bytesTransferred: 5000,
          packetsTransferred: 15,
          trafficType: 'HTTPS',
          duration: '20s',
          geoLocation: '北京, 中国'
        },{
          timestamp: '2024-06-13 00:03',
          sourceIP: '203.0.113.12',
          destinationIP: '203.0.113.13',
          sp:'80',
          dp:'44',
          protocol: 'TCP',
          bytesTransferred: 5000,
          packetsTransferred: 15,
          trafficType: 'HTTPS',
          duration: '20s',
          geoLocation: '北京, 中国'
        },{
          timestamp: '2024-06-13 00:03',
          sourceIP: '203.0.113.12',
          destinationIP: '203.0.113.13',
          sp:'80',
          dp:'44',
          protocol: 'TCP',
          bytesTransferred: 5000,
          packetsTransferred: 15,
          trafficType: 'HTTPS',
          duration: '20s',
          geoLocation: '北京, 中国'
        }, {
          timestamp: '2024-06-13 00:01',
          sourceIP: '192.168.0.10',
          destinationIP: '203.0.113.11',
          sp: '8080',
          dp: '22',
          protocol: 'TCP',
          bytesTransferred: 1500,
          packetsTransferred: 10,
          trafficType: 'HTTP',
          duration: '30s',
          geoLocation: '上海, 中国'
        },
        {
          timestamp: '2024-06-13 00:02',
          sourceIP: '198.51.100.1',
          destinationIP: '36.213.9.209',
          sp: '156',
          dp: '22',
          protocol: 'TCP',
          bytesTransferred: 1024,
          packetsTransferred: 8,
          trafficType: 'SSH',
          duration: '45s',
          geoLocation: '深圳, 中国'
        },
        {
          timestamp: '2024-06-13 05:03',
          sourceIP: '203.0.113.12',
          destinationIP: '203.0.113.13',
          sp: '80',
          dp: '44',
          protocol: 'TCP',
          bytesTransferred: 5000,
          packetsTransferred: 15,
          trafficType: 'HTTPS',
          duration: '20s',
          geoLocation: '北京, 中国'
        },
        {
          timestamp: '2024-06-13 00:04',
          sourceIP: '172.16.0.10',
          destinationIP: '203.0.113.11',
          sp: '443',
          dp: '80',
          protocol: 'TCP',
          bytesTransferred: 3000,
          packetsTransferred: 12,
          trafficType: 'HTTPS',
          duration: '25s',
          geoLocation: '广州, 中国'
        },
        {
          timestamp: '2024-06-13 00:05',
          sourceIP: '10.0.0.5',
          destinationIP: '192.0.2.1',
          sp: '22',
          dp: '80',
          protocol: 'TCP',
          bytesTransferred: 4000,
          packetsTransferred: 20,
          trafficType: 'SSH',
          duration: '15s',
          geoLocation: '成都, 中国'
        },{
          timestamp: '2024-06-13 00:03',
          sourceIP: '203.0.113.12',
          destinationIP: '203.0.113.13',
          sp:'80',
          dp:'44',
          protocol: 'TCP',
          bytesTransferred: 5000,
          packetsTransferred: 15,
          trafficType: 'HTTPS',
          duration: '20s',
          geoLocation: '北京, 中国'
        },{
          timestamp: '2024-06-13 00:03',
          sourceIP: '203.0.113.12',
          destinationIP: '203.0.113.13',
          sp:'80',
          dp:'44',
          protocol: 'TCP',
          bytesTransferred: 5000,
          packetsTransferred: 15,
          trafficType: 'HTTPS',
          duration: '20s',
          geoLocation: '广州, 中国'
        },{
          timestamp: '2024-06-13 00:03',
          sourceIP: '203.0.113.12',
          destinationIP: '203.0.113.13',
          sp:'80',
          dp:'44',
          protocol: 'TCP',
          bytesTransferred: 5000,
          packetsTransferred: 15,
          trafficType: 'HTTPS',
          duration: '20s',
          geoLocation: '广州, 中国'
        }, {
          timestamp: '2024-06-13 00:02',
          sourceIP: '198.51.100.1',
          destinationIP: '36.213.9.209',
          sp: '156',
          dp: '22',
          protocol: 'TCP',
          bytesTransferred: 1024,
          packetsTransferred: 8,
          trafficType: 'SSH',
          duration: '45s',
          geoLocation: '深圳, 中国'
        },{
          timestamp: '2024-06-13 00:03',
          sourceIP: '203.0.113.12',
          destinationIP: '203.0.113.13',
          sp:'80',
          dp:'44',
          protocol: 'TCP',
          bytesTransferred: 5000,
          packetsTransferred: 15,
          trafficType: 'HTTPS',
          duration: '20s',
          geoLocation: '广州, 中国'
        },
        {
          timestamp: '2024-06-13 00:03',
          sourceIP: '203.0.113.12',
          destinationIP: '203.0.113.13',
          sp:'80',
          dp:'44',
          protocol: 'TCP',
          bytesTransferred: 5000,
          packetsTransferred: 15,
          trafficType: 'HTTPS',
          duration: '20s',
          geoLocation: '北京, 中国'
        },{
          timestamp: '2024-06-13 00:08',
          sourceIP: '192.168.1.100',
          destinationIP: '198.51.100.45',
          sp: '80',
          dp: '80',
          protocol: 'UDP',
          bytesTransferred: 2500,
          packetsTransferred: 30,
          trafficType: 'DNS',
          duration: '40s',
          geoLocation: '悉尼, 澳大利亚'
        },
        {
          timestamp: '2024-06-13 00:09',
          sourceIP: '198.51.100.100',
          destinationIP: '192.0.2.55',
          sp: '25',
          dp: '25',
          protocol: 'TCP',
          bytesTransferred: 1100,
          packetsTransferred: 8,
          trafficType: 'SMTP',
          duration: '35s',
          geoLocation: '法兰克福, 德国'
        },
        {
          timestamp: '2024-06-13 00:10',
          sourceIP: '10.0.0.100',
          destinationIP: '203.0.113.10',
          sp: '23',
          dp: '23',
          protocol: 'TCP',
          bytesTransferred: 500,
          packetsTransferred: 15,
          trafficType: 'TELNET',
          duration: '30s',
          geoLocation: '香港, 中国'
        },
        {
          timestamp: '2024-06-13 00:11',
          sourceIP: '172.16.0.5',
          destinationIP: '203.0.113.20',
          sp: '3389',
          dp: '3389',
          protocol: 'TCP',
          bytesTransferred: 2500,
          packetsTransferred: 18,
          trafficType: 'RDP',
          duration: '25s',
          geoLocation: '东京, 日本'
        },
        {
          timestamp: '2024-06-13 00:23',
          sourceIP: '203.0.113.12',
          destinationIP: '203.0.113.13',
          sp:'80',
          dp:'44',
          protocol: 'TCP',
          bytesTransferred: 5000,
          packetsTransferred: 15,
          trafficType: 'HTTPS',
          duration: '20s',
          geoLocation: '北京, 中国'
        },
        {
          timestamp: '2024-06-13 00:03',
          sourceIP: '203.0.113.12',
          destinationIP: '203.0.113.13',
          sp:'80',
          dp:'44',
          protocol: 'TCP',
          bytesTransferred: 5000,
          packetsTransferred: 15,
          trafficType: 'HTTPS',
          duration: '20s',
          geoLocation: '北京, 中国'
        },
        {
          timestamp: '2024-06-13 00:03',
          sourceIP: '203.0.113.12',
          destinationIP: '203.0.113.13',
          sp:'80',
          dp:'44',
          protocol: 'TCP',
          bytesTransferred: 5000,
          packetsTransferred: 15,
          trafficType: 'HTTPS',
          duration: '20s',
          geoLocation: '北京, 中国'
        },
      ]
    }
  },
  computed: {
    repeatedData() {
      const repeated = [];
      const repetitions = 15; // 重复的次数

      for (let i = 0; i < repetitions; i++) {
        repeated.push(...this.data);
      }

      return repeated;
    },
  },
  methods: {

  }
}
</script>

<style scoped>
.container {
  padding: 0 30px;
  height: 100vh;
}

.Box {
  width: 85%;
  height: 80%;
  margin: 2% auto;
}

@keyframes borderGradient {
  0% {
    border-image-source: linear-gradient(45deg, #9292fd, #0599f8);
  }
  25% {
    border-image-source: linear-gradient(45deg, #0599f8, #61bfff);
  }
  50% {
    border-image-source: linear-gradient(45deg, #61bfff, #005f9d);
  }
  75% {
    border-image-source: linear-gradient(45deg, #005f9d, #9292fd);
  }
  100% {
    border-image-source: linear-gradient(45deg, #9292fd, #0599f8);
  }
}

.frosted__glass {
  position: relative;
  overflow-y: auto; /* 添加垂直滚动条 */
  height: 600px; /* 设置固定高度 */
  width: 96%;
  margin: 2% 3% 0;
  box-sizing: border-box;

  justify-content: center;
  align-items: center;
  backdrop-filter: blur(3px);
  border: 1.5px solid;
  border-image-slice: 1;
  animation: borderGradient 3s infinite;
  box-shadow: 0 0 20px 3px rgba(54, 54, 157, 0.5);
  border-radius: 10px;
}

table {
  background-color: #000000;
  width: 100%;
  border-collapse: collapse;
  text-align: center;
  color: #cecece;
}

thead {
  position: sticky; /* 使表头固定 */
  background-color: rgba(0, 0, 0, 1); /* 设置背景色 */
  z-index: 1;       /* 确保表头在内容上方 */
}
th,
td {
  padding: 10px;
  border: 1px solid rgba(178, 177, 177, 0.3);
}

th {
  background: transparent;
}

tbody tr:nth-child(even) {
  background: rgba(220, 220, 220, 0.2);
}

tbody tr:nth-child(odd) {
  background: rgba(89, 89, 141, 0.2);
}
</style>
