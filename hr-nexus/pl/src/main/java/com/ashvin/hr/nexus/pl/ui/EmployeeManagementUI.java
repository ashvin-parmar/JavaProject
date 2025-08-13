package com.ashvin.hr.nexus.pl.ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeManagementUI extends JFrame {
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JPanel detailPanel;
    private JButton searchButton;
    private JTextField searchField;
    private JComboBox<String> searchCriteria;
    private JTextArea logArea;
    
    // Detail form components
    private JTextField idField, nameField, designationField, dobField, salaryField;
    private JComboBox<String> genderCombo;
    private JCheckBox isIndianCheck;
    private JTextField panField, aadharField;
    private JButton updateButton;

    public EmployeeManagementUI() {
        super("Employee Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        createSearchPanel();
        createTablePanel();
        createDetailPanel();
        createLogPanel();

        // Sample data for demonstration
        addSampleData();
    }

    private void createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchCriteria = new JComboBox<>(new String[]{"ID", "Name", "PAN", "Aadhar"});
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        
        searchButton.addActionListener(e -> performSearch());

        searchPanel.add(new JLabel("Search by:"));
        searchPanel.add(searchCriteria);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        add(searchPanel, BorderLayout.NORTH);
    }

    private void createTablePanel() {
        String[] columns = {"ID", "Name", "Designation", "DOB", "Salary"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showEmployeeDetails();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createDetailPanel() {
        detailPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        detailPanel.setBorder(BorderFactory.createTitledBorder("Employee Details"));
        detailPanel.setPreferredSize(new Dimension(400, 300));
        
        // Initialize components
        idField = new JTextField();
        nameField = new JTextField();
        designationField = new JTextField();
        dobField = new JTextField();
        salaryField = new JTextField();
        genderCombo = new JComboBox<>(new String[]{"M", "F", "O"});
        isIndianCheck = new JCheckBox("Indian Citizen");
        panField = new JTextField();
        aadharField = new JTextField();
        updateButton = new JButton("Update Record");
        
        // Add components to panel
        detailPanel.add(new JLabel("Employee ID:"));
        detailPanel.add(idField);
        detailPanel.add(new JLabel("Full Name:"));
        detailPanel.add(nameField);
        detailPanel.add(new JLabel("Designation Code:"));
        detailPanel.add(designationField);
        detailPanel.add(new JLabel("Date of Birth:"));
        detailPanel.add(dobField);
        detailPanel.add(new JLabel("Basic Salary:"));
        detailPanel.add(salaryField);
        detailPanel.add(new JLabel("Gender:"));
        detailPanel.add(genderCombo);
        detailPanel.add(new JLabel("Citizenship:"));
        detailPanel.add(isIndianCheck);
        detailPanel.add(new JLabel("PAN Number:"));
        detailPanel.add(panField);
        detailPanel.add(new JLabel("Aadhar Number:"));
        detailPanel.add(aadharField);
        detailPanel.add(updateButton);
        
        updateButton.addActionListener(e -> updateEmployee());
        
        // Add to east side of frame
        add(detailPanel, BorderLayout.EAST);
    }

    private void createLogPanel() {
        logArea = new JTextArea(5, 30);
        logArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("Activity Log"));
        
        add(logScroll, BorderLayout.SOUTH);
        log("Application started");
    }

    private void performSearch() {
        String criteria = (String) searchCriteria.getSelectedItem();
        String term = searchField.getText();
        // Here you would implement actual database search
        log("Search performed: " + criteria + " = " + term);
    }

    private void showEmployeeDetails() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Populate details from selected row (in real app fetch from DB)
            idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            // ... populate other fields ...
            log("Showing details for employee ID: " + idField.getText());
        }
    }

    private void updateEmployee() {
        // Validate input
        try {
            // Get values from form
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            // ... get other values ...
            
            // Here you would implement actual update logic
            log("Updated employee record: ID=" + id);
            JOptionPane.showMessageDialog(this, "Update successful!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Update Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void log(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        logArea.append("[" + sdf.format(new Date()) + "] " + message + "\n");
    }

    private void addSampleData() {
        // Sample data for demonstration
        tableModel.addRow(new Object[]{"101", "John Doe", "ENG01", "1990-05-15", "75000.00"});
        tableModel.addRow(new Object[]{"102", "Jane Smith", "MGR02", "1985-11-22", "95000.00"});
    }
}
