package sk.uniza.fri.vykreslovace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sk.uniza.fri.MojaHra;
import sk.uniza.fri.gui.Prehra;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Trieda VykreslenieKoniecHry je potomkom triedy ScreenAdapter.
 * Vykresuluje hru po tom ako hrac prehra.
 */
public class VykreslenieKoniecHry extends ScreenAdapter {
    private final Prehra prehra;
    private SpriteBatch batch;
    private final MojaHra mojaHra;

    public VykreslenieKoniecHry(MojaHra mojaHra) {
        this.mojaHra = mojaHra;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        this.prehra = new Prehra();
    }

    @Override
    public void show() {
        this.batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        if (this.prehra.neexistuje()) {
            this.mojaHra.setScreen(new Vykreslenie(this.mojaHra));
        }
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.batch.begin();
        this.batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
