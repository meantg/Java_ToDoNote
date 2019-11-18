package BUS;

import java.sql.SQLException;
import java.util.List;

import DAO.TinhTrangDAO;
import DTO.TinhTrangDTO;

public class TinhTrangBUS {

    public static List<TinhTrangDTO> getDSTinhTrang() throws SQLException {
        return TinhTrangDAO.getDSTinhTrang();
    }

}