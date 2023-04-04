package scheduler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

//import java.awt.*;
import java.util.Locale;
import java.io.IOException;
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
import java.lang.System;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;


public class loginController implements Initializable {

    @FXML Label loginLabel;
    @FXML Label userNameLabel;
    @FXML Label passwordLabel;
    @FXML Label locationLabel;
    @FXML Button loginButton;
    @FXML Button exitButton;
    @FXML TextField usernameTextField;
    @FXML TextField passwordTextField;




    @FXML
    public void loginButtonClick(ActionEvent actionEvent) throws IOException {
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        loginLabel.setText(rb.getString("login"));
        userNameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        locationLabel.setText(rb.getString("location"));
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
    }

}
