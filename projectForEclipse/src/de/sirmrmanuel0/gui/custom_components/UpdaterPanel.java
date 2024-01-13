package de.sirmrmanuel0.gui.custom_components;

import de.sirmrmanuel0.gui.Start;
import de.sirmrmanuel0.logic.Warenkorb;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UpdaterPanel extends JPanel {
    protected ArrayList<JButton> Buttons = new ArrayList<>();

    public UpdaterPanel(FlowLayout flowLayout) {
        super(flowLayout);
    }

    // Methode zum Hinzufügen eines Buttons zur Aktualisierung.
    public void addToUpdate(JButton Button){
        Buttons.add(Button);
    }

    // Methode zum Aktualisieren aller hinzugefügten Buttons.
    public void updateAll(Warenkorb Korb, JButton Warenkorb, CustomFrame parent){
        // Wenn die Liste der Buttons leer ist, wird die Methode beendet.
        if (Buttons.isEmpty())
            return;

        // Iteration durch alle Buttons und Aktualisierung der Listener.
        for (JButton Button : Buttons){
            Start.PizzenActionListener Listener = (Start.PizzenActionListener) Button.getActionListeners()[0];
            Listener.updateListener(Korb, Warenkorb, parent);
        }


    }
}
