package scheduler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import scheduler.model.alerts;

public class loginController implements Initializable {


    public void loginButtonClick(ActionEvent actionEvent) {
    }

    public void exitButtonClick(ActionEvent actionEvent) {
        if (alerts.alert("Exit", "Would you like to close this application?", "You have not yet logged in.")){
            System.exit(0);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I got initialized!");
    }

}
