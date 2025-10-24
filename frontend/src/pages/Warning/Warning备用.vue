<template>
  <div class="container">
    <div class="TopArea">
      <div class="left">
        <div class="topTitle"></div>
        <dv-border-box-13>
          <div class="topTitle" style="font-size: 18px; top: 10%;">安 全 威 胁 报 告</div>
          <div class="report">
            本报告基于对上传CSV文件:<span style="text-decoration: underline; font-style: italic;color:#46a5f5;">异常网络数据包</span>的分析。报告旨在提供对这些威胁的深入了解，以便采取相应的防护措施。
            <div class="Btn">
              <button @click="openModal">查看报告</button>
            </div>
          </div>
        </dv-border-box-13>
      </div>
      <div class="right">
        <div class="topTitle">威 胁 信 息 统 计</div>
        <dv-border-box-13>
          <ThreatInfo></ThreatInfo>
        </dv-border-box-13>
      </div>
    </div>
    <div class="BottomArea">
      <div class="bottom">
        <div class="topTitle" style="z-index: 999; top: 5%;">告 警 信 息 溯 源</div>
        <dv-border-box-2 :color="['#4F68B7', '#4F68B7']" dur="10"
                         style="padding: 10px; box-sizing: border-box; position:relative;">
          <WarningForm @OpenDetails="handleDetails" @handleInfo="openHandleModel"></WarningForm>
        </dv-border-box-2>
      </div>
    </div>

    <!-- 安全报告弹框部分 -->
    <div class="modal" v-if="showModal">
      <div class="modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <ReportText></ReportText>
      </div>
    </div>
    <!-- 溯源详情弹框部分-->
    <div class="InfoModal" v-if="infoModal">
      <div class="InfoModal-content">
        <span class="InfoClose" @click="closeModal">&times;</span>
        <div class="infoTitle">攻击流量详情</div>
        <div class="infoBox">
          <WarningDetails style="margin: 10px 20px;" :items="InfoItem"></WarningDetails>
        </div>
      </div>
    </div>
    <div class="modal" v-if="handleModal">
      <div class="handleModal-content">
        <span class="handleClose" @click="closeModal">&times;</span>
        <div style="display: flex;height: 100%;justify-content:center;align-items: center">
          <div class="LoadBtn" @click="handleEvent()">
            将IP加入黑名单
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ThreatInfo from "@/pages/Warning/component/ThreatInfo.vue";
import WarningForm from "@/pages/Warning/component/WarningForm.vue";
import ReportText from "@/pages/Warning/component/ReportText.vue";
import WarningDetails from "@/pages/Warning/component/WarningDetails.vue";
export default {
  components: {
    ThreatInfo,
    WarningForm,
    ReportText,
    WarningDetails
  },
  data() {
    return {
      showModal: false, // 控制弹框显示与隐藏的数据
      infoModal: false,
      handleModal: false,
      InfoItem:{},
    };
  },
  methods: {
    openHandleModel(item){
      this.InfoItem = item
      console.log(item)
      console.log("打开了处理框")
      this.handleModal = true;
    },
    handleDetails(item) {
      this.InfoItem = item
      this.infoModal = true;
    },
    openModal() {
      this.showModal = true; // 打开弹框
    },
    closeModal() {
      this.showModal = false; // 关闭弹框
      this.infoModal = false;
      this.handleModal = false;
    },
    handleEvent(){
      this.$message.success("操作成功")
      this.handleModal = false;
    }
  }
};
</script>

<style scoped>
.container {
  padding: 0 30px;
  height: 100vh;
}

.TopArea {
  display: flex;
  height: 30%;

  .left {
    flex: 1;
    height: 100%;
    margin-right: 1%;
    position: relative;
  }

  .right {
    flex: 3;
    height: 100%;
    position: relative;
  }
}

.BottomArea {
  margin-top: 1%;
  height: 55%;

  .bottom {
    height: 100%;
    position: relative;
  }
}

