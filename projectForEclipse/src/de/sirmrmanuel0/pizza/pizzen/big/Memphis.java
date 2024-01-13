package de.sirmrmanuel0.pizza.pizzen.big;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Memphis extends Pizza {
    public Memphis(){
        Name = "Pizza Memphis";
        preis = 24.89;
        Beschreibung = "Pizza mit Hähnchenbrustfilet. Blattspinat. Cherry-Tomaten und Knoblauch-Sauce.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
