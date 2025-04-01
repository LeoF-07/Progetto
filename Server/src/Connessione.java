import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Connessione extends Thread {

    private Socket clientSocket;

    private BufferedReader in = null;
    private PrintWriter out = null;

    private final String PATH = "./Progetto/Mappa-dei-monumenti-in-Italia.csv";
    private File file;

    public Connessione(Socket clientSocket){
        this.clientSocket = clientSocket;
        this.file = creaFile(PATH);
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            in = new BufferedReader(isr);

            OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(osw);

            out = new PrintWriter(bw, true);

            out.print("Hello (END to end connection): ");
            out.flush();

            esegui();
        } catch (IOException e) {
            System.err.println("Accept failed");
            System.exit(1);
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

    private void esegui(){
        String comando;

        do {
            try {
                comando = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while(!comando.equals("END"));

        System.out.println("EchoServer: closing...");
        chiudi();
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
