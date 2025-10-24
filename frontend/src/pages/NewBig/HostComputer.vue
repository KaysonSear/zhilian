<template>
    <div style="position:relative;">
      <div style="position:absolute;left:315px;top:15px;font-size: 14px;color: #6ba8ef;text-decoration: underline;z-index: 999" @click="toHost()">查看移动云主机></div>
        <Host></Host>
    </div>
</template>

<script>
import Host from "@/pages/Host/Host.vue";

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
  methods: {
    toHost(){
      this.$router.push('/computer');
    }
  },
  components: {Host},
  beforeDestroy() {
  }

}
</script>

<style scoped>
.container {
  height: 100vh;
}

.Box {
  width: 75%;
  height: 80%;
  margin: 2% auto;
}

.topTitle {
  position: absolute;
  color: #EEC101;
  font-size: 25px;
  font-weight: 700;
  top: 3%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.MiniBox {
  width: 95%;
  height: 18%;
  background-color: rgba(66, 99, 117, 0.42);
  box-sizing: border-box;
  margin: 0 auto;
  margin-bottom: 20px;
}

.title {
  color: #CECECE;
  padding: 10px;
}
.item{
  color: #CECECE;
  font-size: 16px;
  display: flex;
  justify-content: space-between;
  padding: 0 200px 0 50px;
  margin-bottom: 12px;
}
</style>
