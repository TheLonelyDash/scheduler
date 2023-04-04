package scheduler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import scheduler.model.alerts;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class mainMenuController implements Initializable {

    @FXML Button mainMenuAppointments;
    @FXML Button mainMenuCustomers;
    @FXML Button mainMenuReports;
    @FXML Button mainMenuExit;



    public void mainMenuAppointmentsClick(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        if (Locale.getDefault().getLanguage() == "en"){stage.setTitle("Appointments Menu");}
        else {stage.setTitle("Menu des Rendez-Vous");}
        stage.show();
    }

    public void mainMenuCustomersClick(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/customers.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        if (Locale.getDefault().getLanguage() == "en"){stage.setTitle("Customers Menu");}
        else {stage.setTitle("Menu des Clientes");}
        stage.show();
    }


    public void mainMenuReportsClick(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/reports.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        if (Locale.getDefault().getLanguage() == "en"){stage.setTitle("Reports Menu");}
        else {stage.setTitle("Menu des Rapports");}
        stage.show();
    }

    /***
     * This creates an alert when the user clicks on the log out button from the main menue.  If they choose OK to log out, it will direct them back to the log in screen.  Otherwise it will remove the alert and allow them to continue.
     * @param actionEvent The event that will be used to change scenes from the log out button.
     * @throws IOException The exception handler for the log out button.
     */
    public void mainMenuLogOutClick(ActionEvent actionEvent) throws IOException{
        if (Locale.getDefault().getLanguage() == "en"){
            if (alerts.alert("Log Out?", "Are you sure you'd like to log out?", "Your changes will NOT be lost.")){
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/loginMenu.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Log In!");
                stage.show();
            }
        }
        else {
            if (alerts.alert("Se déconnecter?", "Voulez-vous vraiment vous déconnecter ?", "Vos modifications ne seront PAS perdues.")){
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/loginMenu.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Connexion!");
                stage.show();
            }
        }


    }

    /***
     * This is the initializable method for the main menu.
     * @param url this is the URL for the initializable method of the main menu.
     * @param resourceBundle this is the resourceBundle for the main menu.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        mainMenuAppointments.setText(rb.getString("appointments"));
        mainMenuCustomers.setText(rb.getString("customers"));
        mainMenuReports.setText((rb.getString("reports")));
        mainMenuExit.setText(rb.getString("exit"));
    }
}
