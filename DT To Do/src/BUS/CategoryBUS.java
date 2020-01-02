package BUS;

import DAO.CategoryDAO;
import DTO.CategoryDTO;

import java.sql.SQLException;
import java.util.List;

public class CategoryBUS {
    public static List<CategoryDTO> getListCategory(Integer userID) throws SQLException {
        return CategoryDAO.getListCategory(userID);
    }

    public static Integer getNumOfNotesByID(Integer categoryID) throws SQLException {
        return CategoryDAO.getNumOfNotesByID(categoryID);
    }

    public static String getCategoryNameByID(Integer categoryID) throws SQLException {
        return CategoryDAO.getCategoryNameByID(categoryID);
    }

    public static boolean insertCategory(CategoryDTO category) throws SQLException {
        return CategoryDAO.insertCategory(category);
    }

    public static boolean updateCategory(CategoryDTO category) throws SQLException {
        return CategoryDAO.updateCategory(category);
    }

    public static boolean deleteCategory(Integer categoryID) throws SQLException {
        return CategoryDAO.deleteCategory(categoryID);
    }
}
