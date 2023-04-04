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

    Locale currentLocale = Locale.getDefault();
    ResourceBundle rb = ResourceBundle.getBundle("language/language", currentLocale);


    @FXML
    public void loginButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/mainMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }

    @FXML
    public void exitButtonClick(ActionEvent actionEvent) {
        if (alerts.alert("Exit", "Would you like to close this application?", "You have not yet logged in.")){
            System.exit(0);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginLabel.setText(rb.getString("login"));
        userNameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        locationLabel.setText(rb.getString("location"));
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
        System.out.println("I got initialized!");
    }

}
