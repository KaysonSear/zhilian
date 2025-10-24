<template>
  <div class="table-container">
    <table class="table">
      <thead>
      <tr>
        <th class="no-background">序号</th>
        <th class="no-background">时间</th>
        <th class="no-background">攻击者画像</th>
        <th class="no-background">攻击类型</th>
        <th class="no-background">判断类型</th>
        <th class="no-background">准确度</th>
        <th class="no-background">操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item, index) in displayedData" :key="index">
        <td>{{ (currentPage - 1) * pageSize + index + 1 }}</td>
<!--        <td>{{item.timestamp}}</td>-->
        <td>{{ formatDate(item.timestamp) }} </td>
        <td style="font-size: 14px; ">
          <div style="display: flex;justify-content:space-between;padding: 0 10%">
            <div style="width: 55%; text-align: left">
              <span class="IP-Text">攻击者IP: </span>{{ item.srcIp }}&nbsp;&nbsp;&nbsp;
            </div>
            <div style="width: 35%; text-align: left">
              <span class="IP-Text">源端口: </span>{{ item.srcPort }}&nbsp;&nbsp;&nbsp;
            </div>
            <div style="width: 20%; text-align: left">
              <span class="protocol">协议: </span>{{ getProtocolName(item.protocol) }}
<!--              <span class="protocol">协议: </span>{{ item.protocol }}-->
            </div>
          </div>
        </td>
        <td>{{ item.label }}</td>
        <td>模型识别</td>
        <td>{{  formatConfidence(item.confidence) }}</td>
        <td>
          <div style="color: #498DFF; font-size: 14px">
            <span @click="detailsInfo(item)">详情</span>&nbsp;&nbsp;
