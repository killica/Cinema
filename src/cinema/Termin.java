package cinema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

class MojPosao extends TimerTask {

    private Stage primaryStage;
    private Label l;
    private boolean prvi, been = false;
    private int kraj, kk;

    public MojPosao(Stage primaryStage, Label l, boolean prvi, int kraj) {
        this.primaryStage = primaryStage;
        this.l = l;
        this.prvi = prvi;
        this.kraj = kraj;
        kk = (kraj == 1) ? 705 : 1200;
    }

    public void run() {
        if (prvi == true) {
            if (primaryStage.getWidth() <= kk) {
                primaryStage.setWidth(primaryStage.getWidth() + 2);
            } else {
                l.setText("Gotovo");
            }
        } else {
            if (primaryStage.getWidth() >= 275 && !been) {
                primaryStage.setWidth(primaryStage.getWidth() - 2);
            } else {
                been = true;
                if (primaryStage.getWidth() <= kk) {
                    primaryStage.setWidth(primaryStage.getWidth() + 2);
                } else {
                    been = false;
                    l.setText("Gotovo");
                }

            }

        }

    }
}

public class Termin {

    private final ObservableList numList = FXCollections.observableArrayList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    private Label[] to = new Label[10];
    private TextField[] t = new TextField[10];
    private ToggleGroup[] group = new ToggleGroup[10];
    private boolean prvi = true;
    private int n;
    private boolean uspeo = true;

    public boolean uporedi(String s1, String s2, String s3, String s4) {
        int a = Integer.parseInt(s1.substring(0, 2)) * 60 + Integer.parseInt(s1.substring(3));
        int b = Integer.parseInt(s2.substring(0, 2)) * 60 + Integer.parseInt(s2.substring(3));
        int c = Integer.parseInt(s3.substring(0, 2)) * 60 + Integer.parseInt(s3.substring(3));
        int d = Integer.parseInt(s4.substring(0, 2)) * 60 + Integer.parseInt(s4.substring(3));
        if (Integer.parseInt(s1.substring(0, 2)) > Integer.parseInt(s2.substring(0, 2))) {
            b += 1440;//broj minuta u danu
        }
        if (Integer.parseInt(s3.substring(0, 2)) > Integer.parseInt(s4.substring(0, 2))) {
            d += 1440;//broj minuta u danu
        }
        return (((a < c) ? c : a) <= ((b < d) ? b : d));

    }

