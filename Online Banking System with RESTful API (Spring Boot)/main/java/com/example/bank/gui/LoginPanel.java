package com.example.bank.gui;

import com.example.bank.service.UserService;
import com.example.bank.service.AccountService;
import com.example.bank.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private final UserService userService;
    private final AccountService accountService;
    private final JFrame parentFrame;

    public LoginPanel(UserService userService, AccountService accountService, JFrame parentFrame) {
        this.userService = userService;
        this.accountService = accountService;
        this.parentFrame = parentFrame;
        initialize();
    }

    private void initialize() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel, gbc);

        JTextField userField = new JTextField(15);
        gbc.gridx = 1;
        add(userField, gbc);

        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(loginButton, gbc);

        JButton registerButton = new JButton("Register");
        gbc.gridx = 1;
        add(registerButton, gbc);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (userService.authenticate(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful.");
                User user = userService.getUserByUsername(username);

                // display account details
                java.util.List<com.example.bank.entity.Account> accs = accountService.getAccountsByUser(user);
                if (accs.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No accounts found for this user.");
                } else {
                    StringBuilder sb = new StringBuilder("Your accounts:\n");
                    for (com.example.bank.entity.Account a : accs) {
                        sb.append("ID:").append(a.getId()).append(" Number:").append(a.getAccountNumber()).append(" Bal:").append(a.getBalance()).append("\n");
                    }
                    JOptionPane.showMessageDialog(this, sb.toString());
                }

                parentFrame.getContentPane().removeAll();
                parentFrame.getContentPane().add(new AccountPanel(user, accountService));
                parentFrame.revalidate();
                parentFrame.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Choose a username:");
            if (username == null || username.trim().isEmpty()) {
                return;
            }
            String password = JOptionPane.showInputDialog(this, "Choose a password:");
            if (password == null || password.trim().isEmpty()) {
                return;
            }
            String email = JOptionPane.showInputDialog(this, "Enter your email:");
            if (email == null || email.trim().isEmpty()) {
                return;
            }
            String mobile = JOptionPane.showInputDialog(this, "Enter your mobile number:");
            if (mobile == null || mobile.trim().isEmpty()) {
                return;
            }
            try {
                User newUser = new User();
                newUser.setUsername(username);
                newUser.setPassword(password);
                newUser.setEmail(email);
                newUser.setMobile(mobile);
                userService.register(newUser);
                // create initial account
                String accNum = String.valueOf((long)(Math.random() * 1_000_000_0000L));
                String ifsc = "BANK0001";
                com.example.bank.entity.Account acc = accountService.createAccount(newUser, accNum, 0.0);
                JOptionPane.showMessageDialog(this, "Registration successful.\nAccount#: " + accNum + "\nIFSC: " + ifsc + "\nYou can now login.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
