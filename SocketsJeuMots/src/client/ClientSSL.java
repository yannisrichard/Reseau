/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author yannis
 */
public class ClientSSL {
    public ClientSSL(int port, String adresse) throws IOException {
        String ligne, response;

        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try(SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(adresse, port)){        
            try (Reader reader = new InputStreamReader(System.in)) {
                try (BufferedReader keyboard = new BufferedReader(reader)) {
                    try (PrintStream output = new PrintStream(sslsocket.getOutputStream())) {
                        try (Reader socketReader = new InputStreamReader(sslsocket.getInputStream())) {
                            try (BufferedReader buffer = new BufferedReader(socketReader)) {
                                while (true) {
                                    printMenu();
                                    ligne = keyboard.readLine();
                                    
                                    if (ligne.equals("0")) {
                                        break;
                                    }
                                    System.out.println("Entrer votre phrase : ");
                                    String choixtmp=ligne; 
                                    ligne = keyboard.readLine(); 
                                    output.println(choixtmp);
                                    output.println(ligne);
                                    output.flush();                                    

                                    System.out.println("Réponse du serveur :" + buffer.readLine());
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private void printMenu() {
        System.out.println("1 : Nombre de voyelles \n"
                + "2 : Nombre de consonnes \n"
                + "0 : Fin du programme\n");
    }

}
