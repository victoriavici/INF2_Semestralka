package sk.uniza.fri;

import com.badlogic.gdx.Game;
import sk.uniza.fri.vykreslovace.Vykreslenie;

/**
 * trieda MojaHra extenduje triedu Game
 */
public class MojaHra extends Game {
    /**
     * nastavenie screenu
     */
    @Override
    public void create() {
        setScreen(new Vykreslenie(this));
    }
}
