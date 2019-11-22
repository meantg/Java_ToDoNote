package custom;

import BUS.CategoryBUS;
import DTO.CategoryDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
        contextMenu.setPrefWidth(300);
        MenuItem item1 = new MenuItem("Delete");
        item1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                editCategoryBox();
            }
        });
        contextMenu.getItems().addAll(item1);

        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isSecondaryButtonDown()) {
                    showContextMenu(event);
                }
            }
        });

        /*
        this.setOnMouseEntered(e -> {
            this.setBackground(
                    new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        });

        this.setOnMouseExited(e -> {
            if(!isClicked)
            this.setBackground(
                    new Background(new BackgroundFill(Color.rgb(244, 244, 244), CornerRadii.EMPTY, Insets.EMPTY)));
        });
        */
    }

    public void showContextMenu(MouseEvent event) {
        contextMenu.show(this, Side.TOP, event.getSceneX(), 0);
    }
    public void editCategoryBox() {
        //TODO: set auto focus textfield
        EditableCategoryBox editableCategoryBox = new EditableCategoryBox(this);
        editableCategoryBox.setEditable(true);
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
    public void setEditable(boolean b) {
        isEditable = b;
    }
}
