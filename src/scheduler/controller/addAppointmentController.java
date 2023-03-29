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

public class addAppointmentController implements Initializable {

    public void addAppSaveClick(ActionEvent actionEvent) throws IOException {
        if (alerts.alert("Add Appointment?", "Are you sure you'd like to save?", "Your changes will be saved")){
            Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/appointments.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Appointments");
            stage.show();
        }
    }

    public void addAppCancelClick(ActionEvent actionEvent) throws IOException {
        if (alerts.alert("Cancel?", "Are you sure you'd like to cancel?", "Your changes will be lost.")){
            Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/appointments.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Appointments");
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I initialized the add appointment thingy.");
    }
}
