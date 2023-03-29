package scheduler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scheduler.model.alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addCustomer implements Initializable {

    public void saveClick(ActionEvent actionEvent) throws IOException {
        if (alerts.alert("Add Customer?", "Are you sure you'd like to save?", "Your changes will be saved")){
            Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/customers.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Customers");
            stage.show();
        }
    }

    public void cancelClick(ActionEvent actionEvent) throws IOException {
        if (alerts.alert("Cancel?", "Are you sure you'd like to cancel?", "Your changes will be lost.")){
            Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/customers.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Customers");
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I initialized the add customer stuff.");
    }
}
