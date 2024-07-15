package cinema;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class NewStage {

    Boolean play = true;
    VBox vbox;
    HBox hbox;
    double bandWidth;

    NewStage(String s) {

        Stage primaryStage = new Stage();
        Group root = new Group();

        Media media = new Media(getClass().getResource(s).toString());
        final MediaPlayer player = new MediaPlayer(media);
        MediaView view = new MediaView(player);
        //System.out.println(view.getWidth() +"\n"+media.getHeight());

        /*player.setOnReady(new Runnable() {    
                @Override
                public void run() {
                    // Add Pane to scene
                    Scene scene = new Scene(root, media.getWidth(), media.getHeight());
                    primaryStage.setScene(scene);
                    primaryStage.show();  
                }
            });*/
        view.setFitWidth(media.getWidth());
        view.setFitHeight(media.getHeight());

        final Slider slider = new Slider();

        final int bands = player.getAudioSpectrumNumBands();
        final Rectangle[] rects = new Rectangle[bands];
        bandWidth = view.getFitWidth() / rects.length;
        view.setPreserveRatio(true);
        vbox = new VBox();
        hbox = new HBox(5);
        for (int i = 0; i < rects.length; i++) {
            rects[i] = new Rectangle();
            rects[i].setFill(Color.GREENYELLOW);
            hbox.getChildren().add(rects[i]);
        }

        vbox.getChildren().add(slider);
        vbox.getChildren().add(hbox);
        vbox.setTranslateY(view.getFitHeight() - 100);

        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {

                view.prefWidth((double) newSceneWidth);
                view.maxWidth((double) newSceneWidth);
                view.minWidth((double) newSceneWidth);
                view.setFitWidth((double) newSceneWidth);
                slider.setPrefWidth(view.getFitWidth());
                slider.setMaxWidth(view.getFitWidth());
                slider.setMinWidth(view.getFitWidth());

                bandWidth = view.getFitWidth() / rects.length;

                slider.setPrefWidth(view.getFitWidth());
                hbox.setMinWidth(view.getFitWidth());
                hbox.setMaxWidth(view.getFitWidth());
                for (Rectangle r : rects) {
                    r.setWidth(bandWidth);
                    r.setHeight(2);
                }
                vbox.setPrefWidth(view.getFitWidth());
                vbox.setMaxWidth(view.getFitWidth());
            }
        });

        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                view.setFitHeight((double) newSceneHeight);
                view.prefHeight((double) newSceneHeight);
                view.maxHeight((double) newSceneHeight);
                view.minHeight((double) newSceneHeight);

                vbox.setTranslateY(view.getFitHeight() - 100);
            }
        });

        final Timeline slideIn = new Timeline();
        final Timeline slideOut = new Timeline();
        root.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                slideIn.play();
            }
        });
        root.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                slideOut.play();

            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Choose the option");
                alert.setContentText("Do you want to stop watching this trailer?");
                ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(okButton, cancelButton);
                alert.showAndWait().ifPresent(type -> {

                    if (type.getText().toString().equals("Yes")) {
                        player.stop();

                    } else {
                        event.consume();
                        primaryStage.show();

                    }

                });

            }
        });

        Button b1 = new Button("Pause", GlyphsDude.createIcon(FontAwesomeIcons.PLAY, "20px"));
        b1.setLayoutX(0);
        b1.setLayoutY(0);
        b1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (play == true) {
                    player.pause();
                    b1.setText("Resume");
                    b1.setGraphic(GlyphsDude.createIcon(FontAwesomeIcons.PAUSE, "20px"));
                    play = false;
                } else {
                    player.play();
                    b1.setText("Pause");
                    b1.setGraphic(GlyphsDude.createIcon(FontAwesomeIcons.PLAY, "20px"));
                    play = true;
                }
            }
        });
        b1.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-background-color: DARKCYAN; -fx-text-fill: #000000");

        root.getChildren().add(view);
        root.getChildren().add(vbox);
        root.getChildren().addAll(b1);

        player.play();
        player.setOnReady(new Runnable() {

            public void run() {

                Scene scene = new Scene(root, media.getWidth(), media.getHeight());
                primaryStage.setTitle("Playing video!");
                primaryStage.setScene(scene);
                double w = view.getFitWidth();
                double h = view.getFitHeight();
                hbox.setMinWidth(w);
                hbox.setMaxWidth(w);
                bandWidth = w / rects.length;
                for (Rectangle r : rects) {
                    r.setWidth(bandWidth);
                    r.setHeight(2);
                }

                slider.setMin(0.0);
                slider.setValue(0.0);
                slider.setMax(player.getTotalDuration().toSeconds());

                slideIn.getKeyFrames().addAll(
                        new KeyFrame(new Duration(0), new KeyValue(vbox.translateYProperty(), h), new KeyValue(vbox.opacityProperty(), 0.0)),
                        new KeyFrame(new Duration(300), new KeyValue(vbox.translateYProperty(), h - 100), new KeyValue(vbox.opacityProperty(), 0.9))
                );

                slideOut.getKeyFrames().addAll(
                        new KeyFrame(new Duration(0), new KeyValue(vbox.translateYProperty(), h - 100), new KeyValue(vbox.opacityProperty(), 0.9)),
                        new KeyFrame(new Duration(300), new KeyValue(vbox.translateYProperty(), h), new KeyValue(vbox.opacityProperty(), 0.0))
                );

            }

        });

        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration current) {
                slider.setValue(current.toSeconds());
            }
        });

        slider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                player.seek(Duration.seconds(slider.getValue()));
            }

        });

        player.setAudioSpectrumListener((double v, double vl, float[] mags, float[] floats1) -> {
            for (int i = 0; i < rects.length; i++) {
                double h = mags[i] + 60;
                if (h > 2) {
                    rects[i].setHeight(h);
                }
            }
        });

        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();

    }
}
