package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.SignatureSpi;
import java.util.Scanner;

/**
 * MainApplication is the root application, which launches the primary window
 * @author Mauricio Graciano Álvarez
 * 149605
 * August 26, 2016
 */
public class MainApplication extends Application {

    //region Application
    /**
     * sets up the main container defaults, and then displays the primary window
     * @param primaryStage main container
     * @throws IOException if the main pane fxml file is not found
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Sets up the main window from the main_pain.fxml file
        Parent root = FXMLLoader.load(getClass().getResource("../resources/main_pane.fxml"));

        // Sets up title, as well as maximium and minimun size of container
        primaryStage.setTitle("Interfaz Grafica");
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(400);
        primaryStage.setMaxWidth(500);
        primaryStage.setMaxHeight(800);

        // Create the window, adds it to the container, and then displays it
        primaryStage.setScene( new Scene(root) );
        primaryStage.show();
    }
    //endregion

    //region MainApplication

    /**
     * start method
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    //endregion
}
