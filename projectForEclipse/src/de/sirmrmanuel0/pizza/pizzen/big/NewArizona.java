package de.sirmrmanuel0.pizza.pizzen.big;

import de.sirmrmanuel0.pizza.Pizza;


// This is an AUTO-Generated Class
public class NewArizona extends Pizza {
    public NewArizona(){
        Name = "Pizza New Arizona";
        preis = 25.39;
        Beschreibung = "Pizza mit Hähnchen-Döner und Zwiebeln. dazu ein Becher (zur 32er zwei Becher. zur 38er drei Becher) Tzatziki.";
    }

   @Override
   public void setPreis(double increment){this.anzahl += (int) increment;}

   @Override
   public String toString(){return String.valueOf(anzahl);}
}
