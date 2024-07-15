package cinema;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.StrokeTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.text.Text;

public class User {

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
    private ImageView profilepicture;
    private List<Film> filmList = new LinkedList<>();
    private List<Film> ComedyList = new LinkedList<>();
    private List<Film> ThrillerList = new LinkedList<>();
    private List<Film> CartoonList = new LinkedList<>();
    private List<Film> HorrorList = new LinkedList<>();
    private List<Film> DramaList = new LinkedList<>();
    private List<Food> foodList = new LinkedList<>();

    public void bloom(Node n) {
        Bloom bloom = new Bloom();
        bloom.setThreshold(.0);
        n.setEffect(bloom);
    }

    public User() {

    }

    public void setProfilepicture(ImageView profilepicture) {
        this.profilepicture = profilepicture;
    }

    public User(String fullname, String username, String password, String gender, String city, String dateofbirth, String country, String email, String securityquestion, String answer, ImageView profilepicture, List<Film> filmList, List<Food> foodList) {

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
        this.filmList = filmList;
        this.foodList = foodList;

        Stage primaryStage = new Stage();

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
        cc.setLayoutY(510);
        cc.setFont(Font.font(null, FontWeight.BOLD, 22));
        Image image3 = new Image(getClass().getResource("Images/i.png").toString());
        ImageView iv3 = new ImageView(image3);
        iv3.setFitHeight(30);
        iv3.setFitWidth(30);
        iv3.setLayoutX(40);
        iv3.setLayoutY(510);

        Label n = new Label("Send feedback");
        n.setTextFill(Color.WHITE);
        n.setLayoutX(90);
        n.setLayoutY(460);
        n.setFont(Font.font(null, FontWeight.BOLD, 22));
        Image image4 = new Image(getClass().getResource("Images/email2.png").toString());
        ImageView iv4 = new ImageView(image4);
        iv4.setFitHeight(40);
        iv4.setFitWidth(40);
        iv4.setLayoutX(35);
        iv4.setLayoutY(455);

        Label c = new Label("Profile Information");
        c.setTextFill(Color.WHITE);
        c.setLayoutX(90);
        c.setLayoutY(410);
        c.setFont(Font.font(null, FontWeight.BOLD, 22));
        Image image2 = new Image(getClass().getResource("Images/pi.png").toString());
        ImageView iv2 = new ImageView(image2);
        iv2.setFitHeight(30);
        iv2.setFitWidth(60);
        iv2.setLayoutX(27);
        iv2.setLayoutY(410);

        Rectangle Calculator = new Rectangle(0, 400, 300, 50);
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

            ProfileInformation pi = new ProfileInformation(null, this, 0, this.fullname, this.username, this.password, this.gender, this.city, this.dateofbirth, this.country, this.email, this.securityquestion, this.answer, this.profilepicture.getImage());

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

            ProfileInformation pi = new ProfileInformation(null, this, 0, this.fullname, this.username, this.password, this.gender, this.city, this.dateofbirth, this.country, this.email, this.securityquestion, this.answer, this.profilepicture.getImage());

        });

        Rectangle Notepad = new Rectangle(0, 450, 300, 50);
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

        Rectangle Currency = new Rectangle(0, 500, 300, 50);
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
        lo.setLayoutY(560);
        lo.setFont(Font.font(null, FontWeight.BOLD, 22));
        Rectangle Logout = new Rectangle(0, 550, 300, 50);
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
        createIcon.setLayoutY(585);
        createIcon.setLayoutX(42);

        Menu Genre = new Menu("   Genre");
        Genre.setStyle("-fx-font-size: 1.7em; -fx-background-color: wheat; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-text-fill: white");

        ToggleGroup tg = new ToggleGroup();
        RadioMenuItem Cartoons = new RadioMenuItem("Cartoons...");
        RadioMenuItem Drama = new RadioMenuItem("Drama...");
        RadioMenuItem Horror = new RadioMenuItem("Horror...");
        RadioMenuItem Comedy = new RadioMenuItem("Comedy...");
        RadioMenuItem Thriller = new RadioMenuItem("Thriller...");

        Cartoons.setToggleGroup(tg);
        Drama.setToggleGroup(tg);
        Horror.setToggleGroup(tg);
        Comedy.setToggleGroup(tg);
        Thriller.setToggleGroup(tg);

        Genre.getItems().addAll(Cartoons, new SeparatorMenuItem(), Drama, new SeparatorMenuItem(), Horror, new SeparatorMenuItem(), Comedy, new SeparatorMenuItem(), Thriller);

        MenuBar menuBar = new MenuBar();
        menuBar.setLayoutX(315);
        menuBar.setLayoutY(0);
        menuBar.setStyle("-fx-background-color: WHEAT");

        menuBar.setScaleX(1.25);
        menuBar.setScaleY(1.25);
        menuBar.getMenus().addAll(Genre);

        Group root = new Group();

        TabPane tabPane = new TabPane();
        tabPane.getSelectionModel().select(6);

        Tab tab1 = new Tab("Thriller");
        Tab tab2 = new Tab("Comedy");
        Tab tab3 = new Tab("Horror");
        Tab tab4 = new Tab("Drama");
        Tab tab5 = new Tab("Cartoons");

        tab1.setStyle(" -fx-font-family: Arial; -fx-font-size: 18; -fx-font-weight: bold;");
        tab2.setStyle(" -fx-font-family: Arial; -fx-font-size: 18; -fx-font-weight: bold;");
        tab3.setStyle(" -fx-font-family: Arial; -fx-font-size: 18; -fx-font-weight: bold;");
        tab4.setStyle(" -fx-font-family: Arial; -fx-font-size: 18; -fx-font-weight: bold;");
        tab5.setStyle(" -fx-font-family: Arial; -fx-font-size: 18; -fx-font-weight: bold;");

        /*Button b = new Button("Button 1@Tab C");
        b.setLayoutX(500);
        b.setLayoutY(500);
        tab1.setContent(b);*/
        tabPane.getTabs().addAll(tab1, tab2, tab3, tab4, tab5);

        tabPane.setLayoutX(300);
        tabPane.setLayoutY(0);
        tabPane.setMinSize(1000, 50);
        tabPane.setMaxSize(1000, 50);

        tabPane.setTabMinWidth(175);
        tabPane.setTabMaxWidth(175);
        tabPane.setTabMinHeight(50);
        tabPane.setTabMinHeight(50);

        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                Group gp = new Group();
                Label label = new Label("");
                label.setLayoutX(45);
                label.setLayoutY(45);
                Image image0 = new Image(getClass().getResource("Images/wall3.jpg").toString());
                ImageView iv0 = new ImageView(image0);
                iv0.setLayoutX(45);
                iv0.setLayoutY(45);
                iv0.setFitWidth(1000);
                iv0.setFitHeight(1000);

                gp.getChildren().addAll(label, iv0);
                ScrollPane sp = new ScrollPane(gp);
                sp.setPannable(true);
                sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                sp.setPrefViewportWidth(980);
                sp.setPrefViewportHeight(540);
                //gp.setLayoutX(300);
                //gp.setLayoutY(50);
                sp.setLayoutX(300);
                sp.setLayoutY(55);
                if (t1.getText().equals("Comedy")) {
                    if (ComedyList.isEmpty()) {
                        insert(gp);
                    } else {
                        for (int i = 0; i < ComedyList.size(); i++) {
                            Film f = ComedyList.get(i);
                            Image image1 = new Image(getClass().getResource("Images/frame2.png").toString());
                            ImageView iv = new ImageView(image1);
                            iv.setLayoutX(45 + (i % 3) * 316.67);
                            iv.setLayoutY(60 + (i / 3) * 454);
                            iv.setFitWidth(360);
                            iv.setFitHeight(424);

                            //ImageView poster = new ImageView();
                            ImageView poster = ComedyList.get(i).getPoster();
                            poster.setFitWidth(250);
                            poster.setFitHeight(400);
                            poster.setPreserveRatio(true);
                            poster.setLayoutX(103 + (i % 3) * 316.67);
                            poster.setLayoutY(85 + (i / 3) * 454);
                            poster.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

                                ColorAdjust colorAdjust = new ColorAdjust();
                                colorAdjust.setBrightness(0.2);
                                poster.setEffect(colorAdjust);

                            });
                            poster.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

                                poster.setEffect(null);

                            });

                            poster.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                                FilmDetails fd = new FilmDetails(f, foodList, username, fullname);

                            });

                            gp.getChildren().addAll(iv, poster);
                        }
                    }
                }
                if (t1.getText().equals("Thriller")) {
                    if (ThrillerList.isEmpty()) {
                        insert(gp);
                    } else {
                        for (int i = 0; i < ThrillerList.size(); i++) {
                            Film f = ThrillerList.get(i);
                            Image image1 = new Image(getClass().getResource("Images/frame2.png").toString());
                            ImageView iv = new ImageView(image1);
                            iv.setLayoutX(45 + (i % 3) * 316.67);
                            iv.setLayoutY(60 + (i / 3) * 454);
                            iv.setFitWidth(360);
                            iv.setFitHeight(424);

                            //ImageView poster = new ImageView();
                            ImageView poster = ThrillerList.get(i).getPoster();
                            poster.setFitWidth(250);
                            poster.setFitHeight(400);
                            poster.setPreserveRatio(true);
                            poster.setLayoutX(103 + (i % 3) * 316.67);
                            poster.setLayoutY(85 + (i / 3) * 454);
                            poster.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

                                ColorAdjust colorAdjust = new ColorAdjust();
                                colorAdjust.setBrightness(0.2);
                                poster.setEffect(colorAdjust);

                            });
                            poster.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

                                poster.setEffect(null);

                            });

                            poster.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                                FilmDetails fd = new FilmDetails(f, foodList, username, fullname);

                            });
                            gp.getChildren().addAll(iv, poster);
                        }
                    }
                }
                if (t1.getText().equals("Drama")) {
                    if (DramaList.isEmpty()) {
                        insert(gp);
                    } else {
                        for (int i = 0; i < DramaList.size(); i++) {
                            Film f = DramaList.get(i);
                            Image image1 = new Image(getClass().getResource("Images/frame2.png").toString());
                            ImageView iv = new ImageView(image1);
                            iv.setLayoutX(45 + (i % 3) * 316.67);
                            iv.setLayoutY(60 + (i / 3) * 454);
                            iv.setFitWidth(360);
                            iv.setFitHeight(424);

                            //ImageView poster = new ImageView();
                            ImageView poster = DramaList.get(i).getPoster();
                            poster.setFitWidth(250);
                            poster.setFitHeight(400);
                            poster.setPreserveRatio(true);
                            poster.setLayoutX(103 + (i % 3) * 316.67);
                            poster.setLayoutY(85 + (i / 3) * 454);
                            poster.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

                                ColorAdjust colorAdjust = new ColorAdjust();
                                colorAdjust.setBrightness(0.2);
                                poster.setEffect(colorAdjust);

                            });
                            poster.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

                                poster.setEffect(null);

                            });

                            poster.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                                FilmDetails fd = new FilmDetails(f, foodList, username, fullname);

                            });
                            gp.getChildren().addAll(iv, poster);
                        }
                    }
                }
                if (t1.getText().equals("Horror")) {
                    if (HorrorList.isEmpty()) {
                        insert(gp);
                    } else {
                        for (int i = 0; i < HorrorList.size(); i++) {
                            Film f = HorrorList.get(i);
                            Image image1 = new Image(getClass().getResource("Images/frame2.png").toString());
                            ImageView iv = new ImageView(image1);
                            iv.setLayoutX(45 + (i % 3) * 316.67);
                            iv.setLayoutY(60 + (i / 3) * 454);
                            iv.setFitWidth(360);
                            iv.setFitHeight(424);

                            //ImageView poster = new ImageView();
                            ImageView poster = HorrorList.get(i).getPoster();
                            poster.setFitWidth(250);
                            poster.setFitHeight(400);
                            poster.setPreserveRatio(true);
                            poster.setLayoutX(103 + (i % 3) * 316.67);
                            poster.setLayoutY(85 + (i / 3) * 454);
                            poster.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

                                ColorAdjust colorAdjust = new ColorAdjust();
                                colorAdjust.setBrightness(0.2);
                                poster.setEffect(colorAdjust);

                            });
                            poster.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

                                poster.setEffect(null);

                            });

                            poster.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                                FilmDetails fd = new FilmDetails(f, foodList, username, fullname);

                            });
                            gp.getChildren().addAll(iv, poster);
                        }
                    }
                }
                if (t1.getText().equals("Cartoons")) {
                    if (CartoonList.isEmpty()) {
                        insert(gp);
                    } else {
                        for (int i = 0; i < CartoonList.size(); i++) {
                            Film f = CartoonList.get(i);
                            Image image1 = new Image(getClass().getResource("Images/frame2.png").toString());
                            ImageView iv = new ImageView(image1);
                            iv.setLayoutX(45 + (i % 3) * 316.67);
                            iv.setLayoutY(60 + (i / 3) * 454);
                            iv.setFitWidth(360);
                            iv.setFitHeight(424);

                            //ImageView poster = new ImageView();
                            ImageView poster = CartoonList.get(i).getPoster();
                            poster.setFitWidth(250);
                            poster.setFitHeight(400);
                            poster.setPreserveRatio(true);
                            poster.setLayoutX(103 + (i % 3) * 316.67);
                            poster.setLayoutY(85 + (i / 3) * 454);
                            poster.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {

                                ColorAdjust colorAdjust = new ColorAdjust();
                                colorAdjust.setBrightness(0.2);
                                poster.setEffect(colorAdjust);

                            });
                            poster.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {

                                poster.setEffect(null);

                            });

                            poster.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                                FilmDetails fd = new FilmDetails(f, foodList, username, fullname);

                            });
                            gp.getChildren().addAll(iv, poster);
                        }
                    }
                }
                root.getChildren().addAll(sp);

            }
        }
        );

        for (int i = 0; i < filmList.size(); i++) {
            if (filmList.get(i).getZanr().equals("Comedy")) {
                ComedyList.add(filmList.get(i));
            }
            if (filmList.get(i).getZanr().equals("Horror")) {
                HorrorList.add(filmList.get(i));
            }
            if (filmList.get(i).getZanr().equals("Thriller")) {
                ThrillerList.add(filmList.get(i));
            }
            if (filmList.get(i).getZanr().equals("Drama")) {
                DramaList.add(filmList.get(i));
            }
            if (filmList.get(i).getZanr().equals("Cartoons")) {
                CartoonList.add(filmList.get(i));
            }
        }

        root.getChildren().addAll(this.profilepicture, rec, clip, l, day, time, Calculator, Notepad, Currency, Logout, cc, n, c, lo, createIcon);
        root.getChildren().addAll(iv2, iv3, iv4, menuBar, tabPane);

        Scene scene = new Scene(root, 1300, 600);

        primaryStage.setTitle("Films");
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

    public void insert(Group root) {
        Text text = new Text("We'll be back soon\n   with new films!");
        text.setFill(Color.TRANSPARENT);
        text.setStrokeWidth(3);
        text.setFont(Font.font("arial", 100));

        Rectangle rect = new Rectangle();
        rect.setWidth(text.getBoundsInParent().getWidth() + 15);
        rect.setHeight(text.getBoundsInParent().getHeight() + 150);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(5);
        rect.setBlendMode(BlendMode.ADD);

        StrokeTransition rect_stroke_trans = new StrokeTransition(Duration.millis(1000), rect, Color.RED, Color.GREENYELLOW);
        rect_stroke_trans.setDelay(Duration.millis(70));
        rect_stroke_trans.setCycleCount(Timeline.INDEFINITE);
        rect_stroke_trans.setAutoReverse(true);
        rect_stroke_trans.play();

        Glow glow = new Glow();
        glow.setLevel(0.7);

        Reflection ref = new Reflection();
        ref.setFraction(0.5);
        ref.setInput(glow);

        text.setEffect(ref);
        rect.setEffect(ref);

        StrokeTransition text_stroke_trans = new StrokeTransition(Duration.millis(300), text, Color.RED, Color.GREENYELLOW);
        text_stroke_trans.setDelay(Duration.millis(70));
        text_stroke_trans.setCycleCount(Timeline.INDEFINITE);
        text_stroke_trans.setAutoReverse(true);
        text_stroke_trans.play();

        text.setLayoutX(100);
        text.setLayoutY(225);
        rect.setLayoutX(90);
        rect.setLayoutY(100);

        //U nekim lepsim danima moze se eventualno dodati i rect na root
        root.getChildren().addAll(text);
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
