package de.sirmrmanuel0.pizza.pizzen.small;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Seattle extends Pizza {
    public Seattle(){
        Name = "Pizza Seattle";
        preis = 13.39;
        Beschreibung = "Pizza mit Currychicken, Zwiebeln, Jalapeños und Barbecue-Sauce.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
