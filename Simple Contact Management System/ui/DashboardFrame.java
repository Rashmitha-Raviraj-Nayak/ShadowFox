package ui;

import java.awt.*;
import javax.swing.*;

public class DashboardFrame extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SIDEBAR_COLOR = new Color(44, 62, 80);
    private static final Color ACCENT_COLOR = new Color(52, 152, 219);

    private JPanel contentPanel;

    public DashboardFrame() {
        setTitle("Smart Contact Manager - Professional Edition");
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);

        // ===== TOP BAR =====
        JPanel topBar = new JPanel();
        topBar.setBackground(PRIMARY_COLOR);
        topBar.setPreferredSize(new Dimension(1400, 70));
        topBar.setLayout(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("📋 Smart Contact Manager");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Keep your contacts organized and accessible");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(200, 220, 240));
        
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(subtitle);
        
        topBar.add(titlePanel, BorderLayout.WEST);

        add(topBar, BorderLayout.NORTH);

        // ===== SIDEBAR =====
        JPanel sideBar = new JPanel();
        sideBar.setBackground(SIDEBAR_COLOR);
        sideBar.setPreferredSize(new Dimension(220, 800));
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Logo/Branding
        JLabel logo = new JLabel("Contact Hub");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logo.setForeground(ACCENT_COLOR);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sideBar.add(logo);
        sideBar.add(Box.createVerticalStrut(30));

        // Navigation Buttons
        ModernButton contactsBtn = new ModernButton("📞 Contacts");
        contactsBtn.setButtonStyle(ACCENT_COLOR);
        contactsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        contactsBtn.setMaximumSize(new Dimension(180, 40));
        
        ModernButton aboutBtn = new ModernButton("ℹ️ About");
        aboutBtn.setButtonStyle(new Color(155, 89, 182));
        aboutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutBtn.setMaximumSize(new Dimension(180, 40));
        
        ModernButton exitBtn = new ModernButton("❌ Exit");
        exitBtn.setButtonStyle(new Color(231, 76, 60));
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setMaximumSize(new Dimension(180, 40));

        sideBar.add(contactsBtn);
        sideBar.add(Box.createVerticalStrut(15));
        sideBar.add(aboutBtn);
        sideBar.add(Box.createVerticalGlue());
        sideBar.add(exitBtn);

        add(sideBar, BorderLayout.WEST);

        // ===== CONTENT PANEL =====
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(236, 240, 241));
        add(contentPanel, BorderLayout.CENTER);

        // Default panel
        contentPanel.add(new ContactPanel(), BorderLayout.CENTER);

        // ===== EVENTS =====
        contactsBtn.addActionListener(actionEvent -> switchPanel(new ContactPanel()));
        aboutBtn.addActionListener(actionEvent -> showAbout());
        exitBtn.addActionListener(actionEvent -> System.exit(0));

        setVisible(true);
    }

    private void switchPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showAbout() {
        String aboutText = "Smart Contact Manager\n\n" +
                "Version 1.0\n" +
                "Professional Edition\n\n" +
                "Features:\n" +
                "✓ Add, Update, Delete Contacts\n" +
                "✓ Search & Filter\n" +
                "✓ Beautiful Modern UI\n" +
                "✓ Data Persistence\n" +
                "✓ Input Validation\n\n" +
                "© 2026 All Rights Reserved";
        
        JOptionPane.showMessageDialog(this, aboutText, "About", JOptionPane.INFORMATION_MESSAGE);
    }
}
