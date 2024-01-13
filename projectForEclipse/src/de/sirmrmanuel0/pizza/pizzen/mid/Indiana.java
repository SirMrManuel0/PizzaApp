package de.sirmrmanuel0.pizza.pizzen.mid;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Indiana extends Pizza {
    public Indiana(){
        Name = "Pizza Indiana";
        preis = 16.99;
        Beschreibung = "Pizza mit Hinterschinken. Cherry-Tomaten. Rucola und frischem Trentingran.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
