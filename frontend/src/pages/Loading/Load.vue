<template>
  <div class="container">
    <transition name="slide">
      <div v-if="!showNextBox" class="transition-box" :style="{ opacity: currentOpacity, transform: currentTransform }">
        <dv-border-box-12>
          <div class="content">
            <div class="Top">
              <span>数据采集:</span>
              <input type="file" id="uploadFile" name="uploadFile" @change="handleFileUpload">
            </div>
            <div class="Middle">
              <span>分析模式选择:</span>
              <span style="margin-left: 10px;color: #48a4ff">{{ activeTab }}</span>
              <span style="margin-left: 10px;font-size: 13px;color: #8b939b">/*不同模式识别网络流量信息存在差异</span>
            </div>
            <div class="Bottom">
              <div class="tab">
                <button v-for="(tab, index) in tabs"
                        :key="index"
                        :class="{ 'tablinks': true, 'active': activeTab === tab.id }"
                        @click="openTab(tab.id)">
                  {{ tab.name }}
                </button>
              </div>
              <div v-for="(tab, index) in tabs"
                   :key="index"
                   :id="tab.id"
                   class="tabcontent"
                   :class="{ 'active': activeTab === tab.id }">
                <h3 style="margin-bottom: 5px; padding: 0">{{ tab.title }} </h3>
                <p style="color: #9d9d9d;font-size: 15px">{{ tab.content }}</p>
              </div>
            </div>
          </div>
        </dv-border-box-12>
      </div>
    </transition>

    <transition name="slide">
      <div v-if="showNextBox" class="transition-secondBox " :style="{ opacity: nextOpacity, transform: nextTransform }">
        <dv-border-box-12>
          <div class="radar-scan">
            <div class="tangjialing"></div>
            <div style="color:#cecece;font-size: 18px;margin-top: 15px">{{ tip }}</div>
          </div>
        </dv-border-box-12>
      </div>
    </transition>
    <div class="Btn">
      <button @click="nextStep">{{ BtnText }}</button>
    </div>
  </div>
</template>

<script>
import {Test, uploadFile} from '@/API'

export default {
  name: 'TransitionEffect',
  data() {
    return {
      file:null,
      showNextBox: false, // 控制是否显示下一个方框
      currentText: '当前方框内容',
      nextText: '下一个方框内容',
      currentOpacity: 1, // 当前方框的透明度
      nextOpacity: 0, // 下一个方框的透明度
      currentTransform: 'translate(-50%, -50%)',
      nextTransform: 'translate(calc(-50% + 100vw), -50%)',
      BtnText: '下一步',
      tip: '报告生成中',
      activeTab: 'AI模型', // 默认选中的标签页
      tabs: [ // 定义选项卡内容
        {id: 'AI模型', name: 'AI模型', content: 'TransEC-GAN技术通过深度学习算法，能够从复杂的网络流量数据中提取关键特征，模型根据提取的特征，能够有效地识别出多种网络威胁（如PortScan、DDoS攻击等）。同时TransEC-GAN具备自适应学习能力，能够随着网络环境的变化和新出现的攻击模式不断优化其识别算法。',title:"TransEC-GAN模型"},
        {id: '规则匹配', name: '规则匹配', content: '这里是规则匹配的详细描述和内容。'},
      ]
    };
  },
  mounted() {
    this.activeTab = this.tabs[0].id;
  },
  methods: {
    handleFileUpload(event) {
      // 获取用户选择的文件
      this.file = event.target.files[0];
      console.log(this.file)
    },
    submitFile() {
      let num = 1 //AI
      if(this.activeTab === 'AI模型'){
        num = 1
      }else{
        num = 0
      }
      console.log(num)
      // 调用上传文件的接口函数
      uploadFile(this.file,num)
          .then(response => {
            // 处理上传成功的逻辑
            console.log('文件上传成功', response);
          })
          .catch(error => {
            // 处理上传失败的逻辑
            console.error('文件上传失败', error);
          });
      // const user = {
      //   username:'123',
      //   password:'1451'
      // }
      // Test().then(res=>{
      //   console.log(res)
      // })
    },
    openTab(tabId) {
      this.activeTab = tabId;
    },
    nextStep() {
      // 开始向左移动当前方框和淡出
      if (this.BtnText === '分析中') {
        return
      } else if (this.BtnText === '查看结果') {
        this.$router.push({name: 'Index'});
        console.log("展示")
        return;
      }
      this.submitFile()
      this.currentOpacity = 0;
      this.currentTransform = 'translate(calc(-50% - 100vw), -50%)';
      // 同时显示下一个方框，并淡入和向左移动
      setTimeout(() => {
        this.showNextBox = true;
        this.nextOpacity = 1;
        this.nextTransform = 'translate(-50%, -50%)';
        this.BtnText = '分析中'
      }, 500); // 等待0.5秒（动画时间）后切换到下一个方框

      setTimeout(() => {
        this.tip = '生成完毕'
        this.BtnText = '查看结果'
      }, 3000); // 等待0.5秒（动画时间）后切换到下一个方框
    }
  }
};
</script>

<style scoped>
/* 标签按钮的样式 */
.tab {
  overflow: hidden;
  background-color: #000;
  text-align: left;
}

.tab button {
  background-color: #444;
  color: #fff;
  cursor: pointer;
  padding: 10px 20px;
  font-size: 16px;
  transition: 0.3s;
  border: 1px solid transparent;
  margin: 20px 0.5px 0 0.5px;
}

.tab button:hover {
  background-color: #666;
}

.tab button.active {

  border: 1px solid transparent; /* Initial transparent border */
  border-image-slice: 1; /* Ensure the border slices properly */
  animation: borderGradient 5s linear infinite; /* Animation settings */
  background-color: rgba(42, 58, 83, 0.73);
  color: #b7eaf5;
}

