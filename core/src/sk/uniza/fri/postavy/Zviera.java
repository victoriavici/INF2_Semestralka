package sk.uniza.fri.postavy;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.Smer;
import sk.uniza.fri.mapa.MapaZoSuboru;

/**
 * Abstraktná trieda Zviera dedi od abstarktnej triedy Postava. Definuje sa v nej pohyb zvierat.
 */
public abstract class Zviera extends Postava {

    private final int rychlost;
    private final int body;

    /**
     * Inicializacia atributov na zaklade parameterov
     * @param x -ova suradnica
     * @param y - ova suradnica
     * @param textureL - textura pri pohybe dolava
     * @param textureR - textura pri pohybe doprava
     * @param mapa po ktorej sa pohybuje
     * @param sirka zvierata
     * @param vyska zvierata
     * @param smer pohybu
     * @param rychlost pohybu
     * @param zivot ich pocet
     * @param body ktore ziskame po zabiti
     */
    public Zviera(int x, int y, int vyska, int sirka, Texture textureL, Texture textureR, MapaZoSuboru mapa, int zivot, Smer smer, int rychlost, int body) {
        super(x, y, vyska, sirka, textureL, textureR, mapa, zivot, smer);
        this.rychlost = rychlost;
        this.body = body;
        if (this.getSmer() == Smer.VPRAVO) {
            this.setTexture(this.getTextury(1));
        }
    }

    public int getRychlost() {
        return this.rychlost;
    }

    public int getBody() {
        return body;
    }

    /**
     * Metoda kontroloje ci sa moze a ako sa ma zmenit xova suradnica zvierata na to, aby sa posunolo
     * @param smer pohybu
     */
    @Override
    public void pohyb(Smer smer) {
        if (smer != this.getSmer() && smer != Smer.NIKAM ) {
            if (this.getSmer() == Smer.VLAVO && this.mozePosun()) {
                this.setX(this.getX() - this.getRychlost() + 2 * this.getSmer().getPosunX());
            } else if (this.getSmer() == Smer.VPRAVO && this.mozePosun()) {
                this.setX(this.getX() + this.getRychlost() + 2 * this.getSmer().getPosunX());
            } else {
                this.zmenaSmeru();
            }
        } else if (smer == this.getSmer() && this.getRychlost() == 1) {
            if (smer == Smer.VLAVO && this.mozePosun()) {
                this.setX(this.getX() - this.getSmer().getPosunX());
            } else if (smer == Smer.VPRAVO && this.mozePosun()) {
                this.setX(this.getX() - this.getSmer().getPosunX());
            } else {
                this.zmenaSmeru();
            }
        } else if (smer == Smer.NIKAM) {
            if (this.getSmer() == Smer.VLAVO && this.mozePosun()) {
                this.setX(this.getX() + this.getRychlost() * this.getSmer().getPosunX());
            } else if (this.getSmer() == Smer.VPRAVO && this.mozePosun()) {
                this.setX(this.getX() + this.getRychlost() * this.getSmer().getPosunX());
            } else {
                this.zmenaSmeru();
            }
        } else {
            if ((this.getSmer() == Smer.VLAVO && (this.getMapa().jeNaXY(this.getX(), this.getY() + 1) || !this.getMapa().jeNaXY(this.getX(), this.getY())))
                    || (this.getSmer() == Smer.VPRAVO && (this.getMapa().jeNaXY(this.getX() + this.getSirka(), this.getY() + 1) || !this.getMapa().jeNaXY(this.getX() + this.getSirka(), this.getY())))) {
                this.zmenaSmeru();
            }
        }
    }

    /**
     * Metoda zmni smer pohybu a nastavi k tomu adekvatnu texturu
     */
    private void zmenaSmeru() {
        if (this.getSmer() == Smer.VLAVO) {
            this.setSmer(Smer.VPRAVO);
            this.setTexture(this.getTextury(1));
        } else {
            this.setSmer(Smer.VLAVO);
            this.setTexture(this.getTextury(0));
        }
    }

    /**
     * Metoda zistuje, ci sa zviera este moze posunut v danom smere
     * @return ci je mozny posun
     */
    private boolean mozePosun() {
        if (this.getSmer() == Smer.VLAVO && !this.getMapa().jeNaXY(this.getX(), this.getY() + 1) && this.getMapa().jeNaXY(this.getX(), this.getY())) {
            return true;
        } else {
            return this.getSmer() == Smer.VPRAVO && !this.getMapa().jeNaXY(this.getX() + this.getSirka(), this.getY() + 1) && this.getMapa().jeNaXY(this.getX() + this.getSirka(), this.getY());
        }
    }

    /**
     * Metoda vyhodnocuje, ci prislo k zraneniu zvierata
     * @param xZ - ova zaciatocna suradnica
     * @param xK - koncova xova suradnica
     * @param y - ova suradnica
     * @return ci doslo k zraneniu
     */
    public boolean zranenieZvierata(int xZ, int xK, int y) {
        return xZ <= this.getX() + this.getSirka() && xK >= this.getX() + this.getSirka() && y < this.getY() + this.getVyska() && y >= this.getY();
    }

    public abstract boolean zranenieHraca(int xZ, int xK, int y);

    public void posun(Smer smer) {
        this.pohyb(smer);
    }
}

