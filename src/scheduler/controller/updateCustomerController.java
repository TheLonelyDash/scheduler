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
import scheduler.model.customer;
import scheduler.model.division;
import scheduler.utilities.countrySearch;
import scheduler.utilities.customerSearch;
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
    @FXML TextField idText;
    @FXML TextField nameText;
    @FXML TextField phoneText;
    @FXML TextField postalCodeText;
    @FXML TextField addressText;

    private static customer selected;

    /***
     * This method retrieves the selected customer from the customersController.
     * @param customer
     */
    public static void getSelected(customer customer){
        selected = customer;
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

    /***
     * Updates the division dropdown box with divisions based on the country selected.
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





    public void saveClick(ActionEvent actionEvent) throws IOException {
        boolean condition = evaluateEmptyTextField(nameText.getText(), addressText.getText(), postalCodeText.getText(), phoneText.getText());
        if (condition == true){
            try{
                if (Locale.getDefault().getLanguage() == "en") {
                    if (alerts.alert("Update Customer?", "Are you sure you want to update?", "It's okay, you can update if you want to!")) {
                        customerSearch.updateCustomer(
                                Integer.parseInt(idText.getText()),
                                nameText.getText(),
                                addressText.getText(),
                                postalCodeText.getText(),
                                phoneText.getText(),
                                divisionIdComboBox.getValue()
                        );
                        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/customers.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle("Customers");
                        stage.show();
                    }
                }
                else {
                    if (alerts.alert("Mettre à jour le client?", "Êtes-vous sûr de vouloir mettre à jour?", "C'est bon, tu peux mettre à jour si tu veux !")) {
                        customerSearch.updateCustomer(
                                Integer.parseInt(idText.getText()),
                                nameText.getText(),
                                addressText.getText(),
                                postalCodeText.getText(),
                                phoneText.getText(),
                                divisionIdComboBox.getValue()
                        );
                        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/customers.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle("Customers");
                        stage.show();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    /***
     * this method takes the input from the textFields and ensures that they are filled out.
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @return
     */
    private boolean evaluateEmptyTextField(String name, String address, String postalCode, String phone){
        if (name.isEmpty()){
            if (Locale.getDefault().getLanguage() == "en"){
                alerts.alert("No Name", "The Name TextField is Empty!", "Please provide a Name.");
            }
            else{
                alerts.alert("Sans nom", "Le champ de texte du nom est vide", "Veuillez fournir un nom.");
            }
            return false;
        }
        if (address.isEmpty()){
            if (Locale.getDefault().getLanguage() == "en"){
                alerts.alert("No Address", "The Address TextField is Empty!", "Please provide an Address.");
            }
            else{
                alerts.alert("pas d'adresse", "le champ de texte de l'adresse est vide.", "Merci de fournir une adresse.");
            }
            return false;
        }
        if (phone.isEmpty()){
            if (Locale.getDefault().getLanguage() == "en"){
                alerts.alert("No Phone Number", "The Phone Number TextField is Empty!", "Please provide a Phone Number.");
            }
            else{
                alerts.alert("Pas de numéro de téléphone", "Le champ de texte du numéro de téléphone est vide !", "Veuillez fournir un numéro de téléphone.");
            }
            return false;
        }
        if (postalCode.isEmpty()){
            if (Locale.getDefault().getLanguage() == "en"){
                alerts.alert("No Postal Code", "The Postal Code TextField is Empty!", "Please provide a postal code.");
            }
            else{
                alerts.alert("Aucun code postal", "Le champ de texte du code postal est vide !", "Veuillez fournir un code postal.");
            }
            return false;
        }
        return true;
    }




    /***
     * This method makes no changes to any of the update fields but redirects the user back to the customers stage gui.
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




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setCountryComboBox();
        setDivisionIdComboBox();
        try {
            ObservableList<division> allDivs = divisionSearch.getAllDivisions();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        updateCustomer.setText(rb.getString("updateCustomer"));
        save.setText(rb.getString("addAppSave"));
        cancel.setText(rb.getString("addAppCancel"));

        idText.setText(Integer.toString(selected.getCustomerID()));
        nameText.setText(selected.getCustomerName());
        phoneText.setText(selected.getCustomerPhoneNumber());
        addressText.setText(selected.getCustomerAddress());
        postalCodeText.setText(selected.getCustomerPostalCode());
        countryComboBox.getSelectionModel().select(selected.getCustomerCountry());
        try {
            divisionSearch.getAllDivisions().forEach(d-> {
                if (d.getDivisionId() == selected.getCustomerDivisionID()){
                    divisionIdComboBox.getSelectionModel().select(d.getDivision());
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //divisionIdComboBox.getSelectionModel().select(selected.getCustomerDivisionID());

    }

}
