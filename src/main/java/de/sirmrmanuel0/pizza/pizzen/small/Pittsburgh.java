package de.sirmrmanuel0.pizza.pizzen.small;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Pittsburgh extends Pizza {
    public Pittsburgh(){
        Name = "Pizza Pittsburgh";
        preis = 12.89;
        Beschreibung = "Pizza mit Hähnchenbrustfilet. Blattspinat. einer Prise Knoblauch und Crème fraîche.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
