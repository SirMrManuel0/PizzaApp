package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Buffalo extends Pizza {
    public Buffalo(){
        Name = "Pizza Buffalo";
        preis = 15.09;
        Beschreibung = "Pizza mit Bauernschinken und einem Becher (zur 38er zwei Becher) Sour Cream (100 ml).";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
