package de.sirmrmanuel0.gui.custom_components;

import de.sirmrmanuel0.gui.Start;
import de.sirmrmanuel0.logic.Warenkorb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UpdaterPanel extends JPanel {
    protected ArrayList<JButton> Buttons = new ArrayList<>();

    public UpdaterPanel(FlowLayout flowLayout) {
        super(flowLayout);
    }

    public void addToUpdate(JButton Button){
        Buttons.add(Button);
    }
    public void updateAll(Warenkorb Korb, JButton Warenkorb, CustomFrame parent){
        if (Buttons.isEmpty())
            return;
        for (JButton Button : Buttons){
            Start.PizzenActionListener Listener = (Start.PizzenActionListener) Button.getActionListeners()[0];
            Listener.updateListener(Korb, Warenkorb, parent);
        }


    }
}
