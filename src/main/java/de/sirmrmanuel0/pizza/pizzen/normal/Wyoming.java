package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class Wyoming extends Pizza {
    public Wyoming(){
        Name = "Pizza Wyoming";
        preis = 17.49;
        Beschreibung = "Vier-Käse-Pizza) Pizza mit Gouda. Mozzarella. Gorgonzola und Hirtenkäse. Nur mit Rand und ungeschnitten bestellbar.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
