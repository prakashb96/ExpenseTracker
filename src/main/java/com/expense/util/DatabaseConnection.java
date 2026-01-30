package com.expense.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static final String driver = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/expensetracker";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "2005";
    
    static {
        try {
            Class.forName(driver);
            System.out.println("MySQL Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
            System.out.println("Please add MySQL Connector/J to classpath");
        }
    }

    public static Connection getDBConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            throw e;
        }
    }
}
