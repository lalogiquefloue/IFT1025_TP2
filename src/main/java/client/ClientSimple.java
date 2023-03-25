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
    static String courseSession;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;

    public static void main(String[] args) {
        System.out.println("main"); //DEBUG

        try {
            System.out.println("socket"); //DEBUG
            Socket cS = new Socket("127.0.0.1", 1337); // localHost 127.0.0.1

            objectOutputStream = new ObjectOutputStream(cS.getOutputStream());
            objectInputStream = new ObjectInputStream(cS.getInputStream());

            mainLoop();

            objectInputStream.close();
            objectOutputStream.close();
            cS.close();

        } catch (ConnectException x) {
            System.out.println("Connexion impossible sur port 1337: pas de serveur.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mainLoop() {
        courseSession = chooseSession();
        ArrayList<Course> courses = loadCourses(courseSession);

        //DEBUG--------------------------------------------
        for (int i = 0; i < courses.size(); i++) {
            System.out.println(courses.get(i).toString());
        }
        //DEBUG--------------------------------------------

        chooseCourse(courses);
//            registerCourse();
    }

    public static void askServer(String cmd, String arg) {
        String line = cmd + " " + arg + "\n";
        try {
            objectOutputStream.writeObject(line);
            objectOutputStream.flush();
            System.out.println("askServer(" + cmd + ", " + arg + ") was called");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Course> loadCourses(String arg) {
        askServer("CHARGER", arg);
        System.out.println("après charger");
        try {
            System.out.println("in try");
            Object courses = objectInputStream.readObject();
            return (ArrayList<Course>) courses;
        } catch (IOException e) {
            System.out.println("IOException");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
            throw new RuntimeException(e);
        }
    }

    public static void registerCourse(String name, String code, String session) {
        // TODO
        askServer("CHARGER", "");
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
            System.out.println(i+1 + ". " + courses.get(i).getCode() + " " + courses.get(i).getName());
        }
        System.out.println("Options:");
        System.out.println("1. Consulter les cours offerts pour une autre session.");
        System.out.println("2. Inscription à un cours.");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choix: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    mainLoop(); // BUG A REGLER
                case 2:
                    break;
                default:
                    System.out.println("Choix invalide, recommencer.");
            }
        }
    }
}