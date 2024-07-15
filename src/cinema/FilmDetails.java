package cinema;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class FilmDetails {

    private Film film;
    private List<Food> foodList = new LinkedList<>();
    private String username, fullname;

    public FilmDetails(Film film, List<Food> foodList, String username, String fullname) {
        this.film = film;
        this.foodList = foodList;
        this.username = username;
        this.fullname = fullname;
        Group root = new Group();
        StackPane root0 = new StackPane(root);
        //root0.getChildren().add(root);
        Stage primaryStage = new Stage();
        Scene scene = new Scene(root0, 500, 500);

        Image image1 = new Image(getClass().getResource("Images/curtains.png").toString());
        ImageView iv = new ImageView(image1);
        iv.setLayoutX(0);
        iv.setLayoutY(0);
        iv.setFitWidth(500);
        iv.setFitHeight(500);

        Image image2 = new Image(getClass().getResource("Images/goldstar.png").toString());
        ImageView i2 = new ImageView(image2);
        i2.setLayoutX(315);
        i2.setLayoutY(150);
        i2.setFitWidth(30);
        i2.setFitHeight(30);
        i2.setPreserveRatio(true);

        Rectangle rec = new Rectangle(290, 35, Color.ORANGE);
        rec.setLayoutX(102);
        rec.setLayoutY(75);
        rec.setStrokeWidth(5);
        rec.setArcWidth(5);
        rec.setArcHeight(5);
        rec.setStroke(Color.DARKGOLDENROD);

        Text title = new Text(this.film.getIme());
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        title.setLayoutX(112);
        title.setLayoutY(100);
        //title.setTextAlignment(TextAlignment.CENTER);

        Line line1 = new Line(100, 185, 394, 185);
        line1.setStrokeWidth(1);
        line1.setStroke(Color.GRAY);
        Line line2 = new Line(100, 235, 394, 235);
        line2.setStrokeWidth(1);
        line2.setStroke(Color.GRAY);
        Line line3 = new Line(100, 285, 394, 285);
        line3.setStrokeWidth(1);
        line3.setStroke(Color.GRAY);
        Line line4 = new Line(100, 335, 394, 335);
        line4.setStrokeWidth(1);
        line4.setStroke(Color.GRAY);
        Line line5 = new Line(100, 385, 394, 385);
        line5.setStrokeWidth(1);
        line5.setStroke(Color.GRAY);

        Label rating = new Label("IMDB Rating:");
        rating.setTextFill(Color.CRIMSON);
        rating.setLayoutX(105);
        rating.setLayoutY(155);
        rating.setFont(Font.font(null, FontWeight.BOLD, 20));

        Label md = new Label("Movie duration:");
        md.setTextFill(Color.CRIMSON);
        md.setLayoutX(105);
        md.setLayoutY(205);
        md.setFont(Font.font(null, FontWeight.BOLD, 20));

        Label mp = new Label("Movie projection:");
        mp.setTextFill(Color.CRIMSON);
        mp.setLayoutX(105);
        mp.setLayoutY(255);
        mp.setFont(Font.font(null, FontWeight.BOLD, 20));

        Label sd = new Label("Short description:");
        sd.setTextFill(Color.CRIMSON);
        sd.setLayoutX(105);
        sd.setLayoutY(305);
        sd.setFont(Font.font(null, FontWeight.BOLD, 20));

        Label wt = new Label("Watch a trailer:");
        wt.setTextFill(Color.CRIMSON);
        wt.setLayoutX(105);
        wt.setLayoutY(355);
        wt.setFont(Font.font(null, FontWeight.BOLD, 20));

        Label ratingIMDB = new Label(this.film.getIMDBrating());
        ratingIMDB.setTextFill(Color.CRIMSON);
        ratingIMDB.setLayoutX(280);
        ratingIMDB.setLayoutY(155);
        ratingIMDB.setFont(Font.font(null, FontWeight.BOLD, 20));

        Label movieDuration = new Label();
        String duration = this.film.getDuzina();
        movieDuration.setText(duration.substring(0, 2) + "h " + duration.substring(3) + "m");
        movieDuration.setTextFill(Color.CRIMSON);
        movieDuration.setLayoutX(280);
        movieDuration.setLayoutY(205);
        movieDuration.setFont(Font.font(null, FontWeight.BOLD, 20));

        Button movieProjections = new Button("Click here", GlyphsDude.createIcon(FontAwesomeIcons.CALENDAR, "20px"));
        movieProjections.setLayoutX(280);
        movieProjections.setLayoutY(245);
        movieProjections.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

        movieProjections.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            movieProjections.setStyle("-fx-font-size: 1.35em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-text-fill: white");

        });
        movieProjections.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            movieProjections.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");
        });

        movieProjections.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Projection p = new Projection(primaryStage, this.film, this.foodList, this.username, this.fullname);

        });

        Button shortDescription = new Button("Click here", GlyphsDude.createIcon(FontAwesomeIcons.PENCIL_SQUARE, "20px"));
        shortDescription.setLayoutX(280);
        shortDescription.setLayoutY(295);
        shortDescription.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

        shortDescription.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            shortDescription.setStyle("-fx-font-size: 1.35em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-text-fill: white");

        });
        shortDescription.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            shortDescription.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");
        });

        shortDescription.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            JOptionPane.showMessageDialog(null, this.film.getKratakOpis(), this.film.getIme(), JOptionPane.INFORMATION_MESSAGE, null);

        });

        Button watchTrailer = new Button("Click here", GlyphsDude.createIcon(FontAwesomeIcons.FILM, "20px"));
        watchTrailer.setLayoutX(280);
        watchTrailer.setLayoutY(345);
        watchTrailer.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

        watchTrailer.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            watchTrailer.setStyle("-fx-font-size: 1.35em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-text-fill: white");

        });
        watchTrailer.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            watchTrailer.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");
        });

        watchTrailer.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            NewStage ns = new NewStage(this.film.getTrejler());
        });

        Button booking = new Button("Book now!", GlyphsDude.createIcon(FontAwesomeIcons.BOOK, "20px"));
        booking.setLayoutX(162);
        booking.setLayoutY(435);
        booking.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-text-fill: #000000");

        booking.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            booking.setStyle("-fx-font-size: 2em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-text-fill: white");

        });
        booking.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            booking.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-text-fill: #000000");
        });

        booking.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            /*Tickets t = new Tickets();
            Group root2 = t.getRoot();
            
            root0.getChildren().add(root2);
            double width = root0.getWidth();
            KeyFrame start = new KeyFrame(Duration.ZERO,
                    new KeyValue(root2.translateXProperty(), width),
                    new KeyValue(root.translateXProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.millis(500),
                    new KeyValue(root2.translateXProperty(), 0),
                    new KeyValue(root.translateXProperty(), -width));
            Timeline slide = new Timeline(start, end);
            slide.setOnFinished(e -> root.getChildren().remove(root));
            slide.play();*/
            Projection p = new Projection(primaryStage, this.film, this.foodList, this.username, this.fullname);

        });

        root.getChildren().addAll(iv, rec, title, line1, line2, line3, line4, line5, rating, md, mp, sd, wt);
        root.getChildren().addAll(ratingIMDB, i2, movieDuration, movieProjections, shortDescription, watchTrailer, booking);

        primaryStage.setTitle(this.film.getIme());
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }

}
