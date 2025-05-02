import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Main {

    public final static int PORT = 1050; // porta al di fuori del range 1-1024 !
    public static Gestore gestore;

    public static void main(String[] args) throws IOException {
        gestore = new Gestore();
        gestore.start();

        while(true){
            try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
                Connessione connessione = new Connessione(serverSocket);
                connessione.comunica();
            }
        }
    }

}