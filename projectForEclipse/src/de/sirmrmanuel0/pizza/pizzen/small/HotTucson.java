package de.sirmrmanuel0.pizza.pizzen.small;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class HotTucson extends Pizza {
    public HotTucson(){
        Name = "Pizza Hot Tucson";
        preis = 11.79;
        Beschreibung = "Pizza mit Peperonisalami. Jalapeños und Sauce Hollandaise.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
