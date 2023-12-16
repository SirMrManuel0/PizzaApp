package de.sirmrmanuel0.gui;

import de.sirmrmanuel0.pizza.BetterPizza;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class Warenkorb extends CustomFrame{
    protected ArrayList<BetterPizza> WarenListe;
    protected JPanel Main;
    protected BigDecimal GesamtSumme;
    protected JScrollPane QuittungScroll;
    protected JPanel QuittungWrapper;
    protected JPanel Quittung;
    protected ArrayList<JPanel> Waren;
    protected JLabel GesamtPreis;

    public Warenkorb(ArrayList<BetterPizza> WarenListe, Point Location, BigDecimal GesammtSumme){
        super(1.5, 1, 1, 1.2, "Pizza Lieferung Deluxe - Warenkorb", false);
        this.WarenListe = WarenListe;
        this.GesamtSumme = GesammtSumme;
        Waren = new ArrayList<JPanel>();
        setBackgroundImage(loadImage("background.jpg"));
        initComponents();
        setLocation(Location);
        setVisible(true);
    }

    protected void initComponents(){
        JPanel Footer = new JPanel(new GridBagLayout());
        JPanel Header = new JPanel(new GridBagLayout());
        Main = new JPanel();


        JButton Back = new JButton("Zurück");
        Back.setPreferredSize(getScaledDimension(50, 20));
        Back.setBackground(Color.BLACK);
        Back.setForeground(Color.WHITE);
        Back.setFocusPainted(false);
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Start(WarenListe, getLocation());
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

        Impressum.addActionListener(new Start.FooterActionListener());
        Datenschutz.addActionListener(new Start.FooterActionListener());
        Kontakt.addActionListener(new Start.FooterActionListener());


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
        Main.setPreferredSize(getScaledDimension(1,1.1,7.5,10));
        Footer.setPreferredSize(getScaledDimension(1,10));


        Header.setBackground(new Color(0,0,0,90));
        Footer.setBackground(new Color(0,0,0,150));

        GridBagConstraints headerGbc = new GridBagConstraints();
        headerGbc.weightx = 1;
        headerGbc.fill = GridBagConstraints.HORIZONTAL;

        Header.add(LogoWrapper, headerGbc);
        Header.add(Title, headerGbc);
        Header.add(Back, headerGbc);

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
        Main.setLayout(new GridLayout(1,2));
        QuittungWrapper = new JPanel();
        Quittung = new JPanel();
        JPanel Bezahlen = new JPanel();
        JPanel BezahlenButtonPanel = new JPanel(new GridLayout(3,1));
        JPanel Filler1 = new JPanel();
        JPanel Filler2 = new JPanel();
        JPanel GesamtPreisPanel = new JPanel(new GridBagLayout());
        JButton BezahlenButton = new JButton("Bezahlen");

        Bezahlen.setLayout(new GridLayout(2,1));
        Bezahlen.setBackground(new Color(0,0,0,0));
        GesamtPreis = new JLabel("Summe: " + String.valueOf(GesamtSumme).replace(".",",") + "€");
        GesamtPreis.setForeground(Color.WHITE);
        GesamtPreis.setFont(new Font("SansSerif", Font.BOLD, 25));

        Filler1.setBackground(new Color(0,0,0,0));
        Filler2.setBackground(new Color(0,0,0,0));
        BezahlenButtonPanel.setBackground(new Color(0,0,0,0));
        GesamtPreisPanel.setBackground(new Color(0,0,0,90));

        BezahlenButton.setBackground(Color.BLACK);
        BezahlenButton.setForeground(Color.WHITE);
        BezahlenButton.setFocusPainted(false);


        BezahlenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] message = {
                        "Danke für ihre Bestellung von " + String.valueOf(GesamtSumme).replace(".",",") + "€!",
                        "Bis zum nächsten Mal!"
                };

                JOptionPane.showMessageDialog(Warenkorb.this, message, "Auf Wiedersehen", JOptionPane.INFORMATION_MESSAGE);
                Warenkorb.this.dispose();
            }
        });

        initQuittung();

        QuittungWrapper.setBackground(new Color(0,0,0,0));
        Quittung.setBackground(new Color(0,0,0,0));

        Quittung.setLayout(new GridLayout(Waren.size(),1));


        QuittungScroll = new JScrollPane(Quittung);
        QuittungScroll.setBackground(new Color(0,0,0,0));

        QuittungScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                repaint();
            }
        });

        QuittungScroll.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                repaint();
            }
        });

        JScrollBar verticalScrollBar = QuittungScroll.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);
        verticalScrollBar.setBlockIncrement(60);

        JScrollBar horizontalScrollBar = QuittungScroll.getHorizontalScrollBar();
        horizontalScrollBar.setUnitIncrement(20);
        horizontalScrollBar.setBlockIncrement(60);

        QuittungWrapper.setLayout(new GridLayout(1,1));
        QuittungWrapper.add(QuittungScroll);

        BezahlenButtonPanel.add(Filler1);
        BezahlenButtonPanel.add(Filler2);
        BezahlenButtonPanel.add(BezahlenButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        GesamtPreisPanel.add(GesamtPreis, gbc);

        Bezahlen.add(GesamtPreisPanel);
        Bezahlen.add(BezahlenButtonPanel);

        Main.add(QuittungWrapper);
        Main.add(Bezahlen);
    }

    protected void initQuittung(){
        boolean EvenChild = false;
        for (BetterPizza Pizza : WarenListe){
            JPanel PizzaWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0 ,0));
            RoundedCornerPanel PizzaPanel = new RoundedCornerPanel(25);
            RoundedButton DeleteSmall = new RoundedButton( Pizza.getSizes()[0] + "cm entfernen");
            RoundedButton DeleteMid = new RoundedButton(Pizza.getSizes()[1] + "cm entfernen");
            RoundedButton DeleteNormal = new RoundedButton(Pizza.getSizes()[2] + "cm entfernen");
            RoundedButton DeleteBig = new RoundedButton(Pizza.getSizes()[3] + "cm entfernen");
            JPanel Mengen = new JPanel();
            JLabel MengeKlein = new JLabel("25cm x " + Pizza.getAnzahl()[0] + " x " + String.valueOf(Pizza.getPreise()[0]).replace(".",",") + "€");
            JLabel MengeMid = new JLabel("28cm x " + Pizza.getAnzahl()[1] + " x " + String.valueOf(Pizza.getPreise()[1]).replace(".",",") + "€");
            JLabel MengeNormal = new JLabel("32cm x " + Pizza.getAnzahl()[2] + " x " + String.valueOf(Pizza.getPreise()[2]).replace(".",",") + "€");
            JLabel MengeBig = new JLabel("38cm x " + Pizza.getAnzahl()[3] + " x " + String.valueOf(Pizza.getPreise()[3]).replace(".",",") + "€");
            JLabel Name = new JLabel(Pizza.getName());
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

                DeleteSmall.setBackground(Color.lightGray);
                DeleteMid.setBackground(Color.lightGray);
                DeleteNormal.setBackground(Color.lightGray);
                DeleteBig.setBackground(Color.lightGray);
                EvenChild = false;
            } else {
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


            DeleteSmall.setFocusPainted(false);
            DeleteMid.setFocusPainted(false);
            DeleteNormal.setFocusPainted(false);
            DeleteBig.setFocusPainted(false);

            DeleteSmall.setBorderPainted(false);
            DeleteMid.setBorderPainted(false);
            DeleteNormal.setBorderPainted(false);
            DeleteBig.setBorderPainted(false);

            DeleteSmall.addMouseListener(new Start.PizzenMouseAdapter(!EvenChild));
            DeleteMid.addMouseListener(new Start.PizzenMouseAdapter(!EvenChild));
            DeleteNormal.addMouseListener(new Start.PizzenMouseAdapter(!EvenChild));
            DeleteBig.addMouseListener(new Start.PizzenMouseAdapter(!EvenChild));

            DeleteSmall.addActionListener(new WarenActionListener(Pizza, 0, PizzaWrapper));
            DeleteMid.addActionListener(new WarenActionListener(Pizza,1, PizzaWrapper));
            DeleteNormal.addActionListener(new WarenActionListener(Pizza,2, PizzaWrapper));
            DeleteBig.addActionListener(new WarenActionListener(Pizza,3, PizzaWrapper));

            if (Pizza.getAnzahl()[0] == 0){
                DeleteSmall.setEnabled(false);
            }
            if (Pizza.getAnzahl()[1] == 0){
                DeleteMid.setEnabled(false);
            }
            if (Pizza.getAnzahl()[2] == 0){
                DeleteNormal.setEnabled(false);
            }
            if (Pizza.getAnzahl()[3] == 0){
                DeleteBig.setEnabled(false);
            }

            Mengen.setBackground(new Color(0,0,0,0));
            Mengen.setLayout(new GridLayout(2,2));

            MengeKlein.setFont(new Font("SansSerif", Font.PLAIN, 12));
            MengeMid.setFont(new Font("SansSerif", Font.PLAIN, 12));
            MengeNormal.setFont(new Font("SansSerif", Font.PLAIN, 12));
            MengeBig.setFont(new Font("SansSerif", Font.PLAIN, 12));

            MengeKlein.setForeground(Color.WHITE);
            MengeMid.setForeground(Color.WHITE);
            MengeNormal.setForeground(Color.WHITE);
            MengeBig.setForeground(Color.WHITE);

            Name.setFont(new Font("SansSerif", Font.BOLD, 20));
            Name.setForeground(Color.WHITE);

            PizzaPanel.setPreferredSize(getScaledDimension(1.1,10));
            IconPanel.setPreferredSize(getScaledDimension(15,14));
            Schrift.setPreferredSize(getScaledDimension(2.4,11));
            Buttons.setPreferredSize(getScaledDimension(2.8,11));

            Buttons.setLayout(new GridLayout(2,2));
            Schrift.setLayout(new GridLayout(1,2));

            PizzaWrapper.setBackground(new Color(0,0,0,0));

            IconPanel.add(Icon);

            Mengen.add(MengeKlein);
            Mengen.add(MengeMid);
            Mengen.add(MengeNormal);
            Mengen.add(MengeBig);

            Schrift.add(Name);
            Schrift.add(Mengen);

            Buttons.add(DeleteSmall);
            Buttons.add(DeleteMid);
            Buttons.add(DeleteNormal);
            Buttons.add(DeleteBig);

            PizzaPanel.add(IconPanel);
            PizzaPanel.add(Schrift);
            PizzaPanel.add(Buttons);

            PizzaWrapper.add(PizzaPanel);
            Quittung.add(PizzaWrapper);
            Waren.add(PizzaWrapper);
        }
    }


    protected class WarenActionListener implements ActionListener{
        protected BetterPizza Pizza;
        protected int Index;
        protected JPanel PizzaWrapper;
        protected int PizzaIndex;
        protected JPanel Panel;

        WarenActionListener(BetterPizza Pizza, int Index, JPanel PizzaWrapper){
            this.Index = Index;
            this.Pizza = Pizza;
            this.PizzaWrapper = PizzaWrapper;
            PizzaIndex = WarenListe.indexOf(Pizza);
            Panel = PizzaWrapper;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton Source = (JButton) e.getSource();
            int[] NeueAnzahl = Pizza.getAnzahl();
            NeueAnzahl[Index] -= 1;
            Pizza.setAnzahl(NeueAnzahl);

            if (Arrays.equals(NeueAnzahl, new int[]{0, 0, 0, 0})){
                Waren.remove(Panel);
                WarenListe.remove(Pizza);
            }

            GesamtSumme = GesamtSumme.subtract(BigDecimal.valueOf(Pizza.getPreise()[Index]));

            if (WarenListe.isEmpty()){
                Object[] message = {
                        "Schade, dass Sie nichts für sich gefunden haben!",
                        "Bis zum nächsten mal!"
                };
                JOptionPane.showMessageDialog(Warenkorb.this, message, "Auf Wiedersehen", JOptionPane.INFORMATION_MESSAGE);
                Warenkorb.this.dispose();
                return;
            }

            Waren = new ArrayList<JPanel>();
            new Warenkorb(WarenListe, Warenkorb.this.getLocation(), GesamtSumme);
            Warenkorb.this.dispose();
        }
    }
}
