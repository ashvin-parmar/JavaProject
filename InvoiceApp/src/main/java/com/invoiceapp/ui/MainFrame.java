package com.invoiceapp.ui;

import com.invoiceapp.service.CustomerService;
import com.invoiceapp.util.ColorScheme;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CustomerService customerService;
    private final CustomerPanel customerPanel;
    private final CustomerTablePanel customerTablePanel;

    public MainFrame() {
        this.customerService = new CustomerService();
        this.customerPanel = new CustomerPanel(customerService);
        this.customerTablePanel = new CustomerTablePanel(customerService);

        setupFrame();
        setupLayout();
    }

    private void setupFrame() {
        setTitle("Invoice Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        getContentPane().setBackground(ColorScheme.BACKGROUND);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Create split pane
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            customerPanel,
            customerTablePanel
        );
        splitPane.setDividerLocation(400);
        
        // Add components
        add(createHeader(), BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        
        // Add padding
        ((JPanel)getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(ColorScheme.PRIMARY);
        header.setPreferredSize(new Dimension(1024, 60));
        header.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));

        JLabel titleLabel = new JLabel("Invoice Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel);

        return header;
    }
}

