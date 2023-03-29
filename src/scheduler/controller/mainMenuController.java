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

public class mainMenuController implements Initializable {


    public void mainMenuAppointmentsClick(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Appointments Menu");
        stage.show();
    }


    public void mainMenuCustomersClick(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/customers.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Customers Menu");
        stage.show();
    }


    public void mainMenuReportsClick(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/reports.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reports Menu");
        stage.show();
    }

    public void mainMenuLogOutClick(ActionEvent actionEvent) throws IOException{
        if (alerts.alert("Log Out?", "Are you sure you'd like to log out?", "Your changes will NOT be lost.")){
            Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/loginMenu.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Log In!");
            stage.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I initialized the main Menuuuuuuu!");
    }
}
