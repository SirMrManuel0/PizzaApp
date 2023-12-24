package de.sirmrmanuel0.pizza.pizzen.small;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Pasadena extends Pizza {
    public Pasadena(){
        Name = "Pizza Pasadena";
        preis = 13.79;
        Beschreibung = "Vollkornpizza aus einer Dinkel-. Roggen- und Weizenmischung mit Hirtenkäse. Blattspinat und Cherry-Tomate.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
