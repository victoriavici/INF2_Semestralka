package sk.uniza.fri.vykreslovace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sk.uniza.fri.MojaHra;
import sk.uniza.fri.gui.Vyhra;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Trieda dedi od ScreenAdapter. Zpbrazuje sa vtedy, ked hrac vyhra.
 */
public class VykreslenieVyhra extends ScreenAdapter {
    private final Vyhra vyhra;
    private SpriteBatch batch;
    private final MojaHra mojaHra;

    public VykreslenieVyhra(MojaHra mojaHra, int skore) {
        this.mojaHra = mojaHra;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        this.vyhra = new Vyhra(skore);
    }

    @Override
    public void show() {
        this.batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        if (this.vyhra.neexistuje()) {
            this.mojaHra.setScreen(new Vykreslenie(this.mojaHra));
        }
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.batch.begin();
        this.batch.end();
    }

    @Override
    public void dispose() {
    }
}