package sk.uniza.fri.mapa;

/**
 * Enum reprezentujúci druhy kociek, ktoré sa vyskytujú na mape.
 */
public enum DruhKocky {
    ZBURATELNA("zburatelna"),
    NEZBURATELNA("nezburatelna"),
    SPREDMETOM("spredmetom"),
    PADAJUCA("padajuca");

    private final String reprezentacia;

    DruhKocky(String reprezentacia) {
        this.reprezentacia = reprezentacia;
    }

    public static DruhKocky getDruhKocky(String druh) {
        for (DruhKocky dk : DruhKocky.values()) {
            if (dk.reprezentacia.equals(druh)) {
                return dk;
            }
        }
        return null;
    }
}

