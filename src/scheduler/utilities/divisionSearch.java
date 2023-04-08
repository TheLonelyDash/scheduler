package scheduler.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduler.model.country;
import scheduler.model.division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class divisionSearch {

    public static ObservableList<division> getAllDivisions() throws SQLException {
        ObservableList<division> divisions = FXCollections.observableArrayList();
        String statement = "SELECT * FROM first_level_divisions;";
        DBQuery.setPreparedStatement(JDBC.getConnection(), statement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
        try{
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                division newDivision = new division(resultSet.getInt("Division_ID"), resultSet.getString("Division"), resultSet.getInt("Country_ID"));
                divisions.add(newDivision);
            }
            return  divisions;
        } catch (SQLException e) {
            System.out.println("Jesus. Not another error!");
            e.printStackTrace();
            return null;
        }
    }


    /***
     * This is a method that retrieves a division ID.
     * @param division the division is the parameter and argument
     * @return There is no return.
     * @throws SQLException
     */
    public static division getDivisionID(String division) throws SQLException {
        String queryStatement = "SELECT * FROM first_level_divisions WHERE Division=?";
        DBQuery.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
        preparedStatement.setString(1, division);
        try {
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                division newDivision = new division(resultSet.getInt("Division_ID"), resultSet.getString("Division"), resultSet.getInt("Country_ID"));
                return newDivision;
            }
        } catch (SQLException e) {
            System.out.println("Well, there's another error...");
            e.printStackTrace();
        }
        return null;
    }


    public static ObservableList<division> getDivisionsByCountry(String country) throws SQLException {
        country newCountry = countrySearch.getCountryId(country);
        ObservableList<division> divisions = FXCollections.observableArrayList();
        String queryStatement = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID=?;";
        DBQuery.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
        preparedStatement.setInt(1, newCountry.getCountryId());
        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {

                division newDivision = new division(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("COUNTRY_ID")
                );
                divisions.add(newDivision);
            }
            return divisions;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
