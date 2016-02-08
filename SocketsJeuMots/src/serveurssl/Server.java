package serveurssl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLSocket;

public class Server {
     @SuppressWarnings("serial")
	private static final List<Character> voyelles = new ArrayList<Character>() {
        {
            add('a');
            add('e');
            add('i');
            add('o');
            add('u');
            add('y');
        }
    };

    public void startServeur(SSLSocket socket) throws IOException {
        boolean boucle = true;
        String ligne;
        
        

        try (Reader reader = new InputStreamReader(socket.getInputStream())) {
            try (PrintStream output = new PrintStream(socket.getOutputStream())) {
                try (BufferedReader buffer = new BufferedReader(reader)) {
                    while (boucle) {
                        ligne = buffer.readLine();
                        switch (ligne.charAt(0)) {
                            case '0':
                                boucle = false;
                                break;
                            case '1':
                                ligne = buffer.readLine();
                                int nbVoyelles = compterVoyelles(ligne);
                                System.out.println("Phrase saisie par " + socket + " : " + ligne);
                                output.println(nbVoyelles);
                                output.flush();
                                break;
                            case '2':
                                ligne = buffer.readLine();
                                int nbConsonnes = compterConsonnes(ligne);
                                System.out.println("Phrase saisie par " + socket + " : " + ligne);
                                output.println(nbConsonnes);
                                output.flush();
                                
                                break;
                        }
                    }
                }
            }
        }
    }

    private int compterVoyelles(String ligne) {
        int nbVoyelles = 0;

        for (char c : ligne.toCharArray()) {
            if (voyelles.contains(c)) {
                nbVoyelles++;
            }
        }

        return nbVoyelles;
    }

    private int compterConsonnes(String ligne) {
        int nbConsonnes = 0;

        for (char c : ligne.toCharArray()) {
            if (!voyelles.contains(c)) {
                nbConsonnes++;
            }
        }

        return nbConsonnes;
    }
}
