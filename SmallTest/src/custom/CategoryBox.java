package custom;

import BUS.CategoryBUS;
import DTO.CategoryDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class CategoryBox extends HBox {
    private int categoryID;
    private Label lbIcon;
    private Label lbTitle;
    private Label lbNumOfNotes;
    private boolean isClicked = false;
    public CategoryBox(CategoryDTO category) throws SQLException {
        this.categoryID = category.getMaPhanLoai();

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

        Integer numOfNotes = CategoryBUS.getNumOfNotesByMaPhanLoai(categoryID);
        lbNumOfNotes = new Label();
        lbNumOfNotes.setText(numOfNotes.toString());
        lbNumOfNotes.setAlignment(Pos.CENTER_RIGHT);
        lbNumOfNotes.setPrefSize(70,10);
        lbNumOfNotes.setFont(Font.font("system", 20));

        this.setPrefWidth(330);
        this.setPadding(new Insets(10));
        this.getChildren().clear();
        this.getChildren().addAll(lbIcon,lbTitle);
        if(numOfNotes > 0) this.getChildren().add(lbNumOfNotes);

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

    public void changeBackgroundColor(Color color) {
        this.setBackground(
                new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public int getTypeCategory() {
        return categoryID;
    }

}
