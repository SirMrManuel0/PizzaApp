package de.sirmrmanuel0.gui;

import de.sirmrmanuel0.gui.custom_components.CustomFrame;
import de.sirmrmanuel0.gui.custom_components.RoundedButton;
import de.sirmrmanuel0.gui.custom_components.RoundedCornerPanel;
import de.sirmrmanuel0.gui.snake.GameOverObserver;
import de.sirmrmanuel0.gui.snake.Snake;
import de.sirmrmanuel0.gui.snake.SnakePanel;
import de.sirmrmanuel0.logic.ConfigManager;
import de.sirmrmanuel0.logic.Warenkorb;
import de.sirmrmanuel0.pizza.Pizza;

import javax.naming.ConfigurationException;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

public class Kasse extends CustomFrame implements GameOverObserver {
    protected Warenkorb Korb;
    protected JPanel Main;
    protected double GesamtSumme;
    protected JScrollPane QuittungScroll;
    protected JPanel QuittungWrapper;
    protected JPanel Quittung;
    protected ArrayList<JPanel> Waren;
    protected JLabel GesamtPreis;
    protected SnakePanel RabattPanel;
    protected Snake snake;

    /**
     * Konstruktor für die Kasse.
     * @param Korb Der Warenkorb, der die ausgewählten Produkte enthält.
     * @param Location Die Position, an der das Kassenfenster platziert werden soll.
     */
    public Kasse(Warenkorb Korb, Point Location){
        super(1, 1.5, 1, 1.2, "Pizza Lieferung Deluxe - Warenkorb", false);
        this.Korb = Korb;
        GesamtSumme = Korb.getGesamtPreis();
        Waren = new ArrayList<JPanel>();
        try {
            setBackgroundImage(loadImage("background.jpg", ConfigManager.readConfig(ConfigManager.BACKGROUND)),
                    0, 0, getWidth(), getHeight());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        initComponents();
        if (!Korb.getRabatte().isEmpty())
            rabatt();
        setLocation(Location);
        setVisible(true);
    }

    /**
     * Initialisiert die GUI-Komponenten des Kassenfensters.
     * Dazu gehören Header, Main und Footer Panels sowie ihre jeweiligen Komponenten wie Buttons und Labels.
     */
    protected void initComponents(){
        // Erstellen der Panels für den Header, Main und Footer mit GridBagLayout
        JPanel Footer = new JPanel(new GridBagLayout());
        JPanel Header = new JPanel(new GridBagLayout());
        Main = new JPanel();

        // Zurück-Button erstellen und konfigurieren
        JButton Back = new JButton("Zurück");
        Back.setPreferredSize(getScaledDimension(50, 20));
        Back.setBackground(Color.BLACK);
        Back.setForeground(Color.WHITE);
        Back.setFocusPainted(false);
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aktion beim Klicken des Zurück-Buttons: Fenster schließen und zum Startfenster zurückkehren
                dispose();
                new Start(Korb, getLocation());
            }
        });

        // Titel-Label erstellen und konfigurieren
        JLabel Title = new JLabel("Pizza Lieferung Deluxe");
        Title.setFont(new Font("Times New Roman", Font.BOLD, 25));
        Title.setForeground(Color.WHITE);

        // Impressum, Datenschutz und Kontakt-Buttons erstellen und konfigurieren
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

        // ActionListener für Footer-Buttons hinzufügen
        Impressum.addActionListener(new Start.FooterActionListener());
        Datenschutz.addActionListener(new Start.FooterActionListener());
        Kontakt.addActionListener(new Start.FooterActionListener());

        // Logo-Icon laden, skalieren und in ein ImageIcon umwandeln
        ImageIcon originalIcon = null;
        try {
            originalIcon = new ImageIcon(loadImage("logo_prop2.jpg", ConfigManager.readConfig(ConfigManager.LOGO_2)));
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        Image resizedImage = originalIcon.getImage();
        resizedImage = resizedImage.getScaledInstance(
                getWidth() / 11,
                getHeight() / 10,
                Image.SCALE_SMOOTH
        );

        // Skaliertes Logo in ein neues ImageIcon einfügen
        ImageIcon Logo = new ImageIcon(resizedImage);
        JLabel LogoWrapper = new JLabel(Logo);

        // Größen für Header, Main und Footer Panels festlegen
        Header.setPreferredSize(getScaledDimension(1,10));
        Main.setPreferredSize(getScaledDimension(1,1.1,7.5,10));
        Footer.setPreferredSize(getScaledDimension(1,10));

        // Hintergrundfarben für Header und Footer setzen
        Header.setBackground(new Color(0,0,0,90));
        Footer.setBackground(new Color(0,0,0,150));

        // GridBagConstraints für den Header erstellen und konfigurieren
        GridBagConstraints headerGbc = new GridBagConstraints();
        headerGbc.weightx = 1;
        headerGbc.fill = GridBagConstraints.HORIZONTAL;

        // Header-Elemente (Logo, Titel, Zurück-Button) zum Header-Panel hinzufügen
        Header.add(LogoWrapper, headerGbc);
        Header.add(Title, headerGbc);
        Header.add(Back, headerGbc);

        // GridBagConstraints für den Footer erstellen und konfigurieren
        GridBagConstraints footerGbc = new GridBagConstraints();
        footerGbc.anchor = GridBagConstraints.CENTER;
        footerGbc.insets = new Insets(0, 10, 0, 10);

        // Footer-Elemente (Impressum, Datenschutz, Kontakt) zum Footer-Panel hinzufügen
        Footer.add(Impressum, footerGbc);
        Footer.add(Datenschutz, footerGbc);
        Footer.add(Kontakt, footerGbc);

        // initMain-Methode aufrufen, um das Main-Panel zu initialisieren
        initMain();

        // Hintergrund des Main-Panels transparent setzen
        Main.setBackground(new Color(0,0,0,0));

        // Header, Main und Footer zum Kassenfenster hinzufügen
        add(Header);
        add(Main);
        add(Footer);
    }

    /**
     * Initialisiert das Hauptpanel des Kassenfensters.
     * Dieses Panel enthält die Quittung der ausgewählten Pizzen sowie den Bezahlen-Bereich.
     */
    protected void initMain(){
        // Layout für das Main-Panel auf GridLayout(1,2) setzen
        Main.setLayout(new GridLayout(1,2));

        // Panels für Quittung, Bezahlen, BezahlenButtonPanel, Filler1, RabattSpielButton, GesamtPreisPanel und GesamtPreisWrapper erstellen
        QuittungWrapper = new JPanel();
        Quittung = new JPanel();
        JPanel Bezahlen = new JPanel();
        JPanel BezahlenButtonPanel = new JPanel(new GridLayout(3,1));
        JPanel Filler1 = new JPanel();
        JButton RabattSpielButton = new JButton("<html><center>Rabatt-Spiel Snake<br>Sparen Sie 1 Euro pro 100 Punkte</center></html>");
        JPanel GesamtPreisPanel = new JPanel(new GridBagLayout());
        JButton BezahlenButton = new JButton("Bezahlen");
        JPanel GesamtPreisWrapper = new JPanel();

        // Layout und Hintergrundfarben für Bezahlen, Filler1, BezahlenButtonPanel, GesamtPreisPanel und GesamtPreisWrapper setzen
        Bezahlen.setLayout(new GridLayout(2,1));
        Bezahlen.setBackground(new Color(0,0,0,0));
        Filler1.setBackground(new Color(0,0,0,0));
        BezahlenButtonPanel.setBackground(new Color(0,0,0,0));
        GesamtPreisPanel.setBackground(new Color(0,0,0,90));
        GesamtPreisWrapper.setBackground(new Color(0,0,0,0));

        // Text, Farbe und Schriftart für GesamtPreis-Label festlegen
        GesamtPreis = new JLabel("Summe: " + String.valueOf(GesamtSumme).replace(".",",") + "€");
        GesamtPreis.setForeground(Color.WHITE);
        GesamtPreis.setFont(new Font("SansSerif", Font.BOLD, 25));

        // Hintergrundfarben für BezahlenButton und RabattSpielButton setzen
        BezahlenButton.setBackground(Color.BLACK);
        BezahlenButton.setForeground(Color.WHITE);
        BezahlenButton.setFocusPainted(false);

        RabattSpielButton.setBackground(Color.BLACK);
        RabattSpielButton.setForeground(Color.WHITE);
        RabattSpielButton.setFocusPainted(false);

        // ActionListener für RabattSpielButton hinzufügen
        RabattSpielButton.addActionListener(new RabattSpielActionListener());

        // ActionListener für BezahlenButton hinzufügen
        BezahlenButton.addActionListener(new BezahlenActionListener());

        // initQuittung-Methode aufrufen, um das Quittung-Panel zu initialisieren
        initQuittung();

        // Hintergrundfarben für QuittungWrapper und Quittung setzen
        QuittungWrapper.setBackground(new Color(0,0,0,0));
        Quittung.setBackground(new Color(0,0,0,0));

        // Layout für Quittung auf GridLayout(Waren.size(), 1) setzen
        Quittung.setLayout(new GridLayout(Waren.size(),1));

        // JScrollPane für Quittung erstellen und konfigurieren
        QuittungScroll = new JScrollPane(Quittung);
        QuittungScroll.setBackground(new Color(0,0,0,0));

        // AdjustmentListener für vertikalen und horizontalen ScrollBar der QuittungScroll hinzufügen
        QuittungScroll.getVerticalScrollBar().addAdjustmentListener(new ScrollAdjustmentListener());
        QuittungScroll.getHorizontalScrollBar().addAdjustmentListener(new ScrollAdjustmentListener());

        // UnitIncrement und BlockIncrement für vertikalen und horizontalen ScrollBar festlegen
        JScrollBar verticalScrollBar = QuittungScroll.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);
        verticalScrollBar.setBlockIncrement(60);

        JScrollBar horizontalScrollBar = QuittungScroll.getHorizontalScrollBar();
        horizontalScrollBar.setUnitIncrement(20);
        horizontalScrollBar.setBlockIncrement(60);

        // Layout für QuittungWrapper auf GridLayout(1, 1) setzen und QuittungScroll hinzufügen
        QuittungWrapper.setLayout(new GridLayout(1,1));
        QuittungWrapper.add(QuittungScroll);

        // Filler1, RabattSpielButton und BezahlenButton zum BezahlenButtonPanel hinzufügen
        BezahlenButtonPanel.add(Filler1);
        BezahlenButtonPanel.add(RabattSpielButton);
        BezahlenButtonPanel.add(BezahlenButton);

        // GridBagConstraints für GesamtPreisWrapper konfigurieren
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        // GesamtPreis zum GesamtPreisWrapper hinzufügen und GesamtPreisWrapper zum GesamtPreisPanel hinzufügen
        GesamtPreisWrapper.add(GesamtPreis);
        GesamtPreisPanel.add(GesamtPreisWrapper, gbc);

        // JScrollPane für GesamtPreisPanel erstellen und Hintergrundfarbe setzen
        JScrollPane GesamtPreisScroll = new JScrollPane(GesamtPreisPanel);
        GesamtPreisScroll.setBackground(new Color(0,0,0,0));

        // AdjustmentListener für vertikalen ScrollBar der GesamtPreisScroll hinzufügen
        JScrollBar VerticalBar = GesamtPreisScroll.getVerticalScrollBar();
        VerticalBar.addAdjustmentListener(new ScrollAdjustmentListener());

        // AdjustmentListener für horizontalen ScrollBar der GesamtPreisScroll hinzufügen
        JScrollBar HorizontalBar = GesamtPreisScroll.getHorizontalScrollBar();
        HorizontalBar.addAdjustmentListener(new ScrollAdjustmentListener());

        // GesamtPreisScroll zum Bezahlen-Panel hinzufügen
        Bezahlen.add(GesamtPreisScroll);
        Bezahlen.add(BezahlenButtonPanel);

        // QuittungWrapper und Bezahlen zum Main-Panel hinzufügen
        Main.add(QuittungWrapper);
        Main.add(Bezahlen);
    }

    /**
     * Initialisiert das Panel für die Quittung mit den ausgewählten Pizzen.
     * Jede ausgewählte Pizza wird in einem eigenen Panel dargestellt, inklusive Mengen, Preise und Lösch-Buttons.
     */
    protected void initQuittung(){
        // Iteration durch alle Pizzen im Warenkorb
        boolean EvenChild = false; // Flag zur Bestimmung der Hintergrundfarben für abwechselnde Kinderkomponenten
        for (Pizza[] Pizza : Korb.getAllToBuy()){
            // Panels und Komponenten für die Anzeige einer Pizza im Warenkorb erstellen
            JPanel PizzaWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0 ,0));
            RoundedCornerPanel PizzaPanel = new RoundedCornerPanel(25);
            RoundedButton DeleteSmall = new RoundedButton( "25 cm entfernen");
            RoundedButton DeleteMid = new RoundedButton("28 cm entfernen");
            RoundedButton DeleteNormal = new RoundedButton("32 cm entfernen");
            RoundedButton DeleteBig = new RoundedButton("38 cm entfernen");
            JPanel Mengen = new JPanel();
            JLabel MengeKlein = new JLabel("25cm x " + Pizza[0].toString() + " x " + String.valueOf(Pizza[0].getPreis()).replace(".",",") + "€");
            JLabel MengeMid = new JLabel("28cm x " + Pizza[1].toString() + " x " + String.valueOf(Pizza[1].getPreis()).replace(".",",") + "€");
            JLabel MengeNormal = new JLabel("32cm x " + Pizza[2].toString() + " x " + String.valueOf(Pizza[2].getPreis()).replace(".",",") + "€");
            JLabel MengeBig = new JLabel("38cm x " + Pizza[3].toString() + " x " + String.valueOf(Pizza[3].getPreis()).replace(".",",") + "€");
            JLabel Name = new JLabel(Pizza[0].getName());
            ImageIcon LogoIcon = null;
            try {
                LogoIcon = new ImageIcon(loadImage("logo_prop1.png", ConfigManager.readConfig(ConfigManager.LOGO_1)));
            } catch (ConfigurationException e) {
                throw new RuntimeException(e);
            }
            JLabel Icon = new JLabel();
            JPanel IconPanel = new JPanel();
            JPanel Schrift = new JPanel();
            JPanel Buttons = new JPanel();
            JPanel DropDowns = new JPanel(new GridLayout(2,2));

            // Variablen für die Längen der Pizzagrößen und ihre Verwendbarkeit initialisieren
            int lengthSmall = 1;
            int lengthMid = 1;
            int lengthNormal = 1;
            int lengthBig = 1;

            boolean usableSmall = false;
            boolean usableMid = false;
            boolean usableNormal = false;
            boolean usableBig = false;

            // Überprüfen, ob die Anzahl der Pizzen in jeder Größe größer als 0 ist
            if (!Pizza[0].toString().equals("0")){
                lengthSmall = Integer.parseInt(Pizza[0].toString());
                usableSmall = true;
            }
            if (!Pizza[1].toString().equals("0")){
                lengthMid = Integer.parseInt(Pizza[1].toString());
                usableMid = true;
            }
            if (!Pizza[2].toString().equals("0")){
                lengthNormal = Integer.parseInt(Pizza[2].toString());
                usableNormal = true;
            }
            if (!Pizza[3].toString().equals("0")){
                lengthBig = Integer.parseInt(Pizza[3].toString());
                usableBig = true;
            }

            // Arrays für Dropdown-Menüs erstellen und befüllen
            String[] dropArraySmall = new String[lengthSmall];
            String[] dropArrayMid = new String[lengthMid];
            String[] dropArrayNormal = new String[lengthNormal];
            String[] dropArrayBig = new String[lengthBig];

            for (int i = 0; i<lengthSmall; i++){
                dropArraySmall[i] = String.valueOf(i+1);
            }
            for (int i = 0; i<lengthMid; i++){
                dropArrayMid[i] = String.valueOf(i+1);
            }
            for (int i = 0; i<lengthNormal; i++){
                dropArrayNormal[i] = String.valueOf(i+1);
            }
            for (int i = 0; i<lengthBig; i++){
                dropArrayBig[i] = String.valueOf(i+1);
            }

            // Dropdown-Menüs erstellen und konfigurieren
            JComboBox<String> dropDownSmall = new JComboBox<>(dropArraySmall);
            JComboBox<String> dropDownMid = new JComboBox<>(dropArrayMid);
            JComboBox<String> dropDownNormal = new JComboBox<>(dropArrayNormal);
            JComboBox<String> dropDownBig = new JComboBox<>(dropArrayBig);

            dropDownSmall.setEditable(usableSmall);
            dropDownMid.setEditable(usableMid);
            dropDownNormal.setEditable(usableNormal);
            dropDownBig.setEditable(usableBig);

            dropDownSmall.setEnabled(usableSmall);
            dropDownMid.setEnabled(usableMid);
            dropDownNormal.setEnabled(usableNormal);
            dropDownBig.setEnabled(usableBig);

            // PopupMenuListener für die Dropdown-Menüs hinzufügen
            dropDownSmall.addPopupMenuListener(new WarenPopUpListener());
            dropDownMid.addPopupMenuListener(new WarenPopUpListener());
            dropDownNormal.addPopupMenuListener(new WarenPopUpListener());
            dropDownBig.addPopupMenuListener(new WarenPopUpListener());

            // Logo-Bildgröße anpassen und zur Icon-Komponente hinzufügen
            Image resizedImage = LogoIcon.getImage();
            resizedImage = resizedImage.getScaledInstance(
                    getWidth() / 15,
                    getHeight() / 14,
                    Image.SCALE_SMOOTH
            );
            Icon.setIcon(new ImageIcon(resizedImage));

            // Hintergrundfarben je nach EvenChild-Status festlegen
            if (EvenChild){
                // Hintergrundfarben für gerade Kinderkomponenten
                PizzaPanel.setBackground(new Color(81, 81, 81,250));
                IconPanel.setBackground(new Color(136, 136, 138,0));
                Schrift.setBackground(new Color(136, 136, 138,0));
                Buttons.setBackground(Color.lightGray);

                DeleteSmall.setBackground(Color.lightGray);
                DeleteMid.setBackground(Color.lightGray);
                DeleteNormal.setBackground(Color.lightGray);
                DeleteBig.setBackground(Color.lightGray);
                EvenChild = false;
            } else {
                // Hintergrundfarben für ungerade Kinderkomponenten
                PizzaPanel.setBackground(new Color(136, 136, 136,250));
                IconPanel.setBackground(new Color(136, 136, 138,0));
                Schrift.setBackground(new Color(136, 136, 138,0));
                Buttons.setBackground(Color.WHITE);

                DeleteSmall.setBackground(Color.WHITE);
                DeleteMid.setBackground(Color.WHITE);
                DeleteNormal.setBackground(Color.WHITE);
                DeleteBig.setBackground(Color.WHITE);
                EvenChild = true;
            }

            // Einstellungen für Buttons und deren MouseAdapter
            configureButton(DeleteSmall, EvenChild);
            configureButton(DeleteMid, EvenChild);
            configureButton(DeleteNormal, EvenChild);
            configureButton(DeleteBig, EvenChild);

            // ActionListener für die Löschen-Buttons hinzufügen
            DeleteSmall.addActionListener(new WarenActionListener(Pizza, 0, PizzaWrapper, dropDownSmall));
            DeleteMid.addActionListener(new WarenActionListener(Pizza,1, PizzaWrapper, dropDownMid));
            DeleteNormal.addActionListener(new WarenActionListener(Pizza,2, PizzaWrapper, dropDownNormal));
            DeleteBig.addActionListener(new WarenActionListener(Pizza,3, PizzaWrapper, dropDownBig));

            // Buttons für nicht ausgewählte Pizzen deaktivieren
            disableIfZero(Pizza[0].toString(), DeleteSmall);
            disableIfZero(Pizza[1].toString(), DeleteMid);
            disableIfZero(Pizza[2].toString(), DeleteNormal);
            disableIfZero(Pizza[3].toString(), DeleteBig);

            // Mengen-Panel konfigurieren
            configureMengenPanel(Mengen, MengeKlein, MengeMid, MengeNormal, MengeBig);

            // Schriftart, Größe und Farbe für den Pizzanamen festlegen
            Name.setFont(new Font("SansSerif", Font.BOLD, 20));
            Name.setForeground(Color.WHITE);

            // Größen und Layouts für die Panels und Komponenten festlegen
            PizzaPanel.setPreferredSize(getScaledDimension(0.66,10));
            IconPanel.setPreferredSize(getScaledDimension(15,14));
            Schrift.setPreferredSize(getScaledDimension(2.4,11));
            Buttons.setPreferredSize(getScaledDimension(2.8,11));

            Buttons.setLayout(new GridLayout(2,2)); // Layout für Buttons festlegen
            Schrift.setLayout(new GridLayout(1,2)); // Layout für Mengeninformationen festlegen

            PizzaWrapper.setBackground(new Color(0,0,0,0)); // Hintergrundfarbe des PizzaWrappers setzen

            // Dropdown-Menüs dem DropDowns-Panel hinzufügen
            DropDowns.add(dropDownSmall);
            DropDowns.add(dropDownMid);
            DropDowns.add(dropDownNormal);
            DropDowns.add(dropDownBig);

            // Icon zu IconPanel hinzufügen
            IconPanel.add(Icon);

            // Mengeninformationen zum Mengen-Panel hinzufügen
            Mengen.add(MengeKlein);
            Mengen.add(MengeMid);
            Mengen.add(MengeNormal);
            Mengen.add(MengeBig);

            // Pizzaname und Mengeninformationen zum Schrift-Panel hinzufügen
            Schrift.add(Name);
            Schrift.add(Mengen);

            // Buttons zum Buttons-Panel hinzufügen
            Buttons.add(DeleteSmall);
            Buttons.add(DeleteMid);
            Buttons.add(DeleteNormal);
            Buttons.add(DeleteBig);

            // IconPanel, Schrift, Dropdown-Menüs und Buttons zum PizzaPanel hinzufügen
            PizzaPanel.add(IconPanel);
            PizzaPanel.add(Schrift);
            PizzaPanel.add(DropDowns);
            PizzaPanel.add(Buttons);

            PizzaWrapper.add(PizzaPanel); // PizzaPanel zum PizzaWrapper hinzufügen
            Quittung.add(PizzaWrapper); // PizzaWrapper zum Quittung-Panel hinzufügen
            Waren.add(PizzaWrapper); // PizzaWrapper zur Waren-Liste hinzufügen
        }
    }

    /**
     * Konfiguriert die Darstellungseigenschaften eines RoundedButton.
     *
     * @param Button Der abgerundete Button, der konfiguriert wird.
     * @param even Gibt an, ob das Eltern-Panel gerade ist (für Farbanpassung).
     */
    protected void configureButton(RoundedButton Button, boolean even){
        Button.setFocusPainted(false);
        Button.setBorderPainted(false);
        Button.addMouseListener(new Start.PizzenMouseAdapter(!even));
    }

    /**
     * Deaktiviert den Button, wenn die übergebene Anzahl gleich "0" ist.
     *
     * @param anzahl Die Anzahl, die überprüft wird.
     * @param Button Der Button, der deaktiviert wird, wenn die Anzahl "0" ist.
     */
    protected void disableIfZero(String anzahl, RoundedButton Button){
        if (anzahl.equals("0"))
            Button.setEnabled(false);
    }

    /**
     * Konfiguriert das Mengen-Panel mit den JLabels für jede Pizzagröße.
     *
     * @param mengenPanel Das JPanel für die Mengen-Anzeige.
     * @param mengeKlein   JLabel für die Klein-Pizza.
     * @param mengeMid     JLabel für die Mittel-Pizza.
     * @param mengeNormal  JLabel für die Normal-Pizza.
     * @param mengeBig     JLabel für die Groß-Pizza.
     */
    protected void configureMengenPanel(JPanel mengenPanel, JLabel mengeKlein, JLabel mengeMid, JLabel mengeNormal, JLabel mengeBig) {
        mengenPanel.setBackground(new Color(0, 0, 0, 0));
        mengenPanel.setLayout(new GridLayout(2, 2));

        // Schrift- und Farbeinstellungen für die Mengen-Anzeige
        mengeKlein.setFont(new Font("SansSerif", Font.PLAIN, 12));
        mengeMid.setFont(new Font("SansSerif", Font.PLAIN, 12));
        mengeNormal.setFont(new Font("SansSerif", Font.PLAIN, 12));
        mengeBig.setFont(new Font("SansSerif", Font.PLAIN, 12));

        mengeKlein.setForeground(Color.WHITE);
        mengeMid.setForeground(Color.WHITE);
        mengeNormal.setForeground(Color.WHITE);
        mengeBig.setForeground(Color.WHITE);

        // Mengen-Anzeige zum Mengen-Panel hinzufügen
        mengenPanel.add(mengeKlein);
        mengenPanel.add(mengeMid);
        mengenPanel.add(mengeNormal);
        mengenPanel.add(mengeBig);
    }

    /**
     * Zeigt Rabatte auf der Quittung an und aktualisiert die Gesamtsumme.
     */
    protected void rabatt(){
        // GesamtPreis-Label mit dem Gesamtpreis ohne Rabatt initialisieren
        GesamtPreis.setText("<html><center>"
                + String.valueOf(Korb.getGesamtPreisOhneRabatt()).replace(".",",") + "€<br>");

        // Schleife durch alle Rabatte im Warenkorb
        for (Object[] val : Korb.getRabatte()){
            // Rabattwert als BigDecimal extrahieren und temporären Rabatt als Double berechnen
            BigDecimal rabattBig = (BigDecimal) val[1];
            double tempRabatt = rabattBig.doubleValue();

            // Temporären Rabatt auf zwei Dezimalstellen runden
            tempRabatt = (double) Math.round(tempRabatt * 100) / 100;

            // Temporären Rabatt als String formatieren
            String rabattStr = String.valueOf((int) (tempRabatt * 100));

            // Rabatt-String auf die richtige Länge bringen
            switch (rabattStr.length()){
                case 1:
                    rabattStr = "00" + rabattStr;
                    break;
                case 2:
                    rabattStr = "0" + rabattStr;
                    break;
            }

            // Temporären Rabatt wieder als Double umwandeln und in das GesamtPreis-Label einfügen
            tempRabatt = Double.parseDouble(rabattStr.substring(0, rabattStr.length()-2)
                    + "." + rabattStr.substring(rabattStr.length()-2));

            GesamtPreis.setText(GesamtPreis.getText() + "<font color='red' size='5' bgcolor='black'>- "
                    + String.valueOf(tempRabatt).replace(".",",") + "€"
                    + " " + (String) val[0] + "</font><br>");
        }

        // GesamtSumme aktualisieren und zum GesamtPreis-Label hinzufügen
        GesamtSumme = Korb.getGesamtPreis();
        GesamtPreis.setText(GesamtPreis.getText() + "Summe: " + String.valueOf(GesamtSumme).replace(".",",") + "€" + "</center></html>");

        // Repaint aufrufen, um die Aktualisierung anzuzeigen
        repaint();
    }

    @Override
    public void onGameOver(int score) {
        Korb.addRabatt("Rabatt-Spiel Snake", (double) score / 100);
        rabatt();
    }

    @Override
    public void toClose() {
        snake.dispose();
    }

    /**
     * ActionListener für das Rabatt-Spiel Snake.
     */
    protected class RabattSpielActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            RabattPanel = new SnakePanel();
            RabattPanel.addObserver(Kasse.this);
            snake = new Snake(RabattPanel);
        }
    }

    /**
     * ActionListener für die Aktionen beim Entfernen von Pizzen im Warenkorb.
     */
    protected class WarenActionListener implements ActionListener{
        protected Pizza[] Pizza;
        protected int Index;
        protected JPanel PizzaWrapper;
        protected int PizzaIndex;
        protected JPanel Panel;
        protected JComboBox<String> dropDown;

        /**
         * Konstruktor für den WarenActionListener.
         * @param Pizza Array zur Darstellung der Pizza in verschiedenen Größen
         * @param Index Index für die ausgewählte Pizza-Größe
         * @param PizzaWrapper Wrapper-Panel für die ausgewählte Pizza
         * @param dropDown Dropdown-Menü für die Pizza-Menge
         */
        WarenActionListener(Pizza[] Pizza, int Index, JPanel PizzaWrapper, JComboBox<String> dropDown){
            this.Index = Index;
            this.Pizza = Pizza;
            this.PizzaWrapper = PizzaWrapper;
            this.dropDown = dropDown;
            PizzaIndex = Korb.getAllToBuy().indexOf(Pizza);
            Panel = PizzaWrapper;
        }

        /**
         * Aktion, die beim Klicken auf den "Entfernen"-Button ausgelöst wird.
         * @param e ActionEvent, das die Aktion ausgelöst hat
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton Source = (JButton) e.getSource();
            int decrement = 0;

            // Versuche die Menge aus dem Dropdown-Menü zu extrahieren
            try{
                decrement = Integer.parseInt((String) dropDown.getSelectedItem());
            } catch (NumberFormatException ex){
                // Bei einer ungültigen Eingabe zeige eine Fehlermeldung und beende die Aktion
                Object[] message = new Object[]{
                        "Bitte gebe eine echte Zahle ein!",
                        "Gebe nicht '" + dropDown.getSelectedItem() + "' ein!"
                };

                JOptionPane.showMessageDialog(Kasse.this, message, "Ungültige Angabe", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verringere den Preis der Pizza entsprechend der ausgewählten Menge
            Pizza[Index].setPreis(-decrement);

            // Überprüfe, ob alle Größen der Pizza aus dem Warenkorb entfernt wurden
            if (Integer.parseInt(Pizza[0].toString()) <= 0
                    && Integer.parseInt(Pizza[1].toString()) <= 0
                    && Integer.parseInt(Pizza[2].toString()) <= 0
                    && Integer.parseInt(Pizza[3].toString()) <= 0){
                // Entferne das Panel und die Pizza aus dem Warenkorb
                Waren.remove(Panel);
                Korb.remove(Pizza);
            }

            // Überprüfe, ob der Warenkorb nach dem Entfernen leer ist
            if (Korb.getAllToBuy().isEmpty()){
                // Zeige eine Abschlussmeldung und schließe das Kasse-Fenster
                Object[] message = {
                        "Schade, dass Sie nichts für sich gefunden haben!"
                };
                JOptionPane.showMessageDialog(Kasse.this, message, "Auf Wiedersehen", JOptionPane.INFORMATION_MESSAGE);
                Kasse.this.dispose();
                new Start(Korb, getLocation());
                return;
            }

            // Erstelle eine neue Kasse mit aktualisierten Informationen und schließe das aktuelle Kasse-Fenster
            Waren = new ArrayList<JPanel>();
            new Kasse(Korb, Kasse.this.getLocation());
            Kasse.this.dispose();
        }
    }

    /**
     * PopupMenuListener für die Dropdown-Menüs in der Quittung.
     */
    protected class WarenPopUpListener implements PopupMenuListener{

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            Kasse.this.repaint();
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            Kasse.this.repaint();
        }
    }

    /**
     * AdjustmentListener für die Scrollbars in der Quittung.
     */
    protected class ScrollAdjustmentListener implements AdjustmentListener{

        @Override
        public void adjustmentValueChanged(AdjustmentEvent e) {
            Kasse.this.repaint();
        }
    }

    /**
     * ActionListener für den Bezahlen-Button.
     */
    protected class BezahlenActionListener implements ActionListener{

        /**
         * Aktion, die beim Klicken auf den Bezahlen-Button ausgelöst wird.
         * @param e ActionEvent, das die Aktion ausgelöst hat
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Nachricht für die Bestellbestätigung
            Object[] message = {
                    "Danke für ihre Bestellung von " + String.valueOf(GesamtSumme).replace(".",",") + "€!",
                    "Bis zum nächsten Mal!",
                    "Wollen Sie eine Quittung?"
            };
            int option = JOptionPane.showConfirmDialog(Kasse.this, message, "Auf Wiedersehen", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION){
                // Erstellen der Quittung
                JTextArea Area = new JTextArea();
                Area.setEditable(false);

                // Aktuelles Datum und Uhrzeit abrufen
                LocalDate aktuellesDatum = LocalDate.now();
                LocalTime aktuelleUhrzeit = LocalTime.now();

                // Formatierung des Datums
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formatiertesDatum = aktuellesDatum.format(dateFormatter);

                // Formatierung der Uhrzeit
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formatierteUhrzeit = aktuelleUhrzeit.format(timeFormatter);

                // Quittung erstellen
                Area.setText("----------------------------------------------------------\n"
                            + "                       QUITTUNG\n"
                            + "----------------------------------------------------------\n"
                            + "\n"
                            + "Datum: " + formatiertesDatum + "\n"
                            + "Uhrzeit: " + formatierteUhrzeit + "\n"
                            + "\n"
                            + "----------------------------------------------------------\n"
                            + "\n"
                            + "Artikel                  Menge    Preis    Gesamt\n"
                            + "----------------------------------------------------------\n");

                for (Pizza[] Pizzen : Korb.getAllToBuy()){
                    // Für jede Pizza-Größe in der Bestellung
                    Pizza Small = Pizzen[0];
                    Pizza Mid = Pizzen[1];
                    Pizza Normal = Pizzen[2];
                    Pizza Big = Pizzen[3];

                    // Informationen zu jeder Pizza in die Quittung einfügen
                    if (!Small.toString().equals("0")){
                        Area.setText(Area.getText()
                                + Small.getName() + " 25 cm | " + Small.toString() + " | " + String.valueOf(Small.getPreis()).replace(".",",")
                                + "€ | "
                                + String.valueOf((double) Math.round((Double.parseDouble(Small.toString()) * Small.getPreis()) * 100) / 100).replace(".",",")
                                + "€\n");
                    }
                    if (!Mid.toString().equals("0")){
                        Area.setText(Area.getText()
                                + Mid.getName() + " 28 cm | " + Mid.toString() + " | " + String.valueOf(Mid.getPreis()).replace(".",",")
                                + "€ | "
                                + String.valueOf((double) Math.round((Double.parseDouble(Mid.toString()) * Mid.getPreis()) * 100) / 100).replace(".",",")
                                + "€\n");
                    }
                    if (!Normal.toString().equals("0")){
                        Area.setText(Area.getText()
                                + Normal.getName() + " 32 cm | " + Normal.toString() + " | " + String.valueOf(Normal.getPreis()).replace(".",",")
                                + "€ | "
                                + String.valueOf((double) Math.round((Double.parseDouble(Normal.toString()) * Normal.getPreis()) * 100) / 100).replace(".",",")
                                + "€\n");
                    }
                    if (!Big.toString().equals("0")){
                        Area.setText(Area.getText()
                                + Big.getName() + " 38 cm | " + Big.toString() + " | " + String.valueOf(Big.getPreis()).replace(".",",")
                                + "€ | "
                                + String.valueOf((double) Math.round((Double.parseDouble(Big.toString()) * Big.getPreis()) * 100) / 100).replace(".",",")
                                + "€\n");
                    }
                }

                Area.setText(Area.getText()
                        + "----------------------------------------------------------\n"
                        + "\n"
                        + "Zwischensumme: " + String.valueOf(Korb.getGesamtPreisOhneRabatt()).replace(".",",") + "€\n");

                if (!Korb.getRabatte().isEmpty()){
                    // Rabatte hinzufügen, wenn vorhanden
                    Area.setText(Area.getText()
                            + "\n"
                            + "----------------------------------------------------------\n"
                            + "\n"
                            + "Rabatte\n"
                            + "----------------------------------------------------------\n");
                    for (Object[] rabatt : Korb.getRabatte()){
                        String Grund = (String) rabatt[0];
                        String Abzug =  String.valueOf((double) Math.round(Double.parseDouble(rabatt[1].toString()) * 100)/100).replace(".",",");

                        Area.setText(Area.getText()
                                + Grund + ": "
                                + Abzug + "€\n");
                    }

                    Area.setText(Area.getText()
                            + "----------------------------------------------------------\n\n");
                } else {
                    Area.setText(Area.getText() + "Keine Rabatte!\n");
                }

                Area.setText(Area.getText()
                        + "Gesamt: " + String.valueOf(Korb.getGesamtPreis()).replace(".",",") + "€\n\n"
                        + "Vielen Dank für Ihren Einkauf!\n\n"
                        + "----------------------------------------------------------");

                // Quittung in einem JScrollPane anzeigen
                JScrollPane FieldScrollPane = new JScrollPane(Area);
                FieldScrollPane.setPreferredSize(new Dimension(310,400));
                Object[] Quittung = {FieldScrollPane};
                JOptionPane.showMessageDialog(Kasse.this, Quittung, "Quittung", JOptionPane.PLAIN_MESSAGE);
            }
            Kasse.this.dispose();
        }
    }
}
