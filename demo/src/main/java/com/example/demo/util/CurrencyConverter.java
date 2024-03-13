package com.example.demo.util;

import javax.money.MonetaryAmount;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import java.math.BigDecimal;

public final class CurrencyConverter {

       private CurrencyConverter() {}

    public static BigDecimal exchangeRate(String from, String to, BigDecimal amount) {
        CurrencyUnit currency = Monetary.getCurrency(from);
        MonetaryAmount amt = Monetary.getDefaultAmountFactory()
      .setCurrency(from).setNumber(amount).create();
        // Get the currencies

        CurrencyConversion conv = MonetaryConversions.getConversion(to);

    
        return amt.with(conv).getNumber().numberValue(BigDecimal.class);

    }
}
