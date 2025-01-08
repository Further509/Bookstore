package org.example.controller;

import org.example.model.Cart;
import org.example.model.Order;
import org.example.service.CartService;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    // 获取指定客户的购物车
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Cart> getCartByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.getCartByCustomerId(customerId));
    }

    // 向购物车中添加书
    @PostMapping("/customer/{customerId}/add")
    public ResponseEntity<Cart> addBookToCart(@PathVariable Long customerId,
                                              @RequestParam Long bookId,
                                              @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.addBookToCart(customerId, bookId, quantity));
    }

    // 减少书的数量
    @PostMapping("/customer/{customerId}/reduce")
    public ResponseEntity<Cart> reduceBookQuantity(@PathVariable Long customerId,
                                                   @RequestParam Long bookId,
                                                   @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.reduceBookQuantity(customerId, bookId, quantity));
    }

    // 删除购物车中的某本书
    @DeleteMapping("/customer/{customerId}/delete")
    public ResponseEntity<Cart> deleteBookFromCart(@PathVariable Long customerId,
                                                   @RequestParam Long bookId) {
        return ResponseEntity.ok(cartService.deleteBookFromCart(customerId, bookId));
    }

    // 结算购物车，清空并转化为订单
    @PostMapping("/customer/{customerId}/checkout")
    public ResponseEntity<Order> checkoutCart(@PathVariable Long customerId) {
        try {
            Order order = orderService.createOrderFromCart(customerId);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 返回错误状态码
        }
    }
}
