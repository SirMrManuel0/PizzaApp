package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Virginia extends Pizza {
    public Virginia(){
        Name = "Pizza Virginia";
        preis = 15.39;
        Beschreibung = "Pizza mit Tomate und Hirtenkäse.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
