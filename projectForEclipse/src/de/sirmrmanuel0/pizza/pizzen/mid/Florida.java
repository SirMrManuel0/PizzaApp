package de.sirmrmanuel0.pizza.pizzen.mid;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Florida extends Pizza {
    public Florida(){
        Name = "Pizza Florida";
        preis = 14.39;
        Beschreibung = "Pizza mit Black Tiger Garnelen. grünem Spargel und Sweet Chili Sauce.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
