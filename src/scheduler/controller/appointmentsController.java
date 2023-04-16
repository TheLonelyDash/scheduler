package scheduler.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import scheduler.model.alerts;
import scheduler.utilities.appointmentSearch;
import scheduler.model.appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/***
 * This class controls the appointments gui.
 */
public class appointmentsController implements Initializable {

    //Labels
    @FXML Label appointmentsAppointmentsLabel;

    //Buttons
    @FXML Button addAppointment;
    @FXML Button updateAppointment;
    @FXML Button deleteAppointment;
    @FXML Button backAppointment;

    //Radio Buttons
    @FXML RadioButton weekly;
    @FXML RadioButton monthly;
    @FXML RadioButton all;

    //ToggleGroups
    @FXML private ToggleGroup toggleView;

    //Lists
    static ObservableList<appointment> appointments;

    //TableViews
    @FXML TableView<appointment> appointmentsTableView;

    //TableColumns
    @FXML private TableColumn<?, ?> appointment_IDCol;
    @FXML private TableColumn<?, ?> titleCol;
    @FXML private TableColumn<?, ?> descriptionCol;
    @FXML private TableColumn<?, ?> locationCol;
    @FXML private TableColumn<?, ?> contactCol;
    @FXML private TableColumn<?, ?> typeCol;
    @FXML private TableColumn<?, ?> startDateCol;
    @FXML private TableColumn<?, ?> endDateCol;
    @FXML private TableColumn<?, ?> customer_IDCol;
    @FXML private TableColumn<?, ?> user_IDCol;


    /***
     * This method controls the three radio buttons on the appointments screen. Each calls a method to organize the
     * appointments by upcoming week, upcoming, month, or all appointments.
     * @param event
     */
    @FXML
    void viewToggle(ActionEvent event) {
        if (all.isSelected()) {
            try {
                appointments = appointmentSearch.getAllAppointments();
                appointmentsTableView.setItems(appointments);
                appointmentsTableView.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (toggleView.getSelectedToggle().equals(monthly)) {
            try {
                appointments = appointmentSearch.getAppointmentsByMonth();
                appointmentsTableView.setItems(appointments);
                appointmentsTableView.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (toggleView.getSelectedToggle().equals(weekly)) {
            try {
                appointments = appointmentSearch.getAppointmentsByWeek();
                appointmentsTableView.setItems(appointments);
                appointmentsTableView.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /***
     * Method that takes the user to the add appointments gui.
     * @param actionEvent
     * @throws IOException
     */
    public void addAppointmentClick(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/addAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        if (Locale.getDefault().getLanguage() == "en"){stage.setTitle("Add Appointment");}
        else {stage.setTitle("Ajouter un rendez-vous");}
        stage.show();
    }


    /***
     * Method that takes the user to the update appointments gui by first taking the selected item.  If no item has
     * been selected, it throws an alert telling the user to make a selection.
     * @param event
     * @throws IOException
     */
    public void updateAppointmentClick(ActionEvent event) throws IOException{
        updateAppointmentController.getSelectedAppointment(appointmentsTableView.getSelectionModel().getSelectedItem());
        if (appointmentsTableView.getSelectionModel().getSelectedItem() != null) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/scheduler/view/updateAppointment.fxml"));
                stage.setScene(new Scene(scene));
                if (Locale.getDefault().getLanguage() == "en"){stage.setTitle("Update Appointment");}
                else{stage.setTitle("Mettre à jour le rendez-vous");}
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("No Selection", "You must make a selection.", "Please choose an appointment to update.");}
            else{alerts.alert("Pas de choix", "Vous devez faire une sélection.", "Veuillez choisir un rendez-vous à mettre à jour.");}
        }
    }


    /***
     * This method deletes an appointment from the appointments table.  If the user does not make a selection, an alert is thrown
     * reminding them to make a selection.  An alert is also thrown asking the user if they are sure they would like to delete the
     * appointment. If the deletion is successful, an alert will tell the user the appointment information that was deleted.
     * @param actionEvent
     * @throws SQLException
     */
    public void deleteAppointmentClick(ActionEvent actionEvent) throws SQLException {
        appointment selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        Optional<ButtonType> result;
        Alert alert;
        if (selectedAppointment == null) {
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("No Selection", "You must make a selection.", "Please choose an appointment to delete.");}
            else{alerts.alert("Pas de choix", "Vous devez faire une sélection.", "Veuillez choisir un rendez-vous à supprimer.");}
        } else if (appointmentsTableView.getSelectionModel().getSelectedItem() != null) {
            if (Locale.getDefault().getLanguage()=="en"){
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to continue? Deleting this appointment is permanent.");
                result = alert.showAndWait();}
            else {alert = new Alert(Alert.AlertType.CONFIRMATION, "Souhaitez-vous continuer? La suppression de ce rendez-vous est définitive.");
                result = alert.showAndWait();}
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {
                    boolean deleteSuccessful = appointmentSearch.deleteAppointment(appointmentsTableView.getSelectionModel().getSelectedItem().getAppointment_ID());
                    if (deleteSuccessful) {
                        if(Locale.getDefault().getLanguage()=="en"){alerts.alertI("Delete Complete!", "The " + selectedAppointment.getType() + " appointment with " + selectedAppointment.getContactName() + " has been cancelled!\nDeleted Appointment ID: " + selectedAppointment.getAppointment_ID(), "It's done.");}
                        else{alerts.alertI("Supprimer terminé!", "Le rendez-vous " + selectedAppointment.getType() + " avec " + selectedAppointment.getContactName() +" a été annulé.", "C'est fait.");}
                        appointments = appointmentSearch.getAllAppointments();
                        appointmentsTableView.setItems(appointments);
                        appointmentsTableView.refresh();
                    }
                    else {
                        if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error!", "Something happened.  It didn't work. Sorry, no deleting today.", "It's done.");}
                        else{alerts.alertE("Erreur!", "Quelque chose est arrivé. Cela n'a pas fonctionné. Désolé, pas de suppression aujourd'hui.", "C'est fait.");}
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /***
     * Method that takes the user back to the main menu.
     * @param actionEvent
     * @throws IOException
     */
    public void backAppointmentClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/mainMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        if(Locale.getDefault().getLanguage() == "en"){stage.setTitle("Main Menu");}
        else{stage.setTitle("Menu principal");}
        stage.show();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        weekly.setToggleGroup(toggleView);
        monthly.setToggleGroup(toggleView);
        all.setToggleGroup(toggleView);

        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        appointmentsAppointmentsLabel.setText(rb.getString("appointments"));
        addAppointment.setText(rb.getString("addAppointment"));
        updateAppointment.setText(rb.getString("updateAppointment"));
        deleteAppointment.setText(rb.getString("deleteAppointment"));
        backAppointment.setText(rb.getString("back"));
        weekly.setText(rb.getString("weekly"));
        monthly.setText(rb.getString("monthly"));
        all.setText(rb.getString("all"));
        descriptionCol.setText(rb.getString("description"));
        titleCol.setText(rb.getString("title"));
        locationCol.setText(rb.getString("location"));
        contactCol.setText(rb.getString("contact"));
        typeCol.setText(rb.getString("type"));
        startDateCol.setText(rb.getString("startdateandtime"));
        endDateCol.setText(rb.getString("enddateandtime"));

        try {
            appointments = appointmentSearch.getAllAppointments();
            appointmentsTableView.setItems(appointments);
            appointment_IDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDateCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endDateCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            customer_IDCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
            user_IDCol.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
