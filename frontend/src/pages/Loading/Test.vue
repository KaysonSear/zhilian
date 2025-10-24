<template>
  <div id="app">
    <div class="loading-container" v-if="loading">
      <div class="spinner"></div>
      <div class="loading-text">加载中....</div>
    </div>
    <div v-else>
      <h1>加载完成！</h1>
    </div>
  </div>
</template>

<style>
#app {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.loading-container {
  text-align: center;
}

.spinner {
  margin: 0 auto;
  width: 50px;
  height: 50px;
  border: 5px solid #f3f3f3; /* 外圈颜色 */
  border-top: 5px solid #3b82f6; /* 科技蓝色 */
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.loading-text {
  margin-top: 10px;
  font-size: 16px;
  color: #3b82f6; /* 科技蓝色 */
  font-weight: bold;
}
</style>

<script>
export default {
  data() {
    return {
      loading: true // 控制加载动画显示状态
    };
  },
  mounted() {
    // 模拟加载完成后隐藏动画
    setTimeout(() => {
      this.loading = false;
    }, 10000); // 3秒后隐藏
  }
};
</script>
