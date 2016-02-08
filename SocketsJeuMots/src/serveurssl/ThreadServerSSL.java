package serveurssl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

public class ThreadServerSSL implements Runnable {
    private SSLSocket socket; 
    
    ThreadServerSSL(SSLSocket socket) {
        this.socket = socket; 
    }

    @Override
    public void run() {
        Server serveur = new Server(); 
        try {
            serveur.startServeur(socket);            
            System.out.println("Client déconnecté de " + socket);
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadServerSSL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
