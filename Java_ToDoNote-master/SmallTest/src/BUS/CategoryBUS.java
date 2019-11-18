package BUS;

import DAO.CategoryDAO;
import DTO.CategoryDTO;

import java.sql.SQLException;
import java.util.List;

public class CategoryBUS {
    public static List<CategoryDTO> getListCategory(Integer maNguoiDung) throws SQLException {
        return CategoryDAO.getListCategory(maNguoiDung);
    }

    public static Integer getNumOfNotesByMaPhanLoai(Integer maPhanLoai) throws SQLException {
        return CategoryDAO.getNumOfNotesByMaPhanLoai(maPhanLoai);
    }

    public static String getTenPhanLoaiByMa(Integer maPhanLoai) throws SQLException {
        return CategoryDAO.getTenPhanLoaiByMa(maPhanLoai);
    }
}
