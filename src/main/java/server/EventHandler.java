/*
IFT1025 - TP2
Auteur: Carl Thibault
Date: 17 avril 2023
 */

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
