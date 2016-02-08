package serveurssl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class ServerSSL {
    /**
     * @param args the command line arguments
     */
	public ServerSSL(int port){
        Server serveur = new Server();
        List<Thread> threadServeurs = new ArrayList<>();
        try {
            SSLServerSocketFactory sslserversocketfactory =
                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket sslserversocket =
                    (SSLServerSocket) sslserversocketfactory.createServerSocket(4563);
                 
            System.out.println("Serveur lancé");
            while (true) {    
                SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();     
                try {
                    System.out.println("Connexion réalisée a " + sslsocket);
                    Thread t = new Thread(new ThreadServerSSL(sslsocket));
                    t.start();
                }
                catch(Exception e){
                    sslsocket.close();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerSSL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
