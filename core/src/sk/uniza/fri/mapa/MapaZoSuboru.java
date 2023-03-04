package sk.uniza.fri.mapa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sk.uniza.fri.Smer;
import sk.uniza.fri.citanie.Citac;
import sk.uniza.fri.citanie.Riadok;
import sk.uniza.fri.postavy.Chrobak;
import sk.uniza.fri.postavy.Jezko;
import sk.uniza.fri.postavy.Slimak;
import sk.uniza.fri.postavy.Zviera;
import sk.uniza.fri.predmety.Minca;
import sk.uniza.fri.predmety.Predmet;
import sk.uniza.fri.predmety.Siska;
import sk.uniza.fri.predmety.Zivot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Trieda MapaZoSuboru vytvára kocky, zvieratá a predmety v hre.
 */
public class MapaZoSuboru {

    private final ArrayList<Riadok> riadky;
    private final LinkedList<Kocka> kocky;
    private final ArrayList<Predmet> predmety;
    private final ArrayList<Zviera> zvierata;


    public MapaZoSuboru() throws IOException {
        this.riadky = Citac.nacitajSubor("C:\\Users\\vikig\\OneDrive - Žilinská univerzita v Žiline\\Pracovná plocha\\Semestralka1\\mapa.txt");
        this.kocky = new LinkedList<>();
        this.predmety = new ArrayList<>();
        this.zvierata = new ArrayList<>();
    }

    public Predmet getPredmety(int index) {
        return this.predmety.get(index);
    }

    public int getVelkostPredmetov() {
        return this.predmety.size();
    }

    public void odstranPredmet(int index) {
        this.predmety.remove(index);
    }

    public Zviera getZviera(int index) {
        return this.zvierata.get(index);
    }

    public int getVelkostZverov() {
        return this.zvierata.size();
    }

    public void odstranZviera(int index) {
        this.zvierata.remove(index);
    }

    public Kocka getPoslednaKocka() {
        return this.kocky.getLast();
    }

    /**
     * Metóda volá ostatné metódy, pomocou ktorých vytvorí všetky dane predmety
     */
    public void nacitanie() {
        this.nacitajKocky();
        this.nacitajPredmety();
        this.nacitajZvierata();
    }

    /**
     * Metóda nacitajKocky prejde riadky od daneho indexu riadku.
     * Z udajov, ktore sa nachadzaju v riadku vytvori instancie kociek.
     * Ked narazi na riadok s dalsim klucom, usporiada kocky v liste a skonci sa.
     */
    private void nacitajKocky() {
        for (int i = najdiIndexOf("Kocky") + 1; i < this.riadky.size(); i++) {
            if (this.riadky.get(i).getKluc() != null) {
                this.ulozenieKociek();
                return;
            }
            if (this.riadky.get(i).getX() != null && this.riadky.get(i).getY() != null && this.riadky.get(i).getDruh() != null) {
                Riadok riadok = this.riadky.get(i);
                this.kocky.add(new Kocka(Integer.parseInt(riadok.getX()), Integer.parseInt(riadok.getY()), DruhKocky.getDruhKocky(riadok.getDruh())));
            }
        }
    }

    /**
     * Ulozenie kociek podla xovej suradnice od najmensej po najvacsiu.
     */
    private void ulozenieKociek() {
        Kocka[] pom = new Kocka[this.kocky.size()];
        for (int i = 0; i < this.kocky.size(); i++) {
            pom[i] = this.kocky.get(i);
        }
        for (int i = 0; i < pom.length; i++) {
            for (int j = 0; j < pom.length - i - 1; j++) {
                if (pom[j].getX() > pom[j + 1].getX()) {
                    Kocka pomK = pom[j];
                    pom[j] = pom[j + 1];
                    pom[j + 1] = pomK;
                }
            }
        }
        this.kocky.clear();
        Collections.addAll(this.kocky, pom);
    }

    /**
     * Metoda prejde od daneho indexu riadky, vytvori z ich udajov predmety a ukonci sa ak narazi na riadok s inym klucom
     */
    private void nacitajPredmety() {
        for (int i = najdiIndexOf("Predmety") + 1;  i < this.riadky.size(); i++) {
            if (this.riadky.get(i).getKluc() != null) {
                return;
            }
            if (this.riadky.get(i).getX() != null && this.riadky.get(i).getY() != null && this.riadky.get(i).getDruh() != null) {
                Riadok riadok = this.riadky.get(i);
                switch (riadok.getDruh()) {
                    case "siska":
                        this.predmety.add(new Siska(Integer.parseInt(riadok.getX()), Integer.parseInt(riadok.getY())));
                        break;
                    case "minca":
                        this.predmety.add(new Minca(Integer.parseInt(riadok.getX()), Integer.parseInt(riadok.getY())));
                        break;
                    case "zivot":
                        this.predmety.add(new Zivot(Integer.parseInt(riadok.getX()), Integer.parseInt(riadok.getY())));
                        break;
                }
            }
        }
    }

    /**
     * Vytvorenie zvierat z udajov ulozenych v riadkoch. Zvieratam vygeneruje, ich zaciatocny smer pohybu.
     */
    private void nacitajZvierata() {
        Random random = new Random();
        for (int i = najdiIndexOf("Zvierata") + 1;  i < this.riadky.size(); i++) {
            if (this.riadky.get(i).getKluc() != null) {
                return;
            }
            if (this.riadky.get(i).getX() != null && this.riadky.get(i).getY() != null && this.riadky.get(i).getDruh() != null) {
                Riadok riadok = this.riadky.get(i);
                int s = random.nextInt(2);
                Smer smer;
                if (s == 0) {
                    smer = Smer.VPRAVO;
                } else {
                    smer = Smer.VLAVO;
                }
                switch (riadok.getDruh()) {
                    case "slimak":
                        this.zvierata.add(new Slimak(Integer.parseInt(riadok.getX()), Integer.parseInt(riadok.getY()), smer, this));
                        break;
                    case "chrobak":
                        this.zvierata.add(new Chrobak(Integer.parseInt(riadok.getX()), Integer.parseInt(riadok.getY()), smer, this));
                        break;
                    case "jezko":
                        this.zvierata.add(new Jezko(Integer.parseInt(riadok.getX()), Integer.parseInt(riadok.getY()), smer, this));
                        break;
                }
            }
        }
    }

