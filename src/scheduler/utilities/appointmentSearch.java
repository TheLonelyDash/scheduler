package scheduler.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduler.model.appointment;
import scheduler.model.contactInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
            return appointments;
        } catch (Exception e) {
            System.out.println("ERROR!" + e.getMessage());
            return null;
        }
    }

    public static ObservableList<appointment> getAppointmentsByMonth() throws SQLException {

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime prevMonth = today.minusMonths(1);

        ObservableList<appointment> appointments = FXCollections.observableArrayList();
        String statement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Start < ? AND Start > ?;";
        DBQuery.setPreparedStatement(JDBC.getConnection(), statement);
        PreparedStatement preparedStatement = JDBC.getPreparedStatement();
        preparedStatement.setDate(1, java.sql.Date.valueOf(today.toLocalDate()));
        preparedStatement.setDate(2, java.sql.Date.valueOf(prevMonth.toLocalDate()));

        try {
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
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
            return appointments;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public static ObservableList<appointment> getAppointmentsByWeek() throws SQLException {

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime prevWeek = today.minusDays(7);

        ObservableList<appointment> appointments = FXCollections.observableArrayList();
        String statement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Start < ? AND Start > ?;";
        DBQuery.setPreparedStatement(JDBC.getConnection(), statement);
        PreparedStatement preparedStatement = JDBC.getPreparedStatement();
        preparedStatement.setDate(1, java.sql.Date.valueOf(today.toLocalDate()));
        preparedStatement.setDate(2, java.sql.Date.valueOf(prevWeek.toLocalDate()));

        try {
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
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
            return appointments;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public static boolean addAppointment(String contactName, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customer_ID, Integer user_ID) throws SQLException{
        contactInfo contact = contactSearch.getContactId(contactName);

        String insertStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DBQuery.setPreparedStatement(JDBC.getConnection(), insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, type);
        preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
        preparedStatement.setInt(7, customer_ID);
        preparedStatement.setInt(8, contact.getContact_ID());
        preparedStatement.setInt(9, user_ID);

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Success!");
            } else {
                System.out.println("Not...successful.");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

public static boolean deleteAppointment(int appointmentId) throws SQLException{
        String statement = "DELETE from appointments WHERE Appointment_ID=?";
        JDBC.setPreparedStatement(JDBC.getConnection(), statement);
        PreparedStatement preparedStatement = JDBC.getPreparedStatement();
        preparedStatement.setInt(1, appointmentId);
    try {
        preparedStatement.execute();
        if (preparedStatement.getUpdateCount() > 0) {
            System.out.println("Success!");
        } else {
            System.out.println("Not really successful, huh?");
        }
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

}
