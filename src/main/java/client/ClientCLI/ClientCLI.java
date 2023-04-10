package client.ClientCLI;

import client.Client;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientCLI extends Client {
    static boolean courseIsChosen = false;
    static String courseSession;
    static ArrayList<Course> courses = null;

    public static void main(String[] args) {

        while (!courseIsChosen) {
            try {
                courseSession = chooseSession();
                courses = loadCoursesBySession(courseSession);
                chooseCourse(courses);
            } catch (Exception e) {
                System.out.println("Échec de la requête, veuillez réessayer.");
            }
        }
        registerCourseLoop();
    }

    private static String chooseSession() {
//        String session = "";
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
                    return "Automne";
                case 2:
                    return "Hiver";
                case 3:
                    return "Ete";
                default:
                    System.out.println("Pas un choix valide, veuillez réessayer.");
            }
        }
    }

    private static void chooseCourse(ArrayList<Course> courses) {
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
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }

    private static void registerCourseLoop() {
        Scanner scanner = new Scanner(System.in);

        boolean courseExists = false;
        boolean inscriptionSucceeded = false;

        String courseCode = null;
        String firstName = null;

        System.out.println("Veuillez saisir votre prénom:     ");
        firstName = scanner.nextLine();

        System.out.println("Veuillez saisir votre nom:        ");
        String lastName = scanner.nextLine();

        boolean emailIsValid = false;
        String email = null;
        while (!emailIsValid) {
            System.out.println("Veuillez saisir votre email:      ");
            email = scanner.nextLine();
            emailIsValid = email.matches("^(.+)@(.+)$");
            if (!emailIsValid) {
                System.out.println("Email invalide, veuillez réessayer.");
            }
        }

        boolean idIsValid = false;
        String idNumber = null;
        while (!idIsValid) {
            System.out.println("Veuillez saisir votre matricule:  ");
            idNumber = scanner.nextLine();
            idIsValid = idNumber.matches("^[0-9]{8}$");
            if (!idIsValid) {
                System.out.println("Matricule invalide, veuillez réessayer.");
            }
        }

        System.out.println(courses); //DEBUG

        while (!courseExists || !inscriptionSucceeded) {

            System.out.println("Veuillez saisir le code du cours: ");
            String courseId = scanner.nextLine();

            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).getCode().trim().equals(courseId)) {
                    RegistrationForm rf = new RegistrationForm(firstName, lastName, email, idNumber, courses.get(i));
                    courseCode = courses.get(i).getCode();
                    courseExists = true;
                    try {
                        sendRegistrationForm2Server(rf);
                        inscriptionSucceeded = true;
                    } catch (Exception e) {
                        System.out.println("Échec de l'inscription, veuillez réessayer.");
                    }
                    break;
                } else {
                    courseExists = false; //REQUIS??
                }
            }
            if (!courseExists) {
                System.out.println("Numéro de cours invalide, veuillez réessayer.");
                System.out.println("*************************************************************");
            }
        }
        System.out.println("Félicitations! Insciption réussie de " + firstName + " au cours " + courseCode + ".");
    }

    private static void registerCourse() {

    }
}