import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JFrame {

    private JPanel comandoEParametro;
    private JPanel tabella;
    private JTable tabellaMonumenti;
    private JScrollPane scrollPaneTabella;
    private JComboBox<String> selectComandi;
    private JTextField[] parametri;
    private JTextArea descrizione;
    private JButton invia;
    private JFrame frameTabella;

    private ArrayList<String> risposte;

    private int max;

    public GUI(String[] comandi, String[] parametriPrevisti, String[] descrizioni, String[] attributiMonumento){
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

        String[][] data = new String[1][attributiMonumento.length];
        data[0] = attributiMonumento;
        tabellaMonumenti = new JTable(data, attributiMonumento);
        tabellaMonumenti.setLayout(null);
        tabellaMonumenti.setBounds(0, 0, 1800, 100);
        //tabellaMonumenti.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabellaMonumenti.doLayout();
        tabellaMonumenti.setBackground(Color.ORANGE);

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

                risposte = Main.inviaComando((String) selectComandi.getSelectedItem(), parametro);
                String[][] data = new String[risposte.size() + 1][attributiMonumento.length];
                data[0] = attributiMonumento;
                for(int i = 0; i < risposte.size(); i++) data[i + 1] = risposte.get(i).split(";");
                tabellaMonumenti = new JTable(data, attributiMonumento);
                tabellaMonumenti.setLayout(null);
                tabellaMonumenti.setBounds(0, 0, 1800, 800);
                //tabellaMonumenti.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                tabellaMonumenti.doLayout();
                tabellaMonumenti.setBackground(Color.ORANGE);
                tabellaMonumenti.setVisible(true);

                scrollPaneTabella = new JScrollPane(tabellaMonumenti);
                scrollPaneTabella.setBounds(0, 0, 1800, 800);

                tabella.add(scrollPaneTabella);
                frameTabella.setVisible(true);
            }
        });

        comandoEParametro = new JPanel();
        comandoEParametro.setLayout(null);
        comandoEParametro.add(selectComandi);

        for(int i = 0; i < max; i++) comandoEParametro.add(parametri[i]);

        comandoEParametro.add(descrizione);
        comandoEParametro.add(invia);
        comandoEParametro.setBounds(0, 0, 1000, 250);
        comandoEParametro.setBackground(Color.ORANGE);

        frameTabella = new JFrame("Risposta");
        frameTabella.setLayout(null);
        frameTabella.setBounds(0, 0, 2000, 800);
        frameTabella.setVisible(false);

        tabella = new JPanel();
        tabella.setLayout(null);
        tabella.setBackground(Color.BLUE);
        //tabella.add(tabellaMonumenti);
        tabella.setBounds(0, 0, 2000, 800);

        this.add(comandoEParametro);
        frameTabella.add(tabella);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(80, 80, 1000, 800);
        this.setVisible(true);
    }

}
