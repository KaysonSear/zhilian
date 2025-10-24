<template>
  <div class="container">
    <div class="Box">
      <dv-border-box-2 :color="['#4F68B7', '#4F68B7']" dur="10"
                       style="padding: 10px;box-sizing: border-box;position:relative;">
        <div class="topTitle">移&nbsp;动&nbsp;云&nbsp;服&nbsp;务&nbsp;器</div>
        <div style="position:absolute;right: 5px;font-size: 14px;color: #6ba8ef;text-decoration: underline" @click="toHost()">查看私有云服务器></div>
        <div style="height: 50px"></div>
        <div class="MiniBox">
          <div class="title">基本情况</div>
          <div class="item">
            <div style="width: 45%">
              <div style="color: #4d79d9;">ID<span style="color: #CECECE;margin-left:20px;">i-f8zgkwftio3etkjg1dz3</span></div>
            </div>
            <div style="width: 30%">
              <div style="color: #4d79d9;">地域 <span style="color: #CECECE;margin-left:15px;">华南-广州3</span></div>
            </div>
          </div>
          <div class="item">
            <div style="width: 45%">
              <div style="color: #4d79d9;">公网IP<span style="color: #CECECE;margin-left:20px;">36.213.9.209</span></div>
            </div>
            <div style="width: 30%">
              <div style="color: #4d79d9;">私网IP <span style="color: #CECECE;margin-left:15px;">192.168.0.2</span></div>
            </div>
          </div>
        </div>
        <!--客户端状态-->
        <div class="MiniBox" style="height: 23%;">
          <div class="title">系统状态</div>
          <div class="item">
            <div style="width: 45%">
              <div style="color: #4d79d9;">客户端名称<span style="color: #5cf34f;margin-left:20px;">ECS-19558922</span></div>
            </div>
            <div style="width: 30%">
              <div style="color: #4d79d9;">操作系统 <span style="color: #CECECE;margin-left:15px;position: relative"><img src="@/assets/Linux.png" style="width: 24px;position: absolute;bottom:1px;right: 40px;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Linux </span></div>
            </div>
          </div>
          <div class="item">
            <div style="width: 45%">
              <div style="color: #4d79d9;">Agent状态<span style="color: #CECECE;margin-left:20px;">启用</span></div>
            </div>
            <div style="width: 30%">
              <div style="color: #4d79d9;">AgentID <span style="color: #CECECE;margin-left:15px;">6aarebc2e868d</span></div>
            </div>
          </div>
          <div class="item">
            <div style="width: 45%">
              <div style="color: #4d79d9;">CPU<span style="color: #CECECE;margin-left:20px;">Intel(R) Xeon(R) Platinum / 2核</span></div>
            </div>
            <div style="width: 30%">
              <div style="color: #4d79d9;">磁盘内存 <span style="color: #CECECE;margin-left:15px;">0.8G/40G</span></div>
            </div>
          </div>
        </div>
<!--        风险状态-->
        <div class="MiniBox" style="height: 40%;">
          <div class="title">近7日告警通知</div>
          <div style="display: flex">
            <div class="one">
              <danger />
            </div>
            <div class="one">
              <div v-for="(item, index) in data" :key="index" class="legend-item">
                <div class="block" :style="{ backgroundColor: item.color }"></div>
                <div class="name">{{ item.name }}</div>
                <div class="value">{{ item.value }}%</div>
              </div>
              <div class="LoadBtn" @click="toPage()">查看更多</div>
            </div>
          </div>
        </div>
      </dv-border-box-2>
    </div>
  </div>
</template>

<script>

import danger from '@/pages/NewBig/component/danger.vue'
export default {
  props: [],
  components: {
    danger
  },
  watch: {},
  data() {
    return {
      name: "",
      data: [
        {value: 35, name: '暴力破解',color:'#4f81bd'},
        {value: 26, name: 'SQL注入',color:'#c0504d'},
        {value: 17, name: 'DDoS攻击',color:'#9bbb59'},
        {value: 13, name: '异常登录',color:'#f79646'},
        {value: 9, name: '其他',color:'#8064a2'},
      ],
    }
  },
  created() {
  },
  mounted() {
  },
  methods: {
    toPage(){
      this.$router.push('/datas');
    },
    toHost(){
      this.$router.push('/HostComputer');
    }
  },
  beforeDestroy() {
  }

}
</script>

<style scoped>
.container {
  padding: 0 30px;
  height: 100vh;
}

.Box {
  width: 75%;
  height: 80%;
  margin: 2% auto;
}

.topTitle {
  position: absolute;
  color: #EEC101;
  font-size: 25px;
  font-weight: 700;
  top: 3%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.MiniBox {
  width: 95%;
  margin: 0 auto;
  height: 18%;
  background-color: rgba(66, 99, 117, 0.42);
  box-sizing: border-box;
  margin-bottom: 20px;
}

.title {
  color: #CECECE;
  padding: 10px;
}
.item{
  color: #CECECE;
  font-size: 16px;
  display: flex;
  justify-content: space-between;
  padding: 0 200px 0 50px;
  margin-bottom: 12px;
}
.one{
  position: relative;
  flex: 1;
}
.legend-item {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
  font-size: 15px;
  color: #CECECE;
}

.block {
  width: 20px;
  height: 20px;
  margin-right: 8px;
  border-radius: 3px;
}

.name {
  flex: 1;
}

.value {
  flex: 4;
  margin-left: 8px;
}

.LoadBtn {
  height: 12%;
  width: 20%;
  padding: 1%;
  text-align: center;
  font-size: 15px;
  border: 1px solid #298ee1;
  background-color: rgba(42, 58, 83, 0.73);
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
  /* 添加鼠标指针 */
  color: #b7eaf5;
}
</style>
