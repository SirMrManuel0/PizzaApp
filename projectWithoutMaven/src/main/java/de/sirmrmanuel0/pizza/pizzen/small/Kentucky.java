package de.sirmrmanuel0.pizza.pizzen.small;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Kentucky extends Pizza {
    public Kentucky(){
        Name = "Pizza Kentucky";
        preis = 10.99;
        Beschreibung = "Pizza mit Currychicken und Ananas.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
