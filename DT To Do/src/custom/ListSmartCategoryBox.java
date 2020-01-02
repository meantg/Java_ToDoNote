package custom;

import BUS.NoteBUS;
import DTO.CategoryDTO;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListSmartCategoryBox{
    private static List<SmartCategoryBox> listSmartCategoryBox;
    private SmartCategoryBox curSmartCategoryBox;
    //TODO: coi lại load smart category


    public ListSmartCategoryBox(List<CategoryDTO> listSmartCategories) {
        listSmartCategoryBox = new ArrayList<SmartCategoryBox>();
        try {
            listSmartCategories.stream().forEach(smartCategory -> {
                SmartCategoryBox smartCategoryBox = new SmartCategoryBox(smartCategory);
                smartCategoryBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if(curSmartCategoryBox != null)
                        curSmartCategoryBox.changeBackgroundColor(Color.TRANSPARENT);
                    curSmartCategoryBox = smartCategoryBox;
                    smartCategoryBox.changeBackgroundColor(Color.LIGHTGRAY);
                });
                listSmartCategoryBox.add(smartCategoryBox);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<SmartCategoryBox> getList() {
        return listSmartCategoryBox;
    }

    public SmartCategoryBox getCurSmartCategoryBox() {
        return curSmartCategoryBox;
    }

    public void setCurSmartCategory(SmartCategoryBox smartCategoryBox) {
        if(curSmartCategoryBox != null)
            curSmartCategoryBox.changeBackgroundColor(Color.TRANSPARENT);
        curSmartCategoryBox = smartCategoryBox;
        curSmartCategoryBox.changeBackgroundColor(Color.LIGHTGRAY);
    }
}
