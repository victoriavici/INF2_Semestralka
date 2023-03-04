package sk.uniza.fri.postavy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.Smer;
import sk.uniza.fri.mapa.DruhKocky;
import sk.uniza.fri.mapa.Kocka;
import sk.uniza.fri.mapa.MapaZoSuboru;
import sk.uniza.fri.predmety.Minca;
import sk.uniza.fri.predmety.Siska;
import sk.uniza.fri.predmety.Zivot;

import java.util.LinkedList;

/**
 * Trieda Hrac je potomkom abstraktnej triedy Postava. Predstavuje postavicku, pomocou ktorej sa hrac pohybuje po mape.
 * Zbiera predmety, zabija zvieratka a jeho cielom je dostat sa na koniec mapy.
 */
public class Hrac extends Postava {
    private int yStare = 0;
    private int vSkoku = 0;
    private int zraneny = 0;
    private int pocetMinci = 0;
    private int skore = 0;
    private float cas = 0;
    private int index = 0;
    private final Texture[]  texturky = {new Texture("Karkulka.png"), new Texture("Karkulka1.png"), new Texture("Karkulka2.png"),
                                         new Texture("hracL.png"), new Texture("hracL1.png"), new Texture("hracL2.png")};
    private final LinkedList<Siska> sisky = new LinkedList<>();

    /**
     * Inicializacia atributov a vytvorenie instancie.
     * @param x suradnica
     * @param y suradnica
     * @param mapa po ktorej sa pohybu
     */
    public Hrac(int x, int y, MapaZoSuboru mapa) {
        super(x, y, 130, 52, new Texture("hracL.png"), new Texture("Karkulka.png"), mapa, 1, Smer.VPRAVO);
    }