<!--            <span @click="handleInfo(item)">处理</span>-->
          </div>
        </td>
      </tr>
      </tbody>
    </table>
    <div class="pagination">
      <button @click="previousPage" :disabled="currentPage === 1">上一页</button>
      <span style="font-size: 14px">{{ currentPage }} / {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">下一页</button>
    </div>
  </div>
</template>

<script>
import { getAllFlow } from "../../../API/index";

export default {
  name: 'TableComponent',
  data() {
    return {
      tableData: [],
      currentPage: 1,
      pageSize: 9
    };
  },
  computed: {
    // 计算总页数
    totalPages() {
      return Math.ceil(this.tableData.length / this.pageSize);
    },
    // 根据当前页码计算显示的数据
    displayedData() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.tableData.slice(start, end);
    }
  },
  mounted() {
    // 模拟获取数据的过程，这里使用 setTimeout 来模拟异步获取数据的延迟
    // setTimeout(() => {
    //   this.tableData = [
    //     {
    //       timestamp: '14/06/2024 10:23',
    //       protocol: 17,
    //       dstIp: "192.168.10.101",
    //       dstPort: 8080,
    //       packetLengthMean: 8.2,
    //       srcIp: "203.0.113.27",
    //       srcPort: 53728,
    //       label: 'Malware',
    //       confidence: 0.987654321
    //     },
    //     {
    //       timestamp: '14/06/2024 11:45',
    //       protocol: 6,
    //       dstIp: "192.168.5.67",
    //       dstPort: 3389,
    //       packetLengthMean: 15.1,
    //       srcIp: "172.16.20.55",
    //       srcPort: 45,
    //       label: 'Phishing',
    //       confidence: 0.97531042
    //     },
    //     {
    //       timestamp: '14/06/2024 13:18',
    //       protocol: 17,
    //       dstIp: "10.0.1.99",
    //       dstPort: 53,
    //       packetLengthMean: 6.8,
    //       srcIp: "198.51.100.17",
    //       srcPort: 62512,
    //       label: 'Botnet',
    //       confidence: 0.99887766
    //     },
    //     {
    //       timestamp: '15/06/2024 09:55',
    //       protocol: 6,
    //       dstIp: "192.168.8.34",
    //       dstPort: 22,
    //       packetLengthMean: 11.9,
    //       srcIp: "192.0.2.128",
    //       srcPort: 45678,
    //       label: 'Phishing',
    //       confidence: 0.987654321
    //     },
    //     {
    //       timestamp: '15/06/2024 14:37',
    //       protocol: 17,
    //       dstIp: "172.16.15.200",
    //       dstPort: 123,
    //       packetLengthMean: 7.5,
    //       srcIp: "198.18.3.42",
    //       srcPort: 54321,
    //       label: 'Malware',
    //       confidence: 0.99887766
    //     },
    //     {
    //       timestamp: '16/06/2024 08:02',
    //       protocol: 6,
    //       dstIp: "192.168.3.75",
    //       dstPort: 443,
    //       packetLengthMean: 10.5,
    //       srcIp: "203.0.113.99",
    //       srcPort: 65432,
    //       label: 'DDoS',
    //       confidence: 0.9999998807
    //     },
    //     {
    //       timestamp: '16/06/2024 11:20',
    //       protocol: 17,
    //       dstIp: "10.1.1.5",
    //       dstPort: 161,
    //       packetLengthMean: 9.3,
    //       srcIp: "198.51.100.23",
    //       srcPort: 56789,
    //       label: 'Botnet',
    //       confidence: 0.987654321
    //     },
    //     {
    //       timestamp: '17/06/2024 10:45',
    //       protocol: 6,
    //       dstIp: "192.168.1.100",
    //       dstPort: 80,
    //       packetLengthMean: 12.7,
    //       srcIp: "192.0.2.176",
    //       srcPort: 43210,
    //       label: 'Phishing',
    //       confidence: 0.97531042
    //     },
    //     {
    //       timestamp: '17/06/2024 14:15',
    //       protocol: 17,
    //       dstIp: "172.16.20.15",
    //       dstPort: 137,
    //       packetLengthMean: 6.1,
    //       srcIp: "198.18.3.84",
    //       srcPort: 98765,
    //       label: 'Malware',
    //       confidence: 0.99887766
    //     },
    //     {
    //       timestamp: '18/06/2024 09:30',
    //       protocol: 6,
    //       dstIp: "192.168.7.88",
    //       dstPort: 21,
    //       packetLengthMean: 14.2,
    //       srcIp: "203.0.113.66",
    //       srcPort: 54321,
    //       label: 'DDoS',
    //       confidence: 0.9999998807
    //     }
    //   ];
    // }, 1); // 假设延迟1秒钟获取数据，实际中替换为实际的API调用


    // 真实数据
    this.getData()
  },
  methods: {
    handleInfo(item){
      this.$emit('handleInfo', item);
    },
    detailsInfo(item){
      this.$emit('OpenDetails', item);
    },
    previousPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    getData(){
      getAllFlow().then(res=>{
        const filterArray = res.data
        console.log(filterArray)
        this.tableData = filterArray.filter(item => item.label !== 'BENIGN');
        console.log( this.tableData)
      })
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
    formatDate(inputDate) {
      // 分割输入的日期字符串
      let parts = inputDate.split(' ');
      // 分割日期部分
      let dateParts = parts[0].split('/');
      let day = parseInt(dateParts[0], 10);
      let month = parseInt(dateParts[1], 10);
      let year = parseInt(dateParts[2], 10);
      // 分割时间部分
      let timeParts = parts[1].split(':');
      let hours = parseInt(timeParts[0], 10);
      let minutes = parseInt(timeParts[1], 10);
      // 构建日期对象
      let date = new Date(year, month - 1, day, hours, minutes); // 月份需要减去1，因为月份是从0开始的
      // 格式化为 YYYY-MM-DD HH:mm 格式的字符串
      let formattedDate = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;
      return formattedDate;
    },
    formatConfidence(confidence) {
      // 将字符串转换为浮点数
      const floatValue = parseFloat(confidence);
      // 将浮点数转换为字符串，保留两位小数，但不进行四舍五入
      const formattedValue = floatValue.toFixed(2);
      // 直接截取两位小数
      const truncatedValue = Math.floor(floatValue * 100) / 100;
      // 返回截取后的结果
      return truncatedValue.toFixed(2);
    }
  }
};
</script>

<style scoped>
.table-container {
  margin: 2.5% auto;
  width: 98%;
  overflow-x: auto;
}
.IP-Text{
  color:#f85959 ;
  letter-spacing: 1.5px;

}
.protocol{
  color: #6f8bf8;
  letter-spacing: 1.5px;
}
.table {
  width: 100%;
  border-collapse: separate; /* 使用独立的边框模型 */
  border-spacing: 0; /* 确保边框间距为0 */
}

.table th,
.table td {
  padding: 8px;
  text-align: center;
  border-bottom: 5px solid #131521; /* 添加行间的底部边框 */
}

.table tbody {
  background: radial-gradient(
      circle,
      rgba(9, 103, 231, 0.31) 20%, /* Outer color */
      rgba(11, 44, 115, 0.51) 100% /* Inner color */
  );
  color: #cecece;
}

/* 为每行设置白色边框 */
.table tbody tr {
  border: 1px solid #ffffff !important; /* 使用 !important 提高优先级 */
}

.table th {
  color: #a9b1c9; /* 设置表头字体颜色 */
  border-bottom: 0px solid #ccc;
}

.table th.no-background {
  background-color: transparent; /* 移除表头背景色 */
}

.table td:nth-child(3) {
  /* 设置时间和攻击者画像列宽度较大 */
  min-width: 150px; /* 根据需要调整宽度 */
  white-space: nowrap; /* 不换行显示 */
  overflow: hidden;
  text-overflow: ellipsis;
}

.pagination {
  margin-top: 10px;
  text-align: center;
  position: absolute;
  top: -40px;
  right: 10px;
  color: white;
}

.pagination button {
  margin: 0 5px;
  padding: 2px 8px;
  cursor: pointer;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 2px;
}

.pagination button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
  color: #1a1a1a;
}
</style>
