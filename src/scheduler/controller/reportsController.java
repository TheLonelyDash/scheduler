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
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class reportsController implements Initializable {

    @FXML Button reportBackButton;
    @FXML Label reportScheduler;
    @FXML Tab scheduleTab;
    @FXML Tab totalAppTab;
    @FXML Tab totalCountryTab;

    ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());

    public void reportBackButtonClick(ActionEvent actionEvent) throws IOException {
        if (Locale.getDefault().getLanguage() == "en") {
            Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/mainMenu.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Main Menu");
            stage.show();
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/mainMenu.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Menu Principal");
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportBackButton.setText(rb.getString("back"));
        reportScheduler.setText(rb.getString("reportScheduler"));
        scheduleTab.setText(rb.getString("schedule"));
        totalAppTab.setText(rb.getString("totalAppTab"));
        totalCountryTab.setText(rb.getString("totalCountryTab"));
    }
}
