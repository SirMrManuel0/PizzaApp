package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Kansas extends Pizza {
    public Kansas(){
        Name = "Pizza Kansas";
        preis = 14.89;
        Beschreibung = "Pizza mit Peperonisalami und frischen Champignon.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
