<template>
  <div>
    <div id="bubble" style="width: 100%; height: 300px;"></div>
  </div>
</template>

<script>
import * as echarts from "echarts"; // 导入ECharts库
import { getAddress } from "@/API"; // 假设这是你的 API 调用方法

export default {
  data() {
    return {
      CityIp: [
        {
          city: "示例城市名1",
          ipArray: ['IP1', 'IP2']
        },
        {
          city: "示例城市名2",
          ipArray: ['IP3', 'IP4']
        }
      ],
    };
  },
  mounted() {
    this.getAddress();
  },
  methods: {
    getAddress() {
      getAddress().then(res => {
        const duplicateArray = this.findDuplicatesByCity(res.data);
        const CityKey = Object.keys(duplicateArray.duplicates).sort(); // 按城市名称首字母排序
        let newStructure = [];
        CityKey.forEach(city => {
          let firstObject = duplicateArray.duplicates[city]; // 获取每个城市数组中的第一个对象
          let newArray = firstObject.map(item => item.ip);
          newStructure.push({
            city: city,
            ipArray: newArray,
          });
        });
        this.CityIp = newStructure;
        console.log(this.CityIp)
        // 数据处理完成后，渲染气泡图
        this.renderBubbleChart();
      });
    },
    findDuplicatesByCity(arr) {
      let seen = {};
      let duplicates = {};
      for (let obj of arr) {
        let city = obj.city;
        if (!seen[city]) {
          seen[city] = true;
        } else {
          if (!duplicates[city]) {
            duplicates[city] = [];
          }
          duplicates[city].push(obj);
        }
      }
      // 返回重复的对象及其数量
      return {duplicates};
    },
    renderBubbleChart() {
      let chartDom = document.getElementById('bubble');
      let myChart = echarts.init(chartDom);

      // 准备数据
      let data = this.CityIp.map((cityData, index) => ({
        name: cityData.city,
        value: [cityData.ipArray.length, index], // 第一个值是IP数量，第二个值是位置信息
        ipArray: cityData.ipArray
      }));

      // 配置项
      let option = {
        tooltip: {
          formatter: function (params) {
            let ipList = params.data.ipArray.join(', ');
            return `地理位置: ${params.name}<br />IP列表: ${ipList}`;
          },
          position: 'right'
        },
        grid: {
          left: '5%',
          right: '10%',
          top: '15%',
          bottom: '20%',
          containLabel: true
        },
        xAxis: {
          name: '',
          type: 'value',
          axisLine: {
            lineStyle: {
              color: '#999' // X轴颜色
            }
          },
          axisLabel: {
            color: '#ccc' // X轴标签颜色
          },
          splitLine: {
            show: true
          }
        },
        yAxis: {
          type: 'category',
          data: data.map((item,i) => i+1),
          axisLine: {
            lineStyle: {
              color: '#999' // Y轴颜色
            }
          },
          axisLabel: {
            color: '#ccc' // Y轴标签颜色
          },
          splitLine: {
            show: true
          }
        },
        series: [{
          type: 'scatter',
          data: data,
          symbolSize: function (data) {
            return Math.sqrt(data[0]) * 10; // 气泡大小与IP数量相关
          },
          label: {
            emphasis: {
              show: true,
              formatter: function (param) {
                return param.data.name;
              },
              position: 'top',
              textStyle: {
                color: '#fff' // 强调标签颜色
              }
            }
          },
          itemStyle: {
            normal: {
              shadowBlur: 10,
              shadowColor: 'rgba(120, 36, 50, 0.5)',
              shadowOffsetY: 5,
              color: function (params) {
                // 根据index生成颜色
                let colorPalette = ['#5470C6', '#91CC75', '#FAC858', '#EE6666', '#73C0DE', '#3BA272', '#FC8452', '#9A60B4', '#EA7CCC'];
                return colorPalette[params.data.value[1] % colorPalette.length];
              }
            }
          }
        }]
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    }
  }
};
</script>

<style scoped>
/* 可选的样式 */
</style>
