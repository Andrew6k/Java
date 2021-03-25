package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static javax.swing.SwingConstants.NORTH;
import static javax.swing.SwingConstants.SOUTH;

public class MainFrame extends JFrame {
    private static final int CENTER =0 ;
    ConfigPanel configPanel;

    @Override
    public void setLayout(LayoutManager manager) {
        super.setLayout(manager);
    }

    ControlPanel controlPanel;
    DrawingPanel canvas;

    public MainFrame() {
        super("My Drawing Application");
        init();
    }

    public void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        canvas = new DrawingPanel(this);
        controlPanel=new ControlPanel(this);
        configPanel=new ConfigPanel(this);
        add(canvas, CENTER);
        add(controlPanel,SOUTH);
        add(configPanel,NORTH);
        pack();
    }
}
