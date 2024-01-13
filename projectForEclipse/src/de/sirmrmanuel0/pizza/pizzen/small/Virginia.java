package de.sirmrmanuel0.pizza.pizzen.small;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Virginia extends Pizza {
    public Virginia(){
        Name = "Pizza Virginia";
        preis = 11.69;
        Beschreibung = "Pizza mit Tomate und Hirtenkäse.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
