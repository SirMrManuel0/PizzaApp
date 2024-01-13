package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Montana extends Pizza {
    public Montana(){
        Name = "Pizza Montana";
        preis = 16.39;
        Beschreibung = "Pizza mit saftigen Rindfleisch-Streifen, Broccoli und Sauce Hollandaise.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
