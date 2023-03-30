package scheduler.utilities;

import java.sql.Connection;
import java.sql.DriverManager;

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

    //Create a connection varriable.
    public static Connection connection;



    //Establish a database connection
    public static void openConnection(){
        try {
            //locate the driver
            Class.forName(driver);
            //create a connection object to get connection to the database.
            connection = DriverManager.getConnection(jdbUrl, userName, password);
            System.out.println("The connection was successfull to the DB");
        }
        catch(Exception e) {
            System.out.println("ERRROR: " + e.getMessage());
        }
    }

    //Close databse connection
    public static void closeConnection(){
        try {
            connection.close();
            System.out.println("The connection was CLOSED");
        }
        catch(Exception e) {
            System.out.println("ERRROR: " + e.getMessage());
        }
    }


}
