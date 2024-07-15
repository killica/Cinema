package cinema;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Download {

    private final FileChooser fileChooser = new FileChooser();

    public Download(Stage stage4, Stage stage0, Stage stage, Cart cart, List<String> selectedSeats, int sala) {
        Group root = new Group();
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);

        Label text = new Label("Your reservation has been recorded successfully.\nSelect a folder where you want to download your ticket.\nAfter downloading it, print it and bring it on projection.");
        text.setTextFill(Color.ROYALBLUE);
        text.setLayoutX(10);
        text.setLayoutY(5);
        text.setWrapText(true);
        text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 22));

        Button download = new Button("Download", GlyphsDude.createIcon(FontAwesomeIcons.DOWNLOAD, "20px"));
        download.setLayoutX(225);
        download.setLayoutY(150);
        download.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

        download.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            download.setStyle("-fx-font-size: 1.35em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-text-fill: white");

        });
        download.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            download.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");
        });

        download.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            cart.generatePDF(stage4, stage0, stage, primaryStage, selectedSeats, sala);

        });

        root.getChildren().addAll(text, download);
        Scene scene = new Scene(root, 625, 200);

        primaryStage.setTitle("Download tickets");
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();

    }

}
