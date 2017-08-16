package shared;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.SignatureSpi;
import java.util.Scanner;

/**
 * SharedApplication is the root application, which launches the primary window
 *
 * @version 1.0
 * @author Mauricio Graciano Álvarez, Eder Cozatl Xicoyencatl, Emmanuel De Los Santos Castro
 *
 * 149605, 145468, 151975
 * August 26, 2016
 */
public class SharedApplication extends Application {

    //region Application
    /**
     * sets up the main container defaults, and then displays the primary window
     * @param primaryStage main container
     * @throws IOException if the sharted pane fxml file is not found
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Sets up the main window from the shared_pain.fxml file
        Parent root = FXMLLoader.load(getClass().getResource("../resources/shared_pane.fxml"));

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

    //region SharedApplication
    /**
     * start method
     * @param args not applicable
     */
    public static void main(String[] args) {
        launch(args);
    }
    //endregion
}
