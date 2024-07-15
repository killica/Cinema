package cinema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ForgotPassword {

    public ForgotPassword() {
        Group root = new Group();
        Stage primaryStage = new Stage();
        Image image = new Image(getClass().getResource("Images/bubble.jpeg").toString());
        ImageView iv = new ImageView(image);
        iv.setFitHeight(450);
        iv.setFitWidth(450);
        iv.setLayoutY(150);
        iv.setLayoutX(35);

        Label ma = new Label("What is your email address?");
        ma.setTextFill(Color.BLACK);
        ma.setLayoutX(15);
        ma.setLayoutY(25);
        ma.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 20));

        TextField tr = new TextField();
        tr.setLayoutX(300);
        tr.setLayoutY(25);
        tr.setFont(Font.font(null, FontPosture.ITALIC, 18));
        tr.setMinSize(250, 25);
        tr.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");

        tr.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    Connection con = SqliteConnection.Connector();
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    Label sq = new Label();
                    sq.setTextFill(Color.BLACK);
                    sq.setLayoutX(80);
                    sq.setLayoutY(100);
                    sq.setTextFill(Color.CRIMSON);
                    sq.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 20));
                    sq.setOpacity(0);

                    Label pr = new Label();
                    pr.setTextFill(Color.BLACK);
                    pr.setLayoutX(320);
                    pr.setLayoutY(475);
                    pr.setTextFill(Color.GREEN);
                    pr.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 20));
                    pr.setOpacity(0);

                    TextField ans = new TextField();
                    ans.setLayoutX(270);
                    ans.setLayoutY(220);
                    ans.setFont(Font.font(null, FontPosture.ITALIC, 18));
                    ans.setMinSize(120, 35);
                    ans.setMaxSize(120, 35);
                    ans.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");
                    ans.setOpacity(0);

                    root.getChildren().add(sq);

                    Image image1 = new Image(getClass().getResource("Images/light.jpg").toString());
                    ImageView iv1 = new ImageView(image1);
                    iv1.setFitHeight(200);
                    iv1.setFitWidth(200);
                    iv1.setLayoutY(300);
                    iv1.setLayoutX(340);
                    iv1.setOpacity(0);
                    iv1.toBack();
                    root.getChildren().add(iv1);

                    try {
                        String sql = "SELECT * FROM User WHERE Email = ?";
                        ps = con.prepareStatement(sql);
                        MessageDigest md = new MessageDigest(tr.getText());
                        ps.setString(1, md.getSHA());
                        rs = ps.executeQuery();
                        String securityQuestion = rs.getString("SecurityQuestion");
                        String answer = rs.getString("Answer");
                        sq.setText(securityQuestion);
                        ans.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                if (event.getCode().equals(KeyCode.ENTER)) {
                                    MessageDigest mdAns = new MessageDigest(ans.getText());
                                    if (mdAns.getSHA().equals(answer)) {
                                        ans.setDisable(true);
                                        FadeTransition fd = new FadeTransition();
                                        fd.setDuration(Duration.millis(1000));
                                        fd.setNode(iv1);
                                        fd.setFromValue(0);
                                        fd.setToValue(1);
                                        fd.play();
                                        fd.setOnFinished(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {

                                                root.getChildren().add(pr);
                                                pr.setText("              Correct!\nEnter your new password: ");
                                                FadeTransition fd1 = new FadeTransition();
                                                fd1.setDuration(Duration.millis(1000));
                                                fd1.setNode(pr);
                                                fd1.setFromValue(0);
                                                fd1.setToValue(1);
                                                fd1.play();

                                                fd1.setOnFinished(new EventHandler<ActionEvent>() {
                                                    @Override
                                                    public void handle(ActionEvent event) {

                                                        TextField newPass = new TextField();
                                                        newPass.setLayoutX(330);
                                                        newPass.setLayoutY(550);
                                                        newPass.setFont(Font.font(null, FontPosture.ITALIC, 18));
                                                        newPass.setMinSize(120, 35);
                                                        newPass.setMaxSize(120, 35);
                                                        newPass.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");
                                                        newPass.setOpacity(0);
                                                        root.getChildren().add(newPass);
                                                        FadeTransition fd2 = new FadeTransition();
                                                        fd2.setDuration(Duration.millis(1000));
                                                        fd2.setNode(newPass);
                                                        fd2.setFromValue(0);
                                                        fd2.setToValue(1);
                                                        fd2.play();
                                                        fd2.setOnFinished(new EventHandler<ActionEvent>() {
                                                            @Override
                                                            public void handle(ActionEvent event) {

                                                                Button spt = new Button("Go!");
                                                                spt.setLayoutX(480);
                                                                spt.setLayoutY(550);
                                                                //spt.setMinSize(35,50);
                                                                spt.setMaxHeight(35);
                                                                spt.setMinHeight(35);
                                                                //spt.setMaxSize(35, 35);
                                                                spt.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-color: orange; -fx-text-fill: #000000");
                                                                spt.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
                                                                    spt.setStyle("-fx-font-size: 1.5em; -fx-background-color: crimson; -fx-font-weight: bold;  -fx-text-fill: white");

                                                                });
                                                                spt.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
                                                                    spt.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-color: orange; -fx-text-fill: #000000");

                                                                });

                                                                spt.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                                                                    try {
                                                                        con.close();

                                                                        MessageDigest md = new MessageDigest(newPass.getText());
                                                                        MessageDigest md1 = new MessageDigest(tr.getText());
                                                                        String query = "UPDATE User SET Password = ? WHERE Email = ?";
                                                                        try (Connection con1 = SqliteConnection.Connector(); PreparedStatement ps = con1.prepareStatement(query)) {
                                                                            ps.setString(1, md.getSHA());
                                                                            ps.setString(2, md1.getSHA());
                                                                            ps.executeUpdate();
                                                                        }
                                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                                        alert.setHeaderText("Password change successful!");
                                                                        alert.setContentText("You can now log in with your new password.");
                                                                        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.YES);
                                                                        alert.getButtonTypes().setAll(okButton);
                                                                        alert.showAndWait().ifPresent(type -> {

                                                                            if (type.getText().toString().equals("Ok")) {

                                                                                primaryStage.close();

                                                                            } else {
                                                                                event.consume();

                                                                            }

                                                                        });

                                                                    } catch (SQLException ex) {
                                                                        System.out.println("NEUSPESNA OBNOVA LOZINKE!");
                                                                        System.out.println(ex);
                                                                    }

                                                                });
                                                                spt.setOpacity(0);
                                                                root.getChildren().add(spt);
                                                                FadeTransition fd3 = new FadeTransition();
                                                                fd3.setDuration(Duration.millis(1000));
                                                                fd3.setNode(spt);
                                                                fd3.setFromValue(0);
                                                                fd3.setToValue(1);
                                                                fd3.play();

                                                            }

                                                        });

                                                    }

                                                });

                                            }
                                        });
                                    } else {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Wrong answer!");
                                        alert.setHeaderText("Oops!");
                                        alert.setContentText("Unfortunately, this is not the answer to your security question. Please, try again.");
                                        alert.showAndWait();
                                    }

                                }
                            }

                        });

                        tr.setDisable(true);
                        FadeTransition fadeTransition = new FadeTransition();
                        fadeTransition.setDuration(Duration.millis(1000));
                        fadeTransition.setNode(sq);
                        fadeTransition.setFromValue(0);
                        fadeTransition.setToValue(1);
                        fadeTransition.play();
                        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {

                                root.getChildren().add(ans);
                                FadeTransition fd = new FadeTransition();
                                fd.setDuration(Duration.millis(1000));
                                fd.setNode(ans);
                                fd.setFromValue(0);
                                fd.setToValue(1);
                                fd.play();

                            }
                        });

                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Email address not found");
                        alert.setHeaderText("Oops!");
                        alert.setContentText("It seems that email address which you entered is not valid. Please try again with the different email address.");
                        alert.showAndWait();
                    }
                }
            }
        });

        root.getChildren().addAll(iv, ma, tr);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Password recovery");
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }

}
