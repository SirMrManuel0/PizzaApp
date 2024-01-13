package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Utah extends Pizza {
    public Utah(){
        Name = "Pizza Utah";
        preis = 19.79;
        Beschreibung = "Pizza mit Salami, Tomate, Mozzarella und Rucola.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
