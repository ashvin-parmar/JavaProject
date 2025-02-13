package com.invoiceapp.ui;

import com.invoiceapp.model.Customer;
import com.invoiceapp.service.CustomerService;
import com.invoiceapp.util.ColorScheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerTablePanel extends JPanel {
    private final CustomerService customerService;
    private final JTable customerTable;
    private final DefaultTableModel tableModel;

    public CustomerTablePanel(CustomerService customerService) {
        this.customerService = customerService;
        
        // Create table model
        String[] columns = {"Name", "Email", "Phone", "City"};
        this.tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        this.customerTable = new JTable(tableModel);
        setupPanel();
        refreshTable();
    }

    private void setupPanel() {
        setBackground(ColorScheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout());

        // Configure table
        customerTable.setFillsViewportHeight(true);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerTable.getTableHeader().setBackground(ColorScheme.PRIMARY);
        customerTable.getTableHeader().setForeground(Color.WHITE);

        // Add components
        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(new JLabel("Customer Records"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(ColorScheme.SECONDARY);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(e -> refreshTable());
        add(refreshButton, BorderLayout.SOUTH);
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Customer customer : customerService.getAllCustomers()) {
            tableModel.addRow(new Object[]{
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCity()
            });
        }
    }
}

