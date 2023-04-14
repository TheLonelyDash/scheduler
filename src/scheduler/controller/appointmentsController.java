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

public class appointmentsController implements Initializable {

    @FXML Label appointmentsAppointmentsLabel;
    @FXML Button addAppointment;
    @FXML Button updateAppointment;
    @FXML Button deleteAppointment;
    @FXML Button backAppointment;
    @FXML RadioButton weekly;
    @FXML RadioButton monthly;
    @FXML RadioButton all;
    @FXML private ToggleGroup toggleView;

    static ObservableList<appointment> appointments;
    @FXML TableView<appointment> appointmentsTableView;

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


    @FXML void viewsToggle(ActionEvent event){
        if (toggleView.getSelectedToggle().equals(all)){
            try {
                appointments = appointmentSearch.getAllAppointments();
                appointmentsTableView.setItems(appointments);
                appointmentsTableView.refresh();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        else if (toggleView.getSelectedToggle().equals(monthly)){
            try {
                appointments = appointmentSearch.getAppointmentsByMonth();
                appointmentsTableView.setItems(appointments);
                appointmentsTableView.refresh();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        else if (toggleView.getSelectedToggle().equals(weekly)){
            try {
                appointments = appointmentSearch.getAppointmentsByWeek();
                appointmentsTableView.setItems(appointments);
                appointmentsTableView.refresh();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void addAppointmentClick(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/addAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        if (Locale.getDefault().getLanguage() == "en"){stage.setTitle("Add Appointment");}
        else {stage.setTitle("Ajouter un rendez-vous");}
        stage.show();
    }

    public void updateAppointmentClick(ActionEvent event) throws IOException{
        updateAppointmentController.getSelectedAppointment(appointmentsTableView.getSelectionModel().getSelectedItem());

        if (appointmentsTableView.getSelectionModel().getSelectedItem() != null) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/scheduler/view/updateAppointment.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Update Existing Appointment");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Load Screen Error.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("You must select an appointment to update.");
            alert.showAndWait();
        }
        /*
        if (appointmentsTableView.getSelectionModel().getSelectedItem() != null){
            Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/updateAppointment.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            if (Locale.getDefault().getLanguage() == "en"){stage.setTitle("Update Appointment");}
            else { stage.setTitle("Mettre à jour le rendez-vous");}
            stage.show();
        }
        else {
            if (Locale.getDefault().getLanguage() == "en"){alerts.alert("Make a selection.", "Hey! You didn't choose an appointment to modify!", "Seriously, just choose one.");}
            else{alerts.alert("Choisissez.", "Hé! Vous n'avez pas choisi de rendez-vous à modifier!", "Sérieusement, choisissez-en un.");}

        }
*/
    }

    public void deleteAppointmentClick(ActionEvent actionEvent) throws SQLException {
        appointment selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("You must select an appointment to delete.");
            alert.showAndWait();
        } else if (appointmentsTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected appointment. Do you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {
                    boolean deleteSuccessful = appointmentSearch.deleteAppointment(appointmentsTableView.getSelectionModel().getSelectedItem().getAppointment_ID());

                    if (deleteSuccessful) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successful Delete");
                        alert.setContentText("Successfully deleted Appointment ID: " + selectedAppointment.getAppointment_ID() + " Type: " + selectedAppointment.getType());
                        alert.showAndWait();

                        appointments = appointmentSearch.getAllAppointments();
                        appointmentsTableView.setItems(appointments);
                        appointmentsTableView.refresh();
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setContentText("Could not delete appointment.");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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

        try {
            appointments = appointmentSearch.getAllAppointments();
            appointmentsTableView.setItems(appointments);
            appointment_IDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            customer_IDCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
            user_IDCol.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
