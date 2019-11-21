package controller;

import BUS.CategoryBUS;
import BUS.NoteBUS;
import DTO.NoteDTO;
import DTO.UserDTO;
import custom.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class MainController{

    @FXML
    VBox done_note_box;
    @FXML
    VBox note_box;

    @FXML
    BorderPane root;

    @FXML
    Text lbIcon_Content;
    @FXML
    Text lbNameCategory_Content;

    @FXML
    TilePane menu_pane;

    //UserDTO user -> userID = user.getID();
    private UserDTO user;
    private Pane pane;

    public void initialize(UserDTO user) {
        menu_pane.getScene().getRoot().setOnMousePressed(e-> menu_pane.getScene().getRoot().requestFocus());
        lbIcon_Content.getParent().setOnMousePressed(e->lbIcon_Content.getParent().requestFocus());
        this.user = user;
        loadMenu();
    }

    public void loadContent(CategoryBox categoryBox) {
        lbIcon_Content.setText(categoryBox.getCategory().getIcon());
        lbNameCategory_Content.setText(categoryBox.getCategory().getTenPhanLoai());
    }

    public void loadMenu() {
        menu_pane.getChildren().clear();
        menu_pane.getChildren().add(new UserBox(user));
        //Add categoryBox
        try {
            ListCategory listCategory = new ListCategory(CategoryBUS.getListCategory(user.getMaNguoiDung()));
            listCategory.getList().stream().forEach(categoryBox -> {
                loadNotePane(categoryBox);
                categoryBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    loadContent(categoryBox);
                    loadNotePane(categoryBox);
                });
                menu_pane.getChildren().add(categoryBox);
            });
            loadContent(listCategory.getList().get(0));
            loadNotePane(listCategory.getList().get(0));
        }
        catch (SQLException SQLException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể tải danh sách list!");
            alert.setContentText("Lỗi database!");
            alert.showAndWait();
            SQLException.printStackTrace();
        }
        //Add New List
        menu_pane.getChildren().add(new AddListBox());
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e->{
            root.getChildren().remove(root.getRight());
        });
        pane = new Pane();
        pane.getChildren().add(closeButton);
        pane.setPrefWidth(120);
        root.setRight(pane);
    }

    public void loadNotePane(CategoryBox categoryBox) {
        note_box.getChildren().clear();
        done_note_box.getChildren().clear();

        try {
            List<NoteDTO> listNotes = NoteBUS.getToDoList(categoryBox.getCategory().getMaPhanLoai(), 12002);
            listNotes.stream().forEach(note -> {

                try {
                    NoteBox noteBox = new NoteBox(note);
                    CheckBox checkBox = (CheckBox)noteBox.getCheckBtn();
                    checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            noteBox.changedStatus(note);
                            loadNotePane(categoryBox);
                            loadMenu();
                        }
                    });
                    note_box.getChildren().add(noteBox);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            List<NoteDTO> listFinishedNotes = NoteBUS.getToDoList(categoryBox.getCategory().getMaPhanLoai(), 12001);
            listFinishedNotes.stream().forEach(note -> {
                try {
                    NoteBox noteBox = new NoteBox(note);
                    noteBox.setOnMouseClicked(e-> root.setRight(pane));
                    CheckBox checkBox = (CheckBox)noteBox.getCheckBtn();
                    checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            noteBox.changedStatus(note);
                            loadNotePane(categoryBox);
                            loadMenu();
                        }
                    });
                    done_note_box.getChildren().add(noteBox);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void handleButton() {
    }

}
