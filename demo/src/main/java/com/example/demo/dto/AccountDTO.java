package com.example.demo.dto;

import java.math.BigDecimal;

/**
 * Account resource represented in HAL+JSON via REST API.
 */
public class AccountDTO {
    private String name;

    private BigDecimal balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}