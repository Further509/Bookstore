<template>
    <div class="register">
        <h2>用户注册</h2>
        <form @submit.prevent="register">
            <div class="form-group">
                <label for="username">用户名</label>
                <input v-model="username" type="text" id="username" required />
            </div>
            <div class="form-group">
                <label for="email">邮箱</label>
                <input v-model="email" type="email" id="email" required />
            </div>
            <div class="form-group">
                <label for="password">密码</label>
                <input v-model="password" type="password" id="password" required />
            </div>
            <button type="submit">注册</button>
        </form>
        <p>已有账号？<router-link to="/login">去登录</router-link></p>
    </div>
</template>

<script>
import { registerUser } from '@/api/books'; 

export default {
    name: 'Register',
    data() {
        return {
            username: '',
            email: '',
            password: '',
        };
    },
    methods: {
        async register() {
            try {
                // 创建注册数据对象
                const userData = {
                    name: this.username,
                    email: this.email,
                    password: this.password,
                };

                // 调用 registerUser 函数进行注册
                const response = await registerUser(userData);
                
                // 注册成功，跳转到登录页面
                console.log('注册成功:', response);
                alert('注册成功！请登录');
                this.$router.push('/login'); // 注册成功后跳转到登录页面
                
            } catch (error) {
                console.error('注册失败:', error);
                alert('注册失败，请检查输入信息');
            }
        },
    },
};
</script>

<style scoped>
.register {
    min-width: 400px;
    margin: 50px auto;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 5px;
    background: #f9f9f9;
}
.form-group {
    margin-bottom: 15px;
}
label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
}
input {
    width: 100%;
    padding: 8px;
    margin-top: 5px;
    border: 1px solid #ccc;
    border-radius: 4px;
}
button {
    width: 100%;
    padding: 10px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}
button:hover {
    background-color: #0056b3;
}
</style>
