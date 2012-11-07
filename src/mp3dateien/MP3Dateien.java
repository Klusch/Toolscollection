package mp3dateien;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JFileChooser;

public class MP3Dateien {

    static final boolean DEBUG = true;
    private Vector<DateiinfoObjekt> inhaltVZAlt = new Vector();
    private Vector<DateiinfoObjekt> inhaltVZNeu = new Vector();

    /**
     * Verschiedene Funktionen
     */
    public MP3Dateien(String alt, String neu) { // jeweils für 2 Verzeichnisse

        // Verzeichnis einlesen (mit Filter)
        String[] inhaltAltVZ = verzeichnisListen(alt);
        String[] inhaltNeuVZ = verzeichnisListen(neu);

        for (int i = 0; i < inhaltNeuVZ.length; i++) {
            // Trennen der Dateinamenteile und Speichern in Listen
            inhaltVZNeu.add(trennen(inhaltNeuVZ[i], "-", false));
        }
        for (int i = 0; i < inhaltAltVZ.length; i++) {
            // Trennen der Dateinamenteile und Speichern in Listen
            inhaltVZAlt.add(trennen(inhaltAltVZ[i], "-", false));
        }

        // Ein Unterverzeichnis erstellen und nachher die Daten dahin verschieben
        File vzerst = new File(alt + "/geloescht/");
        if (!vzerst.exists()) {
            vzerst.mkdirs();
        }

        for (int i = 0; i < inhaltVZAlt.size(); i++) {
            String interpretAlt = inhaltVZAlt.get(i).getInterpret().toLowerCase();
            for (int j = 0; j < inhaltVZNeu.size(); j++) {
                String interpretNeu = inhaltVZNeu.get(j).getInterpret().toLowerCase();
                if (interpretAlt.equals(interpretNeu)) {
                    String titelAlt = inhaltVZAlt.get(i).getTitel().toLowerCase();
                    String titelNeu = inhaltVZNeu.get(j).getTitel().toLowerCase();
                    if (titelAlt.equals(titelNeu)) {
                        // "LOESCHEN"
                        String original = alt+"/"+inhaltVZAlt.get(i).getDateiname();
                        String ziel = alt+"/geloescht/"+inhaltVZAlt.get(i).getDateiname();
                        //System.out.println(original);
                        //System.out.println(ziel);
                        move(original, ziel);
                    } // sonst beibehalten
                } // sonst beibehalten
            }

        }

    }

    private static DateiinfoObjekt trennen(String kette, String trennzeichen, boolean sonderfunktion) {
        DateiinfoObjekt sammlung = new DateiinfoObjekt();
        String interpret, titel;
        StringTokenizer teiler = new StringTokenizer(kette, trennzeichen);
        boolean unsauber = false;

        if (teiler.countTokens() > 3) {
            unsauber = true;
        }

        String teil = teiler.nextToken();
        Integer platzierung = 0;
        try {
            platzierung = new Integer(teil.trim());
        } catch (NumberFormatException e) {
            System.out.println("Keine Zahl");
            System.exit(0);
        }
        if (platzierung > 0) {
            interpret = teiler.nextToken();
            if (unsauber) {
              interpret = interpret + " "+ teiler.nextToken();
            }
            interpret = interpret.replace("_", " ").trim();
            titel = teiler.nextToken();
            titel = titel.replace("_", " ").trim();

            sammlung.setDateiname(kette);
            sammlung.setInterpret(interpret);
            sammlung.setPlatzierung(platzierung.toString());
            sammlung.setTitel(titel);

            if (sonderfunktion == true) {
                // Für die Webseite
                System.out.println("<tr>\n<td style=\"text-align: center;\">" + platzierung + "</td>");
                System.out.println("<td>" + interpret + "</td>");
                System.out.println("<td>" + titel.replaceAll(".mp3", "") + "</td>\n</tr>");
            }
        }
        return sammlung;
    }

    /**
     * Einlesen der Mp3-Dateien und Eintrag deren Namen in Tabelle.
     *
     * @param vz   Das Verzeichnis
     * @return      Die Auflistung der Mp3-Dateien
     */
    private String[] verzeichnisListen(String vz) {
        File verzeichnis = new File(vz);
        FilterMP3 mp3filter = new FilterMP3();
        return verzeichnis.list(mp3filter);
    }

    /**
     * Die Mp3-Dateien einlesen, nach Plazierung, Interpret und Titel
     * trennen und schließlich mit entsprechenden HTML-Tags einkappseln,
     * so dass die Text-Ausgabe kopiert und in ein HTML-Dokument ein-
     * gefügt werden kann-
     *
     * @param vz    Das einzulesende Verzeichnis
     */
    private static void dateilistingAlsHTML(String vz) {
        File verzeichnis = new File(vz);
        FilterMP3 mp3filter = new FilterMP3();
        String inhaltA1[] = verzeichnis.list(mp3filter);
        Arrays.sort(inhaltA1);
        for (int i = 0; i < inhaltA1.length; i++) {
            trennen(inhaltA1[i], "-", true);
        }
    }

    private void oeffnen() {
        final JFileChooser chooser = new JFileChooser("Verzeichnis wählen");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        final File file = new File("/home");

        chooser.setCurrentDirectory(file);

        chooser.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent e) {
                if (e.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) || e.getPropertyName().equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
                    final File f = (File) e.getNewValue();
                }
            }
        });

        chooser.setVisible(true);
        final int result = chooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File inputVerzFile = chooser.getSelectedFile();
            String inputVerzStr = inputVerzFile.getPath();
            System.out.println("Eingabepfad:" + inputVerzStr);
        }
        System.out.println("Abbruch");
        chooser.setVisible(false);
    }

    private static void move(String strsource, String strdestination) {
        // File Objekt für die Ursprungs-Datei erzeugen
        File source = new File(strsource);
        // File Objekt für die neue Datei erzeugen
        File destination = new File(strdestination);
        // Datei wird umbenannt/verschoben
        if (!source.renameTo(destination)) {
            System.err.println("Fehler beim Umbenennen der Datei: " + source.getName());
        }
    }

    public static void main(String[] args) {
        //String altesverzeichnis = "S:/FTP/unverschluesselt/Medien/iTunes-Musik/000-GERMAN_TOP100_Single_Chartz/150A-German_TOP100_Single_Charts_12_04_2010";
        //String neuesverzeichnis = "S:/FTP/unverschluesselt/Medien/iTunes-Musik/000-GERMAN_TOP100_Single_Chartz/160-German Top100 Single Charts 24.05.2010";

        //new MP3Dateien(altesverzeichnis, neuesverzeichnis);

        // Sonderfunktion für die Webseite - HTML-Text erzeugen
        
        String verz3 = "S:/FTP/unverschluesselt/Medien/iTunes-Musik/000-GERMAN_TOP100_Single_Chartz/025-German_TOP100_Single_Charts_05_07_2010";
        dateilistingAlsHTML(verz3);
        
    }
}
