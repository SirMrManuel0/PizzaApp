package de.sirmrmanuel0.pizza.pizzen.small;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class VollkornTippCharlotte extends Pizza {
    public VollkornTippCharlotte(){
        Name = "Pizza Vollkorn-Tipp Charlotte";
        preis = 16.09;
        Beschreibung = "Vollkornpizza mit frischen Champignons. Paprika. Mais und knusprigem Hirtenkäse überbacken.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
