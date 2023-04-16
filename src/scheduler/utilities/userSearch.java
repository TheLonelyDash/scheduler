package scheduler.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduler.model.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * The userSearch class is utilized to access the database and use SQL statements in regards to the users.
 */
public class userSearch {

    /***
     * This method checks whether the username and passwords match in the database.
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public static boolean checkNameAndPassword(String username, String password) throws SQLException {
        String searchStatement = "SELECT * FROM users WHERE User_Name=? AND Password=?";
        dbSearch.setPreparedStatement(JDBC.getConnection(), searchStatement);
        PreparedStatement preparedStatement = dbSearch.getPreparedStatement();
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            return (resultSet.next());
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    /**
     * This method returns all users.
     * @return
     * @throws SQLException
     */
    public static ObservableList<user> getUsers() throws SQLException {
        ObservableList<user> users = FXCollections.observableArrayList();
        String statement = "SELECT * FROM users;";
        dbSearch.setPreparedStatement(JDBC.getConnection(), statement);
        PreparedStatement preparedStatement = dbSearch.getPreparedStatement();
        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                user newUser = new user(resultSet.getInt("User_ID"), resultSet.getString("User_Name"), resultSet.getString("Password"));
                users.add(newUser);
            }
            return users;
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

}
