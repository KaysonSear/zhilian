<template>
  <div class="container">
    <div class="Box">
      <dv-border-box-2
          :color="['#4F68B7', '#4F68B7']"
          dur="10"
          style="padding: 10px; box-sizing: border-box; position: relative;"
      >
        <div class="topTitle">移&nbsp;&nbsp; 动&nbsp;&nbsp; 云&nbsp;&nbsp; 告&nbsp;&nbsp; 警&nbsp;&nbsp; 通&nbsp;&nbsp; 知</div>

        <div>
          <!-- 表格展示 -->
          <div class="process-table">
            <table>
              <thead>
              <tr>
                <th>序号</th>
                <th>风险程度</th>
                <th>告警名称</th>
                <th>影响资产</th>
                <th>关注点</th>
                <th>ATT&CK攻击阶段</th>
                <th>最近攻击时间</th>
                <th>操作项</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(process, index) in alertData" :key="index">
                <td >{{ index+1 }}</td>
                <td :style="{ color: getRiskColor(process.riskLevel) }">{{ process.riskLevel }}</td>
                <td @click="toPage(process)">{{ process.alertName }}</td>
                <td>
                  <ul class="list-items">
                    <li v-for="(asset, idx) in process.assets" :key="idx">{{ asset }}</li>
                  </ul>
                </td>
                <td>
                  <ul class="list-items">
                    <li v-for="(point, idx) in process.attentionPoints" :key="idx">{{ point }}</li>
                  </ul>
                </td>
                <td>
                  <ul class="list-items">
                    <li v-for="(phase, idx) in process.attackPhase.split('，')" :key="idx">{{ phase }}</li>
                  </ul>
                </td>
                <td>{{ process.lastAttackTime }}</td>
                <td ><button style="color: #6ba9e5;font-size: 14px;padding: 5px;border: 1px solid #6ba9e5;background-color:rgba(255,255,255,0);" @click="toPage">分析样本</button></td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </dv-border-box-2>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      alertData: [
        {
          riskLevel: '中危',
          alertName: '发现暴力破解行为，密码爆破',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:49.73.136.246(中国江苏)', '攻击端口:22', '服务类型:sshd'],
          attackPhase: '初始入口，横向渗透',
          lastAttackTime: '2024/5/10 21:23',
          lastHandledTime: '2024/5/10 21:23',
          alertStatus: '已防御',
        },
        {
          riskLevel: '高危',
          alertName: '发现暴力破解行为，密码爆破',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:203.0.113.45(美国加利福尼亚)', '攻击端口:22', '服务类型:sshd'],
          attackPhase: '初始入口，口令破解',
          lastAttackTime: '2024/8/1 10:15',
          lastHandledTime: '2024/8/1 10:30',
          alertStatus: '处理中',
        },
        {
          riskLevel: '中危',
          alertName: '检测到SSH暴力破解尝试',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:198.51.100.23(英国伦敦)', '攻击端口:22', '服务类型:sshd'],
          attackPhase: '初始入口，字典攻击',
          lastAttackTime: '2024/8/2 03:45',
          lastHandledTime: '2024/8/2 04:00',
          alertStatus: '已防御',
        },
        {
          riskLevel: '中危',
          alertName: '检测到SSH暴力破解尝试',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:198.51.100.23(英国伦敦)', '攻击端口:22', '服务类型:sshd'],
          attackPhase: '初始入口，字典攻击',
          lastAttackTime: '2024/8/2 03:45',
          lastHandledTime: '2024/8/2 04:00',
          alertStatus: '已防御',
        },
        {
          riskLevel: '中危',
          alertName: '发现XSS攻击行为',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:195.34.23.67(中国广东)', '攻击端口:8080', '服务类型:apache'],
          attackPhase: '信息窃取，篡改数据',
          lastAttackTime: '2024/5/12 16:30',
          lastHandledTime: '2024/5/12 16:45',
          alertStatus: '已防御',
        },
        {
          riskLevel: '高危',
          alertName: '发现恶意软件行为',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:89.45.23.56(中国广东)', '攻击端口:21', '服务类型:ftpd'],
          attackPhase: '初始入口，感染系统',
          lastAttackTime: '2024/5/13 09:00',
          lastHandledTime: '2024/5/13 09:15',
          alertStatus: '处理中',
        },
        {
          riskLevel: '高危',
          alertName: '发现DDoS攻击行为',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:192.0.2.123(中国上海)', '攻击端口:80', '服务类型:HTTP'],
          attackPhase: '资源消耗，服务中断',
          lastAttackTime: '2024/8/1 14:45',
          lastHandledTime: '2024/8/1 15:00',
          alertStatus: '处理中',
        },
        {
          riskLevel: '高危',
          alertName: '发现DDoS攻击行为',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:192.0.2.123(中国上海)', '攻击端口:80', '服务类型:HTTP'],
          attackPhase: '资源消耗，服务中断',
          lastAttackTime: '2024/8/1 14:45',
          lastHandledTime: '2024/8/1 15:00',
          alertStatus: '处理中',
        },
        {
          riskLevel: '高危',
          alertName: '发现DDoS攻击行为',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:203.0.113.11(广东深圳)', '攻击端口:443', '服务类型:HTTPS'],
          attackPhase: '资源消耗，服务中断',
          lastAttackTime: '2024/8/2 16:20',
          lastHandledTime: '2024/8/2 16:35',
          alertStatus: '处理中',
        },
        {
          riskLevel: '高危',
          alertName: '发现DDoS攻击行为',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:198.51.100.45(广东深圳)', '攻击端口:8080', '服务类型:Web服务'],
          attackPhase: '资源消耗，服务中断',
          lastAttackTime: '2024/8/3 12:05',
          lastHandledTime: '2024/8/3 12:20',
          alertStatus: '处理中',
        },
        {
          riskLevel: '中危',
          alertName: '检测到SSH暴力破解尝试',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:150.158.104.171(中国上海)', '攻击端口:22', '服务类型:sshd'],
          attackPhase: '暴力破解行为，密码爆破',
          lastAttackTime: '2024/8/2 03:45',
          lastHandledTime: '2024/8/2 04:00',
          alertStatus: '已防御',
        },
        {
          riskLevel: '高危',
          alertName: '发现DDoS攻击行为',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:192.0.2.123(中国上海)', '攻击端口:80', '服务类型:HTTP'],
          attackPhase: '资源消耗，服务中断',
          lastAttackTime: '2024/8/1 14:45',
          lastHandledTime: '2024/8/1 15:00',
          alertStatus: '处理中',
        },
        {
          riskLevel: '高危',
          alertName: '发现DDoS攻击行为',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:192.0.2.123(中国上海)', '攻击端口:80', '服务类型:HTTP'],
          attackPhase: '资源消耗，服务中断',
          lastAttackTime: '2024/8/1 14:45',
          lastHandledTime: '2024/8/1 15:00',
          alertStatus: '处理中',
        },
        {
          riskLevel: '高危',
          alertName: '发现DDoS攻击行为',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:203.0.113.11(广东深圳)', '攻击端口:443', '服务类型:HTTPS'],
          attackPhase: '资源消耗，服务中断',
          lastAttackTime: '2024/8/2 16:20',
          lastHandledTime: '2024/8/2 16:35',
          alertStatus: '处理中',
        },
        {
          riskLevel: '高危',
          alertName: '发现DDoS攻击行为',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:198.51.100.45(广东深圳)', '攻击端口:8080', '服务类型:Web服务'],
          attackPhase: '资源消耗，服务中断',
          lastAttackTime: '2024/8/3 12:05',
          lastHandledTime: '2024/8/3 12:20',
          alertStatus: '处理中',
        },
        {
          riskLevel: '中危',
          alertName: '检测到异常登录尝试',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:192.168.1.100(未知位置)', '攻击端口:22', '服务类型:sshd'],
          attackPhase: '账号枚举，尝试登录',
          lastAttackTime: '2024/8/3 08:15',
          lastHandledTime: '2024/8/3 08:30',
          alertStatus: '已防御',
        },
        {
          riskLevel: '高危',
          alertName: '检测到SQL注入攻击',
          assets: ['ECS-19558922', '192.168.0.2（私）', '36.213.9.209（公）'],
          attentionPoints: ['攻击IP:198.51.100.200(美国德州)', '攻击端口:443', '服务类型:HTTPS'],
          attackPhase: '数据操纵，尝试窃取',
          lastAttackTime: '2024/8/3 11:00',
          lastHandledTime: '2024/8/3 11:15',
          alertStatus: '处理中',
        },
        {
          riskLevel: '中危',
          alertName: '发现恶意流量行为',
          assets: ['ECS-19558923', '192.168.1.10（私）', '36.213.10.209（公）'],
          attentionPoints: ['攻击IP:172.16.0.2(新加坡)', '攻击端口:8080', '服务类型:Web服务'],
          attackPhase: '数据窃取，篡改流量',
          lastAttackTime: '2024/8/4 10:30',
          lastHandledTime: '2024/8/4 10:45',
          alertStatus: '已防御',
        },
      ],
    };
  },
  methods: {
    getRiskColor(level) {
      switch (level) {
        case '高危':
          return '#f56c6c'; // 红色
        case '中危':
          return '#e6a23c'; // 橙色
        case '低危':
          return '#67c23a'; // 绿色
        default:
          return '#909399'; // 灰色
      }
    },
    toPage(){
      this.$router.push('/chat');
    }
  },
};
</script>

