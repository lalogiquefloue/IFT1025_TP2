/*
IFT1025 - TP2
Auteur: Carl Thibault
Date: 17 avril 2023
 */

package server;

/**
 * Classe servant à lancer l'exécution du serveur.
 */
public class ServerLauncher {
    public final static int PORT = 1337;

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(PORT);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}