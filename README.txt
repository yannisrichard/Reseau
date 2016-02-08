----------------------------------------

Yannis RICHARD
Clement THUAIRE

----------------------------------------
------------ Configuration -------------
----------------------------------------

Pour utiliser ces applications, il faut d'abord modifier les constantes d�finies au d�but de chaque fichiers.
Vous pouvez configurer l'adresse du serveur Java, l'adresse du serveur PHP, et les ports.

----------------------------------------
------------- Serveur JAVA -------------
----------------------------------------

Dans SocketsJeuMots/src/server, il y a le serveur java. On le lance en lan�ant ServerMain.java.

----------------------------------------
-------------- Client JAVA -------------
----------------------------------------

Dans SocketsJeuMots/src/client, il y a le client java. On le lance avec ClientMain.java
On peut avoir plusieurs clients java en m�me temps et il marche avec le serveur web et le serveur java.


----------------------------------------
--------------- Serveur C ---------------
----------------------------------------

Dans ServerC/, il y a le serveur C.
Pour compiler, il suffit de se rendre dans le dossier du Client C avec un invit� de commande
Puis d'ex�cuter la commande suivante :
	"gcc ServeurC.c -o ServeurC"

Pour le lancer, il ne reste plus qu'� ex�cuter la commande suivante :
	"./ServeurC"

----------------------------------------
--------------- Serveur Web ---------------
----------------------------------------

Le serveur web est situ� dans le dossier ServerPHP
Nous avons mis le index.php dans c:/wamp/www sur Windows, comme cela on a acc�s � ce projet
� l'adresse http://localhost/ServerPhp/.
Lorsque nous avons test� sur les ordinateurs sous Linux � l'IUT, nous avons mis index.php dans
~NomUtilisateur/public_html/

----------------------------------------
------------- Serveur Java SSL ---------
----------------------------------------

Dans SocketsJeuMotsSSL/src/ssl/serverssl, il y a le serveur ssl java. On le lance en lan�ant ServerSSLMain.java

----------------------------------------
------------- Client Java SSL ----------
----------------------------------------

Dans SocketsJeuMotsSSL/src/ssl/clientssl, il y a le client ssl java. On le lance en lan�ant ServerSSLMain.java

----------------------------------------
----------------------------------------
----------------------------------------
--------- Bilan - Explication ----------
----------------------------------------
----------------------------------------
----------------------------------------

Le client java avec les serveurs java et web marchent bien.
Les classes ClientOld et ThreadOperationOld sont une nouvelle impl�mentation du Client et Serveur Java avec un
systeme de menu plus simple pour l'utilisateur mais il fonctionne seulement avec les appels aux serveurs web
nous n'avons pas eu le temps de corriger les erreurs pour le serveur Java.
Le client C fonctionne �galement.