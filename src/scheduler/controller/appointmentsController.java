package scheduler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import scheduler.model.alerts;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
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



    public void addAppointmentClick(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/addAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Appointment");
        stage.show();
    }

    public void updateAppointmentClick(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/updateAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Appointment");
        stage.show();
    }

    public void deleteAppointmentClick(ActionEvent actionEvent) {
    }

    public void backAppointmentClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/mainMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }

    public void weeklyRadio(ActionEvent actionEvent) {
    }

    public void monthlyRadio(ActionEvent actionEvent) {
    }

    public void allRadio(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

            appointment_IDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_Id"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            startDateCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            endDateCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            customer_IDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            user_IDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        }

    }
}
