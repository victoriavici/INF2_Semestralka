package sk.uniza.fri.predmety;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.Smer;
import sk.uniza.fri.ZakladnaTrieda;

/**
 * Abstraktná trieda Predmet je potomkom triedy ZakladnaTrieda.
 * Definuje sa v nej pohyb a zobranie z mapy.
 */
public abstract class Predmet extends ZakladnaTrieda {
    /**
     * Inicializacia atributov
     * @param x - ova suradnica
     * @param y -ova suradnica
     * @param vyska predmetu
     * @param sirka predmetu
     * @param texture textura predmetu
     */
    public Predmet(int x, int y, int vyska, int sirka, Texture texture) {
        super(x, y, vyska, sirka, texture);
    }

    /**
     * Metoda porovnava dane parametre s atributmi predmetu.
     * @param x - ova suradnica
     * @param y - ova suradnica
     * @param s sirka
     * @param v vyska
     * @return true ak su dane parametre z daneho rozsahu
     */
    public boolean zober(int x, int y, int s, int v) {
        if ((x <= this.getX() + this.getSirka() && x + s >= this.getX() + this.getSirka())
                && y <= this.getY() && (y + v >= this.getY())) {
            System.out.println("mam ta");
            return true;
        }
        return false;
    }

    /**
     * Posunutie predmetov pri posune mapy.
     * @param smer pohybu
     */
    public void pohyb(Smer smer) {
        this.setX(this.getX() - 2 * smer.getPosunX());
    }

}
