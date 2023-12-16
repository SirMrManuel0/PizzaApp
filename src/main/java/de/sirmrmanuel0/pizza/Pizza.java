package de.sirmrmanuel0.pizza;


public class Pizza {
    protected double preis;
    protected int anzahl;
    protected String Name, Beschreibung;

    public Pizza() {
        Name = "Pizza";
        preis = 0;
        anzahl = 0;
        Beschreibung = "Feinster Pizzateig, megafrische Tomatensauce, Bio-Büffelmozzarella!";
    }

    public void setName(String Zusatzname) {
        Name += Zusatzname;
    }

    public String getName() {
        return Name;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public double getPreis() {
        return preis;
    }

    public void setBeschreibung(String ZusatzBeschreibung) {
        Beschreibung += ZusatzBeschreibung;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }
}