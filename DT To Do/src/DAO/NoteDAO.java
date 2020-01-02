package DAO;

import BUS.CategoryBUS;
import DTO.CategoryDTO;
import DTO.NoteDTO;
import helper.DBHelper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {
    public static List<NoteDTO> getToDoList(Integer categoryID, boolean descending) throws SQLException {
        Connection conn = DBHelper.getConnection();

        String query = descending ? "SELECT * FROM notes WHERE CategoryID = ? ORDER BY stateID DESC"
                                  : "SELECT * FROM notes WHERE CategoryID = ? ORDER BY stateID ASC";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, categoryID);
        ResultSet rs = statement.executeQuery();

        List<NoteDTO> output = new ArrayList<NoteDTO>();
        while (rs.next()) {
            LocalDate dueDate;
            if(rs.getTimestamp("dueDate") != null) {
                dueDate = rs.getTimestamp("dueDate").toLocalDateTime().toLocalDate();
            }
            else {
                dueDate = null;
            }
            NoteDTO note = new NoteDTO(
                    rs.getInt("noteID"),
                    rs.getInt("userID"),
                    rs.getInt("categoryID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("stateID"),
                    rs.getBoolean("isMyDay"),
                    rs.getBoolean("isImportance"),
                    rs.getTimestamp("createDate").toLocalDateTime().toLocalDate(),
                    dueDate
            );
            output.add(note);
        }
        conn.close();
        return output;
    }

    public static boolean updateTinhTrang(Integer stateID, Integer noteID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "UPDATE notes SET stateID = ? WHERE noteID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, stateID);
        statement.setInt(2, noteID);
        int records = statement.executeUpdate();
        conn.close();
        return records > 0;
    }


    public static boolean updateNote(NoteDTO noteDTO) throws SQLException{
        Connection conn = DBHelper.getConnection();
        String query = "UPDATE notes SET categoryID = ?, title = ?, description = ?, stateID = ?, isMyDay = ?, isImportance = ?, dueDate = ? WHERE noteID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        if (noteDTO.getCategoryID() != 0) {
            statement.setInt(1, noteDTO.getCategoryID());
        } else {
            statement.setNull(1, Types.INTEGER);
        }
        statement.setString(2,noteDTO.getTitle());
        statement.setString(3,noteDTO.getDescription());
        statement.setInt(4,noteDTO.getStateID());
        statement.setBoolean(5, noteDTO.getMyDay());
        statement.setBoolean(6, noteDTO.getImportance());
//        if (noteDTO.getDueDate() != null) {
//            statement.setDate(7, noteDTO.getDueDate());
//        } else {
//            statement.setDate(7, null);
//        }
        statement.setDate(7, noteDTO.getDueDate());
        statement.setInt(8, noteDTO.getNoteID());
        int records = statement.executeUpdate();
        conn.close();
        return records > 0;
    }

    public static boolean insertNote(NoteDTO noteDTO) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "INSERT INTO notes (userID, categoryID, title, description, stateID, isMyDay, isImportance, createDate, dueDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, noteDTO.getUserID());
        if (noteDTO.getCategoryID() != 0) {
            statement.setInt(2, noteDTO.getCategoryID());
        } else {
            statement.setNull(2, Types.INTEGER);
        }
        statement.setString(3, noteDTO.getTitle());
        statement.setString(4, noteDTO.getDescription());
        statement.setInt(5, noteDTO.getStateID());
        statement.setBoolean(6, noteDTO.getMyDay());
        statement.setBoolean(7, noteDTO.getImportance());
        statement.setDate(8, noteDTO.getCreateDate());
        statement.setDate(9, noteDTO.getDueDate());
        int records = statement.executeUpdate();
        conn.close();
        return records > 0;
    }

    public static boolean deleteNote(Integer noteID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "DELETE FROM notes WHERE noteID = " + noteID;
        Statement statement = conn.createStatement();
        int records = statement.executeUpdate(query);
        conn.close();
        return records > 0;
    }

    public static List<NoteDTO> getToDoList(Integer categoryID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT * FROM notes WHERE categoryID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, categoryID);
        ResultSet rs = statement.executeQuery();

        List<NoteDTO> output = new ArrayList<NoteDTO>();
        while (rs.next()) {
            LocalDate dueDate;
            if(rs.getTimestamp("dueDate") != null) {
                dueDate = rs.getTimestamp("dueDate").toLocalDateTime().toLocalDate();
            }
            else {
                dueDate = null;
            }
            NoteDTO note = new NoteDTO(
                    rs.getInt("noteID"),
                    rs.getInt("userID"),
                    rs.getInt("categoryID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("stateID"),
                    rs.getBoolean("isMyDay"),
                    rs.getBoolean("isImportance"),
                    rs.getTimestamp("createDate").toLocalDateTime().toLocalDate(),
                    dueDate
            );
            output.add(note);
        }
        conn.close();
        return output;
    }

    public static List<NoteDTO> getMyDayNotes(Integer userID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT * FROM notes WHERE userID = ? AND isMyDay = true";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();

        List<NoteDTO> output = new ArrayList<NoteDTO>();
        while (rs.next()) {
            LocalDate dueDate;
            if(rs.getTimestamp("dueDate") != null) {
                dueDate = rs.getTimestamp("dueDate").toLocalDateTime().toLocalDate();
            }
            else {
                dueDate = null;
            }
            NoteDTO note = new NoteDTO(
                    rs.getInt("noteID"),
                    rs.getInt("userID"),
                    rs.getInt("categoryID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("stateID"),
                    rs.getBoolean("isMyDay"),
                    rs.getBoolean("isImportance"),
                    rs.getTimestamp("createDate").toLocalDateTime().toLocalDate(),
                    dueDate
            );
            output.add(note);
        }
        conn.close();
        return output;
    }

    public static List<NoteDTO> getMyDayNotes(Integer userID, boolean descending) throws SQLException {
        Connection conn = DBHelper.getConnection();

        String query = descending ? "SELECT * FROM notes WHERE UserID = ?  AND isMyDay = true ORDER BY stateID DESC"
                : "SELECT * FROM notes WHERE UserID = ? AND isMyDay = true ORDER BY stateID ASC";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();

        List<NoteDTO> output = new ArrayList<NoteDTO>();
        while (rs.next()) {
            LocalDate dueDate;
            if(rs.getTimestamp("dueDate") != null) {
                dueDate = rs.getTimestamp("dueDate").toLocalDateTime().toLocalDate();
            }
            else {
                dueDate = null;
            }
            NoteDTO note = new NoteDTO(
                    rs.getInt("noteID"),
                    rs.getInt("userID"),
                    rs.getInt("categoryID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("stateID"),
                    rs.getBoolean("isMyDay"),
                    rs.getBoolean("isImportance"),
                    rs.getTimestamp("createDate").toLocalDateTime().toLocalDate(),
                    dueDate
            );
            output.add(note);
        }
        conn.close();
        return output;
    }


    public static Integer getMyDayNumOfNotes(Integer userID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT COUNT(*) AS NumOfNotes FROM Notes WHERE UserID = ? AND stateID = 12002 AND isMyDay = true ";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();
        Integer output = null;
        if(rs.next()) {
            output = rs.getInt("NumOfNotes");
        }
        conn.close();
        return output;
    }

    public static List<NoteDTO> getImportanceNotes(Integer userID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT * FROM notes WHERE userID = ? AND isImportance = true";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();

        List<NoteDTO> output = new ArrayList<NoteDTO>();
        while (rs.next()) {
            LocalDate dueDate;
            if(rs.getTimestamp("dueDate") != null) {
                dueDate = rs.getTimestamp("dueDate").toLocalDateTime().toLocalDate();
            }
            else {
                dueDate = null;
            }
            NoteDTO note = new NoteDTO(
                    rs.getInt("noteID"),
                    rs.getInt("userID"),
                    rs.getInt("categoryID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("stateID"),
                    rs.getBoolean("isMyDay"),
                    rs.getBoolean("isImportance"),
                    rs.getTimestamp("createDate").toLocalDateTime().toLocalDate(),
                    dueDate
            );
            output.add(note);
        }
        conn.close();
        return output;
    }
    public static List<NoteDTO> getImportanceNotes(Integer userID, boolean descending) throws SQLException {
        Connection conn = DBHelper.getConnection();

        String query = descending ? "SELECT * FROM notes WHERE UserID = ?  AND isImportance = true ORDER BY stateID DESC"
                : "SELECT * FROM notes WHERE UserID = ? AND isImportance = true ORDER BY stateID ASC";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();

        List<NoteDTO> output = new ArrayList<NoteDTO>();
        while (rs.next()) {
            LocalDate dueDate;
            if(rs.getTimestamp("dueDate") != null) {
                dueDate = rs.getTimestamp("dueDate").toLocalDateTime().toLocalDate();
            }
            else {
                dueDate = null;
            }
            NoteDTO note = new NoteDTO(
                    rs.getInt("noteID"),
                    rs.getInt("userID"),
                    rs.getInt("categoryID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("stateID"),
                    rs.getBoolean("isMyDay"),
                    rs.getBoolean("isImportance"),
                    rs.getTimestamp("createDate").toLocalDateTime().toLocalDate(),
                   dueDate
            );
            output.add(note);
        }
        conn.close();
        return output;
    }

    public static Integer getImportanceNumOfNotes(Integer userID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT COUNT(*) AS NumOfNotes FROM Notes WHERE UserID = ? AND stateID = 12002 AND isImportance = true ";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();
        Integer output = null;
        if(rs.next()) {
            output = rs.getInt("NumOfNotes");
        }
        conn.close();
        return output;
    }


    public static List<NoteDTO> getPlannedNotes(Integer userID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT * FROM notes WHERE userID = ? AND dueDate is not null";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();

        List<NoteDTO> output = new ArrayList<NoteDTO>();
        while (rs.next()) {
            NoteDTO note = new NoteDTO(
                    rs.getInt("noteID"),
                    rs.getInt("userID"),
                    rs.getInt("categoryID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("stateID"),
                    rs.getBoolean("isMyDay"),
                    rs.getBoolean("isImportance"),
                    rs.getTimestamp("createDate").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("dueDate").toLocalDateTime().toLocalDate()
            );
            output.add(note);
        }
        conn.close();
        return output;
    }

    public static List<NoteDTO> getPlannedNotes(Integer userID, boolean descending) throws SQLException {
        Connection conn = DBHelper.getConnection();

        String query = descending ? "SELECT * FROM notes WHERE UserID = ?  AND dueDate is not null ORDER BY stateID DESC"
                : "SELECT * FROM notes WHERE UserID = ? AND dueDate is not null ORDER BY stateID ASC";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();

        List<NoteDTO> output = new ArrayList<NoteDTO>();
        while (rs.next()) {
            NoteDTO note = new NoteDTO(
                    rs.getInt("noteID"),
                    rs.getInt("userID"),
                    rs.getInt("categoryID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("stateID"),
                    rs.getBoolean("isMyDay"),
                    rs.getBoolean("isImportance"),
                    rs.getTimestamp("createDate").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("dueDate").toLocalDateTime().toLocalDate()
            );
            output.add(note);
        }
        conn.close();
        return output;
    }

    public static Integer getPlannedNumOfNotes(Integer userID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT COUNT(*) AS NumOfNotes FROM Notes WHERE UserID = ? AND stateID = 12002 AND dueDate is not null";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();
        Integer output = null;
        if(rs.next()) {
            output = rs.getInt("NumOfNotes");
        }
        conn.close();
        return output;
    }


    public static List<NoteDTO> getTasksNotes(Integer userID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT * FROM notes WHERE userID = ? AND categoryID is null";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();

        List<NoteDTO> output = new ArrayList<NoteDTO>();
        while (rs.next()) {
            LocalDate dueDate;
            if(rs.getTimestamp("dueDate") != null) {
                dueDate = rs.getTimestamp("dueDate").toLocalDateTime().toLocalDate();
            }
            else {
                dueDate = null;
            }
            NoteDTO note = new NoteDTO(
                    rs.getInt("noteID"),
                    rs.getInt("userID"),
                    rs.getInt("categoryID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("stateID"),
                    rs.getBoolean("isMyDay"),
                    rs.getBoolean("isImportance"),
                    rs.getTimestamp("createDate").toLocalDateTime().toLocalDate(),
                    dueDate
            );
            output.add(note);
        }
        conn.close();
        return output;
    }

    public static List<NoteDTO> getTasksNotes(Integer userID, boolean descending) throws SQLException {
        Connection conn = DBHelper.getConnection();

        String query = descending ? "SELECT * FROM notes WHERE UserID = ?  AND categoryID is null ORDER BY stateID DESC"
                : "SELECT * FROM notes WHERE UserID = ? AND categoryID is null ORDER BY stateID ASC";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();

        List<NoteDTO> output = new ArrayList<NoteDTO>();
        while (rs.next()) {
            LocalDate dueDate;
            if(rs.getTimestamp("dueDate") != null) {
                dueDate = rs.getTimestamp("dueDate").toLocalDateTime().toLocalDate();
            }
            else {
                dueDate = null;
            }
            NoteDTO note = new NoteDTO(
                    rs.getInt("noteID"),
                    rs.getInt("userID"),
                    rs.getInt("categoryID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("stateID"),
                    rs.getBoolean("isMyDay"),
                    rs.getBoolean("isImportance"),
                    rs.getTimestamp("createDate").toLocalDateTime().toLocalDate(),
                    dueDate
            );
            output.add(note);
        }
        conn.close();
        return output;
    }

    public static Integer getTasksNumOfNotes(Integer userID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "SELECT COUNT(*) AS NumOfNotes FROM Notes WHERE UserID = ? AND stateID = 12002 AND CategoryID is null";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();
        Integer output = null;
        if(rs.next()) {
            output = rs.getInt("NumOfNotes");
        }
        conn.close();
        return output;
    }
}
