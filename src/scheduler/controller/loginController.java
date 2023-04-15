package scheduler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import scheduler.model.alerts;
import scheduler.model.appointment;
import scheduler.utilities.appointmentSearch;
import scheduler.utilities.userSearch;
import java.time.ZoneId;

import java.lang.System;
import java.util.*;
import java.io.IOException;
import java.sql.SQLException;

interface LogActivity {
    public String getFileName();
}


public class loginController implements Initializable {

    @FXML Label loginLabel;
    @FXML Label userNameLabel;
    @FXML Label passwordLabel;
    @FXML Label locationLabel;
    @FXML Button loginButton;
    @FXML Button exitButton;
    @FXML TextField usernameTextField;
    @FXML TextField passwordTextField;
    @FXML TextField locationTextField;
    @FXML TextField zoneIDTextField;
    @FXML LocalDateTime start;
    @FXML LocalDateTime appTime;
    @FXML LocalDateTime currTime;
    Integer appID;
    boolean condition;

    @FXML
    public void loginButtonClick(ActionEvent actionEvent) throws IOException, SQLException {

        ObservableList<appointment> getAllAppointments = appointmentSearch.getAllAppointments();
        LocalDateTime currentTimePlus15 = LocalDateTime.now().plusMinutes(15);

        for (appointment appointment: getAllAppointments){
            start = appointment.getStartTime();
            if (start.isBefore(currentTimePlus15) && start.isAfter(LocalDateTime.now())){
                appID = appointment.getAppointment_ID();
                appTime = appointment.getStartTime();
                currTime = LocalDateTime.now();
                condition = true;
            }
        }

        createFile();

        if (usernameTextField.getText().isEmpty()) {
            if (Locale.getDefault().getLanguage() == "en") {alerts.alert("Username", "No username was provided.", "Please enter a valid username!");}
            else {alerts.alert("Nom d'Utilisateur", "Aucun nom d'utilisateur fourni.", "Veuillez saisir un nom d'utilisateur valide!");}
        }
        else if (passwordTextField.getText().isEmpty()) {
            if (Locale.getDefault().getLanguage() == "en") {alerts.alert("Password", "No password was provided.", "Please enter a valid password!");}
            else {alerts.alert("Mot de passe", "Aucun mot de passe n'a été fourni.", "Veuillez saisir un mot de passe valide!");}
        }
        else {
            boolean valid = userSearch.checkNameAndPassword(usernameTextField.getText(), passwordTextField.getText());
            if (valid == true) {
                successfulLogin();
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/mainMenu.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                if (Locale.getDefault().getLanguage() == "en") {stage.setTitle("Main Menu");}
                else {stage.setTitle("Menu Principal");}
                stage.show();
                if (condition == true){
                    if(Locale.getDefault().getLanguage()=="en"){alerts.alert("Upcoming Appointment!", "Upcoming Appointment!\n\nAppointmentID: " + appID + "\nCurrent Time: " + currTime + "\nAppointment Time: " + appTime, "Check it out!");}
                    else{alerts.alert("Rendez-vous à venir!", "Rendez-vous à venir !\n\nAppointmentID: " + appID + "\nCurrent Time: " + currTime + "\nAppointment Time: " + appTime, "Vérifiez-le!");}
                }
                else {
                    if(Locale.getDefault().getLanguage()=="en"){alerts.alert("No Upcoming Appointments", "There are no appointments within the next 15 minutes", "Check it out!");}
                    else{alerts.alert("Aucun rendez-vous à venir", "Il n'y a pas de rendez-vous dans les 15 prochaines minutes", "Vérifiez-le!");}
                }
            }
            else {
                unsuccessfulLogin();
                if (Locale.getDefault().getLanguage() == "en"){alerts.alert("Login Credentials", "Incorrect Username or Password.", "Please enter a valid username and password.");}
                else {alerts.alert("Identifiants de Connexion", "Identifiant ou mot de passe incorrect.", "Veuillez saisir un nom d'utilisateur et un mot de passe valides.");}
            }
        }
    }

    /***
     * This method closes the program.  It first throws an alert asking if the user is sure they would like to close the program.
     * @param actionEvent
     */
    @FXML
    public void exitButtonClick(ActionEvent actionEvent) {
        if (Locale.getDefault().getLanguage() == "en"){
                alerts.alert("Exit", "Would you like to close this application?", "You have not yet logged in.");
                System.exit(0);
            }
        else {
            alerts.alert("Sortie", "Souhaitez-vous fermer cette application?", "Vous n'êtes pas encore connecté.");
            System.exit(0);
        }
    }

    //LAMBDA EXPRESSION: Reduces code and increases readability for the creation of the login_activity text file!
    LogActivity logActivity = () -> {
        return "login_activity.txt";
    };

    /***
     * This method creates a new file to store the login and logout activity in the program. If the file has already
     * been created, it will print out the files location.
     */
    private void createFile(){
        try {
            File newFile = new File(logActivity.getFileName());
            if (newFile.createNewFile()) {System.out.println("You created a new file at " + newFile.getName());}
            else {System.out.println("Yo! You already created this file! It's location is "+ newFile.getPath());}
        }
        catch (IOException e){e.printStackTrace();}
    }

    /***
     * This method writes the login information for the successful logins.
     */
    private void successfulLogin(){
        try {
            FileWriter fileWriter = new FileWriter(logActivity.getFileName(), true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("The Login was SUCCESSFUL! \nUsername: " + usernameTextField.getText() + "\nPassword: " + passwordTextField.getText() + "\nTimestamp: " + simpleDateFormat.format(date) + "\n\n");
            fileWriter.close();
            System.out.println("Successful Login");
        }
        catch (IOException e) {e.printStackTrace();}
    }

    /***
     * This method writes the login information for the UNsuccessful logins.
     */
    private void unsuccessfulLogin() {
        try {
            FileWriter fileWriter = new FileWriter(logActivity.getFileName(), true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("The Login was UNSUCCESSFUL! \nUsername: " + usernameTextField.getText() + "\nPassword: " + passwordTextField.getText() + "\nTimestamp: " + simpleDateFormat.format(date) + "\n\n");
            fileWriter.close();
            System.out.println("Unuccessful Login");
        }
        catch (IOException e) {e.printStackTrace();}
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        loginLabel.setText(rb.getString("login"));
        userNameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        locationLabel.setText(rb.getString("language"));
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
        locationTextField.setText(rb.getString("country"));
        zoneIDTextField.setText(String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
    }
}

