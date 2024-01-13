package de.sirmrmanuel0.pizza.pizzen.mid;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Bakersfie extends Pizza {
    public Bakersfie(){
        Name = "Pizza Bakersfie";
        preis = 14.89;
        Beschreibung = "Pizza mit Sucuk (halal). milden Peperoni und Hirtenkäse.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
