package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class NewBe extends Pizza {
    public NewBe(){
        Name = "Pizza New Be";
        preis = 15.49;
        Beschreibung = "Pizza mit Peperonisalami. Zwiebeln. frischen Champignons und Crème fraîche.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
