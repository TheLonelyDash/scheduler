package scheduler.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC {

    //First part of jdbc URL
    private static final String protocol = "jdbc";

    //Second part of jdbc URL
    private static final String vendor = ":mysql:";

    //Third part of the jdbc URL, this will be for the localhost of the Virtual Machine
    private static final String location = "//localhost/";

    //Fourth part of the jdbc URL, the name of the database. Taken from MySQL Workbench.
    private static final String databaseName = "client_schedule";

    //The conglomerated URL for the database (including the property to switch between time-zones at the end)
    private static final String jdbUrl = protocol + vendor + location + databaseName + "?connectionTimeZone=SERVER";

    //Reference the SQL driver interface.
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    //Reference the user name and password for the virtual machine
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";

    //Create a connection variable.
    public static Connection connection;

    //Create a PreparedStatement variable.
    private static PreparedStatement preparedStatement;

    /***
     * This is a method that creates the Prepared Statement object.
     * @param connection to the database connection
     * @param sqlStatement String for the SQL statement
     * @throws SQLException Catches andy exception
     */
    public static void setPreparedStatement (Connection connection, String sqlStatement) throws SQLException{
        preparedStatement = connection.prepareStatement(sqlStatement);
    }


    /***
     * This is the return method so that the prepared statement may be globally accessible.
     * @return returns the prepared statement
     */
    public static PreparedStatement getPreparedStatement(){
        return preparedStatement;
    }


    /***
     * Establishes a database connection.
     */
    public static void openConnection(){
        try {
            //locate the driver
            Class.forName(driver);
            //create a connection object to get connection to the database.
            connection = DriverManager.getConnection(jdbUrl, userName, password);
            System.out.println("The connection was successful to the DB");
        }
        catch(Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /***
     * Closes the database connection.
     */
    public static void closeConnection(){
        try {
            connection.close();
            System.out.println("The connection was CLOSED");
        }
        catch(Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }


}
