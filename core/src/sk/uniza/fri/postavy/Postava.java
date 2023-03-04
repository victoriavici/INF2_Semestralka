package sk.uniza.fri.postavy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sk.uniza.fri.Smer;
import sk.uniza.fri.ZakladnaTrieda;
import sk.uniza.fri.mapa.MapaZoSuboru;

/**
 * Abstraktná trieda Postava obsahuje atributy a metody, ktore pozivaju jej potomkovia.
 */
public abstract class Postava extends ZakladnaTrieda {
    private final Texture[] textury;
    private final MapaZoSuboru mapa;
    private int zivot;
    private Smer smer;

    /**
     * Iicializovanie atributov na zaklade zadanych parametrov
     * @param x -ova suradnica
     * @param y -ova suradnica
     * @param textureL textura pri pohybe vlavo
     * @param textureR textura pri pohybe vpravo
     * @param mapa na ktorej sa nachazda
     * @param sirka postavy
     * @param vyska postavy
     * @param zivot pocet zivotov
     * @param smer pohybu
     */
    public Postava(int x, int y, int vyska, int sirka, Texture textureL, Texture textureR, MapaZoSuboru mapa, int zivot, Smer smer) {
        super(x, y, vyska, sirka, textureL);
        this.textury = new Texture[]{textureL, textureR};
        this.mapa = mapa;
        this.zivot = zivot;
        this.smer = smer;
    }

    public Texture getTextury(int i) {
        return this.textury[i];
    }

    public int getZivot() {
        return zivot;
    }

    public void setZivot(int zivot) {
        this.zivot = zivot;
    }

    public MapaZoSuboru getMapa() {
        return mapa;
    }

    public Smer getSmer() {
        return smer;
    }

    public void setSmer(Smer smer) {
        this.smer = smer;
    }

    public void vykresli(SpriteBatch batch) {
        if (this.getTexture() != null) {
            batch.draw(this.getTexture(), this.getX(), this.getY());
        }
    }

    public abstract void pohyb(Smer smer);

    public void uberZivot() {
        this.setZivot(this.getZivot() - 1);
    }
}
