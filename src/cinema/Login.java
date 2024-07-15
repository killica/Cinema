package cinema;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransitionBuilder;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransitionBuilder;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBuilder;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Login {

    private Animation animation;
    private LocalDate ld;
    private Button back;
    private Group root;
    private static byte[] image_user;
    private List<Film> filmList = new LinkedList<>();
    private List<Food> foodList = new LinkedList<>();
    private static boolean u = true;

    private final ObservableList<String> country = Stream.of(Locale.getISOCountries())
            .map(locales -> new Locale("", locales))
            .map(Locale::getDisplayCountry)
            .collect(Collectors.toCollection(FXCollections::observableArrayList));

    private final ObservableList accountType = FXCollections.observableArrayList(
            "Saving", "Current");
    private final ObservableList securityQuestion = FXCollections.observableArrayList(
            "What are the last 5 digits of your driver's license number?", "What is the name of your favorite childhood friend?", "In what city and country do you want to retire?", "What is the name of your grandmother's dog?");

    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    public Login() {

    }

    public Group getRoot() {
        return root;
    }

    public Login(Group root, Stage primaryStage, List<Film> filmList, List<Food> foodList) {
        this.filmList = filmList;
        this.foodList = foodList;
        Group root1 = new Group();
        this.root = root;
        Scene scene = new Scene(root, 1200, 725);
        Image image1 = new Image(getClass().getResource("Images/cinema1.jpg").toString());
        ImageView iv1 = new ImageView(image1);
        iv1.setFitHeight(725);
        iv1.setFitWidth(1200);
        iv1.setLayoutX(0);
        iv1.setLayoutY(0);
        Image image2 = new Image(getClass().getResource("Images/user.png").toString());
        ImageView iv2 = new ImageView(image2);
        iv2.setFitHeight(20);
        iv2.setFitWidth(20);
        iv2.setLayoutX(145);
        iv2.setLayoutY(240);
        Image image3 = new Image(getClass().getResource("Images/eye1.jpg").toString());
        ImageView iv3 = new ImageView(image3);
        iv3.setFitHeight(20);
        iv3.setFitWidth(20);
        iv3.setLayoutX(145);
        iv3.setLayoutY(305);
        ImageView iv4 = new ImageView(image2);
        iv4.setFitHeight(20);
        iv4.setFitWidth(20);
        Image image5 = new Image(getClass().getResource("Images/pencil.png").toString());
        ImageView iv5 = new ImageView(image5);
        iv5.setFitHeight(20);
        iv5.setFitWidth(20);

        Rectangle r = new javafx.scene.shape.Rectangle();
        r.setFill(Color.WHITE);
        r.setWidth(600);
        r.setHeight(200);
        r.setLayoutX(300);
        r.setLayoutY(250);

        Label w = new Label("Welcome!");
        w.setTextFill(Color.CRIMSON);
        w.setLayoutX(450);
        w.setLayoutY(80);
        w.setFont(Font.font("SansSerif", FontWeight.BOLD, FontPosture.ITALIC, 60));

        Label l = new Label("Login below to access your account");
        l.setTextFill(Color.CRIMSON);
        l.setLayoutX(125);
        l.setLayoutY(175);
        l.setFont(Font.font(null, FontWeight.BOLD, 20));

        Label l1 = new Label("Login as administrator");
        l1.setTextFill(Color.CRIMSON);
        l1.setLayoutX(875);
        l1.setLayoutY(175);
        l1.setFont(Font.font(null, FontWeight.BOLD, 20));

        Label an = new Label("Username                                                  ");
        an.setTextFill(Color.BLACK);
        an.setLayoutX(175);
        an.setLayoutY(235);
        an.setFont(Font.font(null, FontPosture.ITALIC, 19));

        Label pin = new Label("Password                                                  ");
        pin.setTextFill(Color.BLACK);
        pin.setLayoutX(175);
        pin.setLayoutY(300);
        pin.setFont(Font.font(null, FontPosture.ITALIC, 19));

        PasswordField pwBox = new PasswordField();
        pwBox.setLayoutX(175);
        pwBox.setLayoutY(300);
        pwBox.setDisable(true);
        pwBox.setFont(Font.font(null, FontWeight.BOLD, 15));

        pin.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent me) {
                pwBox.setDisable(false);
                pin.setTextFill(Color.CORNFLOWERBLUE);

                animation = SequentialTransitionBuilder.create()
                        .node(pin)
                        .children(
                                TranslateTransitionBuilder.create()
                                        .duration(Duration.seconds(0.1))
                                        .fromY(0)
                                        .toY(-30)
                                        .build(),
                                PauseTransitionBuilder.create()
                                        .duration(Duration.INDEFINITE)
                                        .build()
                        )
                        //.cycleCount(Timeline.INDEFINITE)           
                        .autoReverse(true)
                        .build();
                play();

            }

        });

        TextField b = new TextField();
        b.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

        });

        an.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent me) {
                b.setDisable(false);
                an.setTextFill(Color.CORNFLOWERBLUE);

                animation = SequentialTransitionBuilder.create()
                        .node(an)
                        .children(
                                TranslateTransitionBuilder.create()
                                        .duration(Duration.seconds(0.1))
                                        .fromY(0)
                                        .toY(-30)
                                        .build(),
                                PauseTransitionBuilder.create()
                                        .duration(Duration.INDEFINITE)
                                        .build()
                        )
                        //.cycleCount(Timeline.INDEFINITE)           
                        .autoReverse(true)
                        .build();
                play();

            }

        });
        b.setLayoutX(175);
        b.setLayoutY(235);
        b.setDisable(true);
        //b.setEffect(is);
        b.setFont(Font.font(null, FontWeight.BOLD, 15));

        Label fp = new Label("Forgot password?");
        fp.setTextFill(Color.DARKGRAY);
        fp.setLayoutX(250);
        fp.setLayoutY(350);
        fp.setUnderline(true);
        fp.setFont(Font.font(null, FontWeight.BOLD, 15));
        fp.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            fp.setTextFill(Color.BLACK);
            scene.setCursor(Cursor.HAND);

        });
        fp.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            fp.setTextFill(Color.DARKGRAY);
            scene.setCursor(Cursor.DEFAULT);
        });

        fp.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            ForgotPassword f = new ForgotPassword();

        });

        Button btnLogin = new Button("Login   ", GlyphsDude.createIcon(FontAwesomeIcons.USER, "20px"));
        btnLogin.setLayoutX(150);
        btnLogin.setLayoutY(400);
        btnLogin.setMinSize(250, 45);
        btnLogin.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: #000000");
        btnLogin.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            btnLogin.setStyle("-fx-font-size: 1.5em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: white");

        });
        btnLogin.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            btnLogin.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: #000000");

        });

        btnLogin.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Rotation(btnLogin);
            String s1 = b.getText();
            String s2 = pwBox.getText();

            if (s1.equals("") || s2.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("You have to complete every field! ");
                alert.showAndWait();
            } else {
                try {
                    ReadFromUser rfu = new ReadFromUser(s1, s2, primaryStage, 0, filmList, foodList);

                    //NEKA ANIMACIJA ZA NESTANAK SCENE
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        TextField a = new TextField();
        a.setLayoutX(860);
        a.setLayoutY(235);
        a.setPromptText("PIN code");
        a.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));
        a.setMinSize(250, 25);
        a.setStyle("-fx-background-radius: 15; -fx-text-fill: crimson;");

        TextField a1 = new TextField();
        a1.setLayoutX(860);
        a1.setLayoutY(300);
        a1.setPromptText("Username");
        a1.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));
        a1.setMinSize(250, 25);
        a1.setStyle("-fx-background-radius: 15; -fx-text-fill: crimson;");

        PasswordField a2 = new PasswordField();
        a2.setLayoutX(860);
        a2.setLayoutY(365);
        a2.setPromptText("Password");
        a2.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));
        a2.setMinSize(250, 25);
        a2.setStyle("-fx-background-radius: 15; -fx-text-fill: crimson;");

        Image image6 = new Image(getClass().getResource("Images/admin3.png").toString());
        ImageView iv6 = new ImageView(image6);
        iv6.setFitHeight(20);
        iv6.setFitWidth(20);

        Button admin = new Button("Login   ", GlyphsDude.createIcon(FontAwesomeIcons.USER, "20px"));
        admin.setLayoutX(860);
        admin.setLayoutY(430);
        admin.setMinSize(250, 45);
        admin.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: #000000");
        admin.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            admin.setStyle("-fx-font-size: 1.5em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: white");

        });
        admin.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            admin.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: #000000");

        });

        admin.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            Rotation(admin);
            String s1 = a.getText();
            String s2 = a1.getText();
            String s3 = a2.getText();

            if (s1.equals("") || s2.equals("") || s3.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("You have to complete every field! ");
                alert.showAndWait();
            } else if (!s1.equals("kq4FJauu")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong PIN code!");
                alert.showAndWait();
            } else {
                try {
                    ReadFromUser rfu = new ReadFromUser(s2, s3, primaryStage, 1, filmList, foodList);
                } catch (Exception ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        Button cna = new Button("Create New Account", GlyphsDude.createIcon(FontAwesomeIcons.PENCIL, "20px"));
        cna.setLayoutX(535);
        cna.setLayoutY(290);
        cna.setMinSize(250, 45);
        cna.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: #000000");
        cna.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            cna.setStyle("-fx-font-size: 1.5em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: white");

        });
        cna.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            cna.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: #000000");

        });

        cna.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            /*CreateNewAccount x = new CreateNewAccount(primaryStage);
                ScrollPane sp = new ScrollPane(x.getRoot());
                sp.setPannable(true);
                sp.setPrefViewportHeight(480);
                sp.setPrefViewportWidth(380);
                sp.setLayoutX(300);
                sp.setLayoutY(0);
                root.getChildren().addAll(sp);*/
            makeFadeOut(root, root1, primaryStage);
        });

        Stop[] stops1 = new Stop[]{
            new Stop(0, Color.PINK),
            new Stop(1, Color.FIREBRICK)
        };
        Stop[] stops2 = new Stop[]{
            new Stop(0, Color.FIREBRICK),
            new Stop(1, Color.PINK)
        };
        LinearGradient gradient1
                = new LinearGradient(0, 1, 1, 0, true, CycleMethod.NO_CYCLE, stops1);

        LinearGradient gradient2
                = new LinearGradient(0, 1, 1, 0, true, CycleMethod.NO_CYCLE, stops2);

        Line line1 = new Line(500, 150, 500, 275);
        line1.setStrokeWidth(3);
        line1.setStroke(gradient2);

        Line line2 = new Line(500, 350, 500, 475);
        line2.setStrokeWidth(3);
        line2.setStroke(gradient1);

        Line line3 = new Line(825, 150, 825, 275);
        line3.setStrokeWidth(3);
        line3.setStroke(gradient2);

        Line line4 = new Line(825, 350, 825, 475);
        line4.setStrokeWidth(3);
        line4.setStroke(gradient1);

        Label or = new Label("OR");
        or.setTextFill(Color.CRIMSON);
        or.setLayoutX(477);
        or.setLayoutY(290);
        or.setFont(Font.font(null, FontWeight.BOLD, 30));

        Label or1 = new Label("OR");
        or1.setTextFill(Color.CRIMSON);
        or1.setLayoutX(802);
        or1.setLayoutY(290);
        or1.setFont(Font.font(null, FontWeight.BOLD, 30));

        root1.getChildren().addAll(w, l, l1, an, pin, pwBox, b, fp, iv2, iv3, iv4, btnLogin, line1, line2, line3, line4, or, or1, cna, a, a1, a2, admin);
        root.getChildren().addAll(iv1, r, root1);

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void play() {
        animation.play();
    }

    public void stop() {
        animation.stop();
    }

    public double getSampleWidth() {
        return 150;
    }

    public double getSampleHeight() {
        return 150;
    }

    public Button getBack() {
        return back;
    }

    private void makeFadeOut(Group root, Group root1, Stage primaryStage) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(root1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadNextScene(root, root1, primaryStage);
            }
        });

        fadeTransition.play();
    }

    public void Rotation(Node b) {
        RotateTransition rotation = new RotateTransition();
        rotation.setDuration(Duration.millis(100));
        rotation.setFromAngle(0);
        rotation.setToAngle(15);
        RotateTransition rotation1 = new RotateTransition();
        rotation1.setDuration(Duration.millis(100));
        rotation1.setFromAngle(15);
        rotation1.setToAngle(-15);
        RotateTransition rotation2 = new RotateTransition();
        rotation2.setDuration(Duration.millis(100));
        rotation2.setFromAngle(-15);
        rotation2.setToAngle(0);
        rotation.setNode(b);
        rotation1.setNode(b);
        rotation2.setNode(b);
        rotation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rotation1.play();
                rotation1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        rotation2.play();
                    }
                });

            }
        });
        rotation.play();

    }

    public void makeFadeInTransition(Group root) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(root);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private static void insert(String FullName, String UserName, String Password, String Gender, String City, String DateOfBirth, 
            String Country, String Email, String SecurityQuestion, String Answer, byte[] image_user1) {
         
        String sql = "INSERT INTO User(FullName,UserName,Password,Gender,City,DateOfBirth,Country,Email,SecurityQuestion,Answer,"
                    + "ProfilePicture,Admin) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = SqliteConnection.Connector();  PreparedStatement ps = conn.prepareStatement(sql)) {
            MessageDigest md = new MessageDigest(Password);
            MessageDigest mdEmail = new MessageDigest(Email);
            MessageDigest mdAnswer = new MessageDigest(Answer);
            ps.setString(1, FullName);
            ps.setString(2, UserName);
            ps.setString(3, md.getSHA());
            ps.setString(4, Gender);
            ps.setString(5, City);
            ps.setString(6, DateOfBirth);
            ps.setString(7, Country);
            ps.setString(8, mdEmail.getSHA());
            ps.setString(9, SecurityQuestion);
            ps.setString(10, mdAnswer.getSHA());
            ps.setBytes(11, image_user1);
            ps.setInt(12, 0);
            ps.execute();
        } catch (SQLException ex) {
            u = false;
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Creating new account failed");
            alert.setHeaderText("This username is already taken!");
            alert.setContentText("Try with another username.");
            alert.showAndWait();
            //System.out.println(e.toString());
        }

    }

    private void loadNextScene(Group root, Group root1, Stage primaryStage) {
        Group root2 = new Group();
        Image image1 = new Image(getClass().getResource("Images/cinema1.jpg").toString());
        ImageView iv1 = new ImageView(image1);
        iv1.setFitHeight(725);
        iv1.setFitWidth(1200);
        iv1.setLayoutX(0);
        iv1.setLayoutY(0);
        Rectangle r = new javafx.scene.shape.Rectangle();
        r.setFill(Color.WHITE);
        r.setWidth(600);
        r.setHeight(200);
        r.setLayoutX(300);
        r.setLayoutY(250);
        Label ar = new Label("    Account Registration");
        ar.setTextFill(Color.CRIMSON);
        ar.setLayoutX(390);
        ar.setLayoutY(75);
        ar.setFont(Font.font("SansSerif", FontWeight.BOLD, 30));
        Label c = new Label("         Fill the form carefully to create account");
        c.setTextFill(Color.BLACK);
        c.setLayoutX(405);
        c.setLayoutY(115);
        c.setFont(Font.font("SansSerif", FontPosture.ITALIC, 15));

        DatePicker dp = new DatePicker();
        dp.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ld = dp.getValue();
            }
        });
        dp.setLayoutX(450);
        dp.setLayoutY(225);
        dp.setMinSize(250, 40);
        dp.setPromptText("Date of Birth");
        dp.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Rotation(dp);

        });

        TextField a = new TextField();
        a.setLayoutX(150);
        a.setLayoutY(165);
        a.setPromptText("Full Name");
        a.setFont(Font.font(null, FontPosture.ITALIC, 18));
        a.setMinSize(250, 25);
        a.setStyle("-fx-background-radius: 15; -fx-text-fill: gray;");
        a.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Rotation(a);

        });

        TextField a1 = new TextField();
        a1.setLayoutX(150);
        a1.setLayoutY(225);
        a1.setPromptText("Username");
        a1.setFont(Font.font(null, FontPosture.ITALIC, 18));
        a1.setMinSize(250, 25);
        a1.setStyle("-fx-background-radius: 15; -fx-text-fill: gray;");
        a1.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Rotation(a1);

        });

        PasswordField a2 = new PasswordField();
        a2.setLayoutX(150);
        a2.setLayoutY(285);
        a2.setPromptText("Password");
        a2.setFont(Font.font(null, FontPosture.ITALIC, 18));
        a2.setMinSize(250, 25);
        a2.setStyle("-fx-background-radius: 15; -fx-text-fill: gray;");
        a2.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Rotation(a2);

        });

        TextField a3 = new TextField();
        a3.setLayoutX(450);
        a3.setLayoutY(165);
        a3.setPromptText("City");
        a3.setFont(Font.font(null, FontPosture.ITALIC, 18));
        a3.setMinSize(250, 25);
        a3.setStyle("-fx-background-radius: 15; -fx-text-fill: gray;");
        a3.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Rotation(a3);

        });

        TextField a4 = new TextField();
        a4.setLayoutX(450);
        a4.setLayoutY(345);
        a4.setPromptText("Email Address");
        a4.setFont(Font.font(null, FontPosture.ITALIC, 18));
        a4.setMinSize(250, 25);
        a4.setStyle("-fx-background-radius: 15; -fx-text-fill: gray;");
        a4.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Rotation(a4);

        });

        Label gen = new Label("Gender");
        gen.setTextFill(Color.CRIMSON);
        gen.setLayoutX(220);
        gen.setLayoutY(340);
        gen.setFont(Font.font("SansSerif", FontWeight.BOLD, FontPosture.ITALIC, 22));

        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        RadioButton other = new RadioButton("Other");
        ToggleGroup group = new ToggleGroup();
        male.setToggleGroup(group);
        female.setToggleGroup(group);
        other.setToggleGroup(group);
        male.setLayoutX(150);
        male.setLayoutY(375);
        female.setLayoutX(150);
        female.setLayoutY(415);
        other.setLayoutX(150);
        other.setLayoutY(455);
        male.setFont(Font.font("SansSerif", FontPosture.ITALIC, 17));
        female.setFont(Font.font("SansSerif", FontPosture.ITALIC, 17));
        other.setFont(Font.font("SansSerif", FontPosture.ITALIC, 17));

        ComboBox<String> dc2 = new ComboBox<>(country);
        dc2.setPromptText("Country");
        dc2.setLayoutX(450);
        dc2.setLayoutY(285);
        dc2.setMinSize(250, 40);
        dc2.setMaxSize(250, 40);
        dc2.setStyle("-fx-background-radius: 15; -fx-text-fill: gray;");
        dc2.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Rotation(dc2);

        });

        ComboBox dc4 = ComboBoxBuilder.create()
                .id("uneditable-combobox")
                .promptText("Security Question")
                .items(FXCollections.observableArrayList(securityQuestion.subList(0, 4))).build();
        dc4.setLayoutX(750);
        dc4.setLayoutY(165);
        dc4.setMinSize(250, 40);
        dc4.setMaxSize(250, 40);
        dc4.setStyle("-fx-background-radius: 15; -fx-text-fill: gray;");
        dc4.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Rotation(dc4);

        });

        TextField a8 = new TextField();
        a8.setLayoutX(750);
        a8.setLayoutY(225);
        a8.setPromptText("Answer");
        a8.setFont(Font.font(null, FontPosture.ITALIC, 18));
        a8.setMinSize(250, 25);
        a8.setStyle("-fx-background-radius: 15; -fx-text-fill: gray;");
        a8.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            Rotation(a8);

        });

        Label pp = new Label("Profile Picture");
        pp.setTextFill(Color.CRIMSON);
        pp.setLayoutX(925);
        pp.setLayoutY(325);
        pp.setFont(Font.font("SansSerif", FontWeight.BOLD, FontPosture.ITALIC, 25));

        Label dim = new Label(" 150 x 150");
        dim.setTextFill(Color.GRAY);
        dim.setLayoutX(760);
        dim.setLayoutY(360);
        dim.setFont(Font.font("SansSerif", FontWeight.BOLD, FontPosture.ITALIC, 25));

        Rectangle re = new Rectangle(750, 300, 150, 150);
        re.setFill(Color.LIGHTGRAY);

        Image image2 = new Image(getClass().getResource("Images/arrow.png").toString());
        ImageView iv = new ImageView(image2);
        iv.setFitHeight(20);
        iv.setFitWidth(20);

        Rectangle re1 = new Rectangle(750, 300, 150, 150);
        re1.setFill(Color.LIGHTGRAY);

        final FileChooser fileChooser = new FileChooser();

        Button openButton = new Button("Browse Picture", iv);
        openButton.setMinSize(150, 45);
        openButton.setStyle("-fx-font-size: 1.2em; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: #000000");
        openButton.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            openButton.setStyle("-fx-font-size: 1.2em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: white");

        });
        openButton.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            openButton.setStyle("-fx-font-size: 1.2em; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: #000000");

        });
        openButton.setLayoutX(930);
        openButton.setLayoutY(380);

        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                Rotation(openButton);
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    Rectangle re1 = new Rectangle(410, 1140, 150, 150);
                    re1.setFill(Color.LIGHTGRAY);
                    Image image = new Image(file.toURI().toString());

                    try {
                        FileInputStream fis = new FileInputStream(file);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        for (int readNum; (readNum = fis.read(buf)) != -1;) {
                            bos.write(buf, 0, readNum);
                        }
                        image_user = bos.toByteArray();
                    } catch (IOException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    ImageView iv = new ImageView(image);
                    iv.setFitHeight(150);
                    iv.setFitWidth(150);
                    iv.setLayoutX(750);
                    iv.setLayoutY(300);
                    root2.getChildren().addAll(re1, iv);
                }
            }
        });

        Button back = new Button("Back to Login page");
        back.setLayoutX(1015);
        back.setLayoutY(475);
        back.setStyle("-fx-font-size: 0.9em; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: #000000");
        back.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            back.setStyle("-fx-font-size: 0.9em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: white");

        });
        back.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            back.setStyle("-fx-font-size: 0.9em; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: #000000");

        });

        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                TranslateTransition tt = new TranslateTransition();
                tt.setDuration(Duration.millis(500));
                tt.setNode(root2);
                tt.setToX(-2000);
                tt.play();
                tt.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        makeFadeInTransition(root1);
                    }
                });

            }
        });

        Button create = new Button("Create Account!");
        create.setLayoutX(495);
        create.setLayoutY(420);
        create.setMinSize(150, 45);
        create.setStyle("-fx-font-size: 1.4em; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: #000000");
        create.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            create.setStyle("-fx-font-size: 1.4em; -fx-background-color: crimson; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: white");

        });
        create.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            create.setStyle("-fx-font-size: 1.4em; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: #000000");

        });
        create.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            Rotation(create);

            String s1 = a.getText();
            String s2 = a1.getText();
            String s3 = a2.getText();

            String s5 = a3.getText();
            String s7 = dc2.getValue();
            String s8 = a4.getText();
            String s10 = a8.getText();

            if (s1.equals("") || s2.equals("") || s3.equals("") || s5.equals("") || s7.equals("") || s8.equals("") || s10.equals("") || (RadioButton) group.getSelectedToggle() == null || dp.getValue() == null || dc4.getValue() == null || image_user == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("You have to complete every field! ");
                alert.showAndWait();
            } else {
                String s4 = ((RadioButton) group.getSelectedToggle()).getText();
                String s6 = dp.getValue().toString();
                String s9 = dc4.getValue().toString();
                insert(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, image_user);

                if (u == true) {

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Creating account successful!");
                    alert.setContentText("You can now login with your new username and password.");
                    alert.showAndWait();
                    s4 = s6 = s7 = null;
                    s1 = s2 = s3 = s5 = s8 = s9 = s10 = "";
                    image_user = null;
                    back.fire();
                }
                u = true;

            }
        });
        Rectangle r1 = new javafx.scene.shape.Rectangle();
        r1.setFill(Color.WHITE);
        r1.setWidth(1060);
        r1.setHeight(425);
        r1.setLayoutX(60);
        r1.setLayoutY(60);
        root2.getChildren().addAll(r1, ar, c, dp, a, a1, a2, a3, a4, dc2, dc4, male, female, other, gen, a8, re1, pp, dim, openButton, create, back);
        root2.setOpacity(0);
        makeFadeInTransition(root2);
        root.getChildren().addAll(root2);

    }

}
