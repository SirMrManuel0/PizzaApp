package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Dallas extends Pizza {
    public Dallas(){
        Name = "Pizza Dallas";
        preis = 14.89;
        Beschreibung = "Pizza mit doppelt Salami auf dem Käse.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
