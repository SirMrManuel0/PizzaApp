package de.sirmrmanuel0.pizza.pizzen.mid;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Washington extends Pizza {
    public Washington(){
        Name = "Pizza Washington";
        preis = 21.69;
        Beschreibung = "Pizza mit geräucherter Putenbrust (halal). Beef (100% Rind gewürzt). frischen Champignons. Paprika. Jalapeños. Zwiebeln und Sauce Hollandaise.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
