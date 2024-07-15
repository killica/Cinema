package cinema;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailUtil {

    public JavaMailUtil(String username, String password) throws Exception {

        Group root = new Group();
        Stage primaryStage = new Stage();
        Rectangle r = new Rectangle(0, 0, 500, 50);
        r.setFill(Color.CRIMSON);
        Label newMessage = new Label("New Message");
        newMessage.setTextFill(Color.WHITE);
        newMessage.setLayoutX(10);
        newMessage.setLayoutY(10);
        newMessage.setFont(Font.font(null, FontWeight.BOLD, 20));
        Label to = new Label("To:");
        to.setTextFill(Color.BLACK);
        to.setLayoutX(10);
        to.setLayoutY(60);
        to.setFont(Font.font(null, FontPosture.ITALIC, 20));
        Label subject = new Label("Subject:");
        subject.setTextFill(Color.BLACK);
        subject.setLayoutX(10);
        subject.setLayoutY(110);
        subject.setFont(Font.font(null, FontPosture.ITALIC, 20));
        Line l1 = new Line();
        l1.setStartX(0);
        l1.setStartY(100);
        l1.setEndX(500);
        l1.setEndY(100);
        l1.setStrokeWidth(2);
        l1.setStroke(Color.BLACK);
        Line l2 = new Line();
        l2.setStartX(0);
        l2.setStartY(150);
        l2.setEndX(500);
        l2.setEndY(150);
        l2.setStrokeWidth(2);
        l2.setStroke(Color.BLACK);

        DropShadow ds = new DropShadow();
        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setColor(Color.GRAY);
        Rectangle rec = new Rectangle(230, 35, Color.ORANGE);
        rec.setLayoutX(50);
        rec.setLayoutY(55);
        rec.setStrokeWidth(5);
        rec.setArcWidth(5);
        rec.setArcHeight(5);
        rec.setStroke(Color.DARKGOLDENROD);
        rec.setEffect(ds);

        Text mymail = new Text("lakica03@gmail.com");
        mymail.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        mymail.setLayoutX(60);
        mymail.setLayoutY(80);

        TextArea textArea = new TextArea();
        textArea.setMaxSize(500, 350);
        textArea.setMinSize(500, 350);
        textArea.setWrapText(true);
        textArea.setLayoutX(0);
        textArea.setLayoutY(150);
        textArea.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        TextField a = new TextField();
        a.setLayoutX(85);
        a.setLayoutY(105);
        a.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));
        a.setMinSize(400, 40);
        a.setMaxSize(400, 40);
        a.setStyle("-fx-background-radius: 15; -fx-text-fill: black; -fx-font-size: 1.5em; -fx-font-weight: bold; -fx-focus-color: transparent; -fx-text-box-border: transparent;");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("samplesurnamesamplename@gmail.com", "fahretaivancic");
            }

        });
        session.getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");
        session.getProperties().put("mail.smtp.starttls.enable", "true");

        Button New = new Button("Send");
        New.setLayoutX(400);
        New.setLayoutY(450);
        New.setMinSize(75, 30);
        New.setStyle("-fx-font-size: 1.5em; -fx-background-color: orange; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-text-fill: white; -fx-border-color:darkgoldenrod;");
        New.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            New.setStyle("-fx-font-size: 1.5em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-text-fill: white");

        });
        New.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            New.setStyle("-fx-font-size: 1.5em; -fx-background-color: orange; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-text-fill: white; -fx-border-color:darkgoldenrod;");

        });

        New.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            if (!a.getText().equals("")) {
                try {

                    Message message = prepareMessage(session, username, "lakica03@gmail.com", a.getText(), textArea.getText());
                    Transport.send(message);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Info");
                    alert.setHeaderText(null);
                    alert.setContentText("Message has been successfully sent.");
                    alert.showAndWait();
                    primaryStage.close();
                } catch (MessagingException ex) {
                    System.out.println(ex);
                }
            }

        });

        root.getChildren().addAll(r, newMessage, l1, to, l2, subject, rec, mymail, textArea, a, New);
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setTitle("Gmail");
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }

    private Message prepareMessage(Session session, String username, String recipient, String subject, String text) throws MessagingException {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("samplesurnamesamplename@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText("Message sent from user: " + username + "\n" + text);
            return message;
        } catch (AddressException ex) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
