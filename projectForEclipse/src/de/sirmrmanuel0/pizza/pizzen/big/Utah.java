package de.sirmrmanuel0.pizza.pizzen.big;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Utah extends Pizza {
    public Utah(){
        Name = "Pizza Utah";
        preis = 27.29;
        Beschreibung = "Pizza mit Salami, Tomate, Mozzarella und Rucola.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
