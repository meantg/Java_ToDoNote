
package controller;

import BUS.NoteBUS;
import DTO.NoteDTO;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import custom.ListSmartCategoryBox;
import custom.NoteBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.icons525.Icons525;
import de.jensd.fx.glyphs.icons525.Icons525View;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.weathericons.WeatherIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditPaneController implements Initializable {
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
    private Integer stateID;
    private LocalDate dueDate;
    private ContextMenu contextMenu;
    private int maPhanLoai;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    private LocalDate date;
    private LocalDate plusdate;
    private DatePicker dp;
    private DatePickerSkin datePickerSkin;

    public EditPaneController() {
    }

    public void loadNote(NoteBox noteBox) {
        this.noteDTO = noteBox.getNote();

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
        checkBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                handleSaveNote();
                Runnable updateNoteStatus = (Runnable) pane.getUserData();
                updateNoteStatus.run();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        star_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            isImportance = !isImportance;
            if (isImportance) {
                star_icon.setFill(Color.YELLOW);
                star_icon.setStroke(Color.TRANSPARENT);
            } else {
                star_icon.setFill(Color.TRANSPARENT);
                star_icon.setStroke(Color.BLACK);
            }
            try {
                handleSaveNote();
                ListSmartCategoryBox.getList().get(1).reloadBox(noteDTO.getUserID());
                Runnable updateNoteStatus = (Runnable) pane.getUserData();
                updateNoteStatus.run();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        tf_editNote_NoteName.setText(noteDTO.getTitle());
        ta_editNote_NoteDiscription.setText(noteDTO.getDescription());
        LocalDate createdDate = noteDTO.getCreateDate().toLocalDate();
        lbCreatedDate.setText(String.format("Create on %s, %s %s", createdDate.getDayOfWeek(), createdDate.getMonth(), createdDate.getYear()));
        //System.out.printf("Create on %s, %s %s", createdDate.getDayOfWeek(), createdDate.getMonth(), createdDate.getYear());
        reloadEditPane();
        addMyDay.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            isMyDay = true;
            try {
                handleSaveNote();
                ListSmartCategoryBox.getList().get(0).reloadBox(noteDTO.getUserID());
                Runnable updateNoteStatus = (Runnable) pane.getUserData();
                updateNoteStatus.run();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            reloadEditPane();
        });
        btnDelMyDay.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            isMyDay = false;
            try{
                handleSaveNote();
                ListSmartCategoryBox.getList().get(0).reloadBox(noteDTO.getUserID());
                Runnable updateNoteStatus = (Runnable) pane.getUserData();
                updateNoteStatus.run();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            reloadEditPane();
        });

        btnDelDueDate.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> {
            dueDate = null;
            try {
                handleSaveNote();
                ListSmartCategoryBox.getList().get(0).reloadBox(noteDTO.getUserID());
                Runnable updateNoteStatus = (Runnable) pane.getUserData();
                updateNoteStatus.run();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            reloadEditPane();
        });

        addDueDate.setOnMouseClicked(e-> {
            showContextMenu();
        });

    }

    public void reloadEditPane() {
        if (isImportance) {
            star_icon.setFill(Color.YELLOW);
            star_icon.setStroke(Color.TRANSPARENT);
            //ListSmartCategoryBox.getList().get(1).reloadBox(noteDTO.getUserID());
        } else {
            star_icon.setFill(Color.TRANSPARENT);
            star_icon.setStroke(Color.BLACK);
            //ListSmartCategoryBox.getList().get(1).reloadBox(noteDTO.getUserID());
        }

        if(isMyDay) {
            lbMyDay.setFill(Color.BLUE);
            lbMyDay.setText("Added to My Day");
            iconMyDay.setFill(Color.BLUE);
            btnDelMyDay.setVisible(true);

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

    public void setImportance() {
        isImportance = !isImportance;
        reloadEditPane();
    }



    public void handleSaveNote() throws SQLException {
        //TODO: isMyDay và isImportance set nếu button được bấm, dueDate sẽ chọn ngày.
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

    public void handleClose() throws SQLException {
        BorderPane root = (BorderPane) tf_editNote_NoteName.getScene().getRoot();
        root.getChildren().remove(root.getRight());
        handleSaveNote();
        Runnable reloadMenuPane = (Runnable) pane.getUserData();
        reloadMenuPane.run();
    }

    public void handleDeleteNote() throws SQLException {
        BorderPane root = (BorderPane) tf_editNote_NoteName.getScene().getRoot();
        root.getChildren().remove(root.getRight());
        NoteBUS.deleteNote(noteDTO.getNoteID());
        Runnable updateNoteStatus = (Runnable) pane.getUserData();
        updateNoteStatus.run();
    }

    public NoteDTO getNoteEditing() {
        return noteDTO;
    }

    public void initContextMenu(){
        contextMenu.setMaxSize(180,180);


        MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.HISTORY);
        icon.setGlyphSize(24);
        MaterialDesignIconView iconNextWeek = new MaterialDesignIconView(MaterialDesignIcon.CALENDAR_PLUS);
        iconNextWeek.setGlyphSize(24);
        MaterialDesignIconView iconNextMonth = new MaterialDesignIconView(MaterialDesignIcon.CALENDAR_MULTIPLE);
        iconNextMonth.setGlyphSize(24);
        Icons525View iconPickADate = new Icons525View(Icons525.CALENDAR_OPEN);
        iconPickADate.setGlyphSize(24);


        Label lbTomorrow = new Label("Tomorrow");
        Label lbTomorrow2 = new Label(date.plusDays(1).getDayOfWeek().toString().substring(0,3));
        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);

        Label lbWeek = new Label("Next week");
        Label lbWeek2 = new Label(date.plusDays(7).getDayOfWeek().toString().substring(0,3));

        Label lbMonth = new Label("Next month");
        Label lbMonth2 = new Label(date.plusDays(30).getDayOfWeek().toString().substring(0,3));

        Label lbPickDate = new Label("Pick a date");

        HBox tomorrowBox = new HBox();
        tomorrowBox.setSpacing(15);
        tomorrowBox.setPadding(new Insets(15));
        tomorrowBox.setMaxSize(200, 45);
        tomorrowBox.setMinSize(200, 45);
        tomorrowBox.setPrefSize(200, 45);
        tomorrowBox.getChildren().addAll(icon, lbTomorrow, spacer, lbTomorrow2);

        final Pane spacer1 = new Pane();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        spacer1.setMinSize(10, 1);

        HBox nextWeekBox = new HBox();
        nextWeekBox.setSpacing(15);
        nextWeekBox.setPadding(new Insets(15));
        nextWeekBox.setMaxSize(200, 45);
        nextWeekBox.setMinSize(200, 45);
        nextWeekBox.setPrefSize(200, 45);
        nextWeekBox.getChildren().addAll(iconNextWeek, lbWeek, spacer1, lbWeek2);

        final Pane spacer2 = new Pane();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        spacer2.setMinSize(10, 1);

        HBox nextMonthBox = new HBox();
        nextMonthBox.setSpacing(15);
        nextMonthBox.setPadding(new Insets(15));
        nextMonthBox.setMaxSize(200, 45);
        nextMonthBox.setMinSize(200, 45);
        nextMonthBox.setPrefSize(200, 45);
        nextMonthBox.getChildren().addAll(iconNextMonth, lbMonth, spacer2, lbMonth2);


        final Pane spacer3 = new Pane();
        HBox.setHgrow(spacer3, Priority.ALWAYS);
        spacer3.setMinSize(10, 1);

        HBox pickDateBox = new HBox();
        pickDateBox.setSpacing(15);
        pickDateBox.setPadding(new Insets(15));
        pickDateBox.setMaxSize(200, 45);
        pickDateBox.setMinSize(200, 45);
        pickDateBox.setPrefSize(200, 45);
        pickDateBox.getChildren().addAll(iconPickADate, lbPickDate, spacer3);

        CustomMenuItem item1 = new CustomMenuItem();
        item1.setContent(tomorrowBox);
        CustomMenuItem item2 = new CustomMenuItem();
        item2.setContent(nextWeekBox);
        CustomMenuItem item3 = new CustomMenuItem();
        item3.setContent(nextMonthBox);
        CustomMenuItem item4 = new CustomMenuItem();
        item4.setContent(pickDateBox);

        contextMenu.getItems().addAll(item1,item2,item3,item4);

        tomorrowBox.setOnMouseClicked((event -> {
            plusdate = date.plusDays(1);
            dueDate = plusdate;
            lbDueDate.setText(formatter.format(plusdate));
            try {
                handleSaveNote();
                ListSmartCategoryBox.getList().get(0).reloadBox(noteDTO.getUserID());
                Runnable updateNoteStatus = (Runnable) pane.getUserData();
                updateNoteStatus.run();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            reloadEditPane();
        }));

        nextWeekBox.setOnMouseClicked((event -> {
            plusdate = date.plusDays(7);
            dueDate = plusdate;
            try {
                handleSaveNote();
                ListSmartCategoryBox.getList().get(0).reloadBox(noteDTO.getUserID());
                Runnable updateNoteStatus = (Runnable) pane.getUserData();
                updateNoteStatus.run();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            reloadEditPane();
            lbDueDate.setText(formatter.format(plusdate));
        }));

        nextMonthBox.setOnMouseClicked((event -> {
            plusdate = date.plusDays(30);
            dueDate = plusdate;
            try {
                handleSaveNote();
                ListSmartCategoryBox.getList().get(0).reloadBox(noteDTO.getUserID());
                Runnable updateNoteStatus = (Runnable) pane.getUserData();
                updateNoteStatus.run();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            reloadEditPane();
            lbDueDate.setText(formatter.format(plusdate));
        }));

        pickDateBox.setOnMouseClicked(e -> {
            System.out.println("Clicked");
            initDatePicker(e);
        });
    }

    public void initDatePicker(MouseEvent event){
        Node node = datePickerSkin.getPopupContent();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.setTitle("Pick a date!");
        alert.setX(event.getScreenX());
        alert.setY(event.getScreenY() - 180);
        alert.getDialogPane().setContent(node);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            dueDate = dp.getValue();
            try {
                handleSaveNote();
                ListSmartCategoryBox.getList().get(0).reloadBox(noteDTO.getUserID());
                Runnable updateNoteStatus = (Runnable) pane.getUserData();
                updateNoteStatus.run();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            reloadEditPane();
        } else if (option.get() == ButtonType.CANCEL){
        }
    }

    public void showContextMenu(){
        Stage stage = (Stage)tf_editNote_NoteName.getScene().getWindow();
        contextMenu.show(addDueDate, Side.BOTTOM, 0, 5);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contextMenu = new ContextMenu();
        date = LocalDate.now();
        dp = new DatePicker();
        datePickerSkin = new DatePickerSkin(dp);
        initContextMenu();
//        initDatePicker();

    }
}