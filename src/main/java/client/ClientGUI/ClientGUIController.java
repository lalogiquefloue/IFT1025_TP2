package client.ClientGUI;

import client.Client;
import client.ClientGUI.ClientGUIView;
import javafx.collections.ObservableList;
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

        this.view.getLoadButton().setOnAction((action) -> {
            String session = view.getSessionSelector().getValue().toString();
            try {
                courses = model.loadCoursesBySession(session);
                view.loadCourses2TableView(courses);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.view.getSendButton().setOnAction((action) -> {
            String firstName = view.getFirstName().getText();
            String lastName = view.getLastName().getText();
            String email = view.getEmail().getText();
            String idNumber = view.getIdNumber().getText();

            Course selectedCourse = (Course) view.getTableView().getSelectionModel().getSelectedItems().get(0);

            RegistrationForm rf = new RegistrationForm(firstName, lastName, email, idNumber, selectedCourse);
            model.askServer("INSCRIRE", "", rf);
        });


    }


}
