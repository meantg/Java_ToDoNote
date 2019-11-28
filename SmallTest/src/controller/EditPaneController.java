
package controller;

import BUS.NoteBUS;
import DTO.NoteDTO;
import custom.EditableCategoryBox;
import custom.NoteBox;
import helper.DBHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    TextField tf_editNote_NoteName;
    @FXML
    TextArea ta_editNote_NoteDiscription;
    @FXML
    CheckBox checkBtn;
    @FXML
    Button btn_Close;

    private NoteDTO noteDTO;
    boolean isNewNote = false;
    int maPhanLoai;

    public EditPaneController() {

    }

    public void newNote(int maPhanLoai){
        isNewNote = true;
        this.maPhanLoai = maPhanLoai;
    }

    public void loadNote(NoteBox noteBox) {
        this.noteDTO = noteBox.getNote();
        if(noteBox.getNote().getMaTinhTrang() == 12002) {
            checkBtn.setSelected(false);
        }
        else {
            checkBtn.setSelected(true);
        }

        checkBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               /* noteBox.getCheckBtn().setSelected(newValue.booleanValue());*/
                try {
                    handleSaveNote();
                    Runnable reloadMenuPane = (Runnable) btn_Close.getParent().getUserData();
                    reloadMenuPane.run();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_Close.setUserData(this);

        tf_editNote_NoteName.setText(noteDTO.getTieuDe());
        ta_editNote_NoteDiscription.setText(noteDTO.getNoiDung());
        LocalDate createdDate = noteDTO.getNgayTao();
        lbCreatedDate.setText(String.format("Create on %s, %s %s", createdDate.getDayOfWeek(), createdDate.getMonth(), createdDate.getYear()));
        //System.out.printf("Create on %s, %s %s", createdDate.getDayOfWeek(), createdDate.getMonth(), createdDate.getYear());
    }

    public void handleExit(){}

    public void handleSaveNote() throws SQLException {
        NoteDTO note = new NoteDTO(noteDTO.getMaNote(),
                                   noteDTO.getMaPhanLoai(),
                                   tf_editNote_NoteName.getText(),
                                   ta_editNote_NoteDiscription.getText(),
                                   checkBtn.isSelected() ? 12001: 12002,
                                   noteDTO.getNgayTao()
                                  );
        NoteBUS.updateNote(note);
    }

    public void handleAddNote() throws SQLException {
        if(tf_editNote_NoteName.getText().trim().isEmpty()) {
            return;
        }
        NoteDTO note = new NoteDTO(maPhanLoai,
                                   tf_editNote_NoteName.getText(),
                                   ta_editNote_NoteDiscription.getText(),
                       12002
                                  );
        NoteBUS.insertNote(note);
    }

    public void handleClose() throws SQLException {
        BorderPane root = (BorderPane) tf_editNote_NoteName.getScene().getRoot();
        root.getChildren().remove(root.getRight());
        if(isNewNote == false) {
            handleSaveNote();
        }
        else {
            handleAddNote();
        }
        Runnable reloadMenuPane = (Runnable) btn_Close.getParent().getUserData();
        reloadMenuPane.run();
    }

    public void handleDeleteNote() throws SQLException {
        NoteBUS.deleteNote(noteDTO.getMaNote());
        Runnable reloadMenuPane = (Runnable) btn_Close.getParent().getUserData();
        reloadMenuPane.run();
        BorderPane root = (BorderPane) tf_editNote_NoteName.getScene().getRoot();
        root.getChildren().remove(root.getRight());
    }
}