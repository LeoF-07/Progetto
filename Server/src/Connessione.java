import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;

public class Connessione extends Thread {

    private final static String FINE_TRASMISSIONE = "FINE";

    private Socket clientSocket;

    private BufferedReader in = null;
    private PrintWriter out = null;

    private final String PATH = ".\\Mappa-dei-monumenti-in-Italia.csv";
    private File file;
    private ArrayList<Monumento> monumenti;

    public Connessione(Socket clientSocket){
        this.clientSocket = clientSocket;
        this.file = creaFile(PATH);
        this.monumenti = prelevaDati();
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            in = new BufferedReader(isr);

            OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(osw);

            out = new PrintWriter(bw, true);

            out.println("Hello (END to end connection)");
            out.flush();

            esegui();
        } catch (IOException e) {
            System.err.println("Accept failed");
            System.exit(1);
        }
    }

    private void esegui(){
        JSONObject partiComando;

        do {
            try {
                partiComando = new JSONObject(in.readLine());
                System.out.println(partiComando);

                String comando = partiComando.getString("comando");
                String parametro;
                try{
                    parametro = partiComando.getString("parametro");
                }catch (JSONException e){
                    break;
                }

                eseguiComando(comando, parametro);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while(!partiComando.getString("comando").equals("END"));

        System.out.println("Server: closing...");
        chiudi();
        System.out.println("Server: closed");
    }

    public void eseguiComando(String nomeComando, String parametro){ // Potrei fare che invia json di monumenti, in modo che possano essere gestiti in liste anche sul Client, o semplicemente visualizzati in base ai campi nella GUI del Client
        if(nomeComando.equals("GET")){
            String comandi = "";
            for(int i = 0; i < Comando.values().length; i++) {
                if(i != Comando.values().length - 1) comandi += Comando.values()[i].nome + " ";
                else comandi += Comando.values()[i].nome;
            }
            out.println(comandi);
            out.flush();
            return;
        }

        Comando comando = null;
        for(Comando c : Comando.values()) if(c.nome.equals(nomeComando)) comando = c;
        if(comando == null) return;

        double longitudine1;
        double longitudine2;
        double latitudine1;
        double latitudine2;

        switch (comando){
            case GET_ROW:
                int riga = Integer.parseInt(parametro);
                out.println(monumenti.get(riga));
                out.flush();
                out.println(FINE_TRASMISSIONE);
                out.flush();
                break;

            case GET_PER_COMUNE:
                for (Monumento monumento : monumenti){
                    if(monumento.getComune().equalsIgnoreCase(parametro)) {
                        out.println(monumento);
                        out.flush();
                    }
                }
                out.println(FINE_TRASMISSIONE);
                out.flush();
                break;

            case GET_PER_PROVINCIA:
                for (Monumento monumento : monumenti){
                    if(monumento.getProvincia().equalsIgnoreCase(parametro)) {
                        out.println(monumento);
                        out.flush();
                    }
                }
                out.println(FINE_TRASMISSIONE);
                out.flush();
                break;

            case GET_PER_REGIONE:
                for (Monumento monumento : monumenti){
                    if(monumento.getRegione().equalsIgnoreCase(parametro)) {
                        out.println(monumento);
                        out.flush();
                    }
                }
                out.println(FINE_TRASMISSIONE);
                out.flush();
                break;

            case GET_PER_NOME:
                for (Monumento monumento : monumenti){
                    if(monumento.getNome().equals(parametro)) {
                        out.println(monumento);
                        out.flush();
                    }
                }
                out.println(FINE_TRASMISSIONE);
                out.flush();
                break;

            case GET_PER_NOME_PARZIALE:
                for (Monumento monumento : monumenti){
                    if(monumento.getNome().contains(parametro)) {
                        out.println(monumento);
                        out.flush();
                    }
                }
                out.println(FINE_TRASMISSIONE);
                out.flush();
                break;

            case GET_PER_TIPO:
                for (Monumento monumento : monumenti){
                    if(monumento.getTipo().equals(parametro)) {
                        out.println(monumento);
                        out.flush();
                    }
                }
                out.println(FINE_TRASMISSIONE);
                out.flush();
                break;

            case GET_PER_ANNO:
                for (Monumento monumento : monumenti){
                    if(monumento.getAnnoInserimento().equals(Year.parse(parametro))) {
                        out.println(monumento);
                        out.flush();
                    }
                }
                out.println(FINE_TRASMISSIONE);
                out.flush();
                break;

            case GET_PER_ANNI:
                String[] anni = parametro.split(" "); // O FACCIO COSÌ O DOVREI MANDARE UN'ARRAY DI PARAMETRI SUL JSON, FORSE È PIÙ COMODO COSÌ
                Year anno1 = Year.parse(anni[0]);
                Year anno2 = Year.parse(anni[1]);

                for (Monumento monumento : monumenti){
                    if(monumento.getAnnoInserimento().compareTo(anno1) >= 0 && monumento.getAnnoInserimento().compareTo(anno2) <= 0) {
                        out.println(monumento);
                        out.flush();
                    }
                }
                out.println(FINE_TRASMISSIONE);
                out.flush();

                break;

            case GET_TRA_LONGITUDINI:
                String[] longitudini = parametro.split(" "); // O FACCIO COSÌ O DOVREI MANDARE UN'ARRAY DI PARAMETRI SUL JSON, FORSE È PIÙ COMODO COSÌ
                longitudine1 = Double.parseDouble(longitudini[0]);
                longitudine2 = Double.parseDouble(longitudini[1]);

                for (Monumento monumento : monumenti){
                    if(monumento.getLongitudine() >= longitudine1 && monumento.getLongitudine() <= longitudine2) {
                        out.println(monumento);
                        out.flush();
                    }
                }
                out.println(FINE_TRASMISSIONE);
                out.flush();

                break;

            case GET_TRA_LATITUDINI:
                String[] latitudini = parametro.split(" "); // O FACCIO COSÌ O DOVREI MANDARE UN'ARRAY DI PARAMETRI SUL JSON, FORSE È PIÙ COMODO COSÌ
                latitudine1 = Double.parseDouble(latitudini[0]);
                latitudine2 = Double.parseDouble(latitudini[1]);

                for (Monumento monumento : monumenti){
                    if(monumento.getLatitudine() >= latitudine1 && monumento.getLatitudine() <= latitudine2) {
                        out.println(monumento);
                        out.flush();
                    }
                }
                out.println(FINE_TRASMISSIONE);
                out.flush();

                break;

            case GET_TRA_LONGITUDINI_E_LATITUDINI:
                String[] longitudiniELatidudini = parametro.split(" ");
                longitudine1 = Double.parseDouble(longitudiniELatidudini[0]);
                longitudine2 = Double.parseDouble(longitudiniELatidudini[1]);
                latitudine1 = Double.parseDouble(longitudiniELatidudini[2]);
                latitudine2 = Double.parseDouble(longitudiniELatidudini[3]);

                System.out.println(longitudine1 + " " + longitudine2 + " " + latitudine1 + " " + latitudine2);

                for (Monumento monumento : monumenti){
                    if(monumento.getLongitudine() >= longitudine1 && monumento.getLongitudine() <= longitudine2 && monumento.getLatitudine() >= latitudine1 && monumento.getLatitudine() <= latitudine2) {
                        out.println(monumento);
                        out.flush();
                    }
                }
                out.println(FINE_TRASMISSIONE);
                out.flush();
                break;
        }
    }

    public void chiudi(){ // Chiusura forzata dal gestore
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Monumento> prelevaDati(){
        ArrayList<Monumento> monumenti = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            while (bufferedReader.ready()){
                String[] informazioni = bufferedReader.readLine().split(";");
                Monumento monumento = new Monumento(informazioni[0], informazioni[1], informazioni[2], informazioni[3],
                                                    informazioni[4], Year.parse(informazioni[5]), LocalDateTime.parse(informazioni[6].substring(0, informazioni[6].length() - 1)),
                                                    informazioni[7], Double.parseDouble(informazioni[8]), Double.parseDouble(informazioni[9]));
                monumenti.add(monumento);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return monumenti;
    }

    private File creaFile(String path){
        File file = new File(path);

        try {
            if(file.createNewFile()) System.out.println("File creato");
            else System.out.println("File CSV pronto all'uso");
        } catch (IOException e) {
            System.out.println("Errore nella creazione del file");
        }

        return file;
    }

}
