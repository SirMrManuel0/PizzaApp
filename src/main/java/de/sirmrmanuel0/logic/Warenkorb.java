package de.sirmrmanuel0.logic;

import de.sirmrmanuel0.gui.custom_components.UpdaterPanel;
import de.sirmrmanuel0.pizza.Pizza;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.security.CodeSource;
import java.util.*;
import java.net.URL;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Warenkorb {
    protected ArrayList<Constructor[]> AllPossible;
    protected ArrayList<Pizza[]> AllPossibleInstances;
    protected ArrayList<UpdaterPanel> AllPossiblePanel;
    protected ArrayList<Pizza[]> AllToBuy;
    protected ArrayList<Object[]> Rabatte;

    public Warenkorb(){
        Rabatte = new ArrayList<>();
        AllPossible = new ArrayList<>();
        AllPossiblePanel = new ArrayList<>();
        AllToBuy = new ArrayList<>();
        AllPossibleInstances = new ArrayList<>();
        initAllPossible();
    }

    protected void initAllPossible(){
        AllPossible = getConstructors();

        for (Constructor[] cons : AllPossible){
            AllPossibleInstances.add(new Pizza[4]);
            for (int i = 0; i<4; i++){
                try{
                    AllPossibleInstances.get(AllPossibleInstances.size() - 1)[i] = (Pizza) cons[i].newInstance();
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void setAllPossiblePanel(ArrayList<UpdaterPanel> panels) {AllPossiblePanel = panels;};
    public void addAllPossiblePanel(UpdaterPanel panel){AllPossiblePanel.add(panel);}
    public void add(Pizza[] pizza){AllToBuy.add(pizza);}
    public void remove(Pizza[] pizza){AllToBuy.remove(pizza);}
    public void remove(int index){AllToBuy.remove(index);}
    public void addRabatt(String Grund, double rabatt){Rabatte.add(new Object[]{Grund, new BigDecimal(rabatt)});}
    public ArrayList<Pizza[]> getAllToBuy(){return AllToBuy;}
    public ArrayList<Constructor[]> getAllPossible(){return AllPossible;}
    public ArrayList<Pizza[]> getAllPossibleInstances(){return AllPossibleInstances;}
    public ArrayList<UpdaterPanel> getAllPossiblePanel(){return AllPossiblePanel;}
    public ArrayList<Object[]> getRabatte(){return Rabatte;}
    public double getGesamtPreis(){
        BigDecimal GesamtPreis = new BigDecimal(0);

        for (Pizza[] pizza : AllToBuy){
            for (int i = 0; i<4; i++){
                GesamtPreis = GesamtPreis.add(new BigDecimal(pizza[i].getPreis()).multiply(new BigDecimal(pizza[i].toString())));
            }
        }

        if (!Rabatte.isEmpty()){
            for (Object[] val : Rabatte){
                GesamtPreis = GesamtPreis.subtract((BigDecimal) val[1]);
            }
        }

        double gesamt = GesamtPreis.doubleValue();
        gesamt = (double) Math.round(gesamt * 100) / 100;
        String gesamtStr = String.valueOf((int) (gesamt * 100));
        gesamt = Double.parseDouble(gesamtStr.substring(0, gesamtStr.length()-2) + "." + gesamtStr.substring(gesamtStr.length()-2));

        return gesamt;
    }
    public double getGesamtPreisOhneRabatt(){
        BigDecimal GesamtPreis = new BigDecimal(0);

        for (Pizza[] pizza : AllToBuy){
            for (int i = 0; i<4; i++){
                GesamtPreis = GesamtPreis.add(new BigDecimal(pizza[i].getPreis()).multiply(new BigDecimal(pizza[i].toString())));
            }
        }

        double gesamt = GesamtPreis.doubleValue();
        gesamt = (double) Math.round(gesamt * 100) / 100;
        String gesamtStr = String.valueOf((int) (gesamt * 100));
        gesamt = Double.parseDouble(gesamtStr.substring(0, gesamtStr.length()-2) + "." + gesamtStr.substring(gesamtStr.length()-2));

        return gesamt;
    }



    // Methode zum Abrufen der Konstruktoren
    protected ArrayList<Constructor[]> getConstructors(){
        // Liste aller Konstruktoren
        ArrayList<Constructor[]> AllConstructor = new ArrayList<>();

        // Liste aller Klassennamen
        ArrayList<String[]> classNames = new ArrayList<>();

        // Überprüfen, ob die Anwendung innerhalb eines JAR-Archivs ausgeführt wird
        if (Warenkorb.class.getResource(Warenkorb.class.getSimpleName() + ".class").toString().startsWith("jar:")) {
            for (int i = 0; i < 4; i++){
                try {
                    // CodeSource und JAR-URL abrufen
                    CodeSource src = Warenkorb.class.getProtectionDomain().getCodeSource();
                    if (src != null) {
                        URL jar = src.getLocation();

                        int counter = 0;

                        // ZipInputStream für das JAR-Archiv erstellen
                        ZipInputStream zip = new ZipInputStream(jar.openStream());
                        while (true) {
                            ZipEntry e = zip.getNextEntry();
                            if (e == null)
                                break;
                            String name = e.getName();

                            String ordner = "";

                            switch(i){
                                case 0:{
                                    ordner = "small";
                                    break;
                                }
                                case 1:{
                                    ordner = "mid";
                                    break;
                                }
                                case 2:{
                                    ordner = "normal";
                                    break;
                                }
                                case 3:{
                                    ordner = "big";
                                    break;
                                }
                            }

                            // Klassennamen filtern und zur Liste hinzufügen
                            if (name.startsWith("de/sirmrmanuel0/pizza/pizzen/" + ordner) && name.endsWith(".class")) {
                                if (i == 0){
                                    classNames.add(new String[4]);
                                    classNames.get(classNames.size() - 1)[i] = name.replace("/", ".").substring(0, name.length() - 6);
                                } else {
                                    classNames.get(counter)[i] = name.replace("/", ".").substring(0, name.length() - 6);
                                    counter ++;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Konstruktoren für die gefundenen Klassen hinzufügen
            for (String[] className : classNames) {
                for (int i = 0; i < 4; i++){
                    try {
                        // Klasse laden
                        Class<?> unbekannteKlasse = Class.forName(className[i]);

                        // Konstruktor abrufen
                        Constructor<?> classConstructor = unbekannteKlasse.getDeclaredConstructor();

                        // Konstruktor zur Liste hinzufügen
                        if (i == 0){
                            AllConstructor.add(new Constructor[4]);
                        }
                        AllConstructor.get(AllConstructor.size() - 1)[i] = classConstructor;

                    } catch (ClassNotFoundException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            try {
                for (int i = 0; i<4; i++) {
                    // Paketname festlegen
                    String packageName = "de.sirmrmanuel0.pizza.pizzen";
                    switch (i){
                        case 0:{
                            packageName = "de.sirmrmanuel0.pizza.pizzen.small";
                            break;
                        }
                        case 1:{
                            packageName = "de.sirmrmanuel0.pizza.pizzen.mid";
                            break;
                        }
                        case 2:{
                            packageName = "de.sirmrmanuel0.pizza.pizzen.normal";
                            break;
                        }
                        case 3:{
                            packageName = "de.sirmrmanuel0.pizza.pizzen.big";
                            break;
                        }
                    }


                    // Pfad des Pakets im Dateisystem erstellen
                    String packagePath = packageName.replace(".", "/");
                    File packageDirectory = new File("src/main/java/" + packagePath);

                    // Überprüfen, ob das Verzeichnis existiert und ein Verzeichnis ist
                    if (packageDirectory.exists() && packageDirectory.isDirectory()) {
                        // Liste aller Dateinamen im Verzeichnis abrufen
                        String[] dateiNamen = packageDirectory.list();

                        // Wenn keine Dateien gefunden wurden, die Liste zurückgeben
                        if (dateiNamen == null) {
                            return AllConstructor;
                        }

                        // Konstruktoren für die gefundenen Klassen hinzufügen
                        for (int ii = 0; ii < dateiNamen.length; ii++) {
                            // Nur Java-Dateien berücksichtigen
                            if (dateiNamen[ii].endsWith(".java")) {
                                // Konstruiere den vollständigen Klassenpfad (ohne ".java" am Ende)
                                String fullClassName = packageName + "." + dateiNamen[ii].substring(0, dateiNamen[ii].length() - 5);

                                // Lade die Klasse
                                // Class<?> bedeutet, dass unbekannteKlasse eine Instanz einer unbekannten Klasse ist.
                                Class<?> unbekannteKlasse = Class.forName(fullClassName);

                                // Instanz der Klasse erstellen und zur Liste hinzufügen
                                // Constructor<?> bedeutet, dass constructor ein unbekannter Konstruktor für die Klasse ist.
                                Constructor<?> constructor = unbekannteKlasse.getDeclaredConstructor();

                                // Konstruktor zur Liste hinzufügen
                                if (i == 0){
                                    AllConstructor.add(new Constructor[4]);
                                    AllConstructor.get(AllConstructor.size()-1)[i] = constructor;
                                } else{
                                    AllConstructor.get(ii)[i] = constructor;
                                }

                            }
                        }

                    }
                }
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return AllConstructor;
    }
}
