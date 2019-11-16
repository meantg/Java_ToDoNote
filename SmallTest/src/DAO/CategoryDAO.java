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
        String query = "SELECT COUNT(*) AS NumOfNotes FROM ToDo_Note WHERE MaPhanLoai = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1,maPhanLoai);
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
}
