package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import constant.Constants;
import server.Server;

/**
 * @author Yannis
 * La classe Client.
 */
public class ClientOld {

	/** Le port de connexion du client. */
	private int port;
	private int portC;

	/**
	 * Constructeur du client.
	 * @param port Le port de connexion du client
	 * @param adresse L'adresse de connexion du client
	 */	
	public ClientOld(int port, int portC, String adresse) throws IOException {
		String ligne, result;
		PrintStream out = null;
		PrintStream outC = null;
		StringBuilder builder = new StringBuilder();
		StringBuilder builderC = new StringBuilder();
		this.port = port;
		this.portC = portC;
		try {

			Socket socket = new Socket(adresse, port);
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintStream(socket.getOutputStream());
			BufferedReader keyboardSoc = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while (true) {
				printMenu();
				builder.setLength(0);
				builderC.setLength(0);
				ligne = keyboard.readLine();

				if (ligne.equals("0")) {
					break;
				}
				if (ligne.equals("1") || ligne.equals("2")) {
					System.out.println("Entrer votre phrase : ");
					String choixtmp=ligne; 
					ligne = keyboard.readLine(); 

					out.println(choixtmp);
					out.println(ligne);
					out.flush();
					System.out.println(ligne + " = " + keyboardSoc.readLine());

				}
				if (ligne.equals("3")) {

					System.out.println("Entrer votre phrase : ");
					ligne = keyboard.readLine();
					caractere(ligne, builder);
					out.println();
					out.flush();
				}
				if (ligne.equals("4")) {
					System.out.println("Entrer votre phrase : ");
					ligne = keyboard.readLine();
					valeur(ligne, builder);
					out.println();
					out.flush();
				}
				if(ligne.equals("5") || ligne.equals("6")){// Serveur C
					Socket socketC = new Socket(adresse, portC);
					System.out.println("Entrer votre phrase : ");
					String choixtmp=ligne; 
					ligne = keyboard.readLine(); 
					String val;
					if(ligne.equals("5")){
						val = "3;"+ligne; // voyelle
					}else{
						val = "4;"+ligne; // consonne
					}
					outC = new PrintStream(socketC.getOutputStream());
					BufferedReader keyboardSocC = new BufferedReader(new InputStreamReader(socketC.getInputStream()));
					outC.println(val);
					outC.flush();
					result = keyboardSocC.readLine();
					builderC.append(ligne).append("=").append(result).append("\n");
					System.out.println(builderC.toString()); // bloquage a cette ligne <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

				}               

			}
			System.out.println("------------- Fin --------------");
			out.println("fin");
			keyboardSoc.close();
			out.close();
			keyboard.close();  
			socket.close();
		}
		catch (IOException e) {
			System.out.println("Erreur de connexion avec le serveur.");
			Logger.getLogger(ClientOld.class.getName()).log(Level.SEVERE, null, e);
		}

	}



	/**
	 * Methode qui calcule le nombre de caractere en appelant le serveur web.
	 * @param ligne La ligne ecrite par l'user
	 * @param builder Le builder qui renvoie le resultat
	 */
	private void caractere(String ligne, StringBuilder builder) 
	{
		String result = httpGet(ligne.replaceAll("\\s", ""));
		if (null != result) {
			builder.append(ligne).append("=").append(result).append("\n");
		}

		System.out.println(builder.toString());
	}

	/**
	 * Methode qui donne la valeur d'une phrase en appelant le serveur web.
	 * @param ligne La ligne ecrite par l'user
	 * @param builder Le builder qui renvoie le resultat
	 */
	private void valeur(String ligne, StringBuilder builder) 
	{
		String result = httpPost(ligne);
		if (null != result) {
			builder.append(ligne).append("=").append(result).append("\n");
		}

		System.out.println(builder.toString());
	}

	private String httpGet(String phrase)
	{
		String line = null;
		try {
			@SuppressWarnings("resource")
			Socket socweb = new Socket(Constants.ADDR_SERVER_PHP, 80);
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(socweb.getInputStream()));
			PrintStream out = new PrintStream(socweb.getOutputStream());
			StringBuilder builder = new StringBuilder();
			builder.append("GET ").append(Constants.FOLDER_WEB).append("index.php?phrase=").append(phrase)
			.append(" HTTP/1.1\r\nHost:").append(Constants.ADDR_SERVER_PHP).append("\r\n\r\n");
			out.println(builder.toString());

			// On retire le header
			do {
				line = keyboard.readLine().toString();
				if (line != null && line.length() == 0) {
					line = keyboard.readLine().toString();
					break;
				}
			} while (line != null);

		} 
		catch (UnknownHostException e) {
            Logger.getLogger(ClientOld.class.getName()).log(Level.SEVERE, null, e);
		} 
		catch (IOException e) {
            Logger.getLogger(ClientOld.class.getName()).log(Level.SEVERE, null, e);
		}
		return line;
	}

	private String httpPost(String phrase)
	{
		String line = null;
		try {
			@SuppressWarnings("resource")
			Socket socweb = new Socket(Constants.ADDR_SERVER_PHP, 80);
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(socweb.getInputStream()));
			PrintStream out = new PrintStream(socweb.getOutputStream());
			StringBuilder builder = new StringBuilder();
			builder.append("POST ").append(Constants.FOLDER_WEB).append("index.php HTTP/1.1\r\n")
			.append("Host :")
			.append(Constants.ADDR_SERVER_PHP)
			.append("\r\n")
			.append("Content-type : application/x-www-form-urlencoded\r\n")
			.append("Content-Length : "+33 + "\r\n\r\n")
			.append("phrase=")
			.append(phrase+"\r\n");
			out.println(builder.toString());

			// On retire le header
			do {
				line = keyboard.readLine().toString();
				if (line != null && line.length() == 0) {
					line = keyboard.readLine().toString();
					break;
				}
			} while (line != null);
		} 
		catch (UnknownHostException e) {
            Logger.getLogger(ClientOld.class.getName()).log(Level.SEVERE, null, e);
		} 
		catch (IOException e) {
            Logger.getLogger(ClientOld.class.getName()).log(Level.SEVERE, null, e);
		}
		return line;
	}

	private void printMenu() {
		System.out.println("*********** En attente d'une opération **************");
		System.out.println("1 : Serveur JAVA : Nombre de voyelles \n"
				+ "2 : Serveur JAVA : Nombre de consonnes \n"
				+ "3 : Serveur WEB : Nombre de caractères\n"
				+ "4 : Serveur WEB : Valeur de la phrase\n"
				+ "5 : Serveur C : Nombre de voyelles \n"
				+ "6 : Serveur C : Nombre de consonnes\n"
				+ "0 : Fin du programme\n");
	}

}
