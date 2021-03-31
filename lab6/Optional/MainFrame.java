package com.company;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public int ok=0;
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;
    ShapesPanel shapesPanel;
    public MainFrame() {
        super("My Drawing Application");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        canvas = new DrawingPanel(this);
        shapesPanel = new ShapesPanel(this);
        controlPanel = new ControlPanel(this);
        configPanel = new ConfigPanel(this);

        add(canvas, BorderLayout.CENTER); //this is BorderLayout.CENTER
        add(controlPanel, BorderLayout.SOUTH);
        add(configPanel, BorderLayout.NORTH);
        add(shapesPanel,BorderLayout.EAST);

        //invoke the layout manager
        pack();
    }
}
