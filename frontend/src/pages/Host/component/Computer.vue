<template>
  <div class="Com">
    <select v-model="selectNum" @change="fetchHostData" class="select">
      <option v-for="host in hosts" :key="host.id" :value="host.id" class="option">{{ host.name }}</option>
    </select>
    <div v-if="selectedHost !== null" class="hostInfo">
      <div><strong>操作系统版本：</strong><span style="text-decoration: underline; font-style: italic;color: #3488da">{{ selectedHost.osType }} {{ selectedHost.osVersion }}</span> </div>
      <div style="display: flex;justify-content:space-between; width: 80%;margin-top: 2%;">
        <div class="item"><strong>主机名：</strong> {{ selectedHost.name}}</div>
        <div class="item"><strong>IP地址：</strong> {{ selectedHost.ip}}</div>
      </div>
      <div class="alertTitle">事件警报：</div>
      <div class="alertBox">
        <ul>
          <li v-for="event in selectedHost.events" :key="event.id">
            <div class="item"><strong>类型：</strong> {{ event.type }}</div>
            <div class="item"><strong>时间戳：</strong> {{ event.timestamp }}</div>
            <div class="item"><strong>严重性：</strong> {{ event.severity }}</div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import store from '@/store/index.js';
export default {
  data() {
    return {
      hosts: [
        {
          id: 1,
          name: '虚拟机A',
          osType: 'Linux',
          osVersion: 'Ubuntu 20.04',
          hostname: 'hostA',
          ip: '192.168.1.1',
          events: [
            {id: 1, type: '登陆失败', timestamp: '2024-07-02 10:30:00', severity: 'High'},
            {id: 2, type: '端口扫描检测', timestamp: '2024-07-02 11:15:00', severity: 'Medium'},
            {id: 3, type: '后门文件植入', timestamp: '2024-07-02 11:15:00', severity: 'High'}
          ]
        },
        {
          id: 2,
          name: '虚拟机B',
          osType: 'Windows',
          osVersion: 'Windows 10',
          hostname: 'hostB',
          ip: '192.168.1.2',
          events: [
              {id: 3, type: '检测到恶意软件', timestamp: '2024-07-02 09:45:00', severity: 'High'}
          ]
        },
        {
          id: 3,
          name: '虚拟机C',
          osType: 'Linux',
          osVersion: 'CentOS 7',
          hostname: 'hostC',
          ip: '192.168.1.3',
          events: [
            {id: 1, type: '检测到恶意软件', timestamp: '2024-07-02 09:45:00', severity: 'High'},
            {id: 2, type: '端口扫描检测', timestamp: '2024-07-02 09:45:00', severity: 'High'}
          ]
        }
      ],
      selectNum:1,
      selectedHost: null,
    };
  },
  methods: {
    fetchHostData() {
      this.selectedHost = this.hosts[this.selectNum-1]
      store.dispatch("updateHostNum",this.selectNum)
      console.log(store.state.HostNum)
    }
  },
  mounted() {
    this.selectedHost = this.hosts[this.selectNum-1]
  }
};
</script>

<style scoped>

.Com {
  color: #CECECE;
  padding:10px;
  position: relative;
}
.select{
  position: absolute;
  top: 46px;
  right: 5px;
  padding: 3px;
  background-color: rgba(20, 72, 131, 0.7);
  color: #006fff;
}
.option{
  padding: 3px;
  background-color: rgba(2, 9, 17, 0.7);
  color: #006fff;
}
.hostInfo{
  padding-top: 35px;
}
.alertTitle{
  margin: 5px 0;
  color: #237ede;
  font-weight: bold;
  font-size: 18px;
}
.alertBox {
  padding: 0 0 10px;
  height: 180px;
  position: relative;
  overflow: auto;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE/Edge */
}
ul{
  padding: 0 35px;
}
li {
  margin-top: 15px;
}

.item {
  margin: 5px 0;
}
</style>
