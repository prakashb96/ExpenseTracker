package com.expense;

import java.util.*;
import javax.swing.*;
import com.expense.gui.Mainframe;

import com.expense.util.DatabaseConnection;
import java.sql.Connection;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Expense Tracker Application");
        try {
            Connection cn = DatabaseConnection.getDBConnection();
            System.out.println("Connected to database successfullyyyyyyyyyy");
            cn.close();
        } catch (Exception e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            System.err.println("Could not set look and feel " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            try {

                new Mainframe().setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}