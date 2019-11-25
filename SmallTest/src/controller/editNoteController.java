package controller;

import DTO.NoteDTO;
import com.sun.org.apache.xml.internal.security.Init;
import helper.DBHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class editNoteController {
    private Stage mainStage;
    private MainController mainController;
    @FXML
    Label lbeditNote_Name;
    @FXML
    TextField tf_editNote_NoteName;
    @FXML
    TextArea ta_editNote_NoteDiscription;
    @FXML
    MenuButton mb_editNote_Category;


    public void loadNote(NoteDTO noteDTO) {
        tf_editNote_NoteName.setText(noteDTO.getTieuDe());
        ta_editNote_NoteDiscription.setText(noteDTO.getNoiDung());
    }
    public void setState(Stage stage){
        mainStage=stage;
    }

    public void handleExit(){}

    public void handleSaveNote(){}

    editNoteController(){
        System.out.print("Initialize");
    }

}