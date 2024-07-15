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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Admin {

    private String meseci[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private String fullname;
    private String username;
    private String password;
    private String gender;
    private String city;
    private String dateofbirth;
    private String country;
    private String email;
    private String securityquestion;
    private String answer;
    private ImageView profilepicture, copy;
    private static byte[] image_user = null;
    private static byte[] foodpic = null;
    private List<Film> filmList = new LinkedList<>();
    private List<Food> foodList = new LinkedList<>();
    private List<ReadFromUser> userList = new LinkedList<>();
    private LocalDate ld;
    private final ObservableList genreList = FXCollections.observableArrayList(
            "Cartoons", "Drama", "Horror", "Comedy", "Thriller");
    private final ObservableList categoryList = FXCollections.observableArrayList(
            "Snacks", "Drinks", "Popcorns", "Desserts", "Combos");

    public void bloom(Node n) {
        Bloom bloom = new Bloom();
        bloom.setThreshold(.0);
        n.setEffect(bloom);
    }

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

    public void setProfilepicture(ImageView profilepicture) {
        this.profilepicture = profilepicture;
    }

    public Admin() {

    }

    public Admin(String fullname, String username, String password, String gender, String city, String dateofbirth, String country, String email, String securityquestion, String answer, ImageView profilepicture, List<Film> filmList, List<Food> foodList, List<ReadFromUser> userList) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.city = city;
        this.dateofbirth = dateofbirth;
        this.country = country;
        this.email = email;
        this.securityquestion = securityquestion;
        this.answer = answer;
        this.profilepicture = profilepicture;
        this.copy = new ImageView(profilepicture.getImage());
        copy.setFitHeight(200);
        copy.setFitWidth(200);
        copy.setPreserveRatio(true);
        this.filmList = filmList;
        this.foodList = foodList;
        this.userList = userList;

        Stage primaryStage = new Stage();
        Group root = new Group();
        Group root1 = new Group();
        Group root2 = new Group();
        Group root3 = new Group();

        Rectangle rec = new Rectangle(0, 200, 300, 600);
        rec.setFill(Color.ORANGE);
        Rectangle rec1 = new Rectangle(0, 0, 300, 200);
        rec1.setFill(Color.BLACK);
        Circle cir = new Circle();
        cir.setLayoutX(150);
        cir.setLayoutY(85);
        cir.setRadius(75);
        cir.setFill(Color.WHITE);

        Shape clip = Shape.subtract(rec1, cir);
        clip.setFill(Color.DARKORANGE);

        Label l = new Label("You");
        l.setTextFill(Color.WHITE);
        l.setLayoutX(125);
        l.setLayoutY(155);
        l.setFont(Font.font(null, FontWeight.BOLD, 30));

        //Ovde treba ucitati sliku iz baze podataka, koristim samo kao primer svoju
        this.profilepicture.setFitHeight(200);
        this.profilepicture.setFitWidth(300);
        this.profilepicture.setLayoutX(0);
        this.profilepicture.setLayoutY(-15);

        Label time = new Label();
        time.setTextFill(Color.CRIMSON);
        time.setLayoutX(85);
        time.setLayoutY(215);
        time.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 30));

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            time.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Label day = new Label();
        day.setTextFill(Color.CRIMSON);
        day.setLayoutX(15);
        day.setLayoutY(255);
        day.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 25));

        Calendar kalendar = Calendar.getInstance();
        day.setText("            " + simpleDateFormat.format(now) + "\n       " + kalendar.get(Calendar.DATE) + " " + meseci[kalendar.get(Calendar.MONTH)] + " " + kalendar.get(Calendar.YEAR));

        Label cc = new Label("About us");
        cc.setTextFill(Color.WHITE);
        cc.setLayoutX(90);
        cc.setLayoutY(560);
        cc.setFont(Font.font(null, FontWeight.BOLD, 22));
        Image image3 = new Image(getClass().getResource("Images/i.png").toString());
        ImageView iv3 = new ImageView(image3);
        iv3.setFitHeight(30);
        iv3.setFitWidth(30);
        iv3.setLayoutX(40);
        iv3.setLayoutY(560);

        Label n = new Label("Send feedback");
        n.setTextFill(Color.WHITE);
        n.setLayoutX(90);
        n.setLayoutY(510);
        n.setFont(Font.font(null, FontWeight.BOLD, 22));
        Image image4 = new Image(getClass().getResource("Images/email2.png").toString());
        ImageView iv4 = new ImageView(image4);
        iv4.setFitHeight(40);
        iv4.setFitWidth(40);
        iv4.setLayoutX(35);
        iv4.setLayoutY(505);

        Label c = new Label("Profile Information");
        c.setTextFill(Color.WHITE);
        c.setLayoutX(90);
        c.setLayoutY(460);
        c.setFont(Font.font(null, FontWeight.BOLD, 22));
        Image image2 = new Image(getClass().getResource("Images/pi.png").toString());
        ImageView iv2 = new ImageView(image2);
        iv2.setFitHeight(30);
        iv2.setFitWidth(60);
        iv2.setLayoutX(27);
        iv2.setLayoutY(460);

        Label us = new Label("Home");
        us.setTextFill(Color.WHITE);
        us.setLayoutX(87);
        us.setLayoutY(410);
        us.setFont(Font.font(null, FontWeight.BOLD, 22));

        Image image1 = new Image(getClass().getResource("Images/home1.png").toString());
        ImageView iv1 = new ImageView(image1);
        iv1.setFitHeight(30);
        iv1.setFitWidth(30);
        iv1.setLayoutX(40);
        iv1.setLayoutY(410);

        Image i = new Image(getClass().getResource("Images/ff.png").toString());
        ImageView iv = new ImageView(i);
        iv.setFitHeight(30);
        iv.setFitWidth(30);
        
        Button manageFilms = new Button("Add a new film", GlyphsDude.createIcon(FontAwesomeIcons.FILM, "20px"));
        Button addFood = new Button("Add food", iv);

        Rectangle UserSettings = new Rectangle(0, 400, 300, 50);
        UserSettings.setFill(Color.ORANGE);
        UserSettings.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            UserSettings.setFill(Color.CRIMSON);
            bloom(us);
        });
        UserSettings.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            UserSettings.setFill(Color.ORANGE);
            us.setEffect(null);
        });

        UserSettings.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            if (root2.getParent() != null) {
                FadeTransition fadeTransition = new FadeTransition();
                fadeTransition.setDuration(Duration.millis(500));
                fadeTransition.setNode(root2);
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);
                fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        root1.setOpacity(0);
                        root.getChildren().add(root1);
                        makeFadeInTransition(root1);
                        root.getChildren().remove(root2);
                        resetElements(root2);
                    }
                });

                fadeTransition.play();
                manageFilms.setDisable(false);
            } else if (root3.getParent() != null) {
                FadeTransition fadeTransition = new FadeTransition();
                fadeTransition.setDuration(Duration.millis(500));
                fadeTransition.setNode(root3);
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);
                fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        root1.setOpacity(0);
                        root.getChildren().add(root1);
                        makeFadeInTransition(root1);
                        root.getChildren().remove(root3);
                        resetElements(root3);
                    }
                });

                fadeTransition.play();
                addFood.setDisable(false);
            }

        });
        us.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            UserSettings.setFill(Color.CRIMSON);
            bloom(us);
        });
        us.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            UserSettings.setFill(Color.ORANGE);
            us.setEffect(null);
        });

        us.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            if (root2.getParent() != null) {
                FadeTransition fadeTransition = new FadeTransition();
                fadeTransition.setDuration(Duration.millis(500));
                fadeTransition.setNode(root2);
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);
                fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        root1.setOpacity(0);
                        root.getChildren().add(root1);
                        makeFadeInTransition(root1);
                        root.getChildren().remove(root2);
                        resetElements(root2);
                    }
                });

                fadeTransition.play();
                manageFilms.setDisable(false);
            } else if (root3.getParent() != null) {
                FadeTransition fadeTransition = new FadeTransition();
                fadeTransition.setDuration(Duration.millis(500));
                fadeTransition.setNode(root3);
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);
                fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        root1.setOpacity(0);
                        root.getChildren().add(root1);
                        makeFadeInTransition(root1);
                        root.getChildren().remove(root3);
                        resetElements(root3);
                    }
                });

                fadeTransition.play();
                addFood.setDisable(false);
            }

        });

        Rectangle Calculator = new Rectangle(0, 450, 300, 50);
        Calculator.setFill(Color.ORANGE);
        Calculator.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            Calculator.setFill(Color.CRIMSON);
            bloom(c);
        });
        Calculator.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            Calculator.setFill(Color.ORANGE);
            c.setEffect(null);
        });

        Calculator.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            ProfileInformation pi = new ProfileInformation(this, null, 1, this.fullname, this.username, this.password, this.gender, this.city, this.dateofbirth, this.country, this.email, this.securityquestion, this.answer, this.profilepicture.getImage());

        });

        c.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            Calculator.setFill(Color.CRIMSON);
            bloom(c);
        });
        c.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            Calculator.setFill(Color.ORANGE);
            c.setEffect(null);
        });

        c.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            ProfileInformation pi = new ProfileInformation(this, null, 1, this.fullname, this.username, this.password, this.gender, this.city, this.dateofbirth, this.country, this.email, this.securityquestion, this.answer, this.profilepicture.getImage());

        });

        Rectangle Notepad = new Rectangle(0, 500, 300, 50);
        Notepad.setFill(Color.ORANGE);
        Notepad.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            Notepad.setFill(Color.CRIMSON);
            bloom(n);
        });
        Notepad.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            Notepad.setFill(Color.ORANGE);
            n.setEffect(null);
        });

        Notepad.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            try {
                JavaMailUtil jmu = new JavaMailUtil(this.username, "lazaigrice");
            } catch (Exception ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        n.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            Notepad.setFill(Color.CRIMSON);
            bloom(n);
        });
        n.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            Notepad.setFill(Color.ORANGE);
            n.setEffect(null);
        });

        n.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            try {
                JavaMailUtil jmu = new JavaMailUtil(this.username, "lazaigrice");
            } catch (Exception ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Rectangle Currency = new Rectangle(0, 550, 300, 50);
        Currency.setFill(Color.ORANGE);
        Currency.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            Currency.setFill(Color.CRIMSON);
            bloom(cc);
        });
        Currency.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            Currency.setFill(Color.ORANGE);
            cc.setEffect(null);
        });

        Currency.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            AboutUs au = new AboutUs();
        });

        cc.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            Currency.setFill(Color.CRIMSON);
            bloom(cc);
        });
        cc.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            Currency.setFill(Color.ORANGE);
            cc.setEffect(null);
        });

        cc.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            AboutUs au = new AboutUs();
        });
        Label lo = new Label("Log out");
        lo.setTextFill(Color.WHITE);
        lo.setLayoutX(90);
        lo.setLayoutY(610);
        lo.setFont(Font.font(null, FontWeight.BOLD, 22));
        Rectangle Logout = new Rectangle(0, 600, 300, 50);
        Logout.setFill(Color.ORANGE);
        Logout.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            Logout.setFill(Color.CRIMSON);
            bloom(lo);
        });
        Logout.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            Logout.setFill(Color.ORANGE);
            lo.setEffect(null);
        });

        Logout.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            
            primaryStage.close();
            Group r = new Group();
            Stage s = new Stage();
            Login login = new Login(r, s, filmList, foodList);
            
        });

        lo.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            Logout.setFill(Color.CRIMSON);
            bloom(lo);
        });
        lo.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

            Logout.setFill(Color.ORANGE);
            lo.setEffect(null);
        });

        lo.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            primaryStage.close();
            Group r = new Group();
            Stage s = new Stage();
            Login login = new Login(r, s, filmList, foodList);

        });
        Text createIcon = GlyphsDude.createIcon(FontAwesomeIcons.CLOSE, "30px");
        createIcon.setLayoutY(635);
        createIcon.setLayoutX(42);

        manageFilms.setLayoutX(305);
        manageFilms.setLayoutY(550);
        manageFilms.setMinSize(805, 45);
        manageFilms.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-text-fill: #000000");
        manageFilms.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            manageFilms.setStyle("-fx-font-size: 1.5em; -fx-background-color: crimson; -fx-font-weight: bold;  -fx-text-fill: white");

        });
        manageFilms.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            manageFilms.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-text-fill: #000000");

        });

        manageFilms.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            addFood.setDisable(false);
            manageFilms.setDisable(true);

            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setDuration(Duration.millis(500));
            fadeTransition.setNode((root3.getParent() == null) ? root1 : root3);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().add(root2);
                    makeFadeInTransition(root2);
                    root.getChildren().remove((root3.getParent() == null) ? root1 : root3);
                }
            });

            fadeTransition.play();

            /*root.getChildren().remove(root2);
               root.getChildren().add(root2);
               makeFadeOut(root1,root2,root);
               manageFilms.setDisable(true);*/
        });

        Button manageBookings = new Button("Manage databases", GlyphsDude.createIcon(FontAwesomeIcons.DATABASE, "20px"));
        manageBookings.setLayoutX(305);
        manageBookings.setLayoutY(500);
        manageBookings.setMinSize(805, 45);
        manageBookings.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-text-fill: #000000");
        manageBookings.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            manageBookings.setStyle("-fx-font-size: 1.5em; -fx-background-color: crimson; -fx-font-weight: bold;  -fx-text-fill: white");

        });
        manageBookings.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            manageBookings.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-text-fill: #000000");

        });

        manageBookings.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            try {
                Manage m = new Manage(this.filmList, this.foodList, this.userList);
            } catch (SQLException ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        addFood.setLayoutX(305);
        addFood.setLayoutY(600);
        addFood.setMinSize(805, 45);
        addFood.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-text-fill: #000000");
        addFood.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            addFood.setStyle("-fx-font-size: 1.5em; -fx-background-color: crimson; -fx-font-weight: bold;  -fx-text-fill: white");

        });
        addFood.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            addFood.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-text-fill: #000000");

        });

        addFood.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            addFood.setDisable(true);
            manageFilms.setDisable(false);

            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setDuration(Duration.millis(500));
            fadeTransition.setNode((root2.getParent() == null) ? root1 : root2);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().add(root3);
                    makeFadeInTransition(root3);
                    root.getChildren().remove((root2.getParent() == null) ? root1 : root2);
                }
            });

            fadeTransition.play();

        });

        Label about = new Label("Welcome to the admin mode!");
        about.setTextFill(Color.CRIMSON);
        about.setLayoutX(480);
        about.setLayoutY(30);
        about.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 30));

        Label about1 = new Label("The author of this application, Lazar Savić,\nconsiders you as a loyal and honest person.\nPlease, do not misuse your right to arrange\nand update this app, or we will be forced to\nfire you. Be careful and enjoy!");
        about1.setTextFill(Color.CRIMSON);
        about1.setLayoutX(420);
        about1.setLayoutY(100);
        about1.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 25));

        Label title = new Label("Title:");
        title.setTextFill(Color.BLACK);
        title.setLayoutX(320);
        title.setLayoutY(30);
        title.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        Label myTitle = new Label("My Title");
        myTitle.setTextFill(Color.ORANGE);
        myTitle.setLayoutX(925);
        myTitle.setLayoutY(30);
        myTitle.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 30));

        TextField t = new TextField();
        t.setLayoutX(405);
        t.setLayoutY(25);
        t.setFont(Font.font(null, FontPosture.ITALIC, 18));
        t.setPromptText("Enter the film title");
        t.setMinSize(250, 25);
        t.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");
        t.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                myTitle.setText(newValue);
            }
        });

        Label poster = new Label("Poster:");
        poster.setTextFill(Color.BLACK);
        poster.setLayoutX(320);
        poster.setLayoutY(80);
        poster.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        Image im = new Image(getClass().getResource("Images/arrow.png").toString());
        ImageView bp = new ImageView(im);
        bp.setFitHeight(20);
        bp.setFitWidth(20);

        final FileChooser fileChooser = new FileChooser();

        Button p = new Button("Browse Picture  ", bp);
        p.setMinSize(150, 45);
        p.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: black;");
        p.setLayoutX(405);
        p.setLayoutY(75);
        p.setMinSize(250, 25);

        p.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
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
                    iv.setFitHeight(250);
                    iv.setFitWidth(169);
                    iv.setLayoutX(700);
                    iv.setLayoutY(30);
                    root2.getChildren().addAll(iv);
                }
            }
        });

        Label trailer = new Label("Trailer:");
        trailer.setTextFill(Color.BLACK);
        trailer.setLayoutX(320);
        trailer.setLayoutY(130);
        trailer.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        TextField tr = new TextField();
        tr.setLayoutX(405);
        tr.setLayoutY(125);
        tr.setFont(Font.font(null, FontPosture.ITALIC, 18));
        tr.setPromptText("Enter the youtube link");
        tr.setMinSize(250, 25);
        tr.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");

        Line line = new Line(675, 0, 675, 300);
        line.setStrokeWidth(2);
        line.setStroke(Color.GRAY);
        Line line2 = new Line(675, 300, 1115, 300);
        line2.setStrokeWidth(2);
        line2.setStroke(Color.GRAY);

        Rectangle r = new Rectangle(700, 30, 169, 250);
        r.setFill(Color.LIGHTGRAY);

        Image im1 = new Image(getClass().getResource("Images/default.png").toString());
        ImageView bp1 = new ImageView(im1);
        bp1.setFitHeight(250);
        bp1.setFitWidth(169);
        bp1.setLayoutX(700);
        bp1.setLayoutY(60);
        bp1.setPreserveRatio(true);

        Label genre = new Label("Genre:");
        genre.setTextFill(Color.BLACK);
        genre.setLayoutX(320);
        genre.setLayoutY(180);
        genre.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        ComboBox<String> g = new ComboBox<>(genreList);
        g.setPromptText("Genre");
        g.setLayoutX(405);
        g.setLayoutY(175);
        g.setMinSize(250, 25);
        g.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");

        Label myGenre = new Label("*My Genre");
        myGenre.setTextFill(Color.ORANGE);
        myGenre.setLayoutX(880);
        myGenre.setLayoutY(75);
        myGenre.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        g.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                myGenre.setText("*" + newValue);
            }
        });

        Label length = new Label("Length:");
        length.setTextFill(Color.BLACK);
        length.setLayoutX(320);
        length.setLayoutY(230);
        length.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        TextField l1 = new TextField();
        l1.setLayoutX(405);
        l1.setLayoutY(225);
        l1.setFont(Font.font(null, FontPosture.ITALIC, 18));
        l1.setPromptText("Format: hh:mm");
        l1.setMinSize(250, 25);
        l1.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");

        Label myLength = new Label("*Length: ?");
        myLength.setTextFill(Color.ORANGE);
        myLength.setLayoutX(880);
        myLength.setLayoutY(105);
        myLength.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        l1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                myLength.setText("*Length: " + newValue);
            }
        });

        TextArea textArea = new TextArea();
        textArea.setMaxSize(340, 200);
        textArea.setMinSize(340, 200);
        textArea.setWrapText(true);
        textArea.setPromptText("Short description of the film...");
        textArea.setLayoutX(320);
        textArea.setLayoutY(280);
        textArea.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        Label times = new Label("Times:");
        times.setTextFill(Color.BLACK);
        times.setLayoutX(680);
        times.setLayoutY(420);
        times.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        Button spt = new Button("Specify projection times", GlyphsDude.createIcon(FontAwesomeIcons.CALENDAR, "20px"));
        spt.setLayoutX(770);
        spt.setLayoutY(458);
        spt.setMinSize(50, 35);
        spt.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-color: orange; -fx-text-fill: #000000");
        spt.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            spt.setStyle("-fx-font-size: 1.5em; -fx-background-color: crimson; -fx-font-weight: bold;  -fx-text-fill: white");

        });
        spt.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            spt.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-color: orange; -fx-text-fill: #000000");

        });

        spt.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            String duzina = l1.getText();
            String imeFilma = t.getText();
            if (duzina.equals("") || imeFilma.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("You have to complete both the length field and title field!");
                alert.showAndWait();
            } else {
                Termin termin = new Termin(duzina, imeFilma, this.username, this.filmList);
            }

        });

        Label rating = new Label("IMDB rating:");
        rating.setTextFill(Color.BLACK);
        rating.setLayoutX(690);
        rating.setLayoutY(315);
        rating.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        TextField IMDBrating = new TextField();
        IMDBrating.setLayoutX(880);
        IMDBrating.setLayoutY(315);
        IMDBrating.setFont(Font.font(null, FontPosture.ITALIC, 18));
        IMDBrating.setPromptText("Format: x.y, 0<=x.y<=10");
        IMDBrating.setMaxWidth(230);
        IMDBrating.setMinWidth(230);
        IMDBrating.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");

        Label adult = new Label("Adult ticket cost:");
        adult.setTextFill(Color.BLACK);
        adult.setLayoutX(690);
        adult.setLayoutY(365);
        adult.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        TextField atc = new TextField();
        atc.setLayoutX(880);
        atc.setLayoutY(365);
        atc.setFont(Font.font(null, FontPosture.ITALIC, 18));
        atc.setPromptText("In euros");
        atc.setMaxWidth(230);
        atc.setMinWidth(230);
        atc.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");

        Label child = new Label("Child ticket cost:");
        child.setTextFill(Color.BLACK);
        child.setLayoutX(690);
        child.setLayoutY(415);
        child.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        TextField ctc = new TextField();
        ctc.setLayoutX(880);
        ctc.setLayoutY(415);
        ctc.setFont(Font.font(null, FontPosture.ITALIC, 18));
        ctc.setPromptText("In euros");
        ctc.setMaxWidth(230);
        ctc.setMinWidth(230);
        ctc.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");

        Button addMovie = new Button("Add Movie", GlyphsDude.createIcon(FontAwesomeIcons.PLUS, "20px"));
        addMovie.setLayoutX(930);
        addMovie.setLayoutY(230);
        addMovie.setMinSize(100, 30);
        addMovie.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: orange;");
        addMovie.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            addMovie.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: crimson;");
        });
        addMovie.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            addMovie.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: orange;");

        });

        addMovie.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            String s1 = t.getText();
            String s2 = tr.getText();
            String s3 = g.getValue();
            String s4 = l1.getText();
            String s5 = textArea.getText();
            String s6 = IMDBrating.getText();
            String s7 = atc.getText();
            String s8 = ctc.getText();

            if (s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("") || s5.equals("") || s6.equals("") || s7.equals("") || s8.equals("") || image_user == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("You have to complete every field! ");
                alert.showAndWait();
            } else {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Reminder");
                alert.setContentText("Have you specified projection times?");
                ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(okButton, cancelButton);
                alert.showAndWait().ifPresent(type -> {
                    if (type.getText().toString().equals("Yes")) {

                        Connection con = SqliteConnection.Connector();
                        PreparedStatement ps = null;
                        try {
                            String sql = "INSERT INTO Film(Ime,Poster,KratakOpis,Trejler,Zanr,Duzina,Dodao,DatumDodavanja,Rating,CenaKarteD,CenaKarteO,Aktivan) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
                            ps = con.prepareStatement(sql);
                            ps.setString(1, s1);
                            ps.setBytes(2, image_user);
                            ps.setString(3, s5);
                            ps.setString(4, s2);
                            ps.setString(5, s3);
                            ps.setString(6, s4);
                            ps.setString(7, this.username);
                            ps.setString(8, LocalDate.now().toString());
                            ps.setString(9, s6);
                            ps.setString(10, s8);
                            ps.setString(11, s7);
                            ps.setInt(12, 1);
                            ps.execute();
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.initStyle(StageStyle.UTILITY);
                            alert1.setTitle("Info");
                            alert1.setHeaderText(null);
                            alert1.setContentText("You have successfully added the film.");
                            alert1.showAndWait();

                        } catch (SQLException e) {
                            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                            alert1.setTitle("Adding new movie failed");
                            alert1.setHeaderText("This film already exists in the database!");
                            alert1.setContentText("Change the name of the movie or delete the existing movie from the database.");
                            alert1.showAndWait();
                        }
                    } else {
                        alert.close();
                    }

                });

            }

        });

        Label name = new Label("Name:");
        name.setTextFill(Color.BLACK);
        name.setLayoutX(320);
        name.setLayoutY(30);
        name.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        Label myName = new Label("My name");
        myName.setTextFill(Color.ORANGE);
        myName.setLayoutX(825);
        myName.setLayoutY(20);
        myName.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 30));

        TextField NAME = new TextField();
        NAME.setLayoutX(430);
        NAME.setLayoutY(25);
        NAME.setFont(Font.font(null, FontPosture.ITALIC, 18));
        NAME.setPromptText("Enter the food name");
        NAME.setMinSize(250, 25);
        NAME.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");
        NAME.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                myName.setText(newValue);
            }
        });

        Label pic = new Label("Picture:");
        pic.setTextFill(Color.BLACK);
        pic.setLayoutX(320);
        pic.setLayoutY(80);
        pic.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        final FileChooser fileChooser1 = new FileChooser();

        Button bpp = new Button("Browse Picture  ", GlyphsDude.createIcon(FontAwesomeIcons.UPLOAD, "20px"));
        bpp.setMinSize(150, 45);
        bpp.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: black;");
        bpp.setLayoutX(430);
        bpp.setLayoutY(75);
        bpp.setMinSize(250, 25);

        bpp.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                configureFileChooser(fileChooser1);
                File file = fileChooser1.showOpenDialog(primaryStage);
                if (file != null) {
                    Image image = new Image(file.toURI().toString());

                    try {
                        FileInputStream fis = new FileInputStream(file);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        for (int readNum; (readNum = fis.read(buf)) != -1;) {
                            bos.write(buf, 0, readNum);
                        }
                        foodpic = bos.toByteArray();
                    } catch (IOException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    ImageView iv = new ImageView(image);
                    iv.setFitHeight(200);
                    iv.setFitWidth(200);
                    iv.setLayoutX(800);
                    iv.setLayoutY(200);
                    root3.getChildren().addAll(iv);
                }
            }
        });

        Image imdef = new Image(getClass().getResource("Images/defaultfood.jpg").toString());
        ImageView bpdef = new ImageView(imdef);
        bpdef.setFitHeight(200);
        bpdef.setFitWidth(200);
        bpdef.setLayoutX(800);
        bpdef.setLayoutY(200);
        //bpdef.setPreserveRatio(true);

        Label category = new Label("Category:");
        category.setTextFill(Color.BLACK);
        category.setLayoutX(320);
        category.setLayoutY(130);
        category.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        ComboBox<String> ccb = new ComboBox<>(categoryList);
        ccb.setPromptText("Category");
        ccb.setLayoutX(430);
        ccb.setLayoutY(125);
        ccb.setMinSize(250, 25);
        ccb.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");

        Label myCategory = new Label("*My Category");
        myCategory.setTextFill(Color.ORANGE);
        myCategory.setLayoutX(780);
        myCategory.setLayoutY(75);
        myCategory.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        ccb.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                myCategory.setText("*" + newValue);
            }
        });

        Label price = new Label("Price:");
        price.setTextFill(Color.BLACK);
        price.setLayoutX(320);
        price.setLayoutY(180);
        price.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        Label myPrice = new Label("*My price");
        myPrice.setTextFill(Color.ORANGE);
        myPrice.setLayoutX(780);
        myPrice.setLayoutY(125);
        myPrice.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 22));

        TextField PRICE = new TextField();
        PRICE.setLayoutX(430);
        PRICE.setLayoutY(175);
        PRICE.setFont(Font.font(null, FontPosture.ITALIC, 18));
        PRICE.setPromptText("In euros");
        PRICE.setMinSize(250, 25);
        PRICE.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");
        PRICE.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                myPrice.setText(newValue);
            }
        });
        TextArea sd = new TextArea();
        sd.setMaxSize(360, 200);
        sd.setMinSize(360, 200);
        sd.setWrapText(true);
        sd.setPromptText("Short description of the food...");
        sd.setLayoutX(320);
        sd.setLayoutY(280);
        sd.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        Line lineY = new Line(700, 0, 700, 485);
        lineY.setStrokeWidth(2);
        lineY.setStroke(Color.GRAY);

        Button record = new Button("Record", GlyphsDude.createIcon(FontAwesomeIcons.PLUS, "20px"));
        record.setLayoutX(850);
        record.setLayoutY(425);
        record.setMinSize(100, 30);
        record.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: orange;");
        record.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

            record.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: crimson;");
        });
        record.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            record.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: orange;");

        });

        record.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            String s1 = NAME.getText();
            String s2 = ccb.getValue();
            String s3 = PRICE.getText();
            String s4 = sd.getText();

            if (s1.equals("") || s2.equals("") || s3.equals("") || foodpic == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("You have to complete every field! ");
                alert.showAndWait();
            } else {

                Connection con = SqliteConnection.Connector();
                PreparedStatement ps = null;
                try {
                    String sql = "INSERT INTO Hrana(Ime,Slika,Kategorija,Cena,Opis,Dodao,DatumDodavanja) VALUES(?,?,?,?,?,?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, s1);
                    ps.setBytes(2, foodpic);
                    ps.setString(3, s2);
                    ps.setString(4, s3);
                    ps.setString(5, s4);
                    ps.setString(6, this.username);
                    ps.setString(7, LocalDate.now().toString());
                    ps.execute();
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.initStyle(StageStyle.UTILITY);
                    alert1.setTitle("Info");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Successfully recorded.");
                    alert1.showAndWait();

                } catch (SQLException e) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Adding food failed");
                    alert1.setHeaderText("This food already exists in the database!");
                    alert1.setContentText("Change the name of the food or delete the existing food from the database.");
                    alert1.showAndWait();
                }

            }

        });

        root1.getChildren().addAll(about, about1);
        root2.getChildren().addAll(title, t, poster, p, trailer, tr, line, line2, r, bp1, genre, g, length, l1, textArea, myTitle, myGenre, myLength, addMovie, spt, rating, IMDBrating, adult, child, atc, ctc);
        //root2bp.getChildren().addAll(title,t,poster,p,trailer,tr,line,line2,r,bp1,genre,g,length,l1,textArea,dp1,dates,from,to,dp2,myTitle,myGenre,myLength,starts,ends,addMovie,spt);
        root2.setOpacity(0);
        root3.getChildren().addAll(name, myName, NAME, pic, bpp, bpdef, category, ccb, myCategory, price, myPrice, PRICE, sd, lineY, record);
        root3.setOpacity(0);
        root.getChildren().addAll(this.profilepicture, rec, clip, l, day, time, UserSettings, Calculator, Notepad, Currency, Logout, cc, n, c, us, lo, createIcon);
        root.getChildren().addAll(iv1, iv2, iv3, iv4, manageFilms, manageBookings, addFood, root1);
        Scene scene = new Scene(root, 1115, 650);

        primaryStage.setTitle("Films");
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

    private void makeFadeOut(Group root1, Group root2, Group root) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(root1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeFadeInTransition(root2);
                root.getChildren().remove(root1);
            }
        });

        fadeTransition.play();
    }

    public void resetElements(Group root) {

    }

    public void makeFadeInTransition(Group root) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(root);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
