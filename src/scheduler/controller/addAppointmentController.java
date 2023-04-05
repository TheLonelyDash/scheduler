package scheduler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import scheduler.model.alerts;
import scheduler.model.time;
import java.time.*;
import java.time.chrono.ChronoZonedDateTime;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class addAppointmentController implements Initializable {

    @FXML Button addAppSave;
    @FXML Button addAppCancel;

    @FXML Label addAnAppointment;

    @FXML TextField addAppIDText;
    @FXML TextField addAppDescriptionText;
    @FXML TextField addAppTitleText;
    @FXML TextField addAppTypeText;
    @FXML TextField addAppCustomerText;
    @FXML TextField addAppUserText;

    @FXML ComboBox addAppStartTimePick;
    @FXML ComboBox addAppEndTimePick;
    @FXML ComboBox addAppContactPick;

    @FXML DatePicker addAppStartDatePick;
    @FXML DatePicker addAppEndDatePick;


    public void addAppSaveClick(ActionEvent actionEvent) throws IOException {
        if (Locale.getDefault().getLanguage() == "en"){
            if (alerts.alert("Add Appointment?", "Are you sure you'd like to save?", "Your changes will be saved")){
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/appointments.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Appointments");
                stage.show();
            }
        }
        else {
            if (alerts.alert("Ajouter un rendez-vous?", "Voulez-vous vraiment enregistrer?", "Vos modifications seront enregistrées")){
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/appointments.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Rendez-Vous");
                stage.show();
            }
        }
    }

    public void addAppCancelClick(ActionEvent actionEvent) throws IOException {
        if (Locale.getDefault().getLanguage() == "en"){
            if (alerts.alert("Cancel?", "Are you sure you'd like to cancel?", "Your changes will be lost.")){
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/appointments.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Appointments");
                stage.show();
            }
        }
        else {
            if (alerts.alert("Annuler?", "Voulez-vous vraiment annuler?", "Vos modifications seront perdues.")){
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/appointments.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Rendez-Vous");
                stage.show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        addAppSave.setText(rb.getString("addAppSave"));
        addAppCancel.setText(rb.getString("addAppCancel"));
        addAnAppointment.setText(rb.getString("addAnAppointment"));
    }
}
