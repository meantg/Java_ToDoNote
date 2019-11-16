package helper;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ConfirmDialogHelper {
    public static boolean confirm(String header) {
        Alert dialog = new Alert(AlertType.CONFIRMATION);
        dialog.setTitle("Xác nhận");
        dialog.setHeaderText(header);
        ButtonType yesButton = new ButtonType("Xác nhận");
        ButtonType noButton = new ButtonType("Hủy bỏ");
        dialog.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = dialog.showAndWait();
        return result.get() == yesButton;
    }

    public static boolean confirm(String header, String content) {
        Alert dialog = new Alert(AlertType.CONFIRMATION);
        dialog.setTitle("Xác nhận");
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        ButtonType yesButton = new ButtonType("Xác nhận");
        ButtonType noButton = new ButtonType("Hủy bỏ");
        dialog.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = dialog.showAndWait();
        return result.get() == yesButton;
    }
}