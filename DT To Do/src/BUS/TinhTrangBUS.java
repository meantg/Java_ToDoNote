package BUS;

import DAO.TinhTrangDAO;
import DTO.TinhTrangDTO;

import java.sql.SQLException;
import java.util.List;

public class TinhTrangBUS {

    public static List<TinhTrangDTO> getDSTinhTrang() throws SQLException {
        return TinhTrangDAO.getDSTinhTrang();
    }

}