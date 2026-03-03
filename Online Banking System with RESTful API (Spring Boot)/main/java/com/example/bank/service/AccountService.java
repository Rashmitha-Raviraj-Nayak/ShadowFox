package com.example.bank.service;

import com.example.bank.entity.Account;
import com.example.bank.entity.User;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class AccountService {
    private final Map<Long, Account> accounts = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Account createAccount(User user, String accountNumber, Double initialBalance) {
        Account account = new Account(user, accountNumber, initialBalance);
        account.setId(idGenerator.getAndIncrement());
        accounts.put(account.getId(), account);
        return account;
    }

    public Account getAccountById(Long id) {
        return accounts.get(id);
    }

    public java.util.List<Account> getAccountsByUser(User user) {
        java.util.List<Account> list = new java.util.ArrayList<>();
        for (Account acc : accounts.values()) {
            if (acc.getUser() != null && acc.getUser().getUsername().equals(user.getUsername())) {
                list.add(acc);
            }
        }
        return list;
    }

    public boolean transfer(Long fromId, Long toId, Double amount) {
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        if (from == null || to == null || amount <= 0 || from.getBalance() < amount) {
            return false;
        }
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        return true;
    }
}
