<template>
  <div class="header">
    <img src="../assets/Head/head_bg.png" alt="" style="width: 100%; height: 100%;">
    <div class="Title">
      网络安全分析溯源系统
    </div>
    <div v-for="(route, index) in routes" :key="index" :class="route.class" @click="navigateTo(route.path)">
      <dv-border-box-7 :color="[isActive(route.path) ? activeColor[0] : defaultColor[0], isActive(route.path) ? activeColor[1] : defaultColor[1]]" >
        <div class="text">{{ route.name }}</div>
      </dv-border-box-7>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Header',
  data() {
    return {
      defaultColor: ['#444444', '#5E5E5E'],
      activeColor: ['#3666ce', '#a4a4a4'],
      router:[
        { path: '/datas', name: '告 警 通 知', class: 'routerBox left2' },
        { path: '/computer', name: '主 机 管 理', class: 'routerBox left3' },
        { path: '/chat', name: '分 析 溯 源', class: 'routerBox right2' },
        { path: '/SetUp', name: '设 置 管 理', class: 'routerBox right3' }
      ],
      routes: [
        { path: '/Index', name: '综 合 大 屏', class: 'routerBox left1' },
        { path: '/Warning', name: '告 警 通 知', class: 'routerBox left2' },
        { path: '/Flow', name: '流 量 分 析', class: 'routerBox left3' },
        { path: '/chat', name: '分 析 溯 源', class: 'routerBox right1' },
        { path: '/controlled', name: '控 制 中 心', class: 'routerBox right2' },
        { path: '/SetUp', name: '共 享 管 理', class: 'routerBox right3' }
      ]
    }
  },
  methods: {
    navigateTo(route) {
      if (this.$route.path !== route) {
        this.$router.push(route).catch(err => {
          if (err.name !== 'NavigationDuplicated') {
            throw err;
          }
        });
      }
    },
    isActive(route) {
      if (route === '/controlled' && (this.$route.path === '/computer' || this.$route.path === '/HostComputer'|| this.$route.path === '/datas'|| this.$route.path === '/controlled')) {
        return true;
      }
      if (route === '/chat' && (this.$route.path === '/chat' || this.$route.path === '/MyChat')) {
        return true;
      }
      return this.$route.path === route;
    }
  }
};
</script>
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Orbitron:wght@400;700&display=swap');

.header {
  width: 100%;
  height: 90px;
  z-index: 1; /* 确保 header 在 overlay 之上 */
  position: relative; /* 确保 z-index 生效 */
}

.Title {
  position: absolute;
  color: #00bfff; /* 设置文字颜色为蓝色 */
  font-weight: 700;
  top: 45%; /* 调整位置使其居中 */
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 40px;
  font-family: 'Orbitron', sans-serif;
  text-shadow: 0 0 10px rgba(0, 191, 255, 0.7), 0 0 20px rgba(0, 191, 255, 0.5), 0 0 30px rgba(0, 191, 255, 0.3);
  letter-spacing: 15px; /* 增加字间距，提升科技感 */
  animation: glow 1.5s ease-in-out infinite alternate;
}

@keyframes glow {
  from {
    text-shadow: 0 0 10px rgba(0, 191, 255, 0.7), 0 0 20px rgba(0, 191, 255, 0.5), 0 0 30px rgba(0, 191, 255, 0.3);
  }
  to {
    text-shadow: 0 0 20px rgba(0, 191, 255, 0.9), 0 0 30px rgba(0, 191, 255, 0.7), 0 0 40px rgba(0, 191, 255, 0.5);
  }
}

.routerBox {
  position: absolute;
  width: 125px;
  height: 35px;
  background-color: #1a1a1a;
  top: 16%;
  cursor: pointer;
}

.text {
  font-size: 20px;
  line-height: 35px;
  font-weight: bold;
  color: #bbbdc4;
  text-align: center; /* 文字居中对齐 */
  white-space: nowrap; /* 防止文本换行 */
}

.left1 {
  left: 19.5%;
}

.left2 {
  left: 10.5%;
}

.left3 {
  left: 1.5%;
}

.right1 {
  right: 19.5%;
}

.right2 {
  right: 10.5%;
}

.right3 {
  right: 1.5%;
}


</style>
