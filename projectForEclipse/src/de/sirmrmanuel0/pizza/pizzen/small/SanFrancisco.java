package de.sirmrmanuel0.pizza.pizzen.small;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class SanFrancisco extends Pizza {
    public SanFrancisco(){
        Name = "Pizza San Francisco";
        preis = 11.29;
        Beschreibung = "Pizza mit Hinterschinken und frischen Champignon.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
