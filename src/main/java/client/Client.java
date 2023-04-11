package client;

import server.models.*;

import java.io.FileNotFoundException;
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
     * À compléter.
     * @param cmd
     * @param arg
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
     * À compléter.
     * @throws ConnectException
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
     * À compléter.
     * @throws IOException
     */
    public static void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        cS.close();
    }

    /**
     * À compléter.
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

        } catch (FileNotFoundException e) {
            System.out.println("Fichier de données des cours non trouvé."); // inutile?
            throw new RuntimeException();
        } catch (IOException e) {
//            System.out.println("IOException: " + e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
//            System.out.println("ClassNotFoundException: " + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * À compléter.
     * @param object
     * @throws IOException
     */
    public static void sendObject2Server(String cmd, Object object) throws IOException {
        try{
            connect();
            askServer(cmd, "");
            objectOutputStream.writeObject(object);
            disconnect();
        } catch (IOException e){
//            System.out.println("Exception: " + e);
            throw new IOException();
        }

    }
}
