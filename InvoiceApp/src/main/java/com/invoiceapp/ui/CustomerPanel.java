package com.invoiceapp.ui;

import com.invoiceapp.model.Customer;
import com.invoiceapp.service.CustomerService;
import com.invoiceapp.service.PDFGenerator;
import com.invoiceapp.util.ColorScheme;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CustomerPanel extends JPanel {
    private final CustomerService customerService;
    private final PDFGenerator pdfGenerator;
    
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipField;

    public CustomerPanel(CustomerService customerService) {
        this.customerService = customerService;
        this.pdfGenerator = new PDFGenerator();
        
        setupPanel();
        createComponents();
    }

    private void setupPanel() {
        setBackground(ColorScheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new GridBagLayout());
    }

    private void createComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize fields
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        addressField = new JTextField(20);
        cityField = new JTextField(20);
        stateField = new JTextField(20);
        zipField = new JTextField(20);

        // Add components
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Customer Details"), gbc);

        addFormField("Name:", nameField, gbc, 1);
        addFormField("Email:", emailField, gbc, 2);
        addFormField("Phone:", phoneField, gbc, 3);
        addFormField("Address:", addressField, gbc, 4);
        addFormField("City:", cityField, gbc, 5);
        addFormField("State:", stateField, gbc, 6);
        addFormField("ZIP:", zipField, gbc, 7);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton saveButton = new JButton("Save Customer");
        JButton generateButton = new JButton("Generate Invoice");
        
        saveButton.setBackground(ColorScheme.ACCENT);
        saveButton.setForeground(Color.WHITE);
        generateButton.setBackground(ColorScheme.SECONDARY);
        generateButton.setForeground(Color.WHITE);
        
        saveButton.addActionListener(e -> saveCustomer());
        generateButton.addActionListener(e -> generateInvoice());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(generateButton);
        
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);
    }

    private void addFormField(String label, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        add(field, gbc);
    }

    private void saveCustomer() {
        Customer customer = new Customer();
        customer.setName(nameField.getText());
        customer.setEmail(emailField.getText());
        customer.setPhone(phoneField.getText());
        customer.setAddress(addressField.getText());
        customer.setCity(cityField.getText());
        customer.setState(stateField.getText());
        customer.setZipCode(zipField.getText());

        customerService.saveCustomer(customer);
        JOptionPane.showMessageDialog(this, "Customer saved successfully!");
        clearFields();
    }

    private void generateInvoice() {
        if (nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please save customer data first!");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Invoice PDF");
        fileChooser.setSelectedFile(new java.io.File("invoice.pdf"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                Customer customer = new Customer();
                customer.setName(nameField.getText());
                customer.setAddress(addressField.getText());
                customer.setCity(cityField.getText());
                customer.setState(stateField.getText());
                customer.setZipCode(zipField.getText());

                pdfGenerator.generateInvoice(customer, fileChooser.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Invoice generated successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error generating invoice: " + e.getMessage());
            }
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        cityField.setText("");
        stateField.setText("");
        zipField.setText("");
    }
}

