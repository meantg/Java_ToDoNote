package controller;

import BUS.CategoryBUS;
import BUS.NoteBUS;
import BUS.TinhTrangBUS;
import DTO.CategoryDTO;
import DTO.NoteDTO;
import DTO.TinhTrangDTO;
import DTO.UserDTO;
import custom.*;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class MainController {

    @FXML
    VBox note_box;

    @FXML
    BorderPane root;

    @FXML
    HBox title_pane;

    @FXML
    HBox title_bar;

    @FXML
    VBox menu_pane;

    @FXML
    HBox addListBox;

    @FXML
    HBox userBox;

    @FXML
    HBox add_note_box;

    @FXML
    MaterialDesignIconView Icon_PLUS;

    @FXML
    Button btnAddNote;

    @FXML
    Button btnDueDate;

    @FXML
    Button btnRemind;

    @FXML
    Button btnRepeat;

    @FXML
    TextField tfTitle;

    @FXML
    ScrollPane scroll_note_pane;

    @FXML
    ScrollPane scroll_category_pane;

    @FXML
    Label lbName;

    private Scene mainScene;
    private Scene userProfileScene;
    private UserDTO user;
    private AnchorPane pane;
    private ListCategory listCategory;
    private ListSmartCategoryBox listSmartCategory;
    private List<CategoryDTO> smartLists;
    private ListNoteBox listNoteBox;
    private CategoryBox curCategoryBox;
    private SmartCategoryBox curSmartCategoryBox;
    private NoteBox curNoteBox;
    private SORT curSortType = SORT.WORKING;
    private EditPaneController editPaneController;

    private LocalDate dueDate;
    private LocalDate repeat;
    private LocalDateTime reminder;
    private Integer add_note_box_categoryID;


    private enum SORT {
        NORMALLY,
        DONE,
        WORKING
    };


    double x, y;

    public void TitleBarDragged(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - x);
        stage.setY(mouseEvent.getScreenY() - y);
    }

    public void TitleBarPressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }

    public void handleMinimizeApp(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void handleResizeApp(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        if(stage.isFullScreen()) {
            stage.setFullScreen(false);
        }
        else {
            stage.setFullScreen(true);
            stage.setFullScreenExitHint(" ");
        }
    }

    public void handleCloseApp(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void initialize(UserDTO user){
        this.user = user;
        root.applyCss();
        root.layout();
        menu_pane.setOnMousePressed(e-> menu_pane.requestFocus());
        title_pane.setOnMousePressed(e->title_pane.requestFocus());
        mainScene = root.getScene();

        initSideMenu();
        initNotePane();
        initAddNoteBox();
        loadTitlePane();
    }

    public void initAddNoteBox() {
        add_note_box_categoryID = null;
        dueDate = null;
        repeat = null;
        add_note_box.getChildren().removeAll(btnDueDate, btnRemind, btnRepeat);
        tfTitle.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.isEmpty()) {
                    add_note_box.getChildren().removeAll(btnDueDate, btnRemind, btnRepeat);
//                    btnDueDate.setVisible(false);
//                    btnRepeat.setVisible(false);
//                    btnRemind.setVisible(false);
                }
                else {
                    if(!add_note_box.getChildren().contains(btnDueDate))
                        add_note_box.getChildren().addAll(btnDueDate, btnRemind, btnRepeat);
//                    btnDueDate.setVisible(true);
//                    btnRemind.setVisible(true);
//                    btnRepeat.setVisible(true);
                }
            }
        });

        btnAddNote.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(tfTitle.getText().isEmpty() && !tfTitle.isFocused()) {
                tfTitle.requestFocus();
            }
            else {
                //TODO: add note
                try {
                    if (curCategoryBox != null) {
                        NoteDTO note = new NoteDTO(curCategoryBox.getCategory().getCategoryID(), user.getUserID(), tfTitle.getText(), false, false, dueDate);
                        NoteBUS.insertNote(note);
                        curCategoryBox.updateNumOfNotes();
                    }
                    else if (curSmartCategoryBox != null) {
                        NoteDTO note = new NoteDTO(add_note_box_categoryID, user.getUserID(), tfTitle.getText(), false, false, dueDate);
                        NoteBUS.insertNote(note);
                        listSmartCategory.getList().forEach(smartCategoryBox -> {
                            smartCategoryBox.reloadBox(user.getUserID());
                        });
                    }

                    tfTitle.clear();
                    add_note_box_categoryID = null;
                    dueDate = null;
                    repeat = null;
                    loadNotePane();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        tfTitle.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    System.out.println("Request focus tf");
                    tfTitle.requestFocus();
                    Icon_PLUS.setGlyphName("CHECKBOX_BLANK_CIRCLE_OUTLINE");
                }
                else {
                    if(!(btnDueDate.isFocused() || btnRemind.isFocused() || btnRepeat.isFocused())) {
                        Icon_PLUS.setGlyphName("PLUS");
                    }
                }
            }
        });

        btnDueDate.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    if(!(tfTitle.isFocused() || btnRemind.isFocused() || btnRepeat.isFocused())) {
                        Icon_PLUS.setGlyphName("PLUS");
                    }
                }
            }
        });

        btnRemind.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    if(!(tfTitle.isFocused() || btnDueDate.isFocused() || btnRepeat.isFocused())) {
                        Icon_PLUS.setGlyphName("PLUS");
                    }
                }
            }
        });

        btnRepeat.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    if(!(tfTitle.isFocused() || btnRemind.isFocused() || btnDueDate.isFocused())) {
                        Icon_PLUS.setGlyphName("PLUS");
                    }
                }
            }
        });

    }

    public void initSideMenu() {
        initSmartCategoriesList(user);
        initCategories();
        initUserBox(user);
        initAddListBox();

        curSmartCategoryBox = listSmartCategory.getList().get(0);
        listSmartCategory.setCurSmartCategory(curSmartCategoryBox);
    }

    public void initNotePane() {
        scroll_note_pane.applyCss();
        scroll_note_pane.layout();

        scroll_note_pane.prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scroll_note_pane.setPrefWidth(newValue.doubleValue());
                note_box.setPrefWidth(newValue.doubleValue());
                //note_box.setPrefWidth(newValue.doubleValue() - 2);
                loadNotePane();
            }
        });

        scroll_note_pane.prefHeightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scroll_note_pane.setPrefHeight(newValue.doubleValue());
                note_box.setPrefHeight(newValue.doubleValue());
                //note_box.setPrefHeight(newValue.doubleValue() - 2);
                //loadNotePane();
            }
        });
        scroll_note_pane.setPrefSize(scroll_note_pane.getWidth(), scroll_note_pane.getHeight());
        //note_box.setPrefSize(scroll_note_pane.getPrefWidth(), scroll_note_pane.getPrefHeight());
    }

    public void initUserBox(UserDTO user) {
        lbName.setText(user.getFullname());
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

    public void initSmartCategoriesList(UserDTO user){
        Integer userID = user.getUserID();
        smartLists = new ArrayList<CategoryDTO>();
        smartLists.add(new CategoryDTO(userID, "My Day"));
        smartLists.add(new CategoryDTO(userID, "Importance"));
        smartLists.add(new CategoryDTO(userID, "Planned"));
        smartLists.add(new CategoryDTO(userID, "Tasks"));
    }


    public void initAddListBox() {
        addListBox.setOnMouseClicked(e-> {
            try {
                CategoryBUS.insertCategory(new CategoryDTO(user.getUserID(), "Untitled List", "☰", 0));
                initCategories();
                curSmartCategoryBox = null;
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
        menu_pane.getChildren().clear();

        try {
            listSmartCategory = new ListSmartCategoryBox(smartLists);
            listSmartCategory.getList().stream().forEach(smartCategoryBox -> {
                smartCategoryBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if(curSmartCategoryBox != smartCategoryBox && root.getRight() != null) {
                        closeEditPane();
                    }
                    if(curCategoryBox != null)
                    curCategoryBox.changeBackgroundColor(Color.TRANSPARENT);
                    curCategoryBox = null;
                    curSmartCategoryBox = smartCategoryBox;
                    loadNotePane();
                    loadTitlePane();
                });
                smartCategoryBox.setUserData(deleteCategoryBox);
                smartCategoryBox.getChildren().get(0).setUserData(reloadMenuPane);
                menu_pane.getChildren().add(smartCategoryBox);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        Separator sp = new Separator();
        sp.setPadding(new Insets(0,15,0,15));
        menu_pane.getChildren().add(sp);
        //Add category box into category pane
        try {
            //Get list category by UserID
            listCategory = new ListCategory(CategoryBUS.getListCategory(user.getUserID()));
            listCategory.getList().stream().forEach(categoryBox -> {
                categoryBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if(curCategoryBox != categoryBox && root.getRight() != null) {
                        closeEditPane();
                    }
                    if(curSmartCategoryBox != null)
                        curSmartCategoryBox.changeBackgroundColor(Color.TRANSPARENT);
                    curSmartCategoryBox = null;
                    curCategoryBox = categoryBox;
                    loadNotePane();
                    loadTitlePane();
                });
                categoryBox.setUserData(deleteCategoryBox);
                categoryBox.getChildren().get(0).setUserData(reloadMenuPane);
                menu_pane.getChildren().add(categoryBox);
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

    //TODO: add thêm hàm set theme color

    public void loadTitlePane() {
        title_pane.getChildren().clear();
        if(curCategoryBox != null) {
            EditableCategoryBox editBox = new EditableCategoryBox(curCategoryBox);
            editBox.setUserData(reloadMenuPane);
            title_pane.getChildren().add(editBox);
        }
        else if(curSmartCategoryBox != null) {
            EditableCategoryBox titleBox = new EditableCategoryBox(curSmartCategoryBox);
            title_pane.getChildren().add(titleBox);
        }
    }

    public void loadNotePane() {
        note_box.getChildren().clear();
        switch(curSortType) {
            case NORMALLY:
                try {
                    if(curCategoryBox != null) {
                        listNoteBox = new ListNoteBox(NoteBUS.getToDoList(curCategoryBox.getCategory().getCategoryID()), note_box);
                    }
                    else if(curSmartCategoryBox != null) {
                        switch(curSmartCategoryBox.getName()) {
                            case "My Day":
                                listNoteBox = new ListNoteBox(NoteBUS.getMyDayNotes(user.getUserID()), note_box);
                                break;
                            case "Importance":
                                listNoteBox = new ListNoteBox(NoteBUS.getImportanceNotes(user.getUserID()), note_box);
                                break;
                            case "Planned":
                                listNoteBox = new ListNoteBox(NoteBUS.getPlannedNotes(user.getUserID()), note_box);
                                break;
                            case "Tasks":
                                listNoteBox = new ListNoteBox(NoteBUS.getTasksNotes(user.getUserID()), note_box);
                                break;
                            default:
                                System.out.println("ERROR");
                                break;
                        }
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case DONE:
                try {
                    if(curCategoryBox != null) {
                        listNoteBox = new ListNoteBox(NoteBUS.getToDoList(curCategoryBox.getCategory().getCategoryID(), false), note_box);
                    }
                    else if(curSmartCategoryBox != null) {
                        switch(curSmartCategoryBox.getName()) {
                            case "My Day":
                                listNoteBox = new ListNoteBox(NoteBUS.getMyDayNotes(user.getUserID(), false), note_box);
                                break;
                            case "Importance":
                                listNoteBox = new ListNoteBox(NoteBUS.getImportanceNotes(user.getUserID(), false), note_box);
                                break;
                            case "Planned":
                                listNoteBox = new ListNoteBox(NoteBUS.getPlannedNotes(user.getUserID(), false), note_box);
                                break;
                            case "Tasks":
                                listNoteBox = new ListNoteBox(NoteBUS.getTasksNotes(user.getUserID(), false), note_box);
                                break;
                            default:
                                System.out.println("ERROR");
                                break;
                        }
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case WORKING:
                try {
                    if(curCategoryBox != null) {
                        listNoteBox = new ListNoteBox(NoteBUS.getToDoList(curCategoryBox.getCategory().getCategoryID(), true), note_box);
                    }
                    else if(curSmartCategoryBox != null) {
                        switch(curSmartCategoryBox.getName()) {
                            case "My Day":
                                listNoteBox = new ListNoteBox(NoteBUS.getMyDayNotes(user.getUserID(), true), note_box);
                                break;
                            case "Importance":
                                listNoteBox = new ListNoteBox(NoteBUS.getImportanceNotes(user.getUserID(), true), note_box);
                                break;
                            case "Planned":
                                listNoteBox = new ListNoteBox(NoteBUS.getPlannedNotes(user.getUserID(), true), note_box);
                                break;
                            case "Tasks":
                                listNoteBox = new ListNoteBox(NoteBUS.getTasksNotes(user.getUserID(), true), note_box);
                                break;
                            default:
                                System.out.println("ERROR");
                                break;
                        }
                    }
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
                    if (root.getRight() != null & curNoteBox.getNote().getNoteID().equals(editPaneController.getNoteEditing().getNoteID())) {
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
                    if(root.getRight() != null && curNoteBox.getNote().getNoteID().equals(editPaneController.getNoteEditing().getNoteID())) {
                        editPaneController.checkBtn.setSelected(newValue.booleanValue());
                    }
                    else {
                        try {
                            NoteBUS.updateTinhTrang(newValue ? 12001 : 12002,curNoteBox.getNote().getNoteID());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    loadNotePane();
                    try {
                        if(curCategoryBox != null) {
                            curCategoryBox.updateNumOfNotes();
                        }
                        else if(curSmartCategoryBox != null) {
                           curSmartCategoryBox.reloadBox(user.getUserID());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            note_box.getChildren().add(noteBox);
        });
    }

    public void closeEditPane() {
        try {
            editPaneController.handleSaveNote();
            root.getChildren().remove(root.getRight());
            root.applyCss();
            root.layout();
            scroll_note_pane.setPrefWidth(scroll_note_pane.getWidth());
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
            //pane.setPrefWidth(400);
            pane.setUserData(updateNoteStatus);
            pane.getStylesheets().add("/resources/css/noteBox.css");
        } catch (IOException exception) {
            System.out.println("Can't load fxml file");
            exception.printStackTrace();
        }
        root.setRight(pane);
        root.applyCss();
        root.layout();
        scroll_note_pane.setPrefWidth(scroll_note_pane.getWidth());
    }

    Runnable reloadMenuPane = () -> {
        if(curCategoryBox != null) {
            int curIndex = listCategory.getList().indexOf(curCategoryBox);
            initCategories();
            curCategoryBox = listCategory.getList().get((curIndex));
            listCategory.setCurCategory(curCategoryBox);
        }
        else if(curSmartCategoryBox != null) {
            int curSmartIndex = listSmartCategory.getList().indexOf(curSmartCategoryBox);
            initCategories();
            curSmartCategoryBox = listSmartCategory.getList().get(curSmartIndex);
            listSmartCategory.setCurSmartCategory(curSmartCategoryBox);
        }
        loadNotePane();
        loadTitlePane();
    };

    Runnable deleteCategoryBox = () -> {
        if(curCategoryBox != null) {
            int curIndex = listCategory.getList().indexOf(curCategoryBox);
            if(curIndex - 1 >= 0) {
                initCategories();
                curCategoryBox = listCategory.getList().get((curIndex - 1));
                curCategoryBox.requestFocus();
                listCategory.setCurCategory(curCategoryBox);
            }
            else {
                curCategoryBox = null;
                initCategories();
                curSmartCategoryBox = listSmartCategory.getList().get(listSmartCategory.getList().size() - 1);
                curSmartCategoryBox.requestFocus();
                listSmartCategory.setCurSmartCategory(curSmartCategoryBox);
            }
        }
        loadTitlePane();
        loadNotePane();
    };

    Runnable updateNoteStatus = () -> {
        root.applyCss();
        root.layout();
        if(root.getRight() == null) {
            scroll_note_pane.setPrefWidth(scroll_note_pane.getWidth());
        }
        else {
            loadNotePane();
        }
        try {
            if(curCategoryBox != null)
                curCategoryBox.updateNumOfNotes();
            else if(curSmartCategoryBox != null) {
                curSmartCategoryBox.reloadBox(user.getUserID());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

}
