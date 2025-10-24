<template>
  <div class="process-table">
    <table>
      <thead>
      <tr>
        <th>PID</th>
        <th>进程名称</th>
        <th>CPU%</th>
        <th>内存%</th>
        <th>启动时间</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(process, index) in paginatedProcesses" :key="index">
        <td>{{ process.pid }}</td>
        <td>{{ process.name }}</td>
        <td>{{ process.cpu }}</td>
        <td>{{ process.memory }}</td>
        <td>{{ process.startTime }}</td>
      </tr>
      </tbody>
    </table>
    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
      <span>{{ currentPage }} / {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">下一页</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentPage: 1,
      itemsPerPage: 5,
      processes: [
        {pid: 1, name: 'sshd', cpu: '0.5%', memory: '0.2%', startTime: '2024-07-01 08:12:45'},
        {pid: 2, name: 'apache2', cpu: '2.3%', memory: '1.8%', startTime: '2024-07-01 09:30:15'},
        {pid: 3, name: 'mysql', cpu: '3.1%', memory: '4.5%', startTime: '2024-07-01 10:45:22'},
        {pid: 4, name: 'nginx', cpu: '1.8%', memory: '1.2%', startTime: '2024-07-01 11:20:57'},
        {pid: 5, name: 'cron', cpu: '0.1%', memory: '0.1%', startTime: '2024-07-01 12:05:33'},
        {pid: 6, name: 'sshd', cpu: '0.4%', memory: '0.3%', startTime: '2024-07-01 13:40:19'},
        {pid: 7, name: 'apache2', cpu: '2.6%', memory: '2.0%', startTime: '2024-07-01 14:55:48'},
        {pid: 8, name: 'nginx', cpu: '1.5%', memory: '1.0%', startTime: '2024-07-01 15:30:01'},
        {pid: 9, name: 'systemd', cpu: '0.3%', memory: '0.4%', startTime: '2024-07-01 16:18:09'},
        {pid: 10, name: 'bash', cpu: '0.2%', memory: '0.1%', startTime: '2024-07-01 17:00:55'},
      ]
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.processes.length / this.itemsPerPage);
    },
    paginatedProcesses() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.processes.slice(start, end);
    },
  },
  methods: {
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
  },
};
</script>

<style scoped>
.process-table {
  width: 100%;
  overflow-x: auto;
}

table {
  width: 96%;
  margin: 3% 2% 0;
  border-collapse: collapse;
  text-align: center;
  color: #fff;
  background-color: rgba(0, 0, 0, 0.5);
}

th,
td {
  padding: 10px;
  border: 1px solid rgba(255, 255, 255, 0.3);
}

th {
  background-color: rgba(0, 0, 0, 0.7);
}

tbody tr:nth-child(even) {
  background-color: rgba(255, 255, 255, 0.1);
}

tbody tr:nth-child(odd) {
  background-color: rgba(0, 0, 255, 0.1);
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 0.5%;
  color: white;
}

.pagination button {
  margin: 0 10px;
  padding: 5px 10px;
  background-color: #298ee1;
  border: none;
  color: #fff;
  cursor: pointer;
  border-radius: 5px;
}

.pagination button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
  color: #1a1a1a;
}
</style>
