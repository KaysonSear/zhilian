<template>
  <div class="container">
    <div class="TopArea">
      <div class="left">
        <div class="topTitle">主 机 信 息</div>
        <dv-border-box-2 :color="['#4F68B7', '#4F68B7']"
                         style="padding: 10px;box-sizing: border-box;position:relative;">
          <Computer></Computer>
        </dv-border-box-2>
      </div>
      <div class="right">
        <div class="topTitle"   style="top: 6%">进 程 监 控</div>
        <dv-border-box-2 :color="['#4F68B7', '#4F68B7']" dur="10"
                         style="padding: 10px;box-sizing: border-box;position:relative;">
        <ProcessMonitoring></ProcessMonitoring>
        </dv-border-box-2>
      </div>
    </div>
    <div class="BottomArea">
      <div class="OneBox">
        <div class="topTitle">系 统 状 态</div>
        <dv-border-box-13>
          <system-state ></system-state>
        </dv-border-box-13>
      </div>
      <div class="OneBox">
        <div class="topTitle">文 件 监 控</div>
        <dv-border-box-13>
          <FileTamperChart></FileTamperChart>
        </dv-border-box-13>
      </div>
      <div class="OneBox">
        <div class="topTitle">流 量 监 控</div>
        <dv-border-box-13>
          <div class="Btn">
            <button @click=" ">上传流量包分析</button>
          </div>
          <TrafficFlowChart></TrafficFlowChart>
        </dv-border-box-13>
      </div>
    </div>
  </div>
</template>

<script>
import systemState from "@/pages/Host/component/systemState.vue";
import ProcessMonitoring from "@/pages/Host/component/ProcessMonitoring.vue";
import FileTamperChart from "@/pages/Host/component/FileTamperChart.vue";
import TrafficFlowChart from "@/pages/Host/component/TrafficFlowChart.vue";
import Computer from "@/pages/Host/component/Computer.vue";
export default {
  props: [],
  watch: {},
  data() {
    return {
      name: "",
    }
  },
  created() {
  },
  mounted() {
  },
  methods: {},
  components: {
    systemState,
    ProcessMonitoring,
    FileTamperChart,
    TrafficFlowChart,
    Computer
  },
  beforeDestroy() {

  }

}
</script>

<style scoped>
.Btn {
  text-align: center;
  margin: 10px auto;
  position: absolute;
  right: 25px;
  top: 25px;
  button {
    width: 100%;
    font-size: 12px;
    border: 1px solid #298ee1;
    background-color: rgba(42, 58, 83, 0.73);
    color: #b7eaf5;
    border-radius: 3px;
    cursor: pointer;
    padding: 5px 6px;
  }
}
.container {
  padding: 0 30px;
  height: 100vh;
}

.TopArea {
  height: 40%;
  display: flex;

  .left {
    flex: 1.2;
    height: 100%;
    margin-right: 3%;
    position: relative;
  }

  .right {
    flex: 3;
    height: 100%;
    position: relative;
  }
}
.topTitle {
  position: absolute;
  color: #EEC101;
  font-size: 20px;
  font-weight: 700;
  top: 7%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.BottomArea {
  height: 40%;
  margin-top: 2%;
  display: flex;
  justify-content: space-between;

  .OneBox {
    position: relative;
    width: 30%;
    height: 100%;
  }
}
</style>
