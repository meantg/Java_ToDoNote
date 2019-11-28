package controller;

import BUS.CategoryBUS;
import BUS.NoteBUS;
import BUS.TinhTrangBUS;
import DTO.CategoryDTO;
import DTO.NoteDTO;
import DTO.TinhTrangDTO;
import DTO.UserDTO;
import custom.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;


public class MainController {

    @FXML
    VBox note_box;

    @FXML
    BorderPane root;

    @FXML
    AnchorPane title_pane;

    @FXML
    VBox menu_pane;

    @FXML
    ScrollPane scroll_note_pane;

    private TilePane noteboxPane;
    //UserDTO user -> userID = user.getID();
    private UserDTO user;
    private Pane pane;
    private TilePane categoryPane;
    private ListCategory listCategory;
    private CategoryBox curCategoryBox;
    private Integer curNoteBoxIndex;

    public void initialize(UserDTO user){
        root.applyCss();
        root.layout();
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
        pane.setUserData(reloadMenuPane);
        EditPaneController editPaneController = loader.getController();
        editPaneController.newNote(curCategoryBox.getCategory().getMaPhanLoai());
        root.setRight(pane);
        root.applyCss();
        root.layout();
        scroll_note_pane.setPrefWidth(scroll_note_pane.getWidth() - 10);
        pane.getStylesheets().add("/custom/styles.css");
    }


    public void initMenu() {
        menu_pane.getChildren().clear();
        menu_pane.getChildren().add(new UserBox(user));
        categoryPane = new TilePane();
        categoryPane.setVgap(1);
        loadCategories();
        menu_pane.getChildren().add(categoryPane);
        curCategoryBox = listCategory.getList().get(0);
        curNoteBoxIndex = 0;
        scroll_note_pane.applyCss();
        scroll_note_pane.layout();

        noteboxPane = new TilePane();
        noteboxPane.setVgap(1);
        noteboxPane.setPadding(new Insets(5));

        scroll_note_pane.prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scroll_note_pane.setPrefWidth(newValue.doubleValue());
                note_box.setPrefWidth(newValue.doubleValue());
                noteboxPane.setPrefWidth(newValue.doubleValue() - 2);
                loadNotePane();
            }
        });

        scroll_note_pane.prefHeightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scroll_note_pane.setPrefHeight(newValue.doubleValue());
                note_box.setPrefHeight(newValue.doubleValue());
                noteboxPane.setPrefHeight(newValue.doubleValue() - 2);
                //loadNotePane();
            }
        });
        scroll_note_pane.setPrefSize(scroll_note_pane.getWidth(), scroll_note_pane.getHeight());
        //note_box.setPrefSize(scroll_note_pane.getPrefWidth(), scroll_note_pane.getPrefHeight());
        loadNotePane();
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
                    if(curCategoryBox != categoryBox && root.getRight() != null) {
                        root.getChildren().remove(root.getRight());
                        root.applyCss();
                        root.layout();
                        scroll_note_pane.setPrefWidth(scroll_note_pane.getWidth() - 10);
                    }
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
        note_box.getChildren().clear();
        note_box.getChildren().add(noteboxPane);
        List<NoteBox> listNoteBox = new ArrayList<NoteBox>();
        try {
            List<TinhTrangDTO> dsTinhTrang = TinhTrangBUS.getDSTinhTrang();
            Collections.sort(dsTinhTrang, Collections.reverseOrder());
            dsTinhTrang.stream().forEach(tinhTrang -> {
                try {
                    List<NoteDTO> listNotes = NoteBUS.getToDoList(curCategoryBox.getCategory().getMaPhanLoai(), tinhTrang.getMaTinhTrang());
                    listNotes.stream().forEach(note -> {
                        try {
                            NoteBox noteBox = new NoteBox(note);
                            listNoteBox.add(noteBox);
                        }
                        catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            System.out.println("Không thể kết nối db");
            e.printStackTrace();
        }

        listNoteBox.stream().forEach(noteBox -> {
            noteBox.setPrefWidth(noteboxPane.getPrefWidth() - noteboxPane.getPadding().getLeft() - noteboxPane.getPadding().getRight());
            noteBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                System.out.println(curNoteBoxIndex);
                if(root.getRight() != null && listNoteBox.indexOf(noteBox) == curNoteBoxIndex) {
                    root.getChildren().remove(root.getRight());
                    root.applyCss();
                    root.layout();
                    EditPaneController editController = (EditPaneController) pane.getChildren().get(0).getUserData();
                    try {
                        editController.handleSaveNote();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    scroll_note_pane.setPrefWidth(scroll_note_pane.getWidth() - 10);
                }
                else if(root.getRight() != null && listNoteBox.indexOf(noteBox) != curNoteBoxIndex) {
                    System.out.println("Change");
                    curNoteBoxIndex = listNoteBox.indexOf(noteBox);
                    EditPaneController editController = (EditPaneController)pane.getChildren().get(0).getUserData();
                    try {
                        editController.handleSaveNote();
                        editController.loadNote(noteBox);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    curNoteBoxIndex = listNoteBox.indexOf(noteBox);
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/application/EditPane.fxml"));
                        pane = loader.load();
                        EditPaneController editPaneController = loader.getController();
                        editPaneController.loadNote(noteBox);
                        pane.setPrefWidth(400);
                        pane.setUserData(reloadMenuPane);
                        pane.getStylesheets().add("/custom/styles.css");
                    } catch (IOException exception) {
                        System.out.println("Can't load fxml file");
                        exception.printStackTrace();
                    }
                    root.setRight(pane);
                    root.applyCss();
                    root.layout();
                    scroll_note_pane.setPrefWidth(scroll_note_pane.getWidth() - 10);
                }
            });
            CheckBox checkBox = (CheckBox)noteBox.getCheckBtn();
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    noteBox.changedStatus(noteBox.getNote());
                    if(root.getRight() != null) {
                        EditPaneController editController = (EditPaneController)pane.getChildren().get(0).getUserData();
                        editController.checkBtn.setSelected(newValue.booleanValue());
                    }
                    loadNotePane();
                    try {
                        curCategoryBox.updateNumOfNotes();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            noteboxPane.getChildren().add(noteBox);
        });
    }

    Runnable reloadMenuPane = () -> {
        int curIndex = listCategory.getList().indexOf(curCategoryBox);
        loadCategories();
        curCategoryBox = listCategory.getList().get((curIndex));
        try {
            curCategoryBox.updateNumOfNotes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listCategory.setCurCategory(curCategoryBox);
        loadNotePane();
        loadTitlePane();
        root.applyCss();
        root.layout();
        scroll_note_pane.setPrefWidth(scroll_note_pane.getWidth() - 10);
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
