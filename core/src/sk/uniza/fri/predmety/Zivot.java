package sk.uniza.fri.predmety;

import com.badlogic.gdx.graphics.Texture;

/**
 * Trieda zivot je potomkom triedy predmet.
 */
public class Zivot extends Predmet {

    public Zivot(int x, int y) {
        super(x, y, 30, 30, new Texture("srdce.png"));
    }
}
