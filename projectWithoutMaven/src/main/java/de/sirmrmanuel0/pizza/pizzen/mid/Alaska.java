package de.sirmrmanuel0.pizza.pizzen.mid;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Alaska extends Pizza {
    public Alaska(){
        Name = "Pizza Alaska";
        preis = 12.99;
        Beschreibung = "Pizza mit Räucherlachs. Crème fraîche und Dillspitze.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
