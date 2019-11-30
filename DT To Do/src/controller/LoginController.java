package controller;

import BUS.UserBUS;
import DTO.UserDTO;
import helper.ConfirmDialogHelper;
import helper.PopupHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Stage mainStage;
    private MainController mainController;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Text textLoginFail;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainStage = PopupHelper.createStage("/resources/fxml/todo_main.fxml", 1280, 800);
        mainStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> {
            if (ConfirmDialogHelper.confirm("Xác nhận thoát?")) {
                PopupHelper.createStage("/resources/fxml/LoginForm.fxml", 825, 522).show();
            } else {
                e.consume();
            }
        });
        FXMLLoader loader = (FXMLLoader) mainStage.getUserData();
        mainController = loader.getController();
        textLoginFail.setVisible(false);
    }

    public void handleLogin() {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        try {
            if (UserBUS.checkLoginUser(username, password)) {
                Stage loginStage = (Stage) tfUsername.getScene().getWindow();
                UserDTO user = UserBUS.getUserByUsername(username);
                loginStage.close();
                mainController.initialize(user);
                mainStage.hide();
                mainStage.showAndWait();
            } else {
                tfUsername.requestFocus();
                textLoginFail.setVisible(true);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể đăng nhập!");
            alert.setContentText("Lỗi database!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void handleSignUp() {
        Stage signInStage = PopupHelper.createStage("/resources/fxml/SignUpForm.fxml", 757, 530);
        signInStage.showAndWait();
    }
}
