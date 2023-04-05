package scheduler.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduler.model.appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class appointmentSearch {


    /***
     * This is a method that utilizes an inner join to combine all appointments by their contact_ID,  then gets them for use!
     * @return
     * @throws SQLException
     */
    public static ObservableList<appointment> getAllAppointments() throws SQLException {
        ObservableList<appointment> appointments = FXCollections.observableArrayList();
        String statement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID;";
        DBQuery.setPreparedStatement(JDBC.getConnection(), statement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
        try {
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                appointment newAppointment = new appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );
                appointments.add(newAppointment);
            }
        } catch (Exception e) {
            System.out.println("ERROR!" + e.getMessage());
        }
        return appointments;
    }








}
