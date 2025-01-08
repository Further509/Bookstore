<template>
  <div>
    <Navbar />
    <div class="supplier-management">
      <h2>供应商管理</h2>
      <button class="primary-btn" @click="showAddSupplierModal = true">新增供应商</button>

      <!-- 供应商表格 -->
      <table class="supplier-table">
        <thead>
          <tr>
            <th>供应商名称</th>
            <th>联系人信息</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="supplier in suppliers" :key="supplier.id">
            <td>{{ supplier.name }}</td>
            <td>{{ supplier.contactInfo }}</td>
            <td>
              <button class="action-btn" @click="selectSupplier(supplier)">查看详情</button>
              <button class="action-btn" @click="openEditModal(supplier)">修改</button>
            </td>
          </tr>
        </tbody>
      </table>

      <!-- 查看详情模态框 -->
      <div v-if="showDetailsModal" class="modal-overlay modal" @click.self="closeDetailsModal">
        <div class="modal-content">
          <h3>{{ selectedSupplier.name }} 的书目信息</h3>
          <table>
            <thead>
              <tr>
                <th>书名</th>
                <th>作者</th>
                <th>价格</th>
                <th>库存</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="book in books" :key="book.id">
                <td>{{ book.title }}</td>
                <td>{{ book.authors }}</td>
                <td>{{ book.price }}</td>
                <td>{{ book.stockQuantity }}</td>
                <td>
                  <button @click="removeBook(book.id)">删除</button>
                </td>
              </tr>
            </tbody>
          </table>
          <div style="margin: 2rem 0 1.5rem;">
            <input v-model="newBookId" placeholder="书目 ID" />
            <button @click="addBook">添加书目</button>
          </div>
          <button class="close" @click="closeDetailsModal">&times;</button>
        </div>
      </div>


      <!-- 新增供应商模态框 -->
      <div v-if="showAddSupplierModal" class="modal-overlay" @click.self="cancelAddModal">
        <div class="modal-content">
          <h3>新增供应商</h3>
          <form @submit.prevent="addSupplier">
            <div class="form-group">
              <label for="supplierName">供应商名称：</label>
              <input id="supplierName" type="text" v-model="newSupplier.name" required />

              <label for="contactInfo">联系人信息：</label>
              <input id="contactInfo" type="text" v-model="newSupplier.contactInfo" required />

              <div class="form-buttons">
                <button type="submit">保存</button>
                <button type="button" @click="cancelAddModal">取消</button>
              </div>
            </div>
          </form>
        </div>
      </div>

      <!-- 修改供应商模态框 -->
      <div v-if="showEditModal" class="modal-overlay" @click.self="cancelEditModal">
        <div class="modal-content">
          <h3>修改供应商信息</h3>
          <form @submit.prevent="updateSupplierInfo">
            <div class="form-group">
            <label for="editSupplierName">供应商名称：</label>
            <input id="editSupplierName" type="text" v-model="editSupplierData.name" required />

            <label for="editContactInfo">联系人信息：</label>
            <input id="editContactInfo" type="text" v-model="editSupplierData.contactInfo" required />

            <div class="form-buttons">
              <button type="submit">保存修改</button>
              <button type="button" @click="cancelEditModal">取消</button>
            </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import Navbar from '@/components/Navbar.vue';
import {
  fetchSuppliers,
  createSupplier,
  updateSupplier,
  fetchSupplierBooks,
  addBookToSupplier,
  fetchBookDetails,
  removeBookFromSupplier,
} from "../api/books";

