package sk.uniza.fri.postavy;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.Smer;
import sk.uniza.fri.mapa.MapaZoSuboru;

/**
 * Trieda Jezko je potomkom abstraktnej triedy Zviera. Predstavuje zviera, na ktore ked hrac skoci, ublizi mu. Jezka je mozne zabit len hodenim sisky po nom.
 */
public class Jezko extends Zviera {

    /**
     * Vytvorenie a inicializacia atributov
     * @param x - suradnica
     * @param y - suradnica
     * @param smer pohybu
     * @param mapa po ktorej sa pohybuje
     */
    public Jezko(int x, int y, Smer smer, MapaZoSuboru mapa) {
        super(x, y, 30, 50, new Texture("jezkoL.png"), new Texture("jezkoR.png"), mapa, 1, smer, 1, 300);
    }

    /**
     * Vyhodnotenie ci doslo k zraneniu hraca na zaklade danych parametrov
     * @param xZ - x zaciatocna suradnica
     * @param xK - x koncova suradnica
     * @param y - suradnica
     * @return ak sa suradnice zhoduju s atributmi jezka vrati true
     */
    @Override
    public boolean zranenieHraca(int xZ, int xK, int y) {
        return xZ <= this.getX() + this.getSirka() && xK >= this.getX() + this.getSirka() && y < this.getY() + this.getVyska() && y >= this.getY();
    }
}
