package controller;

import DTO.NoteDTO;
import custom.EditableCategoryBox;
import helper.DBHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class EditPaneController {
    @FXML
    Label lbCreatedDate;
    @FXML
    Label lbeditNote_Name;
    @FXML
    TextField tf_editNote_NoteName;
    @FXML
    TextArea ta_editNote_NoteDiscription;
    @FXML
    MenuButton mb_editNote_Category;

    private NoteDTO noteDTO;

    public EditPaneController() {
    }

    public void loadNote(NoteDTO noteDTO) {
        this.noteDTO = noteDTO;
        tf_editNote_NoteName.setText(noteDTO.getTieuDe());
        ta_editNote_NoteDiscription.setText(noteDTO.getNoiDung());
        LocalDate createdDate = noteDTO.getNgayTao();
        lbCreatedDate.setText(String.format("Create on %s, %s %s", createdDate.getDayOfWeek(), createdDate.getMonth(), createdDate.getYear()));
        //System.out.printf("Create on %s, %s %s", createdDate.getDayOfWeek(), createdDate.getMonth(), createdDate.getYear());
    }

    public void handleExit(){}

    public void handleSaveNote() {

    }

    public void handleClose() {
        BorderPane root = (BorderPane) tf_editNote_NoteName.getScene().getRoot();
        root.getChildren().remove(root.getRight());
    }
}
