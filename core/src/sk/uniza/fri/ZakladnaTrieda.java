package sk.uniza.fri;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Abstraktna trieda ZakladnaTrieda obsahuje atributy a metody, ktore vyuziva vacsina tried, ktore dedia z tejto triedy
 */
public abstract class ZakladnaTrieda {
    private int x;
    private int y;
    private int vyska;
    private int sirka;
    private Texture texture;

    /**
     * Inicializacia atributov
     * @param x ova suradnica
     * @param y ova suradnica
     * @param vyska vyska
     * @param sirka sirka
     * @param texture textura
     */
    public ZakladnaTrieda(int x, int y, int vyska, int sirka, Texture texture) {
        this.x = x;
        this.y = y;
        this.vyska = vyska;
        this.sirka = sirka;
        this.texture = texture;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVyska() {
        return vyska;
    }

    public void setVyska(int vyska) {
        this.vyska = vyska;
    }

    public int getSirka() {
        return sirka;
    }

    public void setSirka(int sirka) {
        this.sirka = sirka;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void vykresli(SpriteBatch batch) {
        batch.draw(this.getTexture(), this.getX(), this.getY());
    }
}

