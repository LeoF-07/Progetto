import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe Gestore del Server
 * Gestisce il Server consentendone lo spegnimento forzato con la chiusura di tutte le connessioni
 */
public class Gestore extends Thread {

    private ArrayList<Connessione> connessioni;

    public Gestore(ArrayList<Connessione> connessioni){
        this.connessioni = connessioni;

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
                for(Connessione connessione : connessioni) connessione.chiudi();
                System.out.println("Chiusura forzata Server");
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
                for(Connessione connessione : connessioni) connessione.chiudi();
                System.out.println("Chiusura forzata Server");
                System.exit(0);
            }
        }
    }

}