package de.sirmrmanuel0.pizza.pizzen.big;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class StLouis extends Pizza {
    public StLouis(){
        Name = "Pizza St. Louis";
        preis = 24.89;
        Beschreibung = "Pizza mit Beef (100% Rind, gewürzt), Zwiebeln, Tomate und Remoulade.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
