package com.expense.gui;

import java.util.List;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;

import com.expense.dao.CategoryDAO;
import com.expense.model.Category;

public class Mainframe extends JFrame {

    private JPanel main;
    private JButton expenseButton, categoryButton;

    // Components for Expense

    private ExpenseDAO expenseDAO;
    private JFrame frame;
    private JButton addButton, editButton, deleteButton, refreshButton, exitButton;
    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private JTextField amountField;
    private JTextArea descriptionField;
    private JTextField categoryIdField;

    // Components for Category

    private JFrame categoryFrame;
    private CategoryDAO categoryDAO;
    private JButton categoryAddButton, categoryEditButton, categoryDeleteButton, categoryRefreshButton,categoryExitButton;
    private JTable categoryTable;
    private DefaultTableModel categoryTableModel;
    private JTextField categoryNameField;

    public Mainframe() {

        expenseDAO = new ExpenseDAO();
        categoryDAO = new CategoryDAO();
        initialize();
        setupComponents();
        setupEventListeners();

    }

    private void initialize() {
        setTitle("Expense Tracker");
        setVisible(true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

    }

    private void setupComponents() {
        setLayout(new BorderLayout());

        JPanel main = new JPanel(new GridBagLayout());

        main.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        expenseButton = new JButton("Expenses");
        categoryButton = new JButton("Categories");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        main.add(expenseButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        main.add(categoryButton, gbc);

        add(main, BorderLayout.NORTH);
    }

    private void setupEventListeners() {
        expenseButton.addActionListener((ActionEvent e) -> {
            onSelectedExpense();
        });
        categoryButton.addActionListener((ActionEvent e) -> {
            onSelectedCategory();
        });
    }

    // Window for Expense

    public void onSelectedExpense() {

        JFrame frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1920, 1200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        String[] colNames = { "ID", "Amount", "Description", "Created At", "Category ID" };
        tableModel = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        expenseTable = new JTable(tableModel);
        expenseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        amountField = new JTextField(20);
        descriptionField = new JTextArea(4, 20);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        categoryIdField = new JTextField(20);


        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");
        exitButton = new JButton("Exit");

        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill= GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        inputPanel.add(new JLabel("Amount:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(new JScrollPane(descriptionField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Category ID:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(categoryIdField, gbc);

        

        JPanel buttoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        buttoPanel.add(addButton);
        buttoPanel.add(editButton);
        buttoPanel.add(deleteButton);
        buttoPanel.add(refreshButton);
        buttoPanel.add(exitButton);

        JPanel northpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northpanel.add(inputPanel, BorderLayout.CENTER);
        northpanel.add(buttoPanel, BorderLayout.SOUTH);

        frame.add(northpanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(expenseTable), BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("Status: Ready");
        statusPanel.add(statusLabel);
        frame.add(statusPanel, BorderLayout.SOUTH);

        addButton.addActionListener((ActionEvent e) -> addExpense());
        editButton.addActionListener((ActionEvent e) -> updateExpense());
        deleteButton.addActionListener((ActionEvent e) -> deleteExpense());
        refreshButton.addActionListener((ActionEvent e) -> {
            loadExpenses();
            clearFields();
        });

        exitButton.addActionListener((ActionEvent e) -> {
            this.setVisible(true);
            frame.dispose();
        });

        expenseTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting())
                loadSelectedExpense();

        });
        loadExpenses();
        frame.setVisible(true);

    }

    // Window for Category

    public void onSelectedCategory() {
        categoryFrame = new JFrame("Category");

        categoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        categoryFrame.setSize(1920, 1200);
        categoryFrame.setResizable(false);
        categoryFrame.setLocationRelativeTo(null);
        String[] colNames = { "ID", "Category Name" };
        categoryTableModel = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        categoryTable = new JTable(categoryTableModel);
        categoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        categoryNameField = new JTextField(20);
        categoryAddButton = new JButton("Add");
        categoryEditButton = new JButton("Update");
        categoryDeleteButton = new JButton("Delete");
        categoryRefreshButton = new JButton("Refresh");
        categoryExitButton = new JButton("Exit");

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Category Name:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(categoryNameField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.add(categoryAddButton);
        buttonPanel.add(categoryEditButton);
        buttonPanel.add(categoryDeleteButton);
        buttonPanel.add(categoryRefreshButton);
        buttonPanel.add(categoryExitButton);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(inputPanel, BorderLayout.NORTH);
        northPanel.add(buttonPanel, BorderLayout.CENTER);

        categoryFrame.add(northPanel, BorderLayout.NORTH);
        categoryFrame.add(new JScrollPane(categoryTable), BorderLayout.CENTER);

        categoryAddButton.addActionListener((ActionEvent e) -> {
            addCategory();
        });
        categoryEditButton.addActionListener((ActionEvent e) -> {
            updateCategory();
        });
        categoryDeleteButton.addActionListener((ActionEvent e) -> {
            deleteCategory();
        });
        categoryRefreshButton.addActionListener((ActionEvent e) -> {
            loadCategories();
        });

        categoryExitButton.addActionListener((ActionEvent e) -> {
            this.setVisible(true);
            categoryFrame.dispose();
        });

        loadCategories();
        categoryFrame.setVisible(true);
    }

    private void loadCategories() {
        categoryTableModel.setRowCount(0);
        try {

            List<Category> categories = categoryDAO.getAllCategories();
            for (Category category : categories) {
                Object[] rowData = {
                        category.getId(),
                        category.getCategoryname()
                };
                categoryTableModel.addRow(rowData);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(categoryFrame, "Failed to load categories: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Methods for Expense

    private void addExpense() {
        try {
            BigDecimal amount = new BigDecimal(amountField.getText().trim());
            String description = descriptionField.getText().trim();
            int categoryId = Integer.parseInt(categoryIdField.getText().trim());

            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Description cannot be empty.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Expense newExpense = new Expense(0, amount, description, LocalDateTime.now(), categoryId);
            boolean success = expenseDAO.addExpense(newExpense);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Expense added successfully!");
                loadExpenses();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add expense.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter valid numbers for Amount and Category ID.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error adding expense: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void updateExpense() {

        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an expense to edit.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int eid = (int) tableModel.getValueAt(selectedRow, 0);
            BigDecimal amount = new BigDecimal(amountField.getText().trim());
            String description = descriptionField.getText().trim();
            int categoryId = Integer.parseInt(categoryIdField.getText().trim());

            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Description cannot be empty.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Expense updatedExpense = new Expense(eid, amount, description, LocalDateTime.now(), categoryId);
            boolean success = expenseDAO.updateExpense(updatedExpense);

            if (success) {
                JOptionPane.showMessageDialog(frame, "Expense updated successfully!");
                loadExpenses();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to update expense.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter valid numbers for Amount and Category ID.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error updating expense: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an expense to delete.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int eid = (int) tableModel.getValueAt(selectedRow, 0);
        int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this expense?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                boolean success = expenseDAO.deleteExpense(eid);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Expense deleted successfully!");
                    loadExpenses();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to delete expense.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error deleting expense: " + ex.getMessage(), "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void loadExpenses() {
        tableModel.setRowCount(0);
        try {
            List<Expense> expenses = expenseDAO.getAllExpenses();
            for (Expense expense : expenses) {
                Object[] rowData = {
                        expense.getEid(),
                        expense.getAmount(),
                        expense.getDescription(),
                        expense.getCreated_at(),
                        expense.getCategory_id()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load expenses: " + ex.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSelectedExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow != -1) {

            amountField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            descriptionField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            categoryIdField.setText(tableModel.getValueAt(selectedRow, 4).toString());
        }
    }

    private void clearFields() {
        amountField.setText("");
        descriptionField.setText("");
        categoryIdField.setText("");
        expenseTable.clearSelection();
    }

    /// Methods for Category
    private void addCategory() {
        String name = categoryNameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(categoryFrame, "Category name cannot be empty.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // ID 0 indicates a new category to be inserted
            Category newCategory = new Category(0, name);
            boolean success = categoryDAO.addCategory(newCategory);

            if (success) {
                JOptionPane.showMessageDialog(categoryFrame, "Category added successfully!");
                loadCategories();
                clearCategoryFields();
            } else {
                JOptionPane.showMessageDialog(categoryFrame,
                        "Failed to add category. Category name might already exist.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(categoryFrame, "Error adding category: " + ex.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCategory() {
        int selectedRow = categoryTable.getSelectedRow();
        String newName = categoryNameField.getText().trim();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(categoryFrame, "Please select a category to edit.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(categoryFrame, "Category name cannot be empty.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int categoryId = (int) categoryTableModel.getValueAt(selectedRow, 0);
            Category updatedCategory = new Category(categoryId, newName);

            boolean success = categoryDAO.updateCategory(updatedCategory);

            if (success) {
                JOptionPane.showMessageDialog(categoryFrame, "Category updated successfully!");
                loadCategories();
                clearCategoryFields();
            } else {
                JOptionPane.showMessageDialog(categoryFrame,
                        "Failed to update category. Category name might already exist.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(categoryFrame, "Error updating category: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCategory() {
        int selectedRow = categoryTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(categoryFrame, "Please select a category to delete.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int categoryId = (int) categoryTableModel.getValueAt(selectedRow, 0);
        String categoryName = (String) categoryTableModel.getValueAt(selectedRow, 1);

        int confirmation = JOptionPane.showConfirmDialog(
                categoryFrame,
                "Are you sure you want to delete Category: " + categoryName
                        + "?\n(This action will fail if the category is referenced by any expense.)",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                boolean success = categoryDAO.deleteCategory(categoryId);
                if (success) {
                    JOptionPane.showMessageDialog(categoryFrame, "Category deleted successfully!");
                    loadCategories();
                    clearCategoryFields();
                } else {
                    JOptionPane.showMessageDialog(categoryFrame,
                            "Failed to delete category. It might be linked to existing expenses.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(categoryFrame, "Error deleting category: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadSelectedCategory() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow != -1) {
            String categoryName = (String) categoryTableModel.getValueAt(selectedRow, 1);
            categoryNameField.setText(categoryName);
        }
    }

    private void clearCategoryFields() {
        categoryNameField.setText("");
        categoryTable.clearSelection();
    }

}
