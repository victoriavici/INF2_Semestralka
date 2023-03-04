package sk.uniza.fri.predmety;

import com.badlogic.gdx.graphics.Texture;

/**
 * Trieda Minca je potomkom triedy Predmet.
 */
public class Minca extends Predmet {
    /**
     * Inicializacia atributov
     * @param x ova suradnica
     * @param y ova suradnica
     */
    public Minca(int x, int y) {
        super(x, y, 20, 20, new Texture("minca.png"));
    }
}
