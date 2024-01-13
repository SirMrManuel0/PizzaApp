package de.sirmrmanuel0.pizza.pizzen.normal;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class NewArizona extends Pizza {
    public NewArizona(){
        Name = "Pizza New Arizona";
        preis = 18.59;
        Beschreibung = "Pizza mit Hähnchen-Döner und Zwiebeln. dazu ein Becher (zur 32er zwei Becher. zur 38er drei Becher) Tzatziki.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
