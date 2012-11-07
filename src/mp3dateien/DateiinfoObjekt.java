package mp3dateien;

public class DateiinfoObjekt {

    private String dateiname, interpret, titel, platzierung;

    public void setDateiname(String dateiname) {
        this.dateiname = dateiname;
    }

    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setPlatzierung(String platzierung) {
        this.platzierung = platzierung;
    }

    public String getDateiname() {
        return this.dateiname;
    }

    public String getInterpret() {
        return this.interpret;
    }

    public String getTitel() {
        return this.titel;
    }

    public String getPlatzierung() {
        return this.platzierung;
    }
}
