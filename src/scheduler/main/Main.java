package scheduler.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scheduler.utilities.JDBC;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/loginMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Log In!");
        stage.show();
    }

    /***
     * The main method connects to the database prior to launching the main stage of fxml.
     * @param args The argument that launches the application.
     */
    public static void main(String[] args) {
        //Connects the database
        JDBC.openConnection();
        //Opens the application
        launch(args);
        //Disconnects the database
        JDBC.closeConnection();
    }
}
