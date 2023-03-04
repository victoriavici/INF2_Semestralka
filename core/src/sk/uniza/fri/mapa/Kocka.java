package sk.uniza.fri.mapa;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.Smer;
import sk.uniza.fri.ZakladnaTrieda;
import sk.uniza.fri.predmety.Minca;
import sk.uniza.fri.predmety.Predmet;
import sk.uniza.fri.predmety.Siska;
import sk.uniza.fri.predmety.Zivot;

import java.util.Random;

/**
 * Trieda Kocka je potomkom triedy ZakladnaTrieda. Slúži na vytváranie a ovládanie kociek,
 * ktoré sa vyskytujú v hre. Každý druh kociek má iné vlastnosti.
 */
public class Kocka extends ZakladnaTrieda {
    private final DruhKocky druh;
    private Predmet predmet = null;

    /**
     * Inicializácia atribútov a vytvorenie inštancie.
     * Podľa druhu sa priradí inštanii daná textúra, výška, šírka a predmet, ktorý sa môže v nej nachádzať.
     *
     * @param x-ová súradnica
     * @param y-ova súradnica
     * @param druh kocky
     */
    public Kocka(int x, int y, DruhKocky druh) {
        super(x, y, 50, 50, new Texture("mapka.png"));
        this.druh = druh;
        if (this.druh == DruhKocky.PADAJUCA) {
            this.setTexture(new Texture("padajuca.png"));
            this.setSirka(80);
            this.setVyska(16);
        } else if (DruhKocky.ZBURATELNA == this.druh) {
            this.setTexture(new Texture("Zkocka.png"));
        } else if (this.druh == DruhKocky.NEZBURATELNA) {
            this.setTexture(new Texture("mapka.png"));
        } else {
            this.setTexture(new Texture("KockaZlata.png"));
            Predmet[] predmety = new Predmet[]{new Zivot(this.getX(), this.getY()), new Siska(this.getX(), this.getY()), new Minca(this.getX(), this.getY())};
            Random generator = new Random();
            this.predmet = predmety[generator.nextInt(3)];
        }

    }

    public DruhKocky getDruh() {
        return this.druh;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    /**
     * Metóda na zmenu x-ovej súradnice >> posun kocky
     * @param smer posunu kocky
     */
    public void posunKocku(Smer smer) {
        if (smer == Smer.VLAVO) {
            this.setX(this.getX() + 2);
        } else {
            this.setX(this.getX() - 2);
        }
    }

    /**
     * Posun padajúcej plošiny smerom dole.
     */
    public void padaniePlosiny() {
        if (this.getDruh() == DruhKocky.PADAJUCA) {
            this.setY(this.getY() - 2);
        }
    }
}
