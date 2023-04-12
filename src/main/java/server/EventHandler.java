package server;

import java.io.IOException;

/**
 * Interface fonctionnelle servant à la définition des évènements pris en charge par le serveur. L'interface accepte les
 * méthodes ayant comme paramètres le nom de la commande <code>cmd</code> et ses arguments <code>args</code>.
 */
@FunctionalInterface
public interface EventHandler {
    void handle(String cmd, String arg) throws IOException;
}
