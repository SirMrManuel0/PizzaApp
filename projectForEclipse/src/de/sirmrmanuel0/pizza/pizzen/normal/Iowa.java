package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Iowa extends Pizza {
    public Iowa(){
        Name = "Pizza Iowa";
        preis = 14.49;
        Beschreibung = "Vegane Pizza mit Broccoli, Mais, roten Zwiebeln und veganem Schmelzgenuss Gouda-Art.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
