<template>
  <div class="lineChart" ref="chart"></div>
</template>

<script>
import * as echarts from "echarts";
import {fetchDataByDayGroups} from "@/API";

export default {
  name: "LineChart",
  data() {
    return {
      alertData: [
        1, 4, 5, 7, 5, 5, 4, 8, 5, 3,
        9, 6, 4, 6, 5, 16, 3, 3, 4, 4,
        1, 7, 3, 4
      ],
      timePeriods:[
        "08:00", "09:00", "10:00", "11:00",
        "12:00", "13:00", "14:00", "15:00",
        "16:00", "17:00", "18:00", "19:00",
        "20:00", "21:00", "22:00", "23:00",
        "00:00", "01:00", "02:00", "03:00",
        "04:00", "05:00", "06:00", "07:00"
      ]
    };
  },
  mounted() {
    // this.getTimeData()
    this.initChart();

  },
  methods: {
    getTimeData(){
      fetchDataByDayGroups().then(res => {
        this.timePeriods = res.data.map(item => {
          let timePart = item.timePeriod.split(' ')[1];
          return timePart; // 返回时间部分字符串
        });
        this.alertData = res.data.map(item => item.count);
        this.initChart();
      });

    },
    initChart() {
      this.chart = echarts.init(this.$refs.chart);
      const option = {
        title: {
          textStyle: {
            color: '#cecece'
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'line' // 使用十字准星指示器
          },
          textStyle: {
            color: '#cecece'
          },
          formatter: function (params) {
            // 显示每个折点的数据
            let result = `${params[0].axisValue}<br/>`;
            params.forEach(item => {
              result += `${item.marker} ${item.seriesName}: ${item.data}<br/>`;
            });
            return result;
          }
        },
        grid: {
          top: '20%',
          left: '5%', // 增加图表左侧间隔
          right: '5%', // 增加图表右侧间隔
          bottom: '5%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: this.timePeriods, // 生成时间数据
          axisLabel: {
            color: '#cecece',
            fontSize: 10 // 设置字体大小
          },
          axisLine: {
            lineStyle: {
              color: '#cecece' // X轴线条颜色
            }
          },
          splitNumber: 12 // X轴刻度间隔数量
        },
        yAxis: {
          type: 'value',
          name: '告警数量',
          nameTextStyle: {
            color: '#cecece' // Y轴名称字体颜色
          },
          axisLabel: {
            color: '#cecece' // Y轴标签字体颜色
          },
          axisLine: {
            lineStyle: {
              color: '#cecece' // Y轴线条颜色
            }
          }
        },
        series: [
          {
            name: '告警数量',
            type: 'line',
            data: this.alertData, // 使用固定的告警数量数据
            lineStyle: {
              width: 2, // 折线宽度
              color: '#00ccff' // 科技风格的颜色
            },
            itemStyle: {
              color: '#00ccff' // 数据点的颜色
            },
            symbol: 'circle', // 设置折点的形状为圆形
            symbolSize: 8, // 设置折点的大小
            showSymbol: true, // 显示所有折点
            label: {
              show: true,
              position: 'top',
              color: '#fff',
              formatter: '{c}' // 显示数据值
            }
          }
        ]
      };
      this.chart.setOption(option);
      this.startAutoHighlight();
    },
    generateTimeData() {
      const hours = [];
      const startHour = 9; // 开始的小时
      const now = new Date();

      // 设置为第一天的9:00
      now.setHours(startHour, 0, 0, 0);

      // 生成从第一天的9:00到第二天的9:00的时间数据
      for (let i = 0; i < 24; i++) {
        hours.push(`${now.getHours()}:${now.getMinutes().toString().padStart(2, '0')}`);
        now.setHours(now.getHours() + 1);
      }
      return hours;
    },
    startAutoHighlight() {
      const dataLength = this.generateTimeData().length;
      let currentIndex = 0;
      setInterval(() => {
        // 取消之前的高亮
        this.chart.dispatchAction({
          type: 'downplay',
          seriesIndex: 0,
          dataIndex: (currentIndex - 1 + dataLength) % dataLength
        });
        // 高亮当前的点
        this.chart.dispatchAction({
          type: 'highlight',
          seriesIndex: 0,
          dataIndex: currentIndex
        });
        // 显示 tooltip
        this.chart.dispatchAction({
          type: 'showTip',
          seriesIndex: 0,
          dataIndex: currentIndex
        });
        currentIndex = (currentIndex + 1) % dataLength;
      }, 2000); // 每2秒切换一次
    }
  },

};
</script>

<style scoped>
.lineChart {
  width: 100%;
  height: 100%;
}
</style>
