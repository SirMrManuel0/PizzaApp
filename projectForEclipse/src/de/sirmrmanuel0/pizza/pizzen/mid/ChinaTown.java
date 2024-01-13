package de.sirmrmanuel0.pizza.pizzen.mid;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class ChinaTown extends Pizza {
    public ChinaTown(){
        Name = "Pizza China Town";
        preis = 15.79;
        Beschreibung = "Pizza mit Hähnchenbrustfilet, Paprika, Ananas und Sweet Chili Sauce.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
