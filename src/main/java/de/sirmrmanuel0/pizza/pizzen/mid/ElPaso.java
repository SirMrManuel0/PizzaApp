package de.sirmrmanuel0.pizza.pizzen.mid;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class ElPaso extends Pizza {
    public ElPaso(){
        Name = "Pizza El Paso";
        preis = 14.99;
        Beschreibung = "Pizza mit Mozzarella. Tomate. frischen Champignons und einer Prise Basilikum.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
