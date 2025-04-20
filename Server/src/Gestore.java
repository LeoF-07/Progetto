import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class Gestore extends Thread {

    private ArrayList<Connessione> connessioni;

    public Gestore(ArrayList<Connessione> connessioni){
        this.connessioni = connessioni;

        JFrame jFrame = new JFrame("Chiusura connessioni");
        jFrame.setLayout(null);
        jFrame.setBounds(200, 200, 500, 500);
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jFrame.setResizable(false);

        JButton jButton = new JButton("Chiudi connessioni");
        jButton.setBounds(170, 170, 150, 100);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Connessione connessione : connessioni) connessione.chiudi();
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
                System.exit(0);
            }
        }
    }

}
