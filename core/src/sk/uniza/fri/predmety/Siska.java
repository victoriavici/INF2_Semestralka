package sk.uniza.fri.predmety;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.Smer;

/**
 * Trieda Siska je potomkom triedy Predmet. Da sa hodit v smere hraca.
 */
public class Siska extends Predmet {
    private Smer smer = null;

    /**
     * Inicializacia atributov
     * @param x ova suradnica
     * @param y ova suradnica
     */
    public Siska(int x, int y) {
        super(x, y, 20, 16, new Texture("siska.png"));
    }

    public Smer getSmer() {
        return this.smer;
    }

    public void setSmer(Smer smer) {
        this.smer = smer;
    }

    /**
     * Posunutie sisky v danom smere
     * @param smer posunu
     * @return ci sa posunula
     */
    public boolean posun(Smer smer) {
        if (smer == this.smer) {
            this.setX(this.getX() + 6 * this.smer.getPosunX());
        } else if (this.smer != Smer.NIKAM) {
            this.setX(10 * this.smer.getPosunX() + this.getX());
        } else {
            this.setX(this.getX());
        }

        return this.getX() <= Gdx.graphics.getWidth() && this.getX() >= 0;
    }
}


