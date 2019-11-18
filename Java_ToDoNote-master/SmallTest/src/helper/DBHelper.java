package helper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBHelper {
    public static Connection getConnection() throws SQLException {
        String connString = "jdbc:mysql://localhost:3306/ToDoList?autoReconnect=true&useSSL=false";
        String username = "root";
        String password = "1587397561";
        return DriverManager.getConnection(connString, username, password);
    }
}