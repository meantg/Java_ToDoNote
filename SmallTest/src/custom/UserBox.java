package custom;

import DTO.UserDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UserBox extends HBox {
    private Label lbAvatar;
    private Label lbUsername;

    public UserBox(UserDTO user) {
        lbAvatar = new Label();
        lbAvatar.setText(user.getGioiTinh().equals("Nam") ? "➕" : "Nữ");
        lbAvatar.setPrefSize(70,10);
        lbAvatar.setFont(Font.font("system", 20));
        lbAvatar.setAlignment(Pos.CENTER);

        lbUsername = new Label();
        lbUsername.setText(user.getTenNguoiDung());
        lbUsername.setAlignment(Pos.CENTER_LEFT);
        lbUsername.setFont(Font.font("system", 20));
        lbUsername.setPrefSize(150,10);

        this.setPrefSize(330, 60);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(10));
        this.getChildren().clear();
        this.getChildren().addAll(lbAvatar, lbUsername);
    }
}
