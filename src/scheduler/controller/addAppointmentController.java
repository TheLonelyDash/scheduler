package scheduler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import scheduler.model.user;
import scheduler.utilities.userSearch;

import java.sql.SQLException;
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
    @FXML TextField addAppCustomerText;

    @FXML ComboBox addAppUserIDCombo;
    @FXML ComboBox addAppStartTimePick;
    @FXML ComboBox addAppEndTimePick;
    @FXML ComboBox addAppContactPick;
    @FXML ComboBox addAppTypeCombo;

    @FXML DatePicker addAppStartDatePick;
    @FXML DatePicker addAppEndDatePick;

    /***
     * Converts the local date and time to Eastern Standard Time.
     * @param time the local time passed into the argument to be converted to EST
     * @return returns the time in Eastern Standard Time
     */
    private ZonedDateTime convertToEST(LocalDateTime time){
        return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }

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

    /***
     * Method to populate the type combo box with options.
     */
    private void typeComboBox() {
        ObservableList<String> types = FXCollections.observableArrayList();
        types.addAll("Planning Session", "De-Briefing", "other");
        addAppTypeCombo.setItems(types);
    }

    @FXML
    private void userIDComboBox() throws SQLException {
        ObservableList<Integer> userIDs = FXCollections.observableArrayList();
        try {
            ObservableList<user> users = userSearch.getUsers();
            if (users != null) {
                for (user user: users) {
                    userIDs.add(user.getUserID());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addAppUserIDCombo.setItems(userIDs);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        typeComboBox();
        try {
            userIDComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        addAppSave.setText(rb.getString("addAppSave"));
        addAppCancel.setText(rb.getString("addAppCancel"));
        addAnAppointment.setText(rb.getString("addAnAppointment"));
    }
}
