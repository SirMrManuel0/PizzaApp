package de.sirmrmanuel0.pizza.pizzen.mid;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Chicago extends Pizza {
    public Chicago(){
        Name = "Pizza Chicago";
        preis = 12.39;
        Beschreibung = "Pizza mit Thunfisch und Zwiebeln.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
