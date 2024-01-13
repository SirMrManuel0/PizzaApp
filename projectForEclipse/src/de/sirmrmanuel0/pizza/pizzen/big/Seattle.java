package de.sirmrmanuel0.pizza.pizzen.big;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Seattle extends Pizza {
    public Seattle(){
        Name = "Pizza Seattle";
        preis = 23.69;
        Beschreibung = "Pizza mit Currychicken, Zwiebeln, Jalapeños und Barbecue-Sauce.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
