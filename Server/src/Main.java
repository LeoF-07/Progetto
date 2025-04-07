import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Main {

    public final static int PORT = 1050; // porta al di fuori del range 1-1024 !

    public static void main(String[] args) throws IOException {
        ArrayList<Connessione> connessioni = new ArrayList<>();

        Gestore gestore = new Gestore(connessioni);
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