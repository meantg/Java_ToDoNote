package custom;

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
    private NoteDTO note;
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

    public NoteBox(NoteDTO note) {
        this.getStylesheets().add("resources/css/noteBox.css");
        this.note = note;
        checkBtn = new CheckBox();
        checkBtn.getStylesheets().add("resources/css/checkbox.css");
        checkBtn.setSelected(note.getMaTinhTrang() == 12001 ? true : false);
        /*checkBtn.setPrefSize(30,30);*/

        VBox contentBox = new VBox();
        contentBox.setSpacing(1);
        lbTitle = new Label(note.getTieuDe());
/*        lbTitle.setPrefSize(400, 30);*/
        lbTitle.setFont(Font.font("System", 16));

        lbDescription = new Label(note.getNoiDung());
        lbDescription.setFont(Font.font("System", 10));

        //contentBox.setPrefWidth(900);
        contentBox.setAlignment(Pos.CENTER_LEFT);
        contentBox.getChildren().add(lbTitle);
        if(!lbDescription.getText().isEmpty()) {
            contentBox.getChildren().add(lbDescription);
        }

        //this.setPrefWidth(920);
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
        changedStatus();
    }

    public void changedStatus() {
        if(checkBtn.isSelected()) {
            lbTitle.setId("strikethrough");
            this.setId("done");
/*            try {
                NoteBUS.updateTinhTrang(TinhTrang.DONE.getId(), note.getMaNote());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }*/
        }
        else {
            lbTitle.setId("un-strike-through");
            this.setId("undone");
            /*try {
                NoteBUS.updateTinhTrang(TinhTrang.WORKING.getId(), note.getMaNote());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }*/
        }
    }

    public NoteDTO getNote() { return note; }
    public CheckBox getCheckBtn() {
        return checkBtn;
    }
}
