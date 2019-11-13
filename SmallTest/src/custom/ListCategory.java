package custom;

import DTO.CategoryDTO;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListCategory {
    private List<CategoryBox> listCategory;
    private CategoryBox curCategory;

    public ListCategory(List<CategoryDTO> dsPhanLoai) {
        listCategory = new ArrayList<CategoryBox>();
        curCategory = null;

        dsPhanLoai.stream().forEach(phanLoai -> {
            try {
                CategoryBox category = new CategoryBox(phanLoai);


                if (curCategory == null) {
                    curCategory = category;
                    curCategory.changeBackgroundColor(Color.LIGHTGRAY);
                }
                category.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    curCategory.changeBackgroundColor(Color.rgb(244, 244, 244));
                    curCategory = category;
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
}