.topTitle {
  position: absolute;
  color: #EEC101;
  font-size: 20px;
  font-weight: 700;
  top: 9%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.report {
  width: 80%;
  height: 80%;
  padding-top: 15%;
  padding-bottom: 5%;
  letter-spacing: 3px;
  margin: 0 auto;
  color: #cecece;
  font-size: 15px;
  line-height: 25px;
  box-sizing: border-box;
}

.Btn {
  text-align: center;
  margin: 10%;

  button {
    padding: 10px 20px;
    border: 1px solid #298ee1;
    background-color: rgba(42, 58, 83, 0.73);
    color: #b7eaf5;
    border-radius: 5px;
    cursor: pointer;
    font-size: 15px;
  }
}
.handleModal-content{
  background-color: #00274c;
  border: 1px solid #008cff;
  color: #fff;
  width: 35%;
  height: 20%;
  position: relative;
  overflow: auto;
}
.handleClose{
  position: absolute;
  top: 0px;
  right: 10px;
  color: #fff;
  font-size: 30px;
  cursor: pointer;
}
.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  z-index: 1000;
}

.modal-content {
  background-color: #00274c;

  border: 2px solid #00bfff;
  color: #fff;
  width: 45%;
  height: 75%;
  position: relative;
  overflow: auto;
}

.close {
  position: fixed;
  top: 25px;
  right: 50px;
  color: #fff;
  font-size: 50px;
  cursor: pointer;
}

.InfoModal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  z-index: 1000;
}

.InfoModal-content {
  border: 0px solid transparent; /* Initial transparent border */
  background-color: #1f3449;
  color: #fff;
  width: 45%;
  height: 75%;
  position: relative;

}

/* Apply the animation to the border */
.InfoModal-content::before {
  content: ''; /* Required for pseudo-element */
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  border: 1px solid transparent; /* Initial transparent border */
  border-image-slice: 1; /* Ensure the border slices properly */
  animation: borderGradient 5s linear infinite; /* Animation settings */
}

.infoTitle {
  background: linear-gradient(to right, #0015ff, #00d0ff); /* 红蓝渐变背景 */
  -webkit-background-clip: text; /* 使用背景色填充文字 */
  -webkit-text-fill-color: transparent; /* 隐藏实际文字颜色 */
  text-align: center;
  padding: 10px;
  font-size: 25px;
  letter-spacing: 5px;
  font-weight: bold;
}
.infoBox {
  height: 90%;
  position: relative;
  overflow: auto;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE/Edge */
}

.infoBox::-webkit-scrollbar {
  display: none; /* Chrome/Safari/Opera */
}


.InfoClose {
  position: absolute;
  top: 0px;
  right: 10px;
  color: #fff;
  font-size: 30px;
  cursor: pointer;
}

@keyframes borderGradient {
  0% {
    border-image-source: linear-gradient(45deg, #0046f6, #e50808);
  }
  20% {
    border-image-source: linear-gradient(45deg, #e50808, #0046f6);
  }
  40% {
    border-image-source: linear-gradient(45deg, #0046f6, #e50808);
  }
  60% {
    border-image-source: linear-gradient(45deg, #e50808, #0046f6);
  }
  80% {
    border-image-source: linear-gradient(45deg, #0046f6, #e50808);
  }
  100% {
    border-image-source: linear-gradient(45deg, #e50808, #0046f6);
  }
}
.LoadBtn {
  width: 22%;
  height: 20px;
  padding: 1%;
  font-size: 15px;
  text-align: center;
  border: 1px solid #298ee1;
  background-color: rgba(42, 58, 83, 0.73);
  border-radius: 2px;
  cursor: pointer;
  /* 添加鼠标指针 */
  color: #b7eaf5;
}
/*
.close {
  position: absolute;
  top: 0px;
  right: 10px;
  color: #fff;
  font-size: 30px;
  cursor: pointer;
}
 */
</style>
