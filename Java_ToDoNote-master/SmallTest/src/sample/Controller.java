package sample;

import BUS.CategoryBUS;
import BUS.NoteBUS;
import DAO.CategoryDAO;
import DTO.CategoryDTO;
import DTO.NoteDTO;
import custom.AddListBox;
import custom.ListCategory;
import custom.NoteBox;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    Pane UserPane;
    @FXML
    Pane NotePane;

    @FXML
    TilePane menu_pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadMenu();
    }

    public void loadMenu() {
        try {
            ListCategory listCategory = new ListCategory(CategoryBUS.getListCategory(10001));
            listCategory.getList().stream().forEach(categoryBox -> {
                categoryBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> {
                    NotePane.toFront();
                    NotePane.getChildren().clear();
                    switch (categoryBox.getTypeCategory()) {
                        case 10001:
                            try {
                                List<NoteDTO> listNotes = NoteBUS.getToDoList(11001, 10001);
                                listNotes.stream().forEach(note -> {
                                    try {
                                        NoteBox noteBox = new NoteBox(note);
                                        NotePane.getChildren().add(noteBox);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }

                                });
                            }catch (SQLException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 10002:
                            break;
                        default:
                            break;
                    }
                });
                menu_pane.getChildren().add(categoryBox);
            });
        }
        catch (SQLException SQLException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể tải danh sách list!");
            alert.setContentText("Lỗi database!");
            alert.showAndWait();
        }

        menu_pane.getChildren().add(new AddListBox());
    }
    public void handleButton() {
        UserPane.toFront();
        UserPane.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
