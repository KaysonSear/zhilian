<template>
  <div class="container">
    <div class="left">
      <div class="box">
        <div class="topTitle">流 量 协 议 统 计</div>
        <dv-border-box-13>
          <ProtocolPieChart></ProtocolPieChart>
        </dv-border-box-13>
      </div>
      <div class="box">
        <dv-border-box-13>
          <div class="topTitle">I P 访 问 统 计</div>
          <!-- <DynamicBarChart></DynamicBarChart> -->
          <RankingBoard></RankingBoard>
        </dv-border-box-13>
      </div>
    </div>
    <div class="right">
      <dv-border-box-6 :color="['#4F68B7', '#37ff00']">
        <div style="height: 1%;"></div>
        <div class="selectBox">
          <div style="display: flex; flex: 1">
            <div class="LoadBtn">上传文件</div>
<!--            <div class="tip">当前为：<span style="text-decoration: underline; font-style: italic;">异常流量包</span>-->
<!--            </div>-->
          </div>
          <div class="page" style="flex: 1">
            <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
            <span>{{ currentPage }} / {{ totalPages }}</span>
            <button @click="nextPage" :disabled="currentPage === totalPages">下一页</button>
            <input type="number" v-model.number="inputPage" style="width: 25px; text-align: center;">
            <button @click="goToPage" :disabled="inputPage < 1 || inputPage > totalPages">跳转</button>
          </div>
        </div>
        <div class="frosted__glass">
          <table>
            <thead>
            <tr>
              <th>序号</th>
              <th>时间</th>
              <th>源IP</th>
              <th>源端口</th>
              <th>协议</th>
              <th>长度</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in paginatedData" :key="index">
              <td>{{ (currentPage - 1) * itemsPerPage + index + 1 }}</td>
              <td>{{ formatDate(item.timestamp) }}</td>
              <td>{{ item.srcIp }}</td>
              <td>{{ item.srcPort }}</td>
              <td>{{ getProtocolName(item.protocol) }}</td>
              <td>{{ item.packetLengthMean.toFixed(2) }}</td>
              <td style="color: rgb(52,156,245);font-size: 13px" @click="openModal(item)">查看详情</td>
            </tr>
            </tbody>
          </table>
        </div>
      </dv-border-box-6>
      <div class="InfoModal" v-if="infoModal">
        <div class="InfoModal-content">
          <span class="InfoClose" @click="closeModal">&times;</span>
          <div class="infoTitle">网络流量详情</div>
          <div class="infoBox">
            <div style="border: 1px solid #1d649d;padding: 10px 10px">
              <div style="display: flex;font-size: 18px;color: #d5d5d5">
                <div style="flex: 1;">源IP：<span style="color:#0888f5;">{{ selectData.srcIp }}</span></div>
                <div style="flex: 1;">源端口：<span style="color:#0888f5;">{{ selectData.srcPort }}</span></div>
              </div>
              <div style="display: flex;font-size: 18px;color: #d5d5d5;margin-top: 20px;">
                <div style="flex: 1;">目标IP：<span style="color:#0888f5;">{{ selectData.dstIp }}</span></div>
                <div style="flex: 1;">目标端口：<span style="color:#0888f5;">{{ selectData.dstPort }}</span></div>
              </div>
            </div>
            <div style="border: 1px solid #1d649d;padding: 10px 10px;margin-top: 35px;">
              <div style="display: flex;font-size: 18px;color: #d5d5d5">
                <div style="flex: 1;">协议：<span style="color:#0888f5;">{{ getProtocolName(selectData.protocol)
                  }}</span></div>
                <div style="flex: 1;">数据包长度：<span style="color:#0888f5;">{{ selectData.packetLengthMean.toFixed(2)
                  }}</span>&nbsp;<span style="font-size: 14px">Bytes</span></div>
              </div>
              <div style="display: flex;font-size: 18px;color: #d5d5d5;margin-top: 20px;">
                <div style="flex: 1;">访问时间：<span style="color:#0888f5;">{{ selectData.timestamp }}</span></div>
              </div>
            </div>
            <div style="border: 1px solid #1d649d;padding: 10px 10px;margin-top: 35px;font-size: 18px;color: #d5d5d5">
                <div style="flex: 1;">源MAC地址：<span style="color:#0888f5;">{{ selectData.sourceMAC }}</span></div>
                <div style="flex: 1;margin-top: 15px;">目标MAC地址：<span style="color:#0888f5;">{{ selectData.destinationMAC }}</span></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ProtocolPieChart from "@/pages/Flow/component/ProtocolPieChart.vue";
import DynamicBarChart from "@/pages/Flow/component/DynamicBarChart.vue";
import RankingBoard from "@/pages/Flow/component/RankingBoard.vue";
import {getAllFlow} from "../../API/index";
import WarningDetails from "@/pages/Warning/component/WarningDetails.vue";

