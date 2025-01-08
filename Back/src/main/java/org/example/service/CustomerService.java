package org.example.service;

import org.example.model.Customer;
import org.example.repository.CustomerRepository;
import org.example.model.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // 更新客户
    public Customer updateCustomer(Long id, Customer customer, Long currentUserId) {
        // 从 SecurityContextHolder 获取当前用户的角色
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!currentUserId.equals(id) && !isAdmin) {
            throw new RuntimeException("You don't have permission to update this customer.");
        }
        return customerRepository.save(customer);
    }


    // 删除客户
    public void deleteCustomer(Long id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);

        if (customerOpt.isEmpty()) {
            throw new RuntimeException("Customer with ID " + id + " not found.");
        }

        customerRepository.deleteById(id);
    }


    // 注册方法：对密码进行加密
    public Customer register(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword())); // 密码加密
        customer.setCreditLevel(Customer.CreditLevel.LEVEL_1); // 初始设为1级
        return customerRepository.save(customer);
    }

    // 登录方法：验证密码并返回 JWT Token
    public String login(String email, String rawPassword) {
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);
        if (customerOpt.isEmpty()) {
            throw new RuntimeException("User with email " + email + " not found");
        }
        Customer customer = customerOpt.get();
        if (passwordEncoder.matches(rawPassword, customer.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 判断用户角色，并将其添加到 claims 中
        String role = (customer.getId() == 1) ? "ADMIN" : "USER";  // 假设 ID 为 1 的用户是管理员

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", customer.getId());
        claims.put("email", customer.getEmail());
        claims.put("role", role);  // 添加角色信息

        // 生成包含角色信息的 JWT
        return jwtUtil.createJwt(43200000, claims);  // 生成 JWT Token 12小时有效
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            return customerOpt.get();
        } else {
            throw new RuntimeException("Customer with id " + id + " not found");
        }
    }

    // 更新账户余额并调整信用等级
    public Customer updateBalance(Long id, Double amount) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();

            if (amount > 0) {
                // 充值逻辑
                customer.recharge(amount);
            } else if (amount < 0) {
                // 提现逻辑
                if (!customer.canWithdraw(-amount)) {
                    throw new RuntimeException("Insufficient balance or overdraft limit exceeded.");
                }
                customer.setAccountBalance(customer.getAccountBalance() + amount); // 提现减少余额
            }

            return customerRepository.save(customer); // 保存更新后的客户信息
        } else {
            throw new RuntimeException("Customer with id " + id + " not found");
        }
    }


    // 私有方法：根据账户余额调整信用等级
    private void adjustCreditLevel(Customer customer) {
        double balance = customer.getAccountBalance();

        if (balance >= 20000) {
            customer.setCreditLevel(Customer.CreditLevel.LEVEL_5);
        } else if (balance >= 10000) {
            customer.setCreditLevel(Customer.CreditLevel.LEVEL_4);
        } else if (balance >= 5000) {
            customer.setCreditLevel(Customer.CreditLevel.LEVEL_3);
        } else if (balance >= 1000) {
            customer.setCreditLevel(Customer.CreditLevel.LEVEL_2);
        } else {
            customer.setCreditLevel(Customer.CreditLevel.LEVEL_1);
        }
    }


    // 更新客户指定字段
    public Customer updateCustomerFields(Long id, Map<String, Object> updates) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }

        Customer customer = customerOpt.get();

        // 仅允许更新 name, email 和 address
        if (updates.containsKey("name")) {
            customer.setName((String) updates.get("name"));
        }
        if (updates.containsKey("email")) {
            customer.setEmail((String) updates.get("email"));
        }
        if (updates.containsKey("address")) {
            customer.setAddress((String) updates.get("address"));
        }

        return customerRepository.save(customer);
    }

}
