package custom;

import BUS.CategoryBUS;
import DTO.CategoryDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
        Font font = Font.font("system", 20);
        tfCategoryName = new TextField(categoryBox.getCategory().getTenPhanLoai());
        tfCategoryName.setFont(font);
        tfCategoryName.setPrefHeight(10);
        tfCategoryName.setMinWidth(20);
        computeWidth();
        tfCategoryName.setAlignment(Pos.CENTER_LEFT);
        tfCategoryName.setOnMouseClicked(e-> {
            if(tfCategoryName.isFocused()) {
                tfCategoryName.selectAll();
                picker.setId("menubutton");
                this.setId("mybox");
            }
        });

        tfCategoryName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                computeWidth();
            }
        });
        tfCategoryName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!tfCategoryName.isFocused() && !picker.isFocused()) {
                    try {
                        CategoryDTO category = categoryBox.getCategory();
                        if(picker.getText() == null) {
                            category.setIcon("☰");
                        }
                        else {
                            category.setIcon(picker.getText());
                        }
                        category.setTenPhanLoai(tfCategoryName.getText());
                        CategoryBUS.updateCategory(category);
                        Runnable reloadMenuPane = (Runnable) getUserData();
                        reloadMenuPane.run();
                    } catch (SQLException e) {
                        System.out.println("UPDATE FAILED");
                        e.printStackTrace();
                    }
                }
            }
        });
        picker.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!picker.isFocused() && !tfCategoryName.isFocused()) {
                    try {
                        CategoryDTO category = categoryBox.getCategory();
                        if(picker.getText() == null) {
                            category.setIcon("☰");
                        }
                        else
                        {
                            category.setIcon(picker.getText());
                        }
                        category.setTenPhanLoai(tfCategoryName.getText());
                        CategoryBUS.updateCategory(category);
                        Runnable reloadMenuPane = (Runnable) getUserData();
                        reloadMenuPane.run();
                    } catch (SQLException e) {
                        System.out.println("UPDATE FAILED");
                        e.printStackTrace();
                    }
                }
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
        lbCategoryName.setPrefHeight(10);
        lbCategoryName.setAlignment(Pos.CENTER_LEFT);

        this.getStylesheets().add("resources/css/editableCategory.css");
        //this.setPrefWidth(330);
        this.setAlignment(Pos.CENTER_LEFT);
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

    public void setFocusTextfield() {
        tfCategoryName.requestFocus();
        tfCategoryName.selectAll();
        picker.setId("menubutton");
        this.setId("mybox");
    }
    public void computeWidth() {
        Text t = new Text(tfCategoryName.getText());
        t.setFont(Font.font("system", 20));
        tfCategoryName.setPrefWidth(t.getLayoutBounds().getWidth() + 12);
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
