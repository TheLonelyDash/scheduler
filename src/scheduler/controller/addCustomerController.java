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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import scheduler.model.alerts;
import scheduler.model.country;
import scheduler.model.division;
import scheduler.utilities.countrySearch;
import scheduler.utilities.divisionSearch;
import scheduler.utilities.customerSearch;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {

    @FXML Button save;
    @FXML Button cancel;
    @FXML Label addCustomer;
    @FXML ComboBox<String> countryComboBox;
    @FXML ComboBox<String> divisionIdComboBox;

    @FXML TextField nameText;
    @FXML TextField phoneText;
    @FXML TextField postalCodeText;
    @FXML TextField addressText;

    public void saveClick(ActionEvent actionEvent) throws IOException {
        boolean condition = evaluateEmptyTextField(nameText.getText(), addressText.getText(), postalCodeText.getText(), phoneText.getText());
        if (condition == true){
            try {
                if (Locale.getDefault().getLanguage() == "en"){
                    if (alerts.alert("Add Customer?", "Are you sure you'd like to save?", "Your changes will be saved.")){
                        customerSearch.addCustomer(nameText.getText(), addressText.getText(), postalCodeText.getText(), phoneText.getText(), divisionIdComboBox.getValue());
                        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/customers.fxml"));
                        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle("Customers");
                        stage.show();
                    }
                }
                else {
                    if (alerts.alert("Ajouter un client?", "Voulez-vous vraiment enregistrer?", "Vos modifications seront enregistrées.")){
                        customerSearch.addCustomer(nameText.getText(), addressText.getText(), postalCodeText.getText(), phoneText.getText(), divisionIdComboBox.getValue());
                        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/customers.fxml"));
                        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle("Clients");
                        stage.show();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * This method asks the user if they are sure they would like to cancel their add customer action.  If so, it redirects them to the
     * customers screen. Otherwise, it allows tehm to continue to add a customer.
     * @param actionEvent
     * @throws IOException
     */
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
     * Method that populates the combo box on the add customer stage with the three available countries for the company.
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
     * Method that populates the combo box on the add and update customer stage with the three available countries for the company.
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

    /***
     * This method takes in the text parameters of the customer name, address, postal code, and phone number and if one of the textfields are empty upon saving, it will provide an alert.
     * @param name the customer name
     * @param address the customer address
     * @param postalCode the customer postal code
     * @param phone the customer phone number
     * @return boolean true/false depending on if there are any empty textfields
     */
    private boolean evaluateEmptyTextField(String name, String address, String postalCode, String phone){
        if (name.isEmpty()){
            if (Locale.getDefault().getLanguage() == "en"){alerts.alert("No Name", "The Name TextField is Empty!", "Please provide a Name.");}
            else{alerts.alert("Sans nom", "Le champ de texte du nom est vide", "Veuillez fournir un nom.");}
            return false;
        }
        if (phone.isEmpty()){
            if (Locale.getDefault().getLanguage() == "en"){alerts.alert("No Phone Number", "The Phone Number TextField is Empty!", "Please provide a Phone Number.");}
            else{alerts.alert("Pas de numéro de téléphone", "Le champ de texte du numéro de téléphone est vide!", "Veuillez fournir un numéro de téléphone.");}
            return false;
        }
        if (address.isEmpty()){
            if (Locale.getDefault().getLanguage() == "en"){alerts.alert("No Address", "The Address TextField is Empty!", "Please provide an Address.");}
            else{alerts.alert("pas d'adresse", "le champ de texte de l'adresse est vide.", "Merci de fournir une adresse.");}
            return false;
        }
        if (postalCode.isEmpty()){
            if (Locale.getDefault().getLanguage() == "en"){alerts.alert("No Postal Code", "The Postal Code TextField is Empty!", "Please provide a postal code.");}
            else{alerts.alert("Aucun code postal", "Le champ de texte du code postal est vide!", "Veuillez fournir un code postal.");}
            return false;
        }
        if (countryComboBox.getSelectionModel().isEmpty()){
            if (Locale.getDefault().getLanguage() == "en"){alerts.alert("No country", "You didn't choose a country", "Please provide a country.");}
            else{alerts.alert("Aucun pays", "Vous n'avez pas choisi de pays!", "Veuillez fournir un pays.");}
            return false;
        }
        if (divisionIdComboBox.getSelectionModel().isEmpty()){
            if (Locale.getDefault().getLanguage() == "en"){alerts.alert("No division", "You didn't choose a division", "Please provide a division.");}
            else{alerts.alert("Aucun division", "Vous n'avez pas choisi de division!", "Veuillez fournir un division.");}
            return false;
        }
        return true;
    }

    /***
     * This method populates the division combobox so the user may choose what is available in their country.
     * @param event
     */
    public void countryComboClick(ActionEvent event){
        ObservableList<String> listOfDivisions = FXCollections.observableArrayList();
        try {
            ObservableList<division> divisions = divisionSearch.getDivisionsByCountry(countryComboBox.getSelectionModel().getSelectedItem());
            if (divisions != null) {
                for (division division: divisions) {
                    listOfDivisions.add(division.getDivision());
                }
            }
            divisionIdComboBox.setItems(listOfDivisions);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setDivisionIdComboBox();
        setCountryComboBox();

        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        addCustomer.setText(rb.getString("addCustomer"));
        save.setText(rb.getString("addAppSave"));
        cancel.setText(rb.getString("addAppCancel"));



    }
}
