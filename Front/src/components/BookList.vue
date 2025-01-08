<template>
    <div class="book-list">
        <h2>书籍查询</h2>
        
        <!-- 搜索栏 -->
        <div class="search-bar">
            <label for="searchType"></label>
            <select v-model="searchType" id="searchType">
            <option value="id">书号</option>
            <option value="title">书名</option>
            <option value="publisher">出版社</option>
            <option value="authors">作者</option>
            <option value="keywords">关键字</option>
            </select>
            <input 
            type="text" 
            v-model="searchQuery" 
            placeholder="请输入查询内容" 
            />
        </div>
    
        <!-- 查询结果 -->
        <div v-if="filteredBooks.length === 0">未找到相关书籍...</div>
        <div class="book-grid">
            <div class="book" v-for="book in filteredBooks" :key="book.id" >
                <div @click="goToBookDetail(book.id)">
                    <img :src="book.coverImage" alt="" style="width: 150px; height: 200px;" />
                    <h3>{{ book.title }}</h3>
                    <p>作者：{{ book.authors }}</p>
                    <p>关键词：{{ book.keywords }}</p>
                </div>
            <div class="purchase" v-if="isUser">
                <button @click="decreaseQuantity(book)">-</button>
                <span>{{ book.quantity || 0 }}</span>
                <button @click="increaseQuantity(book)">+</button>
            </div>
            </div>
        </div>
    </div>
    </template>

<script>
import { addCart, decreaseCart } from '@/api/books';

export default {
name: 'BookList',
props: {
    books: {
    type: Array,
    required: true,
    },
},
data() {
    return {
    searchType: 'title', // 默认搜索类型为书名
    searchQuery: '',     // 输入的搜索内容
    };
},
computed: {
    filteredBooks() {
    // 如果输入框为空，则显示全部书籍
    if (!this.searchQuery) return this.books;

    return this.books.filter((book) => {
        const query = this.searchQuery.toLowerCase(); // 转换为小写进行比较

        // 根据选中的搜索类型筛选
        switch (this.searchType) {
        case 'id':
            return book.id.toString().includes(query);
        case 'title':
            return book.title.toLowerCase().includes(query);
        case 'publisher':
            return book.publisher.toLowerCase().includes(query);
        case 'authors':
            return book.authors.toLowerCase().includes(query);
        case 'keywords':
            return book.keywords && book.keywords.toLowerCase().includes(query);
        default:
            return false;
        }
    });
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
    goToBookDetail(bookId) {
        this.$router.push({ name: 'BookDetail', params: { id: bookId } });
    },
},
};
</script>

<style scoped>
.book-list {
display: flex;
flex-direction: column;
gap: 1rem;
padding: 1rem;
}

.search-bar {
display: flex;
align-items: center;
justify-content: center;
gap: 1rem;
margin-bottom: 1rem;
}

.search-bar select,
.search-bar input {
padding: 0.5rem;
font-size: 1rem;
border: 1px solid #ccc;
border-radius: 4px;
}

.book-grid {
display: grid;
grid-template-columns: repeat(3, 1fr);
gap: 1rem;
}

.book {
border: 1px solid #ddd;
padding: 1rem;
display: flex;
flex-direction: column;
align-items: center;
}

.purchase {
margin-top: 0.5rem;
display: flex;
align-items: center;
gap: 0.5rem;
}

.purchase button {
padding: 0.3rem 0.6rem;
background-color: #007bff;
color: white;
border: none;
border-radius: 4px;
cursor: pointer;
}

.purchase button:hover {
background-color: #0056b3;
}

.purchase span {
font-size: 1.2rem;
font-weight: bold;
}
</style>
