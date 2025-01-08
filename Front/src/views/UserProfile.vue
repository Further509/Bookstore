<template>
    <div class="user-profile">
        <h2>用户信息</h2>
    
        <!-- 返回主页按钮 -->
        <button class="back-home" @click="goToHome">返回主页</button>
    
        <div v-if="user">
            <div class="profile-item">
            <label>网上ID：</label>
            <span>{{ user.id }}</span>
            </div>
            <div class="profile-item">
            <label>用户名：</label>
            <span>{{ user.name }}</span>
            </div>
            <div class="profile-item">
            <label>电子邮箱：</label>
            <span>{{ user.email }}</span>
            </div>
            <div class="profile-item">
            <label>地址：</label>
            <span>{{ user.address }}</span>
            </div>
            <div class="profile-item">
            <label>账户余额：</label>
            <span>{{ user.accountBalance }}元</span>
            </div>
            <div class="profile-item">
            <label>信用等级：</label>
            <span>{{ user.creditLevel }}</span>
            </div>
    
            <!-- 横向排布的按钮 -->
            <div class="button-container">
            <button @click="openEditProfileModal">编辑信息</button>
            <button @click="openRechargeModal">充值</button>
            <button @click="editPassword">修改密码</button>
            </div>
        </div>
    
        <div v-else class="loading">正在加载用户信息...</div>
    
        <!-- 编辑信息 Modal -->
        <div v-if="showEditProfileModal" class="modal">
            <div class="modal-content">
            <h3>编辑用户信息</h3>
            <div class="form-item">
                <label>用户名：</label>
                <input type="text" v-model="editableUser.name" />
            </div>
            <div class="form-item">
                <label>电子邮箱：</label>
                <input type="email" v-model="editableUser.email" />
            </div>
            <div class="form-item">
                <label>地址：</label>
                <input type="text" v-model="editableUser.address" />
            </div>
            <div style="display: flex; justify-content: space-around;">
                <button @click="saveProfile">保存</button>
                <button @click="closeEditProfileModal">取消</button>
            </div>
            </div>
        </div>
    
        <!-- 充值 Modal -->
        <div v-if="showRechargeModal" class="modal">
            <div class="modal-content">
            <h3>账户充值</h3>
            <div class="form-item">
                <label>充值金额：</label>
                <input type="number" v-model.number="rechargeAmount" min="1" />
            </div>
            <div style="display: flex; justify-content: space-around;">
                <button @click="submitRecharge">充值</button>
                <button @click="closeRechargeModal">取消</button>
            </div>
            </div>
        </div>
    </div>
</template>

<script>
import { getProfile, updateProfile, updateBalance } from "@/api/books";

export default {
name: "UserProfile",
data() {
    return {
    user: null, // 用户信息对象
    editableUser: {}, // 可编辑用户对象
    rechargeAmount: 0, // 充值金额
    showEditProfileModal: false, // 控制编辑信息弹窗显示
    showRechargeModal: false, // 控制充值弹窗显示
    };
},
methods: {
    async fetchUserData() {
    const userId = JSON.parse(localStorage.getItem("user")).id;
    this.user = await getProfile(userId);
    },
    openEditProfileModal() {
    this.editableUser = { ...this.user }; // 复制当前用户信息
    this.showEditProfileModal = true;
    },
    closeEditProfileModal() {
    this.showEditProfileModal = false;
    },
    async saveProfile() {
    try {
        const userId = JSON.parse(localStorage.getItem("user")).id;
        await updateProfile(userId, this.editableUser);
        this.user = { ...this.editableUser }; // 更新当前用户信息
        this.closeEditProfileModal();
        await this.fetchUserData();
        alert("用户信息已更新");
    } catch (error) {
        console.error("更新用户信息失败", error);
        alert("更新失败，请重试");
    }
    },
    openRechargeModal() {
    this.rechargeAmount = 0;
    this.showRechargeModal = true;
    },
    closeRechargeModal() {
    this.showRechargeModal = false;
    },
    async submitRecharge() {
    try {
        const userId = this.user.id;
        await updateBalance( userId, this.rechargeAmount);
        this.user.accountBalance += this.rechargeAmount; // 更新余额
        this.closeRechargeModal();
        await this.fetchUserData();
        alert("充值成功");
    } catch (error) {
        console.error("充值失败", error);
        alert("充值失败，请重试");
    }
    },
    editPassword() {
    alert("修改密码功能尚未实现");
    },
    goToHome() {
    this.$router.push("/");
    },
},
async mounted() {
    await this.fetchUserData();
},
};
</script>

<style scoped>
.user-profile {
min-width: 400px;
max-width: 600px;
margin: 2rem auto;
padding: 2rem;
border: 1px solid #ddd;
border-radius: 8px;
box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
background-color: #f9f9f9;
}

h2 {
text-align: center;
margin-bottom: 1.5rem;
}

.back-home {
display: block;
margin: 0 auto 1.5rem;
padding: 0.5rem 1rem;
font-size: 1rem;
color: white;
background-color: #4caf50;
border: none;
border-radius: 4px;
cursor: pointer;
transition: background-color 0.3s;
}

.back-home:hover {
background-color: #45a049;
}

.profile-item {
display: flex;
justify-content: space-between;
padding: 0.5rem 0;
border-bottom: 1px solid #ddd;
}

.profile-item label {
font-weight: bold;
color: #555;
}

/* 横向按钮布局 */
.button-container {
display: flex;
justify-content: space-around;
margin-top: 1.5rem;
}

button {
padding: 0.5rem 1rem;
font-size: 1rem;
color: white;
background-color: #4caf50;
border: none;
border-radius: 4px;
cursor: pointer;
transition: background-color 0.3s;
}

button:hover {
background-color: #45a049;
}

.loading {
text-align: center;
color: #777;
}

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
</style>
