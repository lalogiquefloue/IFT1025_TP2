package client;

import client.models.Course;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientSimple {

    static String courseName;
    static String courseCode;
    static String courseSession;

    public static void main(String[] args) {

        try {
            Socket cS = new Socket("127.0.0.1", 1337); // localHost 127.0.0.1

            OutputStreamWriter os = new OutputStreamWriter(cS.getOutputStream());
            BufferedWriter bw = new BufferedWriter(os);

//            InputStreamReader is = new InputStreamReader(cS.getInputStream());
//            BufferedReader br = new BufferedReader(is);

            ObjectInputStream ois = new ObjectInputStream(cS.getInputStream());

            // TODO
            ArrayList<Course> courses = loadCourses(bw, ois);
            String session            = askWhichSession(bw);
            String course             = askWhichCourse(bw);
            registerCourse(bw, courseName, courseCode, courseSession);

            bw.close();
            cS.close();

        } catch (ConnectException x) {
            System.out.println("Connexion impossible sur port 1337: pas de serveur.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void askServer(BufferedWriter bw, String cmd, String arg) {
        try {
            bw.append(cmd + " " + arg + "\n");
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Course> loadCourses(BufferedWriter bw, ObjectInputStream ois) {
        askServer(bw, "CHARGER", "");
        ObjectInputStream coursesOis = ois;
        try {
            ArrayList<Course> courses = (ArrayList<Course>) coursesOis.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // TODO server input reader
        return null;
    }

    public static void registerCourse(BufferedWriter bw, String name, String code, String session) {
        // TODO
        askServer(bw, "CHARGER", "");
    }

    public static String askWhichSession(BufferedWriter bw) {
        String session = "";
        //TODO
        return session;
    }

    public static String askWhichCourse(BufferedWriter bw) {
        String course = "";
        //TODO
        return course;
    }
}