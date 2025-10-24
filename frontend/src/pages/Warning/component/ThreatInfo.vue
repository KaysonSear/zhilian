<template>
  <div class="ThreatBox">
    <div class="card">
      <div class="Box front" v-for="(i,item) in AttackData">
        <div class="title">{{ i.name }}</div>
        <div class="img">
          <img :src="require(`@/assets/Attack/${i.img}`)" :alt="`${item.name} Attack`">
        </div>
        <div class="content">{{ i.count }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import {getLabelCounts} from "@/API";

export default {
  props: [],
  watch: {},
  data() {
    return {
      AttackData: [
        {name: 'DDoS', img: 'DDoS.png', count: '42'},
        {name: 'PortScan', img: 'PortScan.png', count: '54'},
        {name: 'SSH-Patator', img: 'SSH-Patator.png', count: '0'},
        {name: 'Web-Attack', img: 'DoS.png', count: '0'},
      ]
    }
  },
  created() {
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      getLabelCounts().then(res => {
        this.AttackData.forEach((item, i) => {
          if(res.data[i]===undefined){
            return
          }
          item.name = res.data[i].label
          item.count = res.data[i].count
        });
      })
    },
  },
  components: {},
  beforeDestroy() {
  }
}
</script>

<style scoped>
.ThreatBox {
  height: 100%;
  padding: 0 3%;
}
.card{
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}
.Box {
  height: 65%;
  width: 22%;
  margin-top: 3%;
  border: 1.5px solid transparent; /* Initial transparent border */
  background: radial-gradient(
      circle,
      rgba(27, 101, 201, 0.31) 0%, /* Outer color */ rgba(15, 28, 54, 0.51) 100% /* Inner color */
  );
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative; /* Ensure relative positioning for absolute border */

  .title {
    position: absolute;
    top: 5px;
    color: #167efc; /* 深蓝色 */
    text-shadow: 0px 1px 3px rgba(0, 0, 0, 0.4); /* 添加细微阴影增强立体感 */
    font-weight: bold;
    font-size: 25px;
    letter-spacing: 2px; /* 增加字间距 */
  }

  .img {
    img {
      position: absolute;
      left: 5%;
      bottom: 10%;
      height: 55%;
      width: 40%;
      opacity: 0.3; /* 设置透明度为70% */
    }
  }

  .content {
    font-size: 60px;
    color: rgba(216, 234, 255, 0.77);
    padding-left: 40%;
    padding-top: 25%;
  }
}

/* Define keyframes for border animation */
@keyframes borderGradient {
  0% {
    border-image-source: linear-gradient(45deg, #007df6, #ab5a5a);
  }
  50% {
    border-image-source: linear-gradient(45deg, #ab5a5a, #007df6);
  }
  100% {
    border-image-source: linear-gradient(45deg, #007df6, #ab5a5a);
  }
}


/* Apply the animation to the border */
.Box::before {
  content: ''; /* Required for pseudo-element */
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  border: 1.5px solid transparent; /* Initial transparent border */
  border-image-slice: 1; /* Ensure the border slices properly */
  animation: borderGradient 5s linear infinite; /* Animation settings */
}


</style>
