<template>
  <div ref="chart" style="width: 100%; height: 100%;"></div>
</template>

<script>
import * as echarts from 'echarts';
import {getProtocolCounts} from '../../../API/index'
export default {
  name: 'ProtocolPieChart',
  data() {
    return {
      protocolData: [],

    };
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      this.protocolData = [
        { value: 1048, name: 'HTTP' },
        { value: 310, name: 'UDP' },
        { value: 178, name: 'TCP' },
        { value: 120, name: 'Others' }
      ];
      getProtocolCounts().then(Res=>{
        const protocolMap = {
          "6": "TCP ",
          "17": "UDP",
          "1": "ICMP",
          "80": "HTTP",
          "443": "HTTPS",
          "21": "FTP",
          "22": "SSH",
          "53": "DNS",
          "25": "SMTP",
          "0": "其他"
        };
        // this.protocolData=Res.data.map(item => ({
        //   value: item.count,
        //   name: protocolMap[item.protocol]
        // }));
        this.initChart();

      })


    },
    initChart() {
      const chart = echarts.init(this.$refs.chart);

      const option = {
        tooltip: {
          trigger: 'item',
          backgroundColor: 'rgba(50, 50, 50, 0.7)',
          borderColor: '#ccc',
          textStyle: {
            color: '#fff'
          }
        },
        legend: {
          bottom: '4%',
          left: 'center',
          textStyle: {
            color: '#ccc'
          }
        },
        series: [
          {
            name: '流量协议数',
            type: 'pie',
            radius: ['30%', '50%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 1,
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowOffsetY: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            },
            label: {
              show: true,
              position: 'outside',
              formatter: '{b}: {c} ({d}%)',
              color: '#fff'
            },
            emphasis: {
              label: {
                show: true,
                position: 'center',
                formatter: '{b}\n{c} ({d}%)',
                fontSize: 20,
                fontWeight: 'bold',
                color: '#fff'
              }
            },
            labelLine: {
              show: true,
              length: 20,
              length2: 10,
              lineStyle: {
                color: '#fff',
                width: 1
              }
            },
            data: this.protocolData,
            color: ['#00c1de', '#007aff', '#1e3cff', '#3e2ec8', '#5e4dff']
          }
        ]
      };

      chart.setOption(option);

      // 自动显示效果
      let currentIndex = -1;
      setInterval(() => {
        const dataLen = option.series[0].data.length;
        // 取消之前高亮的图形
        chart.dispatchAction({
          type: 'downplay',
          seriesIndex: 0,
          dataIndex: currentIndex
        });
        currentIndex = (currentIndex + 1) % dataLen;
        // 高亮当前图形
        chart.dispatchAction({
          type: 'highlight',
          seriesIndex: 0,
          dataIndex: currentIndex
        });
        // 显示 tooltip
        chart.dispatchAction({
          type: 'showTip',
          seriesIndex: 0,
          dataIndex: currentIndex
        });
      }, 5000);
    }
  }
};
</script>

<style scoped>
</style>
