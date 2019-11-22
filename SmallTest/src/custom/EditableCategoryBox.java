package custom;

import BUS.CategoryBUS;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.sql.SQLException;


public class EditableCategoryBox extends HBox {
    private IconPickerBox picker;
    private TextField tfCategoryName;
    private Label lbCategoryName;
    private Label lbIcon;
    public EditableCategoryBox(CategoryBox categoryBox) {
        picker = new IconPickerBox();
        picker.setFont(Font.font("system", 20));
        picker.setPadding(new Insets(5));
        picker.setText(categoryBox.getCategory().getIcon());

        lbIcon = new Label();
        lbIcon.setAlignment(Pos.CENTER_LEFT);
        lbIcon.setPadding(new Insets(5));
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
        tfCategoryName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!tfCategoryName.isFocused())
                    requestFocus();
            }
        });

        this.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(isFocused()) {
                    System.out.println("focus box");
                }
                else {
                    System.out.println("unfocus");
                }
            }
        });
        lbCategoryName = new Label(categoryBox.getCategory().getTenPhanLoai());
        lbCategoryName.setFont(Font.font("system", 20));
        lbCategoryName.setPadding(new Insets(10));
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

    public void updateCategory(CategoryBox categoryBox) throws SQLException {
        CategoryBUS.updateCategory(categoryBox.getCategory());
    }

    public void setEditable(boolean flags) {
        this.getChildren().clear();
        if(flags) {
            this.getChildren().addAll(picker, tfCategoryName);
        }
        else {
            this.getChildren().addAll(lbIcon, lbCategoryName);
        }
    }
}
