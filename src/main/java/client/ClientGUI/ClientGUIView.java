/*
IFT1025 - TP2
Auteur: Carl Thibault
Date: 17 avril 2023
 */

package client.clientgui;

import server.models.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Classe implémentant les éléments affichés par l'interface graphique d'inscription aux cours.
 */
public class ClientGUIView extends GridPane {
    private TableView tableView;
    private Button loadBtn;
    private Button sendBtn;
    private ComboBox sessionSelector;
    private TextField txtFieldFirstName;
    private TextField txtFieldLastName;
    private TextField txtFieldEmail;
    private TextField txtFieldIdNumber;
    private Alert alert;

    /**
     * Constructeur de l'interface graphique d'inscription aux cours.
     */
    public ClientGUIView() {

        // Left column ------------------------------------------------------------------------------------------------
        Label leftColumnTitle = new Label("Liste des cours");
        leftColumnTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

        tableView = new TableView<Course>();
        TableColumn courseCode = new TableColumn<Course, String>("Code");
        courseCode.setCellValueFactory(new PropertyValueFactory<Course, String>("code"));

        tableView = new TableView<Course>();
        TableColumn courseName = new TableColumn<Course, String>("Cours");
        courseName.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));

        tableView.getColumns().add(courseCode);
        tableView.getColumns().add(courseName);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableView.TableViewSelectionModel selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        sessionSelector = new ComboBox();

        loadBtn = new Button("Charger");

        sessionSelector.getItems().add("Automne");
        sessionSelector.getItems().add("Hiver");
        sessionSelector.getItems().add("Ete");
        sessionSelector.getSelectionModel().select(0);

        // this.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
        this.add(leftColumnTitle, 0, 0, 4, 1);
        this.add(tableView, 0, 1, 4, 6);
        this.add(sessionSelector, 0, 7, 2, 1);
        this.add(loadBtn, 3, 7, 1, 1);

        GridPane.setValignment(sessionSelector, VPos.BOTTOM);
        GridPane.setValignment(loadBtn, VPos.BOTTOM);


        // Right column -------------------------------------------------------------------------------------------------
        Label rightColumnTitle = new Label("Formulaire d'inscription");
        rightColumnTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

        Label labelFirstName = new Label("Prénom");
        Label labelLastName = new Label("Nom");
        Label labelEmail = new Label("Email");
        Label labelIdNumber = new Label("Matricule");

        txtFieldFirstName = new TextField();
        txtFieldLastName = new TextField();
        txtFieldEmail = new TextField();
        txtFieldIdNumber = new TextField();

        sendBtn = new Button("Envoyer");

        // this.add(node, columnIndex, rowIndex, columnSpan, rowSpan);
        this.add(rightColumnTitle, 4, 0, 4, 1);
        this.add(labelFirstName, 4, 1, 1, 1);
        this.add(txtFieldFirstName, 5, 1, 3, 1);
        this.add(labelLastName, 4, 2, 1, 1);
        this.add(txtFieldLastName, 5, 2, 3, 1);
        this.add(labelEmail, 4, 3, 1, 1);
        this.add(txtFieldEmail, 5, 3, 3, 1);
        this.add(labelIdNumber, 4, 4, 1, 1);
        this.add(txtFieldIdNumber, 5, 4, 3, 1);
        this.add(sendBtn, 7, 5, 1, 1);

        // Alert dialog box --------------------------------------------------------------------------------------------
        alert = new Alert(AlertType.NONE);

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

    /**
     * Accesseur du bouton "Charger".
     * @return
     */
    public Button getLoadButton() {
        return this.loadBtn;
    }

    /**
     * Accesseur du bouton "Envoyer".
     * @return
     */
    public Button getSendButton() {
        return this.sendBtn;
    }

    /**
     * Accesseur de la liste déroulante affichant les sessions à sélectionner.
     * @return
     */
    public ComboBox getSessionSelector() {
        return this.sessionSelector;
    }

    /**
     * Accesseur du tableau d'affichage des cours.
     * @return
     */
    public TableView getTableView() {
        return this.tableView;
    }

    /**
     * Accesseur du champ de saisie de texte du prénom.
     */
    public TextField getFirstName(){
        return this.txtFieldFirstName;
    }

    /**
     * Accesseur du champ de saisie de texte du nom.
     */
    public TextField getLastName(){
        return this.txtFieldLastName;
    }

    /**
     * Accesseur du champ de saisie de texte de l'email.
     */
    public TextField getEmail(){
        return this.txtFieldEmail;
    }

    /**
     * Accesseur du champ de saisie de texte du matricule.
     */
    public TextField getIdNumber(){
        return this.txtFieldIdNumber;
    }

    /**
     * Accesseur de la fenêtre d'alertes.
     */
    public Alert getAlert(){
        return this.alert;
    }
}
