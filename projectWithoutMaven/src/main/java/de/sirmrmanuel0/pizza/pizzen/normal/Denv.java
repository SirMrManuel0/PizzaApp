package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Denv extends Pizza {
    public Denv(){
        Name = "Pizza Denv";
        preis = 14.49;
        Beschreibung = "Pizza mit Salami, frischen Champignons und Zwiebeln.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
