package com.expense.dao;

import com.expense.model.Category;
import com.expense.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private static final String SELECT_ALL_CATEGORIES = "SELECT id, categoryname FROM category ORDER BY categoryname";
    private static final String INSERT_CATEGORY = "INSERT INTO category (categoryname) VALUES (?)";
    private static final String UPDATE_CATEGORY = "UPDATE category SET categoryname = ? WHERE id = ?";
    private static final String DELETE_CATEGORY = "DELETE FROM category WHERE id = ?";

    private Category getCategoryFromRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String categoryname = rs.getString("categoryname");
        return new Category(id, categoryname);
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_CATEGORIES);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(getCategoryFromRow(rs));
            }
        }
        return categories;
    }

    public boolean addCategory(Category category) throws SQLException {
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_CATEGORY)) {
            
            stmt.setString(1, category.getCategoryname());
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateCategory(Category category) throws SQLException {
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_CATEGORY)) {
            
            stmt.setString(1, category.getCategoryname());
            stmt.setInt(2, category.getId());
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteCategory(int id) throws SQLException {
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_CATEGORY)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}