import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.json.JSONObject;


public class Main{

    final static String nomeServer = "localhost";
    final static int portaServer = 1050;

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

    private static void comunica(Socket sck) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(sck.getInputStream(), StandardCharsets.UTF_8));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(sck.getOutputStream(), StandardCharsets.UTF_8), true);

        Scanner s = new Scanner(System.in, StandardCharsets.UTF_8);

        String comando;
        JSONObject jsonComando;

        do {
            System.out.print("\nComando: ");
            comando = s.nextLine();

            String[] split = comando.split(" ");

            String[] partiComando = new String[2];
            partiComando[0] = split[0];
            if(!comando.equals("END")) partiComando[1] = split[1];
            for(int i = 2; i < split.length; i++) partiComando[1] += " " + split[i];

            jsonComando = new JSONObject();
            jsonComando.put("comando", partiComando[0]);
            if(!comando.equals("END")) jsonComando.put("parametro", partiComando[1]);

            System.out.format("Invio al server: %s%n", jsonComando);

            out.println(jsonComando);
            System.out.println("In attesa di risposta dal server...");

            String risposta = in.readLine();
            /*do{
                System.out.format("Risposta dal server: %s%n", risposta);
                risposta = in.readLine();
            }while(risposta != null && !risposta.equals("FINE"));*/
            while(risposta != null && !risposta.equals("FINE")){
                System.out.format("Risposta dal server: %s%n", risposta);
                risposta = in.readLine();
            }
        } while(!jsonComando.getString("comando").equals("END")); // CAMBIO PERCHé QUI è TUTTO IL COMANDO
    }
}