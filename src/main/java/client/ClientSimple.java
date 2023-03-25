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

    public static void main(String[] args) throws IOException {
        System.out.println("main"); //DEBUG

//        try {
//            Socket cS = new Socket("127.0.0.1", 1337); // localHost 127.0.0.1
//            objectOutputStream = new ObjectOutputStream(cS.getOutputStream());
//            objectInputStream = new ObjectInputStream(cS.getInputStream());


        while (!courseIsChosen) {
            courseSession = chooseSession();
            courses = loadCourses(courseSession);

            //DEBUG--------------------------------------------
            for (int i = 0; i < courses.size(); i++) {
                System.out.println(courses.get(i).toString());
            }
            //DEBUG--------------------------------------------

            chooseCourse(courses);
        }
        System.out.println("after main while loop");
        registerCourse();

//            objectInputStream.close();
//            objectOutputStream.close();
//            cS.close();

//        } catch (ConnectException x) {
//            System.out.println("Connexion impossible sur port 1337: pas de serveur.");
//        } catch (IOException e) {
//            e.printStackTrace();

//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

//    public static void mainLoop() {
//        courseSession = chooseSession();
//        courses = loadCourses(courseSession);
//
//        //DEBUG--------------------------------------------
//        for (int i = 0; i < courses.size(); i++) {
//            System.out.println(courses.get(i).toString());
//        }
//        //DEBUG--------------------------------------------
//
//        chooseCourse(courses);
////            registerCourse();
//    }

//    public static void askServer(String cmd, String arg) {
//
//        try {
//            Socket cS = new Socket("127.0.0.1", 1337); // localHost 127.0.0.1
//            objectOutputStream = new ObjectOutputStream(cS.getOutputStream());
//            objectInputStream = new ObjectInputStream(cS.getInputStream());
//
//            String line = cmd + " " + arg + "\n";
//
//            objectOutputStream.writeObject(line);
//            objectOutputStream.flush();
//
//            System.out.println("askServer(" + cmd + ", " + arg + ") was called"); //DEBUG
//
//            objectOutputStream.close();
//            cS.close();
//
//        } catch (ConnectException x) {
//            System.out.println("Connexion impossible sur port 1337: pas de serveur.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void connect() throws IOException {
        Socket cS = new Socket("127.0.0.1", 1337); // localHost 127.0.0.1
        objectOutputStream = new ObjectOutputStream(cS.getOutputStream());
        objectInputStream = new ObjectInputStream(cS.getInputStream());
    }

    public static void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
//        cS.close(); // important?
    }
    public static ArrayList<Course> loadCourses(String arg) throws IOException {

        connect();

        objectOutputStream.writeObject("CHARGER " + arg);
        objectOutputStream.flush();

        try {
            System.out.println("in try");
            Object courses = objectInputStream.readObject();
            System.out.println("after objectinputstream");

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

    public static void registerCourse() {
        // TODO
        System.out.println("inside registerCourse()");
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
}