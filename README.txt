----------------------------------------

Yannis RICHARD
Clement THUAIRE

----------------------------------------
------------ Configuration -------------
----------------------------------------

Pour utiliser ces applications, il faut d'abord modifier les constantes définies au début de chaque fichiers.
Vous pouvez configurer l'adresse du serveur Java, l'adresse du serveur PHP, et les ports.

----------------------------------------
------------- Serveur JAVA -------------
----------------------------------------

Dans SocketsJeuMots/src/server, il y a le serveur java. On le lance en lançant ServerMain.java

----------------------------------------
--------------- Serveur C ---------------
----------------------------------------

Dans ServerC/, il y a le serveur C.
Pour compiler, il suffit de se rendre dans le dossier du Client C avec un invité de commande
Puis d'exécuter la commande suivante :
	"gcc ServeurC.c -o client"

Pour le lancer, il ne reste plus qu'à exécuter la commande suivante :
	"./ServeurC"

----------------------------------------
--------------- Serveur Web ---------------
----------------------------------------

Le serveur web est situé dans le dossier ServerPHP
Nous avons mis le index.php dans c:/wamp/www sur Windows, comme cela on a accès à ce projet
à l'adresse http://localhost/projetCalculatrice.
Lorsque nous avons testé sur les ordinateurs sous Linux à l'IUT, nous avons mis index.php dans
~NomUtilisateur/public_html/

----------------------------------------
------------- Serveur Java SSL ---------
----------------------------------------

Dans SocketsJeuMotsSSL/src/server, il y a le serveur ssl java. On le lance en lançant ServerMain.java


----------------------------------------
-------------- Client JAVA -------------
----------------------------------------

Dans SocketsJeuMots/src/client, il y a le client java. On le lance avec ClientMain.java
On peut avoir plusieurs clients java en même temps et il marche avec le serveur web et le serveur java.

----------------------------------------
------------- Client Java SSL ----------
----------------------------------------

Dans SocketsJeuMotsSSL/src/client, il y a le client ssl java. On le lance en lançant ServerMain.java

----------------------------------------
----------------------------------------
----------------------------------------
------------- Bilan  --------------------
----------------------------------------
----------------------------------------
----------------------------------------

Le client java avec les serveurs java et web marchent bien.
Le client C fonctionne également.