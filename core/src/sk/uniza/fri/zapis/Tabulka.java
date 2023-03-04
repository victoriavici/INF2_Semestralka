package sk.uniza.fri.zapis;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Trieda Tabulka obsahuje list vstekych zapisov zo suboru. Vie zapisovat dalsie zapisy, citat zo suboru.
 */
public class Tabulka {
    private final ArrayList<Zapis> tabulka;
    private final File subor;

    /**
     * Inicializácia atribútov.
     */
    public Tabulka() {
        this.tabulka = new ArrayList<>();
        this.subor = new File("tabulka.txt");
    }

    /**
     * Metóda slúži na zápis do súboru.
     * Pomocou metódy NacitajZoSuboru do arrayListu tabulka načíta všetky zápisy v textovom dokumente,
     * pridá k nim meno aktuálneho hráča a jeho skore.
     * Následne tento arrayList vypíše celý do textového dokumentu.
     */
    public void ulozDoSuboru(String meno, int skore) throws IOException {
        this.nacitajZoSuboru();
        this.tabulka.add(new Zapis(null, meno, skore));

        PrintWriter zapisovac = new PrintWriter(this.subor);

        for (Zapis zapis : this.tabulka) {
            zapisovac.print(zapis.getMeno());
            zapisovac.print(" ");
            zapisovac.println(zapis.getSkore());
        }

        zapisovac.close();
    }

    /**
     * Metóda slúži na načitanie zápisov z textového dokumentu.
     */
    private void nacitajZoSuboru() throws IOException {
        Scanner citac = new Scanner(this.subor);

        this.tabulka.clear();

        while (citac.hasNextLine()) {
            String menoHraca = citac.next();
            int skoreHraca = citac.nextInt();
            citac.nextLine();

            this.tabulka.add(new Zapis(null, menoHraca, skoreHraca));
        }
    }

    /**
     * Metoda sluzi na nacitanie a usporiadanie zapisov v tabulke podla najvacsieho skore.
     *
     * @throws IOException .
     */
    public void nacitajTabulku() throws IOException {
        this.nacitajZoSuboru();

        Zapis[] poradovaTabulka;
        poradovaTabulka = new Zapis[this.tabulka.size()];
        for (int i = 0; i < this.tabulka.size(); i++) {
            poradovaTabulka[i] = this.tabulka.get(i);
        }

        for (int i = 0; i < poradovaTabulka.length; i++) {
            for (int j = 0; j < poradovaTabulka.length - i - 1; j++) {
                if (poradovaTabulka[j].getSkore() < poradovaTabulka[j + 1].getSkore()) {
                    Zapis pomZapis = poradovaTabulka[j];
                    poradovaTabulka[j] = poradovaTabulka[j + 1];
                    poradovaTabulka[j + 1] = pomZapis;
                }
            }
        }
        this.tabulka.clear();
        for (int i = 0; i < poradovaTabulka.length; i++) {
            poradovaTabulka[i].setPoradie(i + 1 + ".");
            this.tabulka.add(poradovaTabulka[i]);
        }
    }


    public Zapis getZapis(int i) {
        return this.tabulka.get(i);
    }

    public int getVelkost() {
        return this.tabulka.size();
    }
}
