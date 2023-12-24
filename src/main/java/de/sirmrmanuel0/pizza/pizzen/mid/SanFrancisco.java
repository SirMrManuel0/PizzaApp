package de.sirmrmanuel0.pizza.pizzen.mid;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class SanFrancisco extends Pizza {
    public SanFrancisco(){
        Name = "Pizza San Francisco";
        preis = 12.49;
        Beschreibung = "Pizza mit Hinterschinken und frischen Champignon.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
