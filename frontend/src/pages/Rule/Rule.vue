<template>
  <div class="container">
    <div class="Box">
      <dv-border-box-10 :color="['#152C6D', '#003fff']">
        <div class="TopArea">
          <dv-border-box-6 :color="['#5d6a8a', '#5d6a8a']">
            <div
                style="height: 100%; width: 100%; box-sizing: border-box; display: flex; align-items: center; justify-content: space-between; color: #cecece; padding: 0 40px">
              <div style="display: flex; align-items: center;">
                <span
                    style="width: 50px; height: 30px; line-height: 30px; margin-right: 15px; font-size: 18px;">搜索</span>
                <input type="text" style="width: 100%; background-color: #fff; color: #1a1a1a;">
              </div>
              <div class="addBtn" @click="openModal">+自定义规则</div>
            </div>
          </dv-border-box-6>
        </div>
        <div class="modal" v-if="showModal">
          <div class="modal-content">
            <span class="close" @click="closeModal">&times;</span>
            <div class="contentBox">
              <h1 style="text-align: center;color: #007df6">规则</h1>
              <div class="item">
                <div class="titleItem">规则名称：</div>
                <input type="text" v-model="modelItem.description">
              </div>
              <div class="item">
                <div class="titleItem">存放位置：</div>
                <select class="select">
                  <option value="位置1">/etc/huang</option>
                  <option value="位置2">/etc/AppData</option>
                  <option value="位置3">/etc/Program Files</option>
                </select>
              </div>
              <div class="item">
                <div class="titleItem">是否共享：</div>
                <div :class="['switch', { 'switch--on': isActive }]" @click="toggle" style="  margin-top: 10px;;" >
                  <div class="switch__toggle"></div>
                </div>
              </div>
              <div class="item">
                <div class="titleItem">规则内容：</div>
                <textarea type="text" v-model="modelItem.pattern"/>
              </div>
              <div  class="Btn"><button @click="submitInfo()">确认</button></div>
            </div>
          </div>
        </div>
        <div class="BottomArea">
          <div class="custom-table">
            <table>
              <thead>
              <tr>
                <th style="width: 10%;">序号</th>
                <th style="width: 20%;">
                  <span>规则名称</span>
                  <select v-model="selectedDescription" @change="filterByDescription" style="margin-left: 20px;">
                    <option value="">全部</option>
                    <option v-for="(desc, index) in uniqueDescriptions.slice(0, 10)" :key="index" :value="desc">{{ desc
                      }}
                    </option>
                  </select>
                </th>
                <th style="width: 20%;">规则内容</th>
                <th style="width: 10%;">文件位置</th>
                <th style="width: 10%;">是否共享</th>
                <th style="width: 10%;">操作</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(item, index) in paginatedData" :key="item.id">
                <td>{{ item.id }}</td>
                <td>{{ item.description }}</td>
                <td style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;max-width: 600px">{{
                    item.pattern }}
                </td>
                <td>/etc/huang</td>
                <td>{{ item.status === 1 ? '已开启' : '已关闭' }}</td>
                <td>
                  <button @click="editItem(item)">编辑</button>
                  <button @click="deleteItem(item.id)">删除</button>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
          <div class="pagination">
            <button :disabled="currentPage === 1" @click="prevPage">上一页</button>
            <span style="margin: 0 5px;">当前页: {{ currentPage }}</span>
            <button :disabled="isLastPage" @click="nextPage">下一页</button>
          </div>
        </div>
      </dv-border-box-10>
    </div>
  </div>
</template>

<script>
import {getAllRule} from '@/API';
import ReportText from "@/pages/Warning/component/ReportText.vue";

export default {
  components: {ReportText},
  data() {
    return {
      data: [],
      currentPage: 1,
      itemsPerPage: 10,
      uniqueDescriptions: [],
      selectedDescription: '', // 新增选中的规则名称筛选条件
      showModal: false, // 控制弹框显示与隐藏的数据
      isActive: false,
      modelItem:{}
    };
  },
  computed: {
    paginatedData() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.filteredData.slice(start, end); // 使用 filteredData 进行分页显示
    },
    isLastPage() {
      return this.currentPage === Math.ceil(this.filteredData.length / this.itemsPerPage);
    },
    filteredData() {
      if (this.selectedDescription) {
        return this.data.filter(item => item.description === this.selectedDescription);
      } else {
        return this.data;
      }
    },
  },
  mounted() {
    this.getRuleData();
  },
  methods: {
    toggle() {
      this.isActive = !this.isActive;
    },
    openModal() {
      this.modelItem = {}
      this.showModal = true; // 打开弹框
    },
    closeModal() {
      this.isActive = false
      this.showModal = false; // 关闭弹框
    },
    editItem(item) {
      console.log(item);
      this.showModal = true
      if(item.status===1){
        this.isActive = true
      }
      this.modelItem = item
    },
    submitInfo(){
      if(this.isActive){
        this.modelItem.status = 1
      }else{
        this.modelItem.status = 0
      }
      this.$message.success("编辑成功！")
      this.showModal = false
      console.log(this.modelItem)
      // 接口提交
    },
    deleteItem(id) {
      this.data = this.data.filter(item => item.id !== id);
    },
    nextPage() {
      if (!this.isLastPage) {
        this.currentPage++;
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },

    getRuleData() {
      getAllRule().then(res => {
        this.data = res.data;
        let descriptions = this.data.map(item => item.description);
        this.uniqueDescriptions = Array.from(new Set(descriptions));
      });
    },
    filterByDescription() {
      // 当下拉选择框变化时，更新当前页数为第一页
      this.currentPage = 1;
    },
  },
};
</script>

