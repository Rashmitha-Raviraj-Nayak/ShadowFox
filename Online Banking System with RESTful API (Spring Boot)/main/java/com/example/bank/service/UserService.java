package com.example.bank.service;

import com.example.bank.entity.User;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public UserService() {
        // Initialize with a default user for testing
        User admin = new User("admin", "password", "admin@example.com", "0000000000");
        users.put(admin.getUsername(), admin);
    }

    public boolean authenticate(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }

    public User register(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("User already exists");
        }
        users.put(user.getUsername(), user);
        return user;
    }
}
