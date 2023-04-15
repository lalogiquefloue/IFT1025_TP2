/*
IFT1025 - TP2
Auteur: Carl Thibault
Date: 16 avril 2023
 */

package client.ClientGUI;

import client.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe initialisant le client, le contrôleur et affichant l'interface graphique.
 */
public class ClientGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Client model = new Client();
        ClientGUIView view = new ClientGUIView();
        ClientGUIController controller = new ClientGUIController(model, view);
        Scene scene = new Scene(view, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inscription UdeM");
        primaryStage.show();
    }
}