<style scoped>
.Btn {
  text-align: center;
  button {
    padding: 5px 20px;
    border: 1px solid #298ee1;
    background-color: rgba(42, 58, 83, 0.73);
    color: #b7eaf5;
    border-radius: 5px;
    cursor: pointer;
    font-size: 15px;
  }
}
.switch {
  width: 60px;
  height: 30px;
  background-color: #999da2; /* 浅蓝色背景 */
  border-radius: 15px;
  position: relative;
  cursor: pointer;
  transition: background-color 0.3s;
  overflow: hidden; /* 保证子元素不会溢出 */
}

.switch--on {
  background-color: #1d70d9; /* 开启状态的深蓝色 */
}

.switch__toggle {
  width: 26px;
  height: 26px;
  background: radial-gradient(circle, #ffffff 0%, rgba(255, 255, 255, 0) 80%); /* 白色至透明的径向渐变 */
  border-radius: 50%;
  position: absolute;
  top: 2px;
  left: 2px;
  transition: left 0.3s;
}

.switch--on .switch__toggle {
  left: 32px; /* 开启状态下移动 */
}
.contentBox {
  padding: 25px;
  padding-top: 0;
}
.item{
  margin: 10px;
}
.titleItem{
  color: #cecece;
}
textarea{
  background-color: #cecece;
  border-radius: 5px;
  box-sizing: border-box;
  margin-top: 10px;
  width: 100%;
  padding: 5px;
  font-size: 18px;
  line-height: 18px;
  height: 120px;
;
}
.select{
  background-color: #cecece;
  border-radius: 5px;
  box-sizing: border-box;
  margin-top: 10px;
  width: 100%;
  padding: 5px;
  font-size: 18px;
  line-height: 18px;
}
input{
  background-color: #cecece;
  border-radius: 5px;
  box-sizing: border-box;
  margin-top: 10px;
  width: 100%;
  padding: 5px;
  font-size: 18px;
  line-height: 18px;
}
.modal {
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

.modal-content {
  background-color: #10131B;
  border: 2px solid #186a8d;
  color: #fff;
  width: 45%;
  height: 62%;
  position: relative;
  overflow: auto;
}

.close {
  position: absolute;
  top: 0px;
  right: 10px;
  color: #fff;
  font-size: 30px;
  cursor: pointer;
}

.container {
  height: 100vh;
  padding: 0 30px;

  .Box {
    height: 89%;
  }

  .TopArea {
    height: 10%;
    padding: 1%;

    .addBtn {
      padding: 10px;
      background-color: #0F275E;
      border-radius: 5px;
      color: white;
      cursor: pointer;
    }

    .addBtn:hover {
      background-color: #189aec;
    }
  }

  .BottomArea {
    height: 90%;
    padding: 0 1.2%;

    .custom-table {
      width: 100%;
      border-collapse: collapse;
      background-color: #1a2b3c;
      color: #ffffff;
    }

    .custom-table table {
      width: 100%;
      border: 1px solid #223344;
    }

    .custom-table th,
    .custom-table td {
      padding: 12px;
      text-align: left;
      border-bottom: 1px solid #334455;
    }

    .custom-table th {
      background-color: #2b3d4e;
      font-weight: bold;
    }

    .custom-table tr:hover {
      background-color: #3c4e5f;
    }

    .custom-table button {
      margin-right: 5px;
      padding: 5px 10px;
      background-color: #446688;
      color: #ffffff;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }

    .custom-table button:hover {
      background-color: #557799;
    }

    .custom-table input[type="checkbox"] {
      width: 20px;
      height: 20px;
    }

    .custom-table th,
    .custom-table td {
      border-bottom: 1px solid #223344;
    }

    .custom-table th {
      background-color: #223344;
      font-weight: bold;
    }

    .custom-table tr:nth-child(even) {
      background-color: #111629;
    }

    .custom-table tr:nth-child(odd) {
      background-color: #111629;
    }

    .pagination {
      margin-top: 20px;
      text-align: center;
      color: white;
    }

    .pagination button {
      margin: 0 5px;
      padding: 5px 10px;
      background-color: #446688;
      color: #ffffff;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }

    .pagination button[disabled] {
      background-color: #999999;
      cursor: not-allowed;
    }
  }
}
</style>
