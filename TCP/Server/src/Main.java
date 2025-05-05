import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Classe Main del Server TCP
 * Accetta e gestisce le connessioni al Server TCP
 */
public class Main {

    public final static int PORT = 1050; // porta al di fuori del range 1-1024

    /**
     * Metodo main del Server, accetta le connession e avvia un Thread per la gestione di ognuna di esse
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ArrayList<Connessione> connessioni = new ArrayList<>();

        Gestore gestore = new Gestore(connessioni); // Gestore generale del Server, ne consente lo spegnimento forzato
        gestore.start();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Socket clientSocket;

            while(true) {
                try {
                    clientSocket = serverSocket.accept();
                    System.out.println("Connection accepted: " + clientSocket);
                    Connessione connessione = new Connessione(clientSocket);
                    connessione.start();
                    connessioni.add(connessione);
                } catch (IOException e) {
                    System.err.println("Accept failed");
                    System.exit(1);
                }
            }
        }
    }

}