package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Vermo extends Pizza {
    public Vermo(){
        Name = "Pizza Vermo";
        preis = 17.39;
        Beschreibung = "Vegane Pizza mit Blattspinat. frischen Champignons. roten Zwiebeln. veganem Schmelzgenuss Gouda-Art und einer Prise Knoblauch.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
