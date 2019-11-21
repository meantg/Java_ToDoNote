package custom;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AddListBox extends HBox {
    private Label lbIcon;
    private Label lbTitle;

    public AddListBox() {
        lbIcon = new Label();
        lbIcon.setText("➕");
        lbIcon.setPrefSize(70,10);
        lbIcon.setFont(Font.font("system", 20));
        lbIcon.setTextFill(Color.AQUAMARINE);
        lbIcon.setAlignment(Pos.CENTER);

        lbTitle = new Label();
        lbTitle.setText("New List");
        lbTitle.setAlignment(Pos.CENTER_LEFT);
        lbTitle.setFont(Font.font("system", 20));
        lbTitle.setTextFill(Color.AQUAMARINE);
        lbTitle.setPrefSize(150,10);

        this.setPrefWidth(330);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(10));
        this.getChildren().clear();
        this.getChildren().addAll(lbIcon,lbTitle);


        this.setOnMouseEntered(e -> {
            this.setBackground(
                    new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        });

        this.setOnMouseExited(e -> {
            this.setBackground(
                    new Background(new BackgroundFill(Color.valueOf("dcde54"), CornerRadii.EMPTY, Insets.EMPTY)));
        });

    }
}
