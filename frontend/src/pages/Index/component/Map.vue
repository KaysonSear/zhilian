<template>
  <div class="worldMap" id="map"></div>
</template>

<script>
import "@/utils/world.js"; // 导入世界地图配置
import * as echarts from "echarts"; // 导入ECharts库
import {geoCoordMap} from "@/utils/nation.js";
import {getAddress} from "@/API";
export default {
  data() {
    return {
      myChart: null, // ECharts实例
      DataArray: [],
      one: [{name: '南非', IP: '192.168.15.15'}, {name: '深圳', value: 95, IP: '45.25.8.154'}],
      // two: [{name: '俄罗斯', IP: '192.168.15.15'}, {name: '北京', value: 95, IP: '89.4.31.89'}],
      // three: [{name: '美国', IP: '192.168.15.15'}, {name: '乌鲁木齐', value: 95, IP: '192.168.31.89'}],
    };
  },
  mounted() {
    // 组件挂载后设置图表
    this.getData()
  },
  methods: {
    getData() {
      getAddress().then(res => {
        // this.DataArray
        const duplicateArray = this.findDuplicatesByCity(res.data);
        const CityKey = Object.keys(duplicateArray.duplicates);
        let newStructure = [];
        CityKey.forEach(city => {
          let firstObject = duplicateArray.duplicates[city][0]; // 获取每个城市数组中的第一个对象
          newStructure.push({
            city: city,
            IP: firstObject.ip,
            value:duplicateArray.duplicates[city].length
          });
        });
        let newData = newStructure.map(item => ([
              {
                name: item.city,
                IP: item.IP
              },
              {
                name: '深圳',
                value: item.value,
                IP: '192.168.31.89'
              }]
        ));
        // this.DataArray.push(newData);
        newData.push(this.one)
        // newData.push(this.two)
        // newData.push(this.three)
        this.DataArray = newData
        this.setChart();
      })

    },
    findDuplicatesByCity(arr) {
      let seen = {};
      let duplicates = {};
      let duplicateCount = 0;

      for (let obj of arr) {
        let city = obj.city;
        if (!seen[city]) {
          seen[city] = true;
        } else {
          if (!duplicates[city]) {
            duplicates[city] = [];
          }
          duplicates[city].push(obj);
          duplicateCount++;
        }
      }
      // 返回重复的对象及其数量
      return { duplicates, duplicateCount };
    },
    // 设置并配置ECharts实例的方法
    setChart() {
      // 示例数据，表示通信行为
      var BJData = this.DataArray

      // 转换数据函数，将数据转换为ECharts所需格式
      var convertData = function (data) {
        var res = [];
        for (var i = 0; i < data.length; i++) {
          var dataItem = data[i];
          var fromCoord = geoCoordMap[dataItem[0].name]; // 获取起点坐标
          var toCoord = geoCoordMap[dataItem[1].name]; // 获取终点坐标
          if (fromCoord && toCoord) {
            res.push({
              fromName: dataItem[0].name,
              toName: dataItem[1].name,
              coords: [fromCoord, toCoord], // 存储起点和终点坐标
              value:dataItem[1].value
            });
          }
        }
        return res;
      };

      // ECharts配置项
      let option = {
        tooltip: {
          trigger: 'item', // 触发类型
          axisPointer: {
            type: 'line' // 坐标轴指示器类型
          },
          backgroundColor: '#11367a', // 背景颜色
          textStyle: {
            color: '#ffffff', // 文字颜色
            fontSize: 10, // 文字大小
          },
          formatter: function (data) {
            // 格式化提示框内容
            if (data.seriesType === 'effectScatter' || data.seriesType === 'scatter') {
              return `位置: ${data.name} <br> IP: ${data.data.IP}`;
            } else if (data.seriesType === 'lines') {
              return `线路: ${data.data.fromName} -> ${data.data.toName} 访问次数：${data.data.value}`;
            }
          }
        },
        legend: {
          orient: 'horizontal', // 水平方向
          top: '10%',
          right: '3%',
          data: ['组织地址'], // 图例数据
          itemHeight: 7, // 图例项高度
          textStyle: {
            color: '#75deef', // 文字颜色
            fontSize: 16 // 文字大小
          }
        },
        grid: {
          top: '88%', // 网格距离容器上边界的距离
          left: '3%', // 网格距离容器左边界的距离
          right: '3%', // 网格距离容器右边界的距离
          bottom: '2%', // 网格距离容器下边界的距离
        },
        xAxis: {
          type: 'category', // 类目轴
          boundaryGap: false, // 两端空白
          data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], // 横坐标数据
          axisLabel: {
            show: false // 不显示坐标轴标签
          },
          axisTick: {
            show: false // 不显示坐标轴刻度
          },
          axisLine: {
            show: false, // 显示坐标轴线
            lineStyle: {
              color: '#3F4527' // 坐标轴线颜色
            }
          },
          splitLine: {
            show: false // 不显示分割线
          },
        },
        yAxis: {
          type: 'value', // 数值轴
          axisLabel: {
            show: false // 不显示坐标轴标签
          },
          axisTick: {
            show: false // 不显示坐标轴刻度
          },
          axisLine: {
            show: false // 不显示坐标轴线
          },
          splitLine: {
            show: false // 不显示分割线
          }
        },
        geo: {
          map: 'world', // 使用世界地图
          zoom: 1.0, // 缩放比例
          aspectScale: 1, // 地图长宽比
          top: '18%', // 距离容器上边界的距离
          left: '6%', // 距离容器左边界的距离
          right: '6%', // 距离容器右边界的距离
          bottom: '8%', // 距离容器下边界的距离
          roam: true, // 开启缩放、拉伸和漫游功能
          emphasis: {
            itemStyle: {
              areaColor: '#2a333d' // 高亮区颜色
            },
            label: {
              show: false // 不显示高亮区标签
            }
          },
          // 地图颜色设置
          itemStyle: {
            areaColor: '#243C63', // 区域颜色
            borderColor: '#068FCE' // 边界颜色
          }
        },
        series: [
          {
            type: 'lines', // 线图
            coordinateSystem: 'geo', // 使用地理坐标系
            zlevel: 2, // 图层级别
            effect: {
              show: true, // 显示特效
              period: 6, // 特效动画时间
              trailLength: 0, // 特效尾迹长度
              color: '#ffffff', // 特效颜色
              symbol: 'image://' + require('@/assets/weapon.png'),  // 使用图片作为特效符号
              symbolSize: [15, 15] // 特效大小
            },
            lineStyle: {
              normal: {
                color: '#d3d3d3', // 线颜色
                width: 1, // 线宽
                curveness: 0.2 // 线曲度
              }
            },
            data: convertData(BJData) // 转换后的数据
          },
          {
            name: '组织地址',
            type: 'effectScatter', // 涟漪散点图
            coordinateSystem: 'geo', // 使用地理坐标系
            zlevel: 2, // 图层级别
            rippleEffect: {
              brushType: 'stroke' // 波纹效果类型
            },
            data: BJData.map(function (dataItem) {
              // 映射数据
              return {
                name: dataItem[0].name + '-' + dataItem[1].name,
                value: geoCoordMap[dataItem[1].name].concat([dataItem[1].value]),
                IP: dataItem[0].IP + '-' + dataItem[1].IP
              };
            }),
            symbolSize: function (val) {
              return val[2] / 10; // 根据值计算符号大小
            },
            label: {
              show: false // 不显示标签
            },
            emphasis: {
              itemStyle: {
                borderColor: '#fff', // 高亮边界颜色
                borderWidth: 1 // 高亮边界宽度
              }
            },
            itemStyle: {
              color: 'rgb(255,196,0)', // 组织地址颜色
            }
          },
          {
            name: '起点',
            type: 'scatter', // 散点图
            coordinateSystem: 'geo', // 使用地理坐标系
            zlevel: 2, // 图层级别
            symbol: 'circle', // 符号类型
            symbolSize: 10, // 符号大小
            itemStyle: {
              color: 'rgb(255,255,255)' // 项目颜色
            },
            data: BJData.map(function (dataItem) {
              // 映射数据
              return {
                name: dataItem[0].name,
                value: geoCoordMap[dataItem[0].name],
                IP: dataItem[0].IP // 添加IP属性
              };
            })
          },
          {
            name: '终点',
            type: 'scatter', // 散点图
            coordinateSystem: 'geo', // 使用地理坐标系
            zlevel: 2, // 图层级别
            symbol: 'circle', // 符号类型
            symbolSize: 10, // 符号大小
            itemStyle: {
              color: 'rgb(255,255,255)' // 项目颜色
            },
            data: BJData.map(function (dataItem) {
              // 映射数据
              return {
                name: dataItem[1].name,
                value: geoCoordMap[dataItem[1].name],
                IP: dataItem[1].IP // 添加IP属性
              };
            })
          }
        ]
      };

      // 初始化ECharts实例
      this.myChart = echarts.init(document.getElementById('map'));
      this.myChart.clear(); // 清空实例
      this.myChart.resize(); // 调整大小
      this.myChart.setOption(option); // 设置选项
    }
  },

};
</script>

<style scoped>
.worldMap {
  width: 100%;
  height: 100%;
  background-color: rgba(12, 31, 74, 0.85);
}
</style>
