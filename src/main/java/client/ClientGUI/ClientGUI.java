/*
IFT1025 - TP2
Auteur: Carl Thibault
Date: 17 avril 2023
 */

package client.clientgui;

import client.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe initialisant le client, le contrôleur et affichant l'interface graphique d'inscription aux cours.
 */
public class ClientGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Client model = new Client(); // create model object
        ClientGUIView view = new ClientGUIView(); // create view object
        ClientGUIController controller = new ClientGUIController(model, view); // create controller object
        Scene scene = new Scene(view, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inscription UdeM");
        primaryStage.show(); // show GUI
    }
}

