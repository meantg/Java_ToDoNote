package BUS;

import DAO.NoteDAO;
import DTO.CategoryDTO;
import DTO.NoteDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteBUS {
    public static List<NoteDTO> getToDoList(Integer categoryID, boolean descending) throws SQLException {
        return NoteDAO.getToDoList(categoryID, descending);
    }

    public static List<NoteDTO> getToDoList(Integer categoryID) throws SQLException {
        return NoteDAO.getToDoList(categoryID);
    }

    public static List<NoteDTO> getMyDayNotes(Integer userID) throws SQLException {
        return NoteDAO.getMyDayNotes(userID);
    }

    public static List<NoteDTO> getMyDayNotes(Integer userID, boolean descending) throws SQLException {
        return NoteDAO.getMyDayNotes(userID, descending);
    }

    public static Integer getMyDayNumOfNotes(Integer userID) throws SQLException {
        return NoteDAO.getMyDayNumOfNotes(userID);
    }

    public static List<NoteDTO> getImportanceNotes(Integer userID) throws SQLException {
        return NoteDAO.getImportanceNotes(userID);
    }
    public static List<NoteDTO> getImportanceNotes(Integer userID, boolean descending) throws SQLException {
        return NoteDAO.getImportanceNotes(userID, descending);
    }

    public static Integer getImportanceNumOfNotes(Integer userID) throws SQLException {
        return NoteDAO.getImportanceNumOfNotes(userID);
    }

    public static List<NoteDTO> getPlannedNotes(Integer userID) throws SQLException {
        return NoteDAO.getPlannedNotes(userID);
    }

    public static List<NoteDTO> getPlannedNotes(Integer userID, boolean descending) throws SQLException {
        return NoteDAO.getPlannedNotes(userID, descending);
    }

    public static Integer getPlannedNumOfNotes(Integer userID) throws SQLException {
        return NoteDAO.getPlannedNumOfNotes(userID);
    }

    public static List<NoteDTO> getTasksNotes(Integer userID) throws SQLException {
        return NoteDAO.getTasksNotes(userID);
    }

    public static List<NoteDTO> getTasksNotes(Integer userID, boolean descending) throws SQLException {
        return NoteDAO.getTasksNotes(userID, descending);
    }

    public static Integer getTasksNumOfNotes(Integer userID) throws SQLException {
        return NoteDAO.getTasksNumOfNotes(userID);
    }

    public static boolean updateTinhTrang(Integer stateID, Integer noteID) throws SQLException {
        return NoteDAO.updateTinhTrang(stateID, noteID);
    }

    public static boolean insertNote(NoteDTO noteDTO) throws SQLException {
        return NoteDAO.insertNote(noteDTO);
    }

    public static boolean updateNote(NoteDTO noteDTO) throws SQLException{
        return NoteDAO.updateNote(noteDTO);
    }

    public static boolean deleteNote(Integer noteID) throws SQLException{
        return NoteDAO.deleteNote(noteID);
    }
}
