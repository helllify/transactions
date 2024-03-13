package com.example.demo.entity;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;

/**
 * Domain entity mapped to the account table.
 */
public class Account {
    @Id
    private Long id;

    private String currency;

    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}