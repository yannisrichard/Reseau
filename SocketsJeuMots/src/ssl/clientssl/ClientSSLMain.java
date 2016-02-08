/**
 * 
 */
package ssl.clientssl;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yarichard1
 *
 */
public class ClientSSLMain {

	/**
	 * Le main.
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			new ClientSSL(9999, "127.0.0.1");
		} catch (IOException e) {
			Logger.getLogger(ClientSSLMain.class.getName()).log(Level.SEVERE, null, e);
		}

	}

}

