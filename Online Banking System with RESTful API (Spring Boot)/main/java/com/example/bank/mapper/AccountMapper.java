package com.example.bank.mapper;

import com.example.bank.dto.response.AccountResponseDTO;
import com.example.bank.entity.Account;
public class AccountMapper {
    
    public AccountResponseDTO toDTO(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        return dto;
    }
    
    public Account toEntity(com.example.bank.dto.request.AccountRequestDTO dto) {
        Account account = new Account();
        return account;
    }
    
}
