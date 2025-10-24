<template>

  <div class="container">
    <div class="TopSetUp">
      <div class="title" style="top: 8%">区块链账号管理</div>
      <dv-border-box-8 :dur="8">
        <div class="Info">
          <div class="top">
            <div class="item">IP地址：192.168.31.88</div>
            <div class="item">组织名称：知行楼4-506</div>
            <div class="item">坐标：广东深圳</div>
          </div>
          <div class="bottom">
            <div class="item">共享威胁报告：<span style="color:#1072f1;font-size: 25px">4</span><span style="font-size: 16px"> &nbsp;(条)</span></div>
            <div class="item">共享恶意流量信息：<span style="color:#f13210;font-size: 25px">15</span><span style="font-size: 16px"> &nbsp;(条)</span></div>
            <div class="item"></div>
          </div>
          <div class="logout" style="">退出系统</div>
        </div>
      </dv-border-box-8>
    </div>
    <div class="BottomSetUp">
      <dv-border-box-12>
        <div class="title">组织信息共享</div>
        <div class="BBox">
          <div class="mini">
            <dv-border-box-6 :color="['#6d90b4', '#ffffff']">
              <SharedRule style=" box-sizing: border-box;padding:1%;width: 100%;" :limit="3"></SharedRule>
              <SharedFlow style=" box-sizing: border-box;padding:1%;width: 100%;"></SharedFlow>
            </dv-border-box-6>
          </div>
        </div>
      </dv-border-box-12>
    </div>
  </div>
</template>

<script>
import SharedRule from "@/pages/SetUp/component/SharedRule.vue";
import SharedFlow from "@/pages/SetUp/component/SharedFlow.vue";

export default {
  name: 'SetUp',
  components: {
    SharedRule,
    SharedFlow
  }
}
</script>

<style scoped>
.container {
  padding: 0 30px;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.title {
  color: #EEC101;
  position: absolute;
  top: 4%;
  left: 1.5%;
  font-size: 20px;
  font-weight: 700;
  letter-spacing: 2px;
}

.TopSetUp {
  position: relative;
  height: 20%;
}

.Info {
  padding: 3.5% 3% 1.5%;
  color: #cecece;
  font-size: 20px;
  position: relative;
  height: 100%;
  box-sizing: border-box;

  .top {
    display: flex;
    justify-content: space-between;
  }
  .bottom{
    margin-top: 2%;
    display: flex;
    justify-content: space-between;
  }
  .item {
    width: 33%;
  }
}
.logout:hover{
  color: #cecece;
}
.logout {
  position: absolute;
  bottom: 10%;
  right: 3%;
  border: 1px solid #2265bd;
  font-size: 12px;
  padding: 8px 12px;
  border-radius: 2px;
  background: rgba(20, 37, 91, 0.56);
  color: #2265bd;
}

.BottomSetUp {
  position: relative;
  margin-top: 1%;
  height: 65%;
}

.BBox {
  height: 100%;
  box-sizing: border-box;
  padding: 4% 3% 1.5%;

  .mini {

    width: 100%;
    height: 100%;
  }
}

/*
@keyframes borderGradient {
  0% {
    border-image-source: linear-gradient(45deg, #9292fd, #0599f8);
  }
  25% {
    border-image-source: linear-gradient(45deg, #0599f8, #61bfff);
  }
  50% {
    border-image-source: linear-gradient(45deg, #61bfff, #005f9d);
  }
  75% {
    border-image-source: linear-gradient(45deg, #005f9d, #9292fd);
  }
  100% {
    border-image-source: linear-gradient(45deg, #9292fd, #0599f8);
  }
}

.box {
  width: 80%;
  height: auto;
  display: flex;
  justify-content: center;
  align-items: center;
  background-size: cover;
  background-position: center;
  margin: 20px auto;
}

.frosted__glass {
  width: 100%;
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  backdrop-filter: blur(3px);
  border: 1.5px solid;
  border-image-slice: 1;
  animation: borderGradient 3s infinite;
  box-shadow: 0 0 20px 3px rgba(54, 54, 157, 0.5);
  border-radius: 10px;
  background: rgba(255, 255, 255, 0.1);
}

table {
  width: 100%;
  border-collapse: collapse;
  text-align: center;
  color: #fff;
}

th, td {
  padding: 10px;
  border: 1px solid rgba(255, 255, 255, 0.3);
}

th {
  background: rgba(54, 54, 157, 0.7);
}

tbody tr:nth-child(even) {
  background: rgba(255, 255, 255, 0.1);
}

tbody tr:nth-child(odd) {
  background: rgba(54, 54, 157, 0.1);
}
 */
</style>
