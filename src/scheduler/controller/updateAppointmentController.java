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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import scheduler.model.alerts;
import scheduler.model.contactInfo;
import scheduler.model.customer;
import scheduler.model.user;
import scheduler.utilities.contactSearch;
import scheduler.utilities.customerSearch;
import scheduler.utilities.userSearch;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class updateAppointmentController implements Initializable {

    @FXML Label updateAnAppointment;
    @FXML Button upAppSave;
    @FXML Button upAppCancel;



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
        upAppCancel.setText(rb.getString("addAppCancel"));
        upAppSave.setText(rb.getString("addAppSave"));
        updateAnAppointment.setText(rb.getString("updateAnAppointment"));
    }
}
