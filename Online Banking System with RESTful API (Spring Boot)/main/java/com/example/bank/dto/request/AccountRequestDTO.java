package com.example.bank.dto.request;

public class AccountRequestDTO {
    
    private String accountNumber;
    private Double initialBalance;
    
    public AccountRequestDTO() {}

    public AccountRequestDTO(String accountNumber, Double initialBalance) {
        this.accountNumber = accountNumber;
        this.initialBalance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Double initialBalance) {
        this.initialBalance = initialBalance;
    }
} 
