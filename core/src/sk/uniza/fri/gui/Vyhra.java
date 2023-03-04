package sk.uniza.fri.gui;

import sk.uniza.fri.zapis.Tabulka;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.io.IOException;

/**
 * Trieda Vyhra je potomkom triedy Prehra.
 * Vytvori okno s tromi buttonmi. Kazde ma ine inu funkciu a podla volby sa vykona dana funkcia
 */
public class Vyhra extends Prehra {
    private final int skore;
    private JButton zapisSkoreButton;
    private boolean zapisane = false;

    /**
     * Vytvorenie okna vyhry
     * @param skore hraca
     */
    public Vyhra(int skore) {
        super();
        this.skore = skore;
        this.getjFrame().setTitle("Vyhral si");
        this.getText().setText("Dosiahol si skore " + this.skore);
        this.zapisSkoreButton.addActionListener(e -> {
            try {
                Vyhra.this.zapisSkore();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    /**
     * V metóde sa vytvorí dalsie okno, ktorom hrac zada svoje meno.
     * Meno a skore nasledne zapise do tabulky a znovu nacita ju.
     *
     * @throws IOException .
     */
    private void zapisSkore() throws IOException {
        if (!this.zapisane) {
            String meno = JOptionPane.showInputDialog(null, "Zadaj meno");
            Tabulka tabulka = new Tabulka();
            tabulka.ulozDoSuboru(meno, this.skore);
            this.zapisane = true;
            super.nacitajHracov();

        }
    }

    /**
     * Nastavenie Gui
     */
    protected void setupUI() {
        super.setupUI();
        zapisSkoreButton = new JButton();
        zapisSkoreButton.setText("Zapíš skóre");
        this.getPanel1().add(zapisSkoreButton);

    }
}
