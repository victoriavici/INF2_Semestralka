package sk.uniza.fri.citanie;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Trieda slúži na načítanie textového súboru.
 */
public class Citac {

    /**
     * Načítanie danych hodnôt z textového súboru
     *
     * @param nazov - názov súboru
     * @return list riadkov
     * @throws IOException - ak by súbor nebol
     */
    public static ArrayList<Riadok> nacitajSubor(String nazov) throws IOException {
        if (nazov == null) {
            throw new IllegalArgumentException("nazov je null");
        }
        File file = new File(nazov);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("nazov neexistuje");
        }

        ArrayList<Riadok> riadky = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                String key = null;
                String x = null;
                String y = null;
                String druh = null;

                if (line.contains(":")) {
                    key = line.substring(0, line.indexOf(":")).strip();
                } else {
                    int[] medzery = new int[4];
                    int j = 0;
                    for (int i = 0; i < line.length(); i++) {
                        if (Character.isWhitespace(line.charAt(i))) {
                            medzery[j] = i;
                            j++;
                        }
                    }

                    x = line.substring(2, medzery[2]).strip();
                    y = line.substring(medzery[2] + 1, medzery[3]).strip();
                    druh = line.substring(medzery[3] + 1).strip();
                }

                riadky.add(new Riadok(key, x, y, druh));
            }
        }
        return riadky;
    }
}
