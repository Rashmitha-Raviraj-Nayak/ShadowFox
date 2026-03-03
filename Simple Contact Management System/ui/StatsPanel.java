package ui;

import java.awt.*;
import javax.swing.*;
import service.ContactService;

public class StatsPanel extends JPanel {

    private JLabel totalContacts;
    private ContactService service;

    public StatsPanel(ContactService service) {
        this.service = service;

        setLayout(new GridLayout(1, 1, 20, 20));
        setBorder(BorderFactory.createTitledBorder("Dashboard Statistics"));

        totalContacts = new JLabel();
        totalContacts.setFont(new Font("Segoe UI", Font.BOLD, 22));
        totalContacts.setHorizontalAlignment(SwingConstants.CENTER);

        add(totalContacts);
        refreshStats();
    }

    public void refreshStats() {
        totalContacts.setText("Total Contacts: " + service.getAllContacts().size());
    }
}
