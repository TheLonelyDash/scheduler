package scheduler.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userSearch {


    public static boolean checkNameAndPassword(String username, String password) throws SQLException {
        String searchStatement = "SELECT * FROM users WHERE User_Name=? AND Password=?";

        DBQuery.setPreparedStatement(JDBC.getConnection(), searchStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            return (resultSet.next());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }


}
