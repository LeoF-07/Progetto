import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.json.JSONObject;

import javax.swing.*;

public class Main{
    final static String nomeServer = "localhost";
    final static int portaServer = 1050;

    static JFrame jFrame = new JFrame();
    static JTextField areaComando = new JTextField();
    static JTextField areaParametro = new JTextField();
    static JButton jButton = new JButton("Invia comando");

    public static void main(String[] args) {
        jFrame.setBounds(10, 10, 500, 500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new FlowLayout());

        areaComando.setPreferredSize(new Dimension(100, 20));
        areaParametro.setPreferredSize(new Dimension(100, 20));
        jButton.setPreferredSize(new Dimension(100, 20));

        jFrame.add(areaComando);
        jFrame.add(areaParametro);
        jFrame.add(jButton);
        jFrame.pack();
        jFrame.setVisible(true);

        System.out.println("Connessione al server in corso...");
        try (Socket sck = new Socket(nomeServer, portaServer)) {
            String rem = sck.getRemoteSocketAddress().toString();
            String loc = sck.getLocalSocketAddress().toString();
            System.out.format("Server (remoto): %s%n", rem);
            System.out.format("Client (client): %s%n", loc);
            comunica(sck);
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

            String[] partiComando = comando.split(" ");

            jsonComando = new JSONObject();
            jsonComando.put("comando", partiComando[0]);
            if(!comando.equals("END")) jsonComando.put("parametro", partiComando[1]);

            System.out.format("Invio al server: %s%n", jsonComando);

            out.println(jsonComando);
            System.out.println("In attesa di risposta dal server...");

            String risposta = in.readLine();
            System.out.println(risposta);
            risposta = in.readLine();
            System.out.format("Risposta dal server: %s%n", risposta);
        } while(!jsonComando.getString("comando").equals("END")); // CAMBIO PERCHé QUI è TUTTO IL COMANDO
    }
}