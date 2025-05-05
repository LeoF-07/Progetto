import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe Gestore del Server
 * Gestisce il Server consentendo il suo spegnimento forzato
 */
public class Gestore extends Thread {

    private Connessione connessione;

    public Gestore(){
        JFrame jFrame = new JFrame("Chiusura connessioni");
        jFrame.setLayout(null);
        jFrame.setSize(500, 500);
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);

        JButton jButton = new JButton("Chiudi connessioni");
        jButton.setBounds(170, 170, 150, 100);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    connessione.chiudi();
                }catch (NullPointerException ex){

                }
                System.exit(0);
            }
        });

        jFrame.add(jButton);
        jFrame.setVisible(true);
    }

    @Override
    public void run() {
        System.out.println("Gestore delle connessioni\nDigita STOP in qualsiasi momento per spegnere il Server\n");
        while(true){
            if(new Scanner(System.in).nextLine().equals("STOP")){
                try{
                    connessione.chiudi();
                }catch (NullPointerException ex){

                }
                System.exit(0);
            }
        }
    }

    public void setConnessione(Connessione connessione) {
        this.connessione = connessione;
    }

}
