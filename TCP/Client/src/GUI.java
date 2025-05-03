import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class GUI extends JFrame {

    private JPanel comandoEParametro;
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
        super("Selezione comando");


        this.setLayout(null);

        selectComandi = new JComboBox<>(comandi);
        selectComandi.setBounds(20, 80, 300, 20);

        max = 0;
        for (String parametri : parametriPrevisti) if (Integer.parseInt(parametri) > max) max = Integer.parseInt(parametri);

        parametri = new JTextField[max];
        for(int i = 0; i < max; i++) {
            parametri[i] = new JTextField("Parametro");
            parametri[i].setToolTipText("Parametro");
            parametri[i].addFocusListener(addPlaceHolder(parametri[i]));
            parametri[i].setVisible(false);
        }

        parametri[0].setBounds(380, 80, 300, 20);
        parametri[0].setVisible(true);

        descrizione = new JTextArea(descrizioni[0]);
        descrizione.setBounds(20, 130, 940, 50);
        descrizione.setEditable(false);
        // descrizione.setLineWrap(true);

        invia = new JButton("Invia comando");
        invia.setBounds(800, 80, 150, 20);

        scrollPaneTabella = new JScrollPane(tabellaMonumenti);
        scrollPaneTabella.setBounds(0, 0, 1800, 800);

        selectComandi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = Integer.parseInt(parametriPrevisti[selectComandi.getSelectedIndex()]);
                for(int i = 0; i < n; i++){
                    parametri[i].setBounds(380 + (300 / n + 10) * i, 80, 300 / n, 20);
                    parametri[i].setText("Parametro");
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
                    if(i != Integer.parseInt(parametriPrevisti[selectComandi.getSelectedIndex()]) - 1) parametro += parametri[i].getText() + ";";
                    else parametro += parametri[i].getText();
                }

                try {
                    risposte = Main.inviaComando((String) selectComandi.getSelectedItem(), parametro);
                } catch(ServerChiusoException ex){
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(invia, ex.getMessage());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException exc) {
                        throw new RuntimeException(exc);
                    }
                    System.exit(0);
                    return;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(invia, ex.getMessage());
                    System.out.print("\nComando: ");
                    return;
                }

                String[][] data = new String[risposte.size()][attributiMonumento.length];
                for(int i = 0; i < risposte.size(); i++) data[i] = risposte.get(i).split(";");

                tabellaMonumenti = new JTable(data, attributiMonumento);
                tabellaMonumenti.getTableHeader().setPreferredSize(new Dimension(0, 50));
                tabellaMonumenti.getTableHeader().setBackground(Color.PINK);

                scrollPaneTabella = new JScrollPane(tabellaMonumenti);

                frameTabella = new JFrame("Risposta");
                frameTabella.setSize(1800, 800);
                frameTabella.setLocationRelativeTo(null);
                frameTabella.setVisible(true);
                frameTabella.add(scrollPaneTabella);
            }
        });

        comandoEParametro = new JPanel();
        comandoEParametro.setLayout(null);
        comandoEParametro.add(selectComandi);

        for(int i = 0; i < max; i++) comandoEParametro.add(parametri[i]);

        comandoEParametro.add(descrizione);
        comandoEParametro.add(invia);
        comandoEParametro.setBounds(0, 0, 1000, 300);
        comandoEParametro.setBackground(Color.pink);

        this.add(comandoEParametro);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    public FocusListener addPlaceHolder(JTextField j){
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                j.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(j.getText().isEmpty()) j.setText("Parametro");
            }
        };
    }

}
