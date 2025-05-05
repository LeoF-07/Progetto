import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONObject;

/**
 * Classe Main del Client TCP
 * Avvia una connessione al Server e prevede i metodi per comunicare con esso
 * @author Leonardo
 */
public class Main{

    final static String nomeServer = "localhost";
    final static int portaServer = 1050;

    private static BufferedReader in;
    private static PrintWriter out;

    private static String[] attributiMonumento;

    /**
     * Metodo Main del Client TCP
     * Avvia una connessione al Server
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Connessione al server in corso...");
        try (Socket sck = new Socket(nomeServer, portaServer)) {
            String rem = sck.getRemoteSocketAddress().toString();
            String loc = sck.getLocalSocketAddress().toString();
            System.out.format("Server (remoto): %s%n", rem);
            System.out.format("Client (client): %s%n", loc);
            comunica(sck);
            System.exit(0);
        } catch (UnknownHostException e) {
            System.err.format("Nome di server non valido: %s%n", e.getMessage());
        } catch (IOException e) {
            System.err.format("Errore durante la comunicazione con il server: %s%n", e.getMessage());
        }
    }

    /**
     * Metodo per la comunicazione con il Server
     * Rende visibile anche la GUI
     * @param sck Socket del Server
     */
    private static void comunica(Socket sck) {
        try {
            in = new BufferedReader(new InputStreamReader(sck.getInputStream(), StandardCharsets.UTF_8));
            out = new PrintWriter(new OutputStreamWriter(sck.getOutputStream(), StandardCharsets.UTF_8), true);

            JSONObject j = new JSONObject();
            j.put("comando", "GET");
            j.put("parametro", "");
            out.println(j);

            System.out.println(in.readLine()); // Legge il messaggio iniziale
            String comandi = in.readLine(); // Legge una stringa contenente tutti i comandi
            String parametriPrevisti = in.readLine(); // Legge una stringa contenente il numero di parametri previsto per ogni comando
            String descrizioni = in.readLine(); // Legge una stringa contenente tutte le descrizioni per ogni comando
            attributiMonumento = in.readLine().split(";"); // Legge una stringa contenente gli attributi di un monumento

            GUI gui = new GUI(comandi.split(";"), parametriPrevisti.split(";"), descrizioni.split(";"), attributiMonumento);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner s = new Scanner(System.in, StandardCharsets.UTF_8);

        String[] partiComando;
        do {
            System.out.print("\nComando: ");
            String comando = s.nextLine();

            String[] split = comando.split(" ");

            partiComando = new String[2];
            partiComando[0] = split[0];

            if(split.length == 1) partiComando[1] = "";
            else {
                partiComando[1] = comando.substring(split[0].length() + 1);
                partiComando[1] = partiComando[1].replace("; ", ";");
            }

            try {
                serializzaEInviaAlServer(partiComando[0], partiComando[1]);
            } catch (ServerChiusoException e){
                System.out.println(e.getMessage());
                System.exit(0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while(!partiComando[0].equals("END"));
    }

    public static ArrayList<String> inviaComando(String comando, String parametro) throws Exception {
        ArrayList<String> risposte = serializzaEInviaAlServer(comando, parametro);
        if(comando.equals("END")) System.exit(0);
        System.out.print("\nComando: ");
        return risposte;
    }

    /**
     * Metodo che serializza il comando in un messaggio JSON e lo invia al Server
     * @param comando Stringa contenente il comando
     * @param parametro Stringa contenente il parametro (o i parametri) del comando
     * @return Un ArrayList di Stringhe, ovvero delle risposte del Server
     * @throws Exception
     */
    public static ArrayList<String> serializzaEInviaAlServer(String comando, String parametro) throws Exception {
        JSONObject jsonComando = new JSONObject();
        jsonComando.put("comando", comando);
        jsonComando.put("parametro", parametro);

        System.out.format("Invio al server: %s%n", jsonComando);
        out.println(jsonComando);

        System.out.println("In attesa di risposta dal server...");

        String risposta;
        ArrayList<String> risposte = new ArrayList<>();

        try {
            risposta = in.readLine();
            if(risposta != null && risposta.startsWith("ERROR:")) throw new Exception(risposta);
            while(risposta != null && !risposta.equals("FINE")){
                JSONObject jsonMonumento = new JSONObject(risposta);

                String monumento = "";
                for (String attributo : attributiMonumento) monumento += jsonMonumento.get(attributo) + ";";
                System.out.format("Risposta dal server: Monumento: %s%n", jsonMonumento);
                risposte.add(monumento);

                risposta = in.readLine();
            }
        } catch (IOException e) {
            throw new ServerChiusoException("Il Server è stato chiuso o riavviato");
        }

        return risposte;
    }

}