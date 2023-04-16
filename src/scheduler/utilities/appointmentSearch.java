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

/***
 * The appointmentSearch class is utilized to access the database and use SQL statements in regards to appointments.
 */
public class appointmentSearch {


    /***
     * This is a method that utilizes an inner join to combine all appointments by their contact_ID,  then gets them for use!
     * @return
     * @throws SQLException
     */
    public static ObservableList<appointment> getAllAppointments() throws SQLException {
        ObservableList<appointment> appointments = FXCollections.observableArrayList();
        String statement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID;";
        dbSearch.setPreparedStatement(JDBC.getConnection(), statement);
        PreparedStatement preparedStatement = dbSearch.getPreparedStatement();
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

    /***
     * This method creates a list which stores all of the appointments within the next thirty days of the current date.
     * It is then connected to the Monthly radiobutton which populates these appointments on the appointmentsTableView.
     * @return
     * @throws SQLException
     */
    public static ObservableList<appointment> getAppointmentsByMonth() throws SQLException {
        ObservableList<appointment> appointments = FXCollections.observableArrayList();
        LocalDateTime todaysDate = LocalDateTime.now();
        LocalDateTime nextMonth = todaysDate.plusDays(30);
        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Start > ? AND Start < ?;";
        dbSearch.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = dbSearch.getPreparedStatement();
        preparedStatement.setDate(1, java.sql.Date.valueOf(todaysDate.toLocalDate()));
        preparedStatement.setDate(2, java.sql.Date.valueOf(nextMonth.toLocalDate()));
        try {
            preparedStatement.execute();
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
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }


    /***
     * This method creates a list which stores all of the appointments within the next seven days of the current date.
     * It is then connected to the Weekly radiobutton which populates these appointments on the appointmentsTableView.
     * @return
     * @throws SQLException
     */
    public static ObservableList<appointment> getAppointmentsByWeek() throws SQLException {
        ObservableList<appointment> appointments = FXCollections.observableArrayList();
        LocalDateTime todaysDate = LocalDateTime.now();
        LocalDateTime nextWeek = todaysDate.plusDays(7);
        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Start > ? AND Start < ?;";
        dbSearch.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = dbSearch.getPreparedStatement();
        preparedStatement.setDate(1, java.sql.Date.valueOf(todaysDate.toLocalDate()));
        preparedStatement.setDate(2, java.sql.Date.valueOf(nextWeek.toLocalDate()));
        try {
            preparedStatement.execute();
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
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }


    /***
     * This method takes an appointment object and adds it to the database!
     * @param contactName
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customer_ID
     * @param user_ID
     * @return
     * @throws SQLException
     */
    public static boolean addAppointment(String contactName, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customer_ID, Integer user_ID) throws SQLException{
        contactInfo contact = contactSearch.getContactId(contactName);
        String insertStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        dbSearch.setPreparedStatement(JDBC.getConnection(), insertStatement);
        PreparedStatement preparedStatement = dbSearch.getPreparedStatement();
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


    /***
     * This method removes an appointment object from the database.
     * @param appointmentId
     * @return
     * @throws SQLException
     */
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

    /***
     * This method searches the database for appointments with a specific customer ID.
     * @param CustomerID
     * @return
     * @throws SQLException
     */
    public static ObservableList<appointment> getAppointmentsByCustomerID(int CustomerID) throws SQLException {
        ObservableList<appointment> appointments = FXCollections.observableArrayList();
        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Customer_ID=?;";
        dbSearch.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = dbSearch.getPreparedStatement();
        preparedStatement.setInt(1, CustomerID);
        try {
            preparedStatement.execute();
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
            e.printStackTrace();
            return null;
        }
    }


    /***
     * This method searches the database to return appointments by their appointment ID.
     * @param AppointmentID
     * @return
     * @throws SQLException
     */
    public static appointment getAppByID(int AppointmentID) throws SQLException {
        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Appointment_ID=?;";
        dbSearch.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = dbSearch.getPreparedStatement();
        preparedStatement.setInt(1, AppointmentID);
        try {
            preparedStatement.execute();
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
                return newAppointment;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }


    /***
     * This method updates a specific appointment in the database.
     * @param contactName
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userID
     * @param appointmentID
     * @return
     * @throws SQLException
     */
    public static boolean updateAppointment(String contactName, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customerId, Integer userID, Integer appointmentID) throws SQLException {
        contactInfo contact = contactSearch.getContactId(contactName);
        String updateStatement = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, Contact_ID=?, User_ID=? WHERE Appointment_ID = ?;";
        dbSearch.setPreparedStatement(JDBC.getConnection(), updateStatement);
        PreparedStatement preparedStatement = dbSearch.getPreparedStatement();
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, type);
        preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
        preparedStatement.setInt(7, customerId);
        preparedStatement.setInt(8, contact.getContact_ID());
        preparedStatement.setInt(9, userID);
        preparedStatement.setInt(10, appointmentID);
        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }



}
