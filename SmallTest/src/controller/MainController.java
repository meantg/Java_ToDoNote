package controller;

import BUS.CategoryBUS;
import BUS.NoteBUS;
import DTO.CategoryDTO;
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
    AnchorPane title_pane;

    @FXML
    VBox menu_pane;

    //UserDTO user -> userID = user.getID();
    private UserDTO user;
    private Pane pane;
    private TilePane categoryPane;

    public void initialize(UserDTO user) {
        menu_pane.setOnMousePressed(e-> menu_pane.requestFocus());
        title_pane.setOnMousePressed(e->title_pane.requestFocus());
        this.user = user;
        initMenu();
    }


    public void initMenu() {
        menu_pane.getChildren().clear();
        menu_pane.getChildren().add(new UserBox(user));
        categoryPane = new TilePane();
        loadCategories();
        menu_pane.getChildren().add(categoryPane);
        CategoryBox categoryBox = (CategoryBox)categoryPane.getChildren().get(0);
        loadNotePane(categoryBox);
        loadTitlePane(categoryBox);
        //Add New List
        AddListBox addListBox = new AddListBox();
        addListBox.setOnMouseClicked(e-> {
            try {
                CategoryBUS.insertCategory(new CategoryDTO(user.getMaNguoiDung(), "Untitled List", "☰"));
                loadCategories();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        menu_pane.getChildren().add(addListBox);
    }
    public void loadTitlePane(CategoryBox categoryBox) {
        title_pane.getChildren().clear();
        title_pane.getChildren().add(new EditableCategoryBox(categoryBox));
    }
    public void loadCategories() {
        //Add categoryBox
        categoryPane.getChildren().clear();

        try {
            ListCategory listCategory = new ListCategory(CategoryBUS.getListCategory(user.getMaNguoiDung()));
            listCategory.getList().stream().forEach(categoryBox -> {
                categoryBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if(listCategory.getList().indexOf(categoryBox) > 2) {
                        categoryBox.setEditable(true);
                    }
                    loadNotePane(categoryBox);
                    loadTitlePane(categoryBox);
                });
                categoryPane.getChildren().add(categoryBox);
            });

        }
        catch (SQLException SQLException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể tải danh sách list!");
            alert.setContentText("Lỗi database!");
            alert.showAndWait();
            SQLException.printStackTrace();
        }
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
                            try {
                                categoryBox.updateNumOfNotes();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
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
                            try {
                                categoryBox.updateNumOfNotes();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
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

}
