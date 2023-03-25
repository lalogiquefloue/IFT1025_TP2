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

            String session = chooseSession();
            ArrayList<Course> courses = loadCourses(session);

            //DEBUG--------------------------------------------
            for (int i =0; i < courses.size(); i++){
                System.out.println(courses.get(i).toString());
            }
            //DEBUG--------------------------------------------

//            String session            = chooseSession(bw);
//            String course             = chooseCourse(bw);
//            registerCourse(bw, courseName, courseCode, courseSession);
//            System.out.println("test");
//            bufferedWriter.close();
            objectInputStream.close();
            objectOutputStream.close();
            cS.close();

        } catch (ConnectException x) {
            System.out.println("Connexion impossible sur port 1337: pas de serveur.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void askServer(String cmd, String arg) {
        System.out.println("askServer method");
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

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choix: ");
        int sessionChoice = scanner.nextInt();

        switch(sessionChoice){
            case 1:
                session = "Automne";
                break;
            case 2:
                session = "Hiver";
                break;
            case 3:
                session = "Ete";
                break;
        }
        return session;
    }

    public static String askWhichCourse() {
        String course = "";
        //TODO
        return course;
    }
}