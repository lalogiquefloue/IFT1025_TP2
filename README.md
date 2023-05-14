/////////////////////////////////

Travail pratique #2 dans le cadre du cours IFT1025 à l'Université de Montréal. 
Implémentation d'une plateforme d'inscription à un cours avec architecture client / serveur.

/////////////////////////////////

UTILISATION

Programme développé avec Intellij et le JDK 19. Les tests ont été effectué avec Windows PowerShell.


SERVEUR (server.jar)

- Lancer dans la ligne de commande avec "java -jar server.jar"

- Les fichiers requis pour l'exécution des requêtes sont: 
	- "inscription.txt", contenant la liste des insriptions.
	- "cours.txt", contenant la liste des cours disponibles.
	Ceux-ci doivent se trouver dans un dossier "data" dans le même dossier où se trouve "Server.jar" lors de son exéution.


CLIENT LIGNE DE COMMANDE (client_simple.jar)

- Lancer dans la ligne de commande avec "java -jar client_simple.jar"


CLIENT INTERFACE GRAPHIQUE (client_fx.jar)

- Lancer dans la ligne de commande avec "java -jar --module-path "PATH_TO_JAVAFX_LIB" --add-modules=javafx.controls,javafx.fxml client_fx.jar" 

où PATH_TO_JAVAFX_LIB correspond à l'emplacement de la librairie JavaFX sur l'ordinateur.

Ex.: java -jar --module-path "C:\Program Files\Java\javafx-sdk-20\lib" --add-modules=javafx.controls,javafx.fxml client_fx.jar