export default {
  data() {
    return {
      infoModal: false,
      selectData: {
        packetLengthMean: 0,
      },
      currentPage: 1,
      inputPage: 1, // 用户输入的页数
      itemsPerPage: 14,
      totalItems: 0,
      data: [],
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.totalItems / this.itemsPerPage);
    },
    paginatedData() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.data.slice(start, end);
    },
  },
  methods: {
    openModal(item) {
      this.selectData = item
      this.selectData.sourceMAC ="00:1A:2B:3C:4D:5E"
      this.selectData.destinationMAC ="08:00:27:00:BE:EF"
      this.infoModal = true; // 打开弹框
    },
    closeModal() {
      this.infoModal = false;
    },
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
    goToPage() {
      if (this.inputPage >= 1 && this.inputPage <= this.totalPages) {
        this.currentPage = this.inputPage;
      }
    },
    fetchData() {
      getAllFlow()
        .then((res) => {
          const payload = res && res.data ? res.data : [];
          this.data = Array.isArray(payload) ? payload : [];
          this.totalItems = this.data.length;
        })
        .catch((error) => {
          console.error("获取流量数据失败:", error);
          this.data = [];
          this.totalItems = 0;
        });
    },
    getProtocolName(protocolNumber) {
      if (protocolNumber === undefined) {
        return
      }
      const protocolMap = {
        "6": "TCP",
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
      return protocolMap[protocolNumber.toString()] || "未知协议";
    },
    formatDate(inputDate) {
      if (!inputDate || typeof inputDate !== "string") {
        return "";
      }
      // 分割输入的日期字符串
      let parts = inputDate.split(' ');
      if (parts.length < 2) {
        return inputDate;
      }

      // 分割日期部分
      let dateParts = parts[0].split('/');
      if (dateParts.length < 3) {
        return inputDate;
      }
      let day = parseInt(dateParts[0], 10);
      let month = parseInt(dateParts[1], 10);
      let year = parseInt(dateParts[2], 10);

      // 分割时间部分
      let timeParts = parts[1].split(':');
      let hours = parseInt(timeParts[0], 10);
      let minutes = parseInt(timeParts[1], 10);

      // 构建日期对象
      let date = new Date(year, month - 1, day, hours, minutes); // 月份需要减去1，因为月份是从0开始的

      // 格式化为 YYYY-MM-DD HH:mm 格式的字符串
      let formattedDate = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;

      return formattedDate;
    }

  },
  created() {
    this.fetchData();
  },
  components: {
    WarningDetails,
    ProtocolPieChart,
    DynamicBarChart,
    RankingBoard,
  },
};
</script>

<style scoped>

.InfoModal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  z-index: 1000;
}

.InfoModal-content {
  border: 0px solid transparent; /* Initial transparent border */
  background-color: #1f3449;
  color: #fff;
  width: 45%;
  height: 55%;
  position: relative;

}

/* Apply the animation to the border */
.InfoModal-content::before {
  content: ''; /* Required for pseudo-element */
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  border: 1px solid transparent; /* Initial transparent border */
  border-image-slice: 1; /* Ensure the border slices properly */
  animation: borderGradient 5s linear infinite; /* Animation settings */
}

.infoTitle {
  background: linear-gradient(to right, #0015ff, #00d0ff); /* 红蓝渐变背景 */
  -webkit-background-clip: text; /* 使用背景色填充文字 */
  -webkit-text-fill-color: transparent; /* 隐藏实际文字颜色 */
  text-align: center;
  padding: 10px;
  font-size: 25px;
  letter-spacing: 5px;
  font-weight: bold;
}

.infoBox {
  height: 90%;
  position: relative;
  overflow: auto;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE/Edge */
  padding: 1% 5%;
}

.infoBox::-webkit-scrollbar {
  display: none; /* Chrome/Safari/Opera */
}


.InfoClose {
  position: absolute;
  top: 0px;
  right: 10px;
  color: #fff;
  font-size: 30px;
  cursor: pointer;
}

.container {
  padding: 0 30px;
  height: 100vh;
  display: flex;
}

.left {
  height: 88%;
  flex: 2;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-right: 2%;

  .box {
    position: relative;
    height: 48%;
  }
}

.topTitle {
  position: absolute;
  color: #eec101;
  font-size: 20px;
  font-weight: 700;
  top: 8%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.right {
  height: 88%;
  flex: 5;
}

@keyframes borderGradient {
  0% {
    border-image-source: linear-gradient(45deg, #9292fd, #0599f8);
  }
  25% {
    border-image-source: linear-gradient(45deg, #0599f8, #61bfff);
  }
  50% {
    border-image-source: linear-gradient(45deg, #61bfff, #005f9d);
  }
  75% {
    border-image-source: linear-gradient(45deg, #005f9d, #9292fd);
  }
  100% {
    border-image-source: linear-gradient(45deg, #9292fd, #0599f8);
  }
}

.frosted__glass {
  width: 96%;
  margin: 2% 2% 0;
  box-sizing: border-box;
  display: flex;
  justify-content: center;
  align-items: center;
  backdrop-filter: blur(3px);
  border: 1.5px solid;
  border-image-slice: 1;
  animation: borderGradient 3s infinite;
  box-shadow: 0 0 20px 3px rgba(54, 54, 157, 0.5);
  border-radius: 10px;
}

table {
  width: 100%;
  border-collapse: collapse;
  text-align: center;
  color: #cecece;
}

th,
td {
  padding: 10px;
  border: 1px solid rgba(178, 177, 177, 0.3);
}

th {
  //background: rgba(48, 48, 178, 0.7);
  background: transparent;
}

tbody tr:nth-child(even) {
  background: rgba(220, 220, 220, 0.2);
}

tbody tr:nth-child(odd) {
  background: rgba(89, 89, 141, 0.2);
}

.selectBox {
  width: 98%;
  height: 10%;
  margin: 0 1%;
  box-sizing: border-box;
  border: 1px solid #5a678d;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 1%;
}

.LoadBtn {
  height: 25%;
  padding: 1%;
  font-size: 15px;
  border: 1px solid #298ee1;
  background-color: rgba(42, 58, 83, 0.73);
  border-radius: 2px;
  cursor: pointer;
  /* 添加鼠标指针 */
  color: #b7eaf5;
}

.tip {
  color: #b2c8cc;
  margin-left: 1%;
  padding-top: 1%;
}

.page {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  color: white;
}

.page button {
  margin: 0 5px;
  padding: 5px 10px;
  background-color: #298ee1;
  border: none;
  color: #fff;
  cursor: pointer;
  border-radius: 2px;
}

.page button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
  color: #1a1a1a;
}
</style>
