package client;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import client.models.Course;

import client.Client;

import java.io.IOException;
import java.util.ArrayList;

public class ClientGraphique extends Application {

    public static void main(String[] args) throws IOException {

//        Client client = new Client();
//
//        ArrayList<Course> autumnCourses = client.loadCoursesBySession("Automne");
//        ArrayList<Course> winterCourses = client.loadCoursesBySession("Hiver");
//        ArrayList<Course> summerCourses = client.loadCoursesBySession("Ete");

        launch(args);


    }

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        GridPane grid = new GridPane();



        // grid.add(node, columnIndex, rowIndex, columnSpan, rowSpan);

        // Left column ------------------------------------------------------------------------------------------------

        TableView table = new TableView();

        Label rightColumnTitle = new Label("Liste des cours");

        grid.add(rightColumnTitle, 0, 0, 3, 1);

        // Right column -------------------------------------------------------------------------------------------------

        Label leftColumnTitle = new Label("Formulaire d'inscription");
        Label labelFirstName  = new Label("Prénom");
        Label labelLastName   = new Label("Nom");
        Label labelEmail      = new Label("Email");
        Label labelIdNumber   = new Label("Matricule");

        TextField  TxtFieldFirstName  = new TextField ();
        TextField  TxtFieldLastName   = new TextField ();
        TextField  TxtFieldlabelEmail = new TextField ();
        TextField  TxtFieldIdNumber   = new TextField ();

        Button sendBtn = new Button("Envoyer");

        grid.add(leftColumnTitle,   4,0, 3, 1);

        grid.add(labelFirstName,    4, 1, 1, 1);
        grid.add(TxtFieldFirstName, 5, 1, 2, 1);

        grid.add(labelLastName,     4, 2, 1, 1);
        grid.add(TxtFieldLastName,  5, 2, 2, 1);

        grid.add(labelEmail,        4, 3, 1, 1);
        grid.add(TxtFieldlabelEmail,5, 3, 2, 1);

        grid.add(labelIdNumber,     4, 4, 1, 1);
        grid.add(TxtFieldIdNumber,  5, 4, 2, 1);

        grid.add(sendBtn, 6, 6, 1, 1);

        // Grid settings -----------------------------------------------------------------------------------------------

        grid.setVgap(5);
        grid.setHgap(10);

//        ColumnConstraints column1 = new ColumnConstraints();
//        ColumnConstraints column2 = new ColumnConstraints();
//
//        grid.getColumnConstraints().add(column1);
//        grid.getColumnConstraints().add(column2);
//
//        column1.setPrefWidth(300);
//        column2.setPrefWidth(300);


        grid.setPadding(new Insets(15, 15, 15, 15));

        // Stage -------------------------------------------------------------------------------------------------------
        root.setCenter(grid);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inscription UdeM");
        primaryStage.show();
    }
}

