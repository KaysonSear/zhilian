<template>
  <div>
    <div class="reportBox">
      <div class="title">安全威胁报告</div>
      <div class="data">报告日期: 2024年6月26日 <span @click="downloadPDF" style="color: #5caefd">下载</span></div>
      <div class="Htitle">概述：</div>
      <div class="content"><p>· 本报告基于从<span
          style="text-decoration: underline; color:#46a5f5;">异常网络流量包</span>检测到的安全事件数据，分析了当前系统面临的主要安全威胁。
      </p></div>
      <div class="Htitle">检测概要：</div>
      <div class="content"><p>· 总检测流量数： 1120</p></div>
      <div class="content"><p>· 攻击类型覆盖： 包括DDoS、DoS (GoldenEye)、DoS (Hulk)、Port Scan等多种攻击类型。</p></div>
      <div class="Htitle">攻击类型分析：</div>
      <div v-for="(attack, index) in attacks" :key="index" class="attack">
        <div class="content">
          <p>· {{ attack.name }}</p>
          <div style="padding-left: 30px">
            <p>检测次数： {{ attack.count }}</p>
            <p>攻击特征： {{ attack.characteristics }}</p>
            <p v-if="attack.impact">影响范围： {{ attack.impact }}</p>
            <p v-if="attack.target">目标系统： {{ attack.target }}</p>
          </div>
        </div>
      </div>
      <div class="Htitle">威胁评估：</div>
      <div class="content"><p>· 高风险威胁： DDoS攻击和DoS
        (GoldenEye)攻击对业务可用性造成直接威胁，需要立即采取防御措施。</p></div>
      <div class="content"><p>· 中风险威胁：
        SQL注入和恶意扫描暗示可能存在系统漏洞或配置错误，需要进行进一步的安全审查和修复。</p></div>
      <div class="Htitle">建议措施：</div>
      <div class="content" v-for="(defence,index) in  defences" >
        <p>· {{ defence.name }}</p>
        <div style="padding-left: 30px">
          <p>· {{ defence.first }}</p>
          <p>· {{ defence.second }}</p>
          <p>· {{ defence.third }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import html2pdf from 'html2pdf.js';

export default {
  data() {
    return {
      showSuccessMessage:false,
      name: "",
      attacks: [
        {
          name: 'DDoS攻击',
          count: 32,
          characteristics: '主要集中在HTTP Flood和UDP Flood攻击。',
          target: '多数攻击针对内部Web服务器和DNS服务器。'
        },
        {
          name: 'DoS (GoldenEye)攻击',
          count: 2,
          characteristics: '使用特定的DoS工具进行持久性攻击。',
          impact: '导致目标系统服务不可用，需要紧急响应和缓解措施。'
        },
        {
          name: 'DoS (Hulk)攻击',
          count: 72,
          characteristics: '使用HTTP DoS攻击向量，造成系统资源耗尽。',
          target: '主要针对Web服务器和应用程序服务器。'
        },
        {
          name: 'Port Scan',
          count: 86,
          characteristics: '主要针对内部网络的端口扫描，探测开放服务和潜在漏洞。'
        },
        {
          name: '其他攻击类型',
          characteristics: '恶意软件传播： 100次，SQL注入尝试： 100次。'
        }
      ],
      defences: [
        {
          name: 'DDoS攻击',
          first: '流量监控和分析：实时监测网络流量，检测异常流量模式。',
          second: '流量过滤：配置防火墙或专用设备过滤掉来自已知攻击源的流量。',
          third: '负载均衡：通过负载均衡技术分散攻击流量，保护关键服务。'
        },
        {
          name: 'DoS攻击 (GoldenEye, Hulk等)',
          first: '行为分析：监测和分析系统的行为，及时识别异常请求和攻击行为。',
          second: 'IP封锁：临时封锁来自攻击源的IP地址，阻止攻击。',
          third: '服务限制：实施服务限制策略，防止攻击耗尽系统资源。'
        },
        {
          name: '端口扫描',
          first: '实时检测：监测和记录所有的端口扫描活动。',
          second: '端口管理：减少未使用的开放端口，最小化攻击面。',
          third: 'IDS规则更新：确保IDS能够检测到最新的端口扫描技术。'
        }
      ]
    }
  },
  methods: {
    downloadPDF() {
      const element = document.querySelector('.reportBox'); // 获取需要转换为PDF的DOM元素
      this.$message.success('下载成功！');
      html2pdf().from(element).save('security_report.pdf'); // 将DOM元素转换为PDF并保存
    }
  },
}
</script>

<style scoped>
.success-message {
  position: absolute;
  top: 50%;
  right: 43%;
  background-color: #4CAF50;
  color: white;
  padding: 15px;
  border-radius: 5px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.2);
  animation: slideOut 1s forwards; /* 动画效果：1秒后向上滑出并隐藏 */
}
.reportBox{
  padding: 20px;
}

.title {
  text-align: center;
  font-size: 25px;
  letter-spacing: 3px;
  font-weight: bold;
  position: relative;
  color: #179ee8;
  padding-bottom: 15px;
  border-bottom: 1px solid rgba(182, 182, 182, 0.63);
}

.data {
  text-align: right;
  color: #e8e8e8;
  font-size: 15px;
  padding: 20px 0 5px;
  letter-spacing: 2px;
}

.Htitle {
  font-size: 18px;
  font-weight: bold;
  letter-spacing: 2px;
  color: #4094f5;
}

.content {
  padding-left: 10px;
}
</style>
