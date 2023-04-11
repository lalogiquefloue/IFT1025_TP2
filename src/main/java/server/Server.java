package server;

import javafx.util.Pair;
import server.models.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     * Constructeur de la classe "Server" prenant en paramètre le numéro du port qui servira aux communications.
     * La classe initialise une liste de "handlers" avec leurs méthodes.
     * @param port Port avec lequel initialiser le serveur.
     * @throws IOException Exception si une erreur d'I/O survient lors de l'ouverture du socket.
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * Méthode ajoutant un évènement "EventHandler" à la liste d'"handlers" à être exécutés par le serveur.
     * @param h Évènement créé par l'intermédiaire de l'interface fonctionnelle <code>EventHandler</code>.
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     * Méthode
     * @param cmd
     * @param arg
     */
    private void alertHandlers(String cmd, String arg) throws IOException {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * Méthode constituant la base de la boucle d'évènements du serveur.
     * La méthode <code>run()</code> permet d'établir la connection avec les nouveaux clients en créant les
     * <code>ObjectInputStream</code> et <code>ObjectOutputStream</code> requis et attends les commandes à exécuter.
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Méthode recevant et traitant les commandes envoyées par le client.
     * @throws IOException Eception lorsque le format de l'objet transmis est invalide.
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
     * @throws IOException Exception s'il y a une entrée ou sortie invalide.
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * Méthode redirigeant les commandes reçues par le serveur vers les méthodes définies.
     * @param cmd : Commande de la fonction à exécuter.
     * @param arg : Argument pour les fonctions définies, lorsque requis.
     */
    public void handleEvents(String cmd, String arg) throws RuntimeException {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     * Lire un fichier texte contenant des informations sur les cours et les transformer en liste d'objets 'Course'.
     * La méthode filtre les cours par la session spécifiée en argument.
     * Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     * La méthode gère les exceptions si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.
     *---------------------
     * Méthode chargeant depuis un fichier
     * @param arg Session pour laquelle on veut récupérer la liste des cours ("Automne", "Hiver" ou "Ete").
     */
    public void handleLoadCourses(String arg) {

        FileReader fr = null;
        BufferedReader reader = null;
        try {
//            FileReader fr = new FileReader("./data/cours.txt");
            fr = new FileReader("src/main/java/server/data/cours.txt");
            reader = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            System.out.println("Fichier 'cours.txt' non trouvé.");
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
//            throw new RuntimeException(e);
        }
        catch (NullPointerException e){
//            throw new RuntimeException(e);
        }

    }

    /**
     * Méthode sauvegardant dans un fichier texte une inscription envoyée par le client via le modèle "RegistrationForm".
     * @throws Exception si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        try {
            RegistrationForm rf = (RegistrationForm) objectInputStream.readObject();
            String session = rf.getCourse().getSession();
            String code = rf.getCourse().getCode();
            String idNumber = rf.getMatricule();
            String firstName = rf.getPrenom();
            String lastName = rf.getNom();
            String email = rf.getEmail();

            String line =
                    session + " " +
                            code + " " +
                            idNumber + " " +
                            firstName + " " +
                            lastName + " " +
                            email;

            System.out.println(line);

            try {
//                FileWriter fw = new FileWriter("./data/inscription.txt", true);
                FileWriter fw = new FileWriter("src/main/java/server/data/inscription.txt", true);
                BufferedWriter writer = new BufferedWriter(fw);
                writer.newLine();
                writer.write(line);
                writer.close();
            } catch (IOException ex) {
                System.out.println("Erreur à l'écriture du fichier 'inscription.txt'.");
                throw new IOException();
            }

        } catch (IOException e) {
//            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
        }
    }
}

