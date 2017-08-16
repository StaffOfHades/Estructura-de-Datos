package main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;

/**
 * MainController is the class responsible for handling the main_pane.fxml window.
 * It controls all interaction with the user, as well as all the background work.
 *
 * @version 1.0
 * @author Mauricio Graciano Álvarez
 *
 * 149605
 * August 26, 2016
 *
 *
 * Code examples, tutorials & question from the following pages was used:
 * http://stackoverflow.com/questions/15041760/javafx-open-new-window
 * http://www.java2s.com/Code/Java/JavaFX/UsingFXMLtocreateaUI.htm
 * http://stackoverflow.com/questions/15771949/how-do-i-make-jfilechooser-only-accept-txt
 * https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
 * http://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
 * http://stackoverflow.com/questions/20594392/unable-to-get-scene-from-menuitem-in-javafx
 * http://news.kynosarges.org/2013/11/20/javafx-listview-sizing/
 * http://stackoverflow.com/questions/22047457/how-to-change-the-text-font-size-in-javafx
 * https://blog.idrsolutions.com/2012/11/adding-a-window-resize-listener-to-javafx-scene/
 * http://stackoverflow.com/questions/15735185/printing-an-array-of-numbers-on-a-window
 * http://stackoverflow.com/questions/9451066/how-to-detect-overflow-when-convert-string-to-integer-in-java
 * https://examples.javacodegeeks.com/desktop-java/javafx/dialog-javafx/javafx-dialog-example/
 */
public class MainController implements Initializable {

    //region Constants
    // The maximum number of elements in the array
    private static final int MAX_ARRAY = 100;
    //endregion

    //region Properties
    @FXML private TextArea text_area;
    @FXML private MenuBar menu_bar;
    @FXML private VBox vbox;

    private FileChooser fileChooser;
    private Array array;
    //endregion

    //region FXML
    /**
     * handleEraseAction is called whenever the user calls the erase action.
     * If the array is not empty, it asks the user for an integer to be found & erased in
     * the array. If no match is found, the user receives an alert.
     * @param actionEvent ActionEvent sent by the caller
     */
    @FXML
    public void handleEraseAction(ActionEvent actionEvent) {
        // Check to see if array is not empty
        if ( !checkArrayEmpty() ) {
            // Dialog with a text input is created, only showing the title
            Dialog dialog = new TextInputDialog();
            dialog.setTitle("Eliminar");
            dialog.setContentText("");
            dialog.setHeaderText("");
            dialog.setGraphic(null);

            // Dialog is displayed, and an optianal string is retrieved.
            Optional<String> result = dialog.showAndWait();

            // Determines if a result was returned by the dialog
            if ( result.isPresent() ) {
                // Checks to see if the result is an acceptable number
                Integer num = checkNumberResult( result.get() );

                // If an acceptable number is found, start looking in the array
                if (num != null) {

                    // If a match was found, update the window.
                    // Otherwise, show an alert to the user
                    if ( array.erase(num) ) {
                        handleShowAction(actionEvent);
                    } else {
                        dialog = new Alert(Alert.AlertType.WARNING);
                        dialog.setContentText("No se encontro el elemento \'" + num + "\'");
                        dialog.show();
                    }
                }
            } else {
                System.out.println("Optional result is not present");
            }
        }
    }

    /**
     * handleExitAction is called whenever the user calls the exit action.
     * Simply asks the platform to exit the applet and handle everything.
     * @param actionEvent  ActionEvent sent by the caller
     */
    @FXML
    public void handleExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * handleInsertAction is called whenever the user calls the insert action.
     * If the array is not full, it asks the user for an integer to be added into the array.
     * @param actionEvent  ActionEvent sent by the caller
     */
    @FXML
    public void handleInsertAction(ActionEvent actionEvent) throws IOException {
        // Check to see if the array is not full
        if ( !checkArrayFull() ) {

            // Dialog with a text input is created, only showing the title
            Dialog dialog = new TextInputDialog();
            dialog.setTitle("Agregar");
            dialog.setContentText("");
            dialog.setHeaderText("");
            dialog.setGraphic(null);

            // Dialog is displayed, and an optianal string is retrieved.
            Optional<String> result = dialog.showAndWait();

            // Determines if a result was returned by the dialog
            if ( result.isPresent() )  {
                // Checks to see if the result is an acceptable number
                Integer num = checkNumberResult( result.get() );

                // If an acceptable number was found, add it to the array,
                // & update the window
                if (num != null) {
                    array.add(num);
                    handleShowAction(actionEvent);
                }
            } else {
                System.out.println("Optional result is not present");
            }
        }
    }

