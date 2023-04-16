package scheduler.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/***
 * The alerts class provides the three kinds of alerts used in the application.
 */
public class alerts {

    /***
     * This method initializes a confirmation alert.
     * @param x title
     * @param y header
     * @param z content
     * @return
     */
    public static boolean alert(String x,String y,String z){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(x);
        alert.setHeaderText(y);
        alert.setContentText(z);
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    /***
     * This method initializes an information alert.
     * @param x title
     * @param y header
     * @param z content
     */
    public static void alertI(String x, String y, String z){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(x);
        alert.setHeaderText(y);
        alert.setContentText(z);
        alert.showAndWait();
    }

    /***
     * This method initializes an error alert.
     * @param x title
     * @param y header
     * @param z content
     */
    public static void alertE(String x, String y, String z){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(x);
        alert.setHeaderText(y);
        alert.setContentText(z);
        alert.showAndWait();
    }

}
