<template>
  <div class="gaugeChart" ref="chart"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "GaugeChart",
  mounted() {
    this.initChart();
  },
  methods: {
    initChart() {
      const chart = echarts.init(this.$refs.chart);
      const option = {
        tooltip: {
          formatter: '{a} <br/>{b} : {c}分'
        },
        series: [
          {
            name: '安全评分',
            type: 'gauge',
            detail: {
              formatter: '{value}分',
              textStyle: {
                color: '#00ccff', // 仪表盘详情字体颜色（科技风格）
                fontSize: 20
              }
            },
            data: [{ value: this.getRandomScore(), name: '安全评分' }],
            axisLine: {
              lineStyle: {
                color: [
                  [0.3, '#2c4fee'],
                  [0.7, '#5d93f8'],
                  [1, '#95bae5']
                ],
                width: 20 // 仪表盘线条宽度
              }
            },
            pointer: {
              width: 5, // 指针宽度
              color: '#00ccff' // 指针颜色（科技风格）
            },
            splitLine: {
              length: 20,
              lineStyle: {
                color: '#cecece' // 刻度线颜色
              }
            },
            axisLabel: {
              color: '#cecece' // 刻度标签颜色
            },
            title: {
              color: '#cecece' // 仪表盘标题颜色
            },
            center: ['50%', '60%'], // 设置图表的中心点
            radius: '80%' // 设置图表的半径
          }
        ]
      };
      chart.setOption(option);
    },
    getRandomScore() {
      // 模拟生成一个随机的安全分数
      return Math.floor(Math.random() * 100);
    }
  }
};
</script>

<style scoped>
.gaugeChart {
  width: 100%;
  height: 100%; /* 适当调整图表高度 */
}
</style>
