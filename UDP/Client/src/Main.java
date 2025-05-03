import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONObject;


public class Main{

    final static String nomeServer = "localhost";
    final static int portaServer = 1050;

    private static byte[] in;
    private static byte[] out;

    private static DatagramPacket pktIn;
    private static DatagramPacket pktOut;

    private static DatagramSocket serverSocket;
    private static InetAddress ipServer;

    private static String[] attributiMonumento;

    public static void main(String[] args) {
        System.out.println("Connessione al server in corso...");
        try (DatagramSocket sck = new DatagramSocket()) {
            comunica(sck);
            System.exit(0);
        } catch (IOException e) {
            System.err.format("Errore durante la comunicazione con il server: %s%n", e.getMessage());
        }
    }

    private static void comunica(DatagramSocket sck) {
        serverSocket = sck;

        try {
            in = new byte[1024];

            ipServer = InetAddress.getLocalHost();

            out = ("Avvia connessione").getBytes();
            pktOut = new DatagramPacket(out, out.length, ipServer, portaServer);
            sck.send(pktOut);

            pktIn = new DatagramPacket(in, in.length);
            sck.receive(pktIn);
            System.out.println(new String(pktIn.getData(), 0, pktIn.getLength(), StandardCharsets.UTF_8)); // Legge il messaggio iniziale

            JSONObject j = new JSONObject();
            j.put("comando", "GET");
            j.put("parametro", "");
            out = j.toString().getBytes();
            pktOut = new DatagramPacket(out, out.length, ipServer, portaServer);
            sck.send(pktOut);

            sck.receive(pktIn);
            String comandi = new String(pktIn.getData(), 0, pktIn.getLength(), StandardCharsets.UTF_8); // Legge una stringa contenente tutti i comandi
            sck.receive(pktIn);
            String parametriPrevisti = new String(pktIn.getData(), 0, pktIn.getLength(), StandardCharsets.UTF_8); // Legge una stringa contenente il numero di parametri previsto per ogni comando
            sck.receive(pktIn);
            String descrizioni = new String(pktIn.getData(), 0, pktIn.getLength(), StandardCharsets.UTF_8); // Legge una stringa contenente tutte le descrizioni per ogni comando
            sck.receive(pktIn);
            attributiMonumento = new String(pktIn.getData(), 0, pktIn.getLength(), StandardCharsets.UTF_8).split(";"); // Legge una stringa contenente gli attributi di un monumento

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

    public static ArrayList<String> serializzaEInviaAlServer(String comando, String parametro) throws Exception {
        JSONObject jsonComando = new JSONObject();
        jsonComando.put("comando", comando);
        jsonComando.put("parametro", parametro);

        System.out.format("Invio al server: %s%n", jsonComando);
        out = jsonComando.toString().getBytes();
        pktOut = new DatagramPacket(out, out.length, ipServer, portaServer);
        serverSocket.send(pktOut);

        System.out.println("In attesa di risposta dal server...");

        String risposta;
        ArrayList<String> risposte = new ArrayList<>();

        try {
            serverSocket.receive(pktIn);
            risposta = new String(pktIn.getData(), 0, pktIn.getLength(), StandardCharsets.UTF_8);

            if(risposta.startsWith("ERROR:")) throw new Exception(risposta);
            while(!risposta.equals("FINE")){
                JSONObject jsonMonumento = new JSONObject(risposta);

                String monumento = "";
                for (String attributo : attributiMonumento) monumento += jsonMonumento.get(attributo) + ";";
                System.out.format("Risposta dal server: Monumento: %s%n", jsonMonumento);
                risposte.add(monumento);

                serverSocket.receive(pktIn);
                risposta = new String(pktIn.getData(), 0, pktIn.getLength(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new ServerChiusoException("Il Server è stato chiuso o riavviato");
        }

        return risposte;
    }

}