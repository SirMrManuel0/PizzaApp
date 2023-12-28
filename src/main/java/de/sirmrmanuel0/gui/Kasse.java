package de.sirmrmanuel0.gui;

import de.sirmrmanuel0.gui.custom_components.CustomFrame;
import de.sirmrmanuel0.gui.custom_components.RoundedButton;
import de.sirmrmanuel0.gui.custom_components.RoundedCornerPanel;
import de.sirmrmanuel0.gui.snake.GameOverObserver;
import de.sirmrmanuel0.gui.snake.Snake;
import de.sirmrmanuel0.gui.snake.SnakePanel;
import de.sirmrmanuel0.logic.Warenkorb;
import de.sirmrmanuel0.pizza.Pizza;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.math.BigDecimal;
import java.util.ArrayList;

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

    public Kasse(Warenkorb Korb, Point Location){
        super(1, 1.5, 1, 1.2, "Pizza Lieferung Deluxe - Warenkorb", false);
        this.Korb = Korb;
        GesamtSumme = Korb.getGesamtPreis();
        Waren = new ArrayList<JPanel>();
        setBackgroundImage(loadImage("background.jpg"));
        initComponents();
        if (!Korb.getRabatte().isEmpty())
            rabatt();
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
                new Start(Korb, getLocation());
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
        JButton RabattSpielButton = new JButton("<html><center>Rabatt-Spiel Snake<br>Sparen Sie 1 Euro pro 100 Punkte</center></html>");
        JPanel GesamtPreisPanel = new JPanel(new GridBagLayout());
        JButton BezahlenButton = new JButton("Bezahlen");
        JPanel GesamtPreisWrapper = new JPanel();

        Bezahlen.setLayout(new GridLayout(2,1));
        Bezahlen.setBackground(new Color(0,0,0,0));
        GesamtPreis = new JLabel("Summe: " + String.valueOf(GesamtSumme).replace(".",",") + "€");
        GesamtPreis.setForeground(Color.WHITE);
        GesamtPreis.setFont(new Font("SansSerif", Font.BOLD, 25));

        Filler1.setBackground(new Color(0,0,0,0));
        BezahlenButtonPanel.setBackground(new Color(0,0,0,0));
        GesamtPreisPanel.setBackground(new Color(0,0,0,90));
        GesamtPreisWrapper.setBackground(new Color(0,0,0,0));

        BezahlenButton.setBackground(Color.BLACK);
        BezahlenButton.setForeground(Color.WHITE);
        BezahlenButton.setFocusPainted(false);

        RabattSpielButton.setBackground(Color.BLACK);
        RabattSpielButton.setForeground(Color.WHITE);
        RabattSpielButton.setFocusPainted(false);
        RabattSpielButton.addActionListener(new RabattSpielActionListener());


        BezahlenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] message = {
                        "Danke für ihre Bestellung von " + String.valueOf(GesamtSumme).replace(".",",") + "€!",
                        "Bis zum nächsten Mal!"
                };

                JOptionPane.showMessageDialog(Kasse.this, message, "Auf Wiedersehen", JOptionPane.INFORMATION_MESSAGE);
                Kasse.this.dispose();
            }
        });

        initQuittung();

        QuittungWrapper.setBackground(new Color(0,0,0,0));
        Quittung.setBackground(new Color(0,0,0,0));

        Quittung.setLayout(new GridLayout(Waren.size(),1));


        QuittungScroll = new JScrollPane(Quittung);
        QuittungScroll.setBackground(new Color(0,0,0,0));

        QuittungScroll.getVerticalScrollBar().addAdjustmentListener(new ScrollAdjustmentListener());

        QuittungScroll.getHorizontalScrollBar().addAdjustmentListener(new ScrollAdjustmentListener());

        JScrollBar verticalScrollBar = QuittungScroll.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);
        verticalScrollBar.setBlockIncrement(60);

        JScrollBar horizontalScrollBar = QuittungScroll.getHorizontalScrollBar();
        horizontalScrollBar.setUnitIncrement(20);
        horizontalScrollBar.setBlockIncrement(60);

        QuittungWrapper.setLayout(new GridLayout(1,1));
        QuittungWrapper.add(QuittungScroll);

        BezahlenButtonPanel.add(Filler1);
        BezahlenButtonPanel.add(RabattSpielButton);
        BezahlenButtonPanel.add(BezahlenButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);


        GesamtPreisWrapper.add(GesamtPreis);
        GesamtPreisPanel.add(GesamtPreisWrapper, gbc);

        JScrollPane GesamtPreisScroll = new JScrollPane(GesamtPreisPanel);
        GesamtPreisScroll.setBackground(new Color(0,0,0,0));
        JScrollBar VerticalBar = GesamtPreisScroll.getVerticalScrollBar();
        VerticalBar.addAdjustmentListener(new ScrollAdjustmentListener());

        JScrollBar HorizontalBar = GesamtPreisScroll.getHorizontalScrollBar();
        HorizontalBar.addAdjustmentListener(new ScrollAdjustmentListener());


        Bezahlen.add(GesamtPreisScroll);
        Bezahlen.add(BezahlenButtonPanel);

        Main.add(QuittungWrapper);
        Main.add(Bezahlen);
    }

    protected void initQuittung(){
        boolean EvenChild = false;
        for (Pizza[] Pizza : Korb.getAllToBuy()){
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
            ImageIcon LogoIcon = new ImageIcon(loadImage("logo_prop1.png"));
            JLabel Icon = new JLabel();
            JPanel IconPanel = new JPanel();
            JPanel Schrift = new JPanel();
            JPanel Buttons = new JPanel();
            JPanel DropDowns = new JPanel(new GridLayout(2,2));

            int lengthSmall = 1;
            int lengthMid = 1;
            int lengthNormal = 1;
            int lengthBig = 1;

            boolean usableSmall = false;
            boolean usableMid = false;
            boolean usableNormal = false;
            boolean usableBig = false;

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

            dropDownSmall.addPopupMenuListener(new WarenPopUpListener());
            dropDownMid.addPopupMenuListener(new WarenPopUpListener());
            dropDownNormal.addPopupMenuListener(new WarenPopUpListener());
            dropDownBig.addPopupMenuListener(new WarenPopUpListener());


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

            DeleteSmall.addActionListener(new WarenActionListener(Pizza, 0, PizzaWrapper, dropDownSmall));
            DeleteMid.addActionListener(new WarenActionListener(Pizza,1, PizzaWrapper, dropDownMid));
            DeleteNormal.addActionListener(new WarenActionListener(Pizza,2, PizzaWrapper, dropDownNormal));
            DeleteBig.addActionListener(new WarenActionListener(Pizza,3, PizzaWrapper, dropDownBig));

            if (Pizza[0].toString().equals("0")){
                DeleteSmall.setEnabled(false);
            }
            if (Pizza[1].toString().equals("0")){
                DeleteMid.setEnabled(false);
            }
            if (Pizza[2].toString().equals("0")){
                DeleteNormal.setEnabled(false);
            }
            if (Pizza[3].toString().equals("0")){
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

            PizzaPanel.setPreferredSize(getScaledDimension(0.9,10));
            IconPanel.setPreferredSize(getScaledDimension(15,14));
            Schrift.setPreferredSize(getScaledDimension(2.4,11));
            Buttons.setPreferredSize(getScaledDimension(2.8,11));

            Buttons.setLayout(new GridLayout(2,2));
            Schrift.setLayout(new GridLayout(1,2));

            PizzaWrapper.setBackground(new Color(0,0,0,0));

            DropDowns.add(dropDownSmall);
            DropDowns.add(dropDownMid);
            DropDowns.add(dropDownNormal);
            DropDowns.add(dropDownBig);

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
            PizzaPanel.add(DropDowns);
            PizzaPanel.add(Buttons);

            PizzaWrapper.add(PizzaPanel);
            Quittung.add(PizzaWrapper);
            Waren.add(PizzaWrapper);
        }
    }

    protected void rabatt(){
        GesamtPreis.setText("<html><center>"
                + String.valueOf(Korb.getGesamtPreisOhneRabatt()).replace(".",",") + "€<br>");
        for (Object[] val : Korb.getRabatte()){
            BigDecimal rabattBig = (BigDecimal) val[1];
            double tempRabatt = rabattBig.doubleValue();
            tempRabatt = (double) Math.round(tempRabatt * 100) / 100;
            String rabattStr = String.valueOf((int) (tempRabatt * 100));

            switch (rabattStr.length()){
                case 1:
                    rabattStr = "00" + rabattStr;
                    break;
                case 2:
                    rabattStr = "0" + rabattStr;
            }


            tempRabatt = Double.parseDouble(rabattStr.substring(0, rabattStr.length()-2) + "." + rabattStr.substring(rabattStr.length()-2));

            GesamtPreis.setText(GesamtPreis.getText() + "<font color='red' size='5' bgcolor='black'>- "
                    + String.valueOf(tempRabatt).replace(".",",") + "€"
                    + " " + (String) val[0] + "</font><br>");
        }
        GesamtSumme = Korb.getGesamtPreis();
        GesamtPreis.setText(GesamtPreis.getText() + "Summe: " + String.valueOf(GesamtSumme).replace(".",",") + "€" + "</center></html>");
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


    protected class RabattSpielActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            RabattPanel = new SnakePanel();
            RabattPanel.addObserver(Kasse.this);
            snake = new Snake(RabattPanel);
        }
    }

    protected class WarenActionListener implements ActionListener{
        protected Pizza[] Pizza;
        protected int Index;
        protected JPanel PizzaWrapper;
        protected int PizzaIndex;
        protected JPanel Panel;
        protected JComboBox<String> dropDown;

        WarenActionListener(Pizza[] Pizza, int Index, JPanel PizzaWrapper, JComboBox<String> dropDown){
            this.Index = Index;
            this.Pizza = Pizza;
            this.PizzaWrapper = PizzaWrapper;
            this.dropDown = dropDown;
            PizzaIndex = Korb.getAllToBuy().indexOf(Pizza);
            Panel = PizzaWrapper;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton Source = (JButton) e.getSource();
            int decrement = 0;

            try{
                decrement = Integer.parseInt((String) dropDown.getSelectedItem());
            } catch (NumberFormatException ex){
                Object[] message = new Object[]{
                        "Bitte gebe eine echte Zahle ein!",
                        "Gebe nicht '" + dropDown.getSelectedItem() + "' ein!"
                };

                JOptionPane.showMessageDialog(Kasse.this, message, "Ungültige Angabe", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pizza[Index].setPreis(-decrement);

            if (Integer.parseInt(Pizza[0].toString()) <= 0
                    && Integer.parseInt(Pizza[1].toString()) <= 0
                    && Integer.parseInt(Pizza[2].toString()) <= 0
                    && Integer.parseInt(Pizza[3].toString()) <= 0){
                Waren.remove(Panel);
                Korb.remove(Pizza);
            }

            if (Korb.getAllToBuy().isEmpty()){
                Object[] message = {
                        "Schade, dass Sie nichts für sich gefunden haben!",
                        "Bis zum nächsten mal!"
                };
                JOptionPane.showMessageDialog(Kasse.this, message, "Auf Wiedersehen", JOptionPane.INFORMATION_MESSAGE);
                Kasse.this.dispose();
                return;
            }

            Waren = new ArrayList<JPanel>();
            new Kasse(Korb, Kasse.this.getLocation());
            Kasse.this.dispose();
        }
    }

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

    protected class ScrollAdjustmentListener implements AdjustmentListener{

        @Override
        public void adjustmentValueChanged(AdjustmentEvent e) {
            Kasse.this.repaint();
        }
    }
}
