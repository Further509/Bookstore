import axios from 'axios';

const http = axios.create({
    baseURL: 'http://localhost:8080', 
    timeout: 10000,
});

// 添加请求拦截器
http.interceptors.request.use(
    (config) => {
        // 判断请求是否为注册或登录
        if (!config.url.includes('/customers/register') && !config.url.includes('/customers/login')) {
            const token = localStorage.getItem('authToken');
            if (token) {
                config.headers['Authorization'] = token;
            }
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// 注册函数
export const registerUser = async (userData) => {
    try {
        const response = await http.post('/customers/register', userData);
        console.log('User registered successfully:', response.data);
        return response.data; // 假设返回的数据为用户信息
    } catch (error) {
        console.error('Error registering user:', error);
        throw error;
    }
};

// 登录函数
export const loginUser = async (loginData) => {
    try {
        const response = await http.post('/customers/login', loginData);
        console.log('User logged in successfully:', response.data);
        return response.data; // 返回的数据为JWT令牌
    } catch (error) {
        console.error('Error logging in user:', error);
        throw error;
    }
};

// 获取书籍列表函数
export const fetchBooks = async () => {
    try {
        // 发送请求，包含Authorization头部
        const response = await http.get('/api/books');
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Error fetching books:', error);
        throw error;
    }
};

// 获取单个书籍的具体信息
export const fetchBookDetails = async (bookId) => {
    try {
        const response = await http.get(`/api/books/${bookId}`,);
        return response.data;
    } catch (error) {
        console.error('Error fetching book details:', error);
        throw error;
    }
};

// 获取用户列表函数
export const fetchCustomers = async () => {
    try {
        // 发送请求，包含Authorization头部
        const response = await http.get('/customers');
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Error fetching customers:', error);
        throw error;
    }
};

// 增加图书
export const addBook = async (bookData) => {
    try {
        const response = await http.post('/api/books', bookData);
        return response.data;
    } catch (error) {
        console.error('Error add new books:', error);
        throw error;
    }
};

// 获取单个用户信息
export const getProfile = async (id) => {
    try {
        const response = await http.get(`/customers/${id}`);
        return response.data;
    } catch (error) {
        console.error('Error get proflie:', error);
        throw error;
    }
};

// 充值余额

export const updateBalance = async (id, amount) => {
    try {
        const response = await http.put(`/customers/${id}/updateBalance?amount=${amount}`, null);
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Error update Balance:', error);
        throw error;
    }
};

// 修改用户信息

export const updateProfile = async (id, userData) => {
    try {
        const response = await http.put(`/customers/${id}`, userData);
        return response.data;
    } catch (error) {
        console.error('Error update profile:', error);
        throw error;
    }
};

// 删除用户

export const deleteUser = async (id) => {
    try {
        const response = await http.delete(`/customers/${id}`);
        return response;
    } catch (error) {
        console.error('Error delete the user:', error);
        throw error;
    }
};

// 删除图书
export const deleteBook = async (bookId) => {
    try {
        const response = await http.delete(`/api/books/${bookId}`);
        return response.data;
    } catch (error) {
        console.error('Error delete this book:', error);
        throw error;
    }
};

// 更新图书
export const updateBook = async (bookId, bookData) => {
    try {
        const response = await http.put(`/api/books/${bookId}`, bookData);
        return response.data;
    } catch (error) {
        console.error('Error update this book:', error);
        throw error;
    }
};

// 向购物车中添加书
export const addCart = async (customerId, bookId, quantity) => {
    try {
        const response = await http.post(`/api/carts/customer/${customerId}/add?bookId=${bookId}&quantity=${quantity}`,null);
        return response.data;
    } catch (error) {
        console.error('Error add book to the cart:', error);
        throw error;
    }
};

// 获取购物车
export const getCart = async (customerId) => {
    try {
        const response = await http.get(`/api/carts/customer/${customerId}`);
        return response.data;
    } catch (error) {
        console.error('Error get this cart:', error);
        throw error;
    }
};

// 减少购物车中图书的数量
export const decreaseCart = async (customerId, bookId, quantity) => {
    try {
        const response = await http.post(`/api/carts/customer/${customerId}/reduce?bookId=${bookId}&quantity=${quantity}`, null);
        return response.data;
    } catch (error) {
        console.error('Error decrease book in the cart:', error);
        throw error;
    }
};

// 删除购物车中某个图书
export const deleteBookInCart = async (customerId, bookId) => {
    try {
        const response = await http.delete(`/api/carts/customer/${customerId}/delete?bookId=${bookId}`);
        return response.data;
    } catch (error) {
        console.error('Error delete book in the cart:', error);
        throw error;
    }
};

// 结算
export const checkoutInCart = async (customerId) => {
    try {
        const response = await http.post(`/api/carts/customer/${customerId}/checkout`,null);
        return response.data;
    } catch (error) {
        console.error('Error checkout cart:', error);
        throw error;
    }
};

// 获取所有订单
export const fetchOrders = async () => {
    try {
        const response = await http.get(`/api/orders/admin/all`);
        return response.data;
    } catch (error) {
        console.error('Error fetch all orders:', error);
        throw error;
    }
};

// 获取单个用户所有订单
export const fetchCustomerOrder = async (customerId) => {
    try {
        const response = await http.get(`/api/orders/customer/${customerId}`);
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Error fetch orders:', error);
        throw error;
    }
};

// 取消订单
export const cancelOrder = async (orderId) => {
    try {
        const response = await http.delete(`/api/orders/${orderId}/cancel`);
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Error delete the order:', error);
        throw error;
    }
};

// 发货订单
export const shipOrder = async (order_Id) => {
    try {
        const response = await http.put(`/api/orders/${order_Id}/ship`);
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Error ship the order:', error);
        throw error;
    }
};

// 获取供应商列表
export const fetchSuppliers = async () => {
    try {
        const response = await http.get('/suppliers');
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Error fetching suppliers:', error);
        throw error;
    }
};

// 创建供应商
export const createSupplier = async (supplier) => {
    try {
        const response = await http.post('/suppliers', supplier);
        console.log('Supplier created:', response.data);
        return response.data;
    } catch (error) {
        console.error('Error creating supplier:', error);
        throw error;
    }
};

// 更新供应商
export const updateSupplier = async (id, supplier) => {
    try {
        const response = await http.put(`/suppliers/${id}`, supplier);
        console.log('Supplier updated:', response.data);
        return response.data;
    } catch (error) {
        console.error(`Error updating supplier with ID ${id}:`, error);
        throw error;
    }
};

// 删除供应商
export const deleteSupplier = async (id) => {
    try {
        await http.delete(`/suppliers/${id}`);
        console.log(`Supplier with ID ${id} deleted`);
    } catch (error) {
        console.error(`Error deleting supplier with ID ${id}:`, error);
        throw error;
    }
};

// 添加书目信息
export const addBookToSupplier = async (id, bookId) => {
    try {
        const response = await http.post(`/suppliers/${id}/catalog`, null, {
            params: { bookId }
        });
        console.log(`Book with ID ${bookId} added to supplier with ID ${id}:`, response.data);
        return response.data;
    } catch (error) {
        console.error(`Error adding book with ID ${bookId} to supplier with ID ${id}:`, error);
        throw error;
    }
};

// 删除书目信息
export const removeBookFromSupplier = async (id, bookId) => {
    try {
        const response = await http.delete(`/suppliers/${id}/catalog`, {
            params: { bookId }
        });
        console.log(`Book with ID ${bookId} removed from supplier with ID ${id}:`, response.data);
        return response.data;
    } catch (error) {
        console.error(`Error removing book with ID ${bookId} from supplier with ID ${id}:`, error);
        throw error;
    }
};

// 获取供应商供应的书籍 ID 列表
export const fetchSupplierBooks = async (id) => {
    try {
        const response = await http.get(`/suppliers/${id}/books`);
        console.log(`Books supplied by supplier with ID ${id}:`, response.data);
        return response.data;
    } catch (error) {
        console.error(`Error fetching books supplied by supplier with ID ${id}:`, error);
        throw error;
    }
};

// 更新采购单到货
export const receivePurchaseOrder = async (id) => {
    try {
        const response = await http.put(`/purchase-orders/${id}/receive`);
        console.log(`Purchase order with ID ${id} marked as received.`);
        return response.data;
    } catch (error) {
        console.error(`Error marking purchase order with ID ${id} as received:`, error);
        throw error;
    }
};

// 获取所有采购订单
export const fetchAllPurchaseOrders = async () => {
    try {
        const response = await http.get('/purchase-orders');
        console.log('All purchase orders fetched:', response.data);
        return response.data;
    } catch (error) {
        console.error('Error fetching all purchase orders:', error);
        throw error;
    }
};

// 创建采购订单
export const createPurchaseOrder = async (purchaseOrder) => {
    try {
        const response = await http.post('/purchase-orders', purchaseOrder);
        console.log('Purchase order created:', response.data);
        return response.data;
    } catch (error) {
        console.error('Error creating purchase order:', error);
        throw error;
    }
};

// 更新采购订单
export const updatePurchaseOrder = async (id, purchaseOrder) => {
    try {
        const response = await http.put(`/purchase-orders/${id}`, purchaseOrder);
        console.log(`Purchase order with ID ${id} updated:`, response.data);
        return response.data;
    } catch (error) {
        console.error(`Error updating purchase order with ID ${id}:`, error);
        throw error;
    }
};

// 删除采购订单
export const deletePurchaseOrder = async (id) => {
    try {
        const response = await http.delete(`/purchase-orders/${id}`);
        console.log(`Purchase order with ID ${id} deleted.`);
        return response.data;
    } catch (error) {
        console.error(`Error deleting purchase order with ID ${id}:`, error);
        throw error;
    }
};

// 检查库存并生成缺书记录
export const checkStockAndGenerateOutOfStockRecords = async () => {
    try {
        const response = await http.post('/api/out-of-stock/check');
        console.log('Stock checked and out-of-stock records generated.');
        return response.data;
    } catch (error) {
        console.error('Error checking stock and generating out-of-stock records:', error);
        throw error;
    }
};

// 根据缺书记录生成采购单
export const generatePurchaseOrderFromOutOfStockRecords = async () => {
    try {
        const response = await http.post('/api/out-of-stock/generate-purchase-order');
        console.log('Purchase order generated from out-of-stock records:', response.data);
        return response.data;
    } catch (error) {
        console.error('Error generating purchase order from out-of-stock records:', error);
        throw error;
    }
};
