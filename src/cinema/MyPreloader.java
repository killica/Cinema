package cinema;

import javafx.application.Preloader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyPreloader extends Preloader {

    private Stage preloaderStage;
    private Scene scene;
    private Label label = new Label();
    private ProgressBar pb = new ProgressBar(0);

    public MyPreloader() {

    }

    @Override
    public void init() throws Exception {
        label.setTextFill(Color.WHITE);
        label.setLayoutX(260);
        label.setLayoutY(375);
        label.setFont(Font.font(null, FontWeight.BOLD, 30));
        /*pb.setLayoutX(100);
        pb.setLayoutY(150);
        pb.setMinSize(400, 50);
        pb.setMaxSize(400, 50);*/
        Image image1 = new Image(getClass().getResource("Images/Loading.gif").toString());
        ImageView iv = new ImageView(image1);
        iv.setLayoutX(0);
        iv.setLayoutY(0);
        iv.setFitHeight(450);
        iv.setFitWidth(600);
        Group root = new Group();
        root.getChildren().addAll(iv, label);

        scene = new Scene(root, 600, 450);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.preloaderStage = primaryStage;
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();

    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info) {
        if (info instanceof Preloader.ProgressNotification) {
            label.setText(((Preloader.ProgressNotification) info).getProgress() + "%");
            pb.setProgress(((Preloader.ProgressNotification) info).getProgress() / 100);

        }
    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {
        Preloader.StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_START:
                preloaderStage.hide();
                break;
        }
    }

}
