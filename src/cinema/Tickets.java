package cinema;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Tickets {

    private Film film;
    private ReadFromTermin termin = new ReadFromTermin();
    private DropShadow ds = new DropShadow();
    private final ObservableList numOfTickets = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    private double sum = 0;
    private String A = "0", B = "0";
    private List<Food> foodList = new LinkedList<>();
    private List<Food> snacksList = new LinkedList<>();
    private List<Food> drinksList = new LinkedList<>();
    private List<Food> popcornsList = new LinkedList<>();
    private List<Food> combosList = new LinkedList<>();
    private List<Food> dessertsList = new LinkedList<>();
    private List<String> reservationList = new LinkedList<>();
    private List<String> selectedSeats = new LinkedList<>();
    private Cart cart = new Cart();
    private ScrollPane sp = new ScrollPane();
    private int k = 0, n = 0;
    private ComboBox<String> dc, dc2;
    private Label select = new Label("You have to select " + n + ((n > 1) ? " seats: " : " seat: "));
    private Label limit = new Label("You can't select more than " + n + ((n > 1) ? " seats!" : " seat!"));
    private String username, fullname;

    public void bloom(Node n) {
        Bloom bloom = new Bloom();
        bloom.setThreshold(.0);
        n.setEffect(bloom);
    }

    public Tickets(Stage stage4, ReadFromTermin termin, Film film, Stage stage, List<Food> foodList, String username, String fullname) {

        this.termin = termin;
        this.film = film;
        this.foodList = foodList;
        this.username = username;
        this.fullname = fullname;
        Group root = new Group();
        Scene scene = new Scene(root, 1240, 700);
        Group root0 = new Group();
        Group root1 = new Group();
        Group root2 = new Group();
        Group root3 = new Group();
        root1.setLayoutX(1200);
        root3.setLayoutX(2450);
        root.getChildren().addAll(root0, root1, root3);
        Stage primaryStage = new Stage();

        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setColor(Color.GRAY);
        Rectangle rec0 = new Rectangle(20, 20, 1160, 660);
        rec0.setFill(Color.WHITE);
        rec0.setArcWidth(20.0);
        rec0.setArcHeight(10.0);
        rec0.setEffect(ds);
        Rectangle rec = new Rectangle(800, 20, 380, 660);
        rec.setFill(Color.WHEAT);
        rec.setArcWidth(20.0);
        rec.setArcHeight(10.0);
        Rectangle rec1 = new Rectangle(20, 20, 1160, 660);
        rec1.setFill(Color.WHITE);
        rec1.setArcWidth(20.0);
        rec1.setArcHeight(10.0);
        rec1.setEffect(ds);

        Image i = film.getPoster().getImage();
        ImageView iv = new ImageView(i);
        iv.setFitHeight(250);
        iv.setFitWidth(169);
        iv.setPreserveRatio(true);
        iv.setLayoutX(905);
        iv.setLayoutY(50);
        Image i1 = new Image(getClass().getResource("Images/nachos.jpg").toString());
        ImageView iv1 = new ImageView(i1);
        iv1.setFitHeight(300);
        iv1.setFitWidth(300);
        iv1.setLayoutX(450);
        iv1.setLayoutY(375);
        iv1.setPreserveRatio(true);

        Image i2 = new Image(getClass().getResource("Images/popcorn1.jpg").toString());
        ImageView iv2 = new ImageView(i2);
        iv2.setFitHeight(275);
        iv2.setFitWidth(275);
        iv2.setLayoutX(50);
        iv2.setLayoutY(50);
        iv2.setPreserveRatio(true);

        Image i3 = new Image(getClass().getResource("Images/cocacola1.jpg").toString());
        ImageView iv3 = new ImageView(i3);
        iv3.setFitHeight(275);
        iv3.setFitWidth(275);
        iv3.setLayoutX(875);
        iv3.setLayoutY(50);
        iv3.setPreserveRatio(true);

        Label sd = new Label("  Date:             " + this.termin.getDatum());
        sd.setTextFill(Color.DARKBLUE);
        sd.setLayoutX(810);
        sd.setLayoutY(335);
        sd.setFont(Font.font(null, FontWeight.BOLD, 25));
        Line line1 = new Line(810, 375, 1170, 375);
        line1.setStrokeWidth(2);
        line1.setStroke(Color.GRAY);

        Label sd1 = new Label("  Starts:                " + this.termin.getPocetak());
        sd1.setTextFill(Color.DARKBLUE);
        sd1.setLayoutX(810);
        sd1.setLayoutY(400);
        sd1.setFont(Font.font(null, FontWeight.BOLD, 25));
        Line line2 = new Line(810, 440, 1170, 440);
        line2.setStrokeWidth(2);
        line2.setStroke(Color.GRAY);

        Label sd2 = new Label("  Ends:                  " + this.termin.getKraj());
        sd2.setTextFill(Color.DARKBLUE);
        sd2.setLayoutX(810);
        sd2.setLayoutY(465);
        sd2.setFont(Font.font(null, FontWeight.BOLD, 25));
        Line line3 = new Line(810, 505, 1170, 505);
        line3.setStrokeWidth(2);
        line3.setStroke(Color.GRAY);

        Label sd3 = new Label("  Screen:                  " + this.termin.getSala());
        sd3.setTextFill(Color.DARKBLUE);
        sd3.setLayoutX(810);
        sd3.setLayoutY(530);
        sd3.setFont(Font.font(null, FontWeight.BOLD, 25));
        Line line4 = new Line(810, 570, 1170, 570);
        line4.setStrokeWidth(2);
        line4.setStroke(Color.GRAY);

        Label st = new Label("Choose your ticket:");
        st.setTextFill(Color.DARKBLUE);
        st.setLayoutX(285);
        st.setLayoutY(50);
        st.setFont(Font.font(null, FontWeight.BOLD, 30));
        st.setUnderline(true);

        Label ca = new Label("Child\n\n\nAdult");
        ca.setTextFill(Color.DARKBLUE);
        ca.setLayoutX(75);
        ca.setLayoutY(215);
        ca.setFont(Font.font(null, FontWeight.BOLD, 25));

        Label qc = new Label("                       Quantity                  Cost");
        qc.setTextFill(Color.DARKBLUE);
        qc.setLayoutX(125);
        qc.setLayoutY(125);
        qc.setFont(Font.font(null, FontWeight.BOLD, 25));

        Label cost = new Label(String.format("%.2f", this.film.getCenaKarteD()) + "€\n\n\n" + String.format("%.2f", this.film.getCenaKarteO()) + "€");
        cost.setTextFill(Color.CORNFLOWERBLUE);
        cost.setLayoutX(510);
        cost.setLayoutY(215);
        cost.setFont(Font.font(null, FontWeight.BOLD, 25));

        Label yb = new Label("Total: 0.00€");
        yb.setTextFill(Color.GREEN);
        yb.setLayoutX(300);
        yb.setLayoutY(450);
        yb.setUnderline(true);
        yb.setFont(Font.font(null, FontWeight.BOLD, 30));

        dc2 = new ComboBox<>(numOfTickets);
        dc2.setValue("0");
        dc2.setLayoutX(290);
        dc2.setLayoutY(210);
        dc2.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");

        dc2.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                A = newValue;
                int a = Integer.parseInt(A);
                int b = Integer.parseInt(B);
                n = a + b;
                select.setText("You have to select " + n + ((n > 1) ? " seats: " : " seat: "));
                limit.setText("You can't select more than " + n + ((n > 1) ? " seats!" : " seat!"));
                yb.setText("Total: " + String.format("%.2f", (a * film.getCenaKarteD() + b * film.getCenaKarteO())) + "€");

            }
        });

        dc = new ComboBox<>(numOfTickets);
        dc.setValue("0");
        dc.setLayoutX(290);
        dc.setLayoutY(325);
        dc.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");

        dc.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                B = newValue;
                int a = Integer.parseInt(A);
                int b = Integer.parseInt(B);
                n = a + b;
                select.setText("You have to select " + n + ((n > 1) ? " seats: " : " seat: "));
                limit.setText("You can't select more than " + n + ((n > 1) ? " seats!" : " seat!"));
                yb.setText("Total: " + String.format("%.2f", (a * film.getCenaKarteD() + b * film.getCenaKarteO())) + "€");

            }
        });

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(950);
        cancel.setLayoutY(615);
        //spt.setMinSize(35,50);
        //spt.setMaxSize(35, 35);
        cancel.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-background-color: red; -fx-text-fill: white");
        cancel.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            cancel.setStyle("-fx-font-size: 2em; -fx-background-color: royalblue; -fx-font-weight: bold;  -fx-text-fill: white");

        });
        cancel.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            cancel.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-background-color: red; -fx-text-fill: white");

        });

        cancel.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            primaryStage.close();
        });

        Button reset = new Button("Reset");
        reset.setLayoutX(575);
        reset.setLayoutY(615);
        reset.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-background-color: green; -fx-text-fill: white");
        reset.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            reset.setStyle("-fx-font-size: 2em; -fx-background-color: darkgreen; -fx-font-weight: bold;  -fx-text-fill: white");

        });
        reset.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            reset.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-background-color: green; -fx-text-fill: white");

        });

        reset.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            dc.setValue("0");
            dc2.setValue("0");
            yb.setText("Total: 0.00€");

        });

        Button next = new Button("Next");
        next.setLayoutX(150);
        next.setLayoutY(615);
        next.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-background-color: royalblue; -fx-text-fill: white");
        next.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            next.setStyle("-fx-font-size: 2em; -fx-background-color: darkblue; -fx-font-weight: bold;  -fx-text-fill: white");

        });
        next.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            next.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-background-color: royalblue; -fx-text-fill: white");

        });

        next.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            String a = dc.getValue();
            String b = dc2.getValue();
            if (a.equals("0") && b.equals("0")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You have to select at least one number which is not 0.");
                alert.showAndWait();
            } else if (Integer.parseInt(a) + Integer.parseInt(b) > 36 - reservationList.size()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You can select more than " + (36 - reservationList.size()) + " seats.");
                alert.showAndWait();
                System.out.println((Integer.parseInt(a) + Integer.parseInt(b)));
            } else {
                cart.setFilm(this.film);
                cart.setChild(Integer.parseInt(b));
                cart.setAdult(Integer.parseInt(a));

                TranslateTransition tt = new TranslateTransition();
                tt.setDuration(Duration.millis(500));
                tt.setNode(root);
                tt.setToX(-1200);
                tt.play();
            }

        });

        Label food = new Label("Do you want to buy some food?");
        food.setTextFill(Color.DARKBLUE);
        food.setLayoutX(350);
        food.setLayoutY(225);
        food.setFont(Font.font(null, FontWeight.BOLD, 35));

        Button yes = new Button("Yes, please!");
        yes.setLayoutX(225);
        yes.setLayoutY(350);
        yes.setStyle("-fx-font-size: 2.7em; -fx-font-weight: bold; -fx-background-color: green; -fx-text-fill: white");
        yes.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            yes.setStyle("-fx-font-size: 2.7em; -fx-background-color: darkgreen; -fx-font-weight: bold;  -fx-text-fill: white");

        });
        yes.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            yes.setStyle("-fx-font-size: 2.7em; -fx-font-weight: bold; -fx-background-color: green; -fx-text-fill: white");

        });

        yes.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setDuration(Duration.millis(500));
            fadeTransition.setNode(root1);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(root1);
                    root.getChildren().add(root2);
                    FadeTransition fadeTransition = new FadeTransition();
                    fadeTransition.setDuration(Duration.millis(500));
                    fadeTransition.setNode(root2);
                    fadeTransition.setFromValue(0);
                    fadeTransition.setToValue(1);
                    fadeTransition.play();
                }
            });

            fadeTransition.play();

        });

        Button no = new Button("No, thanks.");
        no.setLayoutX(800);
        no.setLayoutY(350);
        no.setStyle("-fx-font-size: 2.7em; -fx-font-weight: bold; -fx-background-color: red; -fx-text-fill: white");
        no.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            no.setStyle("-fx-font-size: 2.7em; -fx-background-color: firebrick; -fx-font-weight: bold;  -fx-text-fill: white");

        });
        no.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            no.setStyle("-fx-font-size: 2.7em; -fx-font-weight: bold; -fx-background-color: red; -fx-text-fill: white");

        });

        no.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            TranslateTransition tt = new TranslateTransition();
            tt.setDuration(Duration.millis(500));
            tt.setNode(root);
            tt.setToX(-2450);
            tt.play();
        });

        Label gb = new Label("Go back...");
        gb.setTextFill(Color.GREEN);
        gb.setLayoutX(50);
        gb.setLayoutY(650);
        gb.setUnderline(true);
        gb.setFont(Font.font(null, FontWeight.BOLD, 15));
        gb.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            gb.setTextFill(Color.DARKGREEN);
            scene.setCursor(Cursor.HAND);

        });
        gb.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            gb.setTextFill(Color.GREEN);
            scene.setCursor(Cursor.DEFAULT);
        });

        gb.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            TranslateTransition tt = new TranslateTransition();
            tt.setDuration(Duration.millis(500));
            tt.setNode(root);
            tt.setToX(0);
            tt.play();

        });

        Group snacksRoot, drinksRoot, popcornsRoot, combosRoot, dessertsRoot;
        snacksRoot = new Group();
        drinksRoot = new Group();
        popcornsRoot = new Group();
        combosRoot = new Group();
        dessertsRoot = new Group();
        snacksRoot.setOpacity(0);
        drinksRoot.setOpacity(0);
        popcornsRoot.setOpacity(0);
        combosRoot.setOpacity(0);
        dessertsRoot.setOpacity(0);
        

        for (int j = 0; j < foodList.size(); j++) {
            Food f = foodList.get(j);
            if (f.getCategory().equals("Snacks")) {
                snacksList.add(f);
            } else if (f.getCategory().equals("Drinks")) {
                drinksList.add(f);
            } else if (f.getCategory().equals("Popcorns")) {
                popcornsList.add(f);
            } else if (f.getCategory().equals("Combos")) {
                combosList.add(f);
            } else if (f.getCategory().equals("Desserts")) {
                dessertsList.add(f);
            }

        }

        Label SNACKS = new Label("Snacks");
        SNACKS.setTextFill(Color.WHITE);
        SNACKS.setLayoutX(55);
        SNACKS.setLayoutY(30);
        SNACKS.setFont(Font.font(null, FontWeight.BOLD, 30));

        Rectangle snacks = new Rectangle(0, 0, 250, 100);
        snacks.setFill(Color.ORANGE);
        snacks.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            snacks.setFill(Color.CRIMSON);
            bloom(SNACKS);
        });
        snacks.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            snacks.setFill(Color.ORANGE);
            SNACKS.setEffect(null);
        });

        snacks.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            root2.getChildren().remove(snacksRoot);
            root2.getChildren().remove(drinksRoot);
            root2.getChildren().remove(popcornsRoot);
            root2.getChildren().remove(combosRoot);
            root2.getChildren().remove(dessertsRoot);
            root2.getChildren().remove(sp);
            sp.setContent(null);
            menu(root2, snacksRoot, snacksList);

        });
        SNACKS.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            snacks.setFill(Color.CRIMSON);
            bloom(SNACKS);
        });
        SNACKS.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            snacks.setFill(Color.ORANGE);
            SNACKS.setEffect(null);
        });

        SNACKS.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            //Isti kod kao unutar UserSettings kad se klikne
            root2.getChildren().remove(snacksRoot);
            root2.getChildren().remove(drinksRoot);
            root2.getChildren().remove(popcornsRoot);
            root2.getChildren().remove(combosRoot);
            root2.getChildren().remove(dessertsRoot);
            root2.getChildren().remove(sp);
            sp.setContent(null);
            menu(root2, snacksRoot, snacksList);

        });

        Label DRINKS = new Label("Drinks");
        DRINKS.setTextFill(Color.WHITE);
        DRINKS.setLayoutX(55);
        DRINKS.setLayoutY(130);
        DRINKS.setFont(Font.font(null, FontWeight.BOLD, 30));

        Rectangle drinks = new Rectangle(0, 100, 250, 100);
        drinks.setFill(Color.ORANGE);
        drinks.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            drinks.setFill(Color.CRIMSON);
            bloom(DRINKS);
        });
        drinks.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            drinks.setFill(Color.ORANGE);
            DRINKS.setEffect(null);
        });

        drinks.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            root2.getChildren().remove(snacksRoot);
            root2.getChildren().remove(drinksRoot);
            root2.getChildren().remove(popcornsRoot);
            root2.getChildren().remove(combosRoot);
            root2.getChildren().remove(dessertsRoot);
            root2.getChildren().remove(sp);
            sp.setContent(null);
            menu(root2, snacksRoot, drinksList);

        });
        DRINKS.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            drinks.setFill(Color.CRIMSON);
            bloom(DRINKS);
        });
        DRINKS.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            drinks.setFill(Color.ORANGE);
            DRINKS.setEffect(null);
        });

        DRINKS.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            root2.getChildren().remove(snacksRoot);
            root2.getChildren().remove(drinksRoot);
            root2.getChildren().remove(popcornsRoot);
            root2.getChildren().remove(combosRoot);
            root2.getChildren().remove(dessertsRoot);
            root2.getChildren().remove(sp);
            sp.setContent(null);
            menu(root2, snacksRoot, drinksList);

        });

        Label POPCORNS = new Label("Popcorn");
        POPCORNS.setTextFill(Color.WHITE);
        POPCORNS.setLayoutX(55);
        POPCORNS.setLayoutY(230);
        POPCORNS.setFont(Font.font(null, FontWeight.BOLD, 30));

        Rectangle popcorns = new Rectangle(0, 200, 250, 100);
        popcorns.setFill(Color.ORANGE);
        popcorns.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            popcorns.setFill(Color.CRIMSON);
            bloom(POPCORNS);
        });
        popcorns.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            popcorns.setFill(Color.ORANGE);
            POPCORNS.setEffect(null);
        });

        popcorns.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            root2.getChildren().remove(snacksRoot);
            root2.getChildren().remove(drinksRoot);
            root2.getChildren().remove(popcornsRoot);
            root2.getChildren().remove(combosRoot);
            root2.getChildren().remove(dessertsRoot);
            root2.getChildren().remove(sp);
            sp.setContent(null);
            menu(root2, snacksRoot, popcornsList);

        });
        POPCORNS.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            popcorns.setFill(Color.CRIMSON);
            bloom(POPCORNS);
        });
        POPCORNS.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            popcorns.setFill(Color.ORANGE);
            POPCORNS.setEffect(null);
        });

        POPCORNS.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            root2.getChildren().remove(snacksRoot);
            root2.getChildren().remove(drinksRoot);
            root2.getChildren().remove(popcornsRoot);
            root2.getChildren().remove(combosRoot);
            root2.getChildren().remove(dessertsRoot);
            root2.getChildren().remove(sp);
            sp.setContent(null);
            menu(root2, snacksRoot, popcornsList);

        });

        Label COMBOS = new Label("Combos");
        COMBOS.setTextFill(Color.WHITE);
        COMBOS.setLayoutX(55);
        COMBOS.setLayoutY(330);
        COMBOS.setFont(Font.font(null, FontWeight.BOLD, 30));

        Rectangle combos = new Rectangle(0, 300, 250, 100);
        combos.setFill(Color.ORANGE);
        combos.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            combos.setFill(Color.CRIMSON);
            bloom(COMBOS);
        });
        combos.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            combos.setFill(Color.ORANGE);
            COMBOS.setEffect(null);
        });

        combos.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            root2.getChildren().remove(snacksRoot);
            root2.getChildren().remove(drinksRoot);
            root2.getChildren().remove(popcornsRoot);
            root2.getChildren().remove(combosRoot);
            root2.getChildren().remove(dessertsRoot);
            root2.getChildren().remove(sp);
            sp.setContent(null);
            menu(root2, snacksRoot, combosList);

        });
        COMBOS.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            combos.setFill(Color.CRIMSON);
            bloom(COMBOS);
        });
        COMBOS.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            combos.setFill(Color.ORANGE);
            COMBOS.setEffect(null);
        });

        COMBOS.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            root2.getChildren().remove(snacksRoot);
            root2.getChildren().remove(drinksRoot);
            root2.getChildren().remove(popcornsRoot);
            root2.getChildren().remove(combosRoot);
            root2.getChildren().remove(dessertsRoot);
            root2.getChildren().remove(sp);
            sp.setContent(null);
            menu(root2, snacksRoot, combosList);

        });

        Label DESSERTS = new Label("Desserts");
        DESSERTS.setTextFill(Color.WHITE);
        DESSERTS.setLayoutX(55);
        DESSERTS.setLayoutY(430);
        DESSERTS.setFont(Font.font(null, FontWeight.BOLD, 30));

        Rectangle desserts = new Rectangle(0, 400, 250, 100);
        desserts.setFill(Color.ORANGE);
        desserts.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            desserts.setFill(Color.CRIMSON);
            bloom(DESSERTS);
        });
        desserts.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            desserts.setFill(Color.ORANGE);
            DESSERTS.setEffect(null);
        });

        desserts.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            root2.getChildren().remove(snacksRoot);
            root2.getChildren().remove(drinksRoot);
            root2.getChildren().remove(popcornsRoot);
            root2.getChildren().remove(combosRoot);
            root2.getChildren().remove(dessertsRoot);
            root2.getChildren().remove(sp);
            sp.setContent(null);
            menu(root2, snacksRoot, dessertsList);

        });
        DESSERTS.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            desserts.setFill(Color.CRIMSON);
            bloom(DESSERTS);
        });
        DESSERTS.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            desserts.setFill(Color.ORANGE);
            DESSERTS.setEffect(null);
        });

        DESSERTS.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            root2.getChildren().remove(snacksRoot);
            root2.getChildren().remove(drinksRoot);
            root2.getChildren().remove(popcornsRoot);
            root2.getChildren().remove(combosRoot);
            root2.getChildren().remove(dessertsRoot);
            root2.getChildren().remove(sp);
            sp.setContent(null);
            menu(root2, snacksRoot, dessertsList);

        });

        Label BILL = new Label("My current bill");
        BILL.setTextFill(Color.BLACK);
        BILL.setLayoutX(25);
        BILL.setLayoutY(530);
        BILL.setFont(Font.font(null, FontWeight.BOLD, 30));

        Rectangle bill = new Rectangle(0, 500, 250, 100);
        bill.setFill(Color.WHEAT);
        bill.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            bill.setFill(Color.TAN);
            bloom(BILL);
        });
        bill.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            bill.setFill(Color.WHEAT);
            bill.setEffect(null);
        });

        bill.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Bill b = new Bill(this.cart, root);

        });
        BILL.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            bill.setFill(Color.TAN);
            bloom(BILL);
        });
        BILL.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            bill.setFill(Color.WHEAT);
            BILL.setEffect(null);
        });

        BILL.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Bill b = new Bill(this.cart, root);

        });

        Label CHECK = new Label("Check out");
        CHECK.setTextFill(Color.BLACK);
        CHECK.setLayoutX(40);
        CHECK.setLayoutY(630);
        CHECK.setFont(Font.font(null, FontWeight.BOLD, 30));

        Rectangle check = new Rectangle(0, 600, 250, 100);
        check.setFill(Color.WHEAT);
        check.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            check.setFill(Color.TAN);
            bloom(CHECK);
        });
        check.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            check.setFill(Color.WHEAT);
            check.setEffect(null);
        });

        check.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Bill b = new Bill(this.cart, root, 1);

        });
        CHECK.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            check.setFill(Color.TAN);
            bloom(CHECK);
        });
        CHECK.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            check.setFill(Color.WHEAT);
            CHECK.setEffect(null);
        });

        CHECK.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Bill b = new Bill(this.cart, root, 1);
        });
        Line l1 = new Line(0, 100, 250, 100);
        l1.setStrokeWidth(1);
        l1.setStroke(Color.BLACK);
        Line l2 = new Line(0, 200, 250, 200);
        l2.setStrokeWidth(1);
        l2.setStroke(Color.BLACK);
        Line l3 = new Line(0, 300, 250, 300);
        l3.setStrokeWidth(1);
        l3.setStroke(Color.BLACK);
        Line l4 = new Line(0, 400, 250, 400);
        l4.setStrokeWidth(1);
        l4.setStroke(Color.BLACK);
        Line l5 = new Line(0, 500, 250, 500);
        l5.setStrokeWidth(1);
        l5.setStroke(Color.BLACK);
        Line l6 = new Line(0, 600, 250, 600);
        l6.setStrokeWidth(1);
        l6.setStroke(Color.BLACK);

        final Tooltip tooltip = new Tooltip();
        tooltip.setText(
                "\nYour password must be\n"
                + "at least 8 characters in length\n"
        );
        Tooltip.install(bill, tooltip);
        Tooltip.install(BILL, tooltip);

        root0.getChildren().addAll(rec0, rec, iv, sd, line1, sd1, line2, sd2, line3, sd3, line4, st, ca, qc, cost, dc2, dc, yb, cancel, reset, next);
        root1.getChildren().addAll(rec1, iv1, food, yes, no, iv2, iv3, gb);
        root2.getChildren().addAll(snacks, SNACKS, drinks, DRINKS, popcorns, POPCORNS, combos, COMBOS, desserts, DESSERTS, bill, BILL, check, CHECK, l1, l2, l3, l4, l5, l6);
        root2.setOpacity(0);
        root2.setLayoutX(1200);
        fillRoot3(stage4, stage, primaryStage, root3, root);

        Label limit = new Label("There are " + (36 - reservationList.size()) + " available seats.");
        limit.setTextFill(Color.CRIMSON);
        limit.setLayoutX(225);
        limit.setLayoutY(520);
        limit.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 22));
        limit.setWrapText(true);
        root0.getChildren().add(limit);

        primaryStage.setTitle("Tickets booking");
        primaryStage.setScene(scene);
        //primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }

    public void menu(Group root2, Group target, List<Food> food) {
        target.setOpacity(0);
        target.getChildren().clear();
        root2.getChildren().add(target);
        //Collections.shuffle(food);   
        sp.setContent(target);
        //sp.setPannable(true);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //sp.setPrefViewportHeight(1000);
        //gp.setLayoutX(300);
        //gp.setLayoutY(50);
        sp.setMaxSize(985, 700);
        sp.setMinSize(985, 700);
        sp.setLayoutX(250);
        sp.setLayoutY(0);
        sp.setStyle("-fx-background:wheat; -fx-body-color: orange; -fx-background-color: orange;");
        root2.getChildren().add(sp);
        Label pom = new Label("");
        pom.setLayoutX(250);
        pom.setLayoutY(0);
        target.getChildren().add(pom);

        for (int k = 0; k < food.size(); k++) {
            Food f = food.get(k);
            Rectangle rec4 = new Rectangle(285 + (k % 3) * 310, 20 + 420 * (k / 3), 290, 400);
            rec4.setFill(Color.WHITE);
            rec4.setArcWidth(20.0);
            rec4.setArcHeight(10.0);
            rec4.setEffect(ds);
            Image image = f.getSlika().getImage();
            ImageView iv = new ImageView(image);
            iv.setFitWidth(240);
            iv.setFitHeight(240);
            iv.setLayoutX(310 + (k % 3) * 310);
            iv.setLayoutY(45 + 420 * (k / 3));
            iv.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
                iv.setStyle("-fx-scale-x: 1.15; -fx-scale-y: 1.15; -fx-scale-z: 1.15;");
            });
            iv.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
                iv.setStyle(null);
            });

            Label l = new Label(f.getIme());
            l.setWrapText(true);
            l.setTextFill(Color.CRIMSON);
            l.setLayoutY(295 + 420 * (k / 3));
            l.setLayoutX(303 + (k % 3) * 310);
            l.setFont(Font.font(null, FontWeight.BOLD, 25));

            Label cena = new Label(f.getCena() + "€");
            cena.setWrapText(true);
            cena.setTextFill(Color.CRIMSON);
            cena.setLayoutY(320 + 420 * (k / 3));
            cena.setLayoutX(390 + (k % 3) * 310);
            cena.setFont(Font.font(null, FontWeight.BOLD, 32));

            Spinner s = new Spinner();
            SpinnerValueFactory<Integer> gradeValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
            s.setValueFactory(gradeValueFactory);
            s.setEditable(true);
            s.setLayoutX(490 + (k % 3) * 310);
            s.setLayoutY(375 + 420 * (k / 3));
            s.setMinWidth(80);
            s.setMaxWidth(80);
            s.setStyle("-fx-text-fill: orange; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18; -fx-body-color: orange; -fx-background-color: orange;");

            Button add = new Button("Add to cart", GlyphsDude.createIcon(FontAwesomeIcons.SHOPPING_CART, "30px"));
            add.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18; -fx-background-color: orange;");
            add.setMaxSize(175, 35);
            add.setMinSize(175, 35);
            add.setLayoutX(303 + (k % 3) * 310);
            add.setLayoutY(375 + 420 * (k / 3));
            add.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
                add.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18; -fx-background-color: darkorange;");
            });
            add.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
                add.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18; -fx-background-color: orange;");
            });

            Group rootPom = new Group();
            rootPom.getChildren().addAll(rec4, iv, l, cena, s, add);

            add.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
                if (Integer.parseInt(s.getValue().toString()) != 0) {
                    for (int i = 0; i < Integer.parseInt(s.getValue().toString()); i++) {
                        cart.addFood(f);
                    }
                    AudioClip audio = new AudioClip(getClass().getResource("Audio/cash.mp3").toString());
                    audio.play();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Food cart");
                    alert.setHeaderText("Added to the cart!");
                    alert.setContentText(f.getIme() + " x" + s.getValue() + " has been added to the cart.");
                    alert.showAndWait();
                }

            });

            target.getChildren().add(rootPom);
        }
        FadeTransition fd3 = new FadeTransition();
        fd3.setDuration(Duration.millis(1000));
        fd3.setNode(target);
        fd3.setFromValue(0);
        fd3.setToValue(1);
        fd3.play();

    }

    public void fillRoot3(Stage stage4, Stage stage0, Stage stage, Group root, Group root0) {
        Image red = new Image(getClass().getResource("Images/crvena.jpg").toString());
        Image black = new Image(getClass().getResource("Images/crna.jpg").toString());
        Image green = new Image(getClass().getResource("Images/green.jpg").toString());

        Rectangle rec = new Rectangle(0, 0, 1250, 700);
        rec.setFill(Color.WHEAT);
        root.getChildren().add(rec);

        Rectangle rec1 = new Rectangle(275, 0, 700, 50);
        rec1.setFill(Color.RED);

        Label name = new Label(this.film.getIme());
        name.setTextFill(Color.WHITE);
        name.setLayoutX(300);
        name.setLayoutY(5);
        name.setFont(Font.font(null, FontWeight.BOLD, 32));
        bloom(name);

        Button next = new Button("Finish!", GlyphsDude.createIcon(FontAwesomeIcons.ARROW_RIGHT, "30px"));
        next.setLayoutX(1050);
        next.setLayoutY(0);
        //spt.setMinSize(35,50);
        //spt.setMaxSize(35, 35);
        next.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-background-color: red; -fx-text-fill: white");
        next.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            next.setStyle("-fx-font-size: 2em; -fx-background-color: firebrick; -fx-font-weight: bold;  -fx-text-fill: white");

        });
        next.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            next.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-background-color: red; -fx-text-fill: white");

        });

        next.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            for (int i = 0; i < selectedSeats.size(); i++) {
                Connection con = SqliteConnection.Connector();
                PreparedStatement ps = null;
                try {
                    String sql = "INSERT INTO Rezervacija(Username,Sifra,Screen,Pocetak,Kraj,Datum,Aktivan) VALUES(?,?,?,?,?,?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, this.username);
                    ps.setString(2, selectedSeats.get(i));
                    ps.setInt(3, this.termin.getSala());
                    ps.setString(4, this.termin.getPocetak());
                    ps.setString(5, this.termin.getKraj());
                    ps.setString(6, this.termin.getDatum());
                    ps.setInt(7, 1);
                    ps.execute();
                    ps.close();
                    con.close();

                } catch (SQLException e) {
                    //java.sql.SQLException: [SQLITE_ERROR] SQL error or missing database (Connection is closed)    
                    System.out.println(e);
                }

            }

            Download dl = new Download(stage4, stage0, stage, this.cart, selectedSeats, termin.getSala());

            /*TranslateTransition tt = new TranslateTransition();
            tt.setDuration(Duration.millis(500));
            tt.setNode(root0);
            tt.setToX(-3700);
            tt.play();*/
        });

        limit = new Label("You can't select more than " + n + ((n > 1) ? " seats!" : " seat!"));
        limit.setTextFill(Color.RED);
        limit.setLayoutX(50);
        limit.setLayoutY(125);
        limit.setFont(Font.font(null, FontWeight.BOLD, 32));

        select.setTextFill(Color.DARKBLUE);
        select.setLayoutX(50);
        select.setLayoutY(75);
        select.setFont(Font.font(null, FontWeight.BOLD, 32));
        select.setStyle("-fx-highlight-fill: yellow;");
        select.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (selectedSeats.size() == n) {
                    root.getChildren().add(next);
                } else {
                    root.getChildren().remove(next);
                }

            }
        });

        root.getChildren().addAll(rec1, name, select);

        Label A = new Label("A");
        A.setTextFill(Color.BLACK);
        A.setLayoutX(20);
        A.setLayoutY(200);
        A.setFont(Font.font(null, FontWeight.BOLD, 32));
        A.setEffect(ds);

        Label B = new Label("B");
        B.setTextFill(Color.BLACK);
        B.setLayoutX(20);
        B.setLayoutY(320);
        B.setFont(Font.font(null, FontWeight.BOLD, 32));
        B.setEffect(ds);

        Label C = new Label("C");
        C.setTextFill(Color.BLACK);
        C.setLayoutX(20);
        C.setLayoutY(440);
        C.setFont(Font.font(null, FontWeight.BOLD, 32));
        C.setEffect(ds);

        Label D = new Label("D");
        D.setTextFill(Color.BLACK);
        D.setLayoutX(20);
        D.setLayoutY(560);
        D.setFont(Font.font(null, FontWeight.BOLD, 32));
        D.setEffect(ds);

        Label one = new Label("1");
        one.setTextFill(Color.BLACK);
        one.setLayoutX(105);
        one.setLayoutY(650);
        one.setFont(Font.font(null, FontWeight.BOLD, 32));
        one.setEffect(ds);

        Label two = new Label("2");
        two.setTextFill(Color.BLACK);
        two.setLayoutX(225);
        two.setLayoutY(650);
        two.setFont(Font.font(null, FontWeight.BOLD, 32));
        two.setEffect(ds);

        Label three = new Label("3");
        three.setTextFill(Color.BLACK);
        three.setLayoutX(345);
        three.setLayoutY(650);
        three.setFont(Font.font(null, FontWeight.BOLD, 32));
        three.setEffect(ds);

        Label four = new Label("4");
        four.setTextFill(Color.BLACK);
        four.setLayoutX(495);
        four.setLayoutY(650);
        four.setFont(Font.font(null, FontWeight.BOLD, 32));
        four.setEffect(ds);

        Label five = new Label("5");
        five.setTextFill(Color.BLACK);
        five.setLayoutX(615);
        five.setLayoutY(650);
        five.setFont(Font.font(null, FontWeight.BOLD, 32));
        five.setEffect(ds);

        Label six = new Label("6");
        six.setTextFill(Color.BLACK);
        six.setLayoutX(735);
        six.setLayoutY(650);
        six.setFont(Font.font(null, FontWeight.BOLD, 32));
        six.setEffect(ds);

        Label seven = new Label("7");
        seven.setTextFill(Color.BLACK);
        seven.setLayoutX(885);
        seven.setLayoutY(650);
        seven.setFont(Font.font(null, FontWeight.BOLD, 32));
        seven.setEffect(ds);

        Label eight = new Label("8");
        eight.setTextFill(Color.BLACK);
        eight.setLayoutX(1005);
        eight.setLayoutY(650);
        eight.setFont(Font.font(null, FontWeight.BOLD, 32));
        eight.setEffect(ds);

        Label nine = new Label("9");
        nine.setTextFill(Color.BLACK);
        nine.setLayoutX(1125);
        nine.setLayoutY(650);
        nine.setFont(Font.font(null, FontWeight.BOLD, 32));
        nine.setEffect(ds);

        root.getChildren().addAll(A, B, C, D, one, two, three, four, five, six, seven, eight, nine);

        Connection con = SqliteConnection.Connector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT Sifra, Aktivan FROM Rezervacija WHERE Pocetak = ? AND Kraj = ? AND Datum = ? AND Screen = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, this.termin.getPocetak());
            ps.setString(2, this.termin.getKraj());
            ps.setString(3, this.termin.getDatum());
            ps.setInt(4, this.termin.getSala());
            rs = ps.executeQuery();
            while (rs.next()) {
                String sifra = rs.getString("Sifra");
                int aktivan = rs.getInt("Aktivan");
                if (aktivan == 1) {
                    this.reservationList.add(sifra);
                }
            }
            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error while loading reservations!");
            //alert.setContentText("Try another username or create a new account.");
            alert.showAndWait();
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 9; j++) {
                String p = "";
                final String q;
                final ImageView iv;
                if (i == 0) {
                    p = "A";
                }
                if (i == 1) {
                    p = "B";
                }
                if (i == 2) {
                    p = "C";
                }
                if (i == 3) {
                    p = "D";
                }
                p += j;
                q = p;
                if (reservationList.contains(p)) {
                    iv = new ImageView(red);
                } else {
                    iv = new ImageView(black);
                    iv.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
                        iv.setStyle("-fx-scale-x: 1.15; -fx-scale-y: 1.15; -fx-scale-z: 1.15;");
                    });
                    iv.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
                        iv.setStyle(null);
                    });
                    iv.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
                        if (selectedSeats.size() < n && iv.getImage().equals(black)) {
                            String pom = "";
                            iv.setImage(green);
                            selectedSeats.add(q);
                            for (int o = 0; o < selectedSeats.size(); o++) {
                                pom = pom + selectedSeats.get(o) + ((o < n - 1) ? ", " : ".");
                            }
                            select.setText("You have to select " + n + ((n > 1) ? " seats: " : " seat: ") + pom);

                        } else if (selectedSeats.size() <= n && iv.getImage().equals(green)) {
                            root.getChildren().remove(limit);
                            String pom = "";
                            iv.setImage(black);
                            selectedSeats.remove(q);
                            for (int o = 0; o < selectedSeats.size(); o++) {
                                pom = pom + selectedSeats.get(o) + ((o < n - 1) ? ", " : ".");
                            }
                            select.setText("You have to select " + n + ((n > 1) ? " seats: " : " seat: ") + pom);
                        } else if (selectedSeats.size() == n && iv.getImage().equals(black)) {
                            root.getChildren().remove(limit);
                            root.getChildren().add(limit);
                        }

                    });
                }

                iv.setFitHeight(100);
                iv.setFitWidth(100);
                iv.setLayoutX(65 + 120 * (j - 1) + ((j - 1) / 3) * 30);
                iv.setLayoutY(175 + i * 120);
                iv.setEffect(ds);

                root.getChildren().add(iv);

            }
        }
    }

}
