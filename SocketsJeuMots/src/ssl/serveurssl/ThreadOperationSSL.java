/**
 * 
 */
package ssl.serveurssl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;

/**
 * @author yarichard1
 *
 */
public class ThreadOperationSSL implements Runnable {
	private SSLSocket soc;
	private BufferedReader client;
	private PrintStream out;
	private int clientNumber;


	/**
	 * @param clientNumber 
	 * @param socket 
	 * 
	 */
	public ThreadOperationSSL(SSLSocket socket, int clientNumber) {
		this.clientNumber = clientNumber;
		try {
			this.soc = socket;
			out = new PrintStream(soc.getOutputStream());
			client = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		} 
		catch (IOException e) {
			Logger.getLogger(ThreadOperationSSL.class.getName()).log(Level.SEVERE, null, e); 
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
					if(!(ligne.equals("Fin"))){
						String[] splitArray = ligne.split(" : ");
						switch (splitArray[0]) {
						case "Consonne":
							resultat = consonne(splitArray[1]);
							out.println(resultat);	
							break;
						case "Voyelle":
							resultat = voyelle(splitArray[1]);
							out.println(resultat);
							break;
						default:
							System.out.println("Mauvaise Commande");
							break;
						}
						resultat = 0;
						ligne = null;
					}
				}
			}


		}
		catch (IOException e) {
			Logger.getLogger(ThreadOperationSSL.class.getName()).log(Level.SEVERE, null, e);      
		}
		finally {
			try {
				out.close();
				client.close();
				soc.close();
			} 
			catch (IOException e) {
				Logger.getLogger(ThreadOperationSSL.class.getName()).log(Level.SEVERE, null, e);    
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
