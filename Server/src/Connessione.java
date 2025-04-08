import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;

public class Connessione extends Thread {

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
        String comando;

        do {
            try {
                comando = in.readLine();
                System.out.println(comando);

                eseguiComando(comando);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while(!comando.equals("END"));

        System.out.println("Server: closing...");
        chiudi();
        System.out.println("Server: closed");
    }

    public void eseguiComando(String comando){
        if(comando.startsWith(Comando.GET_ROW.nome)) {
            int riga = Integer.parseInt(comando.substring(Comando.GET_ROW.lunghezzaComando + 1));
            System.out.println(riga);
            out.println(monumenti.get(riga));
            out.flush();
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
