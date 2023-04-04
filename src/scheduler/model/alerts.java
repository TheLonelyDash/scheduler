package scheduler.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Locale;
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

}