/* 内容区域的样式 */
.tabcontent {
  display: none;
  padding: 20px;
  background-color: #111;
  color: #fff;
  border-top: 1px solid transparent; /* Initial transparent border */
  border-image-slice: 1; /* Ensure the border slices properly */
  animation: borderGradient 5s linear infinite; /* Animation settings */
}

@keyframes borderGradient {
  0% {
    border-image-source: linear-gradient(45deg, #007df6, #cc4c4c);
  }
  50% {
    border-image-source: linear-gradient(45deg, #cc4c4c, #007df6);
  }
  100% {
    border-image-source: linear-gradient(45deg, #007df6, #cc4c4c);
  }
}

.tabcontent.active {
  display: block;
}

.content {
  padding: 25px;
  font-size: 18px;
  color: #cecece;
  letter-spacing: 2px;
}

#uploadFile {
  margin-left: 10px;
}

.Middle {
  margin: 20px 0;
}

.container {
  position: relative;
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.transition-box {
  width: 850px;
  height: 650px;
  color: #fff;
  background-color: rgba(33, 29, 29, 0.64);
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  left: 50%;
  top: 45%;
  transition: opacity 1s ease-in-out, transform 1s ease-in-out;
}

.transition-secondBox {
  width: 600px;
  height: 450px;
  color: #fff;
  background-color: rgba(33, 29, 29, 0.64);
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  left: 50%;
  top: 45%;
  transition: opacity 1s ease-in-out, transform 1s ease-in-out;
}

button {
  margin-top: 20px;
  padding: 10px 20px;
  font-size: 16px;
  background-color: #007bff;
  color: #fff;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056b3;
}

/* Vue 过渡动画 */
.slide-enter-active, .slide-leave-active {
  transition: opacity 1s ease-in-out, transform 1s ease-in-out;
}

.slide-enter, .slide-leave-to {
  opacity: 0;
}

.radar-scan {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 5%;
}

.tangjialing {
  width: 40vmin;
  height: 40vmin;
  max-width: 50vh;
  max-height: 50vh;
  position: relative;
  border-radius: 50%;
  border: 0.2rem solid #0c3ddd;
  overflow: hidden;
  background: /* Blue radial gradient */ radial-gradient(circle at center, rgba(0, 128, 255, 0.3) 0%, rgba(0, 0, 255, 0) 75%),
    /* Repeating blue radial gradient */ repeating-radial-gradient(circle at center, rgba(0, 0, 255, 0) 5.8%, rgba(0, 0, 255, 0) 18%, #0000ff 18.6%, rgba(32, 255, 77, 0) 18.9%),
    /* Green and blue linear gradient */ linear-gradient(90deg, rgba(32, 255, 77, 0) 49.5%, #0000ff 50%, #0000ff 50%, rgba(32, 255, 77, 0) 50.2%),
  linear-gradient(0deg, rgba(0, 0, 255, 0) 49.5%, #0000ff 50%, #0000ff 50%, rgba(32, 255, 77, 0) 50.2%);
}

.tangjialing:before {
  content: ' ';
  display: block;
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  animation: bei 5s infinite;
  animation-timing-function: linear;
  animation-delay: 1.4s;
}

.tangjialing:after {
  content: ' ';
  display: block;
  background-image: linear-gradient(44deg, rgba(0, 255, 51, 0) 50%, #1866dc 100%);
  width: 50%;
  height: 50%;
  position: absolute;
  top: 0;
  left: 0;
  animation: zhuan 5s infinite;
  animation-timing-function: linear;
  transform-origin: bottom right;
  border-radius: 100% 0 0 0;
}

@keyframes zhuan {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

@keyframes bei {
  14% {
    background: radial-gradient(2vmin circle at 75% 70%, #ffffff 10%, #ff2027 30%, rgba(255, 255, 255, 0) 100%);
  }
  14.0002% {
    background: radial-gradient(2vmin circle at 75% 70%, #ffffff 10%, #ff2027 30%, rgba(255, 255, 255, 0) 100%), radial-gradient(2vmin circle at 63% 72%, #ffffff 10%, #ff2027 30%, rgba(255, 255, 255, 0) 100%);
  }
  25% {
    background: radial-gradient(2vmin circle at 75% 70%, #ffffff 10%, #ff2027 30%, rgba(255, 255, 255, 0) 100%), radial-gradient(2vmin circle at 63% 72%, #ffffff 10%, #ff2027 30%, rgba(255, 255, 255, 0) 100%), radial-gradient(2vmin circle at 56% 86%, #ffffff 10%, #ff2027 30%, rgba(255, 255, 255, 0) 100%);
  }
  26% {
    background: radial-gradient(2vmin circle at 75% 70%, #ffffff 10%, #ff2027 30%, rgba(255, 255, 255, 0) 100%), radial-gradient(2vmin circle at 63% 72%, #ffffff 10%, #ff2027 30%, rgba(255, 255, 255, 0) 100%), radial-gradient(2vmin circle at 56% 86%, #ffffff 10%, #ff2027 30%, rgba(255, 255, 255, 0) 100%);
    opacity: 1;
  }
  100% {
    background: radial-gradient(2vmin circle at 75% 70%, #ffffff 10%, #ff2027 30%, rgba(255, 255, 255, 0) 100%), radial-gradient(2vmin circle at 63% 72%, #ffffff 10%, #ff2027 30%, rgba(255, 255, 255, 0) 100%), radial-gradient(2vmin circle at 56% 86%, #ffffff 10%, #ff2027 30%, rgba(255, 255, 255, 0) 100%);
    opacity: 0;
  }
}

.Btn {
  text-align: center;
  margin-top: 40%;

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
</style>
