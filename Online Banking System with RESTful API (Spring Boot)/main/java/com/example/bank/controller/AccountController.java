package com.example.bank.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    
    private final com.example.bank.service.AccountService accountService;

    public AccountController(com.example.bank.service.AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public com.example.bank.entity.Account createAccount(@RequestBody com.example.bank.dto.request.AccountRequestDTO dto) {
        com.example.bank.entity.User user = new com.example.bank.entity.User();
        user.setUsername("anonymous");
        return accountService.createAccount(user, dto.getAccountNumber(), dto.getInitialBalance());
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody com.example.bank.dto.request.TransferRequestDTO dto) {
        boolean ok = accountService.transfer(dto.getFromAccountId(), dto.getToAccountId(), dto.getAmount());
        return ok ? "success" : "failure";
    }
}
