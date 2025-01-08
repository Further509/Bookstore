<template>
    <div>
    <Navbar/>
    <div class="inventory-management">
        <h2>库存管理</h2>

        <!-- 书籍目录管理 -->
        <div class="book-directory">
            <button @click="addBook">添加书籍</button>
            <table class="book-table">
                <thead>
                    <tr>
                        <th>书名</th>
                        <th>作者</th>
                        <th>价格</th>
                        <th>库存量</th>
                        <th>存储位置</th>
                        <th>供应商</th>
                        <th>最低库存量</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="book in books" :key="book.id">
                        <td>{{ book.title }}</td>
                        <td>{{ book.authors }}</td>
                        <td>{{ book.price }}</td>
                        <td>{{ book.stockQuantity }}</td>
                        <td>{{ book.storageLocation }}</td>
                        <td>{{ book.publisher }}</td>
                        <td>{{ book.minStockQuantity }}</td>
                        <td>
                            <button @click="editBook(book)">编辑</button>
                            <button @click="removeBook(book.id)">删除</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- Modal 容器 -->
        <div v-if="showModal" class="modal-overlay" @click.self="cancelEdit">
            <div class="modal-content">
                <h3>{{ currentBook.id ? '编辑书籍' : '添加书籍' }}</h3>
                <form @submit.prevent="saveBook">
                    <label for="title">书名：</label>
                    <input type="text" id="title" v-model="currentBook.title" required />

                    <label for="authors">作者：</label>
                    <input type="text" id="authors" v-model="currentBook.authors" required />

                    <label for="coverImage">封面：</label>
                    <input type="text" id="coverImage" v-model="currentBook.coverImage" required />

                    <label for="price">价格：</label>
                    <input type="number" id="price" v-model.number="currentBook.price" required />

                    <label for="stockQuantity">库存量：</label>
                    <input type="number" id="stockQuantity" v-model.number="currentBook.stockQuantity" required />

                    <label for="storageLocation">存储位置：</label>
                    <input type="text" id="storageLocation" v-model="currentBook.storageLocation" required />

                    <label for="publisher">出版社</label>
                    <input type="text" id="publisher" v-model="currentBook.publisher" required />

                    <label for="keywords">关键词</label>
                    <input type="text" id="keywords" v-model="currentBook.keywords" required />

                    <label for="minStockQuantity">最低库存量</label>
                    <input type="text" id="minStockQuantity" v-model="currentBook.minStockQuantity" required />

                    <div class="form-buttons">
                        <button type="submit">保存</button>
                        <button type="button" @click="cancelEdit">取消</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';
import { fetchBooks, addBook, deleteBook, updateBook } from '@/api/books';

export default {
name: 'InventoryManagement',
components: { Navbar },
data() {
    return {
    books: [
        { id: 1, title: '书籍1', coverImage: '', authors: '作者1', price: 100, publisher: [], stockQuantity: 10 ,storageLocation: "", suppliers: "", keywords: "" },
        { id: 2, title: '书籍2', coverImage: '', authors: '作者2', price: 200, publisher: [], stockQuantity: 5 ,storageLocation: "", suppliers: "", keywords: "" },
    ],
    currentBook: null,
    showModal: false, // 控制 Modal 显示
    };
},
methods: {
    addBook() {
    this.currentBook = { title: '', coverImage: '', authors: '', price: 0, stockQuantity: 0, storageLocation: "", suppliers: [], keywords: "" };
    this.showModal = true; // 打开 Modal
    },
    editBook(book) {
    this.currentBook = { ...book };
    this.showModal = true; // 打开 Modal
    },
    async removeBook(bookId) {
    await deleteBook(bookId);
    this.books = this.books.filter(book => book.id !== bookId);
    alert('书籍已删除');
    },
    async saveBook() {
        try {
            console.log(1)
            if (this.currentBook.id) {
            await updateBook(this.currentBook.id, this.currentBook);
            this.books = await fetchBooks();
            } else {
            await addBook(this.currentBook);
            this.books = await fetchBooks();
            }
            this.showModal = false;
            // 清空表单
            this.currentBook = null;
        } catch (error) {
            console.error('Failed to save book:', error);
            alert('保存书籍信息失败');
        }
    },
    cancelEdit() {
    this.showModal = false;
    this.currentBook = null;
    }
},
async mounted() {
    try {
    this.books = await fetchBooks();
    } catch (error) {
    console.error('Failed to load books:', error);
    }
},
};
</script>

<style scoped>
.inventory-management {
    min-width: 600px;
    max-width: 1400px;
    margin: 0 auto;
    padding: 2rem;
    border: 1px solid #ddd;
    border-radius: 4px;
}

.book-directory, .book-form {
    margin-bottom: 2rem;
}

.book-directory h3, .book-form h3 {
    color: #4caf50;
    margin-bottom: 1rem;
}

.book-form label, .book-form input, .book-form button {
    display: block;
    margin-bottom: 1rem;
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

/* Modal 样式 */
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5); /* 半透明背景 */
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000; /* 确保 Modal 在最上层 */
}

.modal-content {
    will-change: transform, opacity;
    background: white;
    padding: 2rem;
    border-radius: 8px;
    width: 600px;
    max-width: 80%;
    max-height: 80vh; /* 设置最大高度为视口高度的80% */
    overflow-y: auto; /* 允许垂直滚动 */
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
    position: relative;
}

.modal-content h3 {
    margin-top: 0;
    margin-bottom: 1rem;
}

.modal-content label {
    font-weight: bold;
}

.modal-content input {
    width: 100%;
    padding: 0.5rem;
    margin-top: 0.25rem;
    margin-bottom: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.form-buttons {
    display: flex;
    justify-content: space-around;
}

.form-buttons button {
    margin-left: 1rem;
}

@media (max-width: 600px) {
    .modal-content {
        width: 90%;
    }
}

.book-table {
width: 100%;
border-collapse: collapse;
margin-top: 1rem;
}

.book-table th,
.book-table td {
border: 1px solid #ddd;
padding: 0.75rem;
text-align: center;
}

.book-table th {
background-color: #4caf50;
color: white;
}
</style>
