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
    private JTextField parametro;
    private JButton invia;

    public GUI(String[] comandi){
        this.setLayout(null);

        selectComandi = new JComboBox<>(comandi);
        selectComandi.setBounds(10, 80, 300, 20);
        parametro = new JTextField("Inserisci il parametro");
        parametro.setBounds(360, 80, 200, 20);
        invia = new JButton("Invia comando");
        invia.setBounds(600, 80, 150, 20);

        invia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.inviaComando((String) selectComandi.getSelectedItem(), parametro.getText());
            }
        });

        comandoEParametro = new JPanel();
        comandoEParametro.setLayout(null);
        comandoEParametro.add(selectComandi);
        comandoEParametro.add(parametro);
        comandoEParametro.add(invia);
        comandoEParametro.setBackground(Color.BLUE);
        comandoEParametro.setBounds(0, 0, 800, 200);
        this.add(comandoEParametro);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(80, 80, 800, 800);
        this.setVisible(true);
    }

}
