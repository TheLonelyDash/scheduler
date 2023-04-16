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
import java.time.*;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class updateAppointmentController implements Initializable {

    //Dates and Times
    private ZonedDateTime StartDateTimeConversion;
    private ZonedDateTime EndDateTimeConversion;

    //Labels
    @FXML private Label updateAnAppointment;
    @FXML private Label upAppTitle;
    @FXML private Label upAppDescription;
    @FXML private Label upAppType;
    @FXML private Label upAppLocation;
    @FXML private Label upApptStartDate;
    @FXML private Label upAppStartTime;
    @FXML private Label upAppEndDate;
    @FXML private Label upAppEndTime;
    @FXML private Label upAppContact;
    @FXML private Label upAppCustomer;
    @FXML private Label upAppUser;

    //Buttons
    @FXML private Button upAppSave;
    @FXML private Button upAppCancel;

    //Combo Boxes
    @FXML private ComboBox<String> upTypeCombo;
    @FXML private ComboBox<Integer> upUserIDCombo;
    @FXML private ComboBox<String> upAppContactPick;
    @FXML private ComboBox<Integer> upCustomerCombo;
    @FXML private ComboBox<String> upAppStartTimePick;
    @FXML private ComboBox<String> upAppEndTimePick;

    //Text Fields
    @FXML private TextField upAppTitleText;
    @FXML private TextField upAppDescriptionText;
    @FXML private TextField locationCombo;
    @FXML private TextField upAppIDText;

    //Date Pickers
    @FXML DatePicker upAppStartDatePick;
    @FXML DatePicker upAppEndDatePick;


    /***
     * The selected appointment for modification.
     */
    private static appointment selectedAppointment;


    /***
     * Method that gets the selected appointment
     * @param appointment
     */
    public static void getSelectedAppointment(appointment appointment) {
        selectedAppointment = appointment;
    }

    /***
     * A method that converts the local time zone into the time zone of the user.
     * @param time
     * @param zoneId
     * @return
     */
    private ZonedDateTime convertTZ(LocalDateTime time, String zoneId) {
        return ZonedDateTime.of(time, ZoneId.of(zoneId));
    }


    /***
     * This method saves the updates of a selected appointment.
     * @param event
     * @throws IOException
     */
    public void upAppSaveClick(ActionEvent event) throws IOException {
        boolean condition = validateAppointment(
                upAppTitleText.getText(),
                upAppDescriptionText.getText(),
                locationCombo.getText(),
                upAppIDText.getText()
        );
        if (condition == true) {
            try {
                boolean condition2 = appointmentSearch.updateAppointment(
                        upAppContactPick.getSelectionModel().getSelectedItem(),
                        upAppTitleText.getText(),
                        upAppDescriptionText.getText(),
                        locationCombo.getText(),
                        upTypeCombo.getSelectionModel().getSelectedItem(),
                        LocalDateTime.of(upAppStartDatePick.getValue(), LocalTime.parse(upAppStartTimePick.getSelectionModel().getSelectedItem())),
                        LocalDateTime.of(upAppEndDatePick.getValue(), LocalTime.parse(upAppEndTimePick.getSelectionModel().getSelectedItem())),
                        upCustomerCombo.getSelectionModel().getSelectedItem(),
                        upUserIDCombo.getSelectionModel().getSelectedItem(),
                        Integer.parseInt(upAppIDText.getText()));

                if (condition2 == true) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully updated appointment");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
                        try {
                            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            Parent scene = FXMLLoader.load(getClass().getResource("/scheduler/view/appointments.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Failed to update appointment", "Honesly, nobody knows why.");}
                    else{alerts.alertE("Erreur", "Échec de la mise à jour du rendez-vous", "Honnêtement, personne ne sait pourquoi.");}
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /***
     * Method that allows the user to abandon their changes and get redirected to the appointments gui.
     * @param actionEvent
     * @throws IOException
     */
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


    /***
     * This method populates the type combo box with options.
     */
    private void typeComboBox() {
        ObservableList<String> types = FXCollections.observableArrayList();
        if(Locale.getDefault().getLanguage() == "en"){types.addAll("Planning Session", "De-Briefing", "Other");}
        else{types.addAll("Séance de Planification", "Compte Rendu", "Autre");}
        upTypeCombo.setItems(types);
    }


    /***
     * This method populates the User ID combo box with the available user IDs
     */
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


    /***
     * This method populates the customer ID combo box with the available customer information.
     */
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


    /***
     * This method populates the contact combo box with the names of the available contacts.
     */
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


    /***
     * This method populates the time comboboxes with available times
     */
    private void timeComboBoxes() {
        ObservableList<String> time = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(7, 30);
        LocalTime endTime = LocalTime.of(20, 0);
        time.add(startTime.toString());
        while (startTime.isBefore(endTime)) {
            startTime = startTime.plusMinutes(5);
            time.add(startTime.toString());
        }
        upAppStartTimePick.setItems(time);
        upAppEndTimePick.setItems(time);
    }


    /***
     * This method checks all the inputs to ensure that they are completed.  If not completed, an alert informs the
     * user what is missing.
     * @param title
     * @param description
     * @param location
     * @param appointmentId
     * @return
     */
    private boolean validateAppointment(String title, String description, String location, String appointmentId){
        if (upAppContactPick.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Contact is a requirement.", "Please provide a contact.");}
            else{alerts.alertE("Erreur", "Le contact est une exigence.", "Veuillez fournir un contact.");}
            return false;
        }

        if (title.isEmpty()){
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Title is a requirement", "Please provide a title.");}
            else{alerts.alertE("Erreur", "Le titre est une exigence", "Veuillez fournir un titre.");}
            return false;
        }

        if (description.isEmpty()){
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Description is a requirement", "Please provide a description.");}
            else{alerts.alertE("Erreur", "La description est obligatoire", "Veuillez fournir une description.");}
            return false;
        }

        if (location.isEmpty()){
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Location is a requirement", "Please provide a location.");}
            else{alerts.alertE("Erreur", "L'emplacement est une exigence", "Veuillez fournir un emplacement.");}
            return false;
        }

        if (upTypeCombo.getSelectionModel().isEmpty()) {
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Type is a requirement", "Please provide a type.");}
            else{alerts.alertE("Erreur", "Le type est une exigence", "Veuillez fournir un type.");}
            return false;
        }

        if (appointmentId.isEmpty()) {
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Appointment ID is a requirement", "Please provide an Appointment ID.");}
            else{alerts.alertE("Erreur", "L'ID de rendez-vous est obligatoire", "Veuillez fournir un ID de rendez-vous.");}
            return false;
        }

        if (upAppStartDatePick.getValue() == null) {
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Start Date is a requirement", "Please provide a Start Date.");}
            else{alerts.alertE("Erreur", "La date de début est une exigence", "Veuillez fournir une date de début.");}
            return false;
        }

        if (upAppStartTimePick.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Start Time is a requirement", "Please provide a Start Time.");}
            else{alerts.alertE("Erreur", "L'heure de début est une exigence", "Veuillez fournir une heure de début.");}
            return false;
        }

        if (upAppEndDatePick.getValue() == null){
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "End Date is a requirement", "Please provide a End Date.");}
            else{alerts.alertE("Erreur", "La date de fin est une exigence", "Veuillez fournir une date de fin.");}
            return false;
        }

        if (upAppEndTimePick.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "End Time is a requirement", "Please provide a End Time.");}
            else{alerts.alertE("Erreur", "L'heure de fin est une exigence", "Veuillez fournir une heure de fin.");}
            return false;
        }

        if (upAppEndDatePick.getValue().isBefore(upAppStartDatePick.getValue())) {
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Start Date must be before End Date", "Please check your dates.");}
            else{alerts.alertE("Erreur", "La date de début doit être antérieure à la date de fin", "Veuillez vérifier vos dates.");}
            return false;
        }

        if (upCustomerCombo.getSelectionModel().isEmpty()) {
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Customer ID is a requirement", "Please provide a Customer ID.");}
            else{alerts.alertE("Erreur", "L'identifiant client est obligatoire", "Veuillez fournir un identifiant client.");}
            return false;
        }

        if (upUserIDCombo.getSelectionModel().isEmpty()) {
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "User ID is a requirement", "Please provide a User ID.");}
            else{alerts.alertE("Erreur", "L'ID utilisateur est requis", "Veuillez fournir un ID utilisateur.");}
            return false;
        }

        LocalTime startTime = LocalTime.parse((CharSequence) upAppStartTimePick.getSelectionModel().getSelectedItem());
        LocalTime endTime = LocalTime.parse((CharSequence) upAppEndTimePick.getSelectionModel().getSelectedItem());

        if (endTime.isBefore(startTime)) {
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Start time must be prior to end time!", "Please provide correct times.");}
            else{alerts.alertE("Erreur", "L'heure de début doit être antérieure à l'heure de fin !", "Veuillez fournir des heures correctes.");}
            return false;
        };

        LocalDate startDate = upAppStartDatePick.getValue();
        LocalDate endDate = upAppEndDatePick.getValue();

        if (!startDate.equals(endDate)){
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Your appointment dates need to be on the same day.", "Please provide correct dates.");}
            else{alerts.alertE("Erreur", "Vos dates de rendez-vous doivent être le même jour.", "Veuillez fournir des dates correctes.");}
            return false;
        };

        // Check for overlapping appointments
        LocalDateTime selectedStart = startDate.atTime(startTime);
        LocalDateTime selectedEnd = endDate.atTime(endTime);
        LocalDateTime proposedAppointmentStart;
        LocalDateTime proposedAppointmentEnd;

        try {
            ObservableList<appointment> appointments = appointmentSearch.getAppointmentsByCustomerID((Integer) upCustomerCombo.getSelectionModel().getSelectedItem());
            for (appointment appointment: appointments) {
                proposedAppointmentStart = appointment.getStartDate().atTime(appointment.getStartTime().toLocalTime());
                proposedAppointmentEnd = appointment.getEndDate().atTime(appointment.getEndTime().toLocalTime());

                if (proposedAppointmentStart.isAfter(selectedStart) && proposedAppointmentStart.isBefore(selectedEnd)) {
                    if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Appointments can't overlap with existing appointments.", "Please provide correct dates.");}
                    else{alerts.alertE("Erreur", "Les rendez-vous ne peuvent pas se chevaucher avec des rendez-vous existants.", "Veuillez fournir des dates correctes.");}
                    return false;
                } else if (proposedAppointmentEnd.isAfter(selectedStart) && proposedAppointmentEnd.isBefore(selectedEnd)) {
                    if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Appointments can't overlap with existing appointments.", "Please provide correct dates.");}
                    else{alerts.alertE("Erreur", "Les rendez-vous ne peuvent pas se chevaucher avec des rendez-vous existants.", "Veuillez fournir des dates correctes.");}
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StartDateTimeConversion = convertToEST(LocalDateTime.of(upAppStartDatePick.getValue(), LocalTime.parse((CharSequence) upAppStartTimePick.getSelectionModel().getSelectedItem())));
        EndDateTimeConversion = convertToEST(LocalDateTime.of(upAppEndDatePick.getValue(), LocalTime.parse((CharSequence) upAppEndTimePick.getSelectionModel().getSelectedItem())));

        if (StartDateTimeConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Appointments can only be within the business hours of 08:00 - 22:00 EST.", "Please provide correct times.");}
            else{alerts.alertE("Erreur", "Les rendez-vous ne peuvent avoir lieu que pendant les heures ouvrables de 08h00 à 22h00 HNE.", "Veuillez fournir des heures correctes.");}
            return false;
        }

        if (EndDateTimeConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Appointments can only be within the business hours of 08:00 - 22:00 EST.", "Please provide correct times.");}
            else{alerts.alertE("Erreur", "Les rendez-vous ne peuvent avoir lieu que pendant les heures ouvrables de 08h00 à 22h00 HNE.", "Veuillez fournir des heures correctes.");}
            return false;
        }

        if (StartDateTimeConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Appointments can only be within the business hours of 08:00 - 22:00 EST.", "Please provide correct times.");}
            else{alerts.alertE("Erreur", "Les rendez-vous ne peuvent avoir lieu que pendant les heures ouvrables de 08h00 à 22h00 HNE.", "Veuillez fournir des heures correctes.");}
            return false;
        }

        if (EndDateTimeConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Appointments can only be within the business hours of 08:00 - 22:00 EST.", "Please provide correct times.");}
            else{alerts.alertE("Erreur", "Les rendez-vous ne peuvent avoir lieu que pendant les heures ouvrables de 08h00 à 22h00 HNE.", "Veuillez fournir des heures correctes.");}
            return false;
        }

        return true;
    }


    /***
     * This is a method that converts a time into Eastern Standard Time
     * @param time
     * @return
     */
    private ZonedDateTime convertToEST(LocalDateTime time) {
        return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }

    /***
     * This method converts any time into another. General conversion.
     * @param time
     * @param zoneId
     * @return
     */
    private ZonedDateTime convertToTimeZone(LocalDateTime time, String zoneId) {
        return ZonedDateTime.of(time, ZoneId.of(zoneId));
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

        upAppTitle.setText(rb.getString("title"));
        upAppDescription.setText(rb.getString("description"));
        upAppType.setText(rb.getString("type"));
        upAppLocation.setText(rb.getString("location"));
        upApptStartDate.setText(rb.getString("startdate"));
        upAppStartTime.setText(rb.getString("starttime"));
        upAppEndDate.setText(rb.getString("enddate"));
        upAppEndTime.setText(rb.getString("endtime"));
        upAppContact.setText(rb.getString("contact"));
        upAppCustomer.setText(rb.getString("customerID"));
        upAppUser.setText(rb.getString("userID"));



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
