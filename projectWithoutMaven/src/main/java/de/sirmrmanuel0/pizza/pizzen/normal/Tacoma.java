package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Tacoma extends Pizza {
    public Tacoma(){
        Name = "Pizza Tacoma";
        preis = 19.09;
        Beschreibung = "Pizza mit Beef (100% Rind, gewürzt), Blattspinat, Hirtenkäse und Knoblauch-Sauce.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
