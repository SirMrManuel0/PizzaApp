package de.sirmrmanuel0.gui;

import de.sirmrmanuel0.gui.custom_components.*;
import de.sirmrmanuel0.logic.Warenkorb;
import de.sirmrmanuel0.pizza.Pizza;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Start extends CustomFrame {
    // Deklaration der GUI-Komponenten
    protected JPanel Main;
    protected JPanel Pizzen;
    protected JPanel PizzenWrapper;
    protected JScrollPane PizzenScroll;
    protected JButton Warenkorb;
    protected ArrayList<JPanel> PizzenList;
    protected ArrayList<Pizza[]> ObjPizzenList;
    protected ArrayList<JPanel> GesuchtePizzenList;
    protected Warenkorb Korb;
    protected JProgressBar progressBar;
    protected CustomFrame progress;

    /**
     * Konstruktor für das Startfenster ohne vorherigen Warenkorb.
     */
    public Start(){
        super(1, 1.5, 1, 1.2, "Pizza Lieferung Deluxe", false);
        Korb = new Warenkorb();
        setBackgroundImage(loadImage("background.jpg"));
        initComponents(true);
    }

    /**
     * Konstruktor für das Startfenster mit einem vorherigen Warenkorb und bestimmter Position.
     * @param Korb Der vorherige Warenkorb.
     * @param Location Die Position des Fensters.
     */
    public Start(Warenkorb Korb, Point Location){
        super(1, 1.5, 1, 1.2, "Pizza Lieferung Deluxe", false);
        this.Korb = Korb;
        setBackgroundImage(loadImage("background.jpg"));
        initComponents(false);
        Warenkorb.setText("<html><center>Zum Warenkorb<br>" + String.valueOf(this.Korb.getGesamtPreis()).replace(".", ",") + "€</center></html>");
        Warenkorb.setEnabled(true);
        setLocation(Location);
        setVisible(true);
    }

    /**
     * Initialisiert die GUI-Komponenten.
     * @param boolInitPizzen true, wenn die Pizzen initialisiert werden sollen, sonst false.
     */
    protected void initComponents(boolean boolInitPizzen){
        // Initialisierung der Haupt-, Header- und Footer-Panels
        JPanel Footer = new JPanel(new GridBagLayout());
        JPanel Header = new JPanel(new GridBagLayout());
        Main = new JPanel();

        // Initialisierung der Buttons im Header
        Warenkorb = new JButton("Zum Warenkorb");
        Warenkorb.setPreferredSize(getScaledDimension(50, 20));
        Warenkorb.setBackground(Color.BLACK);
        Warenkorb.setForeground(Color.WHITE);
        Warenkorb.setFocusPainted(false);
        Warenkorb.setEnabled(false);

        // Hinzufügen von ActionListeners zu den Buttons
        Warenkorb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Beim Klick auf den Warenkorb-Button: Schließe das aktuelle Fenster und öffne die Kasse mit dem aktuellen Warenkorb und der Position des aktuellen Fensters.
                dispose();
                new Kasse(Korb, getLocation());
            }
        });

        JLabel Title = new JLabel("Pizza Lieferung Deluxe");
        Title.setFont(new Font("Times New Roman", Font.BOLD, 25));
        Title.setForeground(Color.WHITE);

        JButton Impressum = new JButton("Impressum");
        JButton Datenschutz = new JButton("Datenschutz");
        JButton Kontakt = new JButton("Kontaktieren Sie uns!");

        // Einstellungen für die Schaltflächen im Header
        Impressum.setForeground(Color.WHITE);
        Datenschutz.setForeground(Color.WHITE);
        Kontakt.setForeground(Color.WHITE);

        Impressum.setBackground(Color.BLACK);
        Datenschutz.setBackground(Color.BLACK);
        Kontakt.setBackground(Color.BLACK);

        Impressum.setFocusPainted(false);
        Datenschutz.setFocusPainted(false);
        Kontakt.setFocusPainted(false);

        // Hinzufügen von ActionListeners zu den Schaltflächen im Header
        Impressum.addActionListener(new FooterActionListener());
        Datenschutz.addActionListener(new FooterActionListener());
        Kontakt.addActionListener(new FooterActionListener());

        // Laden des Logos und Skalieren des Bildes
        ImageIcon originalIcon = new ImageIcon(loadImage("logo_prop2.jpg"));
        Image resizedImage = originalIcon.getImage();
        resizedImage = resizedImage.getScaledInstance(
                getWidth() / 11,
                getHeight() / 10,
                Image.SCALE_SMOOTH
        );

        ImageIcon Logo = new ImageIcon(resizedImage);
        JLabel LogoWrapper = new JLabel(Logo);

        // Einstellungen für Header-, Haupt- und Footer-Panels
        Header.setPreferredSize(getScaledDimension(1,10));
        Main.setPreferredSize(getScaledDimension(1,1,7.5,10));
        Footer.setPreferredSize(getScaledDimension(1,10));

        Header.setBackground(new Color(0,0,0,90));
        Footer.setBackground(new Color(0,0,0,150));

        // Layout-Einstellungen für den Header
        GridBagConstraints headerGbc = new GridBagConstraints();
        headerGbc.weightx = 1;
        headerGbc.fill = GridBagConstraints.HORIZONTAL;

        Header.add(LogoWrapper, headerGbc);
        Header.add(Title, headerGbc);
        Header.add(Warenkorb, headerGbc);

        // Layout-Einstellungen für den Footer
        GridBagConstraints footerGbc = new GridBagConstraints();
        footerGbc.anchor = GridBagConstraints.CENTER;
        footerGbc.insets = new Insets(0, 10, 0, 10);

        Footer.add(Impressum, footerGbc);
        Footer.add(Datenschutz, footerGbc);
        Footer.add(Kontakt, footerGbc);

        // Initialisierung des Hauptfensters
        initMain(boolInitPizzen);

        Main.setBackground(new Color(0,0,0,0));

        // Hinzufügen der Panels zum Hauptfenster
        add(Header);
        add(Main);
        add(Footer);
    }

    /**
     * Initialisiert das Hauptpanel, in dem die Pizzen angezeigt werden.
     * @param boolInitPizzen true, wenn die Pizzen initialisiert werden sollen, sonst false.
     */
    protected void initMain(boolean boolInitPizzen){
        // Erstellung eines Platzhaltertextfelds für die Suche
        PlaceholderTextField Suche = new PlaceholderTextField("Suche");
        Suche.addKeyListener(new SucheKeyListener());
        PizzenWrapper = new JPanel();
        Pizzen = new JPanel();

        // Einstellungen für das Suchfeld
        Suche.setHorizontalAlignment(JTextField.CENTER);
        Suche.setBackground(Color.BLACK);
        Suche.setForeground(Color.WHITE);
        Suche.setBorder(new LineBorder(Color.BLACK));

        // Abrufen aller möglichen Pizzainstanzen im Warenkorb
        ArrayList<Pizza[]> instances = Korb.getAllPossibleInstances();
        Pizzen.setLayout(new GridLayout(instances.size(),1));
        Pizzen.setBackground(new Color(0,0,0,0));

        // Überprüfen, ob Pizzen initialisiert werden sollen oder bereits vorhandene Panels verwendet werden sollen
        if (boolInitPizzen){
            initPizzen();
        } else {
            for (UpdaterPanel panel : Korb.getAllPossiblePanel()){
                panel.updateAll(Korb, Warenkorb, this);
                Pizzen.add(panel);
            }
        }

        // Erstellung eines Scroll-Panels für die Pizzen
        PizzenScroll = new JScrollPane(Pizzen);
        PizzenScroll.setBackground(new Color(0,0,0,0));

        // Einstellungen für die Scroll-Bar
        JScrollBar verticalScrollBar = PizzenScroll.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);
        verticalScrollBar.setBlockIncrement(60);

        PizzenScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                repaint();
            }
        });

        // Einstellungen für das Suchfeld und das Wrapper-Panel
        Suche.setPreferredSize(getScaledDimension(10, 20));
        PizzenWrapper.setPreferredSize(new Dimension(getWidth(),
                getScaledDimension(1,1,7.5,10).height
                        - getScaledDimension(10, 20).height));

        PizzenWrapper.setLayout(new GridLayout(1,1));
        Main.setLayout(new BorderLayout());

        PizzenWrapper.setBackground(new Color(0,0,0,0));

        // Hinzufügen des PizzenScrolls zum PizzenWrapper und Platzierung im Main-Panel
        PizzenWrapper.add(PizzenScroll);
        Main.add(Suche, BorderLayout.NORTH);
        Main.add(PizzenWrapper, BorderLayout.SOUTH);
    }

    /**
     * Initialisiert die Pizzen, indem sie aus dem Warenkorb geladen werden.
     */
    protected void initPizzen(){
        // Initialisierung der Listen und Variablen für die Pizzen-Instanzen und den Ladevorgang
        ObjPizzenList = Korb.getAllPossibleInstances();
        PizzenList = new ArrayList<>();
        GesuchtePizzenList = new ArrayList<>();

        // Initialisierung und Konfiguration des Fortschrittsdialogs
        progress = new CustomFrame(1,8, 1, 10, "Lädt...", false);
        progress.setLayout(new GridLayout(3,1));

        // Füllen des Fortschrittsdialogs mit leeren Panels und einer Fortschrittsleiste
        JPanel filler = new JPanel();
        JPanel filler1 = new JPanel();
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progress.add(filler);
        progress.add(progressBar);
        progress.add(filler1);

        // Starten des SwingWorkers, um die Pizzen im Hintergrund zu laden
        MySwingWorker worker = new MySwingWorker();
        worker.execute();

        // Anzeigen des Fortschrittsdialogs
        progress.setVisible(true);
    }

    /**
     * Lädt eine Pizza mit einem bestimmten Index.
     * @param Index Index der Pizza, die geladen werden soll.
     */
    protected void load(int Index){
        // Überprüfen, ob es sich um ein gerades oder ungerades Kind handelt
        boolean EvenChild = (Index+1) % 2 == 0;

        // Holen der Pizzainstanz für den aktuellen Index
        Pizza[] Pizza = ObjPizzenList.get(Index);

        // Erstellen von Panels und Buttons für die Pizzagröße
        UpdaterPanel PizzaWrapper = new UpdaterPanel(new FlowLayout(FlowLayout.CENTER, 0 ,0));
        RoundedCornerPanel PizzaPanel = new RoundedCornerPanel(25);
        RoundedButton Small = new RoundedButton( "25 cm " + Pizza[0].getPreis() + "€");
        RoundedButton Mid = new RoundedButton("28 cm " + Pizza[1].getPreis() + "€");
        RoundedButton Normal = new RoundedButton("32 cm " + Pizza[2].getPreis() + "€");
        RoundedButton Big = new RoundedButton("38 cm " + Pizza[3].getPreis() + "€");
        JLabel Name = new JLabel(Pizza[0].getName());
        JLabel Beschreibung = new JLabel(Pizza[0].getBeschreibung());
        ImageIcon LogoIcon = new ImageIcon(loadImage("logo_prop1.png"));
        JLabel Icon = new JLabel();
        JPanel IconPanel = new JPanel();
        JPanel Schrift = new JPanel();
        JPanel Buttons = new JPanel();

        // Skalieren des Logo-Bilds
        Image resizedImage = LogoIcon.getImage();
        resizedImage = resizedImage.getScaledInstance(
                getWidth() / 15,
                getHeight() / 14,
                Image.SCALE_SMOOTH
        );

        Icon.setIcon(new ImageIcon(resizedImage));

        // Festlegen der Hintergrundfarben basierend auf geraden oder ungeraden Kindern
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

        // Konfiguration der Buttons
        Small.setFocusPainted(false);
        Mid.setFocusPainted(false);
        Normal.setFocusPainted(false);
        Big.setFocusPainted(false);

        Small.setBorderPainted(false);
        Mid.setBorderPainted(false);
        Normal.setBorderPainted(false);
        Big.setBorderPainted(false);

        // Hinzufügen von MouseListenern für das Hervorheben von Buttons
        Small.addMouseListener(new PizzenMouseAdapter(!EvenChild));
        Mid.addMouseListener(new PizzenMouseAdapter(!EvenChild));
        Normal.addMouseListener(new PizzenMouseAdapter(!EvenChild));
        Big.addMouseListener(new PizzenMouseAdapter(!EvenChild));

        // Hinzufügen von ActionListenern für die Button-Funktionalität
        Small.addActionListener(new PizzenActionListener(0, Pizza, Korb, Warenkorb, this));
        Mid.addActionListener(new PizzenActionListener(1, Pizza, Korb, Warenkorb, this));
        Normal.addActionListener(new PizzenActionListener(2, Pizza, Korb, Warenkorb, this));
        Big.addActionListener(new PizzenActionListener(3, Pizza, Korb, Warenkorb, this));

        // Hinzufügen der Buttons zum UpdaterPanel
        PizzaWrapper.addToUpdate(Small);
        PizzaWrapper.addToUpdate(Mid);
        PizzaWrapper.addToUpdate(Normal);
        PizzaWrapper.addToUpdate(Big);

        // Konfiguration von Schrift- und Textelementen
        Name.setFont(new Font("SansSerif", Font.BOLD, 20));
        Beschreibung.setFont(new Font("SansSerif", Font.PLAIN, 12));
        Beschreibung.setForeground(Color.WHITE);
        Name.setForeground(Color.WHITE);

        // Festlegen der Größen der Panels
        PizzaPanel.setPreferredSize(getScaledDimension(1.1,10));
        IconPanel.setPreferredSize(getScaledDimension(15,14));
        Schrift.setPreferredSize(getScaledDimension(2.4,11));
        Buttons.setPreferredSize(getScaledDimension(2.8,11));

        // Konfiguration von Layouts
        Buttons.setLayout(new GridLayout(2,2));
        Schrift.setLayout(new GridLayout(2,1));

        // Festlegen der Hintergrundfarbe des Wrapper-Panels
        PizzaWrapper.setBackground(new Color(0,0,0,0));

        // Hinzufügen von Elementen zu den Panels
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

        // Hinzufügen des Pizza-Panels zum Pizzen-Panel und zur Liste
        PizzaWrapper.add(PizzaPanel);
        Pizzen.add(PizzaWrapper);
        PizzenList.add(PizzaWrapper);
        Korb.addAllPossiblePanel(PizzaWrapper);
    }

    /**
     * MouseAdapter für die Pizzen-Buttons.
     */
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

    /**
     * ActionListener für die Aktionen beim Hinzufügen von Pizzen zum Warenkorb.
     */
    public static class PizzenActionListener implements ActionListener{
        protected int Index;
        protected Pizza[] Pizza;
        protected Warenkorb actionWarenkorb;
        protected JButton actionWarenkorbButton;
        protected CustomFrame parent;

        /**
         * Konstruktor für den PizzenActionListener.
         * @param Index Index der ausgewählten Pizza-Größe
         * @param Pizza Array der Pizza-Größen
         * @param actionWarenkorb Warenkorb, in den die Pizza hinzugefügt wird
         * @param Warenkorb Button zur Anzeige des Warenkorbs
         * @param parent Elternfenster
         */
        public PizzenActionListener(int Index, Pizza[] Pizza, Warenkorb actionWarenkorb, JButton Warenkorb, CustomFrame parent){
            this.parent = parent;
            this.actionWarenkorbButton = Warenkorb;
            this.actionWarenkorb = actionWarenkorb;
            this.Index = Index;
            this.Pizza = Pizza;
        }

        /**
         * Aktualisiert die Attribute des Listeners.
         * @param actionWarenkorb Warenkorb, in den die Pizza hinzugefügt wird
         * @param Warenkorb Button zur Anzeige des Warenkorbs
         * @param parent Elternfenster
         */
        public void updateListener(Warenkorb actionWarenkorb, JButton Warenkorb, CustomFrame parent){
            this.parent = parent;
            this.actionWarenkorb = actionWarenkorb;
            this.actionWarenkorbButton = Warenkorb;
        }

        /**
         * Aktion, die beim Klicken auf einen Pizza-Button ausgelöst wird.
         * @param e ActionEvent, das die Aktion ausgelöst hat
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Erlangung von Informationen über die ausgewählte Pizza
            String Name = Pizza[Index].getName();
            String size = "";
            JComboBox<Integer> Anzahl = new JComboBox<>(new Integer[]{1,2,3,4,5,6,7,8,9,10});
            Anzahl.setEditable(true);

            switch(Index){
                case 0:{
                    size = "25";
                    break;
                }
                case 1:{
                    size = "28";
                    break;
                }
                case 2:{
                    size = "32";
                    break;
                }
                case 3:{
                    size = "38";
                    break;
                }
            }

            // Erstellen der Anzeige für den Benutzer
            Object[] message = {
                    "Größe: " + size + "cm",
                    "Einzelpreis: " + String.valueOf(Pizza[Index].getPreis()).replace(".", ",") + "€",
                    "Anzahl:", Anzahl
            };

            // Anzeige des Dialogs für die Benutzereingabe
            int option = JOptionPane.showConfirmDialog(
                    parent,
                    message,
                    Name + " Kaufen",
                    JOptionPane.OK_CANCEL_OPTION
            );

            // Abbrechen, wenn der Benutzer "Abbrechen" wählt
            if (option != JOptionPane.OK_OPTION)
                return;

            String AnzahlValue = String.valueOf(Anzahl.getSelectedItem());
            int IncreaseAnzahlVal;

            try{
                // Parsen und Validieren der eingegebenen Anzahl
                IncreaseAnzahlVal = Integer.parseInt(AnzahlValue);

                if (IncreaseAnzahlVal < 0){
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException ex){
                // Fehlermeldung bei ungültiger Eingabe
                JOptionPane.showMessageDialog(
                        parent,
                        "Ungültige Eingabe für Anzahl. Bitte geben Sie eine gültige Zahl ein.",
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Aktivieren des Warenkorb-Buttons und Aktualisieren der Pizza-Daten
            actionWarenkorbButton.setEnabled(true);
            Pizza[Index].setPreis(IncreaseAnzahlVal);

            // Hinzufügen der Pizza zum Warenkorb, wenn noch nicht vorhanden
            if (!actionWarenkorb.getAllToBuy().contains(Pizza)){
                actionWarenkorb.add(Pizza);
            }

            // Aktualisieren des Texts auf dem Warenkorb-Button
            actionWarenkorbButton.setText("<html><center>Zum Warenkorb<br>"
                    + String.valueOf(actionWarenkorb.getGesamtPreis()).replace(".", ",")
                    + "€</center></html>");
        }
    }

    /**
     * ActionListener für die Aktionen in der Footer-Leiste.
     */
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

    /**
     * KeyListener für die Suche nach Pizzen.
     */
    protected class SucheKeyListener implements KeyListener{

        /**
         * Behandelt das keyTyped-Event für die Suchfeld-Eingabe.
         * @param e KeyEvent, das das Event ausgelöst hat
         */
        @Override
        public void keyTyped(KeyEvent e) {
            // Erlangung des Quell-JTextField-Objekts
            JTextField Source = (JTextField) e.getSource();
            String SuchEingabe = Source.getText(); // Eingabe des Benutzers
            GesuchtePizzenList = new ArrayList<JPanel>(); // Liste für gefundene Pizzen zurücksetzen

            // Durchsuchen aller Pizzen nach Übereinstimmungen mit der Sucheingabe
            for (Pizza[] Pizza : ObjPizzenList) {
                if (Pizza[0].getName().substring(5).toLowerCase().contains(SuchEingabe.toLowerCase())){
                    int Index = ObjPizzenList.indexOf(Pizza);
                    GesuchtePizzenList.add(PizzenList.get(Index));
                }
            }

            // Neues JPanel für gefundene Pizzen erstellen und hinzufügen
            Pizzen = new JPanel();
            Pizzen.setLayout(new GridLayout(GesuchtePizzenList.size(),1));
            Pizzen.setBackground(new Color(0,0,0,0));

            for (JPanel Pizza : GesuchtePizzenList){
                Pizzen.add(Pizza);
            }

            // JPanel aktualisieren
            Pizzen.revalidate();
            Pizzen.repaint();

            GesuchtePizzenList = new ArrayList<JPanel>();

            // Neue JScrollPane für das aktualisierte JPanel erstellen
            PizzenScroll = new JScrollPane(Pizzen);
            PizzenScroll.setBackground(new Color(0,0,0,0));

            JScrollBar verticalScrollBar = PizzenScroll.getVerticalScrollBar();
            verticalScrollBar.setUnitIncrement(20);
            verticalScrollBar.setBlockIncrement(60);

            // AdjustmentListener hinzufügen
            PizzenScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent e) {
                    repaint();
                }
            });

            // JScrollPane aktualisieren
            PizzenScroll.revalidate();
            PizzenScroll.repaint();

            // Aktualisierte Pizzen in das PizzenWrapper-Panel einfügen
            PizzenWrapper.removeAll();
            PizzenWrapper.add(PizzenScroll);

            // PizzenWrapper aktualisieren
            PizzenWrapper.revalidate();
            PizzenWrapper.repaint();
        }

        /**
         * Wird nicht verwendet.
         * @param e KeyEvent, das das Event ausgelöst hat
         */
        @Override
        public void keyPressed(KeyEvent e) {}

        /**
         * Wird nicht verwendet.
         * @param e KeyEvent, das das Event ausgelöst hat
         */
        @Override
        public void keyReleased(KeyEvent e) {}
    }

    /**
     * SwingWorker für den asynchronen Ladevorgang der Pizzen.
     */
    protected class MySwingWorker extends SwingWorker<Void, Integer> {

        /**
         * Führt eine zeitaufwändige Aufgabe im Hintergrund aus.
         * @return null
         * @throws Exception
         */
        @Override
        protected Void doInBackground() throws Exception {
            int totalTasks = Korb.getAllPossible().size();

            // Durchlaufen aller Aufgaben
            for (int i = 0; i < totalTasks; i++) {
                load(i);

                // Fortschritt aktualisieren und veröffentlichen
                int progress = (int) ((i / (double) totalTasks) * 100);
                publish(progress);
            }

            return null;
        }

        /**
         * Aktualisiert die Fortschrittsanzeige während der Hintergrundarbeit.
         * @param chunks Liste von Integer-Werten, die den Fortschritt repräsentieren
         */
        @Override
        protected void process(List<Integer> chunks) {
            for (Integer progress : chunks) {
                progressBar.setValue(progress);
            }
        }

        /**
         * Wird aufgerufen, wenn die Hintergrundarbeit abgeschlossen ist.
         */
        @Override
        protected void done() {
            // Fortschrittsfenster schließen und das Hauptfenster anzeigen
            progress.dispose();
            Start.this.setVisible(true);
        }
    }
}
