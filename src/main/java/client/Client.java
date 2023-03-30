package client;

import server.models.Course;

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

    public static void askServer(String cmd, String arg) {
        try {
            objectOutputStream.writeObject(cmd + " " + arg);
            objectOutputStream.flush();
            System.out.println("askServer(" + cmd + ", " + arg + ") was called");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void connect() {
        try {
            cS = new Socket("127.0.0.1", PORT);
            objectOutputStream = new ObjectOutputStream(cS.getOutputStream());
            objectInputStream = new ObjectInputStream(cS.getInputStream());
        } catch (ConnectException x) {
            System.out.println("Connexion impossible sur port 1337: pas de serveur.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        cS.close();
    }
    public static ArrayList<Course> loadCoursesBySession(String arg) throws IOException {

        connect();
        askServer("CHARGER", arg);

        try {
            Object courses = objectInputStream.readObject();
            disconnect();
            return (ArrayList<Course>) courses;

        } catch (IOException e) {
            System.out.println("IOException: " + e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e);
            throw new RuntimeException(e);
        }
    }

}
