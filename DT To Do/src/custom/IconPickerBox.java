package custom;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;
import java.util.List;

public class IconPickerBox extends MenuButton {
    public IconPickerBox() {
        BorderPane contentPane = new BorderPane();
        contentPane.setPadding(new Insets(10, 0, 0, 0));

        TilePane pane = new TilePane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPrefSize(150, 100);
        pane.setPadding(new Insets(0,0,10,0));

        List<String> list = new ArrayList<String>();
        list.add("🔍");
        list.add("🛒");
        list.add("C");
        list.add("D");
        list.add("E");
        list.add("F");
        list.add("F");
        list.add("F");
        list.add("F");
        list.add("F");
        list.add("F");
        list.add("F");
        list.add("F");
        list.add("F");
        list.add("F");
        list.add("F");
        list.add("F");
        list.stream().forEach(s-> {
            Button b = new Button(s);
            b.setOnAction(e-> {
                this.setText(b.getText());
            });
            pane.getChildren().add(b);
        });

        ScrollPane spane = new ScrollPane();
        spane.setContent(pane);
        spane.setFitToWidth(true);

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(x -> {
            this.setText(null);
        });

        contentPane.setCenter(spane);
        contentPane.setBottom(clearButton);

        CustomMenuItem item = new CustomMenuItem(contentPane);
        this.getStylesheets().add("resources/css/iconPicker.css");
        this.getItems().addAll(item);
        this.setAlignment(Pos.CENTER);
        this.hide();
    }
}