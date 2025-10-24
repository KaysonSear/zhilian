<template>
  <div class="container">
    <div class="TopArea">
      <!--      -->
      <div class="leftBox">
<!--        <div class="TLbox">-->
<!--          <div class="topTitle">系 统 风 险 评 估</div>-->
<!--          <dv-border-box-13>-->
<!--            <panel></panel>-->
<!--          </dv-border-box-13>-->
<!--        </div>-->
        <div class="TLbox">
          <div class="topTitle" style="top: 5%">实 时 IP 访 问 统 计</div>
          <dv-border-box-13>
<!--            <bubble></bubble>-->
            <div style="padding-top: 50px"></div>
            <IPstatistic ></IPstatistic>
          </dv-border-box-13>
        </div>
      </div>
      <!--      -->
      <div class="rightBox">
        <div class="TRbox">
          <dv-border-box-2 :color="['#4F68B7', '#4F68B7']" dur="10"
                           style="padding: 10px;box-sizing: border-box;position:relative;">
            <dv-decoration-7
                style="width:150px;height:30px;position:absolute;color: #cecece;z-index: 999;justify-content: space-between;  top: 2%; left: 50%;transform: translate(-50%, -50%);">
              I P 流 向
            </dv-decoration-7>
<!--            <Map></Map>-->
            <chinaMap></chinaMap>
          </dv-border-box-2>
        </div>
      </div>
    </div>
    <div class="BottomArea">
      <div class="leftBox">
        <div class="BLbox">
          <div class="topTitle">控 制 中 心</div>
          <dv-border-box-13 style="box-sizing: border-box;padding: 12% 5% 5%;">
            <div style="position: absolute;right: 10px;top: -20px;font-size: 14px;color: #75b3ef;text-decoration: underline" @click="tab=!tab">切换查看</div>
            <div v-if="!tab">
              <div >
                <img src="@/assets/1.png" alt="" style="width: 80px;height: 25px;">
              </div>
            <div style="font-size: 14px;color:#CECECE;margin-top: 5px;margin-left: 5px;">
              <div style="display: flex;width: 100%;margin-bottom: 8px;">
               <div style="flex: 2"> 主机名称：ECS-19558922</div>
               <div style="flex: 1"> 状态：<span style="color:#29C26B ">运行中</span></div>
              </div>
              <div style="display: flex;width: 100%;margin-bottom: 15px;">
                <div style="flex: 2"> 操作系统：Linux</div>
                <div style="flex: 1"> IP：36.213.9.209</div>
              </div>

              <div style="display: flex;width: 100%;margin-bottom: 15px;">
                <div style="flex: 2"> 系统状态：<span style="color:#ff5e5e;font-size: 11px;padding: 2px 10px; border: 1px solid #ff5e5e;border-radius: 3px">异常</span></div>
              </div>
              <div style="font-size: 15px;color:#f86363;text-align: center ">服务器当前存在多种威胁攻击，请及时处理！</div>
              <div class="Btn">
                <button @click="toPage ">查看详情</button>
              </div>
            </div>
            </div>
            <div v-if="tab">
              <div >
                <img src="@/assets/2.png" alt="" style="width: 80px;height: 25px;">
              </div>
              <div style="font-size: 14px;color:#CECECE;margin-top: 5px;margin-left: 5px;">
                <div style="display: flex;width: 100%;margin-bottom: 8px;">
                  <div style="flex: 2"> 主机名称：Linux服务器</div>
                  <div style="flex: 1"> 状态：<span style="color:#29C26B ">运行中</span></div>
                </div>
                <div style="display: flex;width: 100%;margin-bottom: 15px;">
                  <div style="flex: 2"> 操作系统：Linux</div>
                  <div style="flex: 1"> IP：192.168.10.50</div>
                </div>

                <div style="display: flex;width: 100%;margin-bottom: 15px;">
                  <div style="flex: 2"> 系统状态：<span style="color:#ff7f2f;font-size: 11px;padding: 2px 10px; border: 1px solid #ff7f2f;border-radius: 3px">告警</span></div>
                </div>
                <div style="font-size: 15px;color:#ff7f2f;text-align: center ">当前服务器出现疑似异常流量，请及时处理！</div>
                <div class="Btn">
                  <button @click="toHost ">查看详情</button>
                </div>
              </div>
            </div>
          </dv-border-box-13>
        </div>
      </div>
      <div class="rightBox">
        <div class="BRbox">
          <div  class="topTitle">实 时 告 警 检 测<span style="color: #cecece;font-size: 10px">（24小时）</span></div>
          <dv-border-box-13>
            <LineWarn></LineWarn>
          </dv-border-box-13>
        </div>
        <div class="BRbox2">
          <div  class="topTitle">攻 击 数 据 统 计</div>
          <dv-border-box-13>
            <BarChart></BarChart>
          </dv-border-box-13>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LineWarn from '@/pages/Index/component/LineWarn.vue'
import Map from '@/pages/Index/component/Map.vue'
import Panel from '@/pages/Index/component/Panel.vue'
import BarChart from '@/pages/Index/component/BarChart.vue'
import bubble from "@/pages/Index/component/bubble.vue";
import chinaMap from "@/pages/Index/component/chinaMap.vue";
import IPstatistic from "@/pages/Index/component/IPstatistic.vue";
export default {
  components: {
    Map,
    LineWarn,
    Panel,
    BarChart,
    bubble,
    chinaMap,
    IPstatistic
  },
  data() {
    return {
      tab:false,
      activeTabName: 'mobileCloud' // 默认显示的标签页
    }
  },
  methods:{
    toPage(){
      this.$router.push('/computer');
    },
    toHost(){
      this.$router.push('/HostComputer');
    }
  }
}
</script>

<style scoped>
.Btn {
  text-align: center;
  margin: 10px auto;
  button {
    width: 40%;
    font-size: 12px;
    border: 1px solid #298ee1;
    background-color: rgba(42, 58, 83, 0.73);
    color: #b7eaf5;
    border-radius: 2px;
    cursor: pointer;
    padding: 5px;
  }
}
.container {
  padding: 0 30px;
  height: 100vh;
}

.TopArea {
  display: flex;
  height: 58%;

}

.BottomArea {
  margin-top: 10px;
  display: flex;
  height: 30%;
}

.leftBox {
  flex: 1.2;
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  .TLbox {
    height: 100%;
    position: relative;
  }

  .BLbox {
    width: 100%;
    position: relative;
    height: 100%;
  }
}

.topTitle {
  position: absolute;
  color: #EEC101;
  font-size: 20px;
  font-weight: 700;
  top: 10%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.rightBox {
  flex: 3;

  .TRbox {
    position: relative;
    margin: 0.5% 1%;
    height: 98%;
  }

  .BRbox {
    width: 60%;
    position: relative;
    height: 100%;
    margin: 0 1%;
  }
  .BRbox2{
    position: absolute;
    width: 25%;
    height: 30%;
    margin: 0 1%;
    bottom: 6px;
    right: 20px;
  }
}
</style>
