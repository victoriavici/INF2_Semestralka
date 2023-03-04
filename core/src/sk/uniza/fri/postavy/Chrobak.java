package sk.uniza.fri.postavy;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.Smer;
import sk.uniza.fri.mapa.MapaZoSuboru;

/**
 * Trieda Chrobak je potomkom triedy Zvierata. Predstavuje maleho chrobacika, ktory sa pohybuje po mapke,
 * ak na neho hrac skoci, zabije ho, ak nan neskoci ale narazia na seba, chrobak ublizi hracovi.
 */
public class Chrobak extends Zviera {
    /**
     * Inicializacia atributov a vytvorenie instancie.
     * @param x - suradnica
     * @param y -suradnica
     * @param smer pohybu
     * @param mapa po ktorej sa pohybuje
     */
    public Chrobak(int x, int y, Smer smer, MapaZoSuboru mapa) {
        super(x, y, 30, 42, new Texture("chrobak.png"), new Texture("chrobakR.png"), mapa, 1, smer, 2, 100);
    }

    /**
     * Metoda, ktora vyhodnocuje, ci doslo k  zrameniu hraca na zaklade zadanych suradnic.
     * @param xZ - zaciatocna xova suradnica
     * @param xK - koncova xova suradnica
     * @param y - suradnica
     * @return ci doslo ku kolizii
     */
    @Override
    public boolean zranenieHraca(int xZ, int xK, int y) {
        return xZ <= this.getX() + this.getSirka() && xK >= this.getX() + this.getSirka() && y < this.getY() + this.getVyska() && y >= this.getY();
    }
}

