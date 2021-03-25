package com.company;

import javax.swing.*;
import java.awt.*;

public class ConfigPanel extends JPanel {
    final MainFrame frame;
    JLabel label;
    JSpinner sidesField;
    JComboBox colorCombo;
    JPanel p = new JPanel(new BorderLayout());
    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {

        JLabel sidesLabel = new JLabel("Number of sides:");
        sidesField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        sidesField.setValue(6);
        add(sidesLabel);
        add(sidesField);
        add(colorCombo);
        sidesLabel.setPreferredSize(p.getSize());
        p.add(sidesLabel);
    }
}
