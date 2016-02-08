/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssl.clientssl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author yannis
 */
public class ClientSSL {


	/** Le port de connexion du client. */
	private int port;
	/**
	 * Constructeur du client.
	 * @param port Le port de connexion du client
	 * @param adresse L'adresse de connexion du client
	 */

	public ClientSSL(int port, String adresse) throws IOException {
		String ligne, result;
		StringBuilder builder;
		PrintStream out = null;
		this.port = port;

		try{

			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(adresse, port);

			InputStream inputstream = System.in;
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			BufferedReader keyboard = new BufferedReader(inputstreamreader);

			out = new PrintStream(sslsocket.getOutputStream());
			Reader socketReader = new InputStreamReader(sslsocket.getInputStream());
			BufferedReader keyboardSoc = new BufferedReader(socketReader); 

			System.out.println(keyboardSoc.readLine());
			printMenu();
			ligne = keyboard.readLine();
			while (!ligne.equals("Fin")) {
				builder = new StringBuilder();
				if (ligne.contains("Consonne :") || ligne.contains("Voyelle :")) {
					out.println(ligne);
					result = keyboardSoc.readLine();
					builder.append(ligne).append("=").append(result).append("\n");
					builder.append("Autre operation :");
					System.out.println(builder.toString());
					out.flush();
				}
				ligne = keyboard.readLine();
				printMenu();
			}
			System.out.println("------------- Fin --------------");
			out.println("fin");
			keyboardSoc.close();
			out.close();
			keyboard.close();  
			sslsocket.close();
		}
		catch (IOException e) {
			System.out.println("Erreur de connexion avec le serveur SSL.");
            Logger.getLogger(ClientSSL.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Methode qui affiche le Menu.
	 */
	public void printMenu() {
		System.out.println("********************** En attente d'une opération *************************");
		System.out.println("Commande -> Voyelle, Consonne, Fin");
		System.out.println("Exemple  -> Voyelle : votre texte");
		System.out.println("Exemple  -> Consonne : votre texte");
		System.out.println("***************************************************************************");
		System.out.println("\nCommande : ");
	}




}
