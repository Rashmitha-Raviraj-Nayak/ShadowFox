package com.example.bank.entity;

import java.time.LocalDateTime;

public class TransactionHistory {
    
    private Long id;
    
    private Account account;
    
    private Double amount;
    private String transactionType;
    private LocalDateTime transactionDate;
    
    public TransactionHistory() {}

    public TransactionHistory(Account account, Double amount, String transactionType, LocalDateTime transactionDate) {
        this.account = account;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
