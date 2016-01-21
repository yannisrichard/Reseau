/**
 * 
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;


/**
 * @author yarichard1
 *
 */
public class Server {

	/** La ServerSocket. */
	private ServerSocket s;
	
	private int clientNumber = 0;
	
	/**
	 * Constructeur.
	 * @param port Le port sur lequel le serveur est lance
	 */
	public Server(int port)
	{
		try {
			s = new ServerSocket(port);
			System.out.println("Socket serveur creee.");
			waitClient();
		}
		catch (IOException e) {
			System.out.println("Erreur lors de la creation de la socket serveur.");
		}
	}
	
	/**
	 * Methode qui attend les clients.
	 */
	public void waitClient()
	{
		while (true) {
            System.out.println("Attente de clients.");
			try {
				clientNumber ++;
                new Thread(new ThreadOperation(s.accept(), clientNumber)).start();
                System.out.println("Connexion du client numero " + clientNumber);
			}
			catch (IOException e){
				System.out.println("Probleme de connexion avec le client.");
			}
        }
	}
}
