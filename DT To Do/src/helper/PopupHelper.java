package helper;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class PopupHelper {
    public static Stage createStage(String link, Integer width, Integer height) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(URL.class.getResource(link));
            Scene scene = new Scene(loader.load(), width, height);
            stage.setUserData(loader);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("📚 DT To Do 📚");
            stage.setResizable(false);
            stage.setScene(scene);
            return stage;
        } catch (IOException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể mở pop-up stage!");
            alert.setContentText("Lỗi đường dẫn đến link pop-up!");
            alert.showAndWait();
            return null;
        }
    }
}