export default {
  name: "SupplierManagement",
  components: { Navbar },
  data() {
    return {
      suppliers: [],
      selectedSupplier: null,
      books: [],
      newSupplier: { name: "", contactInfo: "" },
      newBookId: "",
      showAddSupplierModal: false, 
      showEditModal: false,    
      showDetailsModal: false, 
    };
  },
  methods: {
    async fetchSuppliers() {
      try {
        this.suppliers = await fetchSuppliers();
      } catch (error) {
        console.error("Error fetching suppliers:", error);
      }
    },
    async addSupplier() {
      try {
        const createdSupplier = await createSupplier(this.newSupplier);
        this.suppliers.push(createdSupplier);
        this.newSupplier = { name: "", contactInfo: "" };
        this.showAddSupplierModal = false;
      } catch (error) {
        console.error("Error creating supplier:", error);
      }
    },
    openEditModal(supplier) {
      this.editSupplierData = { ...supplier };
      this.showEditModal = true;
    },
    async selectSupplier(supplier) {
    this.selectedSupplier = supplier;
    try {
      const bookIds = await fetchSupplierBooks(supplier.id); // 获取书籍 ID 列表
      const bookDetailsPromises = bookIds.map((id) => fetchBookDetails(id)); // 调用 fetchBookDetails 获取每本书的详细信息
      this.books = await Promise.all(bookDetailsPromises); // 等待所有请求完成并更新书目信息
      console.log(this.books);
      this.showDetailsModal = true; // 显示详情模态框
    } catch (error) {
      console.error(`Error fetching books for supplier ${supplier.id}:`, error);
    }
    },
    closeDetailsModal() {
      this.showDetailsModal = false;
      this.selectedSupplier = null;
      this.books = [];
    },
    async updateSupplierInfo() {
      const index = this.suppliers.findIndex(s => s.id === this.editSupplierData.id);
      if (index !== -1) {
        this.suppliers[index] = { ...this.editSupplierData };
        await updateSupplier(this.editSupplierData.id, this.editSupplierData);
      }
      this.showEditModal = false;
    },
    async addBook() {
      if (!this.newBookId) {
        alert("请输入书目 ID");
        return;
      }
      try {
        const book = await addBookToSupplier(this.selectedSupplier.id, this.newBookId);
      } catch (error) {
        console.error("Error adding book:", error);
      }
    },
    async removeBook(bookId) {
      if (confirm("确定删除此书目吗？")) {
        try {
          await removeBookFromSupplier(this.selectedSupplier.id, bookId);
          this.books = this.books.filter((book) => book.id !== bookId);
        } catch (error) {
          console.error("Error removing book:", error);
        }
      }
    },
    closeModal() {
      this.selectedSupplier = null;
      this.books = [];
    },
    cancelAddModal() {
      this.showAddSupplierModal = false;
      this.newSupplier = { name: "", contactInfo: "" };
    },
    cancelEditModal() {
      this.showEditModal = false;
      this.editSupplierData = { id: null, name: "", contactInfo: "" };
    },
  },
  async mounted() {
    await this.fetchSuppliers();
  },
};
</script>

<style scoped>
.supplier-management {
  max-width: 1200px;
  min-width: 600px;
  margin: 0 auto;
  padding: 2rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.supplier-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 1rem;
}

.supplier-table th,
.supplier-table td {
  border: 1px solid #ddd;
  padding: 0.75rem;
  text-align: center;
}

.supplier-table th {
  background-color: #4caf50;
  color: white;
}

button {
  padding: 0.5rem 1rem;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}

/* 模态框样式 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  transition: opacity 0.3s ease-in-out;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 8px;
  width: 50%;
  max-width: 600px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  position: relative; /* 为了使关闭按钮定位正确 */
  transition: transform 0.3s ease-in-out;
  transform: scale(0.98); /* 初始状态稍微小一点 */
}

.modal-content input {
  width: 40%;
  padding: 0.5rem;
  margin: 0.25rem 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

/* 模态框显示时的动画 */
.modal.show {
  opacity: 1;
}

.modal-content.show {
  transform: scale(1); /* 显示时放大到正常大小 */
}

/* 关闭按钮样式 */
.close {
  position: absolute;
  top: 1rem;
  right: 1rem;
  font-size: 1.5rem;
  cursor: pointer;
  background: none;
  border: none;
  color: #4caf50;
  transition: color 0.3s ease-in-out;
}

.close:hover {
  color: #45a049;
}

/* 按钮样式 */
button {
  padding: 0.5rem 1rem;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s ease-in-out;
  transform: translateY(0); /* 用于实现点击时的下压效果 */
}

button:hover {
  background-color: #45a049;
}

button:active {
  transform: translateY(2px); /* 点击时的下压效果 */
}

/* 表单按钮布局 */
.form-buttons {
  margin-top: 1rem;
  display: flex;
  justify-content: space-around; 
}

/* 模态框动画 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: scale(0.95);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

/* 模态框关闭动画 */
@keyframes fadeOut {
  from {
    opacity: 1;
  }
  to {
    opacity: 0;
  }
}

.primary-btn {
  padding: 0.75rem 1.5rem;
  margin-bottom: 0.5rem;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s, box-shadow 0.3s;
}

.primary-btn:hover {
  background-color: #45a049;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.supplier-table tr:hover {
  background-color: #f9f9f9;
}

.form-group {
  display: flex;
  flex-direction: column; /* 使子元素垂直排列 */
  margin-bottom: 20px; /* 为每个表单组添加一些底部间距 */
}

.form-group label {
  margin-bottom: 5px; /* 为标签和输入框之间添加一些间隔 */
}

.form-group input {
  width: 100%;
    padding: 0.5rem;
    margin-top: 0.25rem;
    margin-bottom: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.action-btn {
  margin: 0 1rem;
}


.modal-content table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 1rem;
}

.modal-content table th,
.modal-content table td {
  border: 1px solid #ddd;
  padding: 0.75rem;
  text-align: center;
}

.modal-content table th {
  background-color: #4caf50;
  color: white;
}
</style>

