package controller;

import BUS.CategoryBUS;
import BUS.NoteBUS;
import DTO.CategoryDTO;
import DTO.NoteDTO;
import DTO.UserDTO;
import custom.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class MainController{

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
    private TilePane noteboxPane;
    private ListCategory listCategory;
    private CategoryBox curCategoryBox;
    private NoteBox curNoteBox;

    public void initialize(UserDTO user){
        menu_pane.setOnMousePressed(e-> menu_pane.requestFocus());
        title_pane.setOnMousePressed(e->title_pane.requestFocus());
        this.user = user;
        initMenu();


    }

    public void handleAddNote() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/application/EditPane.fxml"));
        pane = loader.load();
        pane.setPrefWidth(400);
        pane.setUserData(reloadNotePane);
        EditPaneController editPaneController = loader.getController();
        editPaneController.newNote(curCategoryBox.getCategory().getMaPhanLoai());
        root.setRight(pane);
    }


    public void initMenu() {
        menu_pane.getChildren().clear();
        menu_pane.getChildren().add(new UserBox(user));
        categoryPane = new TilePane();
        loadCategories();
        menu_pane.getChildren().add(categoryPane);
        curCategoryBox = listCategory.getList().get(0);
        noteboxPane = new TilePane();
        loadNotePane();
        note_box.getChildren().add(noteboxPane);
        loadTitlePane();
        //Add New List
        AddListBox addListBox = new AddListBox();
        addListBox.setOnMouseClicked(e-> {
            try {
                CategoryBUS.insertCategory(new CategoryDTO(user.getMaNguoiDung(), "Untitled List", "☰"));
                reloadMenuPane.run();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        menu_pane.getChildren().add(addListBox);

    }
    public void loadTitlePane() {
        title_pane.getChildren().clear();
        EditableCategoryBox editBox = new EditableCategoryBox(curCategoryBox);
        editBox.setUserData(reloadMenuPane);
        title_pane.getChildren().add(editBox);
    }
    public void loadCategories() {
        //Add categoryBox
        categoryPane.getChildren().clear();

        try {
            listCategory = new ListCategory(CategoryBUS.getListCategory(user.getMaNguoiDung()));
            listCategory.getList().stream().forEach(categoryBox -> {
                categoryBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    curCategoryBox = categoryBox;
                    if(listCategory.getList().indexOf(categoryBox) > 2) {
                        categoryBox.setEditable(true);
                    }
                    loadNotePane();
                    loadTitlePane();
                });
                categoryBox.setUserData(deleteCategoryBox);
                categoryBox.getChildren().get(0).setUserData(reloadMenuPane);
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

    public void loadNotePane() {
        noteboxPane.getChildren().clear();

        try {
            List<NoteDTO> listNotes = NoteBUS.getToDoList(curCategoryBox.getCategory().getMaPhanLoai(), 12002);
            listNotes.stream().forEach(note -> {

                try {
                    NoteBox noteBox = new NoteBox(note);
                    noteBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        if(root.getRight() != null && noteBox == curNoteBox) {
                            root.getChildren().remove(root.getRight());
                        }
                        else {
                            try {
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("/application/EditPane.fxml"));
                                pane = loader.load();
                                EditPaneController editPaneController = loader.getController();
                                editPaneController.loadNote(note);
                                pane.setUserData(reloadNotePane);
                                pane.setPrefWidth(400);
                            } catch (IOException exception) {
                                System.out.println("Can't load fxml file");
                                exception.printStackTrace();
                            }
                            root.setRight(pane);
                        }
                        curNoteBox = noteBox;
                    });
                    CheckBox checkBox = noteBox.getCheckBtn();
                    checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            noteBox.changedStatus(note);
                            loadNotePane();
                            try {
                                curCategoryBox.updateNumOfNotes();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    noteboxPane.getChildren().add(noteBox);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            List<NoteDTO> listFinishedNotes = NoteBUS.getToDoList(curCategoryBox.getCategory().getMaPhanLoai(), 12001);
            listFinishedNotes.stream().forEach(note -> {
                try {
                    NoteBox noteBox = new NoteBox(note);
                    noteBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        if(root.getRight() != null && noteBox == curNoteBox) {
                            root.getChildren().remove(root.getRight());
                        }
                        else {
                            try {
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("/application/EditPane.fxml"));
                                pane = loader.load();
                                EditPaneController editPaneController = loader.getController();
                                editPaneController.loadNote(note);
                                pane.setPrefWidth(400);
                                pane.setUserData(reloadNotePane);
                            } catch (IOException exception) {
                                System.out.println("Can't load fxml file");
                                exception.printStackTrace();
                            }
                            root.setRight(pane);
                        }
                        curNoteBox = noteBox;
                    });
                    CheckBox checkBox = (CheckBox)noteBox.getCheckBtn();
                    checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            noteBox.changedStatus(note);
                            loadNotePane();
                            try {
                                curCategoryBox.updateNumOfNotes();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    noteboxPane.getChildren().add(noteBox);
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


    Runnable reloadMenuPane = () -> {
        int curIndex = listCategory.getList().indexOf(curCategoryBox);
        loadCategories();
        curCategoryBox = listCategory.getList().get((curIndex));
        listCategory.setCurCategory(curCategoryBox);
        loadNotePane();
        loadTitlePane();
    };

    Runnable deleteCategoryBox = () -> {
        int curIndex = listCategory.getList().indexOf(curCategoryBox);
        loadCategories();
        curCategoryBox = listCategory.getList().get((curIndex -1));
        curCategoryBox.requestFocus();
        listCategory.setCurCategory(curCategoryBox);
        loadTitlePane();
        loadNotePane();
    };

    Runnable reloadTitlePane = () -> {
        loadTitlePane();
    };

    Runnable reloadNotePane = () -> {
        loadNotePane();
    };


}
