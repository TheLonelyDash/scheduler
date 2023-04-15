package scheduler.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class alerts {

    public static boolean alert(String x,String y,String z){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(x);
        alert.setHeaderText(y);
        alert.setContentText(z);
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    public static void alertI(String x, String y, String z){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(x);
        alert.setHeaderText(y);
        alert.setContentText(z);
        alert.showAndWait();
    }

    public static void alertE(String x, String y, String z){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(x);
        alert.setHeaderText(y);
        alert.setContentText(z);
        alert.showAndWait();
    }

}
