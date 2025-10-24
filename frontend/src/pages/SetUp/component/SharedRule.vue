<template>
  <div class="table-container">
    <div style="display: flex; justify-content: space-between; margin: 0 2%; height: 20px; letter-spacing: 2px">
      <div style="color: #cecece;">共享威胁报告</div>
      <div style="font-size: 12px; line-height: 20px; color: #9f9898;">查看更多 ></div>
    </div>
    <table class="table">
      <thead>
      <tr>
        <th class="no-background">序号</th>
        <th class="no-background">报告名称</th>
        <th class="no-background">文件名称</th>
        <th class="no-background">操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(report, index) in reports" :key="index">
        <td style="width: 5%;">{{ index + 1 }}</td>
        <td style="width: 30%;">{{ report.name }}</td>
        <td style="width: 55%;">{{ report.filename }}</td>
        <td @click="viewDetails(report)" style="color:#438ad0;font-size: 14px; width: 10%; cursor: pointer;">下载查看</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      reports: [
        {
          name: 'DDoS攻击分析报告',
          filename: 'DDoS_Report_2024_06_13.pdf'
        },
        {
          name: '恶意软件传播报告',
          filename: 'Malware_Spread_Analysis_2024_06_12.pdf'
        },
        {
          name: '钓鱼攻击警报',
          filename: 'Phishing_Alert_2024_06_10.docx'
        },
        {
          name: '暴力破解尝试报告',
          filename: 'Brute_Force_Attempt_2024_06_09.xlsx'
        }
      ]
    }
  },
  methods: {
    viewDetails(report) {
      // Implement your logic for viewing details of the report
      console.log('View details of report:', report);
    }
  }
}
</script>

<style scoped>
.table-container {
  margin-top: 20px;
}

table {
  width: 96%;
  margin: 0.5% 2% 0;
  border-collapse: collapse;
  text-align: center;
  color: #fff;
  background-color: rgba(0, 0, 0, 0.5);
}

th,
td {
  padding: 10px;
  border: 1px solid rgba(255, 255, 255, 0.3);
}

th {
  background-color: rgba(0, 0, 0, 0.7);
}

tbody tr:nth-child(even) {
  background-color: rgba(255, 255, 255, 0.1);
}

tbody tr:nth-child(odd) {
  background-color: rgba(0, 0, 255, 0.1);
}
</style>
