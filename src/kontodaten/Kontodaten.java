package kontodaten;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Alexander Kluge
 */
public class Kontodaten {

    static String quelle = "c:/temp/test.csv";
    static String ziel = "c:/temp/ergebnis.csv";
    static String letztesDatum = "";
    static double summe = 0;
    // mindestens 1 Vorkommastelle, genau 2 Nachkommastellen
    static DecimalFormat f = new DecimalFormat("#0.00");

    static BufferedWriter out;

    private static void datenAbarbeiten(String zeile) throws IOException {
        String teile[] = zeile.split(";");
        if (!teile[2].equals("Valutadatum")) {
            double geld = new Double(teile[8].replace(",", "."));
            SimpleDateFormat datum = new SimpleDateFormat(teile[2]);
            String aktuellesDatum = datum.toLocalizedPattern();
            if (aktuellesDatum.equals(letztesDatum)) {
                summe = summe + geld;
                summe = new Double(f.format(summe).replace(",", "."));
            } else {
                if (!letztesDatum.equals("")) {
                    // speichern
                    System.out.println(letztesDatum + "    " + f.format(summe));
                    out.write(letztesDatum + ";" + f.format(summe) + "\n");
                }
                summe = geld;
            }
            letztesDatum = aktuellesDatum;
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(quelle));
        out = new BufferedWriter(new FileWriter(ziel));
        while (in.ready()) {
            datenAbarbeiten(in.readLine());
        }
        in.close();
        out.close();
    }
}
