package client;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

//        BorderPane root = new BorderPane();
//        VBox root = new VBox();

        GridPane grid = new GridPane();




        // Left column ------------------------------------------------------------------------------------------------

        TableView table = new TableView();

        Label leftColumnTitle = new Label("Liste des cours");
        leftColumnTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

        TableView tableView = new TableView();

        ComboBox sessionSelector = new ComboBox();

        Button loadBtn = new Button("Charger");

        sessionSelector.getItems().add("Automne");
        sessionSelector.getItems().add("Hiver");
        sessionSelector.getItems().add("Été");
        sessionSelector.getSelectionModel().select(0);

        // grid.add(node, columnIndex, rowIndex, columnSpan, rowSpan);

        grid.add(leftColumnTitle,  0,  0, 4, 1);
        grid.add(tableView,        0,  1, 4, 6);
        grid.add(sessionSelector,  0,  7, 2, 1);
        grid.add(loadBtn,          3,  7, 1, 1);

        GridPane.setValignment(sessionSelector, VPos.BOTTOM);
        GridPane.setValignment(loadBtn, VPos.BOTTOM);


        // Right column -------------------------------------------------------------------------------------------------

        Label rightColumnTitle = new Label("Formulaire d'inscription");
        rightColumnTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

        Label labelFirstName  = new Label("Prénom");
        Label labelLastName   = new Label("Nom");
        Label labelEmail      = new Label("Email");
        Label labelIdNumber   = new Label("Matricule");

        TextField  TxtFieldFirstName  = new TextField ();
        TextField  TxtFieldLastName   = new TextField ();
        TextField  TxtFieldlabelEmail = new TextField ();
        TextField  TxtFieldIdNumber   = new TextField ();

        Button sendBtn = new Button("Envoyer");


        // grid.add(node, columnIndex, rowIndex, columnSpan, rowSpan);

        grid.add(rightColumnTitle,   4,0, 4, 1);

        grid.add(labelFirstName,    4, 1, 1, 1);
        grid.add(TxtFieldFirstName, 5, 1, 3, 1);

        grid.add(labelLastName,     4, 2, 1, 1);
        grid.add(TxtFieldLastName,  5, 2, 3, 1);

        grid.add(labelEmail,        4, 3, 1, 1);
        grid.add(TxtFieldlabelEmail,5, 3, 3, 1);

        grid.add(labelIdNumber,     4, 4, 1, 1);
        grid.add(TxtFieldIdNumber,  5, 4, 3, 1);

        grid.add(sendBtn, 7, 5, 1, 1);


        // Grid settings -----------------------------------------------------------------------------------------------

        grid.setVgap(10);
        grid.setHgap(10);

        ColumnConstraints col0 = new ColumnConstraints();
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();
        ColumnConstraints col5 = new ColumnConstraints();
        ColumnConstraints col6 = new ColumnConstraints();
        ColumnConstraints col7 = new ColumnConstraints();

        grid.getColumnConstraints().addAll(col0, col1, col2, col3, col4, col5, col6, col7);

        col0.setPercentWidth(12.5);
        col1.setPercentWidth(12.5);
        col2.setPercentWidth(12.5);
        col3.setPercentWidth(12.5);
        col4.setPercentWidth(12.5);
        col5.setPercentWidth(12.5);
        col6.setPercentWidth(12.5);
        col7.setPercentWidth(12.5);

        GridPane.setHalignment(labelFirstName, HPos.RIGHT);
        GridPane.setHalignment(labelLastName, HPos.RIGHT);
        GridPane.setHalignment(labelEmail, HPos.RIGHT);
        GridPane.setHalignment(labelIdNumber, HPos.RIGHT);

        GridPane.setHalignment(leftColumnTitle, HPos.CENTER);
        GridPane.setHalignment(rightColumnTitle, HPos.CENTER);

        grid.setPadding(new Insets(15, 15, 15, 15));

        // Stage -------------------------------------------------------------------------------------------------------

//        root.setCenter(grid);

        Scene scene = new Scene(grid, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inscription UdeM");
        primaryStage.show();
    }
}

