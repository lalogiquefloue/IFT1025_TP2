package client;

//import client.models.*; // à confirmer que c'est possible?

import server.models.*;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientSimple {

    static String courseName;
    static String courseCode;
    static boolean courseIsChosen = false;
    static String courseSession;
    static ArrayList<Course> courses;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;
    private static Socket cS;
    private final static int PORT = 1337;


    public static void main(String[] args) throws IOException {

        while (!courseIsChosen) {
            courseSession = chooseSession();
            courses = loadCourses(courseSession);
            chooseCourse(courses);
        }
        registerCourse();
    }

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

    public static ArrayList<Course> loadCourses(String arg) throws IOException {

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
    public static String chooseSession() {
        String session = "";
        System.out.println("*** Bienvenue au portail d'inscription de cours de L'UdeM ***");
        System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:");
        System.out.println("1. Automne");
        System.out.println("2. Hiver");
        System.out.println("3. Été");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choix: ");
            int sessionChoice = scanner.nextInt();

            switch (sessionChoice) {
                case 1:
                    return session = "Automne";
                case 2:
                    return session = "Hiver";
                case 3:
                    return session = "Ete";
                default:
                    System.out.println("Pas un choix valide, recommencer.");
            }
        }
    }

    public static void chooseCourse(ArrayList<Course> courses) {
        Course course = null;
        System.out.println("Les cours offerts pendant la session d'" + courseSession + " sont:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println(i + 1 + ". " + courses.get(i).getCode() + " " + courses.get(i).getName());
        }
        System.out.println("Options:");
        System.out.println("1. Consulter les cours offerts pour une autre session.");
        System.out.println("2. Inscription à un cours.");

        loop:
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choix: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    break loop;
                case 2:
                    courseIsChosen = true;
                    break loop;
                default:
                    System.out.println("Choix invalide, recommencer.");
            }
        }
    }
    public static void registerCourse() throws IOException {
        // TODO
        System.out.println("inside registerCourse()");
        connect();
        askServer("INSCRIRE", "");
        disconnect();
    }
}