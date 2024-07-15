package cinema;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ProfileInformation {

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
    private Image profilepicture;
    private static byte[] pic = null;
    private DropShadow ds = new DropShadow();
    private final ObservableList<String> countryList = Stream.of(Locale.getISOCountries())
            .map(locales -> new Locale("", locales))
            .map(Locale::getDisplayCountry)
            .collect(Collectors.toCollection(FXCollections::observableArrayList));

    public ProfileInformation(Admin a, User u, int ind, String fullname, String username, String password, String gender, String city, String dateofbirth, String country, String email, String securityquestion, String answer, Image profilepicture) {
        //Konstruktor se poziva i iz admina i iz usera, pa da bi radilo za oba, prosledjuje se i indikator ind koji je 1 ako je metoda pozvana iz Admin, a 0 ako je pozvana iz User

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

        Group root = new Group();
        Group root1 = new Group();
        Group root2 = new Group();
        Stage primaryStage = new Stage();

        Image image1 = new Image(getClass().getResource("Images/frame1.jpg").toString());
        ImageView iv1 = new ImageView(image1);
        iv1.setLayoutX(30);
        iv1.setLayoutY(100);
        iv1.setFitHeight(280);
        iv1.setFitWidth(280);

        ImageView iv = new ImageView(this.profilepicture);
        iv.setLayoutX(70);
        iv.setLayoutY(140);
        iv.setFitHeight(200);
        iv.setFitWidth(200);
        //iv.setPreserveRatio(true);

        Label t = new Label(this.fullname + ": Profile");
        t.setTextFill(Color.CRIMSON);
        t.setLayoutX(250);
        t.setLayoutY(10);
        t.setFont(Font.font("Palatino Linotype", FontWeight.NORMAL, 35));

        Label gi = new Label("General information");
        gi.setTextFill(Color.BLACK);
        gi.setLayoutX(400);
        gi.setLayoutY(120);
        gi.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        Label fn0 = new Label("Full name: ");
        fn0.setTextFill(Color.BLACK);
        fn0.setLayoutX(400);
        fn0.setLayoutY(180);
        fn0.setFont(Font.font("Arial", FontWeight.NORMAL, 22));

        Label fn = new Label(this.fullname);
        fn.setTextFill(Color.BLACK);
        fn.setLayoutX(600);
        fn.setLayoutY(180);
        fn.setFont(Font.font("Arial", FontWeight.NORMAL, 22));

        Label un0 = new Label("User name: ");
        un0.setTextFill(Color.BLACK);
        un0.setLayoutX(400);
        un0.setLayoutY(230);
        un0.setFont(Font.font("Arial", FontWeight.NORMAL, 22));

        Label un = new Label(this.username);
        un.setTextFill(Color.BLACK);
        un.setLayoutX(600);
        un.setLayoutY(230);
        un.setFont(Font.font("Arial", FontWeight.NORMAL, 22));

        Label gen0 = new Label("Gender: ");
        gen0.setTextFill(Color.BLACK);
        gen0.setLayoutX(400);
        gen0.setLayoutY(280);
        gen0.setFont(Font.font("Arial", FontWeight.NORMAL, 22));

        Label gen = new Label(this.gender);
        gen.setTextFill(Color.BLACK);
        gen.setLayoutX(600);
        gen.setLayoutY(280);
        gen.setFont(Font.font("Arial", FontWeight.NORMAL, 22));

        Label c0 = new Label("City: ");
        c0.setTextFill(Color.BLACK);
        c0.setLayoutX(400);
        c0.setLayoutY(330);
        c0.setFont(Font.font("Arial", FontWeight.NORMAL, 22));

        Label c = new Label(this.city);
        c.setTextFill(Color.BLACK);
        c.setLayoutX(600);
        c.setLayoutY(330);
        c.setFont(Font.font("Arial", FontWeight.NORMAL, 22));

        Label dob0 = new Label("Date of birth: ");
        dob0.setTextFill(Color.BLACK);
        dob0.setLayoutX(400);
        dob0.setLayoutY(380);
        dob0.setFont(Font.font("Arial", FontWeight.NORMAL, 22));

        Label dob = new Label(this.dateofbirth);
        dob.setTextFill(Color.BLACK);
        dob.setLayoutX(600);
        dob.setLayoutY(380);
        dob.setFont(Font.font("Arial", FontWeight.NORMAL, 22));

        Label coun0 = new Label("Country:  ");
        coun0.setTextFill(Color.BLACK);
        coun0.setLayoutX(400);
        coun0.setLayoutY(430);
        coun0.setFont(Font.font("Arial", FontWeight.NORMAL, 22));

        Label coun = new Label(this.country);
        coun.setTextFill(Color.BLACK);
        coun.setLayoutX(600);
        coun.setLayoutY(430);
        coun.setFont(Font.font("Arial", FontWeight.NORMAL, 22));

        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setColor(Color.GRAY);
        Rectangle rec0 = new Rectangle(345, 100, 500, 425);
        rec0.setFill(Color.WHEAT);
        rec0.setArcWidth(20.0);
        rec0.setArcHeight(10.0);
        rec0.setEffect(ds);

        final FileChooser fileChooser = new FileChooser();

        TextField fnEdit = new TextField();
        fnEdit.setLayoutX(1000);
        fnEdit.setLayoutY(180);
        fnEdit.setPromptText(this.fullname);
        fnEdit.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));
        fnEdit.setMinWidth(185);
        fnEdit.setMaxWidth(185);
        fnEdit.setStyle("-fx-background-radius: 15; -fx-text-fill: crimson;");

        TextField unEdit = new TextField();
        unEdit.setLayoutX(1000);
        unEdit.setLayoutY(230);
        unEdit.setPromptText(this.username);
        unEdit.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));
        unEdit.setMinWidth(185);
        unEdit.setMaxWidth(185);
        unEdit.setStyle("-fx-background-radius: 15; -fx-text-fill: crimson;");

        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        RadioButton other = new RadioButton("Other");
        ToggleGroup group = new ToggleGroup();
        male.setToggleGroup(group);
        female.setToggleGroup(group);
        other.setToggleGroup(group);
        male.setLayoutX(1000);
        male.setLayoutY(280);
        female.setLayoutX(1072);
        female.setLayoutY(280);
        other.setLayoutX(1161);
        other.setLayoutY(280);
        male.setFont(Font.font("SansSerif", FontPosture.ITALIC, 17));
        female.setFont(Font.font("SansSerif", FontPosture.ITALIC, 17));
        other.setFont(Font.font("SansSerif", FontPosture.ITALIC, 17));
        //((RadioButton)group.getSelectedToggle()).getText(); OVAKO SE DOBIJA TEKST

        TextField cEdit = new TextField();
        cEdit.setLayoutX(1000);
        cEdit.setLayoutY(330);
        cEdit.setPromptText(this.city);
        cEdit.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));
        cEdit.setMinWidth(185);
        cEdit.setMaxWidth(185);
        cEdit.setStyle("-fx-background-radius: 15; -fx-text-fill: crimson;");

        DatePicker dp = new DatePicker();
        dp.setLayoutX(1000);
        dp.setLayoutY(380);
        dp.setMinWidth(185);
        dp.setMaxWidth(185);
        dp.setMinHeight(40);
        dp.setMaxHeight(40);
        dp.setPromptText(this.dateofbirth);

        ComboBox<String> counEdit = new ComboBox<>(countryList);
        counEdit.setPromptText(this.country);
        counEdit.setLayoutX(1000);
        counEdit.setLayoutY(430);
        counEdit.setMinSize(185, 40);
        counEdit.setMaxSize(185, 40);
        counEdit.setStyle("-fx-background-radius: 15; -fx-text-fill: crimson;");

        Button openButton = new Button("Change Picture");
        openButton.setMinSize(280, 45);
        openButton.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");
        openButton.setLayoutX(30);
        openButton.setLayoutY(400);

        openButton.setOnAction(
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
                        pic = bos.toByteArray();
                        System.out.println(pic);

                    } catch (IOException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    ImageView iv = new ImageView(image);
                    iv.setFitHeight(200);
                    iv.setFitWidth(200);
                    iv.setLayoutX(70);
                    iv.setLayoutY(140);
                    root.getChildren().addAll(iv);

                    if (ind == 0) {
                        u.setProfilepicture(new ImageView(image));
                    } else {
                        a.setProfilepicture(new ImageView(image));
                    }

                    String query = "UPDATE User SET ProfilePicture = ? WHERE UserName = ?";
                    Connection con = SqliteConnection.Connector();
                    PreparedStatement ps = null;
                    try {
                        ps = con.prepareStatement(query);
                        ps.setBytes(1, pic);
                        ps.setString(2, username);
                        ps.executeUpdate();
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

        Button edit = new Button("Edit your profile");
        edit.setMinSize(280, 45);
        edit.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");
        edit.setLayoutX(30);
        edit.setLayoutY(470);

        Button sc = new Button("Save changes");
        sc.setLayoutX(525);
        sc.setLayoutY(700);
        sc.setMaxWidth(150);
        sc.setMinWidth(150);
        sc.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-color: royalblue; -fx-text-fill: #000000");
        sc.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            sc.setStyle("-fx-font-size: 1.5em; -fx-background-color: darkblue; -fx-font-weight: bold;  -fx-text-fill: white");

        });
        sc.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            sc.setStyle("-fx-font-size: 1.5em; -fx-font-weight: bold; -fx-background-color: royalblue; -fx-text-fill: #000000");

        });
        sc.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            try {

                Connection con = SqliteConnection.Connector();
                String sql = "UPDATE User SET FullName = ?, UserName = ?, Gender = ?, City = ?, DateOfBirth = ?, Country = ? WHERE UserName = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, (fnEdit.getText().equals("")) ? (this.fullname) : (fnEdit.getText()));
                ps.setString(2, (unEdit.getText().equals("")) ? (this.username) : (unEdit.getText()));
                ps.setString(3, (((RadioButton) group.getSelectedToggle()) == null) ? (this.gender) : (((RadioButton) group.getSelectedToggle()).getText()));
                ps.setString(4, (cEdit.getText().equals("")) ? (this.city) : (cEdit.getText()));
                ps.setString(5, (dp.getValue() == null) ? (this.dateofbirth) : (dp.getValue().toString()));
                ps.setString(6, (counEdit.getValue() == null) ? (this.country) : (counEdit.getValue()));
                ps.setString(7, this.username);
                ps.executeUpdate();
                if (ind == 1) {
                    a.setFullname((fnEdit.getText().equals("")) ? (this.fullname) : (fnEdit.getText()));
                    a.setUsername((unEdit.getText().equals("")) ? (this.username) : (unEdit.getText()));
                    a.setGender((((RadioButton) group.getSelectedToggle()) == null) ? (this.gender) : (((RadioButton) group.getSelectedToggle()).getText()));
                    a.setCity((cEdit.getText().equals("")) ? (this.city) : (cEdit.getText()));
                    a.setDateofbirth((dp.getValue() == null) ? (this.dateofbirth) : (dp.getValue().toString()));
                    a.setCountry((counEdit.getValue() == null) ? (this.country) : (counEdit.getValue()));
                } else {
                    u.setFullname((fnEdit.getText().equals("")) ? (this.fullname) : (fnEdit.getText()));
                    u.setUsername((unEdit.getText().equals("")) ? (this.username) : (unEdit.getText()));
                    u.setGender((((RadioButton) group.getSelectedToggle()) == null) ? (this.gender) : (((RadioButton) group.getSelectedToggle()).getText()));
                    u.setCity((cEdit.getText().equals("")) ? (this.city) : (cEdit.getText()));
                    u.setDateofbirth((dp.getValue() == null) ? (this.dateofbirth) : (dp.getValue().toString()));
                    u.setCountry((counEdit.getValue() == null) ? (this.country) : (counEdit.getValue()));
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Editing account successful");
                alert.setHeaderText("Editing account successful");
                alert.setContentText("You have successfully edited your profile.");
                ButtonType cancelButton = new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(cancelButton);
                alert.showAndWait().ifPresent(type -> {
                    if (type.getText().toString().equals("Ok")) {
                        primaryStage.close();

                    }
                });

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Editing account failed");
                alert.setHeaderText("This username is already taken!");
                alert.setContentText("Try with another username.");
                alert.showAndWait();
            }

        });

        edit.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {

                edit.setDisable(true);
                TranslateTransition tt1 = new TranslateTransition();
                tt1.setDuration(Duration.millis(500));
                tt1.setNode(fn);
                tt1.setToY(1000);
                tt1.play();
                tt1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        TranslateTransition tt2 = new TranslateTransition();
                        tt2.setDuration(Duration.millis(500));
                        tt2.setNode(un);
                        tt2.setToY(1000);
                        tt2.play();
                        tt2.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                TranslateTransition tt3 = new TranslateTransition();
                                tt3.setDuration(Duration.millis(500));
                                tt3.setNode(gen);
                                tt3.setToY(1000);
                                tt3.play();
                                tt3.setOnFinished(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {

                                        TranslateTransition tt4 = new TranslateTransition();
                                        tt4.setDuration(Duration.millis(500));
                                        tt4.setNode(c);
                                        tt4.setToY(1000);
                                        tt4.play();
                                        tt4.setOnFinished(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {

                                                TranslateTransition tt5 = new TranslateTransition();
                                                tt5.setDuration(Duration.millis(500));
                                                tt5.setNode(dob);
                                                tt5.setToY(1000);
                                                tt5.play();
                                                tt5.setOnFinished(new EventHandler<ActionEvent>() {
                                                    @Override
                                                    public void handle(ActionEvent event) {

                                                        TranslateTransition tt6 = new TranslateTransition();
                                                        tt6.setDuration(Duration.millis(500));
                                                        tt6.setNode(coun);
                                                        tt6.setToY(1000);
                                                        tt6.play();
                                                        tt6.setOnFinished(new EventHandler<ActionEvent>() {
                                                            @Override
                                                            public void handle(ActionEvent event) {

                                                                TranslateTransition tt7 = new TranslateTransition();
                                                                tt7.setDuration(Duration.millis(500));
                                                                tt7.setNode(fnEdit);
                                                                tt7.setToX(-400);
                                                                tt7.play();
                                                                tt7.setOnFinished(new EventHandler<ActionEvent>() {
                                                                    @Override
                                                                    public void handle(ActionEvent event) {

                                                                        TranslateTransition tt8 = new TranslateTransition();
                                                                        tt8.setDuration(Duration.millis(500));
                                                                        tt8.setNode(unEdit);
                                                                        tt8.setToX(-400);
                                                                        tt8.play();
                                                                        tt8.setOnFinished(new EventHandler<ActionEvent>() {
                                                                            @Override
                                                                            public void handle(ActionEvent event) {

                                                                                TranslateTransition tt9 = new TranslateTransition();
                                                                                tt9.setDuration(Duration.millis(500));
                                                                                tt9.setNode(male);
                                                                                tt9.setToX(-400);
                                                                                tt9.play();
                                                                                TranslateTransition tt91 = new TranslateTransition();
                                                                                tt91.setDuration(Duration.millis(500));
                                                                                tt91.setNode(female);
                                                                                tt91.setToX(-400);
                                                                                tt91.play();
                                                                                TranslateTransition tt92 = new TranslateTransition();
                                                                                tt92.setDuration(Duration.millis(500));
                                                                                tt92.setNode(other);
                                                                                tt92.setToX(-400);
                                                                                tt92.play();
                                                                                tt92.setOnFinished(new EventHandler<ActionEvent>() {
                                                                                    @Override
                                                                                    public void handle(ActionEvent event) {

                                                                                        TranslateTransition tt10 = new TranslateTransition();
                                                                                        tt10.setDuration(Duration.millis(500));
                                                                                        tt10.setNode(cEdit);
                                                                                        tt10.setToX(-400);
                                                                                        tt10.play();
                                                                                        tt10.setOnFinished(new EventHandler<ActionEvent>() {
                                                                                            @Override
                                                                                            public void handle(ActionEvent event) {

                                                                                                TranslateTransition tt11 = new TranslateTransition();
                                                                                                tt11.setDuration(Duration.millis(500));
                                                                                                tt11.setNode(dp);
                                                                                                tt11.setToX(-400);
                                                                                                tt11.play();
                                                                                                tt11.setOnFinished(new EventHandler<ActionEvent>() {
                                                                                                    @Override
                                                                                                    public void handle(ActionEvent event) {

                                                                                                        TranslateTransition tt12 = new TranslateTransition();
                                                                                                        tt12.setDuration(Duration.millis(500));
                                                                                                        tt12.setNode(counEdit);
                                                                                                        tt12.setToX(-400);
                                                                                                        tt12.play();
                                                                                                        tt12.setOnFinished(new EventHandler<ActionEvent>() {
                                                                                                            @Override
                                                                                                            public void handle(ActionEvent event) {

                                                                                                                TranslateTransition tt13 = new TranslateTransition();
                                                                                                                tt13.setDuration(Duration.millis(500));
                                                                                                                tt13.setNode(sc);
                                                                                                                tt13.setToY(-140);
                                                                                                                tt13.play();
                                                                                                                tt13.setOnFinished(new EventHandler<ActionEvent>() {
                                                                                                                    @Override
                                                                                                                    public void handle(ActionEvent event) {

                                                                                                                        fn.setLayoutY(180);
                                                                                                                        un.setLayoutX(300);
                                                                                                                        un.setLayoutY(230);
                                                                                                                        gen.setLayoutX(1000);
                                                                                                                        gen.setLayoutY(280);
                                                                                                                        c.setLayoutX(1000);
                                                                                                                        c.setLayoutY(330);
                                                                                                                        dob.setLayoutX(1000);
                                                                                                                        dob.setLayoutY(380);
                                                                                                                        coun.setLayoutX(1000);
                                                                                                                        coun.setLayoutY(430);

                                                                                                                    }
                                                                                                                });
                                                                                                            }
                                                                                                        });

                                                                                                    }
                                                                                                });

                                                                                            }
                                                                                        });

                                                                                    }
                                                                                });

                                                                            }
                                                                        });

                                                                    }
                                                                });
                                                            }
                                                        });

                                                    }
                                                });

                                            }
                                        });

                                    }
                                });

                            }
                        });

                    }
                });

            }
        });

        root.getChildren().addAll(t, rec0, iv1, iv, openButton, edit, root1, sc, root2);
        root1.getChildren().addAll(gi, fn0, fn, un0, un, gen0, gen, c0, c, dob0, dob, coun0, coun);
        root2.getChildren().addAll(fnEdit, unEdit, male, female, other, cEdit, dp, counEdit);
        Scene scene = new Scene(root, 875, 615);
        primaryStage.setTitle("Profile Information");
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
        primaryStage.setResizable(true);
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

    public ProfileInformation() {

    }
}
