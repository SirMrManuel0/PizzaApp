package de.sirmrmanuel0.pizza.pizzen.mid;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class VegetarianIsland extends Pizza {
    public VegetarianIsland(){
        Name = "Pizza Vegetarian Island";
        preis = 15.29;
        Beschreibung = "Pizza mit Broccoli, Paprika, Mais und frischen Champignons.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
