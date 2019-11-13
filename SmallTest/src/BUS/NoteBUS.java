package BUS;

import DAO.NoteDAO;
import DTO.NoteDTO;

import java.sql.SQLException;
import java.util.List;

public class NoteBUS {
    public static List<NoteDTO> getToDoList(Integer maNguoiDung, Integer maPhanLoai) throws SQLException {
        return NoteDAO.getToDoList(maNguoiDung, maPhanLoai);
    }

    public static boolean insertToDoList(NoteDTO note) throws SQLException {
        return NoteDAO.insertToDoList(note);
    }
}
