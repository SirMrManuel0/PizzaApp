package de.sirmrmanuel0.gui;

import de.sirmrmanuel0.pizza.BetterPizza;
import de.sirmrmanuel0.pizza.pizzen.*;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Start extends CustomFrame{
    protected BigDecimal GesamtSumme;
    protected JPanel Main;
    protected JPanel Pizzen;
    protected JPanel PizzenWrapper;
    protected JScrollPane PizzenScroll;
    protected JButton Warenkorb;
    protected ArrayList<JPanel> PizzenList;
    protected ArrayList<BetterPizza> ObjPizzenList;
    protected ArrayList<JPanel> GesuchtePizzenList;
    protected ArrayList<BetterPizza> WarenkorbList;

    public Start(){
        super(1.5, 1, 1, 1.2, "Pizza Lieferung Deluxe", false);
        WarenkorbList = new ArrayList<BetterPizza>();
        GesamtSumme = new BigDecimal(0);
        setBackgroundImage(loadImage("background.jpg"));
        initComponents();
        setVisible(true);
    }

    public Start(ArrayList<BetterPizza> WarenkorbList, Point Location){
        super(1.5, 1, 1, 1.2, "Pizza Lieferung Deluxe", false);
        this.WarenkorbList = WarenkorbList;
        GesamtSumme = new BigDecimal(0);

        for (BetterPizza Pizza : WarenkorbList){
            BigDecimal Preis = new BigDecimal(0);
            for (int i = 0; i < 4 ; i++){
                BigDecimal EinzelPreis = new BigDecimal(Pizza.getPreise()[i]);
                BigDecimal Menge = new BigDecimal(Pizza.getAnzahl()[i]);
                Preis = Preis.add(EinzelPreis.multiply(Menge));
            }
            GesamtSumme = GesamtSumme.add(Preis);
        }

        GesamtSumme = GesamtSumme.multiply(new BigDecimal(100));
        BigDecimal temp = new BigDecimal("0." + String.valueOf(GesamtSumme).substring(String.valueOf(GesamtSumme).length()-3,
                String.valueOf(GesamtSumme).length()-1));
        GesamtSumme = new BigDecimal(GesamtSumme.intValue()/ 100);
        GesamtSumme = GesamtSumme.add(temp);

        setBackgroundImage(loadImage("background.jpg"));
        initComponents();
        Warenkorb.setText("<html>Zum Warenkorb<br>" + String.valueOf(GesamtSumme).replace(".", ",") + "€</html>");
        Warenkorb.setEnabled(true);
        setLocation(Location);
        setVisible(true);
    }

    protected void initComponents(){
        JPanel Footer = new JPanel(new GridBagLayout());
        JPanel Header = new JPanel(new GridBagLayout());
        Main = new JPanel();


        Warenkorb = new JButton("Zum Warenkorb");
        Warenkorb.setPreferredSize(getScaledDimension(50, 20));
        Warenkorb.setBackground(Color.BLACK);
        Warenkorb.setForeground(Color.WHITE);
        Warenkorb.setFocusPainted(false);
        Warenkorb.setEnabled(false);
        Warenkorb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Warenkorb(WarenkorbList, getLocation(), GesamtSumme);
            }
        });

        JLabel Title = new JLabel("Pizza Lieferung Deluxe");
        Title.setFont(new Font("Times New Roman", Font.BOLD, 25));
        Title.setForeground(Color.WHITE);

        JButton Impressum = new JButton("Impressum");
        JButton Datenschutz = new JButton("Datenschutz");
        JButton Kontakt = new JButton("Kontaktieren Sie uns!");

        Impressum.setForeground(Color.WHITE);
        Datenschutz.setForeground(Color.WHITE);
        Kontakt.setForeground(Color.WHITE);

        Impressum.setBackground(Color.BLACK);
        Datenschutz.setBackground(Color.BLACK);
        Kontakt.setBackground(Color.BLACK);

        Impressum.setFocusPainted(false);
        Datenschutz.setFocusPainted(false);
        Kontakt.setFocusPainted(false);

        Impressum.addActionListener(new FooterActionListener());
        Datenschutz.addActionListener(new FooterActionListener());
        Kontakt.addActionListener(new FooterActionListener());


        ImageIcon originalIcon = new ImageIcon(loadImage("logo_prop2.jpg"));

        Image resizedImage = originalIcon.getImage();
        resizedImage = resizedImage.getScaledInstance(
                getWidth() / 11,
                getHeight() / 10,
                Image.SCALE_SMOOTH
        );

        ImageIcon Logo = new ImageIcon(resizedImage);
        JLabel LogoWrapper = new JLabel(Logo);

        Header.setPreferredSize(getScaledDimension(1,10));
        Main.setPreferredSize(getScaledDimension(1,1,7.5,10));
        Footer.setPreferredSize(getScaledDimension(1,10));


        Header.setBackground(new Color(0,0,0,90));
        Footer.setBackground(new Color(0,0,0,150));

        GridBagConstraints headerGbc = new GridBagConstraints();
        headerGbc.weightx = 1;
        headerGbc.fill = GridBagConstraints.HORIZONTAL;

        Header.add(LogoWrapper, headerGbc);
        Header.add(Title, headerGbc);
        Header.add(Warenkorb, headerGbc);

        GridBagConstraints footerGbc = new GridBagConstraints();
        footerGbc.anchor = GridBagConstraints.CENTER;
        footerGbc.insets = new Insets(0, 10, 0, 10);

        Footer.add(Impressum, footerGbc);
        Footer.add(Datenschutz, footerGbc);
        Footer.add(Kontakt, footerGbc);

        initMain();

        Main.setBackground(new Color(0,0,0,0));

        add(Header);
        add(Main);
        add(Footer);
    }

    protected void initMain(){
        PlaceholderTextField Suche = new PlaceholderTextField("Suche");
        Suche.addKeyListener(new SucheKeyListener());
        PizzenWrapper = new JPanel();
        Pizzen = new JPanel();

        Suche.setHorizontalAlignment(JTextField.CENTER);
        Suche.setBackground(Color.BLACK);
        Suche.setForeground(Color.WHITE);
        Suche.setBorder(new LineBorder(Color.BLACK));

        ArrayList<BetterPizza> instances = getInstances();
        Pizzen.setLayout(new GridLayout(instances.size(),1));
        Pizzen.setBackground(new Color(0,0,0,0));

        initPizzen();

        PizzenScroll = new JScrollPane(Pizzen);
        PizzenScroll.setBackground(new Color(0,0,0,0));

        JScrollBar verticalScrollBar = PizzenScroll.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);
        verticalScrollBar.setBlockIncrement(60);

        PizzenScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                repaint();
            }
        });

        Suche.setPreferredSize(getScaledDimension(10, 20));
        PizzenWrapper.setPreferredSize(new Dimension(getWidth(),
                getScaledDimension(1,1,7.5,10).height
                        - getScaledDimension(10, 20).height));

        PizzenWrapper.setLayout(new GridLayout(1,1));
        Main.setLayout(new BorderLayout());

        PizzenWrapper.setBackground(new Color(0,0,0,0));

        PizzenWrapper.add(PizzenScroll);
        Main.add(Suche, BorderLayout.NORTH);
        Main.add(PizzenWrapper, BorderLayout.SOUTH);
    }

    protected void initPizzen(){
        ObjPizzenList = getInstances();
        PizzenList = new ArrayList<JPanel>();
        GesuchtePizzenList = new ArrayList<JPanel>();
        boolean EvenChild = false;
        for (BetterPizza Pizza : ObjPizzenList){
            JPanel PizzaWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0 ,0));
            RoundedCornerPanel PizzaPanel = new RoundedCornerPanel(25);
            RoundedButton Small = new RoundedButton( Pizza.getSizes()[0] + "cm " + Pizza.getPreise()[0] + "€");
            RoundedButton Mid = new RoundedButton(Pizza.getSizes()[1] + "cm " + Pizza.getPreise()[1] + "€");
            RoundedButton Normal = new RoundedButton(Pizza.getSizes()[2] + "cm " + Pizza.getPreise()[2] + "€");
            RoundedButton Big = new RoundedButton(Pizza.getSizes()[3] + "cm " + Pizza.getPreise()[3] + "€");
            JLabel Name = new JLabel(Pizza.getName());
            JLabel Beschreibung = new JLabel(Pizza.getBeschreibung());
            ImageIcon LogoIcon = new ImageIcon(loadImage("logo_prop1.png"));
            JLabel Icon = new JLabel();
            JPanel IconPanel = new JPanel();
            JPanel Schrift = new JPanel();
            JPanel Buttons = new JPanel();

            Image resizedImage = LogoIcon.getImage();
            resizedImage = resizedImage.getScaledInstance(
                    getWidth() / 15,
                    getHeight() / 14,
                    Image.SCALE_SMOOTH
            );

            Icon.setIcon(new ImageIcon(resizedImage));

            if (EvenChild){
                PizzaPanel.setBackground(new Color(81, 81, 81,250));
                IconPanel.setBackground(new Color(136, 136, 138,0));
                Schrift.setBackground(new Color(136, 136, 138,0));
                Buttons.setBackground(Color.lightGray);

                Small.setBackground(Color.lightGray);
                Mid.setBackground(Color.lightGray);
                Normal.setBackground(Color.lightGray);
                Big.setBackground(Color.lightGray);
                EvenChild = false;
            } else {
                PizzaPanel.setBackground(new Color(136, 136, 136,250));
                IconPanel.setBackground(new Color(136, 136, 138,0));
                Schrift.setBackground(new Color(136, 136, 138,0));
                Buttons.setBackground(Color.WHITE);

                Small.setBackground(Color.WHITE);
                Mid.setBackground(Color.WHITE);
                Normal.setBackground(Color.WHITE);
                Big.setBackground(Color.WHITE);
                EvenChild = true;
            }


            Small.setFocusPainted(false);
            Mid.setFocusPainted(false);
            Normal.setFocusPainted(false);
            Big.setFocusPainted(false);

            Small.setBorderPainted(false);
            Mid.setBorderPainted(false);
            Normal.setBorderPainted(false);
            Big.setBorderPainted(false);

            Small.addMouseListener(new PizzenMouseAdapter(!EvenChild));
            Mid.addMouseListener(new PizzenMouseAdapter(!EvenChild));
            Normal.addMouseListener(new PizzenMouseAdapter(!EvenChild));
            Big.addMouseListener(new PizzenMouseAdapter(!EvenChild));

            Small.addActionListener(new PizzenActionListener(0, Pizza));
            Mid.addActionListener(new PizzenActionListener(1, Pizza));
            Normal.addActionListener(new PizzenActionListener(2, Pizza));
            Big.addActionListener(new PizzenActionListener(3, Pizza));


            Name.setFont(new Font("SansSerif", Font.BOLD, 20));
            Beschreibung.setFont(new Font("SansSerif", Font.PLAIN, 12));
            Beschreibung.setForeground(Color.WHITE);
            Name.setForeground(Color.WHITE);

            PizzaPanel.setPreferredSize(getScaledDimension(1.1,10));
            IconPanel.setPreferredSize(getScaledDimension(15,14));
            Schrift.setPreferredSize(getScaledDimension(2.4,11));
            Buttons.setPreferredSize(getScaledDimension(2.8,11));

            Buttons.setLayout(new GridLayout(2,2));
            Schrift.setLayout(new GridLayout(2,1));

            PizzaWrapper.setBackground(new Color(0,0,0,0));

            IconPanel.add(Icon);
            Schrift.add(Name);
            Schrift.add(Beschreibung);
            Buttons.add(Small);
            Buttons.add(Mid);
            Buttons.add(Normal);
            Buttons.add(Big);
            PizzaPanel.add(IconPanel);
            PizzaPanel.add(Schrift);
            PizzaPanel.add(Buttons);
            PizzaWrapper.add(PizzaPanel);
            Pizzen.add(PizzaWrapper);
            PizzenList.add(PizzaWrapper);
        }

    }

    protected  ArrayList<BetterPizza> getInstances() {

        // Folgender Code funktioniert nur für IDEs für die .jar Datei ist der Code ab ---------------------------------------------------
        // Liste zur Speicherung von Instanzen erstellen
        ArrayList<BetterPizza> instances = new ArrayList<>();

        try {
            String packageName = "de.sirmrmanuel0.pizza.pizzen";
            // Pfad des Pakets im Dateisystem erstellen
            String packagePath = packageName.replace(".", "/");
            File packageDirectory = new File("src/main/java/" + packagePath);

            // Überprüfen, ob das Verzeichnis existiert und ein Verzeichnis ist
            if (packageDirectory.exists() && packageDirectory.isDirectory()) {
                // Liste aller Dateinamen im Verzeichnis abrufen
                String[] classNames = packageDirectory.list();

                // Wenn keine Dateien gefunden wurden, die Liste zurückgeben
                if (classNames == null) {
                    return instances;
                }
                for (String className : classNames) {
                    // Nur Java-Dateien berücksichtigen
                    if (className.endsWith(".java")) {
                        // Konstruiere den vollständigen Klassenpfad (ohne ".java" am Ende)
                        String fullClassName = packageName + "." + className.substring(0, className.length() - 5);

                        // Lade die Klasse
                        // Class<?> bedeutet, dass unbekannteKlasse eine Instanz einer unbekannten Klasse ist.
                        Class<?> unbekannteKlasse = Class.forName(fullClassName);

                        // Instanz der Klasse erstellen und zur Liste hinzufügen
                        // Constructor<?> bedeutet, dass constructor ein unbekannter Konstruktor für die Klasse ist.
                        Constructor<?> constructor = unbekannteKlasse.getDeclaredConstructor();

                        // constructor.newInstance() erstellt eine neue Instanz der Klasse.
                        instances.add((BetterPizza) constructor.newInstance());


                    }
                }

            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (instances.isEmpty()){
            // ---------------------------------------------------
            instances.add(new Chicago());
            instances.add(new Dallas());
            instances.add(new HotSanAntonio());
            instances.add(new Montana());
            instances.add(new Kentucky());
            instances.add(new Pittsburgh());
            instances.add(new SanFrancisco());
            instances.add(new HotTucson());
            instances.add(new Kansas());
            instances.add(new NewOrleans());
            instances.add(new NewHolland());
            instances.add(new Bakersfie());
            instances.add(new Denv());
            instances.add(new Memphis());
            instances.add(new ElPaso());
            instances.add(new NewArizona());
            instances.add(new Buffalo());
            instances.add(new Alaska());
            instances.add(new VegetarianIsland());
            instances.add(new ChinaTown());
            instances.add(new Wyoming());
            instances.add(new California());
            instances.add(new Seattle());
            instances.add(new Texas());
            instances.add(new Indiana());
            instances.add(new Utah());
            instances.add(new Florida());
            instances.add(new Tacoma());
            instances.add(new Pasadena());
            instances.add(new Iowa());
            instances.add(new NewBe());
            instances.add(new Oceanside());
            instances.add(new Vermo());
            instances.add(new Virginia());
            instances.add(new SantaMaria());
            instances.add(new VollkornTippCharlotte());
            instances.add(new StLouis());
            instances.add(new Washington());
            instances.add(new SantaFe());

        }
        return instances;
    }

    static class PizzenMouseAdapter extends MouseAdapter {
        protected Color BackgroundMouseExited = Color.WHITE;
        PizzenMouseAdapter(boolean Even){
            if (Even){
                BackgroundMouseExited = Color.lightGray;
            }
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            JButton Source = (JButton) e.getSource();
            Source.setBackground(new Color(247, 142, 5));
            Source.setForeground(Color.WHITE);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton Source = (JButton) e.getSource();
            Source.setBackground(BackgroundMouseExited);
            Source.setForeground(Color.BLACK);
        }

        @Override
        public void mousePressed(MouseEvent e){
            JButton Source = (JButton) e.getSource();
            Source.setBackground(new Color(199, 114, 4));
        }

        @Override
        public void mouseReleased(MouseEvent e){
            JButton Source = (JButton) e.getSource();
            Source.setBackground(new Color(247, 142, 5));
        }
    }

    protected class PizzenActionListener implements ActionListener{
        protected int Index;
        protected BetterPizza Pizza;

        public PizzenActionListener(int Index, BetterPizza Pizza){
            this.Index = Index;
            this.Pizza = Pizza;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String Name = Pizza.getName();
            JComboBox<Integer> Anzahl = new JComboBox<>(new Integer[]{1,2,3,4,5,6,7,8,9,10});
            Anzahl.setEditable(true);

            Object[] message = {
                    "Größe: " + Pizza.getSizes()[Index] + "cm",
                    "Einzelpreis: " + String.valueOf(Pizza.getPreise()[Index]).replace(".", ",") + "€",
                    "Anzahl:", Anzahl
            };


            int option = JOptionPane.showConfirmDialog(
                    Start.this,
                    message,
                    Name + " Kaufen",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (option != JOptionPane.OK_OPTION){return;}

            String AnzahlValue = String.valueOf(Anzahl.getSelectedItem());
            int IncreaseAnzahlVal;

            try{
                IncreaseAnzahlVal = Integer.parseInt(AnzahlValue);

                if (IncreaseAnzahlVal < 0){
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(
                        Start.this,
                        "Ungültige Eingabe für Anzahl. Bitte geben Sie eine gültige Zahl ein.",
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Warenkorb.setEnabled(true);
            Pizza.increaseAnzahl(IncreaseAnzahlVal, Index);

            BigDecimal Preis = BigDecimal.valueOf(Pizza.getPreise()[Index]);
            BigDecimal DecimalAnzahl = new BigDecimal(IncreaseAnzahlVal);

            GesamtSumme = GesamtSumme.add(Preis.multiply(DecimalAnzahl));
            Warenkorb.setText("<html>Zum Warenkorb<br>" + String.valueOf(GesamtSumme).replace(".", ",") + "€</html>");
            if (!WarenkorbList.contains(Pizza)){
                WarenkorbList.add(Pizza);
            }

        }
    }

    static class FooterActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String text = source.getText();
            JFrame Parent = findParentFrame(source);

            switch (text){
                case "Impressum": {
                    Object[] message = {
                            "Das sind wir!"
                    };
                    JOptionPane.showConfirmDialog(Parent, message, "Impressum",
                            JOptionPane.DEFAULT_OPTION,  JOptionPane.PLAIN_MESSAGE);
                    break;
                }
                case "Datenschutz": {
                    Object[] message = {
                            "Das ist unser Datenschutz!"
                    };
                    JOptionPane.showConfirmDialog(Parent, message, "Datenschutz",
                            JOptionPane.DEFAULT_OPTION,  JOptionPane.PLAIN_MESSAGE);
                    break;
                }
                case "Kontaktieren Sie uns!": {
                    Object[] message = {
                            "So können sie uns kontaktieren!"
                    };
                    JOptionPane.showConfirmDialog(Parent, message, "Kontakt",
                            JOptionPane.DEFAULT_OPTION,  JOptionPane.PLAIN_MESSAGE);
                    break;
                }
            }
        }

        protected JFrame findParentFrame(Component component) {
            Container container = component.getParent();
            while (container != null) {
                if (container instanceof JFrame) {
                    return (JFrame) container;
                }
                container = container.getParent();
            }
            return null;
        }

    }

    protected class SucheKeyListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            JTextField Source = (JTextField) e.getSource();
            String SuchEingabe = Source.getText();
            GesuchtePizzenList = new ArrayList<JPanel>();


            for (BetterPizza Pizza : ObjPizzenList) {
                if (Pizza.getName().substring(5).toLowerCase().contains(SuchEingabe.toLowerCase())){
                    int Index = ObjPizzenList.indexOf(Pizza);
                    GesuchtePizzenList.add(PizzenList.get(Index));
                }
            }

            Pizzen = new JPanel();
            Pizzen.setLayout(new GridLayout(GesuchtePizzenList.size(),1));
            Pizzen.setBackground(new Color(0,0,0,0));

            for (JPanel Pizza : GesuchtePizzenList){
                Pizzen.add(Pizza);
            }

            Pizzen.revalidate();
            Pizzen.repaint();


            GesuchtePizzenList = new ArrayList<JPanel>();

            PizzenScroll = new JScrollPane(Pizzen);
            PizzenScroll.setBackground(new Color(0,0,0,0));

            JScrollBar verticalScrollBar = PizzenScroll.getVerticalScrollBar();
            verticalScrollBar.setUnitIncrement(20);
            verticalScrollBar.setBlockIncrement(60);

            PizzenScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent e) {
                    repaint();
                }
            });

            PizzenScroll.revalidate();
            PizzenScroll.repaint();

            PizzenWrapper.removeAll();
            PizzenWrapper.add(PizzenScroll);

            PizzenWrapper.revalidate();
            PizzenWrapper.repaint();
        }

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}
    }


}
