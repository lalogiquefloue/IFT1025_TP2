/*
IFT1025 - TP2
Auteur: Carl Thibault
Date: 16 avril 2023
 */

package client.ClientGUI;

import client.Client;
import server.models.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe contrôleur établissant la logique entre les interactions de l'utilisateur avec l'interface graphique
 * et les méthodes du client.
 */
public class ClientGUIController {
    private ClientGUIView view;
    private ArrayList<Course> courses;

    /**
     * Constructeur du contrôleur.
     *
     * @param model Objet <code>Client</code> afin d'en accéder les méthodes.
     * @param view  Objet <code>ClientGUIView</code> afin d'afficher l'interface graphique défini.
     */
    public ClientGUIController(Client model, ClientGUIView view) {
        this.view = view;
        Alert alert = this.view.getAlert();

        // Load courses from selected session into the tableView
        this.view.getLoadButton().setOnAction((action) -> {
            String session = view.getSessionSelector().getValue().toString();
            try {
                courses = model.loadCoursesBySession(session);
                loadCourses2TableView(courses);
            } catch (Exception e) {
                showAlert(alert, "ERROR", "Impossible de charger les cours via le serveur pour le moment.\nVeuillez réessayer.\n");
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
            if (errorLog.equals("")) {

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
                        model.sendObjectToServer("INSCRIRE", "", rf);
                        showAlert(alert, "INFORMATION", "Félicitations! " + firstName + " " + lastName + " est inscrit(e) avec succès au cours " + selectedCourse.getCode() + "!");
                    } catch (IOException e) {
                        showAlert(alert, "ERROR", "Impossible de transmettre la demande d'inscription au serveur pour le moment.\nVeuillez réessayer.\n");
                    }
                } else {
                    if (firstNameIsEmpty) {
                        errorLog += "- Entrez votre prénom.\n";
                    }
                    if (lastNameIsEmpty) {
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
            if (!errorLog.equals("")) {
                errorLog += "Veuillez réessayer.";
                showAlert(alert, "ERROR", errorLog);
            }
        });
    }

    // Method taking an array of courses and showing them in th GUI table view
    private void loadCourses2TableView(ArrayList<Course> courses) {
        view.getTableView().getItems().clear();
        for (Course cours : courses) {
            view.getTableView().getItems().add(cours);
        }
    }

    // Method showing an alert dialog box taking a type and a message.
    private void showAlert(Alert alert, String alertType, String msg) {
        switch (alertType) {
            case "ERROR" -> alert.setAlertType(AlertType.ERROR);
            case "INFORMATION" -> alert.setAlertType(AlertType.INFORMATION);
            case "CONFIRMATION" -> alert.setAlertType(AlertType.CONFIRMATION);
            case "WARNING" -> alert.setAlertType(AlertType.WARNING);
            case "NONE" -> alert.setAlertType(AlertType.NONE);

        }
        alert.setContentText(msg);
        alert.show();
    }
}
