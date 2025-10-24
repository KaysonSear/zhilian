<template>
  <div class="container">
    <div style="text-align: center">
      <h1 style="margin-bottom: 5px; color: #179ee8;">网络流量数据采集</h1>
    </div>
    <div style="display: flex; justify-content: right; padding-right: 50px">
      <button @click="startCollection" class="BtnCollect">开始采集</button>
    </div>
    <div v-if="loading" class="loading-spinner"></div>
    <table v-else class="data-table">
      <thead>
      <tr>
        <th>Flow ID</th>
        <th>Source IP</th>
        <th>Destination IP</th>
        <th>Source Port</th>
        <th>Destination Port</th>
        <th>Protocol</th>
        <th>Timestamp</th>
        <th>Flow Duration</th>
        <th>Total Fwd Packets</th>
        <th>Total Backward Packets</th>
        <th>Total Length of Fwd Packets</th>
        <th>Total Length of Bwd Packets</th>
        <th>Fwd Packet Length Max</th>
        <th>Fwd Packet Length Min</th>
        <th>Bwd Packet Length Max</th>
        <th>Bwd Packet Length Min</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(data, index) in trafficData" :key="index">
        <td>{{ data.flowId }}</td>
        <td>{{ data.sourceIp }}</td>
        <td>{{ data.destinationIp }}</td>
        <td>{{ data.sourcePort }}</td>
        <td>{{ data.destinationPort }}</td>
        <td>{{ data.protocol }}</td>
        <td>{{ data.timestamp }}</td>
        <td>{{ data.flowDuration }}</td>
        <td>{{ data.totalFwdPackets }}</td>
        <td>{{ data.totalBwdPackets }}</td>
        <td>{{ data.totalLengthFwdPackets }}</td>
        <td>{{ data.totalLengthBwdPackets }}</td>
        <td>{{ data.fwdPacketLengthMax }}</td>
        <td>{{ data.fwdPacketLengthMin }}</td>
        <td>{{ data.bwdPacketLengthMax }}</td>
        <td>{{ data.bwdPacketLengthMin }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      trafficData: [],
      loading: false,
      intervalId: null,
    };
  },
  methods: {
    startCollection() {
      this.loading = true;
      if (this.intervalId) {
        clearInterval(this.intervalId);
      }
      setTimeout(() => {
        this.loading = false;
        this.addData(); // 立即添加第一条数据
        this.intervalId = setInterval(this.addData, 2000);
      }, 3000); // 1秒后开始显示数据
    },
    addData() {
      const newData = this.generateRandomData();
      this.trafficData.push(newData);
    },
    generateRandomData() {
      const getRandomValue = (max) => Math.floor(Math.random() * max);
      const getRandomIp = () => `192.168.${getRandomValue(256)}.${getRandomValue(256)}`;
      const getRandomPort = () => getRandomValue(65535);
      const protocols = ['TCP', 'UDP', 'ICMP'];
      const specificDate = new Date('2024-06-28T00:00:00Z');
      const timestamp = new Date(specificDate.getTime() + getRandomValue(24 * 60 * 60 * 1000)).toISOString();
      return {
        flowId: (this.trafficData.length + 1).toString(),
        sourceIp: getRandomIp(),
        destinationIp: getRandomIp(),
        sourcePort: getRandomPort().toString(),
        destinationPort: getRandomPort().toString(),
        protocol: protocols[getRandomValue(protocols.length)],
        timestamp: timestamp,
        flowDuration: getRandomValue(10000).toString(),
        totalFwdPackets: getRandomValue(100).toString(),
        totalBwdPackets: getRandomValue(100).toString(),
        totalLengthFwdPackets: getRandomValue(10000).toString(),
        totalLengthBwdPackets: getRandomValue(10000).toString(),
        fwdPacketLengthMax: getRandomValue(1500).toString(),
        fwdPacketLengthMin: getRandomValue(1500).toString(),
        bwdPacketLengthMax: getRandomValue(1500).toString(),
        bwdPacketLengthMin: getRandomValue(1500).toString(),
      };
    },
  },
  beforeDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  },
};
</script>

<style>
.container {
  color: #ffffff;
  padding: 20px;
  border-radius: 8px;
}

button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 4px;
  margin-bottom: 20px;
}

button:hover {
  background-color: #0056b3;
}

.loading {
  font-size: 18px;
  color: #ffffff;
}

.loading-spinner {

  border: 16px solid #f3f3f3;
  border-top: 16px solid #3498db;
  border-radius: 50%;
  width: 50px;
  height: 50px;
  animation: spin 2s linear infinite;
  margin: 100px auto;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.data-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.data-table th,
.data-table td {
  border: 1px solid #444;
  padding: 5px 8px;
  font-size: 12px;
  text-align: center;
}

.data-table th {
  background-color: #333;
}

.data-table tr:nth-child(even) {
  background-color: rgba(54, 54, 54, 0.51);
}

.BtnCollect {
  padding: 10px 20px;
  border: 1px solid #298ee1;
  background-color: rgba(42, 58, 83, 0.73);
  color: #b7eaf5;
  border-radius: 5px;
  cursor: pointer;
  font-size: 15px;
}
</style>
