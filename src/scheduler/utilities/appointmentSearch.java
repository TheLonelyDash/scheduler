package scheduler.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduler.model.appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class appointmentSearch {

    public static ObservableList<appointment> getAppointments() throws SQLException{
        ObservableList<appointment> appointments = FXCollections.observableArrayList();

        String statement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c on a.CONTACT_ID=c.Contact_ID;";

        DBQuery.setPreparedStatement(JDBC.getConnection(), statement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        try{
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
        }
        catch{}



    }

}
