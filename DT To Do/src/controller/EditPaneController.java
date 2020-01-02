
package controller;

import BUS.NoteBUS;
import DTO.NoteDTO;
import custom.ListSmartCategoryBox;
import custom.NoteBox;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.weathericons.WeatherIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

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
    @FXML
    AnchorPane pane;
    @FXML
    Button btnDelMyDay;
    @FXML
    Button btnDelDueDate;

    @FXML
    MaterialDesignIconView star_icon;

    @FXML
    Button star_button;

    @FXML
    HBox addMyDay;
    @FXML
    HBox addDueDate;

    @FXML
    WeatherIconView iconMyDay;

    @FXML
    MaterialDesignIconView iconDueDate;

    @FXML
    Text lbMyDay;
    @FXML
    Text lbDueDate;


    //TODO: Bỏ isNewNote.

    private NoteDTO noteDTO;
    private boolean isMyDay;
    private boolean isImportance;
    private LocalDate dueDate;
    private int maPhanLoai;

    public EditPaneController() {
    }

    public void loadNote(NoteBox noteBox) {
        this.noteDTO = noteBox.getNote();
        System.out.println(noteDTO.getUserID());
        if(noteBox.getNote().getStateID() == 12002) {
            checkBtn.setSelected(false);
        }
        else {
            checkBtn.setSelected(true);
        }
        isImportance = noteDTO.getImportance();
        isMyDay = noteDTO.getMyDay();
        if(noteDTO.getDueDate() != null) {
         dueDate = noteDTO.getDueDate().toLocalDate();
        }
        else {
            dueDate = null;
        }

        checkBtn.getStylesheets().add("/resources/css/checkbox.css");
        checkBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                /* noteBox.getCheckBtn().setSelected(newValue.booleanValue());*/
                try {
                    handleSaveNote();
                    Runnable updateNoteStatus = (Runnable) pane.getUserData();
                    updateNoteStatus.run();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        tf_editNote_NoteName.setText(noteDTO.getTitle());
        ta_editNote_NoteDiscription.setText(noteDTO.getDescription());
        LocalDate createdDate = noteDTO.getCreateDate().toLocalDate();
        lbCreatedDate.setText(String.format("Create on %s, %s %s", createdDate.getDayOfWeek(), createdDate.getMonth(), createdDate.getYear()));
        //System.out.printf("Create on %s, %s %s", createdDate.getDayOfWeek(), createdDate.getMonth(), createdDate.getYear());
        if (isImportance) {
            star_icon.setFill(Color.YELLOW);
            star_icon.setStroke(Color.TRANSPARENT);
            ListSmartCategoryBox.getList().get(1).reloadBox(noteDTO.getUserID());
        } else {
            star_icon.setFill(Color.TRANSPARENT);
            star_icon.setStroke(Color.BLACK);
            ListSmartCategoryBox.getList().get(1).reloadBox(noteDTO.getUserID());
        }
        if(isMyDay) {
            lbMyDay.setFill(Color.BLUE);
            lbMyDay.setText("Added to My Day");
            iconMyDay.setFill(Color.BLUE);
            btnDelMyDay.setVisible(true);
            ListSmartCategoryBox.getList().get(0).reloadBox(noteDTO.getUserID());
        }
        else {
            lbMyDay.setText("Add to My Day");
            lbMyDay.setFill(Color.BLACK);
            iconMyDay.setFill(Color.BLACK);
            btnDelMyDay.setVisible(false);
            ListSmartCategoryBox.getList().get(0).reloadBox(noteDTO.getUserID());
        }
        if(dueDate != null) {
            lbDueDate.setText(String.format("Due %s, %s %s", dueDate.getDayOfWeek(), dueDate.getMonth(), dueDate.getYear()));
            lbDueDate.setFill(Color.BLUE);
            iconDueDate.setFill(Color.BLUE);
            btnDelDueDate.setVisible(true);
            ListSmartCategoryBox.getList().get(2).reloadBox(noteDTO.getUserID());
        }
        else {
            lbDueDate.setText("Add due date");
            lbDueDate.setFill(Color.BLACK);
            iconDueDate.setFill(Color.BLACK);
            btnDelDueDate.setVisible(false);
            ListSmartCategoryBox.getList().get(2).reloadBox(noteDTO.getUserID());
        }
    }

    public void handleSaveNote() throws SQLException {
        //TODO: isMyDay và isImportance set nếu button được bấm, dueDate sẽ chọn ngày.
        System.out.println(noteDTO.getCategoryID());
        NoteDTO note = new NoteDTO(
                                   noteDTO.getNoteID(),
                                   noteDTO.getUserID(),
                                   noteDTO.getCategoryID(),
                                   tf_editNote_NoteName.getText(),
                                   ta_editNote_NoteDiscription.getText(),
                                   checkBtn.isSelected() ? 12001: 12002,
                                   isMyDay,
                                   isImportance,
                                   noteDTO.getCreateDate().toLocalDate(),
                                   dueDate);
        NoteBUS.updateNote(note);
    }

//    public void handleAddNote() throws SQLException {
//        if(tf_editNote_NoteName.getText().trim().isEmpty()) {
//            return;
//        }
//        NoteDTO note = new NoteDTO(maPhanLoai,
//                                   tf_editNote_NoteName.getText(),
//                                   ta_editNote_NoteDiscription.getText(),
//                       12002
//                                  );
//        NoteBUS.insertNote(note);
//    }

    public void handleClose() throws SQLException {
        BorderPane root = (BorderPane) tf_editNote_NoteName.getScene().getRoot();
        root.getChildren().remove(root.getRight());
        handleSaveNote();
        Runnable reloadMenuPane = (Runnable) pane.getUserData();
        reloadMenuPane.run();
    }

    public void handleDeleteNote() throws SQLException {
        NoteBUS.deleteNote(noteDTO.getNoteID());
        Runnable updateNoteStatus = (Runnable) pane.getUserData();
        updateNoteStatus.run();
        BorderPane root = (BorderPane) tf_editNote_NoteName.getScene().getRoot();
        root.getChildren().remove(root.getRight());
    }

    public NoteDTO getNoteEditing() {
        return noteDTO;
    }
}