package de.sirmrmanuel0.pizza.pizzen.big;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class VollkornTippCharlotte extends Pizza {
    public VollkornTippCharlotte(){
        Name = "Pizza Vollkorn-Tipp Charlotte";
        preis = 27.39;
        Beschreibung = "Vollkornpizza mit frischen Champignons. Paprika. Mais und knusprigem Hirtenkäse überbacken.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
