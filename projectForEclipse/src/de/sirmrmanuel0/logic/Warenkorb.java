package de.sirmrmanuel0.logic;

import de.sirmrmanuel0.gui.custom_components.UpdaterPanel;
import de.sirmrmanuel0.pizza.Pizza;

import javax.naming.ConfigurationException;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.security.CodeSource;
import java.util.*;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Warenkorb {
    protected ArrayList<Constructor[]> AllPossible;
    protected ArrayList<Pizza[]> AllPossibleInstances;
    protected ArrayList<UpdaterPanel> AllPossiblePanel;
    protected ArrayList<Pizza[]> AllToBuy;
    protected ArrayList<Object[]> Rabatte;

    /**
     * Konstruktor für den Warenkorb. Initialisiert verschiedene Listen.
     */
    public Warenkorb(){
        Rabatte = new ArrayList<>();
        AllPossible = new ArrayList<>();
        AllPossiblePanel = new ArrayList<>();
        AllToBuy = new ArrayList<>();
        AllPossibleInstances = new ArrayList<>();
        initAllPossible();
    }

    /**
     * Initialisiert die Liste der möglichen Pizzen und deren Konstruktoren.
     */
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

    /**
     * Setzt die Liste der möglichen UpdaterPanels.
     * @param panels Liste der UpdaterPanels
     */
    public void setAllPossiblePanel(ArrayList<UpdaterPanel> panels) {AllPossiblePanel = panels;};

    /**
     * Fügt ein UpdaterPanel zur Liste der möglichen UpdaterPanels hinzu.
     * @param panel Das hinzuzufügende UpdaterPanel
     */
    public void addAllPossiblePanel(UpdaterPanel panel){AllPossiblePanel.add(panel);}

    /**
     * Fügt eine Pizza zur Liste der zu kaufenden Pizzen hinzu.
     * @param pizza Die hinzuzufügende Pizza
     */
    public void add(Pizza[] pizza){AllToBuy.add(pizza);}

    /**
     * Entfernt eine Pizza aus der Liste der zu kaufenden Pizzen.
     * @param pizza Die zu entfernende Pizza
     */
    public void remove(Pizza[] pizza){AllToBuy.remove(pizza);}

    /**
     * Entfernt eine Pizza anhand ihres Index aus der Liste der zu kaufenden Pizzen.
     * @param index Der Index der zu entfernenden Pizza
     */
    public void remove(int index){AllToBuy.remove(index);}

    /**
     * Fügt einen Rabatt zur Liste der Rabatte hinzu.
     * @param Grund Grund des Rabatts
     * @param rabatt Höhe des Rabatts
     */
    public void addRabatt(String Grund, double rabatt){Rabatte.add(new Object[]{Grund, new BigDecimal(rabatt)});}

    /**
     * Gibt die Liste der zu kaufenden Pizzen zurück.
     * @return Liste der zu kaufenden Pizzen
     */
    public ArrayList<Pizza[]> getAllToBuy(){return AllToBuy;}

    /**
     * Gibt die Liste der möglichen Konstruktoren zurück.
     * @return Liste der möglichen Konstruktoren
     */
    public ArrayList<Constructor[]> getAllPossible(){return AllPossible;}

    /**
     * Gibt die Liste der möglichen Instanzen zurück.
     * @return Liste der möglichen Instanzen
     */
    public ArrayList<Pizza[]> getAllPossibleInstances(){return AllPossibleInstances;}

    /**
     * Gibt die Liste der möglichen UpdaterPanels zurück.
     * @return Liste der möglichen UpdaterPanels
     */
    public ArrayList<UpdaterPanel> getAllPossiblePanel(){return AllPossiblePanel;}

    /**
     * Gibt die Liste der Rabatte zurück.
     * @return Liste der Rabatte
     */
    public ArrayList<Object[]> getRabatte(){return Rabatte;}

    /**
     * Berechnet den Gesamtpreis inklusive Rabatte.
     * @return Der Gesamtpreis
     */
    public double getGesamtPreis(){
        // GesamtPreis als BigDecimal initialisieren
        BigDecimal GesamtPreis = new BigDecimal(0);

        // Durchlaufen aller Pizzen im Warenkorb
        for (Pizza[] pizza : AllToBuy){
            for (int i = 0; i<4; i++){
                // Gesamtpreis mit Einzelpreis und Anzahl der Pizzen aktualisieren
                GesamtPreis = GesamtPreis.add(new BigDecimal(pizza[i].getPreis()).multiply(new BigDecimal(pizza[i].toString())));
            }
        }

        // Rabatte abziehen, falls vorhanden
        if (!Rabatte.isEmpty()){
            for (Object[] val : Rabatte){
                GesamtPreis = GesamtPreis.subtract((BigDecimal) val[1]);
            }
        }

        if (GesamtPreis.equals(new BigDecimal(0))) return 0.0;

        // Runden und Umwandlung in double für die Rückgabe
        double gesamt = GesamtPreis.doubleValue();
        gesamt = (double) Math.round(gesamt * 100) / 100;
        String gesamtStr = String.valueOf((int) (gesamt * 100));
        gesamt = Double.parseDouble(gesamtStr.substring(0, gesamtStr.length()-2)
                + "." + gesamtStr.substring(gesamtStr.length()-2));

        return gesamt;
    }

    /**
     * Berechnet den Gesamtpreis ohne Rabatte.
     * @return Der Gesamtpreis ohne Rabatte
     */
    public double getGesamtPreisOhneRabatt(){
        // GesamtPreis als BigDecimal initialisieren
        BigDecimal GesamtPreis = new BigDecimal(0);

        // Durchlaufen aller Pizzen im Warenkorb
        for (Pizza[] pizza : AllToBuy){
            for (int i = 0; i<4; i++){
                // Gesamtpreis ohne Rabatte mit Einzelpreis und Anzahl der Pizzen aktualisieren
                GesamtPreis = GesamtPreis.add(new BigDecimal(pizza[i].getPreis()).multiply(new BigDecimal(pizza[i].toString())));
            }
        }

        // Runden und Umwandlung in double für die Rückgabe
        double gesamt = GesamtPreis.doubleValue();
        gesamt = (double) Math.round(gesamt * 100) / 100;
        String gesamtStr = String.valueOf((int) (gesamt * 100));
        gesamt = Double.parseDouble(gesamtStr.substring(0, gesamtStr.length()-2)
                + "." + gesamtStr.substring(gesamtStr.length()-2));

        return gesamt;
    }

    /**
     * Methode zum Abrufen der Konstruktoren.
     * @return Liste der Konstruktoren
     */
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
                            if (name.startsWith(ConfigManager.readConfig(ConfigManager.PIZZEN_DIR) + ordner)
                                    && name.endsWith(".class")) {
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
                    String packageName = ConfigManager.readConfig(ConfigManager.PIZZEN_PACK);
                    switch (i){
                        case 0:{
                            packageName += ".small";
                            break;
                        }
                        case 1:{
                            packageName += ".mid";
                            break;
                        }
                        case 2:{
                            packageName += ".normal";
                            break;
                        }
                        case 3:{
                            packageName += ".big";
                            break;
                        }
                    }


                    // Pfad des Pakets im Dateisystem erstellen
                    String packagePath = packageName.replace(".", "/");
                    File packageDirectory = new File(ConfigManager.readConfig(ConfigManager.SRC_DIR)
                            + packagePath);


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
            } catch (ConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        return AllConstructor;
    }
}