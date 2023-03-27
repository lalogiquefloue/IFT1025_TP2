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
     * La classe crée une liste
     *
     * @param port Port sur lequel créer le serveur.
     * @throws IOException Exception si le format du port est invalide.
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * Ajoute un évènement à la liste d'évènements à être exécutés.
     *
     * @param h
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     * ?
     *
     * @param cmd
     * @param arg
     */
    private void alertHandlers(String cmd, String arg) throws IOException {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * Boucle d'évènements. La méthode <code>run()</code> permet d'établir la connection avec les nouveaux clients en créant les
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
     * À COMPLÉTER
     *
     * @throws IOException
     * @throws ClassNotFoundException
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
     * À COMPLÉTER
     *
     * @param line
     * @return
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * Déconnecte le serveur et ses streams lorsque la demande du client a été traitée par la méthode "run()".
     *
     * @throws IOException Exception s'il y a une entrée invalide.
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * Redirection des évènements vers les méthodes <code>handleRegistration()</code> ou <code>handleLoadCourses(arg)</code>.
     *
     * @param cmd : la commande à exécuter, <code>"INSCRIRE"</code> ou <code>"CHARGER"</code>.
     * @param arg : argument pour la fonction <code>handleLoadCourses(arg)</code>. Correspond à la session demandée parmis: "Automne", "Hiver" ou "Été".
     */
    public void handleEvents(String cmd, String arg) {
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
     *
     * @param arg la session pour laquelle on veut récupérer la liste des cours
     */
    public void handleLoadCourses(String arg) {

        System.out.println("inside handleLoadCourses()");

        ArrayList<Course> courses = new ArrayList<Course>();

        try {
            FileReader fr = new FileReader("src/main/java/server/data/cours.txt");
            BufferedReader reader = new BufferedReader(fr);

            String s;

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

            try {
                objectOutputStream.writeObject(courses);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     * et renvoyer un message de confirmation au client.
     *
     * @throws Exception si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        try {
            RegistrationForm rf = (RegistrationForm) objectInputStream.readObject();
            System.out.println(rf);//DEBUG
            String session = rf.getCourse().getSession();
            String code = rf.getCourse().getCode();
            String idNumber = rf.getMatricule();
            String firstName = rf.getPrenom();
            String lastName = rf.getNom();
            String email = rf.getEmail();

            String line =
                    session   + " " +
                    code      + " " +
                    idNumber  + " " +
                    firstName + " " +
                    lastName  + " " +
                    email;

            System.out.println(line);

            try {
                FileWriter fw = new FileWriter("src/main/java/server/data/inscription.txt", true);
                BufferedWriter writer = new BufferedWriter(fw);
                writer.newLine();
                writer.write(line);
                writer.close();
            } catch (IOException ex) {
                System.out.println("Erreur à l'écriture du fichier");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