    public Texture getTexturky(int i) {
        return texturky[i];
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getZraneny() {
        return this.zraneny;
    }

    public int getPocetMinci() {
        return pocetMinci;
    }

    public void setPocetMinci(int pocetMinci) {
        this.pocetMinci = pocetMinci;
    }

    public int getSkore() {
        return skore;
    }

    public void setSkore(int skore) {
        this.skore = skore;
    }

    public int getPocetSisiek() {
        return this.sisky.size();
    }

    public Siska getSiska() {
        return this.sisky.removeLast();
    }

    public int getvSkoku() {
        return vSkoku;
    }

    public void setvSkoku(int vSkoku) {
        this.vSkoku = vSkoku;
    }

    @Override
    public void pohyb(Smer smer) {
        if (this.vSkoku == 0 && smer == Smer.HORE && !this.koliziaSoZburatelnymi()
                && (this.getMapa().jeNaXY(this.getX() + this.getSirka() - 2, this.getY()) || this.getMapa().jeNaXY(this.getX() + 2, this.getY()))) {
            this.vSkoku = 82;
        }
        if (smer == Smer.DOLE || this.koliziaSoZburatelnymi()) {
            this.vSkoku = 0;
        }
        this.yStare = this.getY();
        if (this.vSkoku > 0 && !this.koliziaSoZburatelnymi()) {
            this.setY(this.getY() + 2);
            this.vSkoku--;
        } else if (!this.getMapa().jeNaXY(this.getX() + this.getSirka() - 2, this.getY()) && !this.getMapa().jeNaXY(this.getX() + 2, this.getY())) {
            this.setY(this.getY() - 2);
        }
    }

    /**
     * Posun hraca v pripade, ze uz je koniec mapy.
     * @param smer posunu
     */
    public void posun(Smer smer) {
        if (this.getX() + smer.getPosunX() >= 0 && smer == Smer.VLAVO && !this.koliziaSoZburatelnymi()) {
            this.setX(this.getX() + 2 * smer.getPosunX());
        } else if (this.getX() + this.getSirka() + smer.getPosunX() <= Gdx.graphics.getWidth() && smer == Smer.VPRAVO && !this.koliziaSoZburatelnymi()) {
            this.setX(this.getX() + 2 * smer.getPosunX());
        } else {
            this.pohyb(smer);
        }
    }

    /**
     * Metóda zistujuca, ci hrac narazil na kocku s predmetom alebo na obycajnu zburatlenu kocku.
     * Dana kocka sa znici, a ak obsahovala nejaky predmet, pripocita ho k ostatnym predmetom hraca.
     * @return ci zbural kocku
     */
    private boolean koliziaSoZburatelnymi() {
        if (this.getvSkoku() != 0) {
            for (int i = this.getX() + 2; i <= this.getX() + this.getSirka() - 2; i++) {
                Kocka kocka = this.getMapa().zburanieKocky(i, this.getY() + this.getVyska());
                if (kocka != null && kocka.getDruh() == DruhKocky.SPREDMETOM) {
                    if (kocka.getPredmet() instanceof Zivot) {
                        if (this.getZivot() == 3) {
                            this.setPocetMinci(this.getPocetMinci() + 1);
                        } else {
                            this.setZivot(this.getZivot() + 1);
                        }
                    } else if (kocka.getPredmet() instanceof Siska) {
                        this.sisky.add((Siska)kocka.getPredmet());
                    } else if (kocka.getPredmet() instanceof Minca) {
                        this.setPocetMinci(this.getPocetMinci() + 1);
                    }
                    return true;
                }
                if (kocka != null) {
                    Zviera zviera = this.getMapa().odstranZvieraZKocky(kocka);
                    if (zviera != null) {
                        this.skore += zviera.getBody() * 90 / 100;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metoda zistujuca, ci hrac narazil na kocku
     * @param smer posunu
     * @return ci narazili
     */
    public boolean kolizia(Smer smer) {
        for (int i = this.getX() + 2 * smer.getPosunX(); i < this.getX() + 2 * smer.getPosunX() + this.getSirka(); i++) {
            for (int j = this.getY() + 2 * smer.getPosunY() + 1; j < this.getY() + 2 * smer.getPosunY() + this.getVyska(); j++) {
                if (this.getMapa().jeNaXY(i, j)) {
                    this.setvSkoku(0);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Zistenie, či hráč skočil na zviera
     * @param xK - xová koncova suradnica zvierata
     * @param yK - yova koncova suradnica zvierata
     * @return ci hrac skocil na zver
     */
    public boolean skokNaZver(int xK, int xZ, int yK) {
        return (this.getX() <= xK && this.getX() + this.getSirka() >= xZ) && this.getY() == yK && this.yStare >= this.getY();
    }

    /**
     * Ubratie života a nastavenie zranenia hráča.
     */
    public void koliziaSoZverom() {
        if (this.zraneny == 0) {
            this.zraneny = 60;
            this.uberZivot();
        }
    }

    /**
     * Odpočitavanie toho, koľko bude hráč zranený.
     */
    public void odpocetZraneny() {
        if (this.zraneny > 0) {
            if (this.zraneny % 6 == 1 || this.zraneny % 6 == 2  || this.zraneny % 6 == 3) {
                this.setTexture(null);
            } else {
                this.nastavenieTextur(0.05f);
            }
            this.zraneny--;
        } else {
            if (this.getTexture() == null) {
                this.nastavenieTextur(0.1f);
            }
        }
    }

    /**
     * Nastevenie textury hráča podľa toho, či stojí, skáče alebo beží.
     * @param delta pridava sa ku času
     */
    public void nastavenieTextur(float delta) {
        this.cas = this.cas + delta;
        if (this.vSkoku != 0 || !this.getMapa().jeNaXY(this.getX() + this.getSirka() - 2, this.getY()) && !this.getMapa().jeNaXY(this.getX() + 2, this.getY())) {
            if (this.getSmer() == Smer.VPRAVO) {
                this.setTexture(this.texturky[0]);
                this.index = 0;
            } else {
                this.setTexture(this.texturky[3]);
                this.index = 3;
            }
            this.cas = 0.3f;
        } else if (this.cas > 0.3) {
            if (this.getSmer() == Smer.VPRAVO) {
                if (this.index == 0 || this.index == 1) {
                    this.index = 2;
                } else {
                    this.index = 1;
                }
            } else if (this.getSmer() == Smer.VLAVO) {
                if (this.index == 4) {
                    this.index = 5;
                } else {
                    this.index = 4;
                }
            }
            this.setTexture(this.texturky[index]);
            this.cas = 0;
        }
    }

    /**
     * Pridanie šišky do arraylistu.
     */
    public void pridajSisku() {
        this.sisky.add(new Siska(this.getX() + getSirka(), this.getY() + 35));
    }
}
