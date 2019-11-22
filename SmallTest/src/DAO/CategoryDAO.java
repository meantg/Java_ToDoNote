package DAO;

import DTO.CategoryDTO;
import helper.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    public static List<CategoryDTO> getListCategory(Integer maNguoiDung) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT * FROM PhanLoai WHERE MaNguoiDung = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, maNguoiDung);
        ResultSet rs = statement.executeQuery();

        List<CategoryDTO> output = new ArrayList<CategoryDTO>();
        while (rs.next()) {
            CategoryDTO category = new CategoryDTO(
                    rs.getInt("MaPhanLoai"),
                    rs.getInt("MaNguoiDung"),
                    rs.getString("TenPhanLoai"),
                    rs.getString("Icon")
            );
            output.add(category);
        }
        conn.close();
        return output;
    }

    public static Integer getNumOfNotesByMaPhanLoai(Integer maPhanLoai) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT COUNT(*) AS NumOfNotes FROM ToDo_Note WHERE MaPhanLoai = ? And MaTinhTrang = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1,maPhanLoai);
        statement.setInt(2,12002);
        ResultSet rs = statement.executeQuery();
        Integer output = null;
        if(rs.next()) {
            output = rs.getInt("NumOfNotes");
        }
        conn.close();
        return output;
    }

    public static String getTenPhanLoaiByMa(Integer maPhanLoai) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT TenPhanLoai FROM PhanLoai WHERE MaPhanLoai = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, maPhanLoai);
        ResultSet rs = statement.executeQuery();
        String output = null;
        if(rs.next()) {
             output = rs.getString("TenPhanLoai");
        }
        conn.close();
        return output;
    }

    public static boolean insertCategory(CategoryDTO category) throws SQLException {
        Connection conn = DBHelper.getConnection();
        try {
            String query = "INSERT INTO PhanLoai (MaNguoiDung, TenPhanLoai, Icon) VALUES (?,?,?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, category.getMaNguoiDung());
            statement.setString(2, category.getTenPhanLoai());
            statement.setString(3, category.getIcon());
            int records = statement.executeUpdate();
            return records > 0;
        } finally {
            conn.close();
        }
    }

    public static boolean updateCategory(CategoryDTO category) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "UPDATE PhanLoai SET TenPhanLoai = ?, Icon = ? WHERE MaPhanLoai = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, category.getTenPhanLoai());
        statement.setString(2, category.getIcon());
        statement.setInt(3, category.getMaPhanLoai());
        int records = statement.executeUpdate();
        conn.close();
        return records > 0;
    }
}
