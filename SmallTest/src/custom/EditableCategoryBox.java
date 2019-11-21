package custom;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;


public class EditableCategoryBox extends HBox {
    private IconPickerBox picker;
    private TextField lbCategoryName;

    public EditableCategoryBox() {
        IconPickerBox picker = new IconPickerBox();
        picker.setPrefSize(70,10);
        picker.setFont(Font.font("system", 20));
        picker.setText("📝");
/*
        if(picker.getText().isEmpty()) {
            picker.setVisible(false);
        }
*/

        lbCategoryName = new TextField("Untitled List");
        lbCategoryName.setFont(Font.font("system", 20));
        lbCategoryName.setPrefSize(180, 10);
        lbCategoryName.setAlignment(Pos.CENTER_LEFT);
        lbCategoryName.setOnMouseClicked(e-> {
            if(lbCategoryName.isFocused())
                lbCategoryName.selectAll();
        });


        this.getStylesheets().add("custom/editableCategory.css");
        this.setPrefWidth(330);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(10));
        this.getChildren().clear();
        this.getChildren().addAll(picker, lbCategoryName);

    }
}
