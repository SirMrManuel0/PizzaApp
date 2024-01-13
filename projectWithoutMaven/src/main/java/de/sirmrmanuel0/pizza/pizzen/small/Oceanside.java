package de.sirmrmanuel0.pizza.pizzen.small;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Oceanside extends Pizza {
    public Oceanside(){
        Name = "Pizza Oceanside";
        preis = 14.09;
        Beschreibung = "Pizza mit Black Tiger Garnelen. Baconwürfeln. Jalapeños und Sauce Hollandaise.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
