package client.ClientGUI;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ClientGUIView extends GridPane {
        public ClientGUIView() {
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

                // this.add(node, columnIndex, rowIndex, columnSpan, rowSpan);

                this.add(leftColumnTitle,  0,  0, 4, 1);
                this.add(tableView,        0,  1, 4, 6);
                this.add(sessionSelector,  0,  7, 2, 1);
                this.add(loadBtn,          3,  7, 1, 1);

                GridPane.setValignment(sessionSelector, VPos.BOTTOM);
                GridPane.setValignment(loadBtn, VPos.BOTTOM);


                // Right column -------------------------------------------------------------------------------------------------
                Label rightColumnTitle = new Label("Formulaire d'inscription");
                rightColumnTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

                Label labelFirstName  = new Label("Prénom");
                Label labelLastName   = new Label("Nom");
                Label labelEmail      = new Label("Email");
                Label labelIdNumber   = new Label("Matricule");

                TextField TxtFieldFirstName  = new TextField();
                TextField TxtFieldLastName   = new TextField();
                TextField TxtFieldEmail      = new TextField();
                TextField TxtFieldIdNumber   = new TextField();

                Button sendBtn = new Button("Envoyer");


                // this.add(node, columnIndex, rowIndex, columnSpan, rowSpan);

                this.add(rightColumnTitle,   4,0, 4, 1);

                this.add(labelFirstName,    4, 1, 1, 1);
                this.add(TxtFieldFirstName, 5, 1, 3, 1);

                this.add(labelLastName,     4, 2, 1, 1);
                this.add(TxtFieldLastName,  5, 2, 3, 1);

                this.add(labelEmail,        4, 3, 1, 1);
                this.add(TxtFieldEmail,5, 3, 3, 1);

                this.add(labelIdNumber,     4, 4, 1, 1);
                this.add(TxtFieldIdNumber,  5, 4, 3, 1);

                this.add(sendBtn, 7, 5, 1, 1);


                // Grid settings -----------------------------------------------------------------------------------------------
                this.setVgap(10);
                this.setHgap(10);

                ColumnConstraints col0 = new ColumnConstraints();
                ColumnConstraints col1 = new ColumnConstraints();
                ColumnConstraints col2 = new ColumnConstraints();
                ColumnConstraints col3 = new ColumnConstraints();
                ColumnConstraints col4 = new ColumnConstraints();
                ColumnConstraints col5 = new ColumnConstraints();
                ColumnConstraints col6 = new ColumnConstraints();
                ColumnConstraints col7 = new ColumnConstraints();

                this.getColumnConstraints().addAll(col0, col1, col2, col3, col4, col5, col6, col7);

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

                this.setPadding(new Insets(15, 15, 15, 15));

        }



}
