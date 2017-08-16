package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mauriciog on 8/16/16.
 */
public class AddController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        Window window = ((Node) (actionEvent.getSource())).getScene().getWindow();

        if (window instanceof Stage){
            ( (Stage) window).close();
        }
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent actionEvent) {
        Window window = ((Node) (actionEvent.getSource())).getScene().getWindow();

        if (window instanceof Stage){
            ( (Stage) window).close();
        }
    }
}


