package controller;

import BUS.NoteBUS;
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
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
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
    @FXML
    Button btn_Close;

    private NoteDTO noteDTO;
    boolean isNewNote = false;
    int maphamloai;

    public EditPaneController() {
    }

    public void newNote(int maphanloai){
         isNewNote = true;
         this.maphamloai = maphanloai;
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

    public void handleSaveNote() throws SQLException {
        NoteDTO note = new NoteDTO(noteDTO.getMaNote(),noteDTO.getMaPhanLoai(),tf_editNote_NoteName.getText(),ta_editNote_NoteDiscription.getText(),noteDTO.getMaTinhTrang(),noteDTO.getNgayTao());
        NoteBUS.updateNote(note);
    }

    public void handleAddNote() throws SQLException {
        NoteDTO note = new NoteDTO(this.maphamloai,tf_editNote_NoteName.getText(),ta_editNote_NoteDiscription.getText(), 12002);
        NoteBUS.insertNote(note);
    }

    public void handleClose() throws SQLException {
        BorderPane root = (BorderPane) tf_editNote_NoteName.getScene().getRoot();
        root.getChildren().remove(root.getRight());
        if(isNewNote == false) {
            handleSaveNote();
        }
        else
            handleAddNote();
        Runnable reloadNotePane = (Runnable) tf_editNote_NoteName.getParent().getUserData();
        reloadNotePane.run();
    }

    public void handleDeleteNote() throws SQLException {
        String nameNote = tf_editNote_NoteName.getText();
        NoteBUS.deleteNote(nameNote);
        Runnable reloadNotePane = (Runnable) tf_editNote_NoteName.getParent().getUserData();
        reloadNotePane.run();
    }
}
