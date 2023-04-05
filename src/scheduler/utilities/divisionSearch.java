package scheduler.utilities;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


}
