<template>
  <div>
    <Navbar/>
    <div class="user-management">
      <h2>用户管理</h2>
  
      <!-- 用户列表 -->
      <table class="user-table">
        <thead>
          <tr>
            <th>用户名</th>
            <th>地址</th>
            <th>账户余额</th>
            <th>信用等级</th>
            <th colspan="2">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in users" :key="user.id">
            <td>{{ user.name }}</td>
            <td>{{ user.address }}</td>
            <td>￥{{ user.accountBalance }}</td>
            <td>{{ user.creditLevel }}</td>
            <td>
              <button @click="editUser(user)">编辑</button>
            </td>
            <td>
              <button @click="deleteUser(user)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 编辑用户信息的 Modal -->
    <div v-if="showEditModal" class="modal">
      <div class="modal-content">
        <h3>编辑用户信息</h3>
        <div class="form-item">
          <label>用户名：</label>
          <input type="text" v-model="editableUser.name" />
        </div>
        <div class="form-item">
          <label>地址：</label>
          <input type="text" v-model="editableUser.address" />
        </div>
        <div style="display: flex; justify-content: space-around;">
          <button @click="saveUser">保存</button>
          <button @click="closeEditModal">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';
import { fetchCustomers, deleteUser, updateProfile } from '@/api/books';

export default {
  name: 'UserManagement',
  components: {
    Navbar,
  },
  data() {
    return {
      users: [
        {
          id: '1',
          name: '张三',
          address: '北京市朝阳区',
          accountBalance: 1000.00,
          creditLevel: 'POOR',
        },
        {
          id: '2',
          name: '李四',
          address: '上海市浦东新区',
          accountBalance: 1500.00,
          creditLevel: 'GOOD',
        },
      ],
      showEditModal: false, // 控制编辑模态框显示
      editableUser: {}, // 存储正在编辑的用户数据
    };
  },
  methods: {
    editUser(user) {
      this.editableUser = { ...user }; // 复制用户数据
      this.showEditModal = true; // 打开编辑用户信息的模态框
    },
    closeEditModal() {
      this.showEditModal = false; // 关闭模态框
    },
    async saveUser() {
      try {
        await updateProfile(this.editableUser.id, this.editableUser);
        const index = this.users.findIndex(user => user.id === this.editableUser.id);
        if (index !== -1) {
          this.users[index] = { ...this.editableUser }; // 更新列表中的用户数据
        }
        this.closeEditModal(); // 关闭编辑框
      } catch (error) {
        console.error("更新用户信息失败", error);
        alert("更新失败，请重试");
      }
    },
    async deleteUser(user) {
      await deleteUser(user.id);
      this.users = this.users.filter(u => u.id !== user.id); // 删除用户
    },
  },
  async mounted() {
    this.users = await fetchCustomers(); // 获取用户数据
  },
};
</script>

<style scoped>
.user-management {
  min-width: 800px;
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.user-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
}

.user-table th,
.user-table td {
  border: 1px solid #ddd;
  padding: 0.75rem;
  text-align: center;
}

.user-table th {
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
  margin-right: 0.5rem;
}

button:hover {
  background-color: #45a049;
}

/* 编辑用户信息模态框 */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 1.5rem;
  border-radius: 8px;
  min-width: 300px;
  max-width: 500px;
}

.form-item {
  margin-bottom: 1rem;
}

select,
input {
  width: 100%;
  padding: 0.5rem;
  margin-top: 0.25rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

button {
  margin-top: 1rem;
}
</style>
