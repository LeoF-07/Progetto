import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private JPanel comandoEParametro;
    private JPanel tabella;
    private JTable tabellaMonumenti;
    private JScrollPane scrollPaneTabella;
    private JComboBox<String> selectComandi;
    private JTextField[] parametri;
    private JTextArea descrizione;
    private JButton invia;

    private int max;

    public GUI(String[] comandi, String[] parametriPrevisti, String[] descrizioni){
        this.setLayout(null);

        selectComandi = new JComboBox<>(comandi);
        selectComandi.setBounds(10, 80, 300, 20);

        max = 0;
        for (String parametri : parametriPrevisti) if (Integer.parseInt(parametri) > max) max = Integer.parseInt(parametri);

        parametri = new JTextField[max];
        for(int i = 0; i < max; i++) {
            parametri[i] = new JTextField("Parametro");
            parametri[i].setVisible(false);
        }

        parametri[0].setBounds(360, 80, 300, 20);
        parametri[0].setVisible(true);

        descrizione = new JTextArea(descrizioni[0]);
        descrizione.setBounds(10, 130, 960, 50);
        descrizione.setEditable(false);
        // descrizione.setLineWrap(true);

        invia = new JButton("Invia comando");
        invia.setBounds(800, 80, 150, 20);

        selectComandi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = Integer.parseInt(parametriPrevisti[selectComandi.getSelectedIndex()]);
                for(int i = 0; i < n; i++){
                    parametri[i].setBounds(360 + (300 / n + 10) * i, 80, 300 / n, 20);
                    parametri[i].setVisible(true);
                }
                for(int i = n; i < max; i++) parametri[i].setVisible(false);
                descrizione.setText(descrizioni[selectComandi.getSelectedIndex()]);
            }
        });

        invia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String parametro = "";
                for(int i = 0; i < Integer.parseInt(parametriPrevisti[selectComandi.getSelectedIndex()]); i++){
                    if(i != Integer.parseInt(parametriPrevisti[selectComandi.getSelectedIndex()]) - 1) parametro += parametri[i].getText() + " ";
                    else parametro += parametri[i].getText();
                }

                Main.inviaComando((String) selectComandi.getSelectedItem(), parametro);
            }
        });

        comandoEParametro = new JPanel();
        comandoEParametro.setLayout(null);
        comandoEParametro.add(selectComandi);

        for(int i = 0; i < max; i++) comandoEParametro.add(parametri[i]);

        comandoEParametro.add(descrizione);
        comandoEParametro.add(invia);
        comandoEParametro.setBounds(0, 0, 1000, 300);

        this.add(comandoEParametro);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(80, 80, 1000, 800);
        this.setVisible(true);
    }

}
