package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import constant.Constants;

/**
 * @author Yannis
 * La classe Client.
 */
public class Client {

	/** Le port de connexion du client. */
	private int port;
	
	/**
	 * Constructeur du client.
	 * @param port Le port de connexion du client
	 * @param adresse L'adresse de connexion du client
	 */
	public Client(int port, String adresse)
	{
	    String ligne, result;
    	StringBuilder builder;
		PrintStream out = null;
		this.port = port;
        System.out.println("Lancement des operations :");
	    try {
    		Socket socket = new Socket(adresse, port);
		    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	    	
		    out = new PrintStream(socket.getOutputStream());
		    BufferedReader keyboardSoc = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    
		    System.out.println(keyboardSoc.readLine());
	        System.out.println("*********** En attente d'une opération **************");
		    System.out.println("Commande -> Voyelle, Consonne, Caractere, Valeur, Fin");
		    System.out.println("Exemple  -> Voyelle : votre texte");
		    System.out.println("\nCommande : ");
		    
			ligne = keyboard.readLine();
		    while (!ligne.equals("Fin")) {
		    	builder = new StringBuilder();
		    	if (ligne.contains("Consonne :") || ligne.contains("Voyelle :")) {
				    out.println(ligne);
				    result = keyboardSoc.readLine();
					builder.append(ligne).append("=").append(result).append("\n");
				    builder.append("Autre operation :");
				    System.out.println(builder.toString());
		    	}
		    	else {
		    		if (ligne.contains("Caractere :")) {
		    			caractere(ligne, builder);
		    		}
		    		else {
		    			if (ligne.contains("Valeur :")) {
		    				valeur(ligne, builder);
		    			}
			    	}
		    	}
			    ligne = keyboard.readLine();
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
		}
	}
	
	/**
	 * Methode qui calcule le nombre de caractere en appelant le serveur web.
	 * @param ligne La ligne ecrite par l'user
	 * @param builder Le builder qui renvoie le resultat
	 */
	private void caractere(String ligne, StringBuilder builder) 
	{
		String[] splitLine = ligne.split(":");
		if (2 == splitLine.length) {
			String result = httpGet(splitLine[1]);
			if (null != result) {
				builder.append(ligne).append("=").append(result).append("\n");
			    System.out.println(builder.toString());
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
		String[] splitLine = ligne.split(":");
		if (2 == splitLine.length) {
			String result = httpPost(splitLine[1]);
			if (null != result) {
				builder.append(ligne).append("=").append(result).append("\n");
			    System.out.println(builder.toString());
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
		try {
			@SuppressWarnings("resource")
			Socket socweb = new Socket(Constants.ADDR_SERVER_PHP, 80);
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(socweb.getInputStream()));
			PrintStream out = new PrintStream(socweb.getOutputStream());
			StringBuilder builder = new StringBuilder();
			// Dossier du projet web
			String folder = "/ServerPhp/";
			builder.append("GET ").append(folder).append("index.php?phrase=").append(phrase)
				   .append(" HTTP/1.1\r\nHost:").append(Constants.ADDR_SERVER_PHP).append("\r\n\r\n");
			out.println(builder.toString());
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String httpPost(String phrase)
	{
		try {
			@SuppressWarnings("resource")
			Socket socweb = new Socket(Constants.ADDR_SERVER_PHP, 80);
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(socweb.getInputStream()));
			PrintStream out = new PrintStream(socweb.getOutputStream());
			StringBuilder builder = new StringBuilder();
			// Dossier du projet web
			String folder = "/ServerPhp/";
			builder.append("POST ").append(folder).append("index.php HTTP/1.1\r\nHost:").append(Constants.ADDR_SERVER_PHP).append("\r\n")
					.append("Content-type: application/x-www-form-urlencoded\r\nContent-Length: 33\r\n\r\nfphrase=")
				   .append(phrase);
			out.println(builder.toString());
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
