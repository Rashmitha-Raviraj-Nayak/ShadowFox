package com.example.bank.gui;

import com.example.bank.entity.User;
import com.example.bank.entity.Account;
import com.example.bank.service.AccountService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountPanel extends JPanel {
    private final User user;
    private final AccountService accountService;
    private DefaultListModel<String> accountListModel;

    public AccountPanel(User user, AccountService accountService) {
        this.user = user;
        this.accountService = accountService;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername());
        add(welcomeLabel, BorderLayout.NORTH);

        accountListModel = new DefaultListModel<>();
        JList<String> accountList = new JList<>(accountListModel);
        add(new JScrollPane(accountList), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton createButton = new JButton("Create Account");
        JButton transferButton = new JButton("Transfer");
        bottom.add(createButton);
        bottom.add(transferButton);
        add(bottom, BorderLayout.SOUTH);

        createButton.addActionListener(e -> onCreateAccount());
        transferButton.addActionListener(e -> onTransfer());
    }

    private void onCreateAccount() {
        String accNum = JOptionPane.showInputDialog(this, "Account number:");
        String balStr = JOptionPane.showInputDialog(this, "Initial balance:");
        try {
            double bal = Double.parseDouble(balStr);
            Account acc = accountService.createAccount(user, accNum, bal);
            accountListModel.addElement(format(acc));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onTransfer() {
        String fromIdStr = JOptionPane.showInputDialog(this, "From account id:");
        String toIdStr = JOptionPane.showInputDialog(this, "To account id:");
        String amtStr = JOptionPane.showInputDialog(this, "Amount:");
        try {
            Long fromId = Long.parseLong(fromIdStr);
            Long toId = Long.parseLong(toIdStr);
            double amt = Double.parseDouble(amtStr);
            boolean success = accountService.transfer(fromId, toId, amt);
            if (success) {
                JOptionPane.showMessageDialog(this, "Transfer completed");
            } else {
                JOptionPane.showMessageDialog(this, "Transfer failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String format(Account acc) {
        return acc.getId() + ": " + acc.getAccountNumber() + " ($" + acc.getBalance() + ")";
    }
}
