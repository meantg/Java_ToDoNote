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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
    HBox addListBox;

    @FXML
    HBox userBox;

    @FXML
    ScrollPane scroll_note_pane;

    @FXML
    ScrollPane scroll_category_pane;

    @FXML
    Label lbAvatar;

    @FXML
    Label lbName;

    private Scene mainScene;
    private Scene userProfileScene;
    private TilePane noteboxPane;
    private UserDTO user;
    private Pane pane;
    private TilePane categoryPane;
    private ListCategory listCategory;
    private ListNoteBox listNoteBox;
    private CategoryBox curCategoryBox;
    private NoteBox curNoteBox;
    private SORT curSortType = SORT.WORKING;
    private EditPaneController editPaneController;


    private enum SORT {
        NORMALLY,
        DONE,
        WORKING
    };

    public void initialize(UserDTO user){
        this.user = user;
        root.applyCss();
        root.layout();
        menu_pane.setOnMousePressed(e-> menu_pane.requestFocus());
        title_pane.setOnMousePressed(e->title_pane.requestFocus());
        mainScene = root.getScene();

        categoryPane = new TilePane();
        categoryPane.setVgap(1);
        menu_pane.getChildren().add(categoryPane);
        initSideMenu();

        noteboxPane = new TilePane();
        noteboxPane.setVgap(1);
        noteboxPane.setPadding(new Insets(5));
        note_box.getChildren().add(noteboxPane);
        initNotePane();
        loadTitlePane();
    }

    public void handleAddNote() throws IOException {
        FXMLLoader loader =  new FXMLLoader(URL.class.getResource("/resources/fxml/EditPane.fxml"));
        pane = loader.load();
        pane.setPrefWidth(400);
        pane.setUserData(updateNoteStatus);
        EditPaneController editPaneController = loader.getController();
        editPaneController.newNote(curCategoryBox.getCategory().getMaPhanLoai());
        root.setRight(pane);
        root.applyCss();
        root.layout();
        scroll_note_pane.setPrefWidth(scroll_note_pane.getWidth() - 10);
        pane.getStylesheets().add("/resources/css/noteBox.css");
    }


    public void initSideMenu() {
        initCategories();
        initUserBox(user);
        initAddListBox();
        curCategoryBox = listCategory.getCurCategory();
    }
    public void initNotePane() {
        scroll_note_pane.applyCss();
        scroll_note_pane.layout();

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
    }

    public void initUserBox(UserDTO user) {
        lbAvatar.setText(user.getGioiTinh().equals("Nam") ? "➕" : user.getGioiTinh().equals("Other") ? "Other" : "Nữ");
        lbName.setText(user.getTenNguoiDung());
        userBox.setOnMouseClicked(e-> {
            try {
                Stage stage = (Stage)userBox.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(URL.class.getResource("/resources/fxml/UserProfile.fxml"));
                BorderPane borderPane = loader.load();
                Scene scene = new Scene(borderPane, stage.getWidth(), stage.getHeight());
                UserProfileController userProfileController = (UserProfileController)loader.getController();
                userProfileController.initialize(user);
                borderPane.setUserData(mainScene);
                stage.setScene(scene);
            } catch (IOException ex) {
                System.out.println("Lỗi open UserProfile");
                ex.printStackTrace();
            }
        });
    }

    public void initAddListBox() {
        addListBox.setOnMouseClicked(e-> {
            try {
                CategoryBUS.insertCategory(new CategoryDTO(user.getMaNguoiDung(), "Untitled List", "☰"));
                initCategories();
                curCategoryBox = listCategory.getList().get(listCategory.getList().size() - 1);
                curCategoryBox.requestFocus();
                listCategory.setCurCategory(curCategoryBox);
                loadTitlePane();
                loadNotePane();
                //scroll to the new list
                scroll_category_pane.applyCss();
                scroll_category_pane.layout();
                scroll_category_pane.setVvalue(1.0);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void initCategories() {
        //Create a Tilepane contains CategoryBox
        categoryPane.getChildren().clear();

        //Add category box into category pane
        try {
            //Get list category by UserID
            listCategory = new ListCategory(CategoryBUS.getListCategory(user.getMaNguoiDung()));
            listCategory.getList().stream().forEach(categoryBox -> {
                categoryBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if(curCategoryBox != categoryBox && root.getRight() != null) {
                        closeEditPane();
                    }
                    curCategoryBox = categoryBox;
                    loadNotePane();
                    loadTitlePane();
                });

                if(listCategory.getList().indexOf(categoryBox) > 2) {
                    categoryBox.setEditable(true);
                }
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

    public void loadTitlePane() {
        title_pane.getChildren().clear();
        EditableCategoryBox editBox = new EditableCategoryBox(curCategoryBox);
        editBox.setUserData(reloadMenuPane);
        title_pane.getChildren().add(editBox);
    }

    public void loadNotePane() {
        noteboxPane.getChildren().clear();
        switch(curSortType) {
            case NORMALLY:
                try {
                    listNoteBox = new ListNoteBox(NoteBUS.getToDoList(curCategoryBox.getCategory().getMaPhanLoai()),noteboxPane);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case DONE:
                try {
                    List<NoteDTO> listDone = NoteBUS.getToDoList(curCategoryBox.getCategory().getMaPhanLoai(), 12001);
                    List<NoteDTO> listWorking = NoteBUS.getToDoList(curCategoryBox.getCategory().getMaPhanLoai(), 12002);
                    List<NoteDTO> listNotes = new ArrayList<NoteDTO>();
                    listNotes.addAll(listDone);
                    listNotes.addAll(listWorking);
                    listNoteBox = new ListNoteBox(listNotes, noteboxPane);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case WORKING:
                try {
                    List<NoteDTO> listDone = NoteBUS.getToDoList(curCategoryBox.getCategory().getMaPhanLoai(), 12001);
                    List<NoteDTO> listWorking = NoteBUS.getToDoList(curCategoryBox.getCategory().getMaPhanLoai(), 12002);
                    List<NoteDTO> listNotes = new ArrayList<NoteDTO>();
                    listNotes.addAll(listWorking);
                    listNotes.addAll(listDone);
                    listNoteBox = new ListNoteBox(listNotes, noteboxPane);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        listNoteBox.getList().stream().forEach(noteBox -> {
            noteBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                curNoteBox = noteBox;
                if(editPaneController != null) {
                    if (root.getRight() != null & curNoteBox.getNote().getMaNote().equals(editPaneController.getNoteEditing().getMaNote())) {
                        closeEditPane();
                    } else {
                        openEditPane();
                    }
                }
                else {
                    openEditPane();
                }
            });
            CheckBox checkBox = (CheckBox)noteBox.getCheckBtn();
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    curNoteBox = noteBox;
                    if(root.getRight() != null && curNoteBox.getNote().getMaNote().equals(editPaneController.getNoteEditing().getMaNote())) {
                        editPaneController.checkBtn.setSelected(newValue.booleanValue());
                    }
                    else {
                        try {
                            NoteBUS.updateTinhTrang(newValue ? 12001 : 12002 ,curNoteBox.getNote().getMaNote());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
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

    public void closeEditPane() {
        try {
            editPaneController.handleSaveNote();
            root.getChildren().remove(root.getRight());
            root.applyCss();
            root.layout();
            scroll_note_pane.setPrefWidth(scroll_note_pane.getWidth() - 10);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void openEditPane() {
        if(root.getRight() != null) closeEditPane();
        try {
            FXMLLoader loader =  new FXMLLoader(URL.class.getResource("/resources/fxml/EditPane.fxml"));
            pane = loader.load();
            editPaneController = loader.getController();
            editPaneController.loadNote(curNoteBox);
            pane.setPrefWidth(400);
            pane.setUserData(updateNoteStatus);
            pane.getStylesheets().add("/resources/css/noteBox.css");
        } catch (IOException exception) {
            System.out.println("Can't load fxml file");
            exception.printStackTrace();
        }
        root.setRight(pane);
        root.applyCss();
        root.layout();
        scroll_note_pane.setPrefWidth(scroll_note_pane.getWidth() - 10);
    }

    Runnable reloadMenuPane = () -> {
        int curIndex = listCategory.getList().indexOf(curCategoryBox);
        initCategories();
        curCategoryBox = listCategory.getList().get((curIndex));
        listCategory.setCurCategory(curCategoryBox);
        loadNotePane();
        loadTitlePane();
    };

    Runnable deleteCategoryBox = () -> {
        int curIndex = listCategory.getList().indexOf(curCategoryBox);
        initCategories();
        curCategoryBox = listCategory.getList().get((curIndex -1));
        curCategoryBox.requestFocus();
        listCategory.setCurCategory(curCategoryBox);
        loadTitlePane();
        loadNotePane();
    };

    Runnable updateNoteStatus = () -> {
        root.applyCss();
        root.layout();
        if(root.getRight() == null) {
            scroll_note_pane.setPrefWidth(scroll_note_pane.getWidth() - 10);
        }
        else {
            loadNotePane();
        }
        try {
            curCategoryBox.updateNumOfNotes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

}
