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
    private Client model;
    private ClientGUIView view;
    private ArrayList<Course> courses;

    /**
     * Constructeur du contrôleur.
     * @param model Objet <code>Client</code> afin d'en accéder les méthodes.
     * @param view Objet <code>ClientGUIView</code> afin d'afficher l'interface graphique défini.
     */
    public ClientGUIController(Client model, ClientGUIView view) {
        this.model = model;
        this.view = view;

        Alert alert = this.view.getAlert();

        // Load courses from selected session into the tableView
        this.view.getLoadButton().setOnAction((action) -> {
            String session = view.getSessionSelector().getValue().toString();
            try {
                courses = model.loadCoursesBySession(session);
                loadCourses2TableView(courses);
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
                        model.sendObjectToServer("INSCRIRE", "", rf);
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
            if (!errorLog.equals("")) {
                errorLog += "Veuillez réessayer.";
                alert.setAlertType(AlertType.ERROR);
                alert.setContentText(errorLog);
                alert.show();
            }
        });
    }

    /**
     * Méthode permettant d'afficher les cours contenu dans une liste de cours dans le tableau de l'interface graphique.
     * @param courses Liste d'objet <code>Course</code>.
     */
    public void loadCourses2TableView(ArrayList<Course> courses) {
        view.getTableView().getItems().clear();
        for (Course cours : courses) {
            view.getTableView().getItems().add(cours);
        }
    }
}
