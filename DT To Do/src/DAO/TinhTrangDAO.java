package DAO;

import DTO.TinhTrangDTO;
import helper.DBHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TinhTrangDAO {

    public static List<TinhTrangDTO> getDSTinhTrang() throws SQLException {
        Connection conn = DBHelper.getConnection();
        try {
            String query = "SELECT * FROM State";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            List<TinhTrangDTO> output = new ArrayList<TinhTrangDTO>();
            while (rs.next()) {
                TinhTrangDTO tinhTrang = new TinhTrangDTO(rs.getInt("stateID"), rs.getString("stateName"));
                output.add(tinhTrang);
            }
            return output;
        } finally {
            conn.close();
        }
    }
}