    public Termin(String s1, String imeFilma, String user, List<Film> filmList) {

        Group root = new Group();
        Scene scene = new Scene(root, 275, 265);
        Group root2 = new Group();
        root2.setOpacity(0);
        Stage primaryStage = new Stage();

        DatePicker dp1 = new DatePicker();
        dp1.setLayoutX(25);
        dp1.setLayoutY(50);
        dp1.setMinSize(220, 40);
        dp1.setValue(LocalDate.now());

        Label fp = new Label("See available termins for " + dp1.getValue().toString());
        fp.setTextFill(Color.GREEN);
        fp.setLayoutX(10);
        fp.setLayoutY(240);
        fp.setUnderline(true);
        fp.setFont(Font.font(null, FontWeight.BOLD, 15));
        fp.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            fp.setTextFill(Color.DARKGREEN);
            scene.setCursor(Cursor.HAND);

        });
        fp.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            fp.setTextFill(Color.GREEN);
            scene.setCursor(Cursor.DEFAULT);
        });

        fp.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {

            AvailableTermins at = new AvailableTermins(dp1.getValue().toString(), filmList);

        });

        Button New = new Button("Record");

        ComboBox<String> g = new ComboBox<>(numList);
        g.setPromptText("Number of projections");
        g.setLayoutX(25);
        g.setLayoutY(125);
        g.setMinSize(200, 40);
        g.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 15;");
        g.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                root2.setOpacity(0);
                root2.getChildren().clear();
                Timer vreme = new Timer();
                New.setDisable(false);

                Label t3 = new Label("Pocelo");
                t3.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        if (t3.getText().equals("Gotovo")) {
                            FadeTransition fadeTransition = new FadeTransition();
                            fadeTransition.setDuration(Duration.millis(500));
                            fadeTransition.setNode(root2);
                            fadeTransition.setFromValue(0);
                            fadeTransition.setToValue(1);
                            fadeTransition.play();
                            vreme.cancel();
                        }

                    }
                });

                n = Integer.parseInt(g.getValue());
                if (prvi) {
                    MojPosao posao = new MojPosao(primaryStage, t3, true, (n <= 5) ? 1 : 2);
                    vreme.schedule(posao, 0, 1);
                    prvi = false;
                } else {
                    MojPosao posao = new MojPosao(primaryStage, t3, false, (n <= 5) ? 1 : 2);
                    vreme.schedule(posao, 0, 1);
                }

                Label s = new Label("Starts:");
                s.setTextFill(Color.CRIMSON);
                s.setLayoutX(320);
                s.setLayoutY(5);
                s.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));
                Label e = new Label("Ends:");
                e.setTextFill(Color.CRIMSON);
                e.setLayoutX(435);
                e.setLayoutY(5);
                e.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));
                Label scr = new Label("Screen:");
                scr.setTextFill(Color.CRIMSON);
                scr.setLayoutX(560);
                scr.setLayoutY(5);
                scr.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));
                root2.getChildren().addAll(s, e, scr);

                Label st1 = new Label("Starts:");
                st1.setTextFill(Color.CRIMSON);
                st1.setLayoutX(770);
                st1.setLayoutY(5);
                st1.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));
                Label e1 = new Label("Ends:");
                e1.setTextFill(Color.CRIMSON);
                e1.setLayoutX(885);
                e1.setLayoutY(5);
                e1.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));
                Label sc = new Label("Screen:");
                sc.setTextFill(Color.CRIMSON);
                sc.setLayoutX(1020);
                sc.setLayoutY(5);
                sc.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));
                if (n > 5) {
                    root2.getChildren().addAll(st1, e1, sc);
                }

                for (int i = 0; i < n; i++) {

                    t[i] = new TextField();
                    group[i] = new ToggleGroup();

                    to[i] = new Label("hh:mm");
                    to[i].setTextFill(Color.ORANGE);
                    to[i].setLayoutX((i < 5) ? 425 : 875);
                    to[i].setLayoutY(40 + ((i < 5) ? i : i - 5) * 40);
                    to[i].setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));

                    int k = i;

                    t[i].setFont(Font.font(null, FontPosture.ITALIC, 18));
                    t[i].setLayoutX((i < 5) ? 300 : 750);
                    t[i].setLayoutY(40 + ((i < 5) ? i : i - 5) * 40);
                    t[i].setPromptText("  hh:mm");
                    t[i].setMinSize(100, 25);
                    t[i].setMaxSize(100, 25);
                    t[i].setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-posture: italic; -fx-font-size: 18;");
                    t[i].textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                            if (newValue.length() == 5 && s1.length() == 5) {

                                int hour = Integer.parseInt(s1.substring(0, 2));
                                int minute = Integer.parseInt(s1.substring(3));
                                int shour = Integer.parseInt(newValue.substring(0, 2));
                                int sminute = Integer.parseInt(newValue.substring(3));
                                int fminute = (minute + sminute) % 60;
                                int fhour = (shour + hour + (minute + sminute) / 60) % 24;
                                to[k].setText(((fhour < 10) ? ("0" + fhour) : fhour) + ":" + ((fminute < 10) ? ("0" + fminute) : fminute));
                            }

                        }
                    });

                    RadioButton scr1 = new RadioButton("1");
                    RadioButton scr2 = new RadioButton("2");
                    RadioButton scr3 = new RadioButton("3");

                    scr1.setToggleGroup(group[i]);
                    scr2.setToggleGroup(group[i]);
                    scr3.setToggleGroup(group[i]);
                    scr1.setLayoutX((i < 5) ? 525 : 980);
                    scr1.setLayoutY(40 + ((i < 5) ? i : i - 5) * 40);
                    scr2.setLayoutX((i < 5) ? 575 : 1030);
                    scr2.setLayoutY(40 + ((i < 5) ? i : i - 5) * 40);
                    scr3.setLayoutX((i < 5) ? 625 : 1080);
                    scr3.setLayoutY(40 + ((i < 5) ? i : i - 5) * 40);
                    scr1.setFont(Font.font("SansSerif", FontPosture.ITALIC, 17));
                    scr2.setFont(Font.font("SansSerif", FontPosture.ITALIC, 17));
                    scr3.setFont(Font.font("SansSerif", FontPosture.ITALIC, 17));

                    Line line = new Line(705, 0, 705, 275);
                    line.setStrokeWidth(2);
                    line.setStroke(Color.GRAY);

                    root2.getChildren().addAll(to[i], t[i], scr1, scr2, scr3, line);
                }

            }
        });

        Label date = new Label("Choose the date of projection:");
        date.setTextFill(Color.ORANGE);
        date.setLayoutX(15);
        date.setLayoutY(15);
        date.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18));

        New.setLayoutX(58);
        New.setLayoutY(185);
        New.setMinSize(150, 40);
        New.setDisable(true);
        New.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: black");
        New.setOnMouseEntered((javafx.scene.input.MouseEvent me) -> {
            New.setStyle("-fx-font-size: 15; -fx-background-color: crimson; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: white");

        });
        New.setOnMouseExited((javafx.scene.input.MouseEvent me) -> {
            New.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-text-fill: black");

        });

        New.setOnMouseClicked((javafx.scene.input.MouseEvent me) -> {
            int ind = 0;
            boolean in = true;
            boolean in1 = true;

            for (int i = 0; i < n; i++) {
                if (t[i].getText().equals("")) {
                    ind = 1;
                    break;
                }
            }
            for (int i = 0; i < n; i++) {
                if (ind == 1) {
                    ind = 0;
                    uspeo = false;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Unsatisfied conditions");
                    alert.setHeaderText(null);
                    alert.setContentText("You have to complete every start of projection!");
                    alert.showAndWait();
                    break;
                } else {

                    for (int m = 0; m < filmList.size(); m++) {
                        List<ReadFromTermin> terminList = filmList.get(m).getTerminList();
                        for (int j = 0; j < terminList.size(); j++) {
                            if (terminList.get(j).getDatum().equals(dp1.getValue().toString()) && this.uporedi(t[i].getText(), to[i].getText(), terminList.get(j).getPocetak(), terminList.get(j).getKraj()) && terminList.get(j).getSala() == Integer.parseInt(((RadioButton) group[i].getSelectedToggle()).getText())) {
                                System.out.println(terminList.get(j));
                                in = false;
                                break;
                            }

                            int hstart, mstart, hfin, mfin;
                            hstart = Integer.parseInt(t[i].getText().substring(0, 2));
                            mstart = Integer.parseInt(t[i].getText().substring(3));
                            hfin = Integer.parseInt(to[i].getText().substring(0, 2));
                            mfin = Integer.parseInt(to[i].getText().substring(3));
                            if (hstart > hfin) {

                                //Moramo da proverimo da li se preklapa i sa nekim terminom iz sutrasnjeg dana ako se proteze na dva dana
                                if (terminList.get(j).getDatum().equals(dp1.getValue().plusDays(1).toString()) && this.uporedi("00:00", to[i].getText(), terminList.get(j).getPocetak(), terminList.get(j).getKraj()) && terminList.get(j).getSala() == Integer.parseInt(((RadioButton) group[i].getSelectedToggle()).getText())) {
                                    System.out.println(terminList.get(j));
                                    in = false;
                                    break;
                                }
                            }

                        }

                        if (in == false) {
                            break;
                        }
                    }
                    for (int p = 0; p < n; p++) {
                        for (int q = p + 1; q < n; q++) {
                            if (this.uporedi(t[p].getText(), to[p].getText(), t[q].getText(), to[q].getText()) && Integer.parseInt(((RadioButton) group[p].getSelectedToggle()).getText()) == Integer.parseInt(((RadioButton) group[q].getSelectedToggle()).getText())) {
                                System.out.println("Desilo se nesto strasno - naime, uneo si termine koji se preklapaju!");
                                in1 = false;
                                break;
                            }
                        }
                        if (in1 == false) {
                            break;
                        }
                    }
                    if (in == false) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setTitle("Termin not available");
                        alert.setHeaderText(null);
                        alert.setContentText("The termin you have specified is not available. Please look at the timetable and choose another termin.");
                        alert.showAndWait();
                    } else if (in1 == false) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setTitle("Invaild termins!");
                        alert.setHeaderText(null);
                        alert.setContentText("The termins you have specified mustn't intersect!");
                        alert.showAndWait();
                    } else {
                        String sql = "INSERT INTO Termin(ImeFilma,Datum,Pocetak,Kraj,Dodao,Sala,Aktivan) VALUES(?,?,?,?,?,?,?)";
                        try (Connection con = SqliteConnection.Connector(); PreparedStatement ps = con. prepareStatement(sql)) {
                            ps.setString(1, imeFilma);
                            ps.setString(2, dp1.getValue().toString());
                            ps.setString(3, t[i].getText());
                            ps.setString(4, to[i].getText());
                            ps.setString(5, user);
                            ps.setInt(6, Integer.parseInt(((RadioButton) group[i].getSelectedToggle()).getText()));
                            ps.setInt(7, 1);
                            ps.execute();
                        } catch (SQLException e) {
                            uspeo = false;
                            System.out.println(e);
                        }
                        try {
                            String query = "UPDATE Film SET Aktivan = 1 WHERE Ime = ?";
                            Connection con1 = SqliteConnection.Connector();
                            PreparedStatement ps1 = con1.prepareStatement(query);
                            ps1.setString(1, imeFilma);
                            ps1.executeUpdate();
                            con1.close();

                        } catch (SQLException e) {
                            uspeo = false;
                            //ALERT ZA POKUSAJ DODATKA FILMA U VEC ZAUZETOM TERMINU!
                            System.out.println(e);
                        }
                    }
                }

            }

            if (uspeo == true && in == true && in1 == true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Info");
                alert.setHeaderText(null);
                alert.setContentText("You have successfully recorded projections for " + dp1.getValue().toString() + ".");
                alert.showAndWait();
            }
            uspeo = true;

        });
        dp1.valueProperty().addListener((ov, oldValue, newValue) -> {
            g.setDisable(false);
            New.setDisable(false);
            fp.setDisable(false);
            New.setDisable(true);
            fp.setText("See available termins for " + newValue.toString());
            Image image1 = new Image(getClass().getResource("Images/text1.png").toString());
            ImageView iv1 = new ImageView(image1);
            iv1.setFitHeight(365);
            iv1.setFitWidth(250);
            iv1.setPreserveRatio(true);
            iv1.setLayoutX(5);
            iv1.setLayoutY(120);
            iv1.setRotate(-30);
            iv1.setOpacity(0);

            int year = Integer.parseInt(newValue.toString().substring(0, 4));
            int month = Integer.parseInt(newValue.toString().substring(5, 7));
            int day = Integer.parseInt(newValue.toString().substring(8, 10));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String s = dtf.format(now);
            int tmpyear = Integer.parseInt(s.substring(0, 4));
            int tmpmonth = Integer.parseInt(s.substring(5, 7));
            int tmpday = Integer.parseInt(s.substring(8, 10));

            //Ovaj deo koda bi zabranjivao da se zadaju projekcije u proslosti
            /*if (year < tmpyear || (year == tmpyear && month < tmpmonth) || (year == tmpyear && month == tmpmonth && day < tmpday)) {
                g.setDisable(true);
                New.setDisable(true);
                fp.setDisable(true);
                iv1.setOpacity(1);
                root.getChildren().addAll(iv1);

            } else {
                System.out.println("usao");
                iv1.setOpacity(0);
                root.getChildren().remove(iv1);
           
            }*/
        });
        root.getChildren().addAll(dp1, g, date, New, root2, fp);
        primaryStage.setTitle("Projection times");
        primaryStage.setScene(scene);
        primaryStage.setX(525);
        primaryStage.setY(350);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }

    public boolean getUspeo() {
        return uspeo;
    }

}
