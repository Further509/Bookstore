<template>
    <div class="book-detail" v-if="book">
        <button class="back-home" @click="goToHome">
            返回
        </button>
        <h2>{{ book.title }}</h2>
        <div class="book-content">
            <img class="book-image" :src="book.coverImage" alt="Cover Image" />
            <div class="book-info">
                <p><strong>作者：</strong>{{ book.authors }}</p>
                <p><strong>出版社：</strong>{{ book.publisher }}</p>
                <p><strong>价格：</strong>{{ book.price }}</p>
                <p><strong>关键字：</strong>{{ book.keywords }}</p>
                <p><strong>存储量：</strong>{{ book.stockQuantity }}本</p>
                <div class="purchase" v-if="isUser">
                    <button @click="decreaseQuantity(book)">-</button>
                    <span >{{ book.quantity || 0 }}</span>
                    <button @click="increaseQuantity(book)">+</button>
                </div>
            </div>
            
        </div>
    </div>
    <div v-else>
        <p>书籍详情加载中或书籍不存在。</p>
    </div>
</template>

<script>
import { fetchBookDetails, addCart, decreaseCart } from '@/api/books';

export default {
    name: 'BookDetail',
    data() {
        return {
            book: null,
            loading: false,
            error: false,
        };
    },
    computed: {
        bookId() {
            return this.$route.params.id;
        },
        isUser() {
        // 用户ID为1的是管理员
        var user = JSON.parse(localStorage.getItem('user'));
        this.userId = user.id;
        this.userName = user.name;
        return this.userId != 1;
    },
    },
    methods: {
        goToHome() {
            this.$router.push('/'); // 导航到主页
        },
        async increaseQuantity(book) {
    if (!book.quantity) book.quantity = 0;
    book.quantity++;
    try {
        var userId = JSON.parse(localStorage.getItem('user')).id;
        await addCart(userId, book.id, 1);
    } catch (error) {
        alert('添加购物车失败，请稍后再试！');
    }
    },
    async decreaseQuantity(book) {
    if (book.quantity && book.quantity > 0) {
        book.quantity--;
        try {
        var userId = JSON.parse(localStorage.getItem('user')).id;
        await decreaseCart(userId, book.id, 1);
    } catch (error) {
        alert('减少失败，请稍后再试！');
    }
    }
    },
    },
    async created() {
        try {
            this.book = await fetchBookDetails(this.bookId);
            this.loading = true;
        } catch (error) {
            console.error('Failed to load the book:', error);
        }
    },
};
</script>

<style scoped>
.book-detail {
    min-width: 800px;
    max-width: 1200px;
    min-height: auto;
    margin: auto;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 4px;
}

.book-detail h2 {
    text-align: center;
    margin-bottom: 20px;
}

.back-home {
    display: block;
    margin-bottom: 10px;
    padding: 5px 8px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.back-home:hover {
    background-color: #0056b3;
}

.book-content {
    display: flex;
    gap: 20px;
    align-items: flex-start;
}

.book-image {
    max-width: 200px;
    height: auto;
    border-radius: 4px;
    object-fit: cover;
}

.book-info {
    flex: 1;
    font-size: 16px;
    line-height: 1.6;
}

.book-info p {
    margin: 8px 0;
}

.purchase {
    margin-top: 0.5rem;
    display: flex; /* 使用 Flexbox 布局 */
    align-items: center;
    justify-content: center; /* 居中对齐 */
    gap: 1rem; /* 设置按钮和数字的间隔 */
}

.purchase button {
    padding: 0.5rem 1rem; /* 增加按钮的内边距 */
    background-color: #007bff; /* 设置按钮的背景色 */
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 1rem; /* 增加字体大小 */
    font-weight: bold;
    transition: background-color 0.3s ease; /* 添加过渡效果 */
}

.purchase button:hover {
    background-color: #0056b3; /* 悬停时改变背景色 */
}

.purchase span {
    font-size: 1.5rem; /* 增加数字的字体大小 */
    font-weight: bold;
    text-align: center;
    min-width: 50px; /* 确保数字区域宽度固定 */
    display: inline-block; /* 保持数字宽度固定 */
}

</style>
