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
import scheduler.utilities.customerSearch;
import scheduler.model.alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class customersController implements Initializable {

    @FXML Button back;
    @FXML Button deleteCustomer;
    @FXML Button updateCustomer;
    @FXML Button addCustomer;
    @FXML Label customerRecords;

    static ObservableList<customer> customers;
    @FXML private TableView<customer> customerTableView;
    @FXML TableColumn<?,?> customerIdCol;
    @FXML TableColumn<?,?> customerNameCol;
    @FXML TableColumn<?,?> customerPostalCodeCol;
    @FXML TableColumn<?,?> customerPhoneNumberCol;
    @FXML TableColumn<?,?> customerCountryCol;
    @FXML TableColumn<?,?> customerAddressCol;
    @FXML TableColumn<?,?> customerDivisionIDCol;


    public void addCustomerClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/addCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Customer");
        stage.show();
    }

    public void updateCustomerClick(ActionEvent actionEvent) throws IOException {
        updateCustomerController.getSelected(customerTableView.getSelectionModel().getSelectedItem());
        if (customerTableView.getSelectionModel().getSelectedItem() == null){
            if (Locale.getDefault().getLanguage() == "en"){alerts.alert("Make a Selection", "You did not choose a customer to update.", "Please choose a customer.");}
            else {alerts.alert("Choisissez", "Vous n'avez pas choisi de client à mettre à jour.", "Veuillez choisir un client.");}
        }
        else {
            if (Locale.getDefault().getLanguage() == "en"){
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/updateCustomer.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Update Customer");
                stage.show();
            }
            else {
                Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/updateCustomer.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Mettre à Jour le Client");
                stage.show();
            }
        }
    }


    /***
     * This is a method that is used to deleted a customer from the customer table.  It will check to see if the customer has any upcoming appointments first.
     * @param actionEvent
     */
    public void deleteCustomerClick(ActionEvent actionEvent) {
        customer selected = customerTableView.getSelectionModel().getSelectedItem();
        //boolean condition = checkForAppointments(selected);
        if (selected == null){
            if (Locale.getDefault().getLanguage() == "en") {
                alerts.alert("No selection", "A customer was not selected!", "Please make a selection for deletion.");
            }
            else {
                alerts.alert("Pas de choix.", "Un client n'a pas été sélectionné!", "Veuillez faire une sélection à supprimer.");
            }
        }
        else {
            if (Locale.getDefault().getLanguage() == "en") {
                alerts.alert("Are you sure?", "You are about to delete a customer.", "All customer information will be lost.");
                if (checkForAppointments(selected) == true){
                    try {
                        customerSearch.deleteCustomer(customerTableView.getSelectionModel().getSelectedItem().getCustomerID());
                        customers = customerSearch.getAllCustomers();
                        customerTableView.setItems(customers);
                        customerTableView.refresh();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                alerts.alert("es-tu sûr?", "Vous êtes sur le point de supprimer un client.", "Toutes les informations client seront perdues.");
                checkForAppointments(selected);
                if (checkForAppointments(selected) == true){
                    try {
                        customerSearch.deleteCustomer(customerTableView.getSelectionModel().getSelectedItem().getCustomerID());
                        customers = customerSearch.getAllCustomers();
                        customerTableView.setItems(customers);
                        customerTableView.refresh();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    /***
     * This method is used to check to see if a specific customer has any upcoming appointments prior to their deletion.
     * @param selectedCustomer
     * @return
     */
    private boolean checkForAppointments(customer selectedCustomer) {
        return true;
        /*
        try {
            ObservableList appointments = AppointmentsQuery.getAppointmentsByCustomerID(selectedCustomer.getCustomerId());
            if (appointments != null && appointments.size() < 1) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        */
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
        stage.setTitle("Main Menu");
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
