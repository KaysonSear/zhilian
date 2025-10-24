<template>
  <div class="traffic-flow-chart">
    <div ref="chart" class="chart-container"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import store from '@/store/index.js';
import { mapState } from 'vuex';
export default {
  name: 'TrafficFlowChart',
  data() {
    return {

      trafficData: [
        {
          time: ['2024-06-01', '2024-06-02', '2024-06-03', '2024-06-04', '2024-06-05', '2024-06-06', '2024-06-07'],
          inFlow: [120, 132, 101, 134, 90, 230, 210],
          outFlow: [220, 182, 191, 234, 290, 330, 310],
        },
        {
          time: ['2024-06-01', '2024-06-02', '2024-06-03', '2024-06-04', '2024-06-05', '2024-06-06', '2024-06-07'],
          inFlow: [788, 784, 754, 834, 750, 430, 610],
          outFlow: [820, 782, 891, 934, 790, 730, 510],
        },
        {
          time: ['2024-06-01', '2024-06-02', '2024-06-03', '2024-06-04', '2024-06-05', '2024-06-06', '2024-06-07'],
          inFlow: [640, 832, 501, 434, 890, 630, 510],
          outFlow: [520, 582, 591, 534, 590, 430, 410],
        }
      ],
    };
  },
  computed: {
    ...mapState({
      hostNum: state => state.HostNum
    })
  },
  mounted() {
    this.initChart(0);

    store.watch(
        state => state.HostNum,
        (newValue, oldValue) => {
          this.initChart(newValue-1)
        }
    );
  },
  watch: {

  },
  methods: {
    initChart(num) {
      const chart = echarts.init(this.$refs.chart);
      const option = {
        title: {
          text: '',
          left: 'center',
          textStyle: {
            color: '#fff',
          },
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985',
            },
          },
        },
        legend: {
          bottom: '0%',
          left: 'center',
          textStyle: {
            color: '#fff',
          },
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '8%',
          containLabel: true,
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: this.trafficData[num].time,
            axisLabel: {
              color: '#fff',
            },
          },
        ],
        yAxis: [
          {
            type: 'value',
            axisLabel: {
              color: '#fff',
            },
          },
        ],
        series: [
          {
            name: '疑似异常流量',
            type: 'line',
            stack: '总量',
            areaStyle: {},
            data: this.trafficData[num].inFlow,
            color: '#f8757f',
          },
          {
            name: '正常流量',
            type: 'line',
            stack: '总量',
            areaStyle: {},
            data: this.trafficData[num].outFlow,
            color: '#1f78b4',
          },
        ],
      };
      chart.setOption(option);
    },
  },
};
</script>

<style scoped>
.traffic-flow-chart {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.chart-container {
  width: 80%;
  height: 80%;
}
</style>
