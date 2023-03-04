package sk.uniza.fri.postavy;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.Smer;
import sk.uniza.fri.mapa.MapaZoSuboru;

/**
 * Trieda SLimak je potomkom triedy zviera. Predstavuje slimaka, ktory sa za urcitym okolnosti vie skryt do ulity,
 * zranit hraca a pohybovat sa po mape.
 */
public class Slimak extends Zviera {
    private boolean schovany;
    private int pocSchovany;

    /**
     * Inicializacia atributov a vytvorenie instancie
     * @param x - ova suradnica
     * @param y - ova suradnica
     * @param smer pohybu
     * @param mapa po ktorej sa pohybuje
     */
    public Slimak(int x, int y, Smer smer, MapaZoSuboru mapa) {
        super(x, y, 46, 46, new Texture("slimak.png"), new Texture("slimakR.png"), mapa, 2, smer, 1, 200);
        this.schovany = false;
        this.pocSchovany = 0;
    }

    /**
     * Metoda uberie zivot slimakovi. Ak ma dva zivoty skryje sa do ulity.
     */
    @Override
    public void uberZivot() {
        if (!this.schovany) {
            this.schovany = true;
            this.pocSchovany = 300;
            this.setTexture(new Texture("slimakSchovany.png"));
        }
        this.setZivot(this.getZivot() - 1);
    }

    /**
     * Metoda urcuje, ci slimak hraca zranil na zaklade danych parametrov. Slimak nemoze ublizit, ak je schovany v ulite
     * @param xZ - ova zaciatocna suradnica
     * @param xK - ova koncova suradnica
     * @param y - ova suradnica
     * @return ci doslo k zraneniu
     */
    @Override
    public boolean zranenieHraca(int xZ, int xK, int y) {
        return !this.schovany && xZ <= this.getX() + this.getSirka() && xK >= this.getX() + this.getSirka() && y < this.getY() + this.getVyska() && y >= this.getY();
    }

    /**
     * Posun slimaka ak nie je schovany v ulite
     * @param smer pohybu
     */
    @Override
    public void posun(Smer smer) {
        if (this.schovany && smer == Smer.NIKAM) {
            this.vylez();
        } else if (this.schovany) {
            this.setX(this.getX() - 2 * smer.getPosunX());
            this.vylez();
        } else {
            this.pohyb(smer);
        }
    }

    /**
     * Slimak je schovany na urcity cas. Metoda sa stara o to aby z nej vyliezol po urcitom case a nastavil mu naspat zivot
     */
    private void vylez() {
        this.pocSchovany--;
        if (this.pocSchovany == 0) {
            if (this.getSmer() == Smer.VLAVO) {
                this.setTexture(this.getTextury(0));
            } else {
                this.setTexture(this.getTextury(1));
            }
            this.schovany = false;
            this.setZivot(2);
        }
    }
}
