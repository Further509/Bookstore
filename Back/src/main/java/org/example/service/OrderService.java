package org.example.service;

import org.example.model.*;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository; // 这里添加对 OrderItemRepository 的依赖

    @Autowired
    private BookRepository bookRepository;

    public Order createOrderFromCart(Long customerId) {
        // 获取客户信息
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // 获取购物车和订单项
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> {
                    Book book = bookRepository.findById(cartItem.getBookId())
                            .orElseThrow(() -> new RuntimeException("Book not found"));

                    if (book.getStockQuantity() < cartItem.getQuantity()) {
                        throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
                    }

                    book.setStockQuantity(book.getStockQuantity() - cartItem.getQuantity());
                    bookRepository.save(book);

                    return new OrderItem(cartItem.getBookId(), cartItem.getQuantity(), book.getPrice());
                })
                .collect(Collectors.toList());

        // 计算总金额并应用折扣
        double totalAmount = orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        double discountedAmount = totalAmount * (1 - customer.getCreditLevel().getDiscount());

        // 检查是否可以支付
        if (!customer.canWithdraw(discountedAmount)) {
            throw new RuntimeException("Insufficient balance or overdraft limit reached.");
        }

        // 扣除余额或记录透支
        customer.setAccountBalance(customer.getAccountBalance() - discountedAmount);
        customerRepository.save(customer);

        // 创建订单
        Order order = new Order(customer, orderItems, discountedAmount);
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        // 清空购物车
        cart.getItems().clear();
        cartRepository.save(cart);

        return order;
    }
    public Order getOrderById(Long orderId) {
        logger.info("Fetching order with ID: {}", orderId);
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    public List<Order> getAllOrdersByCustomer(Long customerId) {
        logger.info("Fetching all orders for customer ID: {}", customerId);
        return orderRepository.findByCustomerId(customerId);
    }

    public Order updateOrder(Long orderId, Order updatedOrder) {
        logger.info("Updating order with ID: {}", orderId);

        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // 更新订单的状态和其他信息
        existingOrder.setStatus(updatedOrder.getStatus());
        logger.info("Order status updated to: {}", updatedOrder.getStatus());

        // 如果需要更新订单的总金额或其他字段，可以根据具体业务需求处理
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long orderId) {
        logger.info("Deleting order with ID: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        orderRepository.delete(order);
        logger.info("Order with ID: {} successfully deleted", orderId);
    }

    // 用户取消订单
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Only pending orders can be canceled.");
        }

        // 返还用户余额
        Customer customer = order.getCustomer();
        double refundedAmount = order.getTotalAmount();
        customer.setAccountBalance(customer.getAccountBalance() + refundedAmount);
        customerRepository.save(customer);

        // 返还书籍库存
        List<OrderItem> orderItems = order.getItems();
        for (OrderItem item : orderItems) {
            Book book = bookRepository.findById(item.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found with ID: " + item.getBookId()));
            book.setStockQuantity(book.getStockQuantity() + item.getQuantity());
            bookRepository.save(book);
        }

        // 删除订单及关联的订单项
        orderItemRepository.deleteAll(orderItems);
        orderRepository.delete(order);
    }

    // 管理员查看所有订单
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 管理员安排订单发货
    public Order shipOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Only pending orders can be shipped.");
        }

        // 设置订单状态为 SHIPPED
        order.setStatus("SHIPPED");
        return orderRepository.save(order);
    }
}
