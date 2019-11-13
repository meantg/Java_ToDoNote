package DAO;

import helper.DBHelper;
import helper.PopupHelper;
import DTO.NoteDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {
    public static List<NoteDTO> getToDoList(Integer maNguoiDung, Integer maPhanLoai) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT * FROM ToDo_Note WHERE MaNguoiDung = ? AND MaPhanLoai = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, maNguoiDung);
        statement.setInt(2, maPhanLoai);
        ResultSet rs = statement.executeQuery();

        List<NoteDTO> output = new ArrayList<NoteDTO>();
        while (rs.next()) {
            NoteDTO note = new NoteDTO(
                    rs.getInt("MaNote"),
                    rs.getInt("MaPhanLoai"),
                    rs.getInt("MaNguoiDung"),
                    rs.getString("TieuDe"),
                    rs.getString("NoiDung"),
                    rs.getInt("maPhanLoai"),
                    rs.getTimestamp("NgayTao").toLocalDateTime().toLocalDate()
                    //rs.getTimestamp("HanChot").toLocalDateTime().toLocalDate()
            );
            output.add(note);
        }
        conn.close();
        return output;
    }

    public static boolean insertToDoList(NoteDTO note) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "INSERT INTO todo_note(MaLoaiNote, MaNguoiDung, TieuDe, NoiDung, HanChot) "
                + "VALUES (?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, note.getMaPhanLoai());
        statement.setInt(2, note.getMaNguoiDung());
        statement.setString(3, note.getTieuDe());
        statement.setString(4, note.getNoiDung());
        statement.setString(5, note.getHanChot().toString());
        int records = statement.executeUpdate();
        conn.close();
        return records > 0;
    }
}
