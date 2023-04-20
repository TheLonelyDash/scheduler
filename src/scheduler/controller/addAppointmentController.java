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
import java.sql.SQLException;
import java.time.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

/***
 * This class controls the gui when adding an appointment to the scheduler.
 */
public class addAppointmentController implements Initializable {

    //Buttons
    @FXML private Button addAppSave;
    @FXML private Button addAppCancel;

    //Labels
    @FXML private Label addAnAppointment;
    @FXML private Label addAppTitle;
    @FXML private Label addAppDescription;
    @FXML private Label addLocation;
    @FXML private Label addApptStartDate;
    @FXML private Label addAppStartTime;
    @FXML private Label addAppEndDate;
    @FXML private Label addAppEndTime;
    @FXML private Label addAppContact;
    @FXML private Label addAppType;
    @FXML private Label addAppCustomer;
    @FXML private Label addAppUser;

    //Combo Boxes
    @FXML private ComboBox<Integer> customerIDCombo;
    @FXML private ComboBox<Integer> addAppUserIDCombo;
    @FXML private ComboBox addAppStartTimePick;
    @FXML private ComboBox addAppEndTimePick;
    @FXML private ComboBox<String> addAppContactPick;
    @FXML private ComboBox addAppTypeCombo;

    //Date Pickers
    @FXML private DatePicker addAppStartDatePick;
    @FXML private DatePicker addAppEndDatePick;

    //Time Zone Conversions
    private ZonedDateTime startDTConversion;
    private ZonedDateTime endDTConversion;

    //TextFields
    @FXML private TextField addAppTitleText;
    @FXML private TextField addAppDescriptionText;
    @FXML private TextField addAppLocation;
    @FXML private TextField addAppIDText;



    /***
     * Converts the local date and time to Eastern Standard Time.
     * @param time the local time passed into the argument to be converted to EST
     * @return returns the time in Eastern Standard Time
     */
    private ZonedDateTime convertToEST(LocalDateTime time){
        return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }


    /***
     * This method retrieves a new appointment ID and then checks that all of the appointment textfields are filled out.  If they are filled out, it then checks that all of the combo boxes and date/times have been selected.
     * If everything is filled out, it adds the appointment using the addAppointment method and redirects the user back to the appointments screen.
     * @param actionEvent initializes the stage to move the user back to the appointments screen.
     * @throws IOException
     * @throws SQLException
     */
    public void addAppSaveClick(ActionEvent actionEvent) throws IOException, SQLException {
        addAppIDText.setText(getID());
        boolean condition = appointmentCheck(
                addAppTitleText.getText(),
                addAppDescriptionText.getText(),
                addAppLocation.getText(),
                addAppIDText.getText()
                );
        if (condition == true){
            boolean condition2 = appointmentSearch.addAppointment(
                    addAppContactPick.getSelectionModel().getSelectedItem(),
                    addAppTitleText.getText(),
                    addAppDescriptionText.getText(),
                    addAppLocation.getText(),
                    addAppTypeCombo.getSelectionModel().getSelectedItem().toString(),
                    LocalDateTime.of(addAppStartDatePick.getValue(), LocalTime.parse((CharSequence) addAppStartTimePick.getSelectionModel().getSelectedItem())),
                    LocalDateTime.of(addAppEndDatePick.getValue(), LocalTime.parse((CharSequence) addAppEndTimePick.getSelectionModel().getSelectedItem())),
                    customerIDCombo.getSelectionModel().getSelectedItem(),
                    addAppUserIDCombo.getSelectionModel().getSelectedItem()
            );
            if (condition2 != true){
                if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Save failed.", "What the heck?! Are you sure everything is filled in?", "Please check that everything is valid.");}
                else {alerts.alert("Échec de la sauvegarde.", "Que diable?! Es-tu sûr que tout est rempli ?", "Veuillez vérifier que tout est valide.");}
            }
            else {
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/appointments.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                if (Locale.getDefault().getLanguage() == "en"){stage.setTitle("Appointments");}
                else {stage.setTitle("Rendez-vous");}
                stage.show();
            }
        }
    }


