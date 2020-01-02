package custom;

import BUS.NoteBUS;
import DTO.CategoryDTO;
import DTO.NoteDTO;
import controller.MainController;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.SQLException;

public class NoteBox extends HBox {
    private CheckBox checkBtn;
    private Label lbTitle;
    private Label lbDescription;
    private NoteDTO note;
    private Button starButton;
    private boolean isImportance;
    private MaterialDesignIconView icon;
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
        checkBtn.setSelected(note.getStateID() == 12001 ? true : false);

        VBox contentBox = new VBox();
        lbTitle = new Label(note.getTitle());
        lbTitle.setFont(Font.font("System", 12));

        lbDescription = new Label(note.getDescription());
        lbDescription.setFont(Font.font("System", 12));

        contentBox.setAlignment(Pos.CENTER_LEFT);
        contentBox.getChildren().add(lbTitle);
        if(!lbDescription.getText().isEmpty()) {
            contentBox.getChildren().add(lbDescription);
        }
        HBox.setHgrow(contentBox, Priority.ALWAYS);
        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);

        starButton = new Button();
        starButton.setStyle("-fx-background-color: transparent");
        icon = new MaterialDesignIconView(MaterialDesignIcon.STAR);
        icon.setGlyphSize(24);
        isImportance = note.getImportance();
        if (isImportance) {
            icon.setFill(Color.YELLOW);
            icon.setStroke(Color.TRANSPARENT);
        } else {
            icon.setFill(Color.TRANSPARENT);
            icon.setStroke(Color.BLACK);
        }

        starButton.setGraphic(icon);
        starButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        HBox.setHgrow(starButton, Priority.ALWAYS);

        this.setId("hbox");
        this.setPadding(new Insets(10, 15, 10, 15));
        this.getChildren().addAll(checkBtn, contentBox, starButton);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setOnMouseEntered(e -> {
            this.setId("hover");
        });

        this.setOnMouseExited(e -> {
            this.setId("hbox");
        });
    }

    public void changedStatus() {
        if(checkBtn.isSelected()) {
            lbTitle.setId("strikethrough");
        }
        else {
            lbTitle.setId("un-strike-through");
        }
    }

    public void setNewState(boolean newValue) {
        if (newValue) {
            note.setStateID(12001);
        } else {
            note.setStateID(12002);
        }
        try {
            NoteBUS.updateNote(note);
            ListSmartCategoryBox.getList().stream().forEach(smartCategoryBox -> {
                smartCategoryBox.reloadBox(note.getUserID());
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setImportance() {
        isImportance = !isImportance;
        if (isImportance) {
            icon.setFill(Color.YELLOW);
            icon.setStroke(Color.TRANSPARENT);
            note.setImportance(true);

        } else {
            icon.setFill(Color.TRANSPARENT);
            icon.setStroke(Color.BLACK);
            note.setImportance(false);
        }
        try {
            NoteBUS.updateNote(note);
            ListSmartCategoryBox.getList().get(1).reloadBox(note.getUserID());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public NoteDTO getNote() { return note; }
    public CheckBox getCheckBtn() { return checkBtn; }
    public Button getStarButton() { return starButton; }
}
