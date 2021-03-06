package controller;

import BUS.UserBUS;
import DTO.UserDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    //private Stage mainStage;
    //private MainController mainController;

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfUserName;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private PasswordField pfRePassword;
    @FXML
    private CheckBox cdGender_M;
    @FXML
    private CheckBox cdGender_F;
    @FXML
    private CheckBox cdGender_O;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
/*        mainStage = PopupHelper.createStage("/application/todo_main.fxml", 1280, 800);
        mainStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> {
            if (ConfirmDialogHelper.confirm("Xác nhận thoát ?")) {
                PopupHelper.createStage("/application/LoginForm.fxml", 825, 522).show();
            } else {
                e.consume();
            }
        });
        FXMLLoader loader = (FXMLLoader) mainStage.getUserData();
        mainController = loader.getController();*/

        cdGender_F.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                cdGender_M.setSelected(false);
                cdGender_O.setSelected(false);
                cdGender_F.setSelected(newValue);
            }
        });
        cdGender_M.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                cdGender_F.setSelected(false);
                cdGender_O.setSelected(false);
                cdGender_M.setSelected(newValue);
            }
        });
        cdGender_O.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                cdGender_M.setSelected(false);
                cdGender_F.setSelected(false);
                cdGender_O.setSelected(newValue);
            }
        });


    }

    public void handleSignUp() throws SQLException {
        String name = tfName.getText();
        String email = tfEmail.getText();
        String phoneNumber = tfPhoneNumber.getText();
        String username = tfUserName.getText();
        String password = pfPassword.getText();
        String rePassword = pfRePassword.getText();
        String gender = cdGender_F.isSelected() ? "Nữ" : cdGender_M.isSelected() ? "Nam" : "Other";

        if(!password.equals(rePassword))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Password and Repassword are not the same !!!");
            alert.setContentText("Please input again!");
            alert.showAndWait();
            return;
        }

        UserDTO user = new UserDTO(name, username, password, gender, phoneNumber, email);

        if(UserBUS.insertUser(user)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Tạo tài khoản thành công");
            alert.setContentText("Đã tạo tài khoản thành công!");
            alert.showAndWait();
            Stage stage = (Stage)tfUserName.getScene().getWindow();
            stage.close();
        } else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Tạo tài khoản thất bại!!!");
            alert.setContentText("Tài khoản đã tồn tại!");
            alert.showAndWait();
        }


    }
}