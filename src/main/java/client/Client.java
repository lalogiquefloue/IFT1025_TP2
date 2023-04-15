package client;

import server.models.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    protected static ObjectInputStream objectInputStream;
    protected static ObjectOutputStream objectOutputStream;
    private static Socket cS;
    private final static int PORT = 1337;
    private final static String SERVER_IP_ADDRESS = "127.0.0.1";

    /**
     * Méthode servant à transmettre une commande au serveur.
     * @param cmd Nom de la commande à transmettre.
     * @param arg Argument de la commande (optionnel).
     */
    public static void askServer(String cmd, String arg) {
        try {
            objectOutputStream.writeObject(cmd + " " + arg);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant d'établir une connection avec le serveur pour permettre la transmission de données.
     * @throws ConnectException Exception lorsque la connection est impossible sur l'adresse IP et le port spécifié.
     */
    public static void connect() throws ConnectException {
        try {
            cS = new Socket(SERVER_IP_ADDRESS, PORT);
            objectOutputStream = new ObjectOutputStream(cS.getOutputStream());
            objectInputStream = new ObjectInputStream(cS.getInputStream());
        } catch (ConnectException x) {
            System.out.println("Connexion impossible sur port " + PORT + ": pas de serveur.");
            throw new ConnectException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode exécutant la fermeture de la connection avec le serveur.
     * @throws IOException Exception s'il y a une I/O problématique et que les actions prévues sont impossibles à exécuter.
     */
    public static void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        cS.close();
    }

    /**
     * À compléter. MAKE MORE ABSTRACT?
     *
     * @param arg
     * @return
     * @throws RuntimeException
     */
    public static ArrayList<Course> loadCoursesBySession(String arg) throws RuntimeException {
        try {
            connect();
            askServer("CHARGER", arg);
            Object courses = objectInputStream.readObject();
            disconnect();
            return (ArrayList<Course>) courses;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Méthode servant à transmettre un objet au serveur à l'aide d'une commande et des ses arguments.
     * @param cmd    Nom de la commande à transmettre au serveur.
     * @param args   Arguments de la commande transmise au serveur (optionnel).
     * @param object Objet à transmettre avec la commande.
     * @throws IOException Exception s'il y a une I/O problématique et que les actions prévues sont impossibles à exécuter.
     */
    public static void sendObjectToServer(String cmd, String args, Object object) throws IOException {
        connect();
        askServer(cmd, args);
        objectOutputStream.writeObject(object);
        disconnect();
    }
}
