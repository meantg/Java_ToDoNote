package custom;

import BUS.CategoryBUS;
import DTO.CategoryDTO;
import controller.MainController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
        lbIcon.setPrefSize(70,10);
        lbIcon.setFont(Font.font("system", 20));
        lbIcon.setAlignment(Pos.CENTER);

        lbTitle = new Label();
        lbTitle.setText(category.getTenPhanLoai());
        lbTitle.setAlignment(Pos.CENTER_LEFT);
        lbTitle.setFont(Font.font("system", 20));
        lbTitle.setPrefSize(150,10);

        Integer numOfNotes = CategoryBUS.getNumOfNotesByMaPhanLoai(category.getMaPhanLoai());
        lbNumOfNotes = new Label();
        lbNumOfNotes.setText(numOfNotes.toString());
        lbNumOfNotes.setAlignment(Pos.CENTER_RIGHT);
        lbNumOfNotes.setPrefSize(70,10);
        lbNumOfNotes.setFont(Font.font("system", 20));

        this.setPrefWidth(330);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(10));
        this.getChildren().clear();
        this.getChildren().addAll(lbIcon,lbTitle, lbNumOfNotes);
        if (numOfNotes > 0) {
            lbNumOfNotes.setVisible(true);
        } else {
            lbNumOfNotes.setVisible(false);
        }


        contextMenu = new ContextMenu();
        VBox contentItems = new VBox();
        contentItems.setPrefWidth(300);
        Button b = new Button("Delete");
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    CategoryBUS.deleteCategory(getCategory().getMaPhanLoai());
                    Runnable deleteCategoryBox = (Runnable) getUserData();
                    deleteCategoryBox.run();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        Button b2 = new Button("Rename");
        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Rename");
                CreateEditCategoryBox();
            }
        });
        contentItems.getChildren().addAll(b2, b);
        CustomMenuItem item = new CustomMenuItem(contentItems);
/*        item1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    CategoryBUS.deleteCategory(getCategory().getMaPhanLoai());
                    Runnable reloadMenuPane = (Runnable) getUserData();
                    reloadMenuPane.run();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });*/
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
        editableCategoryBox.setFocusTextfield();
        editableCategoryBox.setUserData(reloadMenuPane);
        this.getChildren().clear();
        this.getChildren().add(editableCategoryBox);
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