<style scoped>
.container {
  padding: 0 15px;
  height: 100vh;
}

.Box {
  width: 100%;
  height: 80%;
  margin: 2% auto;
}

.topTitle {
  width: 100%;
  height: 50px;
  text-align: center;
  color: #eec101;
  font-size: 25px;
  font-weight: 700;
}

.process-table {
  width: 98%;
  margin: 0 1%;
  max-height: 610px; /* 设置最大高度 */
  overflow-y: auto;  /* 启用垂直滚动 */
  overflow-x: auto;  /* 启用水平滚动，确保宽度超出时可以滚动 */
  display: block;    /* 允许块级别显示 */
}

table {
  width: 100%;
  border-collapse: collapse;
  text-align: center;
  color: #fff;
  background-color: rgba(0, 0, 0, 0.5);
}

thead {
  position: sticky; /* 使表头固定 */
  top: -1px;           /* 设置表头的顶端为0 */
  background-color: rgba(0, 0, 0, 1); /* 设置背景色 */
  z-index: 1;       /* 确保表头在内容上方 */
}

th, td {
  padding: 10px;
  border: 1px solid rgba(255, 255, 255, 0.3);

}

tbody tr:nth-child(even) {
  background-color: rgba(255, 255, 255, 0.1);
}

tbody tr:nth-child(odd) {
  background-color: rgba(0, 0, 255, 0.1);
}


.list-items {
  padding: 0;
  margin: 0;
  list-style: none;
}

.list-items li {
  padding: 2px 0;
}
</style>
