<template>
  <div ref="chart" style="width: 100%; height: 280px;position: absolute;top: -70px"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "VirusPieChart",
  data() {
    return {
      chart: null,
    };
  },
  mounted() {
    this.initChart();
  },
  methods: {
    initChart() {
      // 获取DOM元素
      this.chart = echarts.init(this.$refs.chart);

      // 配置项
      const option = {

        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)',
        },

        series: [
          {
            name: '病毒类型',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [
              {value: 35, name: '暴力破解'},
              {value: 26, name: 'SQL注入'},
              {value: 17, name: 'DDoS攻击'},
              {value: 13, name: '异常登录'},
              {value: 9, name: '其他'},
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
            itemStyle: {
              color: function (params) {
                const colorList = ['#4f81bd', '#c0504d', '#9bbb59', '#f79646', '#8064a2'];
                return colorList[params.dataIndex];
              },
            },
          },
        ],
      };

      // 使用配置项和数据显示图表
      this.chart.setOption(option);

      // 监听窗口大小变化
      window.addEventListener("resize", this.chart.resize);
    },
  },
  beforeDestroy() {
    // 销毁前移除事件监听器并释放资源
    if (this.chart) {
      window.removeEventListener("resize", this.chart.resize);
      this.chart.dispose();
    }
  },
};
</script>

<style scoped>
/* 可以添加自定义样式 */
</style>
