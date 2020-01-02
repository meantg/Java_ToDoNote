package DAO;

import DTO.UserDTO;
import helper.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean checkLoginUser(String username, String password) throws SQLException {
        Connection conn = DBHelper.getConnection();
        try {
            String query = "call login_user(?,?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.execute();
            ResultSet rs = statement.executeQuery();
            rs.next();
            boolean output = rs.getBoolean(1);
            return output;
        } finally {
            conn.close();
        }
    }

    public static boolean checkInsUser(UserDTO user) throws SQLException {
        Connection conn = DBHelper.getConnection();
        try {
            String query = "SELECT (EXISTS (SELECT 1 FROM User WHERE Username = ?))"
                    + "OR (EXISTS (SELECT 1 FROM User WHERE Email = ?))";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            ResultSet rs = statement.executeQuery();
            rs.next();
            boolean output = rs.getBoolean(1);
            return output;
        } finally {
            conn.close();
        }
    }

    public static boolean insertUser(UserDTO user) throws SQLException {
        Connection conn = DBHelper.getConnection();
        try {
            String query = "INSERT INTO User (Fullname, Username, Password, Gender,"
                    + "PhoneNumber, Email) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, user.getFullname());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getGender());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getEmail());
            int records = statement.executeUpdate();
            return records > 0;
        } finally {
            conn.close();
        }
    }


    public static UserDTO getUserByUsername(String username) throws SQLException {
        Connection conn = DBHelper.getConnection();
        try {
            String query = "SELECT * FROM User WHERE Username = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            rs.next();
            UserDTO output = new UserDTO(rs.getInt("UserID"), rs.getString("Fullname"), rs.getString("Username"),
                    rs.getString("Password"), rs.getString("Gender"), rs.getString("PhoneNumber"), rs.getString("Email"));
            return output;
        } finally {
            conn.close();
        }
    }

}
