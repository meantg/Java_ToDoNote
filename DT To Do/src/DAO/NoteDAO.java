package DAO;

import DTO.NoteDTO;
import helper.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {
    public static List<NoteDTO> getToDoList(Integer maPhanLoai, Integer maTinhTrang) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT * FROM ToDo_Note WHERE MaPhanLoai = ? AND MaTinhTrang = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, maPhanLoai);
        statement.setInt(2, maTinhTrang);
        ResultSet rs = statement.executeQuery();

        List<NoteDTO> output = new ArrayList<NoteDTO>();
        while (rs.next()) {
            NoteDTO note = new NoteDTO(
                    rs.getInt("MaNote"),
                    rs.getInt("MaPhanLoai"),
                    rs.getString("TieuDe"),
                    rs.getString("NoiDung"),
                    rs.getInt("MaTinhTrang"),
                    rs.getTimestamp("NgayTao").toLocalDateTime().toLocalDate()
                    //rs.getTimestamp("HanChot").toLocalDateTime().toLocalDate()
            );
            output.add(note);
        }
        conn.close();
        return output;
    }

    public static boolean updateTinhTrang(Integer maTinhTrang, Integer maNote) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "UPDATE todo_note SET MaTinhTrang = ? WHERE MaNote = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, maTinhTrang);
        statement.setInt(2, maNote);
        int records = statement.executeUpdate();
        conn.close();
        return records > 0;
    }


    public static boolean updateNote(NoteDTO noteDTO) throws SQLException{
        Connection conn = DBHelper.getConnection();
        String query = "UPDATE todo_note SET MaPhanLoai = ?, TieuDe = ?, NoiDung = ?, MaTinhTrang = ? WHERE MaNote = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1,noteDTO.getMaPhanLoai());
        statement.setString(2,noteDTO.getTieuDe());
        statement.setString(3,noteDTO.getNoiDung());
        statement.setInt(4,noteDTO.getMaTinhTrang());
        statement.setInt(5,noteDTO.getMaNote());
        int records = statement.executeUpdate();
        conn.close();
        return records > 0;

    }

    public static boolean insertNote(NoteDTO noteDTO) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "INSERT INTO todo_note (MaPhanLoai, TieuDe, NoiDung, MaTinhTrang) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1,noteDTO.getMaPhanLoai());
        statement.setString(2, noteDTO.getTieuDe());
        statement.setString(3, noteDTO.getNoiDung());
        statement.setInt(4, noteDTO.getMaTinhTrang());
        int records = statement.executeUpdate();
        conn.close();
        return records > 0;
    }

    public static boolean deleteNote(Integer maNote) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "DELETE FROM todo_note WHERE MaNote = " + maNote;
        Statement statement = conn.createStatement();
        int records = statement.executeUpdate(query);
        conn.close();
        return records > 0;
    }

    public static List<NoteDTO> getToDoList(Integer maPhanLoai) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT * FROM ToDo_Note WHERE MaPhanLoai = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, maPhanLoai);
        ResultSet rs = statement.executeQuery();

        List<NoteDTO> output = new ArrayList<NoteDTO>();
        while (rs.next()) {
            NoteDTO note = new NoteDTO(
                    rs.getInt("MaNote"),
                    rs.getInt("MaPhanLoai"),
                    rs.getString("TieuDe"),
                    rs.getString("NoiDung"),
                    rs.getInt("MaTinhTrang"),
                    rs.getTimestamp("NgayTao").toLocalDateTime().toLocalDate()
                    //rs.getTimestamp("HanChot").toLocalDateTime().toLocalDate()
            );
            output.add(note);
        }
        conn.close();
        return output;
    }
}
