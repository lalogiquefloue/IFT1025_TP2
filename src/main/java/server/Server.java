/*
IFT1025 - TP2
Auteur: Carl Thibault
Date: 17 avril 2023
 */

package server;

import server.models.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.util.Pair;

/**
 * Classe du serveur assurant la gestion des connexions et des communications avec les clients via un socket.
 * Les commandes traitées sont: <br>
 * - <code>INSCRIRE</code> permettant l'inscription à un cours par le biais de la classe "RegistartionForm". <br>
 * - <code>CHARGER</code> permettant de charger les cours d'une session donnée en argument ("Automne", "Hiver" ou "Ete").
 */
public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;
    Object runThreadLock = new Object();

    /**
     * Constructeur de la classe "Server" prenant en paramètre le numéro du port qui servira aux communications.
     * La classe initialise une liste de "handlers" avec leurs méthodes.
     *
     * @param port Port avec lequel initialiser le serveur.
     * @throws IOException Exception si une erreur d'I/O survient lors de l'ouverture du socket.
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 100);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * Méthode ajoutant un évènement à la liste d'évènements devant être exécutés par le serveur.
     *
     * @param h Évènement créé par l'intermédiaire de l'interface fonctionnelle <code>EventHandler</code>.
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     * Méthode exécutant les évènements contenus dans la liste d'évènements avec la commande et l'argument spécifié.
     *
     * @param cmd : Commande de la fonction à exécuter.
     * @param arg : Argument pour les fonctions définies (optionnel).
     */
    private void alertHandlers(String cmd, String arg) throws IOException {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * Méthode constituant la boucle d'évènements du serveur. La méthode accepte les connections avec les clients,
     * attend leurs commandes pour les traiter et déconnecte ensuite le client.
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Connecté au client: " + client);
            Runnable runThread = this::runThread;
            Thread thread = new Thread(runThread);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Interruption");
            }
        }
    }

    /**
     * Méthode lancée lors de la création de threads pour permettre la gestion de multiples clients simultanément.
     */
    public void runThread() {
        synchronized (runThreadLock) {
            try {
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Client déconnecté!");
        }
    }

    /**
     * Méthode recevant et traitant les commandes envoyées par le client.
     *
     * @throws IOException            Eception lorsque le format de l'objet transmis est invalide.
     * @throws ClassNotFoundException Exception lorsque la classe reçue est invalide.
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     * Méthode faisant le traitement des commandes textuelles transmises au serveur en l'assignant aux valeurs
     * cmd et args correspondantes.
     *
     * @param line Commande textuelle transmise au serveur.
     * @return Paire de String correspondant à la commande et l'argument à exécuter.
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * Méthode exécutant la fermeture des streams et du socket avec le client.
     *
     * @throws IOException Exception s'il y a une I/O problématique et que les actions prévues sont impossibles à exécuter.
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * Méthode redirigeant les commandes reçues par le serveur vers les méthodes définies.
     *
     * @param cmd : Commande de la fonction à exécuter.
     * @param arg : Argument pour les fonctions définies (optionnel).
     */
    public void handleEvents(String cmd, String arg) throws RuntimeException {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     * Méthode chargeant, depuis les données des cours disponibles, tous les cours pour une session données avec la commande "CHARGER nom_de_la_session".
     * La méthode renvoie au client une liste des objets représentant ces cours.
     *
     * @param arg Session pour laquelle on veut récupérer la liste des cours ("Automne", "Hiver" ou "Ete").
     */
    public void handleLoadCourses(String arg) {

        FileReader fr;
        BufferedReader reader;
        try {
            fr = new FileReader("./data/cours.txt"); // jar filepath
//            fr = new FileReader("src/main/java/server/data/cours.txt"); // IDE filepath
            reader = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            System.out.println("Fichier 'cours.txt' non disponible.");
            return;
        }

        try {
            String s;
            ArrayList<Course> courses = new ArrayList<Course>();
            while ((s = reader.readLine()) != null) {
                String[] splitString = s.trim().split("\\s+");
                if (splitString[2].equals(arg.trim())) {
                    String code = splitString[0];
                    String name = splitString[1];
                    String session = splitString[2];
                    courses.add(new Course(name, code, session));
                }
            }
            reader.close();
            objectOutputStream.writeObject(courses);
        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException");
        }

    }

    /**
     * Méthode sauvegardant dans un fichier texte une inscription envoyée par le client via le modèle "RegistrationForm" avec la commande "ENREGISTRER".
     *
     * @throws Exception Exception si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {

        RegistrationForm rf = null;

        try {
            rf = (RegistrationForm) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Erreur liée à la réception du objectInputStream.");
        }

        // Gather information from the transmitted registration form
        String session = rf.getCourse().getSession();
        String code = rf.getCourse().getCode();
        String idNumber = rf.getMatricule();
        String firstName = rf.getPrenom();
        String lastName = rf.getNom();
        String email = rf.getEmail();

        // Line to be added to inscription file
        String line = session + "\t" + code + "\t" + idNumber + "\t" + firstName + "\t" + lastName + "\t" + email;

        try {
            FileWriter fw = new FileWriter("./data/inscription.txt", true); // jar filepath
//            FileWriter fw = new FileWriter("src/main/java/server/data/inscription.txt", true); // IDE filepath
            BufferedWriter writer = new BufferedWriter(fw);
            writer.newLine();
            writer.write(line);
            writer.close();
        } catch (IOException ex) {
            System.out.println("Erreur à l'écriture du fichier 'inscription.txt'.");
        }
    }
}


