package de.sirmrmanuel0.pizza.pizzen.big;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class SantaMaria extends Pizza {
    public SantaMaria(){
        Name = "Pizza Santa Maria";
        preis = 24.99;
        Beschreibung = "100 % Vollkornpizza aus einer Dinkel-. Roggen- und Weizenmischung mit Broccoli. Hirtenkäse. milden Peperoni. frischen Champignons und Sweet Chili Sauce.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
