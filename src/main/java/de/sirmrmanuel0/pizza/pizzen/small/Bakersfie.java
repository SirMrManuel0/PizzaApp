package de.sirmrmanuel0.pizza.pizzen.small;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Bakersfie extends Pizza {
    public Bakersfie(){
        Name = "Pizza Bakersfie";
        preis = 13.29;
        Beschreibung = "Pizza mit Sucuk (halal). milden Peperoni und Hirtenkäse.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
