package controller;

import DTO.UserDTO;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UserProfileController {

    @FXML
    Label lbName;
    @FXML
    Label lbAvatar;
    @FXML
    Label lbUsername;
    @FXML
    Label lbGender;
    @FXML
    Label lbEmail;
    @FXML
    Label lbPhoneNumber;
    @FXML
    BorderPane root;

    public UserProfileController() {}

    public void handleBack() {
        Scene mainScene = (Scene)root.getUserData();
        Stage stage = (Stage)lbName.getScene().getWindow();
        stage.setScene(mainScene);
        System.out.println("Back");
    }

    public void initialize(UserDTO user) {
        lbName.setText(user.getUsername());
        lbAvatar.setText(user.getGender().equals("Nam") ? "+" : "-" );
        lbUsername.setText(user.getUsername());
        lbGender.setText(user.getGender());
        lbEmail.setText(user.getEmail());
        lbPhoneNumber.setText(user.getPhoneNumber());
    }
}