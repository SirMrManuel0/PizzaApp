package de.sirmrmanuel0.pizza.pizzen.big;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class HotSanAntonio extends Pizza {
    public HotSanAntonio(){
        Name = "Pizza Hot San Antonio";
        preis = 25.39;
        Beschreibung = "Pizza mit geräucherter Putenbrust (halal). Beef (100% Rind, gewürzt). Jalapeños und Sauce Hollandaise.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
