package scheduler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import scheduler.model.alerts;
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


    @FXML
    public void loginButtonClick(ActionEvent actionEvent) throws IOException, SQLException {
        createFile();
        if (usernameTextField.getText().isEmpty()) {
            if (Locale.getDefault().getLanguage() == "en") {
                alerts.alert("Username", "No username was provided.", "Please enter a valid username!");
            } else {
                alerts.alert("Nom d'Utilisateur", "Aucun nom d'utilisateur fourni.", "Veuillez saisir un nom d'utilisateur valide!");
            }
        }
        else if (passwordTextField.getText().isEmpty()) {
            if (Locale.getDefault().getLanguage() == "en") {
                alerts.alert("Password", "No password was provided.", "Please enter a valid password!");
            } else {
                alerts.alert("Mot de passe", "Aucun mot de passe n'a été fourni.", "Veuillez saisir un mot de passe valide!");
            }
        }
        else {
            boolean condition = userSearch.checkNameAndPassword(usernameTextField.getText(), passwordTextField.getText());

            if (condition == true) {
                successfulLogin();
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/mainMenu.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                if (Locale.getDefault().getLanguage() == "en") {
                    stage.setTitle("Main Menu");
                } else {
                    stage.setTitle("Menu Principal");
                }
                stage.show();
            }
            else {
                unsuccessfulLogin();
                if (Locale.getDefault().getLanguage() == "en"){
                    alerts.alert("Login Credentials", "Incorrect Username or Password.", "Please enter a valid username and password.");
                }
                else {
                    alerts.alert("Identifiants de Connexion", "Identifiant ou mot de passe incorrect.", "Veuillez saisir un nom d'utilisateur et un mot de passe valides.");
                }
            }
        }
    }


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


    LogActivity logActivity = () -> {
        return "login_activity.txt";
    };

    private void createFile(){
        try {
            File newFile = new File(logActivity.getFileName());
            if (newFile.createNewFile()) {
                System.out.println("You created a new file at " + newFile.getName());
            }
            else {
                System.out.println("Yo! You already created this file! It's location is "+ newFile.getPath());
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void successfulLogin(){
        try {
            FileWriter fileWriter = new FileWriter(logActivity.getFileName(), true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("The Login was SUCCESSFUL! \nUsername: " + usernameTextField.getText() + "\nPassword: " + passwordTextField.getText() + "\nTimestamp: " + simpleDateFormat.format(date) + "\n\n");
            fileWriter.close();
            System.out.println("Successful Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void unsuccessfulLogin() {
        try {
            FileWriter fileWriter = new FileWriter(logActivity.getFileName(), true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("The Login was UNSUCCESSFUL! \nUsername: " + usernameTextField.getText() + "\nPassword: " + passwordTextField.getText() + "\nTimestamp: " + simpleDateFormat.format(date) + "\n\n");
            fileWriter.close();
            System.out.println("Unuccessful Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

