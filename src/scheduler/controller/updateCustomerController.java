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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import scheduler.model.alerts;
import scheduler.model.country;
import scheduler.model.division;
import scheduler.utilities.countrySearch;
import scheduler.utilities.divisionSearch;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class updateCustomerController implements Initializable {

    @FXML Button save;
    @FXML Button cancel;
    @FXML Label updateCustomer;
    @FXML ComboBox<String> countryComboBox;
    @FXML ComboBox<String> divisionIdComboBox;


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

    /***
     * Method that populates the combo box on the add and update customer stage with the three available countries for the company.
     */
    private void setCountryComboBox(){
        ObservableList<String> ListOfCountries = FXCollections.observableArrayList();
        try {
            ObservableList<country> countries = countrySearch.getAllCountries();
            if (countries != null) {
                for (country country: countries){
                    ListOfCountries.add(country.getCountry());
                }
            }
        } catch (SQLException e) {
            System.out.println("Yikes! Another error...");
            e.printStackTrace();
        }
        countryComboBox.setItems(ListOfCountries);
    }

    /***
     * Method that populates the combo box on the add and update customer stage with the available divisions for the company.
     */
    private void setDivisionIdComboBox(){
        ObservableList<String> ListOfDivisionIds = FXCollections.observableArrayList();
        try{
            ObservableList<division> divisions = divisionSearch.getAllDivisions();
            if (divisions != null){
                for (division division: divisions){
                    ListOfDivisionIds.add(division.getDivision());
                }
            }
        } catch (SQLException e) {
            System.out.println("Well, that didn't work...");
            e.printStackTrace();
        }
        divisionIdComboBox.setItems(ListOfDivisionIds);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        updateCustomer.setText(rb.getString("updateCustomer"));
        save.setText(rb.getString("addAppSave"));
        cancel.setText(rb.getString("addAppCancel"));

        setCountryComboBox();
        setDivisionIdComboBox();
    }

}
