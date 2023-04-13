package scheduler.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduler.model.contactInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class contactSearch {
    public static ObservableList<contactInfo> getAllContacts() throws SQLException{
        ObservableList<contactInfo> contacts = FXCollections.observableArrayList();
        String statement = "SELECT * FROM contacts AS c INNER JOIN appointments AS a ON c.Contact_ID = a.Contact_ID;";
        JDBC.setPreparedStatement(JDBC.getConnection(), statement);
        PreparedStatement preparedStatement = JDBC.getPreparedStatement();
        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();;
            while (resultSet.next()) {
                contactInfo newContact = new contactInfo(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")
                );
                contacts.add(newContact);
            }
            return contacts;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static contactInfo getContactId(String contactName) throws SQLException{
        String statement = "SELECT * FROM contacts WHERE Contact_Name=?";
        JDBC.setPreparedStatement(JDBC.getConnection(), statement);
        PreparedStatement preparedStatement = JDBC.getPreparedStatement();
        preparedStatement.setString(1, contactName);
        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();;
            while (resultSet.next()) {
                contactInfo newContact = new contactInfo(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")
                );

                return newContact;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }


}
