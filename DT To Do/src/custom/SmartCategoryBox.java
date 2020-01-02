package custom;

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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class SmartCategoryBox extends HBox {
    private WeatherIconView weatherIconView;
    private MaterialDesignIconView materialDesignIconView;
    private Icons525View icons525View;
    private Label lbCategoryName;
    private Label lbNumOfNotes;
    private Integer numOfNotes;

    public SmartCategoryBox(CategoryDTO category) {
        this.getChildren().clear();
        switch(category.getCategoryName()) {
            case "My Day":
                weatherIconView = new WeatherIconView(WeatherIcon.DAY_SUNNY);
                weatherIconView.setGlyphSize(20);
                weatherIconView.setFill(Color.valueOf("#ffa100"));
                this.getChildren().add(weatherIconView);
                try {
                    numOfNotes = NoteBUS.getMyDayNumOfNotes(category.getUserID());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "Importance":
                materialDesignIconView = new MaterialDesignIconView(MaterialDesignIcon.STAR_OUTLINE);
                materialDesignIconView.setGlyphSize(24);
                materialDesignIconView.setFill(Color.valueOf("#e45a5a"));


                this.getChildren().add(materialDesignIconView);
                try {
                    numOfNotes = NoteBUS.getImportanceNumOfNotes(category.getUserID());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "Planned":
                materialDesignIconView = new MaterialDesignIconView(MaterialDesignIcon.CALENDAR_TODAY);
                materialDesignIconView.setGlyphSize(24);
                materialDesignIconView.setFill(Color.valueOf("#5acd20"));

                this.getChildren().add(materialDesignIconView);
                try {
                    numOfNotes = NoteBUS.getPlannedNumOfNotes(category.getUserID());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "Tasks":
                icons525View = new Icons525View(Icons525.HOME);
                icons525View.setGlyphSize(24);
                icons525View.setFill(Color.valueOf("#e490c4"));

                this.getChildren().add(icons525View);
                try {
                    numOfNotes = NoteBUS.getTasksNumOfNotes(category.getUserID());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("ERROR");
                break;
        }
        lbNumOfNotes = new Label();
        lbNumOfNotes.setText(numOfNotes.toString());
        lbNumOfNotes.setAlignment(Pos.CENTER_RIGHT);
        //lbNumOfNotes.setPrefSize(50,10);
        lbNumOfNotes.setFont(Font.font("system", 16));

        lbCategoryName = new Label();
        lbCategoryName.setText(category.getCategoryName());
        lbCategoryName.setAlignment(Pos.CENTER_LEFT);
        lbCategoryName.setFont(Font.font("system", 16));
        //lbTitle.setPrefSize(230,10);
        HBox.setHgrow(lbCategoryName, Priority.ALWAYS);




        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);

        this.setPrefSize(295, 45);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(15);
        this.setPadding(new Insets(0, 15,0,15));

        this.getChildren().addAll(lbCategoryName, spacer, lbNumOfNotes);
        if (numOfNotes > 0) {
            lbNumOfNotes.setVisible(true);
        } else {
            lbNumOfNotes.setVisible(false);
        }
    }

    public void reloadBox(Integer userID) {
        switch(getName()) {
            case "My Day":
                try {
                    numOfNotes = NoteBUS.getMyDayNumOfNotes(userID);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "Importance":
                try {
                    numOfNotes = NoteBUS.getImportanceNumOfNotes(userID);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "Planned":
                try {
                    numOfNotes = NoteBUS.getPlannedNumOfNotes(userID);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "Tasks":
                try {
                    numOfNotes = NoteBUS.getTasksNumOfNotes(userID);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("ERROR");
                break;
        }
        lbNumOfNotes.setText(numOfNotes.toString());
        lbNumOfNotes.setAlignment(Pos.CENTER_RIGHT);
        lbNumOfNotes.setFont(Font.font("system", 16));
        if(numOfNotes > 0) {
            lbNumOfNotes.setVisible(true);
        }
        else {
            lbNumOfNotes.setVisible(false);
        }
    }

    public String getName() {
        return lbCategoryName.getText();
    }

    public void changeBackgroundColor(Color color) {
        this.setBackground(
                new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
