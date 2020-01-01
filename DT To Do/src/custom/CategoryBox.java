package custom;

import BUS.CategoryBUS;
import DTO.CategoryDTO;
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

import java.sql.SQLException;

public class CategoryBox extends HBox {
    private CategoryDTO category;
    private Label lbIcon;
    private Label lbTitle;
    private Label lbNumOfNotes;
    final ContextMenu contextMenu;
    private boolean isClicked = false;
    private boolean isEditable = false;
    public CategoryBox(CategoryDTO category) throws SQLException {
        this.category = category;

        lbIcon = new Label();
        lbIcon.setText(category.getIcon());
        //lbIcon.setPrefSize(50,10);
        lbIcon.setFont(Font.font("system", 16));
        lbIcon.setAlignment(Pos.CENTER);

        lbTitle = new Label();
        lbTitle.setText(category.getTenPhanLoai());
        lbTitle.setAlignment(Pos.CENTER_LEFT);
        lbTitle.setFont(Font.font("system", 16));
        //lbTitle.setPrefSize(230,10);
        HBox.setHgrow(lbTitle, Priority.ALWAYS);

        Integer numOfNotes = CategoryBUS.getNumOfNotesByMaPhanLoai(category.getMaPhanLoai());
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
        VBox contentItems = new VBox();
        contentItems.setPrefWidth(300);
        Button btnDelete = new Button("Delete");
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(CategoryBUS.deleteCategory(category.getMaPhanLoai())) {
                        Runnable deleteCategoryBox = (Runnable) getUserData();
                        deleteCategoryBox.run();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        Button btnRename = new Button("Rename");
        btnRename.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CreateEditCategoryBox();
            }
        });
        contentItems.getChildren().addAll(btnRename, btnDelete);
        CustomMenuItem item = new CustomMenuItem(contentItems);
        contextMenu.getItems().addAll(item);
    }

    public void showContextMenu(MouseEvent event) {
        contextMenu.show(this, Side.TOP, 10, 0);
    }
    public void CreateEditCategoryBox() {
        //TODO: set auto focus textfield
        Runnable reloadMenuPane = (Runnable) getChildren().get(0).getUserData();
        EditableCategoryBox editableCategoryBox = new EditableCategoryBox(this);
        editableCategoryBox.setEditable(true);
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
        Integer numOfNotes = CategoryBUS.getNumOfNotesByMaPhanLoai(category.getMaPhanLoai());
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

    public boolean isEditable() {return isEditable; }

    public void setEditable(boolean flag) {
        isEditable = flag;
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isSecondaryButtonDown()) {
                    showContextMenu(event);
                }
            }
        });

    }
}