    /**
     * Zistenie indexu riadku, na ktorom sa nachádza hľadané slovo
     * @param kluc - to co hľadáme
     * @return index
     */
    private int najdiIndexOf(String kluc) {
        for (int i = 0; i < this.riadky.size(); i++) {
            Riadok riadok = this.riadky.get(i);
            if (riadok.getKluc() != null && riadok.getKluc().equals(kluc) ) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Vykreslenie kociek, zvierat a predmetov.
     * @param batch batch
     */
    public void vykresli(SpriteBatch batch) {
        for (Kocka kocka : this.kocky) {
            kocka.vykresli(batch);
        }
        for (Zviera zviera : this.zvierata) {
            zviera.vykresli(batch);
        }
        for (Predmet predmet : this.predmety) {
            predmet.vykresli(batch);
        }
    }

    /**
     * Posun všetkých kociek v danom smere
     * @param smer - posunu
     */
    public void posun(Smer smer) {
        for (Kocka kocka : this.kocky) {
            kocka.posunKocku(smer);
        }
    }

    /**
     * Prehladávanie ArrayListu pomocou foreach-u a zisťovanie, či sa nejaká stena nachádza na daných súradniciach.
     *
     * @param xSuradnica x-ová súradnica miesta, kde by sa mala nachádzať
     * @param ySuradnica y-ová súradnica miesta, kde by sa mala nachádzať
     *
     * @return vráti výsledok hľadania
     */
    public boolean jeNaXY(int xSuradnica, int ySuradnica) {
        for (Kocka hladanaKocka : this.kocky) {
            if (xSuradnica >= hladanaKocka.getX() && xSuradnica <= hladanaKocka.getX() + hladanaKocka.getSirka()
                    && ySuradnica >= hladanaKocka.getY() && ySuradnica <= hladanaKocka.getY() + hladanaKocka.getVyska()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Posun plošiny smerom nadol, ak sa zhodujú súradnice
     * @param x - suradnica postavy
     * @param y - suradnica postavy
     * @return true ak sa posunie
     */
    public boolean stojiNaPlosine(float x, int y) {
        for (Kocka plosina : kocky) {
            if (x + 80 > plosina.getX() && x < plosina.getX() + plosina.getSirka() && y + 1 > plosina.getY() && y <= plosina.getY() + plosina.getVyska()) {
                plosina.padaniePlosiny();
                return true;
            }
        }
        return false;
    }

    /**
     * Metóda, ktora vymaže inštanciu kocky, ak je zbúrateľná a je na daných súradniciach
     * @param xSuradnica - postavy
     * @param ySuradnica - postavy
     * @return zburana kocka
     */
    public Kocka zburanieKocky(int xSuradnica, int ySuradnica) {
        for (Kocka hladanaKocka : this.kocky) {
            if (xSuradnica >= hladanaKocka.getX() && xSuradnica <= hladanaKocka.getX() + hladanaKocka.getSirka()
                    && ySuradnica == hladanaKocka.getY()  && (hladanaKocka.getDruh() == DruhKocky.ZBURATELNA || hladanaKocka.getDruh() == DruhKocky.SPREDMETOM)) {
                this.kocky.remove(hladanaKocka);
                return hladanaKocka;
            }
        }
        return null;
    }

    /**
     * Metoda zbura zburatelnu kocku ak sa jej dotkne siska.
     * @param x sisky
     * @param y sisky
     * @param vyska sisky
     * @return ci bola znicena
     */
    public Kocka zburanieSiskou(int x , int y, int vyska) {
        for (Kocka hladanaKocka : this.kocky) {
            if (x >= hladanaKocka.getX() && x <= hladanaKocka.getX() + hladanaKocka.getSirka()
                    && y + vyska >= hladanaKocka.getY() && y <= hladanaKocka.getY() + hladanaKocka.getVyska() && (hladanaKocka.getDruh() == DruhKocky.ZBURATELNA)) {
                this.kocky.remove(hladanaKocka);
                return hladanaKocka;
            }
        }
        return null;
    }

    public Zviera odstranZvieraZKocky(Kocka hladanaKocka) {

        for (int i = 0; i < this.zvierata.size(); i++) {
            if (this.zvierata.get(i).getX() >= hladanaKocka.getX() && this.zvierata.get(i).getX() <= hladanaKocka.getX() + hladanaKocka.getSirka() && this.zvierata.get(i).getY() == hladanaKocka.getY() + hladanaKocka.getVyska()) {
                Zviera zviera = this.zvierata.get(i);
                this.odstranZviera(i);
                return zviera;
            }
        }
        return null;
    }

    /**
     * Zisťovanie, či sme na začiatku mapy
     * @return true, ak sme na začiatku
     */
    public boolean jePrva() {
        return this.kocky.getFirst().getX() == 0;
    }

    /**
     * Zisťovanie, či sme na konci mapy
     * @return false, ak sme na konci
     */
    public boolean jePosledna() {
        return this.kocky.getLast().getX() + this.kocky.getLast().getSirka() == Gdx.graphics.getWidth();
    }
}
