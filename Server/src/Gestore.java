import java.util.ArrayList;
import java.util.Scanner;

public class Gestore extends Thread {

    private ArrayList<Connessione> connessioni;

    public Gestore(ArrayList<Connessione> connessioni){
        this.connessioni = connessioni;
    }

    @Override
    public void run() {
        System.out.println("Gestore delle connessioni\nDigita STOP in qualsiasi momento per spegnere il Server\n");
        while(true){
            if(new Scanner(System.in).nextLine().equals("STOP")){
                for(Connessione connessione : connessioni) connessione.chiudi();
                System.exit(0);
            }
        }
    }

}
