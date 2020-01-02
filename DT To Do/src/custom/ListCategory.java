package custom;

import DTO.CategoryDTO;
import controller.MainController;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListCategory {
    private List<CategoryBox> listCategory;
    private CategoryBox curCategoryBox = null;

    public ListCategory(List<CategoryDTO> dsPhanLoai) {
        listCategory = new ArrayList<CategoryBox>();
        dsPhanLoai.stream().forEach(phanLoai -> {
            try {
                CategoryBox category = new CategoryBox(phanLoai);
                category.updateNumOfNotes();
//                if (curCategoryBox == null) {
//                    curCategoryBox = category;
//                    curCategoryBox.changeBackgroundColor(Color.LIGHTGRAY);
//                }
                category.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if(curCategoryBox != null)
                        curCategoryBox.changeBackgroundColor(Color.TRANSPARENT);
                    curCategoryBox = category;
                    category.changeBackgroundColor(Color.LIGHTGRAY);
                });
                listCategory.add(category);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public List<CategoryBox> getList() {
        return listCategory;
    }

    public CategoryBox getCurCategory() { return curCategoryBox; }

    public void setCurCategory(CategoryBox categoryBox) {
        if(curCategoryBox != null)
            curCategoryBox.changeBackgroundColor(Color.TRANSPARENT);
        curCategoryBox = categoryBox;
        curCategoryBox.changeBackgroundColor(Color.LIGHTGRAY);
    }
}
