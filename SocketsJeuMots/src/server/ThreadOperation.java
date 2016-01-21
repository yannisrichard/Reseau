/**
 * 
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author yarichard1
 *
 */
public class ThreadOperation implements Runnable {
	private Socket soc;
    private BufferedReader client;
    private PrintStream out;
    private int clientNumber;

	
	/**
	 * @param clientNumber 
	 * @param socket 
	 * 
	 */
	public ThreadOperation(Socket socket, int clientNumber) {
    	this.clientNumber = clientNumber;
        try {
            this.soc = socket;
            out = new PrintStream(soc.getOutputStream());
            client = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
	}


	@Override
	public void run() {
		String ligne = null;
		int resultat = 0;
        out.println("Client " + clientNumber);
        try {
            while(!(ligne = client.readLine()).equals("Fin")) { //NullPointer mettre un wait de l'autre côté 
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
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
                client.close();
                soc.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
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
