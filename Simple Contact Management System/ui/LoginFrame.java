package ui;

import java.awt.*;
import javax.swing.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {

        setTitle("Smart CRM Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        panel.add(new JLabel("Username:"));
        panel.add(username);
        panel.add(new JLabel("Password:"));
        panel.add(password);
        panel.add(new JLabel());
        panel.add(loginBtn);

        add(panel);

        loginBtn.addActionListener(e -> {
            if (username.getText().equals("admin") &&
                String.valueOf(password.getPassword()).equals("1234")) {

                dispose();
                new DashboardFrame().setVisible(true);

            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid Credentials",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
