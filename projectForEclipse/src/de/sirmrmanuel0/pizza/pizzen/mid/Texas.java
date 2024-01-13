package de.sirmrmanuel0.pizza.pizzen.mid;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Texas extends Pizza {
    public Texas(){
        Name = "Pizza Texas";
        preis = 15.79;
        Beschreibung = "Pizza mit Baconwürfeln, Mais, Beef (100% Rind, gewürzt) und Barbecue-Sauce.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
