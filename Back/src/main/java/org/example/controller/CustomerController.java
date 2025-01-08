package org.example.controller;

import io.jsonwebtoken.Claims;
import org.example.model.Customer;
import org.example.model.JwtUtil;
import org.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtUtil jwtUtil;

    // 获取当前登录用户的 ID
    private Long getCurrentUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // 移除 "Bearer " 部分
            Claims claims = jwtUtil.parseJwt(token);
            return claims.get("userId", Long.class);
        }
        return null;
    }

    // 获取所有客户
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // 根据用户 ID 查询客户信息
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id, HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);
        if (!currentUserId.equals(id) && !currentUserId.equals(1L)) {
            return ResponseEntity.status(403).body("You don't have permission to view this user.");
        }
        try {
            Customer customer = customerService.getCustomerById(id);
            return ResponseEntity.ok(customer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 创建客户
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    // 更新客户
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates,
            HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);

        // 检查权限
        if (!currentUserId.equals(id) && !currentUserId.equals(1L)) {
            return ResponseEntity.status(403).body("You don't have permission to update this user.");
        }

        // 仅允许更新 name, email 和 address
        try {
            Customer updatedCustomer = customerService.updateCustomerFields(id, updates);
            return ResponseEntity.ok(updatedCustomer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    // 删除客户
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id, HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);

        // 检查权限，仅允许 ID 为 1 的管理员删除用户
        if (!currentUserId.equals(1L)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only the admin (ID: 1) can delete users.");
        }

        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 返回 204 无内容状态
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    // 注册
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        try {
            // 注册用户，不生成 token
            Customer registeredCustomer = customerService.register(customer);

            // 注册成功后，返回成功信息
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredCustomer);
        } catch (RuntimeException e) {
            // 捕获 RuntimeException，返回 400 错误
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 登录
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Customer customer) {
        try {
            // 调用 service 层的 login 方法，验证密码并获取 JWT Token
            String token = customerService.login(customer.getEmail(), customer.getPassword());
            // 从 token 中获取用户信息
            Claims claims = jwtUtil.parseJwt(token);
            Customer loggedCustomer = customerService.getCustomerById(claims.get("userId", Long.class));
            // 创建响应体，包含用户信息和 token
            Map<String, Object> response = new HashMap<>();
            response.put("token", "Bearer " + token);
            response.put("user", loggedCustomer);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // 捕获 RuntimeException，返回 401 未授权状态
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // 更新用户余额及信用等级
    @PutMapping("/{id}/updateBalance")
    public ResponseEntity<?> updateBalance(
            @PathVariable Long id,
            @RequestParam Double amount,
            HttpServletRequest request) {

        Long currentUserId = getCurrentUserId(request);
        if (!currentUserId.equals(id) && !currentUserId.equals(1L)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You don't have permission to perform this action.");
        }

        try {
            Customer updatedCustomer = customerService.updateBalance(id, amount);

            // 返回包含累计充值金额、账户余额、信用等级的结果
            Map<String, Object> response = new HashMap<>();
            response.put("totalRecharge", updatedCustomer.getTotalRecharge());
            response.put("accountBalance", updatedCustomer.getAccountBalance());
            response.put("creditLevel", updatedCustomer.getCreditLevel().toString());
            response.put("discount", updatedCustomer.getCreditLevel().getDiscount());
            response.put("overdraftLimit", updatedCustomer.getCreditLevel().getOverdraftLimit());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



}
