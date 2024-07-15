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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Manage {

    private ArrayList<Film> films;
    private ArrayList<Food> food;
    private ArrayList<ReadFromTermin> termins = new ArrayList<>();
    private ArrayList<ReadFromUser> users = new ArrayList<>();
    private ObservableList<Reservation> reservationList;
    private TableView<Reservation> reservationTable = new TableView<>();
    private ObservableList<Film> filmList;
    private TableView<Film> filmTable = new TableView<>();
    private ObservableList<Food> foodList;
    private TableView<Food> foodTable = new TableView<>();
    private ObservableList<ReadFromTermin> terminList;
    private TableView<ReadFromTermin> terminTable = new TableView<>();
    private ObservableList<ReadFromUser> userList;
    private TableView<ReadFromUser> userTable = new TableView<>();
    private int t = 0;
    private TextArea textArea = new TextArea();
    private static byte[] pic = null;
    private int idHra = 0, idU = 0, idFil = 0;
    //idU je zapravo nepotreban, jer administrator ne sme da menja profilnu sliku korisnika.

    public Manage(List<Film> filmList, List<Food> foodList, List<ReadFromUser> userList) throws SQLException {

        this.films = new ArrayList<>(filmList);
        this.food = new ArrayList<>(foodList);
        this.users = new ArrayList<>(userList);

        for (int i = 0; i < filmList.size(); i++) {
            for (int j = 0; j < filmList.get(i).getTerminList().size(); j++) {
                termins.add(filmList.get(i).getTerminList().get(j));
            }
        }

        Group root = new Group();
        Stage primaryStage = new Stage();

        Reservation r = new Reservation();
        this.reservationList = FXCollections.observableArrayList(r.getList());
        reservationTable.setItems(reservationList);
        this.filmList = FXCollections.observableArrayList(films);
        filmTable.setItems(this.filmList);
        this.foodList = FXCollections.observableArrayList(food);
        foodTable.setItems(this.foodList);
        this.terminList = FXCollections.observableArrayList(termins);
        terminTable.setItems(this.terminList);
        this.userList = FXCollections.observableArrayList(users);
        userTable.setItems(this.userList);

        TableColumn<Reservation, Integer> idRez = new TableColumn<>("IdRez");
        idRez.setMinWidth(143);
        idRez.setCellValueFactory(new PropertyValueFactory<>("idRez"));

        TableColumn<Reservation, String> username = new TableColumn<>("Username");
        username.setMinWidth(143);
        username.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Reservation, String> sifra = new TableColumn<>("Code");
        sifra.setMinWidth(143);
        sifra.setCellValueFactory(new PropertyValueFactory<>("sifra"));

        TableColumn<Reservation, Integer> screen = new TableColumn<>("Screen");
        screen.setMinWidth(143);
        screen.setCellValueFactory(new PropertyValueFactory<>("screen"));

        TableColumn<Reservation, String> pocetak = new TableColumn<>("Starts");
        pocetak.setMinWidth(143);
        pocetak.setCellValueFactory(new PropertyValueFactory<>("pocetak"));

        TableColumn<Reservation, String> kraj = new TableColumn<>("Ends");
        kraj.setMinWidth(143);
        kraj.setCellValueFactory(new PropertyValueFactory<>("kraj"));

        TableColumn<Reservation, String> datum = new TableColumn<>("Date");
        datum.setMinWidth(143);
        datum.setCellValueFactory(new PropertyValueFactory<>("datum"));

        TableColumn<Film, Integer> idFilm = new TableColumn<>("IdFilm");
        idFilm.setMinWidth(91);
        idFilm.setCellValueFactory(new PropertyValueFactory<>("idFilm"));

        TableColumn<Film, String> ime = new TableColumn<>("Name");
        ime.setMinWidth(91);
        ime.setCellValueFactory(new PropertyValueFactory<>("ime"));

        TableColumn<Film, String> opis = new TableColumn<>("Short description");
        opis.setMinWidth(91);
        opis.setCellValueFactory(new PropertyValueFactory<>("kratakOpis"));

        TableColumn<Film, String> trejler = new TableColumn<>("Trailer");
        trejler.setMinWidth(91);
        trejler.setCellValueFactory(new PropertyValueFactory<>("trejler"));

        TableColumn<Film, String> zanr = new TableColumn<>("Genre");
        zanr.setMinWidth(91);
        zanr.setCellValueFactory(new PropertyValueFactory<>("zanr"));

        TableColumn<Film, String> duzina = new TableColumn<>("Duration");
        duzina.setMinWidth(91);
        duzina.setCellValueFactory(new PropertyValueFactory<>("duzina"));

        TableColumn<Film, String> dodao = new TableColumn<>("Added by");
        dodao.setMinWidth(91);
        dodao.setCellValueFactory(new PropertyValueFactory<>("dodao"));

        TableColumn<Film, String> datumDodavanja = new TableColumn<>("Date added");
        datumDodavanja.setMinWidth(91);
        datumDodavanja.setCellValueFactory(new PropertyValueFactory<>("datumDodavanja"));

        TableColumn<Film, String> rating = new TableColumn<>("IMDB Rating");
        rating.setMinWidth(91);
        rating.setCellValueFactory(new PropertyValueFactory<>("IMDBrating"));

        TableColumn<Film, Double> cenaKarteOdrasli = new TableColumn<>("Adult ticket cost");
        cenaKarteOdrasli.setMinWidth(91);
        cenaKarteOdrasli.setCellValueFactory(new PropertyValueFactory<>("CenaKarteO"));

        TableColumn<Film, Double> cenaKarteDeca = new TableColumn<>("Child ticket cost");
        cenaKarteDeca.setMinWidth(91);
        cenaKarteDeca.setCellValueFactory(new PropertyValueFactory<>("CenaKarteD"));

        TableColumn<Food, Integer> idHrana = new TableColumn<>("IdFood");
        idHrana.setMinWidth(143);
        idHrana.setCellValueFactory(new PropertyValueFactory<>("idHrana"));

        TableColumn<Food, String> imeHrane = new TableColumn<>("Name");
        imeHrane.setMinWidth(143);
        imeHrane.setCellValueFactory(new PropertyValueFactory<>("ime"));

        TableColumn<Food, String> kategorija = new TableColumn<>("Category");
        kategorija.setMinWidth(143);
        kategorija.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Food, String> cena = new TableColumn<>("Price");
        cena.setMinWidth(143);
        cena.setCellValueFactory(new PropertyValueFactory<>("cena"));

        TableColumn<Food, String> opisHrane = new TableColumn<>("Short Description");
        opisHrane.setMinWidth(143);
        opisHrane.setCellValueFactory(new PropertyValueFactory<>("opis"));

        TableColumn<Food, String> dodao1 = new TableColumn<>("Added by");
        dodao1.setMinWidth(143);
        dodao1.setCellValueFactory(new PropertyValueFactory<>("dodao"));

        TableColumn<Food, String> datum1 = new TableColumn<>("Date added");
        datum1.setMinWidth(143);
        datum1.setCellValueFactory(new PropertyValueFactory<>("datumDodavanja"));

        TableColumn<ReadFromTermin, Integer> idTermin = new TableColumn<>("IdTermin");
        idTermin.setMinWidth(143);
        idTermin.setCellValueFactory(new PropertyValueFactory<>("idTermin"));

        TableColumn<ReadFromTermin, String> imefilma = new TableColumn<>("Film name");
        imefilma.setMinWidth(143);
        imefilma.setCellValueFactory(new PropertyValueFactory<>("imeFilma"));

        TableColumn<ReadFromTermin, String> datum2 = new TableColumn<>("Date");
        datum2.setMinWidth(143);
        datum2.setCellValueFactory(new PropertyValueFactory<>("datum"));

        TableColumn<ReadFromTermin, String> pocetak2 = new TableColumn<>("Starts");
        pocetak2.setMinWidth(143);
        pocetak2.setCellValueFactory(new PropertyValueFactory<>("pocetak"));

        TableColumn<ReadFromTermin, String> kraj2 = new TableColumn<>("Ends");
        kraj2.setMinWidth(143);
        kraj2.setCellValueFactory(new PropertyValueFactory<>("kraj"));

        TableColumn<ReadFromTermin, String> dodao2 = new TableColumn<>("Added by");
        dodao2.setMinWidth(143);
        dodao2.setCellValueFactory(new PropertyValueFactory<>("dodao"));

        TableColumn<ReadFromTermin, Integer> sala = new TableColumn<>("Screen");
        sala.setMinWidth(143);
        sala.setCellValueFactory(new PropertyValueFactory<>("sala"));

        TableColumn<ReadFromUser, Integer> idUser = new TableColumn<>("IdUser");
        idUser.setMinWidth(125);
        idUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));

        TableColumn<ReadFromUser, String> fullname = new TableColumn<>("Full name");
        fullname.setMinWidth(125);
        fullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));

        TableColumn<ReadFromUser, String> username1 = new TableColumn<>("Username");
        username1.setMinWidth(125);
        username1.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<ReadFromUser, String> pol = new TableColumn<>("Gender");
        pol.setMinWidth(125);
        pol.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<ReadFromUser, String> grad = new TableColumn<>("City");
        grad.setMinWidth(125);
        grad.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<ReadFromUser, String> drodj = new TableColumn<>("Date of birth");
        drodj.setMinWidth(125);
        drodj.setCellValueFactory(new PropertyValueFactory<>("dateofbirth"));

        TableColumn<ReadFromUser, String> drzava = new TableColumn<>("Country");
        drzava.setMinWidth(125);
        drzava.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<ReadFromUser, Integer> admin = new TableColumn<>("Admin");
        admin.setMinWidth(125);
        admin.setCellValueFactory(new PropertyValueFactory<>("admin"));

        foodTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (foodTable.getSelectionModel().getSelectedItem() != null) {

                    TableViewSelectionModel selectionModel = foodTable.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    idHra = foodTable.getItems().get(tablePosition.getRow()).getIdHrana();
                    Image im = foodTable.getItems().get(tablePosition.getRow()).getSlika().getImage();
                    ImageView iv = new ImageView(im);
                    iv.setFitWidth(225);
                    iv.setFitHeight(225);
                    iv.setLayoutX(1055);
                    iv.setLayoutY(512);
                    Rectangle rec = new Rectangle(1055, 512, 225, 225);
                    rec.setFill(Color.WHITE);
                    root.getChildren().addAll(rec, iv);
                }
            }
        });

        filmTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (filmTable.getSelectionModel().getSelectedItem() != null) {
                    TableViewSelectionModel selectionModel = filmTable.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    idFil = filmTable.getItems().get(tablePosition.getRow()).getIdFilm();
                    Image im = filmTable.getItems().get(tablePosition.getRow()).getPoster().getImage();
                    ImageView iv = new ImageView(im);
                    iv.setFitWidth(225);
                    iv.setFitHeight(225);
                    iv.setLayoutX(1055);
                    iv.setLayoutY(512);
                    Rectangle rec = new Rectangle(1055, 512, 225, 225);
                    rec.setFill(Color.WHITE);
                    root.getChildren().addAll(rec, iv);
                }
            }
        });

        userTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (userTable.getSelectionModel().getSelectedItem() != null) {
                    TableViewSelectionModel selectionModel = userTable.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    idU = userTable.getItems().get(tablePosition.getRow()).getIdUser();
                    Image im = userTable.getItems().get(tablePosition.getRow()).getIv().getImage();
                    ImageView iv = new ImageView(im);
                    iv.setFitWidth(225);
                    iv.setFitHeight(225);
                    iv.setLayoutX(1055);
                    iv.setLayoutY(512);
                    Rectangle rec = new Rectangle(1055, 512, 225, 225);
                    rec.setFill(Color.WHITE);
                    root.getChildren().addAll(rec, iv);
                }
            }
        });

        reservationTable.getColumns().addAll(idRez, username, sifra, screen, pocetak, kraj, datum);
        this.filmTable.getColumns().addAll(idFilm, ime, opis, trejler, zanr, duzina, dodao, datumDodavanja, rating, cenaKarteOdrasli, cenaKarteDeca);
        this.foodTable.getColumns().addAll(idHrana, imeHrane, kategorija, cena, opisHrane, dodao1, datum1);
        this.terminTable.getColumns().addAll(idTermin, imefilma, datum2, pocetak2, kraj2, dodao2, sala);
        this.userTable.getColumns().addAll(idUser, fullname, username1, pol, grad, drodj, drzava, admin);
        reservationTable.setMinWidth(1000);
        filmTable.setMinWidth(1000);
        foodTable.setMinWidth(1000);
        terminTable.setMinWidth(1000);
        userTable.setMinWidth(1000);

        VBox vbox = new VBox();
        vbox.setLayoutX(20);
        vbox.setLayoutY(20);

        Line line = new Line(0, 500, 1500, 500);
        line.setStrokeWidth(1);
        line.setStroke(Color.BLACK);

        textArea.setMaxSize(500, 200);
        textArea.setMinSize(500, 200);
        textArea.setWrapText(true);
        textArea.setPromptText("Result text...");
        textArea.setLayoutX(20);
        textArea.setLayoutY(525);
        textArea.setFont(Font.font("Lucida Console", FontWeight.BOLD, 18));
        textArea.setEditable(false);

        Image image2 = new Image(getClass().getResource("Images/arrow.png").toString());
        ImageView iv = new ImageView(image2);
        iv.setFitHeight(20);
        iv.setFitWidth(20);

        final FileChooser fileChooser = new FileChooser();

        Button openButton = new Button("Change Picture", iv);
        openButton.setMinSize(150, 45);
        openButton.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

        openButton.setLayoutX(870);
        openButton.setLayoutY(525);

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

                    } catch (IOException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    ImageView iv = new ImageView(image);
                    iv.setFitHeight(225);
                    iv.setFitWidth(225);
                    iv.setLayoutX(1055);
                    iv.setLayoutY(512);
                    root.getChildren().addAll(iv);
                    if (t == 2) {
                        String query = "UPDATE Film SET Poster = ? WHERE IdFil = ?";
                        Connection con = SqliteConnection.Connector();
                        PreparedStatement ps = null;
                        try {
                            ps = con.prepareStatement(query);
                            ps.setBytes(1, pic);
                            ps.setInt(2, idFil);
                            ps.executeUpdate();
                            textArea.setText(textArea.getText() + "Film poster successfully changed.\n");
                            con.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (t == 3) {
                        String query = "UPDATE Hrana SET Slika = ? WHERE IdHra = ?";
                        Connection con = SqliteConnection.Connector();
                        PreparedStatement ps = null;
                        try {
                            ps = con.prepareStatement(query);
                            ps.setBytes(1, pic);
                            ps.setInt(2, idHra);
                            ps.executeUpdate();
                            textArea.setText(textArea.getText() + "Food picture successfully changed.\n");
                            con.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            }
        });

        Button update = new Button("Update", GlyphsDude.createIcon(FontAwesomeIcons.REFRESH, "20px"));

        Button resBut = new Button("Load reservations", GlyphsDude.createIcon(FontAwesomeIcons.BOOK, "20px"));
        resBut.setLayoutX(1050);
        resBut.setLayoutY(50);
        resBut.setMinWidth(200);
        resBut.setMaxWidth(200);
        resBut.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

        resBut.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            vbox.getChildren().clear();
            vbox.getChildren().add(reservationTable);
            textArea.setText(textArea.getText() + "Reservation table loaded.\n");
            t = 1;
            update.setDisable(false);
            openButton.setDisable(true);
            Rectangle re1 = new Rectangle(1055, 512, 225, 225);
            re1.setFill(Color.WHEAT);
            root.getChildren().add(re1);
        });

        Button filmBut = new Button("Load films", GlyphsDude.createIcon(FontAwesomeIcons.FILM, "20px"));
        filmBut.setLayoutX(1050);
        filmBut.setLayoutY(125);
        filmBut.setMinWidth(200);
        filmBut.setMaxWidth(200);
        filmBut.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

        filmBut.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            vbox.getChildren().clear();
            vbox.getChildren().add(this.filmTable);
            textArea.setText(textArea.getText() + "Film table loaded.\n");
            t = 2;
            update.setDisable(false);
            openButton.setDisable(false);
            Rectangle re1 = new Rectangle(1055, 512, 225, 225);
            re1.setFill(Color.WHEAT);
            Label dim = new Label("Nothing\nselected");
            dim.setTextFill(Color.DARKORANGE);
            dim.setLayoutX(1100);
            dim.setLayoutY(585);
            dim.setFont(Font.font("SansSerif", FontWeight.BOLD, FontPosture.ITALIC, 30));
            root.getChildren().addAll(re1, dim);
            idFil = 0;
        });

        Image i = new Image(getClass().getResource("Images/ff.png").toString());
        ImageView iv0 = new ImageView(i);
        iv0.setFitHeight(30);
        iv0.setFitWidth(30);
        Button foodBut = new Button("Load food", iv0);
        foodBut.setLayoutX(1050);
        foodBut.setLayoutY(200);
        foodBut.setMinWidth(200);
        foodBut.setMaxWidth(200);
        foodBut.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

        foodBut.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            vbox.getChildren().clear();
            vbox.getChildren().add(this.foodTable);
            textArea.setText(textArea.getText() + "Food table loaded.\n");
            t = 3;
            update.setDisable(false);
            openButton.setDisable(false);
            Rectangle re1 = new Rectangle(1055, 512, 225, 225);
            re1.setFill(Color.WHEAT);
            Label dim = new Label("Nothing\nselected");
            dim.setTextFill(Color.DARKORANGE);
            dim.setLayoutX(1100);
            dim.setLayoutY(585);
            dim.setFont(Font.font("SansSerif", FontWeight.BOLD, FontPosture.ITALIC, 30));
            root.getChildren().addAll(re1, dim);
            idHra = 0;
        });

        Button terBut = new Button("Load termins", GlyphsDude.createIcon(FontAwesomeIcons.TABLE, "20px"));
        terBut.setLayoutX(1050);
        terBut.setLayoutY(275);
        terBut.setMinWidth(200);
        terBut.setMaxWidth(200);
        terBut.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

        terBut.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            vbox.getChildren().clear();
            vbox.getChildren().add(this.terminTable);
            textArea.setText(textArea.getText() + "Termin table loaded.\n");
            t = 4;
            update.setDisable(false);
            openButton.setDisable(true);
            Rectangle re1 = new Rectangle(1055, 512, 225, 225);
            re1.setFill(Color.WHEAT);
            root.getChildren().add(re1);
        });

        Button userBut = new Button("Load users", GlyphsDude.createIcon(FontAwesomeIcons.USER, "20px"));
        userBut.setLayoutX(1050);
        userBut.setLayoutY(350);
        userBut.setMinWidth(200);
        userBut.setMaxWidth(200);
        userBut.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

        userBut.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            vbox.getChildren().clear();
            vbox.getChildren().add(this.userTable);
            textArea.setText(textArea.getText() + "Users table loaded.\n");
            t = 5;
            update.setDisable(true);
            openButton.setDisable(false);
            Rectangle re1 = new Rectangle(1055, 512, 225, 225);
            re1.setFill(Color.WHEAT);
            Label dim = new Label("Nothing\nselected");
            dim.setTextFill(Color.DARKORANGE);
            dim.setLayoutX(1100);
            dim.setLayoutY(585);
            dim.setFont(Font.font("SansSerif", FontWeight.BOLD, FontPosture.ITALIC, 30));
            root.getChildren().addAll(re1, dim);
            idU = 0;
        });

        update.setLayoutX(20);
        update.setLayoutY(445);
        update.setMinWidth(475);
        update.setMaxWidth(475);
        update.setStyle("-fx-font-size: 2em; -fx-background-color: orange; -fx-font-weight: bold; -fx-text-fill: white");

        update.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            if (t == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("No table selected!");
                alert.setContentText("You need to load a table first.");
                alert.showAndWait();
            } else {
                delete(true);
            }

        });
        update.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            update.setStyle("-fx-font-size: 2em; -fx-background-color: moccasin; -fx-font-weight: bold; -fx-text-fill: white");
        });
        update.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            update.setStyle("-fx-font-size: 2em; -fx-background-color: orange; -fx-font-weight: bold; -fx-text-fill: white");
        });

        Button delete = new Button("Delete", GlyphsDude.createIcon(FontAwesomeIcons.TRASH, "20px"));
        delete.setLayoutX(545);
        delete.setLayoutY(445);
        delete.setMinWidth(475);
        delete.setMaxWidth(475);
        delete.setStyle("-fx-font-size: 2em; -fx-background-color: royalblue; -fx-font-weight: bold; -fx-text-fill: white");

        delete.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            if (t == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("No table selected!");
                alert.setContentText("You need to load a table first.");
                alert.showAndWait();
            } else {
                delete(false);
            }

        });
        delete.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            delete.setStyle("-fx-font-size: 2em; -fx-background-color: lightskyblue; -fx-font-weight: bold; -fx-text-fill: white");
        });
        delete.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            delete.setStyle("-fx-font-size: 2em; -fx-background-color: royalblue; -fx-font-weight: bold; -fx-text-fill: white");
        });
        Rectangle rec = new Rectangle(20, 20, 1000, 400);
        rec.setFill(Color.WHEAT);
        Label l = new Label("Load your table");
        l.setTextFill(Color.BLACK);
        l.setLayoutX(400);
        l.setLayoutY(200);
        l.setFont(Font.font(null, FontWeight.BOLD, 30));
        Button reset = new Button("Reset output", GlyphsDude.createIcon(FontAwesomeIcons.REMOVE, "20px"));
        reset.setLayoutX(550);
        reset.setLayoutY(525);
        reset.setStyle("-fx-font-size: 1.75em; -fx-font-weight: bold; -fx-text-fill: #000000");

        reset.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            textArea.setText("");
        });

        Rectangle re1 = new Rectangle(750, 300, 150, 150);
        re1.setFill(Color.LIGHTGRAY);

        root.getChildren().addAll(rec, l, vbox, line, resBut, filmBut, foodBut, terBut, userBut, update, delete, textArea, reset, openButton);
        Scene scene = new Scene(root, 1300, 750);
        primaryStage.setTitle("Manage reservations, films, termins, users, food...");
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }

    public void delete(boolean editable) {
        if (t == 1) {
            Group root = new Group();
            Stage primaryStage = new Stage();

            Label l = new Label("Enter ID of the reservation: ");
            l.setTextFill(Color.BLACK);
            l.setLayoutX(10);
            l.setLayoutY(10);
            l.setFont(Font.font(null, FontWeight.BOLD, 18));

            TextField id = new TextField();
            id.setLayoutX(255);
            id.setLayoutY(2);
            id.setFont(Font.font(null, FontWeight.BOLD, 15));
            id.setMinWidth(60);
            id.setMaxWidth(60);

            Line line1 = new Line(0, 50, 325, 50);
            line1.setStrokeWidth(1);
            line1.setStroke(Color.BLACK);

            Line line2 = new Line(0, 230, 325, 230);
            line2.setStrokeWidth(1);
            line2.setStroke(Color.BLACK);

            TextField username = new TextField();
            username.setLayoutX(15);
            username.setLayoutY(75);
            username.setFont(Font.font(null, FontWeight.BOLD, 18));
            username.setPromptText("Username");
            username.setEditable(editable);
            username.setMaxWidth(125);
            username.setMinWidth(125);
            username.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField code = new TextField();
            code.setLayoutX(15);
            code.setLayoutY(125);
            code.setFont(Font.font(null, FontWeight.BOLD, 18));
            code.setPromptText("Code");
            code.setEditable(editable);
            code.setMaxWidth(125);
            code.setMinWidth(125);
            code.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField screen = new TextField();
            screen.setLayoutX(15);
            screen.setLayoutY(175);
            screen.setFont(Font.font(null, FontWeight.BOLD, 18));
            screen.setPromptText("Screen");
            screen.setEditable(editable);
            screen.setMaxWidth(125);
            screen.setMinWidth(125);
            screen.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField starts = new TextField();
            starts.setLayoutX(185);
            starts.setLayoutY(75);
            starts.setFont(Font.font(null, FontWeight.BOLD, 18));
            starts.setPromptText("Starts");
            starts.setEditable(editable);
            starts.setMaxWidth(125);
            starts.setMinWidth(125);
            starts.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField ends = new TextField();
            ends.setLayoutX(185);
            ends.setLayoutY(125);
            ends.setFont(Font.font(null, FontWeight.BOLD, 18));
            ends.setPromptText("Ends");
            ends.setEditable(editable);
            ends.setMaxWidth(125);
            ends.setMinWidth(125);
            ends.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField date = new TextField();
            date.setLayoutX(185);
            date.setLayoutY(175);
            date.setFont(Font.font(null, FontWeight.BOLD, 18));
            date.setPromptText("Date");
            date.setEditable(editable);
            date.setMaxWidth(125);
            date.setMinWidth(125);
            date.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            Button del = new Button("Delete", GlyphsDude.createIcon(FontAwesomeIcons.TRASH, "20px"));
            del.setLayoutX(37);
            del.setLayoutY(255);
            del.setMinWidth(250);
            del.setMaxWidth(250);
            del.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

            del.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                for (int i = 0; i < reservationList.size(); i++) {
                    if (reservationList.get(i).getIdRez() == Integer.parseInt(id.getText())) {
                        reservationList.remove(i);
                        textArea.setText(textArea.getText() + "Reservation with ID " + Integer.parseInt(id.getText()) + " deleted.\n");
                        try {
                            String query = "UPDATE Rezervacija SET Aktivan = 0 WHERE IdRez = ?";
                            Connection con = new SqliteConnection().Connector();
                            PreparedStatement ps = con.prepareStatement(query);
                            ps.setInt(1, Integer.parseInt(id.getText()));
                            ps.executeUpdate();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            });

            Button up = new Button("Update", GlyphsDude.createIcon(FontAwesomeIcons.REFRESH, "20px"));
            up.setLayoutX(37);
            up.setLayoutY(255);
            up.setMinWidth(250);
            up.setMaxWidth(250);
            up.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

            up.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                for (int i = 0; i < reservationList.size(); i++) {
                    if (reservationList.get(i).getIdRez() == Integer.parseInt(id.getText())) {
                        reservationList.get(i).setScreen(Integer.parseInt(screen.getText()));
                        reservationList.get(i).setSifra(code.getText());
                        reservationList.get(i).setUsername(username.getText());
                        reservationList.get(i).setPocetak(starts.getText());
                        reservationList.get(i).setKraj(ends.getText());
                        reservationList.get(i).setDatum(date.getText());

                        textArea.setText(textArea.getText() + "Reservation with ID " + Integer.parseInt(id.getText()) + " updated.\n");
                        try {
                            String query = "UPDATE Rezervacija SET Screen = ?, Sifra = ?, Username = ?, Pocetak = ?, Kraj = ?, Datum = ? WHERE IdRez = ?";
                            Connection con = new SqliteConnection().Connector();
                            PreparedStatement ps = con.prepareStatement(query);
                            ps.setInt(1, Integer.parseInt(screen.getText()));
                            ps.setString(2, code.getText());
                            ps.setString(3, username.getText());
                            ps.setString(4, starts.getText());
                            ps.setString(5, ends.getText());
                            ps.setString(6, date.getText());
                            ps.setInt(7, Integer.parseInt(id.getText()));
                            ps.executeUpdate();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            });

            id.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        try {
                            int ID = Integer.parseInt(id.getText());
                            int ind = 0;
                            for (int i = 0; i < reservationList.size(); i++) {
                                if (reservationList.get(i).getIdRez() == ID) {
                                    if (editable == false) {
                                        root.getChildren().remove(del);
                                        root.getChildren().add(del);
                                    } else {
                                        root.getChildren().remove(up);
                                        root.getChildren().add(up);
                                    }
                                    username.setText(reservationList.get(i).getUsername());
                                    code.setText(reservationList.get(i).getSifra());
                                    screen.setText(reservationList.get(i).getScreen() + "");
                                    starts.setText(reservationList.get(i).getPocetak());
                                    ends.setText(reservationList.get(i).getKraj());
                                    date.setText(reservationList.get(i).getDatum());
                                    ind = 1;
                                    break;
                                }
                            }
                            if (ind == 0) {
                                root.getChildren().remove(del);
                                root.getChildren().remove(up);
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setHeaderText("No ID found!");
                                alert.setContentText("There is not a row with the ID specified. Please enter the valid ID.");
                                alert.showAndWait();
                            }
                        } catch (Exception e) {
                            root.getChildren().remove(del);
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("You have to enter the number!");
                            alert.setContentText("Your input seems to contain characters which are not digits.");
                            alert.showAndWait();
                        }

                    }
                }
            });

            root.getChildren().addAll(l, id, line1, line2, username, code, screen, starts, ends, date);
            Scene scene = new Scene(root, 325, 300);
            primaryStage.setTitle((editable == false) ? "Delete" : "Update");
            primaryStage.setScene(scene);
            //primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.showAndWait();
        }
        if (t == 2) {
            Group root = new Group();
            Stage primaryStage = new Stage();

            Label l = new Label("Enter ID of the film: ");
            l.setTextFill(Color.BLACK);
            l.setLayoutX(10);
            l.setLayoutY(10);
            l.setFont(Font.font(null, FontWeight.BOLD, 18));

            TextField id = new TextField();
            id.setLayoutX(255);
            id.setLayoutY(2);
            id.setFont(Font.font(null, FontWeight.BOLD, 15));
            id.setMinWidth(60);
            id.setMaxWidth(60);

            Line line1 = new Line(0, 50, 325, 50);
            line1.setStrokeWidth(1);
            line1.setStroke(Color.BLACK);

            Line line2 = new Line(0, 330, 325, 330);
            line2.setStrokeWidth(1);
            line2.setStroke(Color.BLACK);

            TextField name = new TextField();
            name.setLayoutX(15);
            name.setLayoutY(75);
            name.setFont(Font.font(null, FontWeight.BOLD, 18));
            name.setPromptText("Name");
            name.setEditable(editable);
            name.setMaxWidth(125);
            name.setMinWidth(125);
            name.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField sd = new TextField();
            sd.setLayoutX(15);
            sd.setLayoutY(125);
            sd.setFont(Font.font(null, FontWeight.BOLD, 18));
            sd.setPromptText("Short description");
            sd.setEditable(editable);
            sd.setMaxWidth(125);
            sd.setMinWidth(125);
            sd.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField trailer = new TextField();
            trailer.setLayoutX(15);
            trailer.setLayoutY(175);
            trailer.setFont(Font.font(null, FontWeight.BOLD, 18));
            trailer.setPromptText("Trailer");
            trailer.setEditable(editable);
            trailer.setMaxWidth(125);
            trailer.setMinWidth(125);
            trailer.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField genre = new TextField();
            genre.setLayoutX(15);
            genre.setLayoutY(225);
            genre.setFont(Font.font(null, FontWeight.BOLD, 18));
            genre.setPromptText("Genre");
            genre.setEditable(editable);
            genre.setMaxWidth(125);
            genre.setMinWidth(125);
            genre.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField dur = new TextField();
            dur.setLayoutX(15);
            dur.setLayoutY(275);
            dur.setFont(Font.font(null, FontWeight.BOLD, 18));
            dur.setPromptText("Duration");
            dur.setEditable(editable);
            dur.setMaxWidth(125);
            dur.setMinWidth(125);
            dur.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField ab = new TextField();
            ab.setLayoutX(185);
            ab.setLayoutY(75);
            ab.setFont(Font.font(null, FontWeight.BOLD, 18));
            ab.setPromptText("Added by");
            ab.setEditable(editable);
            ab.setMaxWidth(125);
            ab.setMinWidth(125);
            ab.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField date = new TextField();
            date.setLayoutX(185);
            date.setLayoutY(125);
            date.setFont(Font.font(null, FontWeight.BOLD, 18));
            date.setPromptText("Date added");
            date.setEditable(editable);
            date.setMaxWidth(125);
            date.setMinWidth(125);
            date.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField IMDB = new TextField();
            IMDB.setLayoutX(185);
            IMDB.setLayoutY(175);
            IMDB.setFont(Font.font(null, FontWeight.BOLD, 18));
            IMDB.setPromptText("IMDB Rating");
            IMDB.setEditable(editable);
            IMDB.setMaxWidth(125);
            IMDB.setMinWidth(125);
            IMDB.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField at = new TextField();
            at.setLayoutX(185);
            at.setLayoutY(225);
            at.setFont(Font.font(null, FontWeight.BOLD, 18));
            at.setPromptText("Adult Ticket Cost");
            at.setEditable(editable);
            at.setMaxWidth(125);
            at.setMinWidth(125);
            at.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField ct = new TextField();
            ct.setLayoutX(185);
            ct.setLayoutY(275);
            ct.setFont(Font.font(null, FontWeight.BOLD, 18));
            ct.setPromptText("Child Ticket Cost");
            ct.setEditable(editable);
            ct.setMaxWidth(125);
            ct.setMinWidth(125);
            ct.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            Button del = new Button("Delete", GlyphsDude.createIcon(FontAwesomeIcons.TRASH, "20px"));
            del.setLayoutX(37);
            del.setLayoutY(355);
            del.setMinWidth(250);
            del.setMaxWidth(250);
            del.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

            del.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                for (int i = 0; i < filmList.size(); i++) {
                    if (filmList.get(i).getIdFilm() == Integer.parseInt(id.getText())) {
                        filmList.remove(i);
                        textArea.setText(textArea.getText() + "Film with ID " + Integer.parseInt(id.getText()) + " deleted.\n");
                        try {
                            String query = "UPDATE Film SET Aktivan = 0 WHERE IdFil = ?";
                            Connection con = new SqliteConnection().Connector();
                            PreparedStatement ps = con.prepareStatement(query);
                            ps.setInt(1, Integer.parseInt(id.getText()));
                            ps.executeUpdate();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            });

            Button up = new Button("Update", GlyphsDude.createIcon(FontAwesomeIcons.REFRESH, "20px"));
            up.setLayoutX(37);
            up.setLayoutY(355);
            up.setMinWidth(250);
            up.setMaxWidth(250);
            up.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

            up.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
                for (int i = 0; i < filmList.size(); i++) {
                    if (filmList.get(i).getIdFilm() == Integer.parseInt(id.getText())) {
                        filmList.get(i).setIme(name.getText());
                        filmList.get(i).setKratakOpis(sd.getText());
                        filmList.get(i).setTrejler(trailer.getText());
                        filmList.get(i).setZanr(genre.getText());
                        filmList.get(i).setDuzina(dur.getText());
                        filmList.get(i).setDodao(ab.getText());
                        filmList.get(i).setDatumDodavanja(date.getText());
                        filmList.get(i).setIMDBrating(IMDB.getText());
                        filmList.get(i).setCenaKarteD(Double.parseDouble(ct.getText()));
                        filmList.get(i).setCenaKarteO(Double.parseDouble(at.getText()));

                        textArea.setText(textArea.getText() + "Film with ID " + Integer.parseInt(id.getText()) + " updated.\n");

                        String query = "UPDATE Film SET Ime = ?, KratakOpis = ?, Trejler = ?, Zanr = ?, Duzina = ?, Dodao = ?, DatumDodavanja = ?, "
                                + "Rating = ?, CenaKarteD = ?, CenaKarteO = ? WHERE IdFil = ?";
                        try (Connection con = SqliteConnection.Connector(); PreparedStatement ps = con.prepareStatement(query)) {
                            ps.setString(1, name.getText());
                            ps.setString(2, sd.getText());
                            ps.setString(3, trailer.getText());
                            ps.setString(4, genre.getText());
                            ps.setString(5, dur.getText());
                            ps.setString(6, ab.getText());
                            ps.setString(7, date.getText());
                            ps.setString(8, IMDB.getText());
                            ps.setString(9, ct.getText());
                            ps.setString(10, at.getText());
                            ps.setInt(11, Integer.parseInt(id.getText()));
                            ps.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            });

            id.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        try {
                            int ID = Integer.parseInt(id.getText());
                            int ind = 0;
                            for (int i = 0; i < filmList.size(); i++) {
                                if (filmList.get(i).getIdFilm() == ID) {
                                    if (editable == false) {
                                        root.getChildren().remove(del);
                                        root.getChildren().add(del);
                                    } else {
                                        root.getChildren().remove(up);
                                        root.getChildren().add(up);
                                    }
                                    name.setText(filmList.get(i).getIme());
                                    sd.setText(filmList.get(i).getKratakOpis());
                                    trailer.setText(filmList.get(i).getTrejler());
                                    genre.setText(filmList.get(i).getZanr());
                                    dur.setText(filmList.get(i).getDuzina());
                                    ab.setText(filmList.get(i).getDodao());
                                    date.setText(filmList.get(i).getDatumDodavanja());
                                    IMDB.setText(filmList.get(i).getIMDBrating());
                                    ct.setText(filmList.get(i).getCenaKarteD() + "");
                                    at.setText(filmList.get(i).getCenaKarteO() + "");
                                    ind = 1;
                                    break;
                                }
                            }
                            if (ind == 0) {
                                root.getChildren().remove(del);
                                root.getChildren().remove(up);
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setHeaderText("No ID found!");
                                alert.setContentText("There is not a row with the ID specified. Please enter the valid ID.");
                                alert.showAndWait();
                            }
                        } catch (Exception e) {
                            root.getChildren().remove(del);
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("You have to enter the number!");
                            alert.setContentText("Your input seems to contain characters which are not digits.");
                            alert.showAndWait();
                        }

                    }
                }
            });

            root.getChildren().addAll(l, id, line1, line2, name, sd, trailer, genre, dur, ab, date, IMDB, ct, at);
            Scene scene = new Scene(root, 325, 400);
            primaryStage.setTitle((editable == false) ? "Delete" : "Update");
            primaryStage.setScene(scene);
            //primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.showAndWait();
        }
        if (t == 4) {
            Group root = new Group();
            Stage primaryStage = new Stage();

            Label l = new Label("Enter ID of the termin: ");
            l.setTextFill(Color.BLACK);
            l.setLayoutX(10);
            l.setLayoutY(10);
            l.setFont(Font.font(null, FontWeight.BOLD, 18));

            TextField id = new TextField();
            id.setLayoutX(255);
            id.setLayoutY(2);
            id.setFont(Font.font(null, FontWeight.BOLD, 15));
            id.setMinWidth(60);
            id.setMaxWidth(60);

            Line line1 = new Line(0, 50, 325, 50);
            line1.setStrokeWidth(1);
            line1.setStroke(Color.BLACK);

            Line line2 = new Line(0, 230, 325, 230);
            line2.setStrokeWidth(1);
            line2.setStroke(Color.BLACK);

            TextField name = new TextField();
            name.setLayoutX(15);
            name.setLayoutY(75);
            name.setFont(Font.font(null, FontWeight.BOLD, 18));
            name.setPromptText("Film name");
            name.setEditable(editable);
            name.setMaxWidth(125);
            name.setMinWidth(125);
            name.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField date = new TextField();
            date.setLayoutX(185);
            date.setLayoutY(175);
            date.setFont(Font.font(null, FontWeight.BOLD, 18));
            date.setPromptText("Date");
            date.setEditable(editable);
            date.setMaxWidth(125);
            date.setMinWidth(125);
            date.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField starts = new TextField();
            starts.setLayoutX(15);
            starts.setLayoutY(125);
            starts.setFont(Font.font(null, FontWeight.BOLD, 18));
            starts.setPromptText("Starts");
            starts.setEditable(editable);
            starts.setMaxWidth(125);
            starts.setMinWidth(125);
            starts.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField ends = new TextField();
            ends.setLayoutX(15);
            ends.setLayoutY(175);
            ends.setFont(Font.font(null, FontWeight.BOLD, 18));
            ends.setPromptText("Ends");
            ends.setEditable(editable);
            ends.setMaxWidth(125);
            ends.setMinWidth(125);
            ends.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField ab = new TextField();
            ab.setLayoutX(185);
            ab.setLayoutY(125);
            ab.setFont(Font.font(null, FontWeight.BOLD, 18));
            ab.setPromptText("Added by");
            ab.setEditable(editable);
            ab.setMaxWidth(125);
            ab.setMinWidth(125);
            ab.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField screen = new TextField();
            screen.setLayoutX(185);
            screen.setLayoutY(75);
            screen.setFont(Font.font(null, FontWeight.BOLD, 18));
            screen.setPromptText("Screen");
            screen.setEditable(editable);
            screen.setMaxWidth(125);
            screen.setMinWidth(125);
            screen.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            Button del = new Button("Delete", GlyphsDude.createIcon(FontAwesomeIcons.TRASH, "20px"));
            del.setLayoutX(37);
            del.setLayoutY(255);
            del.setMinWidth(250);
            del.setMaxWidth(250);
            del.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

            del.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                for (int i = 0; i < terminList.size(); i++) {
                    if (terminList.get(i).getIdTermin() == Integer.parseInt(id.getText())) {
                        terminList.remove(i);
                        textArea.setText(textArea.getText() + "Termin with ID " + Integer.parseInt(id.getText()) + " deleted.\n");
                        try {
                            String query = "UPDATE Termin SET Aktivan = 0 WHERE IdTerm = ?";
                            Connection con = new SqliteConnection().Connector();
                            PreparedStatement ps = con.prepareStatement(query);
                            ps.setInt(1, Integer.parseInt(id.getText()));
                            ps.executeUpdate();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            });

            Button up = new Button("Update", GlyphsDude.createIcon(FontAwesomeIcons.REFRESH, "20px"));
            up.setLayoutX(37);
            up.setLayoutY(255);
            up.setMinWidth(250);
            up.setMaxWidth(250);
            up.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

            up.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                for (int i = 0; i < terminList.size(); i++) {
                    if (terminList.get(i).getIdTermin() == Integer.parseInt(id.getText())) {
                        terminList.get(i).setImeFilma(name.getText());
                        terminList.get(i).setDatum(date.getText());
                        terminList.get(i).setPocetak(starts.getText());
                        terminList.get(i).setKraj(ends.getText());
                        terminList.get(i).setDodao(ab.getText());
                        terminList.get(i).setSala(Integer.parseInt(screen.getText()));

                        textArea.setText(textArea.getText() + "Termin with ID " + Integer.parseInt(id.getText()) + " updated.\n");
                        try {
                            String query = "UPDATE Termin SET ImeFilma = ?, Datum = ?, Pocetak = ?, Kraj = ?, Dodao = ?, Sala = ? WHERE IdTerm = ?";
                            Connection con = new SqliteConnection().Connector();
                            PreparedStatement ps = con.prepareStatement(query);
                            ps.setString(1, name.getText());
                            ps.setString(2, date.getText());
                            ps.setString(3, starts.getText());
                            ps.setString(4, ends.getText());
                            ps.setString(5, ab.getText());
                            ps.setString(6, screen.getText());
                            ps.setInt(7, Integer.parseInt(id.getText()));
                            ps.executeUpdate();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            });

            id.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        try {
                            int ID = Integer.parseInt(id.getText());
                            int ind = 0;
                            for (int i = 0; i < terminList.size(); i++) {
                                if (terminList.get(i).getIdTermin() == ID) {
                                    if (editable == false) {
                                        root.getChildren().remove(del);
                                        root.getChildren().add(del);
                                    } else {
                                        root.getChildren().remove(up);
                                        root.getChildren().add(up);
                                    }
                                    name.setText(terminList.get(i).getIme());
                                    date.setText(terminList.get(i).getDatum());
                                    starts.setText(terminList.get(i).getPocetak());
                                    ends.setText(terminList.get(i).getKraj());
                                    ab.setText(terminList.get(i).getDodao());
                                    screen.setText(terminList.get(i).getSala() + "");
                                    ind = 1;
                                    break;
                                }
                            }
                            if (ind == 0) {
                                root.getChildren().remove(del);
                                root.getChildren().remove(up);
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setHeaderText("No ID found!");
                                alert.setContentText("There is not a row with the ID specified. Please enter the valid ID.");
                                alert.showAndWait();
                            }
                        } catch (Exception e) {
                            root.getChildren().remove(del);
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("You have to enter the number!");
                            alert.setContentText("Your input seems to contain characters which are not digits.");
                            alert.showAndWait();
                        }

                    }
                }
            });

            root.getChildren().addAll(l, id, line1, line2, name, date, starts, ends, ab, screen);
            Scene scene = new Scene(root, 325, 300);
            primaryStage.setTitle((editable == false) ? "Delete" : "Update");
            primaryStage.setScene(scene);
            //primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.showAndWait();
        }
        if (t == 3) {
            Group root = new Group();
            Stage primaryStage = new Stage();

            Label l = new Label("Enter ID of the food: ");
            l.setTextFill(Color.BLACK);
            l.setLayoutX(10);
            l.setLayoutY(10);
            l.setFont(Font.font(null, FontWeight.BOLD, 18));

            TextField id = new TextField();
            id.setLayoutX(255);
            id.setLayoutY(2);
            id.setFont(Font.font(null, FontWeight.BOLD, 15));
            id.setMinWidth(60);
            id.setMaxWidth(60);

            Line line1 = new Line(0, 50, 325, 50);
            line1.setStrokeWidth(1);
            line1.setStroke(Color.BLACK);

            Line line2 = new Line(0, 230, 325, 230);
            line2.setStrokeWidth(1);
            line2.setStroke(Color.BLACK);

            TextField name = new TextField();
            name.setLayoutX(15);
            name.setLayoutY(75);
            name.setFont(Font.font(null, FontWeight.BOLD, 18));
            name.setPromptText("Name");
            name.setEditable(editable);
            name.setMaxWidth(125);
            name.setMinWidth(125);
            name.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField category = new TextField();
            category.setLayoutX(15);
            category.setLayoutY(125);
            category.setFont(Font.font(null, FontWeight.BOLD, 18));
            category.setPromptText("Category");
            category.setEditable(editable);
            category.setMaxWidth(125);
            category.setMinWidth(125);
            category.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField price = new TextField();
            price.setLayoutX(15);
            price.setLayoutY(175);
            price.setFont(Font.font(null, FontWeight.BOLD, 18));
            price.setPromptText("Price");
            price.setEditable(editable);
            price.setMaxWidth(125);
            price.setMinWidth(125);
            price.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField sd = new TextField();
            sd.setLayoutX(185);
            sd.setLayoutY(75);
            sd.setFont(Font.font(null, FontWeight.BOLD, 18));
            sd.setPromptText("Short description");
            sd.setEditable(editable);
            sd.setMaxWidth(125);
            sd.setMinWidth(125);
            sd.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField ab = new TextField();
            ab.setLayoutX(185);
            ab.setLayoutY(125);
            ab.setFont(Font.font(null, FontWeight.BOLD, 18));
            ab.setPromptText("Added by");
            ab.setEditable(editable);
            ab.setMaxWidth(125);
            ab.setMinWidth(125);
            ab.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField date = new TextField();
            date.setLayoutX(185);
            date.setLayoutY(175);
            date.setFont(Font.font(null, FontWeight.BOLD, 18));
            date.setPromptText("Date");
            date.setEditable(editable);
            date.setMaxWidth(125);
            date.setMinWidth(125);
            date.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            Button del = new Button("Delete", GlyphsDude.createIcon(FontAwesomeIcons.TRASH, "20px"));
            del.setLayoutX(37);
            del.setLayoutY(255);
            del.setMinWidth(250);
            del.setMaxWidth(250);
            del.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

            del.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                for (int i = 0; i < foodList.size(); i++) {
                    if (foodList.get(i).getIdHrana() == Integer.parseInt(id.getText())) {
                        foodList.remove(i);
                        textArea.setText(textArea.getText() + "Food with ID " + Integer.parseInt(id.getText()) + " deleted.\n");
                        try {
                            String query = "DELETE FROM Hrana WHERE IdHra = ?";
                            Connection con = new SqliteConnection().Connector();
                            PreparedStatement ps = con.prepareStatement(query);
                            ps.setInt(1, Integer.parseInt(id.getText()));
                            ps.execute();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            });

            Button up = new Button("Update", GlyphsDude.createIcon(FontAwesomeIcons.REFRESH, "20px"));
            up.setLayoutX(37);
            up.setLayoutY(255);
            up.setMinWidth(250);
            up.setMaxWidth(250);
            up.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

            up.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                for (int i = 0; i < foodList.size(); i++) {
                    if (foodList.get(i).getIdHrana() == Integer.parseInt(id.getText())) {
                        foodList.get(i).setIme(name.getText());
                        foodList.get(i).setCategory(category.getText());
                        foodList.get(i).setCena(Double.parseDouble(price.getText()));
                        foodList.get(i).setOpis(sd.getText());
                        foodList.get(i).setDodao(ab.getText());
                        foodList.get(i).setDatumDodavanja(date.getText());

                        textArea.setText(textArea.getText() + "Food with ID " + Integer.parseInt(id.getText()) + " updated.\n");
                        try {
                            String query = "UPDATE Hrana SET Ime = ?, Kategorija = ?, Cena = ?, Opis = ?, Dodao = ?, DatumDodavanja = ? WHERE IdHra = ?";
                            Connection con = new SqliteConnection().Connector();
                            PreparedStatement ps = con.prepareStatement(query);
                            ps.setString(1, name.getText());
                            ps.setString(2, category.getText());
                            ps.setString(3, price.getText());
                            ps.setString(4, sd.getText());
                            ps.setString(5, ab.getText());
                            ps.setString(6, date.getText());
                            ps.setInt(7, Integer.parseInt(id.getText()));
                            ps.executeUpdate();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            });

            id.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        try {
                            int ID = Integer.parseInt(id.getText());
                            int ind = 0;
                            for (int i = 0; i < foodList.size(); i++) {
                                if (foodList.get(i).getIdHrana() == ID) {
                                    if (editable == false) {
                                        root.getChildren().remove(del);
                                        root.getChildren().add(del);
                                    } else {
                                        root.getChildren().remove(up);
                                        root.getChildren().add(up);
                                    }
                                    name.setText(foodList.get(i).getIme());
                                    category.setText(foodList.get(i).getCategory());
                                    price.setText(foodList.get(i).getCena() + "");
                                    sd.setText(foodList.get(i).getOpis());
                                    ab.setText(foodList.get(i).getDodao());
                                    date.setText(foodList.get(i).getDatumDodavanja());
                                    ind = 1;
                                    break;
                                }
                            }
                            if (ind == 0) {
                                root.getChildren().remove(del);
                                root.getChildren().remove(up);
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setHeaderText("No ID found!");
                                alert.setContentText("There is not a row with the ID specified. Please enter the valid ID.");
                                alert.showAndWait();
                            }
                        } catch (Exception e) {
                            root.getChildren().remove(del);
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("You have to enter the number!");
                            alert.setContentText("Your input seems to contain characters which are not digits.");
                            alert.showAndWait();
                        }

                    }
                }
            });

            root.getChildren().addAll(l, id, line1, line2, name, category, price, sd, ab, date);
            Scene scene = new Scene(root, 325, 300);
            primaryStage.setTitle((editable == false) ? "Delete" : "Update");
            primaryStage.setScene(scene);
            //primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.showAndWait();
        }
        if (t == 4) {
            Group root = new Group();
            Stage primaryStage = new Stage();

            Label l = new Label("Enter ID of the termin: ");
            l.setTextFill(Color.BLACK);
            l.setLayoutX(10);
            l.setLayoutY(10);
            l.setFont(Font.font(null, FontWeight.BOLD, 18));

            TextField id = new TextField();
            id.setLayoutX(255);
            id.setLayoutY(2);
            id.setFont(Font.font(null, FontWeight.BOLD, 15));
            id.setMinWidth(60);
            id.setMaxWidth(60);

            Line line1 = new Line(0, 50, 325, 50);
            line1.setStrokeWidth(1);
            line1.setStroke(Color.BLACK);

            Line line2 = new Line(0, 230, 325, 230);
            line2.setStrokeWidth(1);
            line2.setStroke(Color.BLACK);

            TextField name = new TextField();
            name.setLayoutX(15);
            name.setLayoutY(75);
            name.setFont(Font.font(null, FontWeight.BOLD, 18));
            name.setPromptText("Film name");
            name.setEditable(editable);
            name.setMaxWidth(125);
            name.setMinWidth(125);
            name.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField date = new TextField();
            date.setLayoutX(185);
            date.setLayoutY(175);
            date.setFont(Font.font(null, FontWeight.BOLD, 18));
            date.setPromptText("Date");
            date.setEditable(editable);
            date.setMaxWidth(125);
            date.setMinWidth(125);
            date.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField starts = new TextField();
            starts.setLayoutX(15);
            starts.setLayoutY(125);
            starts.setFont(Font.font(null, FontWeight.BOLD, 18));
            starts.setPromptText("Starts");
            starts.setEditable(editable);
            starts.setMaxWidth(125);
            starts.setMinWidth(125);
            starts.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField ends = new TextField();
            ends.setLayoutX(15);
            ends.setLayoutY(175);
            ends.setFont(Font.font(null, FontWeight.BOLD, 18));
            ends.setPromptText("Ends");
            ends.setEditable(editable);
            ends.setMaxWidth(125);
            ends.setMinWidth(125);
            ends.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField ab = new TextField();
            ab.setLayoutX(185);
            ab.setLayoutY(125);
            ab.setFont(Font.font(null, FontWeight.BOLD, 18));
            ab.setPromptText("Added by");
            ab.setEditable(editable);
            ab.setMaxWidth(125);
            ab.setMinWidth(125);
            ab.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField screen = new TextField();
            screen.setLayoutX(185);
            screen.setLayoutY(75);
            screen.setFont(Font.font(null, FontWeight.BOLD, 18));
            screen.setPromptText("Screen");
            screen.setEditable(editable);
            screen.setMaxWidth(125);
            screen.setMinWidth(125);
            screen.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            Button del = new Button("Delete", GlyphsDude.createIcon(FontAwesomeIcons.TRASH, "20px"));
            del.setLayoutX(37);
            del.setLayoutY(255);
            del.setMinWidth(250);
            del.setMaxWidth(250);
            del.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

            del.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                for (int i = 0; i < terminList.size(); i++) {
                    if (terminList.get(i).getIdTermin() == Integer.parseInt(id.getText())) {
                        terminList.remove(i);
                        textArea.setText(textArea.getText() + "Termin with ID " + Integer.parseInt(id.getText()) + " deleted.\n");
                        try {
                            String query = "UPDATE Termin SET Aktivan = 0 WHERE IdTerm = ?";
                            Connection con = new SqliteConnection().Connector();
                            PreparedStatement ps = con.prepareStatement(query);
                            ps.setInt(1, Integer.parseInt(id.getText()));
                            ps.executeUpdate();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            });

            Button up = new Button("Update", GlyphsDude.createIcon(FontAwesomeIcons.REFRESH, "20px"));
            up.setLayoutX(37);
            up.setLayoutY(255);
            up.setMinWidth(250);
            up.setMaxWidth(250);
            up.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

            up.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                for (int i = 0; i < terminList.size(); i++) {
                    if (terminList.get(i).getIdTermin() == Integer.parseInt(id.getText())) {
                        terminList.get(i).setImeFilma(name.getText());
                        terminList.get(i).setDatum(date.getText());
                        terminList.get(i).setPocetak(starts.getText());
                        terminList.get(i).setKraj(ends.getText());
                        terminList.get(i).setDodao(ab.getText());
                        terminList.get(i).setSala(Integer.parseInt(screen.getText()));

                        textArea.setText(textArea.getText() + "Termin with ID " + Integer.parseInt(id.getText()) + " updated.\n");
                        try {
                            String query = "UPDATE Termin SET ImeFilma = ?, Datum = ?, Pocetak = ?, Kraj = ?, Dodao = ?, Sala = ? WHERE IdTerm = ?";
                            Connection con = new SqliteConnection().Connector();
                            PreparedStatement ps = con.prepareStatement(query);
                            ps.setString(1, name.getText());
                            ps.setString(2, date.getText());
                            ps.setString(3, starts.getText());
                            ps.setString(4, ends.getText());
                            ps.setString(5, ab.getText());
                            ps.setString(6, screen.getText());
                            ps.setInt(7, Integer.parseInt(id.getText()));
                            ps.executeUpdate();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            });

            id.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        try {
                            int ID = Integer.parseInt(id.getText());
                            int ind = 0;
                            for (int i = 0; i < terminList.size(); i++) {
                                if (terminList.get(i).getIdTermin() == ID) {
                                    if (editable == false) {
                                        root.getChildren().remove(del);
                                        root.getChildren().add(del);
                                    } else {
                                        root.getChildren().remove(up);
                                        root.getChildren().add(up);
                                    }
                                    name.setText(terminList.get(i).getIme());
                                    date.setText(terminList.get(i).getDatum());
                                    starts.setText(terminList.get(i).getPocetak());
                                    ends.setText(terminList.get(i).getKraj());
                                    ab.setText(terminList.get(i).getDodao());
                                    screen.setText(terminList.get(i).getSala() + "");
                                    ind = 1;
                                    break;
                                }
                            }
                            if (ind == 0) {
                                root.getChildren().remove(del);
                                root.getChildren().remove(up);
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setHeaderText("No ID found!");
                                alert.setContentText("There is not a row with the ID specified. Please enter the valid ID.");
                                alert.showAndWait();
                            }
                        } catch (Exception e) {
                            root.getChildren().remove(del);
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("You have to enter the number!");
                            alert.setContentText("Your input seems to contain characters which are not digits.");
                            alert.showAndWait();
                        }

                    }
                }
            });

            root.getChildren().addAll(l, id, line1, line2, name, date, starts, ends, ab, screen);
            Scene scene = new Scene(root, 325, 300);
            primaryStage.setTitle((editable == false) ? "Delete" : "Update");
            primaryStage.setScene(scene);
            //primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.showAndWait();
        }
        if (t == 5) {
            Group root = new Group();
            Stage primaryStage = new Stage();

            Label l = new Label("Enter ID of the user: ");
            l.setTextFill(Color.BLACK);
            l.setLayoutX(10);
            l.setLayoutY(10);
            l.setFont(Font.font(null, FontWeight.BOLD, 18));

            TextField id = new TextField();
            id.setLayoutX(255);
            id.setLayoutY(2);
            id.setFont(Font.font(null, FontWeight.BOLD, 15));
            id.setMinWidth(60);
            id.setMaxWidth(60);

            Line line1 = new Line(0, 50, 325, 50);
            line1.setStrokeWidth(1);
            line1.setStroke(Color.BLACK);

            Line line2 = new Line(0, 230, 325, 230);
            line2.setStrokeWidth(1);
            line2.setStroke(Color.BLACK);

            TextField name = new TextField();
            name.setLayoutX(15);
            name.setLayoutY(75);
            name.setFont(Font.font(null, FontWeight.BOLD, 18));
            name.setPromptText("Name");
            name.setEditable(editable);
            name.setMaxWidth(125);
            name.setMinWidth(125);
            name.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField username = new TextField();
            username.setLayoutX(15);
            username.setLayoutY(125);
            username.setFont(Font.font(null, FontWeight.BOLD, 18));
            username.setPromptText("Username");
            username.setEditable(editable);
            username.setMaxWidth(125);
            username.setMinWidth(125);
            username.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField gender = new TextField();
            gender.setLayoutX(15);
            gender.setLayoutY(175);
            gender.setFont(Font.font(null, FontWeight.BOLD, 18));
            gender.setPromptText("Gender");
            gender.setEditable(editable);
            gender.setMaxWidth(125);
            gender.setMinWidth(125);
            gender.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField city = new TextField();
            city.setLayoutX(185);
            city.setLayoutY(75);
            city.setFont(Font.font(null, FontWeight.BOLD, 18));
            city.setPromptText("City");
            city.setEditable(editable);
            city.setMaxWidth(125);
            city.setMinWidth(125);
            city.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField date = new TextField();
            date.setLayoutX(185);
            date.setLayoutY(125);
            date.setFont(Font.font(null, FontWeight.BOLD, 18));
            date.setPromptText("Date");
            date.setEditable(editable);
            date.setMaxWidth(125);
            date.setMinWidth(125);
            date.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField country = new TextField();
            country.setLayoutX(185);
            country.setLayoutY(175);
            country.setFont(Font.font(null, FontWeight.BOLD, 18));
            country.setPromptText("Country");
            country.setEditable(editable);
            country.setMaxWidth(125);
            country.setMinWidth(125);
            country.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18;");

            Button del = new Button("Delete", GlyphsDude.createIcon(FontAwesomeIcons.TRASH, "20px"));
            del.setLayoutX(37);
            del.setLayoutY(255);
            del.setMinWidth(250);
            del.setMaxWidth(250);
            del.setStyle("-fx-font-size: 1.35em; -fx-font-weight: bold; -fx-text-fill: #000000");

            del.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).getIdUser() == Integer.parseInt(id.getText())) {
                        userList.remove(i);
                        textArea.setText(textArea.getText() + "User with ID " + Integer.parseInt(id.getText()) + " deleted.\n");
                        try {
                            String query = "DELETE FROM User WHERE IdUser = ?";
                            Connection con = new SqliteConnection().Connector();
                            PreparedStatement ps = con.prepareStatement(query);
                            ps.setInt(1, Integer.parseInt(id.getText()));
                            ps.execute();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            });

            id.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        try {
                            int ID = Integer.parseInt(id.getText());
                            int ind = 0;
                            for (int i = 0; i < userList.size(); i++) {
                                if (userList.get(i).getIdUser() == ID) {
                                    if (editable == false) {
                                        root.getChildren().remove(del);
                                        root.getChildren().add(del);
                                    }

                                    name.setText(userList.get(i).getFullname());
                                    username.setText(userList.get(i).getUsername());
                                    gender.setText(userList.get(i).getGender());
                                    city.setText(userList.get(i).getCity());
                                    country.setText(userList.get(i).getCountry());
                                    date.setText(userList.get(i).getDateofbirth());
                                    ind = 1;
                                    break;
                                }
                            }
                            if (ind == 0) {
                                root.getChildren().remove(del);
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setHeaderText("No ID found!");
                                alert.setContentText("There is not a row with the ID specified. Please enter the valid ID.");
                                alert.showAndWait();
                            }
                        } catch (Exception e) {
                            root.getChildren().remove(del);
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("You have to enter the number!");
                            alert.setContentText("Your input seems to contain characters which are not digits.");
                            alert.showAndWait();
                        }

                    }
                }
            });

            root.getChildren().addAll(l, id, line1, line2, name, username, gender, city, country, date);
            Scene scene = new Scene(root, 325, 300);
            primaryStage.setTitle((editable == false) ? "Delete" : "Update");
            primaryStage.setScene(scene);
            //primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.showAndWait();
        }

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

}
