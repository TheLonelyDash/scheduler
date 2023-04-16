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

    @FXML private Button reportBackButton;
    @FXML private Label reportScheduler;
    @FXML private Label usa;
    @FXML private Label uk;
    @FXML private Label appByMonthLabel;
    @FXML private Label janLabel;
    @FXML private Label febLabel;
    @FXML private Label marLabel;
    @FXML private Label aprLabel;
    @FXML private Label mayLabel;
    @FXML private Label junLabel;
    @FXML private Label julLabel;
    @FXML private Label augLabel;
    @FXML private Label sepLabel;
    @FXML private Label octLabel;
    @FXML private Label novLabel;
    @FXML private Label decLabel;
    @FXML private Label appByTypeLabel;
    @FXML private Label planningSessions;
    @FXML private Label debriefing;
    @FXML private Label other;
    @FXML private Label customersPerCountry;

    @FXML private Tab scheduleTab;
    @FXML private Tab totalAppTab;
    @FXML private Tab totalCountryTab;
    static ObservableList<appointment> appointments;
    @FXML private TableView<appointment> reportsScheduleTableView;
    @FXML private TableColumn<?, ?> appIDCol;
    @FXML private TableColumn titleCol;
    @FXML private TableColumn<?, ?> typeCol1;
    @FXML private TableColumn<?, ?> descriptionCol;
    @FXML private TableColumn<?, ?> startCol;
    @FXML private TableColumn<?, ?> endCol;
    @FXML private TableColumn<?, ?> custIDCol;

    @FXML private TextField planningSessionsField;
    @FXML private TextField debriefingField;
    @FXML private TextField otherField;

    @FXML private TextField january;
    @FXML private TextField february;
    @FXML private TextField march;
    @FXML private TextField april;
    @FXML private TextField may;
    @FXML private TextField june;
    @FXML private TextField july;
    @FXML private TextField august;
    @FXML private TextField september;
    @FXML private TextField october;
    @FXML private TextField november;
    @FXML private TextField december;

    @FXML private TextField canadaText;
    @FXML private TextField usaText;
    @FXML private TextField ukText;


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

        appIDCol.setText(rb.getString("appointmentID"));
        titleCol.setText(rb.getString("title"));
        typeCol1.setText(rb.getString("type"));
        descriptionCol.setText(rb.getString("description"));
        startCol.setText(rb.getString("startdateandtime"));
        endCol.setText(rb.getString("enddateandtime"));
        custIDCol.setText(rb.getString("customerID"));
        appByMonthLabel.setText(rb.getString("appByMonth"));
        janLabel.setText(rb.getString("jan"));
        febLabel.setText(rb.getString("feb"));
        marLabel.setText(rb.getString("mar"));
        aprLabel.setText(rb.getString("apr"));
        mayLabel.setText(rb.getString("may"));
        junLabel.setText(rb.getString("jun"));
        julLabel.setText(rb.getString("jul"));
        augLabel.setText(rb.getString("aug"));
        sepLabel.setText(rb.getString("sep"));
        octLabel.setText(rb.getString("oct"));
        novLabel.setText(rb.getString("nov"));
        decLabel.setText(rb.getString("dec"));
        appByTypeLabel.setText(rb.getString("appByType"));
        planningSessions.setText(rb.getString("planningSession"));
        debriefing.setText(rb.getString("debriefing"));
        other.setText(rb.getString("other"));
        customersPerCountry.setText(rb.getString("customersPerCountry"));
        usa.setText(rb.getString("usa"));
        uk.setText(rb.getString("uk"));
    }
}
