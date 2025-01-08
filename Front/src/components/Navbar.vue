<template>
    <nav class="navbar">
        <h1>网上书店</h1>
        
        <ul v-if="isAdministrator">
            <li>
                <router-link to="/" class="nav-link">首页</router-link>
            </li>
            <li>
                <router-link to="/admin/inventoryManagement" class="nav-link">书目管理</router-link>
            </li>
            <li>
                <router-link to="/admin/userManagement" class="nav-link">用户管理</router-link>
            </li>
            <li>
                <router-link to="/admin/orderManagement" class="nav-link">订单管理</router-link>
            </li>
            <li>
                <router-link to="/admin/supplierManagement" class="nav-link">供应商管理</router-link>
            </li>
            <li>
                <router-link to="/admin/purchaseManagement" class="nav-link">采购管理</router-link>
            </li>
            </ul>
            
            <ul v-else>
            <li>
                <router-link to="/" class="nav-link">首页</router-link>
            </li>
            <li>
                <router-link to="/shoppingCart" class="nav-link">购物车</router-link>
            </li>
            <li>
                <router-link to="/orders" class="nav-link">我的订单</router-link>
            </li>
            <li>
                <router-link to="/profile" class="nav-link">用户信息</router-link>
            </li>
            <li>
                <router-link to="/contact" class="nav-link">联系我们</router-link>
            </li>
        </ul>
        
        <!-- 用户信息和退出登录 -->
        <div class="user-info" v-if="isLoggedIn">
            <span>欢迎, {{ userName }}</span>
            <button @click="logout">退出登录</button>
        </div>
        <div v-else>
            <router-link to="/login" class="login-link">登录</router-link>
        </div>
    </nav>
</template>

<script>
export default {
    name: 'Navbar',
    data() {
        return {
            isLoggedIn: !!localStorage.getItem('authToken'), // 根据本地存储判断登录状态
            userName: '张三', 
            userId: 2
        };
    },
    computed: {
        isAdministrator() {
        // 用户ID为1的是管理员
        var user = JSON.parse(localStorage.getItem('user'));
        console.log(user);
        this.userId = user.id;
        this.userName = user.name;
        return this.userId === 1;
    },
    },
    methods: {
        logout() {
            localStorage.removeItem('authToken');
            localStorage.removeItem('user');
            this.isLoggedIn = false;
            alert('已退出登录');
            
            this.$router.push('/login');
        },
    },
    watch: {
        // 监听本地存储变化（如登录状态改变时重新渲染）
        isLoggedIn(newValue) {
            if (!newValue) {
                this.userName = ''; // 清空用户信息
            }
        },
    },
    created() {
    if (!this.isLoggedIn) {
        this.$router.push('/login');
    }
    },
};
</script>

<style scoped>
.navbar {
min-width: 1000px;
display: flex;
justify-content: space-between;
align-items: center;
padding: 1rem 2rem;
background-color: #4caf50;
color: white;
}

.navbar ul {
list-style: none;
display: flex;
gap: 1.5rem;
}

.navbar ul li .nav-link {
text-decoration: none;
color: white;
font-weight: bold;
}

.navbar ul li .nav-link:hover {
text-decoration: underline;
}

.user-info {
display: flex;
align-items: center;
gap: 1rem;
}

.user-info span {
font-weight: bold;
}

.user-info button {
background-color: #f44336;
color: white;
border: none;
padding: 0.5rem 1rem;
border-radius: 5px;
cursor: pointer;
}

.user-info button:hover {
background-color: #d32f2f;
}

.login-link {
text-decoration: none;
color: white;
font-weight: bold;
background-color: #2196f3;
padding: 0.5rem 1rem;
border-radius: 5px;
}

.login-link:hover {
background-color: #1976d2;
}
</style>
