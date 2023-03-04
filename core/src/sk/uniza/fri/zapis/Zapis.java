package sk.uniza.fri.zapis;

/**
 * Trieda Zapis predstavuje zapis hraca v tabulke. Pozostava z mena, skore a poradoveho cisla.
 */
public class Zapis {
    private final String meno;
    private final int skore;
    private String poradie;
    /**
     * Vytvorenie inštancie, inicializácia atribútov.
     *
     * @param meno meno hráča
     * @param skore skóre, ktoré hráč dosiahol
     */
    public Zapis(String poradie, String meno, int skore) {
        this.meno = meno;
        this.skore = skore;
        this.poradie = poradie;
    }

    /**
     * Metóda vracajúca meno z daného zápisu.
     *
     * @return meno
     */
    public String getMeno() {
        return this.meno;
    }

    /**
     * Metóda vracajúca meno z daného zápisu.
     *
     * @return skore
     */
    public int getSkore() {
        return this.skore;
    }

    public void setPoradie(String poradie) {
        this.poradie = poradie;
    }

    /**
     * Metóda na výpis zápisu.
     *
     * @return reťazec pozostávajúci z mena a skóre.
     */
    @Override
    public String toString() {
        String s = String.format("%4s  %-20s %10d", this.poradie, this.meno, this.skore);
        System.out.println(s);
        return s;

    }
}

