package com.example.bank.gui;

import com.example.bank.service.UserService;
import com.example.bank.service.AccountService;
import javax.swing.*;
import java.awt.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Online Banking");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            UserService userService = new UserService();
            AccountService accountService = new AccountService();
            LoginPanel loginPanel = new LoginPanel(userService, accountService, frame);
            frame.add(loginPanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
