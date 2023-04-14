package scheduler.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import scheduler.model.appointment;
import scheduler.utilities.appointmentSearch;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class reportsController implements Initializable {

    @FXML Button reportBackButton;
    @FXML Label reportScheduler;
    @FXML Tab scheduleTab;
    @FXML Tab totalAppTab;
    @FXML Tab totalCountryTab;
    static ObservableList<appointment> appointments;
    @FXML TableView<appointment> reportsScheduleTableView;
    @FXML TableColumn<?, ?> appIDCol;
    @FXML TableColumn titleCol;
    @FXML TableColumn<?, ?> typeCol1;
    @FXML TableColumn<?, ?> descriptionCol;
    @FXML TableColumn<?, ?> startCol;
    @FXML TableColumn<?, ?> endCol;
    @FXML TableColumn<?, ?> custIDCol;
    @FXML TableColumn<?, ?> locationCol;
    @FXML TableColumn<?, ?> contactCol;
    @FXML TableColumn<?, ?> user_IDCol;


    public void reportBackButtonClick(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/scheduler/view/mainMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        if (Locale.getDefault().getLanguage() == "en") {stage.setTitle("Main Menu");}
        else{stage.setTitle("Menu Principal");}
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            appointments = appointmentSearch.getAllAppointments();
            reportsScheduleTableView.setItems(appointments);
            appIDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            custIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        }
        catch (Exception e){
            e.printStackTrace();
        }


        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        reportBackButton.setText(rb.getString("back"));
        reportScheduler.setText(rb.getString("reportScheduler"));
        scheduleTab.setText(rb.getString("schedule"));
        totalAppTab.setText(rb.getString("totalAppTab"));
        totalCountryTab.setText(rb.getString("totalCountryTab"));
    }
}
