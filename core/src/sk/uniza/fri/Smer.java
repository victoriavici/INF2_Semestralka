package sk.uniza.fri;

/**
 * Enum Smer reprezentuje smery, ktorymi sa mozu veci, postavy pohybovat v hre
 */
public enum Smer {
    HORE(0, 1),
    DOLE(0, -1),
    VPRAVO(1, 0),
    VLAVO(-1, 0),
    NIKAM(0, 0);

    private final int posunX;
    private final int posunY;

    Smer(int posunX, int posunY) {
        this.posunX = posunX;
        this.posunY = posunY;
    }

    public int getPosunX() {
        return this.posunX;
    }

    public int getPosunY() {
        return this.posunY;
    }

}
