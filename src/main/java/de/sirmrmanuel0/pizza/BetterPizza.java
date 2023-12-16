package de.sirmrmanuel0.pizza;


public class BetterPizza extends Pizza{
    protected int[] sizes;
    protected double[] prizes;
    protected int[] anzahl;


    public BetterPizza() {
        sizes = new int[] {25, 28, 32, 38};
        prizes = new double[] {7.99, 9.49, 11.99, 14.99};
        anzahl = new int[]{0, 0, 0, 0};
    }

    public void setPreise(double[] preise) {
        this.prizes = preise;
    }

    public double[] getPreise() {
        return prizes;
    }

    public void setSizes(int[] sizes) {
        this.sizes = sizes;
    }

    public int[] getSizes() {
        return sizes;
    }

    public  void setAnzahl(int[] anzahl){this.anzahl = anzahl;}
    public  int[] getAnzahl(){return anzahl;}

    public void increaseAnzahl(int Increase, int Index){anzahl[Index] += Increase;}

}
