package scheduler.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduler.model.customer;
import scheduler.model.division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customerSearch {

    /***
     * This method combines the customers and first_level_division tables and searches through them to find the information to populate the customer tableview.
     * @return returns an ObservableList that holds the customer information for the tableView.
     * @throws SQLException
     */
    public static ObservableList<customer> getAllCustomers() throws SQLException {
        ObservableList<customer> customers = FXCollections.observableArrayList();
        String statement = "SELECT * FROM customers AS c INNER JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID INNER JOIN countries AS co ON co.Country_ID=d.COUNTRY_ID;";
        DBQuery.setPreparedStatement(JDBC.getConnection(), statement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
        try {
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                customer newCustomer = new customer(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("Postal_Code"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Address"),
                        resultSet.getString("Country"),
                        resultSet.getInt("Division_ID")
                );
                customers.add(newCustomer);
            }
            return customers;
        }
        catch (Exception e){
            System.out.println("Yo! There was an error!  It was " + e.getMessage());
            return null;
        }
    }



    public static boolean addCustomer(String name, String address, String postalCode, String phone, String division) throws SQLException{
        division newDivision = divisionSearch.getDivisionID(division);
        String statement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        DBQuery.setPreparedStatement(JDBC.getConnection(), statement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, postalCode);
        preparedStatement.setString(4, phone);
        preparedStatement.setInt(5, newDivision.getDivisionId());
        try{
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
