package main;

import domain.Appointment;
import domain.Patient;
import exceptions.DuplicateItemException;
import exceptions.ItemNotFound;
import gui.PatientsAppointmentsController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.*;
import service.DentalService;
import ui.UI;

import java.io.FileReader;
import java.io.IOException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;



// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main extends Application {
    public static void main(String[] args){

            launch();
        }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Dentist Clinic \uD83E\uDDB7");
        DentalService dentalService = new DentalService();
        PatientsAppointmentsController patientsAppointmentsController = new PatientsAppointmentsController(dentalService);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PatientsAppointmentsGUI.fxml"));
        loader.setController(patientsAppointmentsController);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
        
    }
}



