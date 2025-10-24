<template>
  <div class="app-background">

    <dv-full-screen-container>
      <div class="background-overlay"></div>
      <div v-if="showHeader">
        <Header></Header>
      </div>
      <router-view></router-view>
    </dv-full-screen-container>
  </div>
</template>

<script>
import Header from './components/Header.vue';

export default {
  name: 'App',
  components: {
    Header
  },
  data() {
    return {
      // 根据路由判断是否显示Header
      showHeader: true
    };
  },
  watch: {
    // 监听路由变化，根据需要隐藏或显示Header
    '$route': {
      handler() {
        this.checkHeaderVisibility();
      },
      immediate: true
    }
  },
  methods: {
    checkHeaderVisibility() {
      // 根据当前路由路径判断是否显示Header
      this.showHeader = !(this.$route.path === '/Login' || this.$route.path === '/Load' || this.$route.path === '/Test' || this.$route.path === '/Collect');
    }
  }
};
</script>
<style scoped>
.app-background {
  background-image: url('/src/assets/BG/background.png');
  background-size: cover; /* 根据需要调整 */
  background-position: center;
  min-height: 100vh; /* 确保背景覆盖整个视口高度 */
  width: 100vw;
  position: relative; /* 确保内部定位正确 */
  overflow: hidden; /* 确保 ::before 伪元素不会超出容器 */

}

.background-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.05); /* 使用 RGBA 设置透明度，0.5 表示 50% 透明度 */
  z-index: 0; /* 设置较低的 z-index */
  pointer-events: none; /* 确保此覆盖层不影响鼠标事件 */
}

</style>
