package com.example.demo.controller;

import com.example.demo.dao.AccountRepository;
import com.example.demo.exception.NegativeBalanceException;
import com.example.demo.entity.Account;
// import com.example.demo.dto.AccountModel;

import com.example.demo.util.CurrencyConverter;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/")
    public ResponseEntity<String> index() {
        String message = "Welcome to the Account Management System!";
        return ResponseEntity.ok(message);
    }

    @GetMapping("/account")
    @Transactional(propagation = REQUIRES_NEW)
    public ResponseEntity<Object> listAccounts(
            @PageableDefault(size = 5) Pageable page) {

        return ResponseEntity.ok(accountRepository.findAll(page));
    }

    @GetMapping("/account/{id}")
    @Transactional(propagation = REQUIRES_NEW, readOnly = true)
    public ResponseEntity<Object> getAccount(@PathVariable("id") Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new DataRetrievalFailureException("No such account: " + accountId));
        return ResponseEntity.ok(account);
    }

    @PostMapping("/transfer")
    @Transactional(propagation = REQUIRES_NEW)
    public ResponseEntity<Object> transfer(
            @RequestParam("fromId") Long fromId,
            @RequestParam("toId") Long toId,
            @RequestParam("currency") String currency,
            @RequestParam("amount") BigDecimal amount) {
                
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Negative amount");
        }
        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("From and to accounts must be different");
        }
        Account fromAccount = accountRepository.findById(fromId)
                .orElseThrow(() -> new DataRetrievalFailureException("No such account: " + fromId));

        Account toAccount = accountRepository.findById(toId)
                .orElseThrow(() -> new DataRetrievalFailureException("No such account: " + toId));

        
        BigDecimal negateAmount = CurrencyConverter.exchangeRate(currency, fromAccount.getCurrency(), amount);
        BigDecimal addAmount = CurrencyConverter.exchangeRate(currency, toAccount.getCurrency(), amount);
        BigDecimal fromBalance = accountRepository.getBalance(fromId).add(negateAmount.negate());
        if (fromBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeBalanceException("Insufficient funds " + negateAmount + " for account " + fromId);
        }

        accountRepository.updateBalance(fromId, negateAmount.negate());
        accountRepository.updateBalance(toId, addAmount);

        return ResponseEntity.ok("Transfer completed successfully");
    }

    // private AccountModel accountModelAssembler(Account entity) {
    //         AccountModel model = new AccountModel();
    //         model.setName(entity.getName());
    //         model.setType(entity.getType());
    //         model.setBalance(entity.getBalance());
    //         return model;
    // }
}
