package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class SantaFe extends Pizza {
    public SantaFe(){
        Name = "Pizza Santa Fe";
        preis = 21.69;
        Beschreibung = "Vegane Pizza mit veganen Hähnchen-Art-Happen, Paprika, Mais, roten Zwiebeln, Barbecue-Sauce und veganem Schmelzgenuss Gouda-Art.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
