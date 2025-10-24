<template>
  <div class="chartContainer">
    <div class="barChart" ref="chart"></div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import {getLabelCounts} from '../../../API/index'

export default {
  name: "BarChart",
  data() {
    return {
      Data: {
        label:["PostScan","WebAttack","DDoS攻击","Dos"],
        count:[15,14,25,12]
      }
    };
  },
  mounted() {
    // this.getData();

    this.initChart()

  },
  methods: {
    getData(){
      getLabelCounts().then(res=>{
        this.Data.label = res.data.map(item => item.label);
        this.Data.count = res.data.map(item => item.count);
        this.initChart()
      })
    },
    initChart() {
      const chart = echarts.init(this.$refs.chart);
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: this.Data.label,
          axisLabel: {
            color: '#cecece', // X轴标签字体颜色
            fontSize: 10, // X轴标签字体大小
            interval: 0 // 强制显示所有标签
          },
          axisLine: {
            lineStyle: {
              color: '#cecece' // X轴线条颜色
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            color: '#cecece' // Y轴标签字体颜色
          },
          axisLine: {
            lineStyle: {
              color: '#cecece' // Y轴线条颜色
            }
          },
          splitLine: {
            lineStyle: {
              color: '#7a7a7a' // 分割线颜色
            }
          }
        },
        series: [
          {
            name: '次数',
            type: 'bar',
            data: this.generateAttackData(), // 生成攻击次数数据
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#00c6ff' }, // 渐变起始色
                { offset: 1, color: '#0072ff' }  // 渐变结束色
              ])
            }
          }
        ]
      };
      chart.setOption(option);
    },
    generateAttackData() {
      // 生成模拟的攻击次数数据
      return this.Data.count;
    }
  }
};
</script>

<style scoped>
.chartContainer {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  width: 100%;
  height: 100%;
}

.barChart {
  width: 80%;
  height: 90%;
}
</style>
