package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import constant.Constants;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Yannis
 * La classe Client.
 */
public class Client {

	/** Le port de connexion du client. */
	private int port;
	private int portC;
	/**
	 * Constructeur du client.
	 * @param port Le port de connexion du client
	 * @param port Le port de connexion du clientC
	 * @param adresse L'adresse de connexion du client
	 */
	public Client(int port, int portC, String adresse)
	{
		String ligne, result;
		StringBuilder builder;
		PrintStream out = null;
		StringBuilder builderC;
		PrintStream outC = null;
		this.port = port;
		this.portC = portC;
		try {
			Socket socket = new Socket(adresse, port);
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

			out = new PrintStream(socket.getOutputStream());
			BufferedReader keyboardSoc = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			System.out.println(keyboardSoc.readLine());
			printMenu();
			ligne = keyboard.readLine();
			while (!ligne.equals("Fin")) {
				builder = new StringBuilder();
				builderC = new StringBuilder();
				if (ligne.contains("Consonne :") || ligne.contains("Voyelle :")) {
					out.println(ligne);
					result = keyboardSoc.readLine();
					builder.append(ligne).append("=").append(result).append("\n");
					builder.append("Autre operation :");
					System.out.println(builder.toString());
				}
				else if(ligne.contains("Caractere :") || ligne.contains("Valeur :")) {
					if (ligne.contains("Caractere :")) {
						caractere(ligne, builder);
					}   
					else {
						if (ligne.contains("Valeur :")) {
							valeur(ligne, builder);
						}
					}
				}else if(ligne.contains("VoyelleC :") || ligne.contains("ConsonneC :")){// Serveur C
					Socket socketC = new Socket(adresse, portC);
					String split[] = ligne.split(" : ");
					String val;
					if(ligne.contains("VoyelleC :")){
						val = "3;"+split[1]; // voyelle
					}else{
						val = "4;"+split[1]; // consonne
					}
					outC = new PrintStream(socketC.getOutputStream());
					BufferedReader keyboardSocC = new BufferedReader(new InputStreamReader(socketC.getInputStream()));
					outC.println(val);
					outC.flush();
					result = keyboardSocC.readLine();
					builderC.append(ligne).append("=").append(result).append("\n");
					builderC.append("Autre operation :");
					System.out.println(builderC.toString()); 

				}
				ligne = keyboard.readLine();
				printMenu();
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
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Methode qui affiche le Menu.
	 */
	public void printMenu() {
		System.out.println("********************** En attente d'une opération *************************");
		System.out.println("Commande -> Voyelle, Consonne, Caractere, Valeur, VoyelleC, ConsonneC, Fin");
		System.out.println("Exemple  -> Consonne : votre texte");
		System.out.println("Exemple  -> Caractere : votre texte");
		System.out.println("Exemple  -> Valeur : votre texte");
		System.out.println("Exemple  -> VoyelleC : votre texte");
		System.out.println("Exemple  -> ConsonneC : votre texte");
		System.out.println("***************************************************************************");
		System.out.println("\nCommande : ");
	}

	/**
	 * Methode qui calcule le nombre de caractere en appelant le serveur web.
	 * @param ligne La ligne ecrite par l'user
	 * @param builder Le builder qui renvoie le resultat
	 */
	private void caractere(String ligne, StringBuilder builder) 
	{
		String[] splitLine = ligne.split(" : ");
		if (2 == splitLine.length) {
			String result = httpGet(splitLine[1].replaceAll("\\s", ""));
			if (null != result) {
				builder.append(splitLine[1]).append("=").append(result).append("\n");
			}
		}
		else {
			System.out.println("Operation inconnue.");
		}
		builder.append("Autre operation :");
		System.out.println(builder.toString());
	}

	/**
	 * Methode qui donne la valeur d'une phrase en appelant le serveur web.
	 * @param ligne La ligne ecrite par l'user
	 * @param builder Le builder qui renvoie le resultat
	 */
	private void valeur(String ligne, StringBuilder builder) 
	{
		String[] splitLine = ligne.split(" : ");
		if (2 == splitLine.length) {
			String result = httpPost(splitLine[1]);
			if (null != result) {
				builder.append(splitLine[1]).append("=").append(result).append("\n");
			}
		}
		else {
			System.out.println("Operation inconnue.");
		}
		builder.append("Autre operation :");
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
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
		} 
		catch (IOException e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
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
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
		} 
		catch (IOException e) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
		}
		return line;
	}

}
