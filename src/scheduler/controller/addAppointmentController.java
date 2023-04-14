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

import java.sql.SQLException;
import java.time.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class addAppointmentController implements Initializable {

    @FXML private Button addAppSave;
    @FXML private Button addAppCancel;

    @FXML private Label addAnAppointment;

    @FXML private ComboBox<Integer> customerIDCombo;
    @FXML private ComboBox<Integer> addAppUserIDCombo;
    @FXML private ComboBox addAppStartTimePick;
    @FXML private ComboBox addAppEndTimePick;
    @FXML private ComboBox<String> addAppContactPick;
    @FXML private ComboBox addAppTypeCombo;

    @FXML private DatePicker addAppStartDatePick;
    @FXML private DatePicker addAppEndDatePick;

    private ZonedDateTime startDTConversion;
    private ZonedDateTime endDTConversion;

    @FXML private TextField addAppTitleText;
    @FXML private TextField addAppDescriptionText;
    @FXML private TextField addAppLocation;
    @FXML private TextField addAppIDText;

    /***
     * Converts the local date and time to Eastern Standard Time.
     * @param time the local time passed into the argument to be converted to EST
     * @return returns the time in Eastern Standard Time
     */
    private ZonedDateTime convertToEST(LocalDateTime time){
        return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }

    public void addAppSaveClick(ActionEvent actionEvent) throws IOException, SQLException {
        addAppIDText.setText(getID());
        boolean condition = appointmentCheck(
                addAppTitleText.getText(),
                addAppDescriptionText.getText(),
                addAppLocation.getText(),
                addAppIDText.getText()
                );
        if (condition == true){
            boolean valid = appointmentSearch.addAppointment(
                    addAppContactPick.getSelectionModel().getSelectedItem(),
                    addAppTitleText.getText(),
                    addAppDescriptionText.getText(),
                    addAppLocation.getText(),
                    addAppTypeCombo.getSelectionModel().getSelectedItem().toString(),
                    LocalDateTime.of(addAppStartDatePick.getValue(), LocalTime.parse((CharSequence) addAppStartTimePick.getSelectionModel().getSelectedItem())),
                    LocalDateTime.of(addAppEndDatePick.getValue(), LocalTime.parse((CharSequence) addAppEndTimePick.getSelectionModel().getSelectedItem())),
                    customerIDCombo.getSelectionModel().getSelectedItem(),
                    addAppUserIDCombo.getSelectionModel().getSelectedItem()
            );
            if (valid != true){
                if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Save failed.", "What the heck?! Are you sure everything is filled in?", "Please check that everything is valid.");}
                else {alerts.alert("Échec de la sauvegarde.", "Que diable?! Es-tu sûr que tout est rempli ?", "Veuillez vérifier que tout est valide.");}
            }
            else {
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/appointments.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                if (Locale.getDefault().getLanguage() == "en"){stage.setTitle("Appointments");}
                else {stage.setTitle("Rendez-vous");}
                stage.show();
            }
        }
    }

    private String getID() throws SQLException {
        int [] arr = new int[appointmentSearch.getAllAppointments().size()];
        for(int i = 0; i < appointmentSearch.getAllAppointments().size(); i++){
            arr[i] = appointmentSearch.getAllAppointments().get(i).getAppointment_ID();
        }
        int n = arr.length;
        int n1 = n+1;
        int answer = n*n1/2;
        for (int i = 0; i < n; i++){
            answer -= arr[i];
        }
        return String.valueOf(answer);
    }


    private boolean appointmentCheck(String title, String description, String location, String appointmentID){
        if (addAppContactPick.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a contact!", "Please choose a contact.");}
            else {alerts.alert("Informations manquantes", "Hé! Vous n'avez pas choisi de contact!", "Veuillez choisir un contact.");}
            return false;
        }

        if (customerIDCombo.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a customer ID!", "Please choose a customer ID.");}
            else {alerts.alert("Informations manquantes", "Hé! Vous n'avez pas choisi de customer ID!", "Veuillez choisir un customer ID.");}
            return false;
        }

        if (addAppUserIDCombo.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a user ID!", "Please choose a user ID.");}
            else {alerts.alert("Informations manquantes", "Hé! Vous n'avez pas choisi de user ID!", "Veuillez choisir un user ID.");}
            return false;
        }

        if (title.isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a title!", "Please choose a title.");}
            else {alerts.alert("Informations manquantes", "Hé! Vous n'avez pas choisi de title!", "Veuillez choisir un title.");}
            return false;
        }

        if (description.isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a description!", "Please choose a description.");}
            else {alerts.alert("Informations manquantes", "Hé! Vous n'avez pas choisi de description!", "Veuillez choisir un description.");}
            return false;
        }

        if (location.isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a location!", "Please choose a location.");}
            else {alerts.alert("Informations manquantes", "Hé! Vous n'avez pas choisi de location!", "Veuillez choisir un location.");}
            return false;
        }

        if (appointmentID.isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose an appointment ID!", "Please choose an appointment ID.");}
            else {alerts.alert("Informations manquantes", "Hé! Vous n'avez pas choisi de appointment ID!", "Veuillez choisir un appointment ID.");}
            return false;
        }

        if(addAppTypeCombo.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a type!", "Please choose a type.");}
            else {alerts.alert("Informations manquantes", "Hé! Vous n'avez pas choisi de type!", "Veuillez choisir un type.");}
            return false;
        }

        LocalTime startTime = LocalTime.parse((CharSequence) addAppStartTimePick.getSelectionModel().getSelectedItem());
        LocalTime endTime = LocalTime.parse((CharSequence) addAppEndTimePick.getSelectionModel().getSelectedItem());

        if (endTime.isBefore(startTime)) {
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Incorrect information", "Hey! Check your times are correct!", "Ensure your times are valid.");}
            else {alerts.alert("Informations incorrectes", "Hé! Vous vérifiez que vos temps sont corrects!", "Assurez-vous que vos temps sont valides.");}
            return false;
        };

        LocalDate startDate = addAppStartDatePick.getValue();
        LocalDate endDate = addAppEndDatePick.getValue();

        if (!startDate.equals(endDate)){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Incorrect information", "Hey! Check your dates are correct!", "Ensure your dates are valid.");}
            else {alerts.alert("Informations incorrectes", "Hé! Vous vérifiez que vos dates sont corrects!", "Assurez-vous que vos dates sont valides.");}
            return false;
        };

        if (addAppStartDatePick.getValue() == null){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a start date!", "Please choose a start date.");}
            else {alerts.alert("Informations manquantes", "Hé! Vous n'avez pas choisi de start date!", "Veuillez choisir un start date.");}
            return false;
        }

        if(addAppStartTimePick.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a start time!", "Please choose a start time.");}
            else {alerts.alert("Informations manquantes", "Hé! Vous n'avez pas choisi de start time!", "Veuillez choisir un start time.");}
            return false;
        }

        if (addAppEndDatePick.getValue() == null){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a end date!", "Please choose a end date.");}
            else {alerts.alert("Informations manquantes", "Hé! Vous n'avez pas choisi de end date!", "Veuillez choisir un end date.");}
            return false;
        }

        if (addAppEndDatePick.getValue().isBefore(addAppStartDatePick.getValue())){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Check your start and end dates.", "Check your start and end dates.");}
            else {alerts.alert("Informations manquantes", "Vérifiez vos dates de début et de fin.", "Vérifiez vos dates de début et de fin.");}
            return false;
        }

        if (addAppEndTimePick.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a end time!", "Please choose a end time.");}
            else {alerts.alert("Informations manquantes", "Hé! Vous n'avez pas choisi de end time!", "Veuillez choisir un end time.");}
            return false;
        }

        LocalDateTime selectedStart = startDate.atTime(startTime);
        LocalDateTime selectedEnd = endDate.atTime(endTime);
        LocalDateTime possAppointmentStart;
        LocalDateTime possAppointmentEnd;

        try {
            ObservableList<appointment> appointments = appointmentSearch.getAppointmentsByCustomerID((Integer) customerIDCombo.getSelectionModel().getSelectedItem());
            for (appointment appointment: appointments) {
                possAppointmentStart = appointment.getStartDate().atTime(appointment.getStartTime().toLocalTime());
                possAppointmentEnd = appointment.getEndDate().atTime(appointment.getEndTime().toLocalTime());

                if (possAppointmentStart.isAfter(selectedStart) && possAppointmentStart.isBefore(selectedEnd)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Appointments must not overlap with existing customer appointments.");
                    alert.showAndWait();
                    return false;
                } else if (possAppointmentEnd.isAfter(selectedStart) && possAppointmentEnd.isBefore(selectedEnd)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Appointments must not overlap with existing customer appointments.");
                    alert.showAndWait();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        startDTConversion = convertToEST(LocalDateTime.of(addAppStartDatePick.getValue(), LocalTime.parse((CharSequence) addAppStartTimePick.getSelectionModel().getSelectedItem())));
        endDTConversion = convertToEST(LocalDateTime.of(addAppEndDatePick.getValue(), LocalTime.parse((CharSequence) addAppEndTimePick.getSelectionModel().getSelectedItem())));

        if (startDTConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Error.", "Appointments must be in business hours!", "Please choose a start time.");}
            else {alerts.alert("Erreur", "Les rendez-vous doivent être en heures ouvrables!", "Veuillez choisir une heure de début.");}
            return false;
        }

        if (endDTConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Error.", "Appointments must be in business hours!", "Please choose an end time.");}
            else {alerts.alert("Erreur", "Les rendez-vous doivent être en heures ouvrables!", "Veuillez choisir une heure de fin.");}
            return false;
        }

        if (startDTConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Error.", "Appointments must be in business hours!", "Please choose a start time.");}
            else {alerts.alert("Erreur", "Les rendez-vous doivent être en heures ouvrables!", "Veuillez choisir une heure de début.");}
            return false;
        }

        if (endDTConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Error.", "Appointments must be in business hours!", "Please choose an end time.");}
            else {alerts.alert("Erreur", "Les rendez-vous doivent être en heures ouvrables!", "Veuillez choisir une heure de fin.");}
            return false;
        }
        return true;
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
        addAppUserIDCombo.setItems(userIDs);
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
        customerIDCombo.setItems(customerIDs);
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
        addAppContactPick.setItems(contacts);
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

        addAppStartTimePick.setItems(time);
        addAppEndTimePick.setItems(time);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        typeComboBox();
        userIDComboBox();
        customerIDComboBox();
        contactComboBox();
        timeComboBoxes();


        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        addAppSave.setText(rb.getString("addAppSave"));
        addAppCancel.setText(rb.getString("addAppCancel"));
        addAnAppointment.setText(rb.getString("addAnAppointment"));
    }
}
