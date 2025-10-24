<template>
  <div>
    <h1 class="title" style="position: absolute;top: 30%;color: #cccccc;width: 100%;text-align: center">选择您要查看的主机信息</h1>
    <div class="Btn">
      <!--              <button @click="openModal">查看报告</button>-->
      <button @click=" toPage">移动云主机监控</button>
      <button @click=" toPage1">网关服务器监控</button>
    </div>
  </div>
</template>

<script>
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
    toPage(){
      this.$router.push('/computer');
    },
    toPage1(){
      this.$router.push('/HostComputer');
    }
  },
  components: {},
  beforeDestroy() {
  }

}
</script>

<style  scoped>
.Btn {
  text-align: center;
  margin: 20% 25%;
  display: flex;
  justify-content: space-between;
  button {
    width: 30%;
    font-size: 20px;
    padding: 10px 20px;
    border: 1px solid #298ee1;
    background-color: rgba(42, 58, 83, 0.73);
    color: #b7eaf5;
    border-radius: 2px;
    cursor: pointer;
  }
}
</style>
