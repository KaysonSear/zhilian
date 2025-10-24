<template>
  <div style=" ">
    <div class="top">
      <div class="peopleInfo">
        <div class="title">攻击者画像</div>
        <div style="margin-top: 3%;margin-left: 5%;">
          <div style="display: flex; font-size: 17px">
            <div style="flex: 1">攻击者IP: <span style="color: #007df6;font-size: 16px">{{ items.srcIp }}</span> </div>
            <div style="flex: 1">地理位置: <span style="color: #007df6;font-size: 16px">广东深圳</span> <span style="font-size: 11px;color: #6d87a1">(有误差)</span></div>
          </div>
          <div style="display: flex;margin-top: 2%; font-size: 17px">
            <div style="flex: 1">攻击者端口号: <span style="color: #007df6;font-size: 16px">{{ items.srcPort }}</span></div>
            <div style="flex: 1">协议信息: <span style="color: #007df6;font-size: 16px">{{ getProtocolName(items.protocol) }}</span></div>
          </div>
        </div>
        <br>
        <br>
        <div class="title">攻击对象</div>
        <div style="margin-top: 5%;margin-left: 5%;">
          <div style="display: flex; font-size: 17px">
            <div style="flex: 1">目标IP: <span style="color: #007df6;font-size: 16px">{{ items.dstIp }}</span> </div>
            <div style="flex: 1">目标端口号: <span style="color: #007df6;font-size: 16px">{{ items.dstPort }}</span></div>
          </div>
        </div>
        <br>
        <br>
       <div class="title">攻击信息</div>
        <div style="margin-top: 5%;margin-left: 5%;">
          <div style="display: flex; font-size: 17px">
            <div style="flex: 1">攻击方式: <span style="color: #e0411e;font-size: 16px">{{ items.label }}</span> </div>
            <div style="flex: 1">攻击时间: <span style="color: #007df6;font-size: 16px">{{ items.timestamp }}</span></div>
          </div>
          <div style="display: flex;margin-top: 2%; font-size: 17px">
            <div style="flex: 1">数据包长度: <span style="color: #007df6;font-size: 16px">{{ items.packetLengthMean }} 字节</span></div>
            <div style="flex: 1">攻击持续时间: <span style="color: #007df6;font-size: 16px">2.64 秒</span></div>
          </div>
        </div>
      </div>
      <div class="img"><img src="@/assets/hank.png" alt=""></div>
    </div>
    <div class="bottom">
      <div class="content">
        <div class="tip">以下内容由威胁情报AI助手生成</div>
        <div class="msgBox">
          <div class="avatar"><img src="@/assets/AI.png" alt=""></div>
          <div class="msg">
            <div>{{AttackContent.overview}}{{AttackContent.techniques}}</div>
            <div style="margin-top: 5px;"><span style="font-weight: bold;">影响：</span>{{AttackContent.impact}}</div>
            <div style="margin-top: 5px;"><span style="font-weight: bold;">防御手段：</span>{{AttackContent.defenses}}</div>
            <div style="margin-top: 5px;"><span style="font-weight: bold;">总结：</span>{{AttackContent.conclusion}}</div>
          </div>
        </div>
      </div>
    </div>
    <button style="text-align: center;margin:0 39% ;margin-top: 20px; padding: 10px;border: 1px solid #98c6f3;background-color:transparent;color: #98c6f3" @click="toPage()">去进行安全威胁溯源</button>
  </div>
</template>

