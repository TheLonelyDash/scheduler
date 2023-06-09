package scheduler.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import scheduler.model.customer;
import scheduler.utilities.appointmentSearch;
import scheduler.utilities.customerSearch;
import scheduler.model.alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class customersController implements Initializable {

    //Buttons
    @FXML Button back;
    @FXML Button deleteCustomer;
    @FXML Button updateCustomer;
    @FXML Button addCustomer;

    //Labels
    @FXML Label customerRecords;

    //Lists
    static ObservableList<customer> customers;

    //Table Views
    @FXML private TableView<customer> customerTableView;

    //Table Columns
    @FXML TableColumn<?,?> customerIdCol;
    @FXML TableColumn<?,?> customerNameCol;
    @FXML TableColumn<?,?> customerPostalCodeCol;
    @FXML TableColumn<?,?> customerPhoneNumberCol;
    @FXML TableColumn<?,?> customerCountryCol;
    @FXML TableColumn<?,?> customerAddressCol;
    @FXML TableColumn<?,?> customerDivisionIDCol;


    /***
     * A method that directs the user to the add customer gui.
     * @param actionEvent
     * @throws IOException
     */
    public void addCustomerClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/addCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        if(Locale.getDefault().getLanguage()=="en"){stage.setTitle("Add Customer");}
        else{stage.setTitle("Ajouter un Client");}
        stage.show();
    }


    /***
     * A method that directs the user to the update customer gui.  It first checks to see if the user has selected a customer
     * to update.  If not, it throws an alert telling them to do so.
     * @param actionEvent
     * @throws IOException
     */
    public void updateCustomerClick(ActionEvent actionEvent) throws IOException {
        updateCustomerController.getSelected(customerTableView.getSelectionModel().getSelectedItem());
        if (customerTableView.getSelectionModel().getSelectedItem() == null){
            if (Locale.getDefault().getLanguage() == "en"){alerts.alert("Make a Selection", "You did not choose a customer to update.", "Please choose a customer.");}
            else {alerts.alert("Choisissez", "Vous n'avez pas choisi de client à mettre à jour.", "Veuillez choisir un client.");}
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/updateCustomer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            if (Locale.getDefault().getLanguage() == "en"){stage.setTitle("Update Customer");}
            else{stage.setTitle("Mettre à jour le client");}
            stage.show();
        }
    }


    /***
     * This is a method that is used to deleted a customer from the customer table.  It will check to see if the customer has any upcoming appointments first.
     * @param actionEvent
     */
    public void deleteCustomerClick(ActionEvent actionEvent) {
        //Get the selected customer
        customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        //Check that the user has made a selection
        if (selectedCustomer == null) {
            if (Locale.getDefault().getLanguage() == "en") {
                alerts.alert("No selection", "A customer was not selected!", "Please make a selection for deletion.");
            } else {
                alerts.alert("Pas de choix.", "Un client n'a pas été sélectionné!", "Veuillez faire une sélection à supprimer.");
            }
        }
        //Check if there are any appointments
        boolean condition = checkForAppointments(selectedCustomer);
        Optional<ButtonType> result;
        if(Locale.getDefault().getLanguage()=="en"){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you'd like to delete? All appointments will also be deleted.");
            result = alert.showAndWait();}
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer ? Tous les rendez-vous seront également supprimés.");
            result = alert.showAndWait();}
        //If there are outstanding appointments, delete them
        if (condition != true && result.isPresent() && result.get() == ButtonType.OK) {
            try {
                appointmentSearch.deleteAppointmentByCustomerID(selectedCustomer.getCustomerID());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //Delete the customer
        try {
            customerSearch.deleteCustomer(customerTableView.getSelectionModel().getSelectedItem().getCustomerID());
            customers = customerSearch.getAllCustomers();
            customerTableView.setItems(customers);
            customerTableView.refresh();
            if(Locale.getDefault().getLanguage()=="en"){alerts.alertI("Delete Complete!", selectedCustomer.getCustomerName() + " has been deleted.", "It's done.");}
            else{alerts.alertI("Supprimer terminé!", selectedCustomer.getCustomerName() + " a été supprimé.", "C'est fait.");}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /***
     * This method checks if there are current appointments for a customer.
     * @param selected
     * @return
     */
    private boolean checkForAppointments(customer selected) {
        try {
            ObservableList appointments = appointmentSearch.getAppointmentsByCustomerID(selected.getCustomerID());
            if (appointments != null && appointments.size() < 1) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    /***
     * This method closes the customer stage and returns the user to the main menu.
     * @param actionEvent
     * @throws IOException
     */
    public void backClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/mainMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        if(Locale.getDefault().getLanguage()=="en"){stage.setTitle("Main Menu");}
        else{stage.setTitle("Menu principal");}
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        customerRecords.setText(rb.getString("customerRecords"));
        back.setText(rb.getString("back"));
        deleteCustomer.setText(rb.getString("deleteCustomer"));
        updateCustomer.setText(rb.getString("updateCustomer"));
        addCustomer.setText(rb.getString("addCustomer"));
        customerIdCol.setText(rb.getString("customerID"));
        customerNameCol.setText(rb.getString("name"));
        customerPostalCodeCol.setText(rb.getString("postalcode"));
        customerPhoneNumberCol.setText(rb.getString("phonenumber"));
        customerAddressCol.setText(rb.getString("address"));
        customerCountryCol.setText(rb.getString("countrycol"));
        customerDivisionIDCol.setText(rb.getString("divisionID"));
        customerRecords.setText(rb.getString("customerRecords"));

        try{
            customers = customerSearch.getAllCustomers();
            customerTableView.setItems(customers);
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            customerPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
            customerDivisionIDCol.setCellValueFactory(new PropertyValueFactory<>("customerDivisionID"));
        }
        catch(SQLException e){
            System.out.println("Oh no! Not another error!");
            e.printStackTrace();
        }
    }
}
