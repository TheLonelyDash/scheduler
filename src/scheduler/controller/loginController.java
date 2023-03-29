package scheduler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import scheduler.model.alerts;

public class loginController implements Initializable {

    @FXML
    public void loginButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/mainMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }

    @FXML
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
