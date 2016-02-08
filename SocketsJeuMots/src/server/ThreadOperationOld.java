/**
 * 
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import ssl.clientssl.ClientSSL;

/**
 * @author yarichard1
 *
 */
public class ThreadOperationOld implements Runnable {
	private Socket soc;
	private BufferedReader client;
	private PrintStream out;
	private int clientNumber;


	/**
	 * @param clientNumber 
	 * @param socket 
	 * 
	 */
	public ThreadOperationOld(Socket socket, int clientNumber) {
		this.clientNumber = clientNumber;
		try {
			this.soc = socket;
			out = new PrintStream(soc.getOutputStream());
			client = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		} 
		catch (IOException e) {
			Logger.getLogger(ThreadOperationOld.class.getName()).log(Level.SEVERE, null, e);
		}
	}


	@Override
	public void run() {
		String ligne = null;
		int resultat = 0;
		out.println("Client " + clientNumber);
		try {
			if(client!=null){
				while((ligne = client.readLine()) != null) { 
					if(!(ligne.equals("0"))){
						switch (ligne) {
						case "1":
							ligne = client.readLine();
							resultat = voyelle(ligne);
							System.out.println("Client " + clientNumber + " : voyelle " + resultat);
							out.println(resultat);
							out.flush();
							break;
						case "2":
							ligne = client.readLine();
							resultat = consonne(ligne);
							System.out.println("Client " + clientNumber + " : consonne " + resultat);
							out.println(resultat);
							out.flush();
							break;
						default:
							System.out.println("Mauvaise Commande");
							break;
						}
					}
					resultat = 0;
					ligne = null;

				}
			}
		}
		catch (IOException e) {
			Logger.getLogger(ThreadOperationOld.class.getName()).log(Level.SEVERE, null, e);
		}
		finally {
			try {
				out.close();
				client.close();
				soc.close();
			} 
			catch (IOException e) {
				Logger.getLogger(ThreadOperationOld.class.getName()).log(Level.SEVERE, null, e);
			}
			finally {
				System.out.println("Le client " + clientNumber + " s'est deconnecte.");
			}
		}
	}


	public int consonne(String phrase){
		String trim = phrase.replaceAll(" ","");
		return trim.length() - voyelle(phrase);
	}

	public int voyelle(String phrase){
		int nb = 0;

		for (int i = 0; i < phrase.length(); i++) {
			if (phrase.charAt(i) == 'a' || phrase.charAt(i)== 'A' 
					|| phrase.charAt(i)== 'e' || phrase.charAt(i)== 'E' 
					|| phrase.charAt(i)== 'i' || phrase.charAt(i)== 'I' 
					|| phrase.charAt(i)== 'o' || phrase.charAt(i)== 'O' 
					|| phrase.charAt(i)== 'u' || phrase.charAt(i)== 'U' 
					|| phrase.charAt(i)== 'y' || phrase.charAt(i)== 'Y' ) {
				nb++;
			}
		}

		return nb;
	}
}
