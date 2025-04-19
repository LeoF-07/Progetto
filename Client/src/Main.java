import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.json.JSONObject;


public class Main{

    final static String nomeServer = "localhost";
    final static int portaServer = 1050;

    private static BufferedReader in;
    private static PrintWriter out;

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

    private static void comunica(Socket sck) {
        try {
            in = new BufferedReader(new InputStreamReader(sck.getInputStream(), StandardCharsets.UTF_8));
            out = new PrintWriter(new OutputStreamWriter(sck.getOutputStream(), StandardCharsets.UTF_8), true);

            JSONObject j = new JSONObject();
            j.put("comando", "GET");
            j.put("parametro", "");
            out.println(j);
            String comandi = in.readLine();
            comandi = in.readLine();
            GUI gui = new GUI(comandi.split(" "));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner s = new Scanner(System.in, StandardCharsets.UTF_8);

        String comando;
        do {
            System.out.print("\nComando: ");
            comando = s.nextLine();

            String[] split = comando.split(" ");

            String[] partiComando = new String[2];
            partiComando[0] = split[0];
            try{
                partiComando[1] = split[1];
                for(int i = 2; i < split.length; i++) partiComando[1] += " " + split[i];
            } catch (ArrayIndexOutOfBoundsException e){
                partiComando[1] = "";
            }

            comando = serializzaEInviaAlServer(partiComando[0], partiComando[1]);
        } while(!comando.equals("END"));
    }

    public static void inviaComando(String comando, String parametro){
        serializzaEInviaAlServer(comando, parametro);
        if(comando.equals("END")) System.exit(0);
        System.out.print("\nComando: ");
    }

    public static String serializzaEInviaAlServer(String comando, String parametro){
        JSONObject jsonComando = new JSONObject();
        jsonComando.put("comando", comando);
        jsonComando.put("parametro", parametro);

        System.out.format("Invio al server: %s%n", jsonComando);
        out.println(jsonComando);

        System.out.println("In attesa di risposta dal server...");

        String risposta;
        try {
            risposta = in.readLine();
            while(risposta != null && !risposta.equals("FINE")){
                System.out.format("Risposta dal server: %s%n", risposta);
                risposta = in.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return comando;
    }

}