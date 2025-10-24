<template>
  <div style="height: 100%; width: 100%;">
    <div id="china-map" ref="chinaMap"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import './js/map/china.js'; // 加载中国地图的数据
import {geoCoordMap} from "@/utils/nation.js"; // 导入包含城市经纬度的映射

export default {
  data() {
    return {
      showBackButton: false, // 控制返回按钮的显示与隐藏
      catches: {}, // 用于缓存已经加载的地图数据
      provincesText: [
        "上海", "河北", "山西", "内蒙古", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建", "江西", "山东",
        "河南", "湖北", "湖南", "广东", "广西", "海南", "四川", "贵州", "云南", "西藏", "陕西", "甘肃", "青海",
        "宁夏", "新疆", "北京", "天津", "重庆", "香港", "澳门"
      ],
    };
  },
  mounted() {
    this.initMap(); // Vue 组件挂载后初始化地图
  },
  methods: {
    initMap() {
      // 初始化 ECharts 实例
      const myChart = echarts.init(this.$refs.chinaMap);

      // 转换数据格式，用于飞线图
      const convertData = (data) => {
        return data.map((item) => {
          const fromCoord = geoCoordMap[item[0].name];
          const toCoord = geoCoordMap[item[1].name];
          if (fromCoord && toCoord) {
            return {
              fromName: item[0].name, // 起点名称
              toName: item[1].name, // 终点名称
              coords: [fromCoord, toCoord], // 起点和终点的坐标
              lineStyle: {
                color: '#f5f1ed' // 默认线条颜色
              }
            };
          }
          return null;
        }).filter(item => item !== null); // 过滤掉无效的数据
      };

      // 生成散点数据，设置发起方和接收方的颜色
      const generateScatterData = (data) => {
        const scatterData = [];
        data.forEach(item => {
          scatterData.push({
            name: item[0].name,
            value: geoCoordMap[item[0].name],
            IP: item[0].IP, // IP 地址
            itemStyle: {color: '#ff3300'} // 发起方为红色
          });
          scatterData.push({
            name: item[1].name,
            value: geoCoordMap[item[1].name],
            IP: item[1].IP, // IP 地址
            itemStyle: {color: '#ffc533'} // 接收方为蓝色
          });
        });
        return scatterData;
      };

      // 定义飞线图的数据
      const data = [
        [{name: '北京', IP: '192.168.1.1'}, {name: '深圳', value: 50, IP: '45.25.8.154'}],
        [{name: '南京', IP: '192.168.1.3'}, {name: '深圳', value: 50, IP: '45.25.8.154'}],
        [{name: '乌鲁木齐', IP: '192.168.15.15'}, {name: '深圳', value: 95, IP: '45.25.8.154'}],
        [{name: '长春', IP: '192.168.15.15'}, {name: '深圳', value: 95, IP: '45.25.8.154'}],
        [{name: '南昌', IP: '192.168.15.15'}, {name: '深圳', value: 95, IP: '45.25.8.154'}],
        [{name: '自贡', IP: '192.168.15.15'}, {name: '深圳', value: 95, IP: '45.25.8.154'}]


      ];

      // ECharts 配置项
      const option = {
        title: {
          text: ' ',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: (params) => {
            if (params.seriesType === 'effectScatter') {
              return `${params.data.name} : ${params.data.IP}`; // 显示城市名和 IP 地址
            } else if (params.seriesType === 'lines') {
              return `${params.data.fromName} -> ${params.data.toName}`; // 显示流向信息
            }
          }
        },
        geo: {
          map: 'china',
          zoom: 1.15, // 缩放比例
          roam: true, // 禁用地图的平移和缩放
          itemStyle: {
            normal: {
              areaColor: 'rgba(2, 12, 23, 0.5)', // 板块颜色较深且透明
              borderColor: 'rgb(17,153,194)', // 设置省份边界颜色为荧光蓝
              borderWidth: 0.5, // 设置省份边界宽度
              shadowColor: 'rgba(255,255,255,0.63)', // 设置阴影颜色为白色
              shadowBlur: 5 // 阴影模糊度
            },
            emphasis: {
              areaColor: '#389BB7' // 设置鼠标悬浮时的区域颜色
            }
          }
        },
        series: [
          {
            type: 'lines', // 系列类型为线图
            zlevel: 2, // 线图所在的图层 zlevel，值越大图层越靠上
            effect: {
              show: true, // 显示特效
              period: 3, // 特效动画周期，值越小动画速度越快
              trailLength: 0.5, // 特效尾迹长度，值越大尾迹越长
              color: '#f1eaea', // 特效颜色
              symbolSize: 3 // 特效符号大小
            },
            lineStyle: {
              normal: {
                color: '#ffffff', // 线的颜色
                width: 0, // 线的宽度
                curveness: 0.2// 线的弯曲程度，0 表示直线
              }
            },
            data: convertData(data) // 转换后的数据
          },
          {
            type: 'effectScatter', // 系列类型为带有涟漪特效的散点图
            coordinateSystem: 'geo', // 使用地理坐标系
            zlevel: 1, // 散点图所在的图层 zlevel
            rippleEffect: {
              brushType: 'stroke' // 涟漪的绘制方式为描边
            },
            label: {
              normal: {
                show: true, // 显示标签
                position: 'right', // 标签位置在右侧
                formatter: '{b}' // 标签格式化，显示城市名称
              }
            },
            symbolSize: 8, // 标记点的大小
            data: generateScatterData(data) // 生成的散点数据
          }
        ]
      };

      // 使用配置项和数据显示图表
      myChart.setOption(option);
    }
  }
};
</script>

<style scoped>
#china-map {
  width: 100%;
  height: 100%;
  background-color: rgba(5, 10, 23, 0.62);
}
</style>
