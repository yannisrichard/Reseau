/**
 * 
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;


/**
 * @author yarichard1
 *
 */
public class ServerSSL {
	
	private SSLServerSocket sslserversocket;
	
	private int clientNumber = 0;
	
	/**
	 * Constructeur.
	 * @param port Le port sur lequel le serveur est lance
	 */
	public ServerSSL(int port)
	{
		try {
			SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(port);
			
			System.out.println("Serveur securise creee.");
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
                new Thread(new ThreadOperation((SSLSocket) sslserversocket.accept(), clientNumber)).start();
                System.out.println("Connexion du client numero " + clientNumber);
			}
			catch (IOException e){
				System.out.println("Probleme de connexion avec le client.");
			}
        }
	}
}
