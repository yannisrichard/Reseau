/**
 * 
 */
package ssl.serveurssl;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;


/**
 * @author yarichard1
 *
 */
public class ServerSSL {

	/** La ServerSocket. */
	private SSLServerSocket s;

	private int clientNumber = 0;

	/**
	 * Constructeur.
	 * @param port Le port sur lequel le serveur est lance
	 */
	public ServerSSL(int port)
	{
		try {
			SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			SSLServerSocket s = (SSLServerSocket) sslserversocketfactory.createServerSocket(port);

			System.out.println("Socket serveur creee.");
			waitClient();
		}
		catch (IOException e) {
			System.out.println("Erreur lors de la creation de la socket serveur.");
			Logger.getLogger(ServerSSL.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Methode qui attend les clients.
	 */
	public void waitClient()
	{
		while (true) {
			System.out.println("Attente de clients.");
			SSLSocket sslsocket;
			try {
				sslsocket = (SSLSocket) s.accept();
				clientNumber ++;
				new Thread(new ThreadOperationSSL(sslsocket, clientNumber)).start();
				System.out.println("Connexion du client numero " + clientNumber);
			}
			catch (IOException e){
				System.out.println("Probleme de connexion avec le client.");
				Logger.getLogger(ServerSSL.class.getName()).log(Level.SEVERE, null, e);
			}
		}
	}
}
