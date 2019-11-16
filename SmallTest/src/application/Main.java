package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
        primaryStage.setTitle("To Do");
        primaryStage.setScene(new Scene(root, 825, 522));
        primaryStage.show();
    }


    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể mở CSDL!");
            alert.showAndWait();
        }
        launch(args);
    }
}
