package edu.erciyes.trafficlightsimulation;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MenuManager.showMenu(primaryStage); // Menü sahnesi ile başla
    }

    public static void main(String[] args) {
        launch(args);
    }
}

