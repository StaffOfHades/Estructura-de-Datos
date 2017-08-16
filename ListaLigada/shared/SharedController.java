package shared;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import main.DoubleLinkedList;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;

/**
 * SharedController is the class responsible for handling the shared_pane.fxml window.
 * It controls all interaction with the user, as well as all the background work.
 *
 * @version 1.0
 * @author Mauricio Graciano Álvarez, Eder Cozatl Xicoyencatl, Emmanuel De Los Santos Castro 
 *
 * 149605, 145468, 151975
 * Septemeber 27, 2016
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
@SuppressWarnings("Duplicates")
public class SharedController implements Initializable {

    //region Properties
    @FXML private TextArea text_area;
    @FXML private MenuItem visibility;
    @FXML private VBox vbox;

    private DoubleLinkedList linkedList;
    private boolean showData;
    //endregion

    //region FXML
    /**
     * handleEraseAction is called whenever the user calls the erase action.
     * If the linked list is not empty, it asks the user for an int to be found & erased in
     * the linked list. If no match is found, the user receives an alert.
     * @param actionEvent ActionEvent sent by the caller
     */
    @FXML
    public void handleEraseAction(ActionEvent actionEvent) {
        // Check to see if linked list is not empty
        if ( !checkArrayEmpty() ) {
            // Ask the user for a number, parses it, and returns it.
            // If the operation failed, the user is alerted of the reason why,
            // and a null value is returned.
            Integer num = createNumberDialog("Eliminar");

            // If a number was returned, start looking in the linked list
            if (num != null) {
                // If a match was found, update the window.
                // Otherwise, show an alert to the user
                if ( linkedList.erase(num) ) {
                    handleShowAction(actionEvent);
                } else {
                    Dialog dialog = new Alert(Alert.AlertType.WARNING);
                    dialog.setContentText("No se encontro el elemento \'" + num + "\'");
                    dialog.show();
                }
            }
        }
    }

    /**
     * handleExitAction is called whenever the user calls the exit action.
     * Simply asks the platform to exit the applet and handle everything.
     */
    @FXML
    public void handleExitAction() {
        Platform.exit();
    }

    /**
     * handleInsertAction is called whenever the user calls the insert action.
     * If the linked list is not full, it asks the user for an int to be added into the linked list.
     * @param actionEvent  ActionEvent sent by the caller
     */
    @FXML
    public void handleInsertAction(ActionEvent actionEvent) throws IOException {
        // Check to see if the linked list is not full
        if ( !checkArrayFull() ) {
            // Ask the user for a number, parses it, and returns it.
            // If the operation failed, the user is alerted of the reason why,
            // and a null value is returned.
            Integer num = createNumberDialog("Agregar");
            // If a number was returned, add it to the linked list,
            // & update the window
            if (num != null) {
                linkedList.add(num);
                handleShowAction(actionEvent);
            }
        }
    }

    /**
     * handleOpenAction is called whenever the user calls the open action.
     * If the linked list is not full, fileChooser shows an open dialog.
     * If the file is not null, then continue to reading the file.
     * The file is read and then buffered line by line as long as there is
     * space in the linked list. Only numbers are added to the linked list,
     * everything else is ignored.
     * @param actionEvent  ActionEvent sent by the caller
     */
    @FXML
    public void handleOpenAction(ActionEvent actionEvent) throws IOException {
        // Check to see if the linked list is not full
        if ( !checkArrayFull() ) {
            // Retrieve the current window, in order to display the dialog
            Window window = ( (MenuItem) actionEvent.getTarget() ).getParentPopup().getScene().getWindow();

            // Create a file chooser & set the title
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Abrir Archivo de Texto");

            // Set the initial directory to be user home, and add the acceptable file types,
            // text (.txt) & rich file text (.rtf)
            fileChooser.setInitialDirectory( new File( System.getProperty("user.home") ) );
            fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt") );
            fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("Rich Text Files (*.rtf)", "*.rtf") );

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
            // and there is space to add in the linked list.
            while ( (line = reader.readLine() ) != null && linkedList.hasSpace() ) {
                // Try to parse the line into an acceptable number.
                // Otherwise, ignore the line
                try {
                    num = Integer.parseInt(line);

                    // Add the number to linked list and set found to true
                    found = true;
                    linkedList.add(num);
                } catch (NumberFormatException ignored) { }
            }

            // If there is at least a remaining line to be read,
            // show an alert to the user
            if (reader.readLine() != null) {
                Dialog dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setContentText("No hay mas espacio para agregar elementos restantes");
                dialog.show();
            }

            // Close the streams to avoid memory leaks.
            reader.close();
            input.close();

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
     * handleFindAction is called whenever user calls the find action.
     * If the linked list is not empty, search the linked list for a match.
     * @param actionEvent ActionEvent sent by the caller
     */
    @FXML
    public void handleFindAction(ActionEvent actionEvent) {
        // Check to see if linked list is not empty
        if (!checkArrayEmpty()) {
            // Ask the user for a number, parses it, and returns it.
            // If the operation failed, the user is alerted of the reason why,
            // and a null value is returned.
            Integer num = createNumberDialog("Buscar");

            // If a number was returned, inform the user if a match
            // is found in the linked list.
            if (num != null) {
                // Create the body text according to whether a match is found
                String text = "El numero " + num + (linkedList.hasElement(num) ? " " : " no ") + "existe en la lista doblemente ligada" ;

                // Inform the user
                Dialog dialog = new Alert(Alert.AlertType.INFORMATION);
                dialog.setContentText(text);
                dialog.show();
            }
        }
    }

    /**
     * handleShowAction is called whenever user calls the show action
     * or whenever the window has to be updated.
     * If the source for the call is show menu item, handle the show/hide logic.
     * Otherwise, if show data is true, update the window.
     * @param actionEvent  ActionEvent sent by the caller
     */
    @FXML
    public void handleShowAction(ActionEvent actionEvent) {
        //Before performing any action, erase the window
        text_area.clear();

        // If the source of the event is the menu item, handle the show/hide logic.
        // Otherwise, if show data is true, just add all the numbers in the linked list to the window
        if ( actionEvent.getSource() == visibility) {
            // If show data is false, add the numbers in the linked list to the window,
            // & set the menu item text to hide.
            // Otherwise, set the menu item to show
            if (!showData) {
                text_area.appendText( linkedList.toString() );
                visibility.setText("Ocultar Datos");
            } else
                visibility.setText("Mostrar Datos");

            // Lastly, invert the show data boolean
            showData = !showData;
        } else if (showData)
            text_area.appendText( linkedList.toString() );
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
        // Create the linked list
        linkedList = new DoubleLinkedList();

        // Data is shown from the start
        showData = true;

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

    //region SharedController
    /**
     * checkArrayEmpty checks if the linke list is empty, and if so,
     * alerts the user of this fact.
     * @return if linked list is empty
     */
    private boolean checkArrayEmpty() {
        if ( linkedList.hasElement() )
            return false;

        Dialog dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setContentText("No hay ningun dato en el arreglo");
        dialog.showAndWait();

        return true;
    }

    /**
     * checkArrayFull checks if the linked list is full, and if so,
     * alerts the user of this fact.
     * @return if linked list is full
     */
    private boolean checkArrayFull() {
        if ( linkedList.hasSpace() )
            return false;

        Dialog dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setContentText("No se pueden agregar mas datos");
        dialog.showAndWait();

        return true;
    }

    /**
     * checkNumberResult tries to parse a string into an integer.
     * If it fails to do so, the user receives an alert concerning
     * the reason of failure.
     * @return result parsed as a number, or null of it failed.
     */
    private Integer checkNumberResult(String result) {
        // Check to see if the string has any character at all
        if (result.trim().length() <= 0)
            return null;

        // Try to parse the string into a number,
        // removing any trailing/leading spaces.
        // If it fails to do so,
        // check to see whether the string was not a number,
        // or if the number could not fit in an int.
        // The user is then alerted of the fact.
        try {
            // Returns the number if possible.
            return Integer.parseInt( result.trim() );
        }  catch (NumberFormatException e) {
            Dialog dialog;

            try {
                // BigInteger can hold numbers bigger than int allows.
                new BigInteger( result.trim() );

                // If BigInteger was able to parse the number,
                // it just means the number could not fit into an int.
                dialog = new Alert(Alert.AlertType.ERROR);
                dialog.setContentText("El elemento que se introdujo esta fuera de los limites aceptados");
                dialog.showAndWait();
            } catch (NumberFormatException e1) {
                // Otherwise, the problem was with the string not being a number.
                dialog = new Alert(Alert.AlertType.ERROR);
                dialog.setContentText("El elemento que se introdujo no es un numero");
                dialog.showAndWait();
            }
        }

        return null;
    }

    /**
     * createNumberDialog create an input dialog that receives a string,
     * and then verifies if its a number. If successful, returns the number,
     * otherwise just return a null value.
     * @param title to be displayed in the dialog.
     * @return result parsed as a number, or null of it failed or is non-existent.
     */
    private Integer createNumberDialog(String title) {
        // Dialog with a text input is created, only showing the title.
        Dialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setContentText("");
        dialog.setHeaderText("");
        dialog.setGraphic(null);

        // Dialog is displayed, and an optianal string is retrieved.
        @SuppressWarnings("unchecked") Optional<String> result = dialog.showAndWait();

        // Determines if a result was returned by the dialog.
        if ( result.isPresent() )
            // Checks to see if the result is an acceptable number
            // returning it if successful, or null otherwise.
            return checkNumberResult( result.get() );
        else
            System.out.println("Optional result is not present");

        // If no result was found, return null value.
        return null;
    }
    //endregion
}