package de.sirmrmanuel0.pizza.pizzen.big;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class California extends Pizza {
    public California(){
        Name = "Pizza California";
        preis = 22.39;
        Beschreibung = "Pizza mit Baconwürfeln, Ei und Sauce Hollandaise.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
