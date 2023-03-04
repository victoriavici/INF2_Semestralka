package sk.uniza.fri.citanie;

/**
 * Trieda Riadok predstavuje jeden riadok zápisu v textovom súbore.
 * Pozostáva z kľuča, x - suradnice, y - súradnice a druhu.
 */
public class Riadok {
    private final String kluc;
    private final String x;
    private final String y;
    private final String druh;


    /**
     * Vytvorenie inštancie, inicializácia atribútov.
     *
     * @param kluc - kluc
     * @param x - x súradnica
     * @param y - y súradnica
     * @param druh - druh
     */
    public Riadok(String kluc, String x, String y, String druh) {
        this.kluc = kluc;
        this.x = x;
        this.y = y;
        this.druh = druh;
    }

    /**
     * Metóda vracajúca kľúč riadka.
     *
     * @return kluc
     */
    public String getKluc() {
        return this.kluc;
    }

    /**
     * Metóda vráti x riadka.
     *
     * @return x
     */
    public String getX() {
        return this.x;
    }

    /**
     * Metóda vráti y riadka.
     *
     * @return y
     */
    public String getY() {
        return this.y;
    }

    /**
     * Vráti druh riadka
     *
     * @return druh
     */
    public String getDruh() {
        return this.druh;
    }

    /**
     * Vráti dané hodnoty atributov v tvare reťazca
     *
     * @return reťazec z kluca, x, y a druhu
     */
    @Override
    public String toString() {
        return this.kluc + " " + this.x + " " + this.y + " " + this.druh;
    }
}
