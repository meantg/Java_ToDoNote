package custom;

import BUS.CategoryBUS;
import BUS.NoteBUS;
import DTO.CategoryDTO;
import de.jensd.fx.glyphs.icons525.Icons525;
import de.jensd.fx.glyphs.icons525.Icons525View;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.weathericons.WeatherIcon;
import de.jensd.fx.glyphs.weathericons.WeatherIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.SQLException;


public class EditableCategoryBox extends HBox {
    private IconPickerBox picker;
    private TextField tfCategoryName;
    private Label lbCategoryName;
    public EditableCategoryBox(CategoryBox categoryBox) {
        this.getChildren().clear();

        picker = new IconPickerBox();
        picker.setFont(Font.font("system", 20));
        picker.setPadding(new Insets(5));
        picker.setText(categoryBox.getCategory().getIcon());

/*
        if(picker.getText().isEmpty()) {
            picker.setVisible(false);
        }
*/
        Font font = Font.font("system", 20);
        tfCategoryName = new TextField(categoryBox.getCategory().getCategoryName());
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
                        category.setCategoryName(tfCategoryName.getText());
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
                        category.setCategoryName(tfCategoryName.getText());
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

        this.getStylesheets().add("resources/css/editableCategory.css");
        //this.setPrefWidth(330);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(picker, tfCategoryName);
    }

    public EditableCategoryBox(SmartCategoryBox smartCategoryBox) {
        this.getChildren().clear();
        switch(smartCategoryBox.getName()) {
            case "My Day":
                System.out.println("SUNNY");
                WeatherIconView weatherIconView = new WeatherIconView(WeatherIcon.DAY_SUNNY);
                weatherIconView.setGlyphSize(20);
                weatherIconView.setFill(Color.valueOf("#ffa100"));
                this.getChildren().add(weatherIconView);
                break;
            case "Importance":
                MaterialDesignIconView materialDesignIconView = new MaterialDesignIconView(MaterialDesignIcon.STAR_OUTLINE);
                materialDesignIconView.setGlyphSize(24);
                materialDesignIconView.setFill(Color.valueOf("#e45a5a"));
                this.getChildren().add(materialDesignIconView);
                break;
            case "Planned":
                materialDesignIconView = new MaterialDesignIconView(MaterialDesignIcon.CALENDAR_TODAY);
                materialDesignIconView.setGlyphSize(24);
                materialDesignIconView.setFill(Color.valueOf("#5acd20"));
                this.getChildren().add(materialDesignIconView);
                break;
            case "Tasks":
                Icons525View icons525View = new Icons525View(Icons525.HOME);
                icons525View.setGlyphSize(24);
                icons525View.setFill(Color.valueOf("#e490c4"));

                this.getChildren().add(icons525View);
                break;
            default:
                System.out.println("ERROR");
                break;
        }

        lbCategoryName = new Label(smartCategoryBox.getName());
        lbCategoryName.setFont(Font.font("system", 20));
        lbCategoryName.setPadding(new Insets(10));
        lbCategoryName.setPrefHeight(10);
        lbCategoryName.setAlignment(Pos.CENTER_LEFT);

        this.getStylesheets().add("resources/css/editableCategory.css");
        //this.setPrefWidth(330);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().add(lbCategoryName);
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
}