    /***
     * This method was going to be used to appropriate a new appointment ID when an appointment is created.  It appears not to work,
     * but will be kept for future use.
     * @return
     * @throws SQLException
     */
    private String getID() throws SQLException {
        int[] arr = new int[appointmentSearch.getAllAppointments().size()];
        for(int i = 0; i < appointmentSearch.getAllAppointments().size(); i++){
            arr[i] = appointmentSearch.getAllAppointments().get(i).getAppointment_ID();
        }
        int n = arr.length;
        int n1 = n+1;
        int answer = n*n1/2;
        for (int i = 0; i < n; i++){
            answer -= arr[i];
        }
        return String.valueOf(answer);
    }


    /***
     * This method check that all the pickers and combo boxes have been utilized and returns an alert if they have not.
     * @param title
     * @param description
     * @param location
     * @param appointmentID
     * @return
     */
    private boolean appointmentCheck(String title, String description, String location, String appointmentID){

        if(addAppStartTimePick.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a start time!", "Please choose a start time.");}
            else {alerts.alert("Informations manquantes", "Hé! Vous n'avez pas choisi de start time!", "Veuillez choisir un start time.");}
            return false;
        }

        if (addAppEndTimePick.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose an end time!", "Please choose a end time.");}
            else {alerts.alert("Informations manquantes", "Hey ! Vous n'avez pas choisi d'heure de fin !", "Veuillez choisir une heure de fin.");}
            return false;
        }

        if (addAppContactPick.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a contact!", "Please choose a contact.");}
            else {alerts.alert("Informations manquantes", "Hé! Vous n'avez pas choisi de contact!", "Veuillez choisir un contact.");}
            return false;
        }

        if (customerIDCombo.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a customer ID!", "Please choose a customer ID.");}
            else {alerts.alert("Informations manquantes", "Hey ! Vous n'avez pas choisi d'identifiant client !", "Veuillez choisir un identifiant client.");}
            return false;
        }

        if (addAppUserIDCombo.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a user ID!", "Please choose a user ID.");}
            else {alerts.alert("Informations manquantes", "Hey ! Vous n'avez pas choisi d'ID utilisateur !", "Veuillez choisir un ID utilisateur.");}
            return false;
        }

        if (title.isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a title!", "Please choose a title.");}
            else {alerts.alert("Infos manquantes", "Hé ! Vous n'avez pas choisi de titre !", "Veuillez choisir un titre.");}
            return false;
        }

        if (description.isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a description!", "Please choose a description.");}
            else {alerts.alert("Informations manquantes", "Hé ! Vous n'avez pas choisi de description !", "Veuillez choisir une description.");}
            return false;
        }

        if (location.isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a location!", "Please choose a location.");}
            else {alerts.alert("Informations manquantes", "Hé ! Vous n'avez pas choisi de lieu !", "Veuillez choisir un lieu.");}
            return false;
        }

        if (appointmentID.isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose an appointment ID!", "Please choose an appointment ID.");}
            else {alerts.alert("Infos manquantes", "Hey ! Vous n'avez pas choisi d'identifiant de rendez-vous !", "Veuillez choisir un identifiant de rendez-vous.");}
            return false;
        }

        if(addAppTypeCombo.getSelectionModel().isEmpty()){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a type!", "Please choose a type.");}
            else {alerts.alert("Informations manquantes", "Hé ! Vous n'avez pas choisi de type !", "Veuillez choisir un type.");}
            return false;
        }

        if (addAppStartDatePick.getValue() == null){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a start date!", "Please choose a start date.");}
            else {alerts.alert("Informations manquantes", "Hey ! Vous n'avez pas choisi de date de début !", "Veuillez choisir une date de début.");}
            return false;
        }

        if (addAppEndDatePick.getValue() == null){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Hey! You didn't choose a end date!", "Please choose a end date.");}
            else {alerts.alert("Informations manquantes", "Hey ! Vous n'avez pas choisi de date de fin !", "Veuillez choisir une date de fin.");}
            return false;
        }

        LocalTime startTime = LocalTime.parse((CharSequence) addAppStartTimePick.getSelectionModel().getSelectedItem());
        LocalTime endTime = LocalTime.parse((CharSequence) addAppEndTimePick.getSelectionModel().getSelectedItem());

        //Checks if the end time is before start time.
        if (endTime.isBefore(startTime)) {
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Incorrect information", "Hey! Check your times are correct!", "Ensure your times are valid.");}
            else {alerts.alert("Informations incorrectes", "Hé! Vous vérifiez que vos temps sont corrects!", "Assurez-vous que vos temps sont valides.");}
            return false;
        };

        //Initiate values for start and ends dates of appointments
        LocalDate startDate = addAppStartDatePick.getValue();
        LocalDate endDate = addAppEndDatePick.getValue();

        if (!startDate.equals(endDate)){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Incorrect information", "Hey! Check your dates are correct!", "Ensure your dates are valid.");}
            else {alerts.alert("Informations incorrectes", "Hé! Vous vérifiez que vos dates sont corrects!", "Assurez-vous que vos dates sont valides.");}
            return false;
        };

        if (addAppEndDatePick.getValue().isBefore(addAppStartDatePick.getValue())){
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Missing Info", "Check your start an end dates.", "Check your start and end dates.");}
            else {alerts.alert("Informations manquantes", "Vérifiez vos dates de début et de fin.", "Vérifiez vos dates de début et de fin.");}
            return false;
        }

        LocalDateTime selectedStart = startDate.atTime(startTime);
        LocalDateTime selectedEnd = endDate.atTime(endTime);
        LocalDateTime possAppointmentStart;
        LocalDateTime possAppointmentEnd;

        try {
            ObservableList<appointment> appointments = appointmentSearch.getAppointmentsByCustomerID(customerIDCombo.getSelectionModel().getSelectedItem());

            for (appointment appointment: appointments) {

                possAppointmentStart = appointment.getStartDate().atTime(appointment.getStartTime().toLocalTime());
                possAppointmentEnd = appointment.getEndDate().atTime(appointment.getEndTime().toLocalTime());
                System.out.println(possAppointmentStart);
                System.out.println(possAppointmentEnd);
                System.out.println(selectedStart);
                System.out.println(selectedEnd);

                if (possAppointmentStart.isAfter(selectedStart) && possAppointmentStart.isBefore(selectedEnd)) {
                    if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Appointments can't overlap with existing appointments.", "Please provide correct dates.");}
                    else{alerts.alertE("Erreur", "Les rendez-vous ne peuvent pas se chevaucher avec des rendez-vous existants.", "Veuillez fournir des dates correctes.");}
                    return false;
                }
                else if (possAppointmentEnd.isAfter(selectedStart) && possAppointmentEnd.isBefore(selectedEnd)) {
                    if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Appointments can't overlap with existing appointments.", "Please provide correct dates.");}
                    else{alerts.alertE("Erreur", "Les rendez-vous ne peuvent pas se chevaucher avec des rendez-vous existants.", "Veuillez fournir des dates correctes.");}
                    return false;
                }
                else if (possAppointmentStart.equals(selectedStart)){
                    if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Appointments can't overlap with existing appointments.", "Please provide correct dates.");}
                    else{alerts.alertE("Erreur", "Les rendez-vous ne peuvent pas se chevaucher avec des rendez-vous existants.", "Veuillez fournir des dates correctes.");}
                    return false;
                }
                else if (possAppointmentEnd.equals(selectedEnd)){
                    if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Appointments can't overlap with existing appointments.", "Please provide correct dates.");}
                    else{alerts.alertE("Erreur", "Les rendez-vous ne peuvent pas se chevaucher avec des rendez-vous existants.", "Veuillez fournir des dates correctes.");}
                    return false;
                }
                else if (possAppointmentStart.isBefore(selectedStart) && possAppointmentEnd.isAfter(selectedEnd)){
                    if(Locale.getDefault().getLanguage()=="en"){alerts.alertE("Error", "Appointments can't overlap with existing appointments.", "Please provide correct dates.");}
                    else{alerts.alertE("Erreur", "Les rendez-vous ne peuvent pas se chevaucher avec des rendez-vous existants.", "Veuillez fournir des dates correctes.");}
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        startDTConversion = convertToEST(LocalDateTime.of(addAppStartDatePick.getValue(), LocalTime.parse((CharSequence) addAppStartTimePick.getSelectionModel().getSelectedItem())));
        endDTConversion = convertToEST(LocalDateTime.of(addAppEndDatePick.getValue(), LocalTime.parse((CharSequence) addAppEndTimePick.getSelectionModel().getSelectedItem())));

        if (startDTConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Error.", "Appointments must be in business hours!", "Please choose a start time.");}
            else {alerts.alert("Erreur", "Les rendez-vous doivent être en heures ouvrables!", "Veuillez choisir une heure de début.");}
            return false;
        }

        if (endDTConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Error.", "Appointments must be in business hours!", "Please choose an end time.");}
            else {alerts.alert("Erreur", "Les rendez-vous doivent être en heures ouvrables!", "Veuillez choisir une heure de fin.");}
            return false;
        }

        if (startDTConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Error.", "Appointments must be in business hours!", "Please choose a start time.");}
            else {alerts.alert("Erreur", "Les rendez-vous doivent être en heures ouvrables!", "Veuillez choisir une heure de début.");}
            return false;
        }

        if (endDTConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            if(Locale.getDefault().getLanguage() == "en"){alerts.alert("Error.", "Appointments must be in business hours!", "Please choose an end time.");}
            else {alerts.alert("Erreur", "Les rendez-vous doivent être en heures ouvrables!", "Veuillez choisir une heure de fin.");}
            return false;
        }
        return true;
    }


    /***
     * This method moves the user back to the appointments stage without making any changes at all.
     * @param actionEvent
     * @throws IOException
     */
    public void addAppCancelClick(ActionEvent actionEvent) throws IOException {
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
     * Method to populate the type combo box with options.
     */
    private void typeComboBox() {
        ObservableList<String> types = FXCollections.observableArrayList();
        if(Locale.getDefault().getLanguage() == "en"){types.addAll("Planning Session", "De-Briefing", "Other");}
        else{types.addAll("Séance de Planification", "Compte Rendu", "Autre");}
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


    /***
     * Method to populate the customer ID combo box.
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
        customerIDCombo.setItems(customerIDs);
    }


    /***
     * Method to populate the contacts combo box.
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
        addAppContactPick.setItems(contacts);
    }


    /***
     * Method that populates the time combo box
     */
    private void timeComboBoxes() {
        ObservableList<String> time = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(21, 0);
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
        addAppSave.setText(rb.getString("addAppSave"));
        addAppCancel.setText(rb.getString("addAppCancel"));
        addAnAppointment.setText(rb.getString("addAnAppointment"));
        addAppTitle.setText(rb.getString("title"));
        addAppDescription.setText(rb.getString("description"));
        addLocation.setText(rb.getString("location"));
        addAppType.setText(rb.getString("type"));
        addApptStartDate.setText(rb.getString("startdate"));
        addAppStartTime.setText(rb.getString("starttime"));
        addAppEndDate.setText(rb.getString("enddate"));
        addAppEndTime.setText(rb.getString("endtime"));
        addAppContact.setText(rb.getString("contact"));
        addAppCustomer.setText(rb.getString("customerID"));
        addAppUser.setText(rb.getString("userID"));

    }
}
