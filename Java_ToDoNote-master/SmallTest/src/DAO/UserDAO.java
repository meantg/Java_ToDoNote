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
            String query = "call login_NguoiDung(?,?)";
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
            String query = "SELECT (EXISTS (SELECT 1 FROM NguoiDung WHERE TenDangNhap = ?))"
                    + "OR (EXISTS (SELECT 1 FROM NguoiDung WHERE Email = ?))";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, user.getTenDangNhap());
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
            String query = "INSERT INTO NguoiDung (TenNguoiDung, TenDangNhap, MatKhau, GioiTinh,"
                    + "SoDienThoai, Email) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, user.getTenNguoiDung());
            statement.setString(2, user.getTenDangNhap());
            statement.setString(3, user.getMatKhau());
            statement.setString(4, user.getGioiTinh());
            statement.setString(5, user.getSoDienThoai());
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
            String query = "SELECT * FROM NguoiDung WHERE TenDangNhap = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            rs.next();
            UserDTO output = new UserDTO(rs.getInt("MaNguoiDung"), rs.getString("TenNguoiDung"),
                    rs.getString("GioiTinh"), rs.getString("SoDienThoai"), rs.getString("Email"));
            return output;
        } finally {
            conn.close();
        }
    }

}
