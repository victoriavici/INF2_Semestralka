package sk.uniza.fri.gui;

import com.badlogic.gdx.Gdx;
import sk.uniza.fri.zapis.Tabulka;
import sk.uniza.fri.zapis.Zapis;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;

/**
 * Trieda Prehra reprezentuje okno, ktore sa objavi po tom, ako hrac prehra.
 * Ma na vyber ci hru ukonci, alebo bude hrat znova.
 * Uvidi aj zoznam s ostatnymi hracmi a ich dosiahnute skore.
 */
public class Prehra  {
    private final JFrame jFrame;
    private JList<Zapis> poradovaTabulkaList;
    private JButton hratZnovaButton;
    private JButton koniecButton;
    private JPanel panel1;
    private JLabel text;
    private JPanel panel;

    /**
     * Vytvorenie okna
     */
    public Prehra() {

        this.setupUI();
        this.jFrame = new JFrame("Prehral si ");
        this.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.jFrame.setLocation(Gdx.graphics.getWidth() / 2 + 70, Gdx.graphics.getHeight() / 2 + 50);
        this.jFrame.setMinimumSize(new Dimension(400, 300));

        this.jFrame.add(this.panel);

        this.jFrame.pack();
        this.text.setText("Umrel si :(Chceš hrať znova?");

        try {
            this.nacitajHracov();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.jFrame.setVisible(true);

        this.hratZnovaButton.addActionListener(e -> Prehra.this.hraj());
        this.koniecButton.addActionListener(e -> Prehra.this.ukonci());

    }

    /**
     * Ukončenie aplikácie
     */
    protected void ukonci() {
        System.exit(0);
    }

    /**
     * Skrytie frameu
     */
    protected void hraj() {
        this.jFrame.setVisible(false);
    }

    /**
     * Metóda na zistenie, či je frame vidiet
     * @return či frame je vidieť
     */
    public boolean neexistuje() {
        return !this.jFrame.isVisible();
    }

    /**
     * Metóda, pomocou ktorej sa načíta poradová tabuľka do 10 zapisov.
     * @throws IOException .
     */
    protected void nacitajHracov() throws IOException {
        Tabulka tabulka = new Tabulka();
        tabulka.nacitajTabulku();
        DefaultListModel<Zapis> zoznam = new DefaultListModel<>();
        for (int i = 0; i < 10; i++) {
            if (i < tabulka.getVelkost()) {
                zoznam.addElement(tabulka.getZapis(i));
            }
        }

        this.poradovaTabulkaList.setModel(zoznam);

    }
    private void createUIComponents() {
        this.poradovaTabulkaList = new JList<>();
    }

    protected void setupUI() {
        createUIComponents();
        panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 0));
        panel.setBackground(new Color(-986896));
        panel.setEnabled(true);

        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.add(panel1, BorderLayout.CENTER);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 20 , 20 ) );
        panel.add(panel2, BorderLayout.NORTH);
        text = new JLabel();
        panel2.add(text);

        hratZnovaButton = new JButton();
        hratZnovaButton.setText("Hrať znova");
        panel1.add(hratZnovaButton);
        koniecButton = new JButton();
        koniecButton.setText("Koniec");
        panel1.add(koniecButton);

        final DefaultListModel<Zapis> defaultListModel1 = new DefaultListModel<>();
        poradovaTabulkaList.setModel(defaultListModel1);
        poradovaTabulkaList.setSelectionMode(0);
        panel.add(poradovaTabulkaList, BorderLayout.SOUTH);

    }

    protected JFrame getjFrame() {
        return jFrame;
    }

    protected JPanel getPanel1() {
        return panel1;
    }

    protected JLabel getText() {
        return text;
    }
}
