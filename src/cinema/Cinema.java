/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema;

import com.sun.javafx.application.LauncherImpl;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.scene.Group;
import javafx.stage.Stage;

/**
 *
 * @author Lazar
 */
public class Cinema extends Application {

    private static final int COUNT_LIMIT = 500000;
    private List<Film> filmList = new LinkedList<>();
    private List<Food> foodList = new LinkedList<>();

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        Login x = new Login(root, primaryStage, filmList, foodList);
        SqliteConnection.Connector();
    }

    @Override
    public void init() throws Exception {
        ReadFromTermin rft = new ReadFromTermin();
        Film f = new Film(rft.getTerminList());
        filmList = f.getFilmList();
        Food food = new Food();
        foodList = food.getFoodList();

        for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
        }
    }

    public static void main(String[] args) {
        LauncherImpl.launchApplication(Cinema.class, MyPreloader.class, args);
    }

}
