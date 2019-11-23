package BUS;

import DAO.NoteDAO;
import DTO.NoteDTO;

import java.sql.SQLException;
import java.util.List;

public class NoteBUS {
    public static List<NoteDTO> getToDoList(Integer maPhanLoai, Integer maTinhTrang) throws SQLException {
        return NoteDAO.getToDoList(maPhanLoai, maTinhTrang);
    }

    public static boolean insertToDoList(NoteDTO note) throws SQLException {
        return NoteDAO.insertToDoList(note);
    }

    public static boolean updateTinhTrang(Integer maTinhTrang, Integer maNote) throws SQLException {
        return NoteDAO.updateTinhTrang(maTinhTrang, maNote);
    }

    public static boolean insertNote(NoteDTO noteDTO) throws SQLException {
        return NoteDAO.insertNote(noteDTO);
    }

    public static boolean updateNote(NoteDTO noteDTO) throws SQLException{
        return NoteDAO.updateNote(noteDTO);
    }
}
