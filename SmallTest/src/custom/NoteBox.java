package custom;

import BUS.CategoryBUS;
import DTO.NoteDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;
import java.sql.SQLException;

public class NoteBox extends HBox {
    private CheckBox checkBtn;
    private Label lbTitle;
    private Label lbCategory;

    public NoteBox(NoteDTO note) throws SQLException {
        checkBtn = new CheckBox();
        checkBtn.setPrefSize(30,40);

        VBox contentBox = new VBox(3);
        lbTitle = new Label(note.getTieuDe());
        lbTitle.setPrefSize(400, 30);
        lbTitle.setFont(Font.font("System", 20));

        lbCategory = new Label(CategoryBUS.getTenPhanLoaiByMa(note.getMaPhanLoai()));
        lbCategory.setFont(Font.font("System", 14));

        contentBox.setPrefWidth(200);
        contentBox.getChildren().addAll(lbTitle, lbCategory);

        this.setPrefWidth(600);
        this.setPadding(new Insets(10));
        this.getChildren().addAll(checkBtn, contentBox);

        this.setOnMouseEntered(e -> {
            this.setBackground(
                    new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        });

        this.setOnMouseExited(e -> {
                this.setBackground(
                        new Background(new BackgroundFill(Color.rgb(244, 244, 244), CornerRadii.EMPTY, Insets.EMPTY)));
        });

        checkBtn.setOnMouseClicked(e-> {
            if(checkBtn.isSelected())
                System.out.println("Hi");
        });
    }
}
