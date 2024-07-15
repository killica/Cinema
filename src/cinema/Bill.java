/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Lazar
 */
public class Bill {

    private List<Food> food = new LinkedList<>();
    private List<Integer> occ = new LinkedList<>();
    private Film film;
    private int child, adult;
    private double total;
    private String bill;

    public Bill(Cart cart, Group koren) {
        this.food = cart.getFood();
        this.occ = cart.getOcc();
        this.film = cart.getFilm();
        this.child = cart.getChild();
        this.adult = cart.getAdult();
        this.total = cart.getTotal();

        GaussianBlur blur = new GaussianBlur(75);
        koren.setEffect(blur);

        Group root = new Group();
        final Stage primaryStage = new Stage();
        final Scene scene = new Scene(root, 560, 600);
        //primaryStage.setY(-1500);

        scene.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            primaryStage.close();
            koren.setEffect(null);

        });

        Label pocetak = new Label(this.film.getIme() + "\n----------------------------------------------------------\n     ITEMS                     PRICE\n\n");
        pocetak.setFont(new Font("Lucida Console", 24));
        pocetak.setLayoutX(0);
        pocetak.setLayoutY(20);
        if (child != 0) {
            pocetak.setText(pocetak.getText() + ("Child tickets x" + child + ":\n"));
        }
        if (adult != 0) {
            pocetak.setText(pocetak.getText() + ("Adult tickets x" + adult + ":\n\n"));
        }
        if (adult == 0) {
            pocetak.setText(pocetak.getText() + "\n");
        }

        Label kraj = new Label();
        kraj.setFont(new Font("Lucida Console", 24));
        kraj.setLayoutX(450);
        kraj.setLayoutY(122);
        if (child != 0) {
            kraj.setText(kraj.getText() + (String.format("%.2f", child * film.getCenaKarteD()) + "€\n"));
        }
        if (adult != 0) {
            kraj.setText(kraj.getText() + (String.format("%.2f", adult * film.getCenaKarteO()) + "€\n\n"));
        }
        if (adult == 0) {
            kraj.setText(kraj.getText() + "\n");
        }
        total = (child * film.getCenaKarteD() + adult * film.getCenaKarteO());

        for (int i = 0; i < food.size(); i++) {
            pocetak.setText(pocetak.getText() + (food.get(i).getIme() + " x" + occ.get(i) + ":\n"));
            kraj.setText(kraj.getText() + String.format("%.2f", occ.get(i) * food.get(i).getCena()) + "€\n");
            total += occ.get(i) * food.get(i).getCena();
        }
        pocetak.setText(pocetak.getText() + "\n----------------------------------------------------------\n     TOTAL:\n");
        kraj.setText(kraj.getText() + "\n\n" + String.format("%.2f", total) + "€\n");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd '\n' HH:mm:ss ");
        Date date = new Date(System.currentTimeMillis());

        Label vreme = new Label(formatter.format(date));
        vreme.setFont(new Font("Lucida Console", 24));
        vreme.setLayoutX(415);
        vreme.setLayoutY(535);

        root.getChildren().addAll(pocetak, kraj, vreme);

        primaryStage.setTitle("My bill");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }

    public Bill(Cart cart, Group koren, int ind) {
        this.food = cart.getFood();
        this.occ = cart.getOcc();
        this.film = cart.getFilm();
        this.child = cart.getChild();
        this.adult = cart.getAdult();
        this.total = cart.getTotal();

        GaussianBlur blur = new GaussianBlur(75);
        koren.setEffect(blur);

        Group root = new Group();
        final Stage primaryStage = new Stage();
        final Scene scene = new Scene(root, 560, 600);
        //primaryStage.setY(-1500);

        Label con = new Label("Is this your final order?");
        con.setFont(new Font("Lucida Console", 32));
        con.setLayoutX(40);
        con.setLayoutY(10);

        Label pocetak = new Label(this.film.getIme() + "\n----------------------------------------------------------\n     ITEMS                     PRICE\n\n");
        pocetak.setFont(new Font("Lucida Console", 24));
        pocetak.setLayoutX(0);
        pocetak.setLayoutY(80);
        if (child != 0) {
            pocetak.setText(pocetak.getText() + ("Child tickets x" + child + ":\n"));
        }
        if (adult != 0) {
            pocetak.setText(pocetak.getText() + ("Adult tickets x" + adult + ":\n\n"));
        }
        if (adult == 0) {
            pocetak.setText(pocetak.getText() + "\n");
        }

        Label kraj = new Label();
        kraj.setFont(new Font("Lucida Console", 24));
        kraj.setLayoutX(450);
        kraj.setLayoutY(182);
        if (child != 0) {
            kraj.setText(kraj.getText() + (String.format("%.2f", child * film.getCenaKarteD()) + "€\n"));
        }
        if (adult != 0) {
            kraj.setText(kraj.getText() + (String.format("%.2f", adult * film.getCenaKarteO()) + "€\n\n"));
        }
        if (adult == 0) {
            kraj.setText(kraj.getText() + "\n");
        }
        total = (child * film.getCenaKarteD() + adult * film.getCenaKarteO());

        for (int i = 0; i < food.size(); i++) {
            pocetak.setText(pocetak.getText() + (food.get(i).getIme() + " x" + occ.get(i) + ":\n"));
            kraj.setText(kraj.getText() + String.format("%.2f", occ.get(i) * food.get(i).getCena()) + "€\n");
            total += occ.get(i) * food.get(i).getCena();
        }
        pocetak.setText(pocetak.getText() + "\n----------------------------------------------------------\n     TOTAL:\n");
        kraj.setText(kraj.getText() + "\n\n" + String.format("%.2f", total) + "€\n");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd '\n' HH:mm:ss ");
        Date date = new Date(System.currentTimeMillis());

        Label vreme = new Label(formatter.format(date));
        vreme.setFont(new Font("Lucida Console", 24));
        vreme.setLayoutX(415);
        vreme.setLayoutY(535);

        Button yes = new Button("Yes!", GlyphsDude.createIcon(FontAwesomeIcons.CHECK, "20px"));
        yes.setLayoutX(80);
        yes.setLayoutY(475);
        yes.setStyle("-fx-font-size: 2.2em; -fx-font-weight: bold; -fx-text-fill: #000000");
        yes.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            yes.setStyle("-fx-font-size: 2.2em; -fx-background-color: darkgreen; -fx-font-weight: bold; -fx-text-fill: white");

        });
        yes.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            yes.setStyle("-fx-font-size: 2.2em; -fx-font-weight: bold; -fx-text-fill: #000000");

        });

        yes.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            primaryStage.close();
            koren.setEffect(null);

            TranslateTransition tt = new TranslateTransition();
            tt.setDuration(Duration.millis(500));
            tt.setNode(koren);
            tt.setToX(-2450);
            tt.play();

        });

        Button no = new Button("No...", GlyphsDude.createIcon(FontAwesomeIcons.CLOSE, "20px"));
        no.setLayoutX(230);
        no.setLayoutY(475);
        no.setStyle("-fx-font-size: 2.2em; -fx-font-weight: bold; -fx-text-fill: #000000");
        no.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            no.setStyle("-fx-font-size: 2.2em; -fx-background-color: firebrick; -fx-font-weight: bold; -fx-text-fill: white");

        });
        no.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            no.setStyle("-fx-font-size: 2.2em; -fx-font-weight: bold; -fx-text-fill: #000000");

        });

        no.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            cart.flushFood();
            primaryStage.close();
            koren.setEffect(null);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cart reseted");
            alert.setHeaderText("Your cart has been reseted!");
            //alert.setContentText();
            alert.showAndWait();

        });

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(375);
        cancel.setLayoutY(475);
        cancel.setStyle("-fx-font-size: 2.2em; -fx-font-weight: bold; -fx-text-fill: #000000");
        cancel.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            cancel.setStyle("-fx-font-size: 2.2em; -fx-background-color: cornflowerblue; -fx-font-weight: bold; -fx-text-fill: white");

        });
        cancel.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            cancel.setStyle("-fx-font-size: 2.2em; -fx-font-weight: bold; -fx-text-fill: #000000");

        });

        cancel.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            primaryStage.close();
            koren.setEffect(null);
        });

        root.getChildren().addAll(con, pocetak, kraj, vreme, yes, no, cancel);

        primaryStage.setTitle("My bill");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }

}
