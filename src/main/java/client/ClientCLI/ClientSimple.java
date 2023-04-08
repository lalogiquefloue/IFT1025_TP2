package client.ClientCLI;

import client.Client;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientSimple extends Client {
    static boolean courseIsChosen = false;
    static String courseSession;
    static ArrayList<Course> courses;

    public static void main(String[] args) throws IOException {

        while (!courseIsChosen) {
            courseSession = chooseSession();
            courses = loadCoursesBySession(courseSession);
            chooseCourse(courses);
        }
        registerCourse();
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
        connect();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Veuillez saisir votre prénom:     ");
        String firstName = scanner.nextLine();

        System.out.println("Veuillez saisir votre nom:        ");
        String lastName = scanner.nextLine();

        System.out.println("Veuillez saisir votre email:      ");
        String email = scanner.nextLine();

        System.out.println("Veuillez saisir votre matricule:  ");
        String idNumber = scanner.nextLine();

        System.out.println(courses);

        boolean courseExists = false;

        while (!courseExists) {

            System.out.println("Veuillez saisir le code du cours: ");
            String courseId = scanner.nextLine();

            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).getCode().trim().equals(courseId)) {
                    courseExists = true;
                    RegistrationForm rf = new RegistrationForm(firstName, lastName, email, idNumber, courses.get(i));
                    askServer("INSCRIRE", "");
                    sendObjectToServer(rf);
                    break;
                }
            }

            if (!courseExists) {
                System.out.println("Numéro de cours invalide, recommencer.");
            }
        }
        disconnect();
        System.out.println("Félicitations!");
    }
}