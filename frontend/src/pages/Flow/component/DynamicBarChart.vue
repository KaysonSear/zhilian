<template>
  <div ref="chart" style="width: 100%; height: 80%;"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'DynamicBarChart',
  data() {
    return {
      ipData: [] // 这里存放IP访问次数的数据
    };
  },
  mounted() {
    this.fetchData();
    this.initChart();
    setInterval(this.updateChart, 3000); // 每3秒更新一次图表数据
  },
  methods: {
    fetchData() {
      // 模拟获取数据，可以替换为实际的API调用
      this.ipData = [
        { ip: '30.41.21.255', count: 336 },
        { ip: '36.177.57.11', count: 293 },
        { ip: '0.76.242.255', count: 262 },
        { ip: '5.79.132.197', count: 202 },
        { ip: '180.141.210', count: 198 }
      ];
    },
    initChart() {
      this.chart = echarts.init(this.$refs.chart);

      const option = {
        title: {
          text: '',
          textStyle: {
            color: '#fff'
          }
        },
        grid: {
          top: '20%',
          left: '1%',  // 增加左边距
          right: '10%', // 增加右边距
          bottom: '2%',
          containLabel: true
        },
        xAxis: {
          max: 'dataMax',
          axisLabel: {
            color: '#fff',
            fontSize: 12 // 设置X轴标签的字体大小
          }
        },
        yAxis: {
          type: 'category',
          data: this.ipData.map(item => item.ip),
          inverse: true,
          animationDuration: 300,
          animationDurationUpdate: 300,
          max: 5, // 展示所有IP
          axisLabel: {
            color: '#fff',
            fontSize: 12, // 设置Y轴标签的字体大小
            rotate: 30 // 旋转Y轴标签，使其斜着显示
          }
        },
        series: [
          {
            realtimeSort: true,
            name: '访问次数',
            type: 'bar',
            data: this.ipData.map(item => item.count),
            barWidth: '20%', // 设置柱形的宽度
            label: {
              show: true,
              position: 'right',
              valueAnimation: true,
              color: '#fff',
              fontSize: 14 // 设置柱形标签的字体大小
            },
            itemStyle: {
              color: params => {
                // 设置不同柱子的颜色
                const colorList = ['#00c1de', '#007aff', '#1e3cff', '#3e2ec8', '#ff4d4d'];
                return colorList[params.dataIndex];
              }
            }
          }
        ],
        legend: {
          show: false,
          textStyle: {
            color: '#fff',
            fontSize: 12 // 设置图例的字体大小
          }
        },
        animationDuration: 0,
        animationDurationUpdate: 3000,
        animationEasing: 'linear',
        animationEasingUpdate: 'linear'
      };

      this.chart.setOption(option);
    },
    updateChart() {
      // 模拟数据更新，可以替换为实际的API调用
      this.ipData.forEach(item => {
        if (Math.random() > 0.9) {
          item.count += Math.round(Math.random() * 2000);
        } else {
          item.count += Math.round(Math.random() * 200);
        }
      });

      // 根据访问次数对数据进行排序
      this.ipData.sort((a, b) => b.count - a.count);

      // 更新图表数据
      this.chart.setOption({
        yAxis: {
          data: this.ipData.map(item => item.ip)
        },
        series: [
          {
            data: this.ipData.map(item => item.count)
          }
        ]
      });
    }
  }
};
</script>

<style scoped>
body {
  background-color: #000;
}
</style>
