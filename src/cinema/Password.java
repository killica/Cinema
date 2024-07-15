package cinema;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Password {

    public Password(String email) {
        Group root = new Group();
        Stage primaryStage = new Stage();

        Label ep = new Label("Enter your email password to continue...");
        ep.setTextFill(Color.CRIMSON);
        ep.setLayoutX(10);
        ep.setLayoutY(30);
        ep.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 20));

        PasswordField pwBox = new PasswordField();
        pwBox.setLayoutX(50);
        pwBox.setLayoutY(130);
        pwBox.setFont(Font.font(null, FontWeight.BOLD, 20));

        Button New = new Button("Send");
        New.setLayoutX(250);
        New.setLayoutY(150);
        New.setMinSize(50, 30);
        New.setStyle("-fx-font-size: 1.2em; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: royalblue");
        New.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            New.setStyle("-fx-font-size: 1.2em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: white");

        });
        New.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            New.setStyle("-fx-font-size: 1.2em; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: royalblue");

        });

        New.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            primaryStage.close();
            try {
                JavaMailUtil jmu = new JavaMailUtil(email, pwBox.getText());
            } catch (Exception ex) {
                Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        root.getChildren().addAll(ep, pwBox, New);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Gmail account validation");
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

}
