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

    private ZonedDateTime StartDateTimeConversion;
    private ZonedDateTime EndDateTimeConversion;

    @FXML Label updateAnAppointment;
    @FXML Button upAppSave;
    @FXML Button upAppCancel;
    @FXML ComboBox<String> upTypeCombo;
    @FXML ComboBox<Integer> upUserIDCombo;
    @FXML ComboBox<String> upAppContactPick;
    @FXML ComboBox<Integer> upCustomerCombo;
    @FXML ComboBox<String> upAppStartTimePick;
    @FXML ComboBox<String> upAppEndTimePick;
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

    public void upAppSaveClick(ActionEvent event) throws IOException {
        boolean valid = validateAppointment(
                upAppTitleText.getText(),
                upAppDescriptionText.getText(),
                locationCombo.getText(),
                upAppIDText.getText()
        );

        if (valid) {
            try {

                boolean success = appointmentSearch.updateAppointment(
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

                if (success) {
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
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialog");
                            alert.setContentText("Load Screen Error.");
                            alert.showAndWait();
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to update appointment");
                    Optional<ButtonType> result = alert.showAndWait();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        /*
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

         */
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









    private boolean validateAppointment(String title, String description, String location, String appointmentId){
        if (upAppContactPick.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Contact is required.");
            alert.showAndWait();
            return false;
        }

        if (title.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Title is required.");
            alert.showAndWait();
            return false;
        }

        if (description.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Description is required.");
            alert.showAndWait();
            return false;
        }

        if (location.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Location is required.");
            alert.showAndWait();
            return false;
        }

        if (upTypeCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Type is required.");
            alert.showAndWait();
            return false;
        }

        if (appointmentId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointment ID is required.");
            alert.showAndWait();
            return false;
        }

        if (upAppStartDatePick.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Start Date is required.");
            alert.showAndWait();
            return false;
        }

        if (upAppStartTimePick.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Start Time is required.");
            alert.showAndWait();
            return false;
        }

        if (upAppEndDatePick.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("End Date is required.");
            alert.showAndWait();
            return false;
        }

        if (upAppEndDatePick.getValue().isBefore(upAppStartDatePick.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("End Date must be after Start Date.");
            alert.showAndWait();
            return false;
        }

        if (upAppEndTimePick.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("End Time is required.");
            alert.showAndWait();
            return false;
        }

        if (upCustomerCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Customer ID is required.");
            alert.showAndWait();
            return false;
        }

        if (upUserIDCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("User ID is required.");
            alert.showAndWait();
            return false;
        }

        // additional date validation

        LocalTime startTime = LocalTime.parse((CharSequence) upAppStartTimePick.getSelectionModel().getSelectedItem());
        LocalTime endTime = LocalTime.parse((CharSequence) upAppEndTimePick.getSelectionModel().getSelectedItem());

        if (endTime.isBefore(startTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointment start time must be before end time.");
            alert.showAndWait();
            return false;
        };

        LocalDate startDate = upAppStartDatePick.getValue();
        LocalDate endDate = upAppEndDatePick.getValue();

        if (!startDate.equals(endDate)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must start and end on the same date.");
            alert.showAndWait();
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
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Appointments must not overlap with existing customer appointments.");
                    alert.showAndWait();
                    return false;
                } else if (proposedAppointmentEnd.isAfter(selectedStart) && proposedAppointmentEnd.isBefore(selectedEnd)) {
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

        // check if between business hours
        StartDateTimeConversion = convertToEST(LocalDateTime.of(upAppStartDatePick.getValue(), LocalTime.parse((CharSequence) upAppStartTimePick.getSelectionModel().getSelectedItem())));
        EndDateTimeConversion = convertToEST(LocalDateTime.of(upAppEndDatePick.getValue(), LocalTime.parse((CharSequence) upAppEndTimePick.getSelectionModel().getSelectedItem())));

        if (StartDateTimeConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        if (EndDateTimeConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        if (StartDateTimeConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        if (EndDateTimeConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        return true;
    }


    private ZonedDateTime convertToEST(LocalDateTime time) {
        return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }

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
