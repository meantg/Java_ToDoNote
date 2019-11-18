package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import BUS.UserBUS;
import DAO.UserDAO;
import DTO.UserDTO;
import helper.ConfirmDialogHelper;
import helper.PopupHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SignUpController implements Initializable {
    private Stage mainStage;
    private MainController mainController;
    
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfUserName;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfRepassword;
    @FXML
    private CheckBox cdGender_M;
    @FXML
    private CheckBox cdGender_F;
    @FXML
    private CheckBox cdGender_O;

    private UserBUS userBUS;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainStage = PopupHelper.createStage("/application/todo_main.fxml", 1280, 800);
        mainStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> {
            if (ConfirmDialogHelper.confirm("Xác nhận thoát ?")) {
                PopupHelper.createStage("/application/LoginForm.fxml", 825, 522).show();
            } else {
                e.consume();
            }
        });
        FXMLLoader loader = (FXMLLoader) mainStage.getUserData();
        mainController = loader.getController();
    }
    
    public void handleSignUp() throws SQLException {
        System.out.print("SignUp");
        String name = tfName.getText();
        String email = tfEmail.getText();
        String phonenumber = tfPhoneNumber.getText();
        String username = tfUserName.getText();
        String password = tfPassword.getText();
        String repassword = tfRepassword.getText();
        String gender= "Nam";

        if(password != repassword)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Password and Repassword are not the same !!!");
                alert.setContentText("Please input again!");
                alert.showAndWait();
                System.out.print("Sai pass");
            }
        UserDTO user = new UserDTO(name, username, password, gender, phonenumber, email);
        if(userBUS.insertUser(user)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Done");
            alert.setHeaderText("");
            alert.setContentText("Please input again!");
            alert.showAndWait();
            System.out.print("Insert Done");
        } else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Password and Repassword are not the same !!!");
            alert.setContentText("Please input again!");
            alert.showAndWait();
            System.out.print("Khong insert dc");
        }


    }
}