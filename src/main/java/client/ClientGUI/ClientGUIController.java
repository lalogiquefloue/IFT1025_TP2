package client.ClientGUI;

import client.Client;
import client.ClientGUI.ClientGUIView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import server.models.*;

import java.io.IOException;
import java.util.ArrayList;


public class ClientGUIController {
    private Client model;
    private ClientGUIView view;
    private ArrayList<Course> courses;

    public ClientGUIController(Client model, ClientGUIView view) {
        this.model = model;
        this.view = view;

        Alert alert = this.view.getAlert();

        // Load courses from selected session into the tableView
        this.view.getLoadButton().setOnAction((action) -> {
            String session = view.getSessionSelector().getValue().toString();
            try {
                courses = model.loadCoursesBySession(session);
                view.loadCourses2TableView(courses);
            } catch (Exception e) {
                alert.setAlertType(AlertType.ERROR);
                alert.setContentText("Impossible de charger les cours pour le moment.\nVeuillez réessayer.\n");
                alert.show();
            }
        });

        // Send registration form to server
        this.view.getSendButton().setOnAction((action) -> {
            String errorLog = "";
            Course selectedCourse = null;

            try {
                selectedCourse = (Course) view.getTableView().getSelectionModel().getSelectedItems().get(0);
            } catch (IndexOutOfBoundsException e) {
                errorLog += "Aucun cours sélectionné. \n";
            }
            if (errorLog == "") {

                String firstName = view.getFirstName().getText();
                boolean firstNameIsEmpty = firstName.isEmpty();

                String lastName = view.getLastName().getText();
                boolean lastNameIsEmpty = lastName.isEmpty();

                String email = view.getEmail().getText();
                boolean emailIsValid = email.matches("^(.+)@(.+)$");

                String idNumber = view.getIdNumber().getText();
                boolean idIsValid = idNumber.matches("^[0-9]{8}$");

                if (idIsValid && emailIsValid && !firstNameIsEmpty && !lastNameIsEmpty) {
                    RegistrationForm rf = new RegistrationForm(firstName, lastName, email, idNumber, selectedCourse);
                    try {
                        model.sendObject2Server("INSCRIRE", rf);
                        alert.setAlertType(AlertType.INFORMATION);
                        alert.setContentText("Félicitations! " + firstName + " " + lastName + " est inscrit(e) avec succès au cours " + selectedCourse.getCode() + "!");
                        alert.show();
                    } catch (IOException e) {
                        alert.setAlertType(AlertType.ERROR);
                        alert.setContentText("Impossible de transmettre la demande d'inscription au serveur pour le moment.");
                        alert.show();
                    }
                } else {
                    if (firstNameIsEmpty){
                        errorLog += "- Entrez votre prénom.\n";
                    }
                    if (lastNameIsEmpty){
                        errorLog += "- Entrez votre nom de famille.\n";
                    }
                    if (!idIsValid) {
                        errorLog += "- Matricule invalide.\n";
                    }
                    if (!emailIsValid) {
                        errorLog += "- Adresse email invalide.\n";
                    }
                }
            }
            if (errorLog != "") {
                errorLog += "Veuillez réessayer.";
                alert.setAlertType(AlertType.ERROR);
                alert.setContentText(errorLog);
                alert.show();
            }
        });
    }
}
