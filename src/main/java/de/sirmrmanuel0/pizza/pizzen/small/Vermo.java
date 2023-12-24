package de.sirmrmanuel0.pizza.pizzen.small;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Vermo extends Pizza {
    public Vermo(){
        Name = "Pizza Vermo";
        preis = 12.79;
        Beschreibung = "Vegane Pizza mit Blattspinat. frischen Champignons. roten Zwiebeln. veganem Schmelzgenuss Gouda-Art und einer Prise Knoblauch.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
