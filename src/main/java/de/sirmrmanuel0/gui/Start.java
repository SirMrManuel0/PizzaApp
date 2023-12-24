package de.sirmrmanuel0.gui;

import de.sirmrmanuel0.gui.custom_components.CustomFrame;
import de.sirmrmanuel0.gui.custom_components.PlaceholderTextField;
import de.sirmrmanuel0.gui.custom_components.RoundedButton;
import de.sirmrmanuel0.gui.custom_components.RoundedCornerPanel;
import de.sirmrmanuel0.logic.Warenkorb;
import de.sirmrmanuel0.pizza.Pizza;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Start extends CustomFrame {
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

    public Start(){
        super(1, 1.5, 1, 1.2, "Pizza Lieferung Deluxe", false);
        Korb = new Warenkorb();
        setBackgroundImage(loadImage("background.jpg"));
        initComponents(true);
    }

    public Start(Warenkorb Korb, Point Location){
        super(1, 1.5, 1, 1.2, "Pizza Lieferung Deluxe", false);
        this.Korb = Korb;
        setBackgroundImage(loadImage("background.jpg"));
        initComponents(false);
        Warenkorb.setText("<html>Zum Warenkorb<br>" + String.valueOf(Korb.getGesamtPreis()).replace(".", ",") + "€</html>");
        Warenkorb.setEnabled(true);
        setLocation(Location);
        setVisible(true);
    }

    protected void initComponents(boolean boolInitPizzen){
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
                new Kasse(Korb, getLocation());
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

        initMain(boolInitPizzen);

        Main.setBackground(new Color(0,0,0,0));

        add(Header);
        add(Main);
        add(Footer);
    }

    protected void initMain(boolean boolInitPizzen){
        PlaceholderTextField Suche = new PlaceholderTextField("Suche");
        Suche.addKeyListener(new SucheKeyListener());
        PizzenWrapper = new JPanel();
        Pizzen = new JPanel();

        Suche.setHorizontalAlignment(JTextField.CENTER);
        Suche.setBackground(Color.BLACK);
        Suche.setForeground(Color.WHITE);
        Suche.setBorder(new LineBorder(Color.BLACK));

        ArrayList<Pizza[]> instances = Korb.getAllPossibleInstances();
        Pizzen.setLayout(new GridLayout(instances.size(),1));
        Pizzen.setBackground(new Color(0,0,0,0));

        if (boolInitPizzen){
            initPizzen();
        } else {
            for (JPanel panel : Korb.getAllPossiblePanel()){
                Pizzen.add(panel);
            }
        }


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
        ObjPizzenList = Korb.getAllPossibleInstances();
        PizzenList = new ArrayList<JPanel>();
        GesuchtePizzenList = new ArrayList<JPanel>();
        boolean EvenChild = false;

        progress = new CustomFrame(1,8, 1, 10, "Lädt...", false);
        progress.setLayout(new GridLayout(3,1));

        JPanel filler = new JPanel();
        JPanel filler1 = new JPanel();

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        progress.add(filler);
        progress.add(progressBar);
        progress.add(filler1);

        MySwingWorker worker = new MySwingWorker();
        worker.execute();

        progress.setVisible(true);
    }

    protected void load(int Index){
        boolean EvenChild = (Index+1) % 2 == 0;
        Pizza[] Pizza = ObjPizzenList.get(Index);
        JPanel PizzaWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0 ,0));
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
        Korb.addAllPossiblePanel(PizzaWrapper);
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
        protected Pizza[] Pizza;

        public PizzenActionListener(int Index, Pizza[] Pizza){
            this.Index = Index;
            this.Pizza = Pizza;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
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

            Object[] message = {
                    "Größe: " + size + "cm",
                    "Einzelpreis: " + String.valueOf(Pizza[Index].getPreis()).replace(".", ",") + "€",
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
            Pizza[Index].setPreis(IncreaseAnzahlVal);

            if (!Korb.getAllToBuy().contains(Pizza)){
                Korb.add(Pizza);
            }

            Warenkorb.setText("<html>Zum Warenkorb<br>" + String.valueOf(Korb.getGesamtPreis()).replace(".", ",") + "€</html>");
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


            for (Pizza[] Pizza : ObjPizzenList) {
                if (Pizza[0].getName().substring(5).toLowerCase().contains(SuchEingabe.toLowerCase())){
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

    protected class MySwingWorker extends SwingWorker<Void, Integer> {

        @Override
        protected Void doInBackground() throws Exception {
            // Simulate time-consuming task
            int totalTasks = Korb.getAllPossible().size();
            for (int i = 0; i < totalTasks; i++) {
                // Perform your initialization task here
                load(i);

                // Update progress
                int progress = (int) ((i / (double) totalTasks) * 100);
                publish(progress);
            }

            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {
            // Update the progress bar while the background task is running
            for (Integer progress : chunks) {
                progressBar.setValue(progress);
            }
        }

        @Override
        protected void done() {
            progress.dispose();
            Start.this.setVisible(true);
        }
    }
}
