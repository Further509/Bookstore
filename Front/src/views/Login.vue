<template>
    <div class="login">
        <h2>用户登录</h2>
        <form @submit.prevent="login">
            <div class="form-group">
                <label for="email">邮箱</label>
                <input v-model="email" type="email" id="email" required />
            </div>
            <div class="form-group">
                <label for="password">密码</label>
                <input v-model="password" type="password" id="password" required />
            </div>
            <button type="submit">登录</button>
        </form>
        <p>没有账号？<router-link to="/register">去注册</router-link></p>
    </div>
</template>

<script>
import { loginUser } from '@/api/books'; 

export default {
    name: 'Login',
    data() {
        return {
            email: '',
            password: '',
        };
    },
    methods: {
        async login() {
            try {
                const loginData = {
                    email: this.email,
                    password: this.password,
                };
                const response = await loginUser(loginData);
                
                console.log('登录成功:', response);
                localStorage.setItem('authToken', response.token);
                localStorage.setItem('user', JSON.stringify(response.user));
                this.$router.push('/'); // 跳转到主页
                
            } catch (error) {
                console.error('登录失败:', error);
                alert('登录失败，请检查邮箱和密码');
            }
        },
    },
};
</script>

<style scoped>
.login {
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
