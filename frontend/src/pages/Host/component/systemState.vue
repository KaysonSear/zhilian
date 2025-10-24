<template>
  <div class="systemBox">
    <div v-for="(state, index) in systemStateFade[num]" :key="index" class="Box">
      <div class="text">
        {{ state.name }}
      </div>
      <div class="bar">
        <div class="num" :style="getStyle(state.num)">
        </div>
      </div>
      <div class="percentage">{{ state.num }}</div>
    </div>
  </div>
</template>

<script>
import {getDisk, getMemory, getCpu} from '../../../API/index'
import {mapState} from "vuex";
import store from "@/store";

export default {
  props: [],
  watch: {},
  data() {
    return {
      num:0,
      systemState:[
        {name: 'CPU使用率', num: ''},
        {name: '磁盘使用率', num: ''},
        {name: '内存占用率', num: ''},
      ],
      systemStateFade: [
        [
          {name: 'CPU使用率', num: '16%'},
          {name: '磁盘使用率', num: '41%'},
          {name: '内存占用率', num: '85%'},
        ],
        [
          {name: 'CPU使用率', num: '6%'},
          {name: '磁盘使用率', num: '45%'},
          {name: '内存占用率', num: '32%'},
        ],
        [
          {name: 'CPU使用率', num: '55%'},
          {name: '磁盘使用率', num: '78%'},
          {name: '内存占用率', num: '15%'},
        ]
      ]
    }
  },
  created() {
  },
  computed: {
    ...mapState({
      hostNum: state => state.HostNum
    })
  },
  mounted() {
    // this.getData()
    store.watch(
        state => state.HostNum,
        (newValue, oldValue) => {
          this.num = newValue-1
        }
    );
  },
  methods: {
    getStyle(num) {
      let percentage = parseInt(num);
      let color = '';
      if (percentage < 33) {
        color = '#20f13c';
      } else if (percentage > 66) {
        color = '#ff2626';
      } else {
        color = '#f19920';
      }
      return {
        width: num,
        backgroundColor: color
      }
    },
    getData() {
      getCpu().then(res => {
        this.systemState.map((item, i) => {
          if (i === 0) {
            if (res.data.percent === 0) {
              item.num = 5 + '%'
            } else {
              item.num = res.data.percent + '%'
            }
          }
        });
      })
      getDisk().then(res => {
        this.systemState.map((item, i) => {
          if (i === 1) {
            item.num = res.data.percent + '%'
          }
        });
      })
      getMemory().then(res => {
        this.systemState.map((item, i) => {
          if (i === 2) {
            if (res.data.percent > 100) {
              item.num = 100 + '%'
            } else {
              item.num = res.data.percent + '%'
            }
          }
        });
      })
    }
  },
  components: {},
  beforeDestroy() {
  }

}
</script>

<style scoped>
.systemBox {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  padding: 0 5%;
  box-sizing: border-box;
}

.Box {
  display: flex;
  width: 100%;
  height: 25%;

  .text {
    color: #cecece;
    flex: 1;
    text-align: center;
    margin: auto 5% auto 0;
    font-size: 2%;
  }

  .bar {
    flex: 3.5;
    background-color: #333848;
    position: relative;
    height: 10%;
    margin: auto 0;
  }

  .num {
    position: absolute;
    background-color: #ff0000;
    width: 30%;
    height: 100%;
  }

  .percentage {
    color: #cecece;
    margin: auto 0 auto 2%;
    font-size: 2%;
  }
}

</style>
