package de.sirmrmanuel0.pizza.pizzen.mid;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class NewHolland extends Pizza {
    public NewHolland(){
        Name = "Pizza New Holland";
        preis = 13.29;
        Beschreibung = "Pizza mit grünem Spargel, Broccoli und Sauce Hollandaise.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
