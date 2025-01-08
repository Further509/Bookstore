<template>
    <div>
        <Navbar />
        <div class="orders">
            <h2>我的订单</h2>
            <div v-if="orders.length === 0" class="empty-message">
            您还没有购买任何书籍！
            </div>
            <table v-else class="order-table">
            <thead>
                <tr>
                <th>订单号</th>
                <th>书籍ID</th>
                <th>数量</th>
                <th>单价</th>
                <th>小计</th>
                <th>总价</th>
                <th>发货状态</th>
                <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <template v-for="order in orders" :key="order.id">
                <tr>
                    <td :rowspan="order.items.length">{{ order.id }}</td>
                    <td>{{ order.items[0].bookId }}</td>
                    <td>{{ order.items[0].quantity }}</td>
                    <td>￥{{ order.items[0].price.toFixed(2) }}</td>
                    <td>￥{{ (order.items[0].quantity * order.items[0].price).toFixed(2) }}</td>
                    <td :rowspan="order.items.length">￥{{ order.totalAmount.toFixed(2) }}</td>
                    <td :rowspan="order.items.length">{{ translateStatus(order.status) }}</td>
                    <td :rowspan="order.items.length">
                    <button @click="cancelTheOrder(order.id)" v-if="order.status === 'PENDING'">
                        取消订单
                    </button>
                    </td>
                </tr>
                <tr v-for="(item) in order.items.slice(1)" :key="item.id">
                    <td>{{ item.bookId }}</td>
                    <td>{{ item.quantity }}</td>
                    <td>￥{{ item.price.toFixed(2) }}</td>
                    <td>￥{{ (item.quantity * item.price).toFixed(2) }}</td>
                </tr>
                </template>
            </tbody>
            </table>
        </div>
    </div>
</template>


<script>
import Navbar from '@/components/Navbar.vue';
import { fetchCustomerOrder, cancelOrder } from '@/api/books'; 

export default {
    name: 'Orders',
    components: { Navbar },
    data() {
        return {
        orders: [],
        };
    },
    methods: {
        translateStatus(status) {
        const statusMap = {
            PENDING: '待发货',
            SHIPPED: '已发货',
            CANCELED: '已取消',
            COMPLETED: '已完成',
        };
        return statusMap[status] || '未知状态';
        },
        // 获取订单数据
        async fetchOrders() {
        try {
            const userId = JSON.parse(localStorage.getItem("user")).id;
            this.orders = await fetchCustomerOrder(userId);
        } catch (error) {
            console.error("获取订单失败:", error);
        }
        },
        // 取消订单
        async cancelTheOrder(orderId) {
        const order = this.orders.find(o => o.id === orderId);
        if (order.status === 'PENDING') {
            await cancelOrder(orderId);
            this.orders = this.orders.filter(o => o.id !== orderId);
            alert('订单已取消');
        } else {
            alert('此订单无法取消');
        }
        },
        // 计算小计行数
        itemsRowSpan(items) {
        return items.length || 1;
        }
    },
    async mounted() {
        await this.fetchOrders();
    },
};
</script>



<style scoped>
.orders {
padding: 2rem;
max-width: 800px;
margin: 0 auto;
text-align: center;
}

h2 {
color: #4caf50;
margin-bottom: 1.5rem;
}

.empty-message {
font-size: 1.2rem;
color: #888;
}

.order-table {
width: 100%;
border-collapse: collapse;
margin-top: 1rem;
}

.order-table th,
.order-table td {
border: 1px solid #ddd;
padding: 0.75rem;
text-align: center;
}

.order-table th {
background-color: #4caf50;
color: white;
}

.total-label {
font-weight: bold;
text-align: right;
}

.total-value {
font-weight: bold;
color: #4caf50;
}
</style>
