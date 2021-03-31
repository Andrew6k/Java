package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.*;

public class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton undoBtn = new JButton("Undo");
    JButton saveBtn = new JButton("Save");
    JButton loadBtn = new JButton("Load");
    JButton resetBtn = new JButton("Reset");
    JButton exitBtn = new JButton("Exit");

    JFileChooser fileChooser = new JFileChooser();

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        setLayout(new FlowLayout());
        add(undoBtn);
        add(saveBtn);
        add(loadBtn);
        add(resetBtn);
        add(exitBtn);

        undoBtn.addActionListener(this::undo);
        saveBtn.addActionListener(this::save);
        loadBtn.addActionListener(this::load);
        resetBtn.addActionListener(this::reset);
        exitBtn.addActionListener(this::exit);
    }

    private void save(ActionEvent e) {

        int approve = fileChooser.showSaveDialog(null);
        if (approve == JFileChooser.APPROVE_OPTION) {
            try {
                ImageIO.write(frame.canvas.image, "PNG", new File(fileChooser.getSelectedFile().getAbsolutePath()));
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    private void load(ActionEvent e) {

        int approve = fileChooser.showOpenDialog(null);
        if (approve == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedImage image = ImageIO.read(new File(fileChooser.getSelectedFile().getAbsolutePath()));
                frame.canvas.image = image;
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    private void reset(ActionEvent e) {
        frame.canvas.image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        frame.canvas.graphics = frame.canvas.image.createGraphics();
        frame.canvas.graphics.setColor(Color.WHITE);
        frame.canvas.graphics.fillRect(0, 0, 800, 600);
        frame.canvas.shapeList.clear();
    }


    private void undo(ActionEvent e){
        frame.canvas.deleteLastShape();
        frame.ok=1;
        frame.canvas.drawShapeList();
    }
    private void exit(ActionEvent e) {
        System.exit(0);
    }
}

