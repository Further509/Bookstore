<template>
    <div class="contact">
        <h2>联系我们</h2>
        <!-- 返回主页按钮 -->
        <button class="back-home" @click="goToHome">返回主页</button>
        <form @submit.prevent="submitForm">
            <div class="form-group">
            <label for="name">姓名:</label>
            <input type="text" id="name" v-model="formData.name" placeholder="请输入您的姓名" required />
            </div>
    
            <div class="form-group">
            <label for="email">电子邮件:</label>
            <input type="email" id="email" v-model="formData.email" placeholder="请输入您的电子邮件" required />
            </div>
    
            <div class="form-group">
            <label for="message">留言:</label>
            <textarea id="message" v-model="formData.message" rows="5" placeholder="请输入您的留言"></textarea>
            </div>
    
            <button type="submit">提交</button>
        </form>
    
        <p v-if="submitted" class="success-message">您的留言已成功提交！</p>
    </div>
</template>

<script>
export default {
name: 'Contact',
data() {
    return {
    formData: {
        name: '',
        email: '',
        message: '',
    },
    submitted: false,
    };
},
methods: {
    submitForm() {
    console.log('表单数据:', this.formData);
    this.submitted = true;

    // 清空表单
    this.formData.name = '';
    this.formData.email = '';
    this.formData.message = '';
    },
    goToHome() {
    // 返回主页
    this.$router.push("/");
    },
},
};
</script>

<style scoped>
.contact {
padding: 2rem;
max-width: 600px;
min-width: 400px;
margin: 0 auto;
text-align: center;
}

h2 {
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

.form-group {
margin-bottom: 1.5rem;
text-align: left;
}

label {
display: block;
margin-bottom: 0.5rem;
color: #555;
}

input,
textarea {
width: 100%;
padding: 0.75rem;
border: 1px solid #ccc;
border-radius: 5px;
font-size: 1rem;
}

button {
background-color: #4caf50;
color: white;
padding: 0.75rem 1.5rem;
border: none;
border-radius: 5px;
font-size: 1.1rem;
cursor: pointer;
}

button:hover {
background-color: #45a049;
}

.success-message {
margin-top: 1rem;
color: #4caf50;
font-weight: bold;
}
</style>
