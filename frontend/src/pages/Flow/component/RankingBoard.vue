<template>
  <div class="Box">
    <div v-if="dataLoaded" style="width:80%;height: 80%;margin: 10% auto">
      <dv-scroll-ranking-board :config="config"  />
    </div>
    <div v-else>
      数据加载中...
    </div>
  </div>
</template>

<script>
import { getIPCount } from '../../../API/index'

export default {
  props: [],
  data() {
    return {
      dataLoaded: false,
      config: {
        data: []
      }
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      getIPCount().then(res => {
        const firstTenObjects = res.data.slice(0, 10);
        this.config.data = firstTenObjects.map(item => ({
          name: item.ip,
          value: item.count
        }));
        this.dataLoaded = true; // 数据加载完成，设置 dataLoaded 为 true
      }).catch(error => {
        console.error('Failed to fetch data:', error);
        this.dataLoaded = true;
      })
    }
  }
}
</script>

<style scoped>
.Box {
  height: 100%;
  display: flex;
}
</style>
