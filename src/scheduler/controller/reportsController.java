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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import scheduler.model.appointment;
import scheduler.utilities.appointmentSearch;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;
import java.util.ResourceBundle;

public class reportsController implements Initializable {

    @FXML Button reportBackButton;
    @FXML Label reportScheduler;
    @FXML Tab scheduleTab;
    @FXML Tab totalAppTab;
    @FXML Tab totalCountryTab;
    static ObservableList<appointment> appointments;
    @FXML TableView<appointment> reportsScheduleTableView;
    @FXML TableColumn<?, ?> appIDCol;
    @FXML TableColumn titleCol;
    @FXML TableColumn<?, ?> typeCol1;
    @FXML TableColumn<?, ?> descriptionCol;
    @FXML TableColumn<?, ?> startCol;
    @FXML TableColumn<?, ?> endCol;
    @FXML TableColumn<?, ?> custIDCol;

    @FXML TextField planningSessionsField;
    @FXML TextField debriefingField;
    @FXML TextField otherField;

    @FXML TextField january;
    @FXML TextField february;
    @FXML TextField march;
    @FXML TextField april;
    @FXML TextField may;
    @FXML TextField june;
    @FXML TextField july;
    @FXML TextField august;
    @FXML TextField september;
    @FXML TextField october;
    @FXML TextField november;
    @FXML TextField december;


    public void totalAppointments() {

        ObservableList<String> planningSessions = FXCollections.observableArrayList();
        ObservableList<String> debriefings = FXCollections.observableArrayList();
        ObservableList<String> other = FXCollections.observableArrayList();

        ObservableList<Integer> jan = FXCollections.observableArrayList();
        ObservableList<Integer> feb = FXCollections.observableArrayList();
        ObservableList<Integer> mar = FXCollections.observableArrayList();
        ObservableList<Integer> apr = FXCollections.observableArrayList();
        ObservableList<Integer> mayMonth = FXCollections.observableArrayList();
        ObservableList<Integer> jun = FXCollections.observableArrayList();
        ObservableList<Integer> jul = FXCollections.observableArrayList();
        ObservableList<Integer> aug = FXCollections.observableArrayList();
        ObservableList<Integer> sep = FXCollections.observableArrayList();
        ObservableList<Integer> oct = FXCollections.observableArrayList();
        ObservableList<Integer> nov = FXCollections.observableArrayList();
        ObservableList<Integer> dec = FXCollections.observableArrayList();

        try {
            ObservableList<appointment> appointments = appointmentSearch.getAllAppointments();

            if (appointments != null) {
                for (appointment appointment : appointments) {
                    String type = appointment.getType();
                    LocalDate date = appointment.getStartDate();

                    if (type.equals("Planning Session")) {planningSessions.add(type);}
                    if (type.equals("De-Briefing")) {debriefings.add(type);}
                    if (type.equals("other")) {other.add(type);}

                    if (date.getMonth().equals(Month.of(1))) {jan.add(date.getMonthValue());}
                    if (date.getMonth().equals(Month.of(2))) {feb.add(date.getMonthValue());}
                    if (date.getMonth().equals(Month.of(3))) {mar.add(date.getMonthValue());}
                    if (date.getMonth().equals(Month.of(4))) {apr.add(date.getMonthValue());}
                    if (date.getMonth().equals(Month.of(5))) {mayMonth.add(date.getMonthValue());}
                    if (date.getMonth().equals(Month.of(6))) {jun.add(date.getMonthValue());}
                    if (date.getMonth().equals(Month.of(7))) {jul.add(date.getMonthValue());}
                    if (date.getMonth().equals(Month.of(8))) {aug.add(date.getMonthValue());}
                    if (date.getMonth().equals(Month.of(9))) {sep.add(date.getMonthValue());}
                    if (date.getMonth().equals(Month.of(10))) {oct.add(date.getMonthValue());}
                    if (date.getMonth().equals(Month.of(11))) {nov.add(date.getMonthValue());}
                    if (date.getMonth().equals(Month.of(12))) {dec.add(date.getMonthValue());}
                }
            }
            planningSessionsField.setText(String.valueOf(planningSessions.size()));
            debriefingField.setText(String.valueOf(debriefings.size()));
            otherField.setText(String.valueOf(other.size()));

            january.setText(String.valueOf(jan.size()));
            february.setText(String.valueOf(feb.size()));
            march.setText(String.valueOf(mar.size()));
            april.setText(String.valueOf(apr.size()));
            may.setText(String.valueOf(mayMonth.size()));
            june.setText(String.valueOf(jun.size()));
            july.setText(String.valueOf(jun.size()));
            august.setText(String.valueOf(aug.size()));
            september.setText(String.valueOf(sep.size()));
            october.setText(String.valueOf(oct.size()));
            november.setText(String.valueOf(nov.size()));
            december.setText(String.valueOf(dec.size()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void reportBackButtonClick(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/mainMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        if (Locale.getDefault().getLanguage() == "en") {stage.setTitle("Main Menu");}
        else{stage.setTitle("Menu Principal");}
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        totalAppointments();

        try {
            appointments = appointmentSearch.getAllAppointments();
            reportsScheduleTableView.setItems(appointments);
            appIDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            custIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        }
        catch (Exception e){
            e.printStackTrace();
        }


        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        reportBackButton.setText(rb.getString("back"));
        reportScheduler.setText(rb.getString("reportScheduler"));
        scheduleTab.setText(rb.getString("schedule"));
        totalAppTab.setText(rb.getString("totalAppTab"));
        totalCountryTab.setText(rb.getString("totalCountryTab"));
    }
}
