import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Main {

    public final static int PORT = 1050; // porta al di fuori del range 1-1024 !

    public static void main(String[] args) throws IOException {
        ArrayList<Connessione> connessioni = new ArrayList<>();

        while(true){
            try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
                Connessione connessione = new Connessione(serverSocket);
                connessione.comunica();
            }
        }
    }

}