    /**
     * handleOpenAction is called whenever the user calls the open action.
     * If the array is not full, fileChooser shows an open dialog.
     * If the file is not null, then continue to reading the file.
     * The file is read and then buffered line by line as long as there is
     * space in the array. Only numbers are added to the array,
     * everything else is ignored.
     * @param actionEvent  ActionEvent sent by the caller
     */
    @FXML
    public void handleOpenAction(ActionEvent actionEvent) throws IOException {
        // Check to see if the array is not full
        if ( !checkArrayFull() ) {
            // Retrieve the current window, in order to display the dialog
            Window window = ( (MenuItem) actionEvent.getTarget() ).getParentPopup().getScene().getWindow();

            // Dialog is displayed, an potential file is retrieved
            File file = fileChooser.showOpenDialog(window);

            // If no file was retrieved, end the method
            if (file == null)
                return;

            // The file is sent to a file reader, who passes it to a buffered reader,
            // in order to read line by line of the file
            FileReader input = new FileReader(file);
            BufferedReader reader = new BufferedReader(input);

            // Current line & num;
            String line;
            int num;

            // Boolean to check if at least one number was found
            boolean found = false;

            /*
             * Method to get the file extension, serves no real use
            String extension = "";

            int i = file.getName().lastIndexOf('.');
            if (i > 0)
                extension = file.getName().substring(i + 1);
            */

            // Only run the loop as long as a line is available,
            // and there is space to add in the array.
            while ( (line = reader.readLine() ) != null && array.hasSpace() ) {
                // Try to parse the line into an acceptable number.
                // Otherwise, ignore the line
                try {
                    num = Integer.parseInt(line);

                    // If a number is found, add it to the array
                    found = true;
                    array.add(num);
                } catch (NumberFormatException ignored) {

                }
            }

            // If there is at least a remaining line to be read,
            // show an alert to the user
            if (reader.readLine() != null) {
                Dialog dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setContentText("No hay mas espacio para agregar elementos restantes");
                dialog.show();
            }

            // If at least one number was found, update the window.
            // Otherwise, show an alert to the user
            if (found) {
                handleShowAction(actionEvent);
            } else {
                Dialog dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setContentText("El archivo no contenia numeros");
                dialog.show();
            }

        }
    }

    /**
     * handleShowAction is called whenever user calls the show action
     * or whenever the window has to be updated.
     * If the source for the call is not the show menu item, update the window.
     * Otherwise, check if the array is empty.
     * @param actionEvent  ActionEvent sent by the caller
     */
    @FXML
    public void handleShowAction(ActionEvent actionEvent) {
        // If the source is not the menu item, update. Otherwise, check if array not empty
        if ( actionEvent.getSource() != menu_bar.getMenus().get(0).getItems().get(3) || !checkArrayEmpty() ) {

            // Remove all numbers displayed & then update with array
            text_area.clear();
            text_area.appendText( array.toString() );
        }
    }
    //endregion

    //region Initializable
    /**
     * initialize is called whenever the main_pane window is created for the first time.
     * Here all the necessary setup is realized.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Create the array with a maximum size of MAX_ARRAY
        array = new Array(MAX_ARRAY);

        // Set the number of rows
        text_area.setPrefRowCount(MAX_ARRAY);

        // Create a file chooser & set the title
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");

        // Set the initial directory to be user home, and add the acceptable file types,
        // text (.txt) & rich file text (.rtf)
        fileChooser.setInitialDirectory( new File( System.getProperty("user.home") ) );
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt") );
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("Rich Text Files (*.rtf)", "*.rtf") );

        // Scale the size of the text according to the height of the window
        vbox.heightProperty().addListener(
                (observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if (newValue.intValue() > 600) {
                        text_area.setStyle("-fx-font-size: 24;");
                    } else if (newValue.intValue() < 400) {
                        text_area.setStyle("-fx-font-size: 16;");
                    } else {
                        text_area.setStyle("-fx-font-size: 20;");
                    }
                }
        );
    }
    //endregion

    //region MainController
    /**
     * checkArrayEmpty checks if the array is empty, and if so,
     * alerts the user of this fact.
     * @return if array is empty
     */
    private boolean checkArrayEmpty() {
        if ( array.hasElements() )
            return false;

        Dialog dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setContentText("No hay ningun dato en el arreglo");
        dialog.show();

        return true;
    }

    /**
     * checkArrayFull checks if the array is full, and if so,
     * alerts the user of this fact.
     * @return if array is full
     */
    private boolean checkArrayFull() {
        if ( array.hasSpace() )
            return false;

        Dialog dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setContentText("No se pueden agregar mas datos");
        dialog.show();

        return true;
    }

    /**
     * checkNumberResult tries to parse a string into an integer.
     * If it fails to do so, the user receives an alert.
     * @return result parsed as a number, or null of it failed
     */
    private Integer checkNumberResult(String result) {
        // Initialize the number as null
        Integer num = null;

        // Try to parse the string into a number. If it fails to do so,
        // check to see whether the string was not a number, or
        // if the number could not fit in an int.
        // The user is then alerted of the fact
        try {
            num = Integer.parseInt(result);
        }  catch (NumberFormatException e) {
            Dialog dialog;

            try {
                // BigInteger can hold numbers bigger than int allows.
                new BigInteger(result);

                // If BigInteger was able to parse the number,
                // it just means the number could not fit into an int
                dialog = new Alert(Alert.AlertType.ERROR);
                dialog.setContentText("El elemento que se introdujo esta fuera de los limites aceptados");
                dialog.show();
            } catch (NumberFormatException e1) {
                // Otherwise, the problem was with the string not being a number,
                dialog = new Alert(Alert.AlertType.ERROR);
                dialog.setContentText("El elemento que se introdujo no es un numero");
                dialog.show();
            }
        }

        return num;
    }
    //endregion
}