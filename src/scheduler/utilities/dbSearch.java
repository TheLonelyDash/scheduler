package scheduler.utilities;

import java.sql.*;

/***
 * The dbSearch class is used for general SQL searching of the database.
 */
public class dbSearch {

    private static PreparedStatement statement;

    /***
     * This is the setting method that provides the Prepared Statement object!
     * @param connection Connection to the Database
     * @param sqlStatement The SQL Statement string that is a command
     * @throws SQLException The catching statment that will provide the stacktrace and error issues.
     */
    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException{
        statement = connection.prepareStatement(sqlStatement);
    }

    /***
     * This method provides and returns the Prepared Statement Object.
     * @return The return is the prepared statement.
     */
    public static PreparedStatement getPreparedStatement(){
        return statement;
    }
}
