package DAO;

import DTO.CategoryDTO;
import helper.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    public static List<CategoryDTO> getListCategory(Integer userID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT * FROM Categories WHERE UserID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();

        List<CategoryDTO> output = new ArrayList<CategoryDTO>();
        while (rs.next()) {
            CategoryDTO category = new CategoryDTO(
                    rs.getInt("CategoryID"),
                    rs.getInt("UserID"),
                    rs.getString("CategoryName"),
                    rs.getString("Icon"),
                    rs.getInt("NumOfNotes")
            );
            output.add(category);
        }
        conn.close();
        return output;
    }

    public static Integer getNumOfNotesByID(Integer CategoryID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT COUNT(*) AS NumOfNotes FROM notes WHERE CategoryID = ? And stateID = 12002";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, CategoryID);
//        statement.setInt(2,12002);
        ResultSet rs = statement.executeQuery();
        Integer output = null;
        if(rs.next()) {
            output = rs.getInt("NumOfNotes");
        }
        conn.close();
        return output;
    }

    public static String getCategoryNameByID(Integer categoryID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT CategoryName FROM categories WHERE CategoryID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, categoryID);
        ResultSet rs = statement.executeQuery();
        String output = null;
        if(rs.next()) {
             output = rs.getString("CategoryName");
        }
        conn.close();
        return output;
    }

    public static boolean insertCategory(CategoryDTO category) throws SQLException {
        Connection conn = DBHelper.getConnection();
        try {
            String query = "INSERT INTO categories (userID, CategoryName, Icon, NumOfNotes ) VALUES (?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, category.getUserID());
            statement.setString(2, category.getCategoryName());
            statement.setString(3, category.getIcon());
            statement.setInt(4, category.getNumOfNotes());
            int records = statement.executeUpdate();
            return records > 0;
        } finally {
            conn.close();
        }
    }

    public static boolean updateCategory(CategoryDTO category) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "UPDATE categories SET CategoryName = ?, Icon = ? WHERE CategoryID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, category.getCategoryName());
        statement.setString(2, category.getIcon());
        statement.setInt(3, category.getCategoryID());
        int records = statement.executeUpdate();
        conn.close();
        return records > 0;
    }

    public static boolean deleteCategory(Integer categoryID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        try {
            String query = "DELETE FROM categories WHERE CategoryID = " + categoryID;
            Statement statement = conn.createStatement();
            int records = statement.executeUpdate(query);
            return records > 0;
        } finally {
            conn.close();
        }
    }
}
