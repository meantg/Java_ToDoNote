package custom;

import BUS.CategoryBUS;
import BUS.NoteBUS;
import DTO.NoteDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class NoteBox extends HBox {
    private CheckBox checkBtn;
    private Label lbTitle;
    private Label lbDescription;
    private enum TinhTrang{
        DONE(12001),
        WORKING(12002);
        private Integer id;
        TinhTrang(int id) {
            this.id = id;
        }
        Integer getId() {
            return id;
        }
    };

    public NoteBox(NoteDTO note) throws SQLException {
        this.getStylesheets().add("custom/styles.css");
        checkBtn = new CheckBox();
        checkBtn.setSelected(note.getMaTinhTrang() == 12001 ? true : false);
        checkBtn.setPrefSize(30,30);

        VBox contentBox = new VBox();
        contentBox.setSpacing(3);
        lbTitle = new Label(note.getTieuDe());
        lbTitle.setPrefSize(400, 30);
        lbTitle.setFont(Font.font("System", 20));

        lbDescription = new Label(note.getNoiDung());
        lbDescription.setFont(Font.font("System", 14));

        contentBox.setPrefWidth(200);
        contentBox.getChildren().add(lbTitle);
        if(!lbDescription.getText().isEmpty()) {
            contentBox.getChildren().add(lbDescription);
        }

        this.setPrefWidth(600);
        this.setPadding(new Insets(10));
        this.getChildren().addAll(checkBtn, contentBox);

        this.setAlignment(Pos.CENTER_LEFT);
        this.setOnMouseEntered(e -> {
            if(!checkBtn.isSelected())
                this.setBackground(
                        new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        });

        this.setOnMouseExited(e -> {
            if(!checkBtn.isSelected())
                this.setBackground(
                        new Background(new BackgroundFill(Color.rgb(244, 244, 244), CornerRadii.EMPTY, Insets.EMPTY)));
        });
        changedStatus(note);
    }

    public void changedStatus(NoteDTO note) {
        if(checkBtn.isSelected()) {
            lbTitle.setId("strikethrough");
            this.setId("done");
            try {
                NoteBUS.updateTinhTrang(TinhTrang.DONE.getId(), note.getMaNote());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        else {
            lbTitle.setId("un-strike-through");
            this.setId("undone");
            try {
                NoteBUS.updateTinhTrang(TinhTrang.WORKING.getId(), note.getMaNote());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public CheckBox getCheckBtn() {
        return checkBtn;
    }
}
