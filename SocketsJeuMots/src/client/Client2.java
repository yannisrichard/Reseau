/**
 * 
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import constant.Constants;

/**
 * @author yarichard1
 *
 */
public class Client2 {

	private int port;
	
	public Client2(int port, String adresse){
		this.port = port;
		String ligne, result;
		PrintStream out = null;
		
		try {
			Socket sock = new Socket(adresse, this.port);
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
			
			out = new PrintStream(sock.getOutputStream());
		    BufferedReader clavierSoc = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		    
		    System.out.println(clavierSoc.readLine());
	        System.out.println("*********** En attente d'une opération **************");
		    System.out.println("Commande -> Voyelle, Consonne, Caractere, Valeur, Fin");
		    System.out.println("Exemple  -> Voyelle : votre texte");
		    System.out.println("\nCommande : ");
	        
			ligne = clavier.readLine();
		    while (!ligne.equals("Fin")) {
	        	out.println(ligne);
		    	result = clavierSoc.readLine();
		    	System.out.println(result);
		    	System.out.println("Autre operation : ");
		        ligne = clavier.readLine();	
			}
	        System.out.println("------------- Fin --------------");
		    out.println("fin");
		    clavierSoc.close();
		    out.close();
		    clavier.close();
	    	sock.close();
			
		}
	    catch (IOException e){
			System.out.println("Erreur de connexion avec le serveur.");
		}
	}
}
