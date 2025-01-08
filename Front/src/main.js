import { createApp } from 'vue';
import App from './App.vue';
import './style.css';

// 普通用户页面
import Register from './views/Register.vue';
import Login from './views/Login.vue';
import Home from './views/Home.vue';
import ShoppingCart from './views/ShoppingCart.vue';
import Contact from './views/Contact.vue';
import Orders from './views/Orders.vue';
import UserProfile from './views/UserProfile.vue';
import BookDetail from './views/BookDetail.vue';

// 管理员页面
import UserManagement from './views/UserManagement.vue';
import SupplierManagement from './views/SupplierManagement.vue';
import InventoryManagement from './views/InventoryManagement.vue';
import PurchaseManagement from './views/PurchaseManagement.vue';
import OrderManagement from './views/OrderManagement.vue';
import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    { path: '/', component: Home },
    { path: '/register', component: Register },
    { path: '/login', component: Login },
    { path: '/shoppingCart', component: ShoppingCart },
    { path: '/contact', component: Contact },
    { path: '/orders', component: Orders },
    { path: '/profile', component: UserProfile },
    { path: '/book/:id', name: 'BookDetail', component: BookDetail, },
    { path: '/admin/supplierManagement', component: SupplierManagement },
    { path: '/admin/inventoryManagement', component: InventoryManagement },
    { path: '/admin/purchaseManagement', component: PurchaseManagement},
    { path: '/admin/orderManagement', component: OrderManagement },
    { path: '/admin/userManagement', component: UserManagement }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

const app = createApp(App);
app.use(router);
app.mount('#app');

export default router;