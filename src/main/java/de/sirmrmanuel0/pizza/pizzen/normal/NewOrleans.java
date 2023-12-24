package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class NewOrleans extends Pizza {
    public NewOrleans(){
        Name = "Pizza New Orleans";
        preis = 18.59;
        Beschreibung = "Pizza mit geräucherter Putenbrust (halal). Broccoli. grünem Spargel und Sauce Hollandaise.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
