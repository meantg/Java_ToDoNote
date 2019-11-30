package BUS;

import DAO.UserDAO;
import DTO.UserDTO;

import java.sql.SQLException;

public class UserBUS {
    public static boolean checkLoginUser(String username, String password) throws SQLException {
        return UserDAO.checkLoginUser(username, password);
    }

    public static boolean insertUser(UserDTO user) throws SQLException {
        if(UserDAO.checkInsUser(user)){
            return false;
        }
        return UserDAO.insertUser(user);
    }

    public static UserDTO getUserByUsername(String username) throws SQLException {
        return UserDAO.getUserByUsername(username);
    }
}
