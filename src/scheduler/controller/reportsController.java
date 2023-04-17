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
import scheduler.model.alerts;
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

/***
 * This class controls the reports gui.
 */
public class reportsController implements Initializable {

    //Buttons
    @FXML private Button reportBackButton;
    @FXML private Button getResultButton;

    //Labels
    @FXML private Label reportScheduler;
    @FXML private Label usa;
    @FXML private Label uk;
    @FXML private Label totalAppLabel;
    @FXML private Label monthLabel;
    @FXML private Label typeLabel;
    @FXML private Label resultLabel;
    @FXML private Label totalsLabel;
    @FXML private Label customersPerCountry;

    //Combo Boxes
    @FXML private ComboBox monthComboBox;
    @FXML private ComboBox typeComboBox;

    //Tabs
    @FXML private Tab scheduleTab;
    @FXML private Tab totalAppTab;
    @FXML private Tab totalCountryTab;

    //Lists
    static ObservableList<appointment> appointments;

    //Table Views
    @FXML private TableView<appointment> reportsScheduleTableView;

    //Table Columns
    @FXML private TableColumn<?, ?> appIDCol;
    @FXML private TableColumn titleCol;
    @FXML private TableColumn<?, ?> typeCol1;
    @FXML private TableColumn<?, ?> descriptionCol;
    @FXML private TableColumn<?, ?> startCol;
    @FXML private TableColumn<?, ?> endCol;
    @FXML private TableColumn<?, ?> custIDCol;

    //Text Fields
    @FXML private TextField canadaText;
    @FXML private TextField usaText;
    @FXML private TextField ukText;



    public void getResultClick(ActionEvent actionEvent) throws SQLException {
        ObservableList<appointment> appointments = appointmentSearch.getAllAppointments();
        ObservableList<Integer> monthAndType = FXCollections.observableArrayList();
        boolean condition = checkBoxes();
        if (condition == true){
            if(appointments != null){
                String typeBox = (String) typeComboBox.getSelectionModel().getSelectedItem();
                String typeBox2 = (String) typeComboBox.getSelectionModel().getSelectedItem();

                if (typeBox == "Planning Session" || typeBox == "Séance de Planification"){
                    typeBox = "Planning Session";
                    typeBox2 = "Séance de Planification";
                }
                else if (typeBox == "De-Briefing" || typeBox == "Compte Rendu") {
                    typeBox = "De-Briefing";
                    typeBox2 = "Compte Rendu";
                }
                else if (typeBox == "Other" || typeBox == "Autre"){
                    typeBox = "Other";
                    typeBox2 = "Autre";
                }

                int monthBox = Integer.parseInt((String) monthComboBox.getSelectionModel().getSelectedItem());
                System.out.println(monthBox);

                for (appointment appointment: appointments){
                    String type = appointment.getType();
                    LocalDate month = appointment.getStartDate();
                    if (type.equals(typeBox) && month.getMonth().equals(Month.of(monthBox))){
                        monthAndType.add(1);
                    }
                    if (type.equals(typeBox2) && month.getMonth().equals(Month.of(monthBox))){
                        monthAndType.add(1);
                    }
                }
            }
        }
        totalsLabel.setText(String.valueOf(monthAndType.size()));
    }

    /***
     * This method checks whether the two combo boxes have been selected.
     * @return
     */
    public boolean checkBoxes(){
        if(monthComboBox.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Missing!", "You are missing a Month!", "Please select a Month.");}
            else{alerts.alertE("Manquant !", "Il vous manque un mois !", "Veuillez sélectionner un mois.");}
            return false;
        }
        if(typeComboBox.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Missing!", "You are missing a Type!", "Please select a Type.");}
            else{alerts.alertE("Manquant !", "Il vous manque un type !", "Veuillez sélectionner un type.");}
            return false;
        }
        return true;
    }

    /***
     * This method populates the type Combo Box for selection
     */
    private void typeComboBox() {
        ObservableList<String> types = FXCollections.observableArrayList();
        if(Locale.getDefault().getLanguage() == "en"){types.addAll("Planning Session", "De-Briefing", "Other");}
        else{types.addAll("Séance de Planification", "Compte Rendu", "Autre");}
        typeComboBox.setItems(types);
    }

    /***
     * This method populates the month Combo Box for selection
     */
    private void monthComboBox() {
        ObservableList<String> month = FXCollections.observableArrayList();
        month.addAll("1", "2", "3", "4", "5", "6", "7", "8", "9","10", "11", "12");
        monthComboBox.setItems(month);
    }

    /***
     * This method counts the number of customers in each country.  It creates a list for each country, parses through
     * all the customers and adds them to their respective lists.  Then, their sizes are produced in their respective textfields.
     */
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
        typeComboBox();
        monthComboBox();
        totalCountries();

        try {
            appointments = appointmentSearch.getAllAppointments();
            reportsScheduleTableView.setItems(appointments);
            appIDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
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
        customersPerCountry.setText(rb.getString("customersPerCountry"));
        usa.setText(rb.getString("usa"));
        uk.setText(rb.getString("uk"));
    }
}
