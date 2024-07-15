package cinema;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutUs {

    public AboutUs() {

        Group root = new Group();
        Stage primaryStage = new Stage();

        Image image1 = new Image(getClass().getResource("Images/logo1.png").toString());
        ImageView iv = new ImageView(image1);
        iv.setLayoutX(20);
        iv.setLayoutY(50);
        iv.setFitHeight(300);
        iv.setFitWidth(300);
        iv.setPreserveRatio(true);

        Label a = new Label("Where movies come to you");
        a.setTextFill(Color.CRIMSON);
        a.setLayoutX(25);
        a.setLayoutY(332);
        a.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 22));

        Label text = new Label("Cinemagic is the Serbian brand that operates in the Film Entertainment and Content. It gained its popularity thanks to its impeccable cinemas with lots of interesting films and top food quality. Started in 2021, as a final project of its owner, Lazar Savic, our company acquired entirely new dimension - online application. Satisfied costumers are the verification of our ultimate goal, and that is to make your night out memorable.");
        text.setTextFill(Color.ROYALBLUE);
        text.setLayoutX(350);
        text.setLayoutY(10);
        text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 22));
        text.setWrapText(true);
        text.setMaxWidth(400);

        root.getChildren().addAll(iv, a, text);
        Scene scene = new Scene(root, 800, 450);
        primaryStage.setTitle("About us");
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();

    }

}
