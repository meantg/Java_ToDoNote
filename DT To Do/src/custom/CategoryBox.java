package custom;

import BUS.CategoryBUS;
import DTO.CategoryDTO;
import de.jensd.fx.glyphs.icons525.Icons525;
import de.jensd.fx.glyphs.icons525.Icons525View;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.swing.*;
import java.sql.SQLException;

public class CategoryBox extends HBox {
    private CategoryDTO category;
    private Label lbIcon;
    private Label lbTitle;
    private Label lbNumOfNotes;
    final ContextMenu contextMenu;
    private boolean isClicked = false;
    public CategoryBox(CategoryDTO category) throws SQLException {
        this.category = category;

        lbIcon = new Label();
        lbIcon.setText(category.getIcon());
        //lbIcon.setPrefSize(50,10);
        lbIcon.setFont(Font.font("system", 16));
        lbIcon.setAlignment(Pos.CENTER);

        lbTitle = new Label();
        lbTitle.setText(category.getCategoryName());
        lbTitle.setAlignment(Pos.CENTER_LEFT);
        lbTitle.setFont(Font.font("system", 16));
        //lbTitle.setPrefSize(230,10);
        HBox.setHgrow(lbTitle, Priority.ALWAYS);

        Integer numOfNotes = category.getNumOfNotes();
        lbNumOfNotes = new Label();
        lbNumOfNotes.setText(numOfNotes.toString());
        lbNumOfNotes.setAlignment(Pos.CENTER_RIGHT);
        //lbNumOfNotes.setPrefSize(50,10);
        lbNumOfNotes.setFont(Font.font("system", 16));

        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);

        this.setPrefSize(295, 45);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(15);
        this.setPadding(new Insets(0, 15,0,15));
        this.getChildren().clear();
        this.getChildren().addAll(lbIcon,lbTitle, spacer, lbNumOfNotes);
        if (numOfNotes > 0) {
            lbNumOfNotes.setVisible(true);
        } else {
            lbNumOfNotes.setVisible(false);
        }

        contextMenu = new ContextMenu();
        Icons525View icons525View = new Icons525View(Icons525.REMOVE);
        icons525View.setGlyphSize(20);
        icons525View.setFill(Color.valueOf("#d41124"));
        HBox remove = new HBox();
        remove.setMaxSize(265, 35);
        remove.setMinSize(265,35);
        remove.setPrefSize(265, 35);
        Label lbRemove = new Label();
        lbRemove.setText("Delete");
        lbRemove.setFont(Font.font("system", 14));
        lbRemove.setTextFill(Color.valueOf("#d41124"));
        remove.setSpacing(25);
        remove.setAlignment(Pos.CENTER_LEFT);
        remove.setPadding(new Insets(10, 15,10, 15));
        remove.getChildren().addAll(icons525View, lbRemove);
        remove.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if(CategoryBUS.deleteCategory(category.getCategoryID())) {
                        Runnable deleteCategoryBox = (Runnable) getUserData();
                        deleteCategoryBox.run();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        HBox rename = new HBox();
        MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.PENCIL);
        icon.setGlyphSize(20);
        icon.setFill(Color.LIGHTGRAY);
        Label lbRename = new Label();
        lbRename.setText("Rename list");
        lbRename.setFont(Font.font("system", 14));
        rename.setSpacing(25);
        rename.setAlignment(Pos.CENTER_LEFT);
        rename.setPadding(new Insets(10, 15,10, 15));
        rename.getChildren().addAll(icon, lbRename);
        rename.setMaxSize(265, 35);
        rename.setMinSize(265,35);
        rename.setPrefSize(265, 35);

        rename.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CreateEditCategoryBox();
            }
        });
        //contentItems.getChildren().addAll(btnRename, btnDelete);
        CustomMenuItem item1 = new CustomMenuItem(rename);
        CustomMenuItem item2 = new CustomMenuItem(remove);
        contextMenu.getItems().addAll(item1, item2);

        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isSecondaryButtonDown()) {
                    showContextMenu(event);
                }
            }
        });

    }

    public void showContextMenu(MouseEvent event) {
        contextMenu.show(this, Side.TOP, 10, 0);
    }

    public void CreateEditCategoryBox() {
        //TODO: set auto focus textfield
        Runnable reloadMenuPane = (Runnable) getChildren().get(0).getUserData();
        EditableCategoryBox editableCategoryBox = new EditableCategoryBox(this);
        editableCategoryBox.setUserData(reloadMenuPane);
        this.getChildren().clear();
        this.getChildren().add(editableCategoryBox);
        editableCategoryBox.setFocusTextfield();
    }

    public void changeBackgroundColor(Color color) {
        this.setBackground(
                new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void updateNumOfNotes() throws SQLException {
        Integer numOfNotes = CategoryBUS.getNumOfNotesByID(category.getCategoryID());
        lbNumOfNotes.setText(numOfNotes.toString());
        if (numOfNotes > 0) {
            lbNumOfNotes.setVisible(true);
        } else {
            lbNumOfNotes.setVisible(false);
        }
    }

    public CategoryDTO getCategory() {
        return category;
    }
}
