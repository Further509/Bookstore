<template>
    <div>
        <Navbar />
        <div class="shopping-cart">
            <h2>购物车</h2>
            <div v-if="cartItems.length === 0">您的购物车是空的。</div>
            <div v-else class="cart-items">
            <div class="cart-item" v-for="(item, index) in cartItems" :key="index">
                <img :src="item.book.coverImage" alt="" class="book-cover" />
                <div class="book-details">
                <h3>{{ item.book.title }}</h3>
                <p>作者：{{ item.book.authors }}</p>
                <p>价格：{{ item.book.price }}元</p>
                </div>
                <div class="quantity-actions">
                <button @click="decreaseQuantity(index)">-</button>
                <input type="number" v-model.number="item.quantity" @blur="updateQuantity(index)" />
                <button @click="increaseQuantity(index)">+</button>
                </div>
                <button @click="removeFromCart(index)">删除</button>
            </div>
            </div>
            <div class="total-price">总计：{{ totalPrice }}元</div>
            <button @click="checkout">结算</button>
        </div>    
    </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import { getCart, fetchBookDetails, addCart, decreaseCart, deleteBookInCart, checkoutInCart} from "@/api/books";


export default {
name: "ShoppingCart",
components: {
    Navbar,
},
data() {
    return {
    cartItems: [], 
    };
},
computed: {
    totalPrice() {
    return this.cartItems.reduce(
        (total, item) => total + item.book.price * item.quantity,
        0
    );
    },
},
methods: {
    async fetchCartItems() {
    try {
        // 获取当前用户 ID
        const userId = JSON.parse(localStorage.getItem('user')).id;
        // 从后端获取购物车数据
        const response = await getCart(userId);
        const cartItems = response.items; // 每个 item 包含 bookId 和 quantity

        // 遍历购物车中的每个 bookId，获取对应书籍详情
        const cartDetails = await Promise.all(
        cartItems.map(async (item) => {
            const bookResponse = await fetchBookDetails(item.bookId); // 获取书籍详情
            return {
            book: bookResponse, // 包含 title, authors, price 等信息
            quantity: item.quantity, // 保留数量信息
            };
        })
        );

        // 更新购物车数据
        this.cartItems = cartDetails;
    } catch (error) {
        console.error("加载购物车数据失败", error);
    }
    },
    // 增加数量
    async increaseQuantity(index) {
    const item = this.cartItems[index];
    item.quantity++;
    try {
        const userId = JSON.parse(localStorage.getItem('user')).id;
        await addCart(userId, item.book.id, 1);
    } catch (error) {
        console.error("更新数量失败", error);
    }
    },
    // 减少数量
    async decreaseQuantity(index) {
    const item = this.cartItems[index];
    if (item.quantity >= 1) {
        item.quantity--;
        try {
            const userId = JSON.parse(localStorage.getItem('user')).id;
            await decreaseCart(userId, item.book.id, 1)
        } catch (error) {
        console.error("更新数量失败", error);
        }
    }
    },
    // 删除
    async removeFromCart(index) {
    const item = this.cartItems[index];
    this.cartItems.splice(index, 1);
    try {
        const userId = JSON.parse(localStorage.getItem('user')).id;
        await deleteBookInCart(userId, item.book.id)
    } catch (error) {
        console.error("删除商品失败", error);
    }
    },
    async checkout() {
    try {
        // 结算需要转为订单
        const userId = JSON.parse(localStorage.getItem('user')).id;
        await checkoutInCart(userId)
        this.cartItems = [];
    } catch (error) {
        console.error("结算失败", error);
    }
    },
},
mounted() {
    this.fetchCartItems(); // 页面加载时获取购物车数据
},
};
</script>


<style scoped>
.shopping-cart {
max-width: 600px;
margin: auto;
padding: 20px;
border: 1px solid #ddd;
border-radius: 4px;
}

.cart-items {
display: flex;
flex-direction: column;
gap: 1rem;
}

.cart-item {
display: flex;
align-items: center;
gap: 1rem;
padding: 1rem;
border: 1px solid #eee;
}

.book-cover {
width: 100px;
height: 150px;
object-fit: cover;
}

.book-details {
flex-grow: 1;
}

.quantity-actions {
display: flex;
align-items: center; /* 垂直居中对齐 */
gap: 0.5rem; /* 为按钮和输入框之间添加间距 */
}

.quantity-actions button {
padding: 0.3rem 0.6rem;
background-color: #007bff;
color: white;
border: none;
border-radius: 4px;
cursor: pointer;
margin-right: 0.5rem; /* 为按钮添加右边距 */
}

.quantity-actions button:hover {
background-color: #0056b3;
}

input[type="number"] {
padding: 0.5rem;
border: 1px solid #ccc;
border-radius: 4px;
width: 50px; /* 限制输入框的宽度 */
text-align: center; /* 使数字居中显示 */
}

.total-price {
margin-top: 1rem;
font-weight: bold;
}

button {
padding: 0.5rem 1rem;
background-color: #28a745;
color: white;
border: none;
border-radius: 4px;
cursor: pointer;
}

button:hover {
background-color: #1e7e34;
}
</style>