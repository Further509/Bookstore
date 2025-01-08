<template>
  <div>
    <Navbar />
    <div class="purchase-management">
      <h2>采购管理</h2>
      <button @click="checkAndGenerateRecords" style="margin: 0 0 2rem 0;">检查库存并生成采购单</button>
      <!-- 采购单管理 -->
      <div class="purchase-order">
        
        <table>
          <thead>
            <tr>
              <th>采购单号</th>
              <th>书名</th>
              <th>已有数量</th>
              <th>补充数量</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="order in purchaseOrders" :key="order.id">
              <td>{{ order.id }}</td>
              <td>
                <ul>
                  <li v-for="book in order.books" :key="book.id">{{ book.title }}</li>
                </ul>
              </td>
              <td>
                <ul>
                  <li v-for="book in order.books" :key="book.id">{{ book.stockQuantity }}</li>
                </ul>
              </td>
              <td>
                <ul>
                  <li v-for="book in order.books" :key="book.id">{{ book.minStockQuantity - book.stockQuantity }}</li>
                </ul>
              </td>
              <td>
                <button @click="updateOrder(order)">更新库存</button>
                <button @click="deleteOrder(order.id)" class="delete">删除订单</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';
import {
  checkStockAndGenerateOutOfStockRecords,
  generatePurchaseOrderFromOutOfStockRecords,
  fetchAllPurchaseOrders,
  receivePurchaseOrder,
  deletePurchaseOrder
} from '@/api/books'; 

export default {
  name: 'PurchaseManagement',
  components: {
    Navbar,
  },
  data() {
    return {
      purchaseOrders: [], // 采购单列表
    };
  },
  methods: {
    // 检查库存并生成缺书记录
    async checkAndGenerateRecords() {
      try {
        await checkStockAndGenerateOutOfStockRecords();
        await this.generatePurchaseOrder(); // 生成采购单
      } catch (error) {
        console.error('库存检查失败:', error);
        alert('库存检查失败，请稍后重试！');
      }
    },
    // 根据缺书记录生成采购单
    async generatePurchaseOrder() {
      try {
        const newOrder = await generatePurchaseOrderFromOutOfStockRecords();
        this.purchaseOrders.push(newOrder);
      } catch (error) {
        console.error('生成采购单失败:', error);
        alert('生成采购单失败，请稍后重试！');
      }
    },
    // 获取所有采购订单
    async fetchPurchaseOrders() {
      try {
        this.purchaseOrders = await fetchAllPurchaseOrders();
      } catch (error) {
        console.error('获取采购订单失败:', error);
        alert('获取采购订单失败，请稍后重试！');
      }
    },
    // 更新库存并确认收货
    async updateOrder(order) {
      try {
        await receivePurchaseOrder(order.id);
        alert('库存已更新，订单已确认！');
        await this.fetchPurchaseOrders(); // 刷新采购订单列表
      } catch (error) {
        console.error('更新库存失败:', error);
        alert('更新库存失败，请稍后重试！');
      }
    },
    // 删除采购订单
    async deleteOrder(orderId) {
      try {
        await deletePurchaseOrder(orderId);
        this.purchaseOrders = this.purchaseOrders.filter(order => order.id !== orderId);
        alert('采购订单已删除！');
      } catch (error) {
        console.error('删除采购订单失败:', error);
        alert('删除采购订单失败，请稍后重试！');
      }
    },
  },
  async mounted() {
    await this.fetchPurchaseOrders();
  },
};
</script>

<style scoped>
.purchase-management {
  min-width: 400px;
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.book-shortage,
.purchase-order {
  margin-bottom: 2rem;
}

.book-shortage h3,
.purchase-order h3 {
  color: #4caf50;
  margin-bottom: 1rem;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 8px 12px;
  border: 1px solid #ddd;
}

.purchase-order {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
}

.purchase-order th,
.purchase-order td {
  border: 1px solid #ddd;
  padding: 0.75rem;
  text-align: center;
}

.purchase-order th {
  background-color: #4caf50;
  color: white;
}

ul {
  padding-left: 16px;
  list-style-type: disc;
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
</style>
