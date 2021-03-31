package com.company;

import javax.swing.*;

public class ShapesPanel extends JPanel {
    final MainFrame frame;
    JComboBox shapesCombo;
    public ShapesPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init(){
        String[] values = {"RegularPolygon", "Circle", "Arc","Pencil"};
        shapesCombo = new JComboBox(values);
        add(shapesCombo);
    }
}
