<template>
  <div class="file-tamper-chart">
    <div class="chart-section">
      <div ref="chart" class="chart-container"></div>
    </div>
    <div class="info-section">
      <p>监控目录: <span style="text-decoration: underline; font-style: italic;color: #3488da">{{tamperData[num][2].directory}}</span></p>
      <p>文件总数: {{tamperData[num][2].count}}</p>
      <p>文件大小总和: {{tamperData[num][2].size}}</p>
      <p>最近修改时间: </p>
      <p style="color: #3488da">{{tamperData[num][2].time}}</p>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import {mapState} from "vuex";
import store from "@/store";

export default {
  name: 'FileTamperChart',
  data() {
    return {
      num:0,
      tamperData: [
        [
          {value: 5, name: '篡改文件'},
          {value: 58, name: '未篡改文件'},
          {directory: '/var/log', count: 63, size: '1.54GB', time: '2024-06-24 23:15:41'}
        ],
        [
          {value: 45, name: '篡改文件'},
          {value: 150, name: '未篡改文件'},
          {directory: '/var/log', count: 195, size: '4.95GB', time: '2024-04-14 04:15:50'}
        ],
        [
          {value: 15, name: '篡改文件'},
          {value: 66, name: '未篡改文件'},
          {directory: '/var/log', count: 81, size: '4.11GB', time: '2024-05-15 11:18:11'}
        ],
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
          this.num = newValue - 1
          this.initChart(newValue - 1)
        }
    );
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
          trigger: 'item',
        },
        legend: {
          bottom: '0%',
          left: 'center',
          textStyle: {
            color: '#fff',
          },
        },
        series: [
          {
            name: '文件状态',
            type: 'pie',
            radius: '65%',
            data: this.tamperData[num],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2,
            },
            label: {
              color: '#fff',
            },
          },
        ],
      };
      chart.setOption(option);
    },
  },
};
</script>

<style scoped>
.file-tamper-chart {
  display: flex;
  width: 100%;
  height: 100%;
}

.chart-section {
  flex: 2;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.chart-container {
  width: 80%;
  height: 80%;
}

.info-section {
  flex: 1.5;
  padding: 20% 0;
  color: #cecece;
}

.info-section h2 {
  margin-bottom: 20px;
}

.info-section p, .info-section ul {
  margin: 10px 0;
}

.info-section ul {
  list-style-type: none;
  padding-left: 0;
}

.info-section li {
  margin-bottom: 10px;
}
</style>
