<template>
  <div>
    <Navbar />
    <div class="order-management">
      <h2>订单管理</h2>
      <table class="order-table">
        <thead>
          <tr>
            <th>订单号</th>
            <th>客户名称</th>
            <th>客户邮箱</th>
            <th>总金额</th>
            <th>发货状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in orders" :key="order.id">
            <td>{{ order.id }}</td>
            <td>{{ order.customer.name }}</td>
            <td>{{ order.customer.email }}</td>
            <td>￥{{ order.totalAmount.toFixed(2) }}</td>
            <td>{{ translateStatus(order.status) }}</td>
            <td>
              <button @click="shipTheOrder(order)" :disabled="order.status !== 'PENDING'">
                安排发货
              </button>
              <button @click="cancelTheOrder(order)" :disabled="order.status === 'SHIPPED'">
                取消订单
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';
import { fetchOrders, cancelOrder, shipOrder } from '@/api/books'; 

export default {
  name: 'OrderManagement',
  components: { Navbar },
  data() {
    return {
      orders: [], // 从后端加载的订单数据
    };
  },
  methods: {
    // 获取订单列表
    async fetchTheOrders() {
      try {
        const response = await fetchOrders();
        console.log('获取订单成功:', response);
        this.orders = response; 
      } catch (error) {
        console.error('获取订单失败:', error);
      }
    },
    // 翻译订单状态
    translateStatus(status) {
      const statusMap = {
        PENDING: '待发货',
        SHIPPED: '已发货',
        CANCELED: '已取消',
        COMPLETED: '已完成',
      };
      return statusMap[status] || '未知状态';
    },
    // 安排发货
    async shipTheOrder(order) {
      if (order.status === 'PENDING') {
        try {
          await shipOrder(order.id);
          order.status = 'SHIPPED';
          alert('已安排发货');
        } catch (error) {
          console.error('发货失败:', error);
          alert('发货失败，请稍后重试');
        }
      }
    },
    // 取消订单
    async cancelTheOrder(order) {
      if (order.status !== 'SHIPPED') {
        try {
          await cancelOrder(order.id);
          order.status = 'CANCELED';
          alert('订单已取消');
        } catch (error) {
          console.error('取消订单失败:', error);
          alert('取消订单失败，请稍后重试');
        }
      } else {
        alert('无法取消已发货的订单');
      }
    },
  },
  async mounted() {
    await this.fetchTheOrders();
  },
};
</script>

  
<style scoped>
.order-management {
  max-width: 900px;
  margin: 0 auto;
  padding: 2rem;
  border: 1px solid #ddd;
  border-radius: 4px;
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

button {
  padding: 0.5rem 1rem;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 0.5rem;
}

button:hover {
  background-color: #45a049;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
