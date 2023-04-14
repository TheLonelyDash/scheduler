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
import scheduler.model.*;
import scheduler.utilities.appointmentSearch;
import scheduler.utilities.contactSearch;
import scheduler.utilities.customerSearch;
import scheduler.utilities.userSearch;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class updateAppointmentController implements Initializable {

    @FXML Label updateAnAppointment;
    @FXML Button upAppSave;
    @FXML Button upAppCancel;
    @FXML ComboBox upTypeCombo;
    @FXML ComboBox upUserIDCombo;
    @FXML ComboBox upAppContactPick;
    @FXML ComboBox upCustomerCombo;
    @FXML ComboBox upAppStartTimePick;
    @FXML ComboBox upAppEndTimePick;
    @FXML TextField upAppTitleText;
    @FXML TextField upAppDescriptionText;
    @FXML TextField locationCombo;
    @FXML TextField upAppIDText;

    @FXML DatePicker upAppStartDatePick;
    @FXML DatePicker upAppEndDatePick;

    private static appointment selectedAppointment;

    public static void getSelectedAppointment(appointment appointment) {
        selectedAppointment = appointment;
    }

    private ZonedDateTime convertTZ(LocalDateTime time, String zoneId) {
        return ZonedDateTime.of(time, ZoneId.of(zoneId));
    }

    public void upAppSaveClick(ActionEvent actionEvent) throws IOException {
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

    public void upAppCancelClick(ActionEvent actionEvent) throws IOException {
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

    private void typeComboBox() {
        ObservableList<String> types = FXCollections.observableArrayList();
        types.addAll("Planning Session", "De-Briefing", "other");
        upTypeCombo.setItems(types);
    }

    private void userIDComboBox() {
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
        upUserIDCombo.setItems(userIDs);
    }

    private void customerIDComboBox() {
        ObservableList<Integer> customerIDs = FXCollections.observableArrayList();
        try {
            ObservableList<customer> customers = customerSearch.getAllCustomers();
            if (customers != null) {
                for (customer customer: customers) {
                    customerIDs.add(customer.getCustomerID());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        upCustomerCombo.setItems(customerIDs);
    }

    private void contactComboBox() {
        ObservableList<String> contacts = FXCollections.observableArrayList();

        try {
            ObservableList<contactInfo> contactsList = contactSearch.getAllContacts();
            if (contacts != null){
                for (contactInfo contact: contactsList) {
                    if (!contacts.contains(contact.getContactName())) {
                        contacts.add(contact.getContactName());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        upAppContactPick.setItems(contacts);
    }

    private void timeComboBoxes() {
        ObservableList<String> time = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(6, 0);
        LocalTime endTime = LocalTime.of(20, 0);
        time.add(startTime.toString());
        while (startTime.isBefore(endTime)) {
            startTime = startTime.plusMinutes(5);
            time.add(startTime.toString());
        }

        upAppStartTimePick.setItems(time);
        upAppEndTimePick.setItems(time);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeComboBox();
        userIDComboBox();
        customerIDComboBox();
        contactComboBox();
        timeComboBoxes();

        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        upAppCancel.setText(rb.getString("addAppCancel"));
        upAppSave.setText(rb.getString("addAppSave"));
        updateAnAppointment.setText(rb.getString("updateAnAppointment"));


        try {
            appointment appointment = appointmentSearch.getAppByID(selectedAppointment.getAppointment_ID());
            ZonedDateTime zonedStartTime = convertTZ(appointment.getStartDate().atTime(appointment.getStartTime().toLocalTime()), String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            ZonedDateTime zonedEndTime = convertTZ(appointment.getEndDate().atTime(appointment.getEndTime().toLocalTime()), String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));

            if (appointment != null) {
                upAppContactPick.getSelectionModel().select(appointment.getContactName());
                upAppTitleText.setText(appointment.getTitle());
                upAppDescriptionText.setText(appointment.getDescription());
                locationCombo.setText(appointment.getLocation());
                upTypeCombo.getSelectionModel().select(appointment.getType());
                upUserIDCombo.getSelectionModel().select(Integer.valueOf(appointment.getUser_ID()));
                upAppIDText.setText(String.valueOf(appointment.getAppointment_ID()));
                upAppStartDatePick.setValue(appointment.getStartDate());
                upAppStartTimePick.getSelectionModel().select(String.valueOf(zonedStartTime.toLocalTime()));
                upAppEndDatePick.setValue(appointment.getEndDate());
                upAppEndTimePick.getSelectionModel().select(String.valueOf(zonedEndTime.toLocalTime()));
                upCustomerCombo.getSelectionModel().select(Integer.valueOf(appointment.getCustomer_ID()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
