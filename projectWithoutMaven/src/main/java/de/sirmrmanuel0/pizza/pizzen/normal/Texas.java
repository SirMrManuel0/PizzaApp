package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Texas extends Pizza {
    public Texas(){
        Name = "Pizza Texas";
        preis = 18.59;
        Beschreibung = "Pizza mit Baconwürfeln, Mais, Beef (100% Rind, gewürzt) und Barbecue-Sauce.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
