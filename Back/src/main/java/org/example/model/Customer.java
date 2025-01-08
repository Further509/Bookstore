package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @Email
    @NotNull
    private String email;

    private String address = "华中科技大学";

//    @JsonIgnore // 防止密码在返回 JSON 时暴露
    private String password;

    private Double accountBalance = 0.0;

    private Double totalRecharge = 0.0; // 累计充值金额

    @Enumerated(EnumType.STRING)
    private CreditLevel creditLevel = CreditLevel.LEVEL_1;

    public enum CreditLevel {
        LEVEL_1(0.10, 0.0), // 10%折扣，不能透支
        LEVEL_2(0.15, 0.0), // 15%折扣，不能透支
        LEVEL_3(0.15, 500.0), // 15%折扣，可透支500
        LEVEL_4(0.20, 1000.0), // 20%折扣，可透支1000
        LEVEL_5(0.25, Double.MAX_VALUE); // 25%折扣，无透支限制

        private final double discount;
        private final double overdraftLimit;

        CreditLevel(double discount, double overdraftLimit) {
            this.discount = discount;
            this.overdraftLimit = overdraftLimit;
        }

        public double getDiscount() {
            return discount;
        }

        public double getOverdraftLimit() {
            return overdraftLimit;
        }
    }

    public boolean canWithdraw(double amount) {
        return this.accountBalance - amount >= -this.creditLevel.getOverdraftLimit();
    }

    // 无参构造函数
    public Customer() {}

    // 全参构造函数
    public Customer(String name, String email, String address, String password, Double accountBalance, CreditLevel creditLevel) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
        this.accountBalance = accountBalance;
        this.creditLevel = creditLevel;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }


    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public CreditLevel getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(CreditLevel creditLevel) {
        this.creditLevel = creditLevel;
    }

    // 账户余额操作
    public void deposit(Double amount) {
        if (amount > 0) {
            this.accountBalance += amount;
        }
    }

    public boolean withdraw(Double amount) {
        if (amount > 0 && this.accountBalance >= amount) {
            this.accountBalance -= amount;
            return true;
        }
        return false;
    }

    // 更新信用等级的逻辑
    public void updateCreditLevel() {
        if (this.totalRecharge >= 50000) {
            this.creditLevel = CreditLevel.LEVEL_5;
        } else if (this.totalRecharge >= 20000) {
            this.creditLevel = CreditLevel.LEVEL_4;
        } else if (this.totalRecharge >= 10000) {
            this.creditLevel = CreditLevel.LEVEL_3;
        } else if (this.totalRecharge >= 5000) {
            this.creditLevel = CreditLevel.LEVEL_2;
        } else {
            this.creditLevel = CreditLevel.LEVEL_1;
        }
    }

    public Double getTotalRecharge() {
        return totalRecharge;
    }

    public void setTotalRecharge(Double totalRecharge) {
        this.totalRecharge = totalRecharge;
    }

    // 账户余额充值逻辑
    public void recharge(Double amount) {
        if (amount > 0) {
            this.accountBalance += amount;
            this.totalRecharge += amount;
            updateCreditLevel(); // 根据累计充值金额更新信用等级
        }
    }
}
