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
import javafx.stage.Stage;
import scheduler.model.alerts;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class updateCustomerController implements Initializable {

    @FXML Button save;
    @FXML Button cancel;
    @FXML Label updateCustomer;

    ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());

    public void saveClick(ActionEvent actionEvent) throws IOException {
        if (Locale.getDefault().getLanguage() == "en"){
            if (alerts.alert("Update Customer?", "Are you sure you'd like to save?", "Your changes will be saved.")){
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/customers.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Customers");
                stage.show();
            }
        }
        else {
            if (alerts.alert("Mettre un client?", "Voulez-vous vraiment enregistrer?", "Vos modifications seront enregistrées.")){
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/customers.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Clients");
                stage.show();
            }
        }
    }

    public void cancelClick(ActionEvent actionEvent) throws IOException {
        if (Locale.getDefault().getLanguage() == "en") {
            if (alerts.alert("Cancel?", "Are you sure you'd like to cancel?", "Your changes will be lost.")) {
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/customers.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Customers");
                stage.show();
            }
        }
        else {
            if (alerts.alert("Annuler?", "Voulez-vous vraiment annuler?", "Vos modifications seront perdues.")){
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/customers.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Clients");
                stage.show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCustomer.setText(rb.getString("updateCustomer"));
        save.setText(rb.getString("addAppSave"));
        cancel.setText(rb.getString("addAppCancel"));
    }

}
