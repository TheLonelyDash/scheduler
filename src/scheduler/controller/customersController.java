package scheduler.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import scheduler.model.customer;
import scheduler.utilities.customerSearch;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
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
    @FXML TableColumn<?,?> customerDivisionID;


    public void addCustomerClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/addCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Customer");
        stage.show();
    }

    public void updateCustomerClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/updateCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Update Customer");
        stage.show();
    }

    public void deleteCustomerClick(ActionEvent actionEvent) {
    }

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
            customerDivisionID.setCellValueFactory(new PropertyValueFactory<>("customerDivisionID"));
        }
        catch(SQLException e){
            System.out.println("Oh no! Not another error!");
            e.printStackTrace();
        }
    }
}
