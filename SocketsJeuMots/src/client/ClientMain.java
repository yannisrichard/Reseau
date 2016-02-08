/**
 * 
 */
package client;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yarichard1
 *
 */
public class ClientMain {

	/**
	 * Le main.
    * @param args the command line arguments
     */
    public static void main(String[] args) {
		new Client(16000, 15000, "127.0.0.1");
//        try {
//			new ClientOld(16000, 15000, "127.0.0.1");
//		} catch (IOException ex) {
//            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
//		}
        
    }

}

