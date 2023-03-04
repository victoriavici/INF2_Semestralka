package sk.uniza.fri;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import sk.uniza.fri.mapa.Kocka;
import sk.uniza.fri.mapa.MapaZoSuboru;
import sk.uniza.fri.postavy.Hrac;
import sk.uniza.fri.postavy.Jezko;
import sk.uniza.fri.postavy.Zviera;
import sk.uniza.fri.predmety.Minca;
import sk.uniza.fri.predmety.Siska;
import sk.uniza.fri.predmety.Zivot;
import java.io.IOException;

/**
 * Trieda Hra spaja triedy Hrac, MapaZoSuboru a riadi vsetky akcie vykonavane pocas hry
 */
public class Hra {
    private final Hrac hrac;
    private MapaZoSuboru mapa;
    private final Smer smer;
    private Siska hodenaSiska = null;
    private boolean vyhral = false;

    /**
     * Inicializacia atributov
     */
    public Hra() {
        try {
            this.mapa = new MapaZoSuboru();
            this.mapa.nacitanie();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.hrac = new Hrac(150, 50, this.mapa);
        this.smer = Smer.VLAVO;
    }

    /**
     * Vykreslenie mapy, hraca a sisky, ak je hodena.
     * @param batch .
     */
    public void vykresli(SpriteBatch batch) {
        this.mapa.vykresli(batch);
        this.hrac.vykresli(batch);

        if (this.hodenaSiska != null) {
            this.hodenaSiska.vykresli(batch);
        }

    }

    /**
     * Posunutie hraca smerom hore.
     * @param smer pohybu
     */
    public void posunHraca(Smer smer) {
        if (this.stojiNaPlosine() && smer == Smer.HORE) {
            this.hrac.setvSkoku(80);
        }
        this.hrac.pohyb(smer);
    }

    /**
     * Metoda na zistenie, ci hrac stoji na plosine
     * @return true ak na nej stoji
     */
    private boolean stojiNaPlosine() {
        return this.mapa.stojiNaPlosine(this.hrac.getX(), this.hrac.getY());
    }

    /**
     * Posunutie mapy v danom smere
     * @param smer posunu
     * @param delta cas
     * @return ci doslo k posunu mapy
     */
    public boolean posunMapy(Smer smer, float delta) {
        if (this.hrac.kolizia(smer)) {
            if (!(this.mapa.jePosledna() && smer == Smer.VPRAVO) && !(this.mapa.jePrva() && smer == Smer.VLAVO) && this.hrac.getX() == 150) {
                if ((smer == Smer.VPRAVO || smer == Smer.VLAVO) && this.hrac.kolizia(smer)) {
                    this.mapa.posun(smer);
                    this.hrac.setSmer(smer);
                    this.hrac.nastavenieTextur(delta);
                    return true;
                }
            } else if ((smer == Smer.VPRAVO || smer == Smer.VLAVO) && this.hrac.kolizia(smer)) {
                this.hrac.posun(smer);
                this.hrac.setSmer(smer);
                this.hrac.nastavenieTextur(delta);
                return false;
            }
        }

        if (this.hrac.getSmer() == Smer.VLAVO) {
            this.hrac.setTexture(this.hrac.getTexturky(3));
            this.hrac.setIndex(3);
        } else {
            this.hrac.setTexture(this.hrac.getTexturky(0));
            this.hrac.setIndex(0);
        }
        return false;
    }

    /**
     * Posun zvierat a predmetov v danom smere
     * @param smer posunu
     */
    public void posunZvieratAPredmetov(Smer smer) {
        for (int i = 0; i < this.mapa.getVelkostZverov(); i++) {
            this.mapa.getZviera(i).posun(smer);
        }
        for (int i = 0; i < this.mapa.getVelkostPredmetov(); i++) {
            this.mapa.getPredmety(i).pohyb(smer);
        }
    }


    /**
     * Posunutie hodenej sisky v danom smere
     * @param smer posunu
     */
    public void posunHodenejSisky(Smer smer) {
        if (this.hodenaSiska != null) {
            if (!this.hodenaSiska.posun(smer) || this.koliziaSisky()) {
                this.hodenaSiska = null;
            }
        }
    }

    /**
     * Metoda zistuje ci doslo ku nejakej kolizii
     */
    public void rozlisenieKolizie() {
        this.koliziaSPredmetom();
        if (!this.zabitieZveru()) {
            this.koliziaSoZverom();
        }
    }

    /**
     * Metoda na zistenie, ci siska narazila na zviera alebo na kocku.
     * V pripade ak narazila na zviera, zabije zviera a prida hracovi body do skore.
     * @return ci doslo ku kolizii
     */
    private boolean koliziaSisky() {
        for (int i = 0; i < this.mapa.getVelkostZverov(); i++) {
            if (this.mapa.getZviera(i).zranenieZvierata(this.hodenaSiska.getX(), this.hodenaSiska.getX() + this.hodenaSiska.getSirka(), this.hodenaSiska.getY())) {
                this.mapa.getZviera(i).uberZivot();
                if (this.mapa.getZviera(i).getZivot() == 0) {
                    this.hrac.setSkore(this.hrac.getSkore() + this.mapa.getZviera(i).getBody());
                    this.mapa.odstranZviera(i);
                }
                return true;
            }
        }
        Kocka kocka = null;
        if (this.smer == Smer.VPRAVO) {
            kocka = this.mapa.zburanieSiskou(this.hodenaSiska.getX() + this.hodenaSiska.getSirka(), this.hodenaSiska.getY(), this.hodenaSiska.getVyska());
        } else if (this.smer == Smer.VLAVO) {
            kocka = this.mapa.zburanieSiskou(this.hodenaSiska.getX(), this.hodenaSiska.getY(), this.hodenaSiska.getVyska());
        }
        if (kocka != null) {
            Zviera zviera = this.mapa.odstranZvieraZKocky(kocka);
            if (zviera != null) {
                this.hrac.setSkore(this.hrac.getSkore() + zviera.getBody() * 90 / 100) ;
            }
            return true;
        }
        return (this.smer == Smer.VPRAVO && this.mapa.jeNaXY(this.hodenaSiska.getX() + this.hodenaSiska.getSirka(), this.hodenaSiska.getY()))
                || (this.smer == Smer.VLAVO && this.mapa.jeNaXY(this.hodenaSiska.getX(), this.hodenaSiska.getY()));
    }

    /**
     * Zistuje, ci doslo ku kolizii hraca s nejakym zvieratom, ak hrac este nie je zraneny.
     */
    private void koliziaSoZverom() {
        this.hrac.odpocetZraneny();
        for (int i = 0; i < this.mapa.getVelkostZverov(); i++) {
            if (this.hrac.getZraneny() == 0 && this.mapa.getZviera(i).zranenieHraca(this.hrac.getX() + 10, this.hrac.getX() + 90, this.hrac.getY())) {
                this.hrac.koliziaSoZverom();
                return;
            }
        }
    }

    /**
     * Metoda zistuje ci doslo ku kolizii hraca s predmetom.
     * Ak ano hrac dany predmet zoberie a pripise si ho do svojho inventara.
     */
    private void koliziaSPredmetom() {
        for (int i = 0; i < this.mapa.getVelkostPredmetov(); i++) {
            //TODO cisla
            if (this.mapa.getPredmety(i).zober(this.hrac.getX() + 10, this.hrac.getY(), this.hrac.getSirka() - 10, this.hrac.getVyska() - 10)) {
                if (this.mapa.getPredmety(i) instanceof Minca) {
                    this.hrac.setPocetMinci(this.hrac.getPocetMinci() + 1);
                } else if (this.mapa.getPredmety(i) instanceof Zivot) {
                    this.hrac.setZivot(this.hrac.getZivot() + 1);
                } else if (this.mapa.getPredmety(i) instanceof Siska) {
                    this.hrac.pridajSisku();
                }
                this.mapa.odstranPredmet(i);
                return;
            }
        }
    }

    /**
     * Metoda skuma, ci doslo k zabitiu zvierata, odstranuje zabite zviera a pripisuje body ku skore
     * @return ci dosla k zabitiu
     */
    private boolean zabitieZveru() {
        for (int i = 0; i < this.mapa.getVelkostZverov(); i++) {
            if (this.hrac.skokNaZver(this.mapa.getZviera(i).getX() + this.mapa.getZviera(i).getSirka(), this.mapa.getZviera(i).getX(), this.mapa.getZviera(i).getY() + this.mapa.getZviera(i).getVyska()) && !(this.mapa.getZviera(i) instanceof Jezko)) {
                this.mapa.getZviera(i).uberZivot();
                if (this.mapa.getZviera(i).getZivot() == 0) {
                    this.hrac.setSkore(this.hrac.getSkore() + this.mapa.getZviera(i).getBody() * 110 / 100);
                    this.mapa.odstranZviera(i);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Vypisuje stav danych atributov
     * @return pocet zivotov, sisiek, minci a skore
     */
    public String vypis() {
        return "Zivoty: " + this.hrac.getZivot() + "   Mince: " + this.hrac.getPocetMinci() + "   Sisky: " + this.hrac.getPocetSisiek() + "    Skore: " + this.hrac.getSkore();
    }

    /**
     * Metoda vracia true ak hrac uz nema zivot alebo padol
     * @return ci prehral
     */
    public boolean prehra() {
        return this.hrac.getZivot() == 0 || this.hrac.getY() < 0;
    }

    /**
     * Zistuje, ci hrac dosiel na koniec hry
     * @return ci je na konci mapy
     */
    public boolean vyhra() {
        return (this.hrac.getX() + this.hrac.getSirka()) == (this.mapa.getPoslednaKocka().getX() + this.mapa.getPoslednaKocka().getSirka());
    }

    /**
     * Nastavenie hodenej sisky
     */
    public void setHodenaSiska() {
        if (this.hodenaSiska == null && this.hrac.getPocetSisiek() > 0) {
            this.hodenaSiska = this.hrac.getSiska();
            this.hodenaSiska.setX(this.hrac.getX() + this.hrac.getSirka());
            this.hodenaSiska.setY(this.hrac.getY() + 20);

            this.hodenaSiska.setSmer(this.hrac.getSmer());
        }
    }

    /**
     * Metoda na vypocet dosiahnuteho skore
     * @return skore
     */
    public int skore() {
        if (this.vyhral) {
            this.hrac.setSkore(this.hrac.getSkore() + this.hrac.getPocetSisiek() * 15 + this.hrac.getPocetMinci() * 10 + (this.hrac.getZivot() - 1 ) * 100);
        }
        return this.hrac.getSkore();
    }

    public void setVyhral() {
        this.vyhral = true;
    }

}
