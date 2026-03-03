package ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import model.Contact;
import service.ContactService;
import util.InputValidator;

public class ContactPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color DANGER_COLOR = new Color(192, 57, 43);
    private static final Color LIGHT_BG = new Color(236, 240, 241);

    private ModernTextField nameField, phoneField, emailField, addressField, companyField;
    private JTextArea notesArea;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel statusLabel, totalContactsLabel;

    private final ContactService contactService = new ContactService();
    private List<Contact> currentDisplayedList;

    public ContactPanel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(LIGHT_BG);

        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.setBackground(LIGHT_BG);
        add(centerPanel, BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = createFormPanel();
        centerPanel.add(formPanel, BorderLayout.WEST);

        // Table Panel
        JPanel tablePanel = createTablePanel();
        centerPanel.add(tablePanel, BorderLayout.CENTER);

        // Status Bar
        statusLabel = new JLabel(" Ready");
        statusLabel.setBorder(BorderFactory.createEtchedBorder());
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setBackground(new Color(240, 240, 240));
        add(statusLabel, BorderLayout.SOUTH);

        // Initialization
        refreshTable(contactService.getAllContacts());
        updateStats();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Contact Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.WEST);

        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statsPanel.setBackground(PRIMARY_COLOR);
        totalContactsLabel = new JLabel("Total Contacts: 0");
        totalContactsLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalContactsLabel.setForeground(Color.WHITE);
        statsPanel.add(totalContactsLabel);
        panel.add(statsPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                "Contact Details", 0, 0,
                new Font("Segoe UI", Font.BOLD, 13), PRIMARY_COLOR
        ));
        panel.setPreferredSize(new Dimension(350, 0));

        JPanel scrollableContent = new JPanel();
        scrollableContent.setLayout(new BoxLayout(scrollableContent, BoxLayout.Y_AXIS));
        scrollableContent.setBackground(Color.WHITE);
        scrollableContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollableContent.add(createLabeledField("Name:", nameField = new ModernTextField()));
        scrollableContent.add(Box.createVerticalStrut(5));
        scrollableContent.add(createLabeledField("Phone:", phoneField = new ModernTextField()));
        scrollableContent.add(Box.createVerticalStrut(5));
        scrollableContent.add(createLabeledField("Email:", emailField = new ModernTextField()));
        scrollableContent.add(Box.createVerticalStrut(5));
        scrollableContent.add(createLabeledField("Address:", addressField = new ModernTextField()));
        scrollableContent.add(Box.createVerticalStrut(5));
        scrollableContent.add(createLabeledField("Company:", companyField = new ModernTextField()));

        JPanel notesPanel = new JPanel(new BorderLayout());
        notesPanel.setBackground(Color.WHITE);
        notesPanel.add(new JLabel("Notes:"), BorderLayout.NORTH);
        notesArea = new JTextArea(2, 20);
        notesArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        notesPanel.add(new JScrollPane(notesArea), BorderLayout.CENTER);
        scrollableContent.add(Box.createVerticalStrut(5));
        scrollableContent.add(notesPanel);
        scrollableContent.add(Box.createVerticalStrut(10));

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        ModernButton addBtn = new ModernButton("➕ Add New");
        addBtn.setButtonStyle(SUCCESS_COLOR);
        addBtn.addActionListener(e -> addContact());

        ModernButton updateBtn = new ModernButton("✏️ Update");
        updateBtn.setButtonStyle(SECONDARY_COLOR);
        updateBtn.addActionListener(e -> updateContact());

        ModernButton deleteBtn = new ModernButton("🗑️ Delete");
        deleteBtn.setButtonStyle(DANGER_COLOR);
        deleteBtn.addActionListener(e -> deleteContact());

        ModernButton clearBtn = new ModernButton("🔄 Clear");
        clearBtn.setButtonStyle(new Color(149, 165, 166));
        clearBtn.addActionListener(e -> clearFields());

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(clearBtn);

        scrollableContent.add(buttonPanel);

        JScrollPane scrollPane = new JScrollPane(scrollableContent);
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createLabeledField(String label, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setPreferredSize(new Dimension(80, 30));
        panel.add(lbl, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                "Contact List", 0, 0,
                new Font("Segoe UI", Font.BOLD, 13), PRIMARY_COLOR
        ));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(new Color(240, 245, 250));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JLabel searchLabel = new JLabel("🔍 Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        ModernTextField searchField = new ModernTextField(25);
        ModernButton searchBtn = new ModernButton("Search");
        searchBtn.setButtonStyle(PRIMARY_COLOR);
        searchBtn.addActionListener(e -> performSearch(searchField.getText()));

        ModernButton clearSearchBtn = new ModernButton("Clear Filter");
        clearSearchBtn.setButtonStyle(new Color(155, 89, 182));
        clearSearchBtn.addActionListener(e -> {
            searchField.setText("");
            performSearch("");
        });

        searchField.addActionListener(e -> performSearch(searchField.getText()));

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(clearSearchBtn);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(
                new String[]{"Name", "Phone", "Email", "Company"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);

        // Alternate row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 248, 250));
                }
                return c;
            }
        });

        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(0, 35));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFields();
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void performSearch(String query) {
        if (query.trim().isEmpty()) {
            refreshTable(contactService.getAllContacts());
            statusLabel.setText(" Showing all contacts (" + contactService.getAllContacts().size() + ")");
        } else {
            List<Contact> results = contactService.searchContacts(query);
            refreshTable(results);
            statusLabel.setText(" Found " + results.size() + " contact(s)");
        }
    }

    private void addContact() {
        if (!validateInput()) return;

        Contact contact = new Contact(
                nameField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
                addressField.getText().trim(),
                companyField.getText().trim(),
                notesArea.getText().trim()
        );

        contactService.addContact(contact);
        refreshTable(contactService.getAllContacts());
        clearFields();
        updateStats();
        statusLabel.setText(" ✓ Contact added successfully");
        JOptionPane.showMessageDialog(this, "Contact added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateContact() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a contact to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validateInput()) return;

        Contact selectedContact = currentDisplayedList.get(row);
        int realIndex = contactService.getContactIndex(selectedContact);

        Contact updatedContact = new Contact(
                nameField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
                addressField.getText().trim(),
                companyField.getText().trim(),
                notesArea.getText().trim()
        );

        contactService.updateContact(realIndex, updatedContact);
        refreshTable(contactService.getAllContacts());
        clearFields();
        updateStats();
        statusLabel.setText(" ✓ Contact updated successfully");
        JOptionPane.showMessageDialog(this, "Contact updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteContact() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a contact to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this contact?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            Contact selectedContact = currentDisplayedList.get(row);
            int realIndex = contactService.getContactIndex(selectedContact);

            contactService.deleteContact(realIndex);
            refreshTable(contactService.getAllContacts());
            clearFields();
            updateStats();
            statusLabel.setText(" ✓ Contact deleted successfully");
            JOptionPane.showMessageDialog(this, "Contact deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void refreshTable(List<Contact> list) {
        currentDisplayedList = list;
        tableModel.setRowCount(0);
        for (Contact c : list) {
            tableModel.addRow(new Object[]{
                    c.getName(),
                    c.getPhone(),
                    c.getEmail(),
                    c.getCompany()
            });
        }
    }

    private void fillFields() {
        int row = table.getSelectedRow();
        if (row != -1) {
            Contact c = currentDisplayedList.get(row);
            nameField.setText(c.getName());
            phoneField.setText(c.getPhone());
            emailField.setText(c.getEmail());
            addressField.setText(c.getAddress());
            companyField.setText(c.getCompany());
            notesArea.setText(c.getNotes());
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
        companyField.setText("");
        notesArea.setText("");
        table.clearSelection();
    }

    private void updateStats() {
        totalContactsLabel.setText("Total Contacts: " + contactService.getAllContacts().size());
    }

    private boolean validateInput() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name, Phone, and Email are required!", "Missing Fields", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!InputValidator.isValidName(name)) {
            JOptionPane.showMessageDialog(this, "Name must be at least 2 characters long.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!InputValidator.isValidPhone(phone)) {
            JOptionPane.showMessageDialog(this, "Phone must contain at least 10 digits.", "Invalid Phone", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!InputValidator.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