<script>
import {getAttackType} from '@/API';
export default {
  props: {
    items: {
      type: Object,
      required: true
    }
  },
  watch: {},
  data() {
    return {
      AttackContent:{
        id: 2,
        label: "DDoS",
        overview: "DDoS攻击是DoS攻击的变种，利用多个来源的计算机或设备同时攻击目标系统，使得攻击更具破坏力和持久性。",
        techniques: "利用Botnet进行攻击、分布式协同攻击。",
        impact: "更高的网络压力、服务长时间不可用。",
        defenses: "流量清洗、分布式防御、行为分析。",
        conclusion: "通过多层次的防护措施来应对DDoS攻击，保障网络服务的可用性和稳定性。"
      }
    }
  },
  created() {
  },
  mounted() {
    console.log(this.items.label)
    this.getLabelContent(this.items.label)
  },
  methods: {
    toPage(){
      this.$router.push('/MyChat');
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
    getLabelContent(label){
      getAttackType(label).then(res=>{
        this.AttackContent = res.data[0]
        console.log(this.AttackContent)
      })
    }

  },
  components: {},
  beforeDestroy() {

  }

}
</script>

<style scoped>
.top {
  display: flex;
}
.bottom{
  margin-top: 5%;
  width: 100%;
  background-color: #343434;
  min-height: 200px;
  padding-bottom: 10px;
}
.tip {
  position: relative; /* 让 ::before 伪元素相对于 .title 定位 */
  padding-left: 20px; /* 留出空间给柱体 */
}
.tip::before {
  content: ''; /* 必须有 content 属性，才能显示伪元素 */
  position: absolute; /* 绝对定位，相对于 .title */
  left: 0; /* 柱体的位置 */
  top: 50%; /* 垂直居中 */
  transform: translateY(-50%); /* 垂直居中 */
  width: 7px; /* 柱体宽度 */
  border-radius: 0 3px 3px 0;
  height: 20px; /* 柱体高度 */
  background-color: #298ee1;
}
.content{
  padding-top: 10px;
  color: #cecece;
}
.msgBox{
  display: flex;
  margin: 3% 4%;
  color: #1a1a1a;
  .avatar{
    width: 35px;
    height: 35px;
    background-color: #298ee1;
    border-radius: 100%;
  }
  img{
    width: 100%;
    height: 100%;
  }
  .msg {
    position: relative; /* 相对定位，用于定位伪元素 */
    margin-left: 18px;
    width: 100%;
    background-color: #dadada;
    padding: 10px; /* 增加内边距，让内容不靠近边框 */
  }

  .msg::before {
    content: ''; /* 必须有 content 属性才能显示伪元素 */
    position: absolute; /* 绝对定位，相对于 .msg 定位 */
    top: 15px; /* 位于垂直中心 */
    left: -16px; /* 位于 .msg 左侧外边 */
    margin-top: -5px; /* 调整位置，使箭头垂直居中 */
    border-width: 8px; /* 箭头的尺寸 */
    border-style: solid;
    border-color: transparent #dadada transparent transparent; /* 箭头颜色，左侧透明 */
  }
}
.peopleInfo {
  width: 75%;
  color: #cecece;

  .title {
    color: #298ee1;
    font-size: 20px;
    padding: 2px;
    letter-spacing: 2px;
    background-color: rgba(48, 106, 154, 0.45);
    border: 1px solid #298ee1;
    width: 120px;
    text-align: center;
    border-radius: 1px;
  }
}

.img {
  margin-left: 5%;
  position: relative;
  overflow: hidden;
  width: 120px; /* 根据需要调整宽度和高度 */
  height: 120px;
  display: inline-block;
  border: 1px solid rgba(22, 100, 155, 0.8); /* 初始边框，透明 */
  transition: border-color 0.3s ease-in-out; /* 边框颜色过渡效果 */

  img {
    width: 100%; /* 让图片充满父容器 */
    height: 100%;
    opacity: 0.8; /* 图片透明度 */
  }

  &::before {
    content: '';
    position: absolute;
    top: -100%; /* 初始位置在顶部之外 */
    left: 0;
    width: 100%;
    height: 1%;
    background-color: rgba(255, 0, 0, 0.8); /* 扫描条的颜色，这里是科技蓝色半透明 */
    animation: scan 3.5s linear infinite; /* 扫描动画 */
    z-index: 999;
  }
}

@keyframes scan {
  0% {
    top: -100%; /* 扫描条从顶部开始 */
  }
  100% {
    top: 100%; /* 扫描条到达底部 */
  }
}
</style>
