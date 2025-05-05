import java.io.*;
import java.net.*;

/**
 * Classe Main del Server UDP
 * Accetta e gestisce le connessioni al Server UDP
 * @author Leonardo
 */
public class Main {

    public final static int PORT = 1050; // porta al di fuori del range 1-1024 !
    public static  Gestore gestore;

    /**
     * Metodo main del Server, crea un DatagramSocket e accetta le connessioni singole
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        gestore = new Gestore(); // Gestore generale del Server, ne consente lo spegimento forzato
        gestore.start();

        while(true){
            try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
                Connessione connessione = new Connessione(serverSocket);
                connessione.comunica();
            }
        }
    }

}