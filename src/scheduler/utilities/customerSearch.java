package scheduler.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduler.model.customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customerSearch {

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
}
