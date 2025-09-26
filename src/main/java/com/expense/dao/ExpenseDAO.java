package com.expense.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.expense.model.Expense;
import com.expense.util.DatabaseConnection;

import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;


public class ExpenseDAO {
    private static final String SELECT_ALL_EXPENSES = "SELECT eid, amount, description, created_at, category_id FROM expense";
        private static final String INSERT_EXPENSE = "INSERT INTO expense (amount, description, created_at, category_id) VALUES (?, ?, ?, ?)";
        private static final String UPDATE_EXPENSE = "UPDATE expense SET amount = ?, description = ?, created_at = ?, category_id = ? WHERE eid = ?";
        private static final String DELETE_EXPENSE = "DELETE FROM expense WHERE eid = ?";

        private Expense getExpenseFromRow(ResultSet rs) throws SQLException {
            int eid = rs.getInt("eid");
            BigDecimal amount = rs.getBigDecimal("amount");
            String description = rs.getString("description");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            int categoryId = rs.getInt("category_id");
            return new Expense(eid, amount, description, createdAt, categoryId);
        }

        public List<Expense> getAllExpenses() throws SQLException {
            List<Expense> expenses = new ArrayList<>();
            
            try (Connection conn = DatabaseConnection.getDBConnection();
                 PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_EXPENSES);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    expenses.add(getExpenseFromRow(rs));
                }
            }
            return expenses;
        }

        public boolean addExpense(Expense expense) throws SQLException {
            try (Connection conn = DatabaseConnection.getDBConnection();
                 PreparedStatement stmt = conn.prepareStatement(INSERT_EXPENSE)) {
                stmt.setBigDecimal(1, expense.getAmount());
                stmt.setString(2, expense.getDescription());
                stmt.setTimestamp(3, Timestamp.valueOf(expense.getCreated_at()));
                stmt.setInt(4, expense.getCategory_id());
                return stmt.executeUpdate() > 0;
            }
        }

        public boolean updateExpense(Expense expense) throws SQLException {
            try (Connection conn = DatabaseConnection.getDBConnection();
                 PreparedStatement stmt = conn.prepareStatement(UPDATE_EXPENSE)) {
                stmt.setBigDecimal(1, expense.getAmount());
                stmt.setString(2, expense.getDescription());
                stmt.setTimestamp(3, Timestamp.valueOf(expense.getCreated_at()));
                stmt.setInt(4, expense.getCategory_id());
                stmt.setInt(5, expense.getEid());
                return stmt.executeUpdate() > 0;
            }
        }

        public boolean deleteExpense(int eid) throws SQLException {
            try (Connection conn = DatabaseConnection.getDBConnection();
                 PreparedStatement stmt = conn.prepareStatement(DELETE_EXPENSE)) {
                stmt.setInt(1, eid);
                return stmt.executeUpdate() > 0;
            }
        }

}

