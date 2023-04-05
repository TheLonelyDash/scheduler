package scheduler.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduler.model.country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class countrySearch {

    /***
     * This is the method that creates a list of all the countries available in the database.
     * @return returns an ObservableList containing all of the country objects
     * @throws SQLException prints a stacktrace in a flippant manner.
     */
    public static ObservableList<country> getAllCountries() throws SQLException{
        ObservableList<country> countries = FXCollections.observableArrayList();
        String statement = "SELECT * FROM countries;";
        DBQuery.setPreparedStatement(JDBC.getConnection(), statement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
        try {
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                country newCountry = new country(resultSet.getInt("Country_ID"), resultSet.getString("Country"));
                countries.add(newCountry);
            }
            return countries;
        }
        catch(Exception e) {
            System.out.println("Not ANOTHER error!  What is it this time?");
            e.printStackTrace();
            return null;
        }
    }

}
