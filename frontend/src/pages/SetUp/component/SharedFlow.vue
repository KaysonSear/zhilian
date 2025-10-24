<template>
  <div class="table-container">
    <div style="display: flex; justify-content: space-between; margin: 0 2%; height: 20px; letter-spacing: 2px">
      <div style="color: #cecece;">恶意流量信息</div>
      <div style="font-size: 12px; line-height: 20px; color: #9f9898;">查看更多 ></div>
    </div>
    <table class="table">
      <thead>
      <tr>
        <th class="no-background">序号</th>
        <th class="no-background">攻击者（IP、端口）</th>
        <th class="no-background">攻击类型</th>
        <th class="no-background">操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(attack, index) in attacks" :key="index">
        <td>{{ index + 1 }}</td>
        <td style="text-align: left;padding: 0 10%"><span style="color: #fa543c;">攻击者IP：{{ attack.attacker }}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #b7a952;">源端口：{{ attack.port }}</span>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #50a7fa;">协议：{{ attack.protocol }}</span></td>
        <td>{{ attack.attackType }}</td>
        <td style="color:#438ad0;font-size: 14px; ">查看详情</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      attacks: [
        { attacker: '192.168.1.1',port:'54', attackType: 'SQL注入攻击',protocol:'TCP' },
        { attacker: '192.168.0.2', port:'80',attackType: 'DDoS攻击',protocol:'TCP' },
        { attacker: '172.162.0.5', port:'22',attackType: 'SSH暴力破解' ,protocol:'TCP'},
      ]
    };
  },
  methods: {
    viewDetails(attack) {
      // Implement your logic for viewing details of the attack
      console.log('View details of attack:', attack);
      // Example: Show details in a modal or perform another action
    }
  }
};
</script>

<style scoped>
table {
  width: 96%;
  margin: 0.5% 2% 0;
  border-collapse: collapse;
  text-align: center;
  color: #fff;
  background-color: rgba(0, 0, 0, 0.5);
}

th, td {
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
