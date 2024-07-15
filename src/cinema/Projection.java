package cinema;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Projection {

    private Film film;
    private List<ReadFromTermin> terminList = new LinkedList<>();
    private int n = 0;
    private DropShadow ds = new DropShadow();
    private boolean ind = false, ind1 = false;
    private List<Food> foodList = new LinkedList<>();
    private String username, fullname;

    public void fillRoot(Stage stage4, Group root, LocalDate date, Stage primaryStage) {
        for (int i = 0; i < terminList.size(); i++) {
            if (terminList.get(i).getDatum().equals(date.toString())) {
                ReadFromTermin pom = terminList.get(i);
                Film pomFilm = this.film;
                Group root0 = new Group();
                n++;
                int hstart, mstart, hfin, mfin;
                hstart = Integer.parseInt(terminList.get(i).getPocetak().substring(0, 2));
                mstart = Integer.parseInt(terminList.get(i).getPocetak().substring(3));
                hfin = Integer.parseInt(terminList.get(i).getKraj().substring(0, 2));
                mfin = Integer.parseInt(terminList.get(i).getKraj().substring(3));
                Label termin = new Label(terminList.get(i).getPocetak() + "\n    -\n" + terminList.get(i).getKraj());
                termin.setTextFill(Color.BLACK);
                termin.setLayoutX(55 + 50 * hstart + 5 * mstart / 6);
                termin.setLayoutY(170 + 150 * (terminList.get(i).getSala() - 1));
                termin.setFont(Font.font(null, FontWeight.BOLD, 25));
                Label purchase = new Label("Purchase!");
                purchase.setTextFill(Color.CRIMSON);
                purchase.setLayoutX(42 + 50 * hstart + 5 * mstart / 6);
                purchase.setLayoutY(210 + 150 * (terminList.get(i).getSala() - 1));
                purchase.setFont(Font.font(null, FontWeight.BOLD, 25));
                purchase.setRotate(-30);

                if (hstart > hfin) {
                    hfin += 24;
                }
                Rectangle rec = new Rectangle(40 + 50 * hstart + 5 * mstart / 6, 160 + 150 * (terminList.get(i).getSala() - 1), 50 * (hfin - hstart) + 5 * (mfin - mstart) / 6, 130);
                rec.setFill(Color.ORANGE);
                rec.setStrokeWidth(5);
                rec.setArcWidth(5);
                rec.setArcHeight(5);
                rec.setStroke(Color.DARKGOLDENROD);
                rec.setEffect(ds);
                root0.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(0.2);
                    rec.setEffect(colorAdjust);
                    termin.toBack();
                    purchase.toFront();
                    purchase.setOpacity(1);

                });
                root0.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
                    rec.setEffect(ds);
                    purchase.toBack();
                    purchase.setOpacity(0);
                    termin.toFront();
                });
                root0.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
                    Tickets t = new Tickets(stage4, pom, pomFilm, primaryStage, this.foodList, this.username, this.fullname);
                });

                root0.getChildren().addAll(purchase, termin, rec);
                root.getChildren().addAll(root0);
                termin.toFront();
                purchase.toBack();
                purchase.setOpacity(0);
            } else if (terminList.get(i).getDatum().equals(date.minusDays(1).toString())) {
                Group root0 = new Group();
                int hstart, mstart, hfin, mfin;
                hstart = Integer.parseInt(terminList.get(i).getPocetak().substring(0, 2));
                mstart = Integer.parseInt(terminList.get(i).getPocetak().substring(3));
                hfin = Integer.parseInt(terminList.get(i).getKraj().substring(0, 2));
                mfin = Integer.parseInt(terminList.get(i).getKraj().substring(3));

                if (hstart > hfin) {
                    hstart = 0;
                    mstart = 0;
                    Rectangle rec = new Rectangle(40 + 50 * hstart + 5 * mstart / 6, 160 + 150 * (terminList.get(i).getSala() - 1), 50 * (hfin - hstart) + 5 * (mfin - mstart) / 6, 130);
                    rec.setFill(Color.ORANGE);
                    rec.setStrokeWidth(5);
                    rec.setArcWidth(5);
                    rec.setArcHeight(5);
                    rec.setStroke(Color.DARKGOLDENROD);
                    rec.setEffect(ds);
                    Label termin = new Label(terminList.get(i).getPocetak() + "\n    -\n" + terminList.get(i).getKraj());
                    termin.setTextFill(Color.BLACK);
                    termin.setLayoutX(55 + 50 * hstart + 5 * mstart / 6);
                    termin.setLayoutY(170 + 150 * (terminList.get(i).getSala() - 1));
                    termin.setFont(Font.font(null, FontWeight.BOLD, 25));
                    root0.getChildren().addAll(rec, termin);
                    root.getChildren().add(root0);
                }

            }

        }
    }

    public Projection(Stage stage4, Film film, List<Food> foodList, String username, String fullname) {
        this.foodList = foodList;
        this.film = film;
        this.username = username;
        this.fullname = fullname;
        terminList = film.getTerminList();
        Group root = new Group();
        Group root1 = new Group();

        Stage primaryStage = new Stage();
        Label sd = new Label("Select projection date: ");
        sd.setTextFill(Color.CRIMSON);
        sd.setLayoutX(350);
        sd.setLayoutY(15);
        sd.setFont(Font.font(null, FontWeight.BOLD, 25));
        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setColor(Color.GRAY);
        DatePicker dp1 = new DatePicker();
        dp1.setLayoutX(650);
        dp1.setLayoutY(15);
        dp1.setMinSize(220, 40);
        dp1.setValue(LocalDate.now());
        dp1.valueProperty().addListener((ov, oldValue, newValue) -> {
            root1.getChildren().clear();
            fillRoot(stage4, root1, dp1.getValue(), primaryStage);

        });

        Button next = new Button("Next day  ", GlyphsDude.createIcon(FontAwesomeIcons.LONG_ARROW_RIGHT, "20px"));
        next.setLayoutX(1100);
        next.setLayoutY(40);
        next.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-text-fill: #000000");
        next.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            next.setStyle("-fx-font-size: 1.5em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-text-fill: white");

        });
        next.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            next.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-text-fill: #000000");

        });

        next.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setDuration(Duration.millis(500));
            fadeTransition.setNode(root1);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.play();
            fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(root1);
                    root1.getChildren().clear();
                    fillRoot(stage4, root1, dp1.getValue().plusDays(1), primaryStage);
                    root1.setOpacity(0);
                    FadeTransition ft = new FadeTransition();
                    ft.setDuration(Duration.millis(500));
                    ft.setNode(root1);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                    root.getChildren().add(root1);
                    dp1.setValue(dp1.getValue().plusDays(1));
                }
            });

        });

        Button previous = new Button("Previous day  ", GlyphsDude.createIcon(FontAwesomeIcons.LONG_ARROW_LEFT, "20px"));
        previous.setLayoutX(20);
        previous.setLayoutY(40);
        previous.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-text-fill: #000000");
        previous.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            previous.setStyle("-fx-font-size: 1.5em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-text-fill: white");

        });
        previous.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            previous.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-text-fill: #000000");

        });

        previous.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setDuration(Duration.millis(500));
            fadeTransition.setNode(root1);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.play();
            fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(root1);
                    root1.getChildren().clear();
                    fillRoot(stage4, root1, dp1.getValue().minusDays(1), primaryStage);
                    root1.setOpacity(0);
                    FadeTransition ft = new FadeTransition();
                    ft.setDuration(Duration.millis(500));
                    ft.setNode(root1);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                    root.getChildren().add(root1);
                    dp1.setValue(dp1.getValue().minusDays(1));
                }
            });

        });
        Line line1 = new Line(0, 150, 1240, 150);
        line1.setStrokeWidth(1);
        line1.setStroke(Color.LIGHTBLUE);
        Line line2 = new Line(0, 300, 1240, 300);
        line2.setStrokeWidth(1);
        line2.setStroke(Color.LIGHTBLUE);
        Line line3 = new Line(0, 450, 1240, 450);
        line3.setStrokeWidth(1);
        line3.setStroke(Color.LIGHTBLUE);

        Rectangle scr1 = new Rectangle(0, 150, 40, 150);
        Rectangle scr2 = new Rectangle(0, 300, 40, 150);
        Rectangle scr3 = new Rectangle(0, 450, 40, 150);
        scr1.setFill(Color.LIGHTGREEN);
        scr2.setFill(Color.GREEN);
        scr3.setFill(Color.DARKGREEN);
        Label one = new Label("1");
        one.setTextFill(Color.WHITE);
        one.setLayoutX(10);
        one.setLayoutY(205);
        one.setFont(Font.font(null, FontWeight.BOLD, 30));
        Label two = new Label("2");
        two.setTextFill(Color.WHITE);
        two.setLayoutX(10);
        two.setLayoutY(355);
        two.setFont(Font.font(null, FontWeight.BOLD, 30));
        Label three = new Label("3");
        three.setTextFill(Color.WHITE);
        three.setLayoutX(10);
        three.setLayoutY(505);
        three.setFont(Font.font(null, FontWeight.BOLD, 30));
        for (int i = 0; i < 24; i++) {
            Line l = new Line(90 + i * 50, 150, 90 + i * 50, 600);
            l.setStrokeWidth(1);
            l.setStroke(Color.LIGHTBLUE);
            Label times = new Label("" + i);
            times.setTextFill(Color.CRIMSON);
            times.setLayoutX((i < 10) ? (55 + i * 50) : (50 + i * 50));
            times.setLayoutY(110);
            times.setFont(Font.font(null, FontWeight.BOLD, 25));
            root.getChildren().addAll(l, times);

        }
        root.getChildren().addAll(root1);
        fillRoot(stage4, root1, dp1.getValue(), primaryStage);

        root.getChildren().addAll(sd, dp1, line1, line2, line3, scr1, scr2, scr3, one, two, three, next, previous);
        Scene scene = new Scene(root, 1240, 600);
        primaryStage.setTitle("Movie projections");
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }

}
