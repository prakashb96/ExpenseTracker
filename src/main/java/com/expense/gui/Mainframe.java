package com.expense.gui;

import javax.swing.*;
import java.sql.*;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Mainframe extends JFrame {

    private JPanel main;
    private JButton expenseButton, categoryButton;

    public Mainframe() {

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
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridy = 0;
        gbc.gridx = 0;
        main.add(expenseButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        main.add(categoryButton, gbc);

        add(main, BorderLayout.CENTER);
    }

   private void setupEventListeners() {
        expenseButton.addActionListener((ActionEvent e) -> {onSelectedExpense();});
        categoryButton.addActionListener((ActionEvent e) -> {onSelectedCategory();});
    }


    public void onSelectedExpense() {
        JFrame frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1920, 1200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);




    }

    public void onSelectedCategory() {
        JFrame frame = new JFrame("Category");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1920, 1200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
   

}



