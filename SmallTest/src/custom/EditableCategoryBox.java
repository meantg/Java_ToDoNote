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
    private TextField tfCategoryName;
    private Label lbCategoryName;
    private Label lbIcon;
    public EditableCategoryBox(CategoryBox categoryBox) {
        IconPickerBox picker = new IconPickerBox();
        picker.setPrefSize(50,10);
        picker.setFont(Font.font("system", 20));
        picker.setText(categoryBox.getCategory().getIcon());

        lbIcon = new Label();
        lbIcon.setPrefSize(50,10);
        lbIcon.setPadding(new Insets(10));
        lbIcon.setAlignment(Pos.CENTER);
        lbIcon.setFont(Font.font("system", 20));
        lbIcon.setText(categoryBox.getCategory().getIcon());
/*
        if(picker.getText().isEmpty()) {
            picker.setVisible(false);
        }
*/

        tfCategoryName = new TextField(categoryBox.getCategory().getTenPhanLoai());
        tfCategoryName.setFont(Font.font("system", 20));
        tfCategoryName.setPrefSize(180, 10);
        tfCategoryName.setAlignment(Pos.CENTER_LEFT);
        tfCategoryName.setOnMouseClicked(e-> {
            if(tfCategoryName.isFocused())
                tfCategoryName.selectAll();
        });

        lbCategoryName = new Label(categoryBox.getCategory().getTenPhanLoai());
        lbCategoryName.setPadding(new Insets(10));
        lbCategoryName.setFont(Font.font("system", 20));
        lbCategoryName.setPrefSize(180, 10);
        lbCategoryName.setAlignment(Pos.CENTER_LEFT);

        this.getStylesheets().add("custom/editableCategory.css");
        this.setId("mybox");
        this.setPrefWidth(330);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(10));
        this.getChildren().clear();
        if(categoryBox.isEditable()) {
            this.getChildren().addAll(picker, tfCategoryName);
        }
        else {
            this.getChildren().addAll(lbIcon, lbCategoryName);

        }
    }
}
