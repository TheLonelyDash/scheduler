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
import scheduler.model.country;
import scheduler.model.customer;
import scheduler.utilities.appointmentSearch;
import scheduler.utilities.countrySearch;
import scheduler.utilities.customerSearch;

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

    @FXML TextField canadaText;
    @FXML TextField usaText;
    @FXML TextField ukText;


    public void totalCountries() {
        ObservableList<Integer> usa = FXCollections.observableArrayList();
        ObservableList<Integer> uk = FXCollections.observableArrayList();
        ObservableList<Integer> canada = FXCollections.observableArrayList();

        try {
            ObservableList<customer> customers = customerSearch.getAllCustomers();
            if (customers != null) {
                for (customer customer : customers) {
                    String count = customer.getCustomerCountry();
                    if (count.equals("U.S")){usa.add(1);}
                    if (count.equals("UK")){uk.add(1);}
                    if (count.equals("Canada")){canada.add(1);}
                }
            }
            canadaText.setText(String.valueOf(canada.size()));
            usaText.setText(String.valueOf(usa.size()));
            ukText.setText(String.valueOf(uk.size()));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * This method collects the information on all of the appointments required in reports.
     */
    public void totalAppointments() {

        ObservableList<String> planningSessions = FXCollections.observableArrayList();
        ObservableList<String> debriefings = FXCollections.observableArrayList();
        ObservableList<String> other = FXCollections.observableArrayList();

        ObservableList<Integer> janMonth = FXCollections.observableArrayList();
        ObservableList<Integer> febMonth = FXCollections.observableArrayList();
        ObservableList<Integer> marMonth = FXCollections.observableArrayList();
        ObservableList<Integer> aprMonth = FXCollections.observableArrayList();
        ObservableList<Integer> mayMonth = FXCollections.observableArrayList();
        ObservableList<Integer> junMonth = FXCollections.observableArrayList();
        ObservableList<Integer> julMonth = FXCollections.observableArrayList();
        ObservableList<Integer> augMonth = FXCollections.observableArrayList();
        ObservableList<Integer> sepMonth = FXCollections.observableArrayList();
        ObservableList<Integer> octMonth = FXCollections.observableArrayList();
        ObservableList<Integer> novMonth = FXCollections.observableArrayList();
        ObservableList<Integer> decMonth = FXCollections.observableArrayList();

        try {
            ObservableList<appointment> appointments = appointmentSearch.getAllAppointments();
            if (appointments != null) {
                for (appointment appointment : appointments) {
                    String type = appointment.getType();
                    LocalDate month = appointment.getStartDate();

                    if (type.equals("Planning Session")) {planningSessions.add(type);}
                    if (type.equals("De-Briefing")) {debriefings.add(type);}
                    if (type.equals("other")) {other.add(type);}

                    if (month.getMonth().equals(Month.of(1))) {janMonth.add(month.getMonthValue());}
                    if (month.getMonth().equals(Month.of(2))) {febMonth.add(month.getMonthValue());}
                    if (month.getMonth().equals(Month.of(3))) {marMonth.add(month.getMonthValue());}
                    if (month.getMonth().equals(Month.of(4))) {aprMonth.add(month.getMonthValue());}
                    if (month.getMonth().equals(Month.of(5))) {mayMonth.add(month.getMonthValue());}
                    if (month.getMonth().equals(Month.of(6))) {junMonth.add(month.getMonthValue());}
                    if (month.getMonth().equals(Month.of(7))) {julMonth.add(month.getMonthValue());}
                    if (month.getMonth().equals(Month.of(8))) {augMonth.add(month.getMonthValue());}
                    if (month.getMonth().equals(Month.of(9))) {sepMonth.add(month.getMonthValue());}
                    if (month.getMonth().equals(Month.of(10))) {octMonth.add(month.getMonthValue());}
                    if (month.getMonth().equals(Month.of(11))) {novMonth.add(month.getMonthValue());}
                    if (month.getMonth().equals(Month.of(12))) {decMonth.add(month.getMonthValue());}
                }
            }
            planningSessionsField.setText(String.valueOf(planningSessions.size()));
            debriefingField.setText(String.valueOf(debriefings.size()));
            otherField.setText(String.valueOf(other.size()));
            january.setText(String.valueOf(janMonth.size()));
            february.setText(String.valueOf(febMonth.size()));
            march.setText(String.valueOf(marMonth.size()));
            april.setText(String.valueOf(aprMonth.size()));
            may.setText(String.valueOf(mayMonth.size()));
            june.setText(String.valueOf(junMonth.size()));
            july.setText(String.valueOf(julMonth.size()));
            august.setText(String.valueOf(augMonth.size()));
            september.setText(String.valueOf(sepMonth.size()));
            october.setText(String.valueOf(octMonth.size()));
            november.setText(String.valueOf(novMonth.size()));
            december.setText(String.valueOf(decMonth.size()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /***
     * Method that takes the user back to the main menu.
     * @param actionEvent
     * @throws IOException
     */
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
        totalCountries();

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
