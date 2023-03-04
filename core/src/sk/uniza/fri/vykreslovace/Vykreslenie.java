package sk.uniza.fri.vykreslovace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sk.uniza.fri.Hra;
import sk.uniza.fri.MojaHra;
import sk.uniza.fri.Smer;

/**
 * Trieda Vykrelovac je potomkom triedy ScreenAdapter. Sluzi na vykreslenie hry pocas toho, ako hra bezi.
 */
public class Vykreslenie extends ScreenAdapter {
    private SpriteBatch batch;
    private Hra hra;
    private BitmapFont text;
    private final MojaHra mojaHra;

    /**
     * Inicializacia
     * @param mojaHra hra
     */
    public Vykreslenie(MojaHra mojaHra) {
        this.mojaHra = mojaHra;
    }

    @Override
    public void show() {
        this.batch = new SpriteBatch();
        this.hra = new Hra();
        this.text = new BitmapFont();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0.6f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            this.hra.posunHraca(Smer.HORE);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.hra.posunHraca(Smer.DOLE);
        } else {
            this.hra.posunHraca(Smer.NIKAM);
        }
        Smer smer;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (this.hra.posunMapy(Smer.VLAVO, delta)) {
                this.hra.posunZvieratAPredmetov(Smer.VLAVO);
                smer = Smer.VLAVO;
            } else {
                this.hra.posunZvieratAPredmetov(Smer.NIKAM);
                smer = Smer.NIKAM;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (this.hra.posunMapy(Smer.VPRAVO, delta)) {
                this.hra.posunZvieratAPredmetov(Smer.VPRAVO);
                smer = Smer.VPRAVO;
            } else {
                this.hra.posunZvieratAPredmetov(Smer.NIKAM);
                smer = Smer.NIKAM;
            }
        } else {
            this.hra.posunZvieratAPredmetov(Smer.NIKAM);
            this.hra.posunMapy(Smer.NIKAM, delta);
            smer = Smer.NIKAM;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.hra.setHodenaSiska();
        }
        this.hra.posunHodenejSisky(smer);

        this.hra.rozlisenieKolizie();

        this.batch.begin();
        this.hra.vykresli(this.batch);
        this.text.setColor(1, 1, 1, 1);
        this.text.draw(this.batch, this.hra.vypis(), 10, 490);

        this.batch.end();
        if (this.hra.prehra()) {
            this.mojaHra.setScreen(new VykreslenieKoniecHry(this.mojaHra));
        }
        if (this.hra.vyhra()) {
            this.hra.setVyhral();
            this.mojaHra.setScreen(new VykreslenieVyhra(this.mojaHra, this.hra.skore()));
        }
    }

    @Override
    public void dispose() {
    }
}