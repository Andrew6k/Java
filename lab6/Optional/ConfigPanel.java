package com.company;


import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ConfigPanel extends JPanel {
    final MainFrame frame;
    JLabel label; // we’re drawing regular polygons
    JSpinner sidesField; // number of sides
    JComboBox colorCombo; // the color of the shape
    JLabel sidesLabel;
    JComboBox modCombo;
    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
        next();
    }

    private void init() {
            label = new JLabel("We're drawing regular polygons.");
            sidesLabel = new JLabel("Number of sides:");
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 360, 1);
            sidesField = new JSpinner(model);
            sidesField.setValue(6); //default number of sides
            String[] values = {"Random", "Black"};
            colorCombo = new JComboBox(values);
           // String[] values1={"Direct","Retained"};
           // modCombo = new JComboBox(values1);
            add(sidesLabel); //JPanel uses FlowLayout by default
            add(sidesField);
            add(colorCombo);
            //add(modCombo);
        }
        private void next(){
            if(frame.shapesPanel.shapesCombo.getSelectedItem()!=null) {
                frame.shapesPanel.shapesCombo.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if(e.getStateChange() == ItemEvent.SELECTED) {
                            String a=frame.shapesPanel.shapesCombo.getSelectedItem().toString();
                            if(a=="Circle"){
                                nextCircle();
                                }
                            if(a=="Arc"){
                                nextArc();
                            }
                            if(a=="Pencil") {
                                nextPencil();
                            }
                            if(a=="RegularPolygon"){
                                sidesLabel.setText("Number of sides:");
                                sidesField.setValue(6);
                            }
                            System.out.print(a);
                        }
                    }
                });
            }
        }
        private void nextCircle(){
            sidesLabel.setText("Radius:");
            sidesField.setValue(30);
            //this.sidesLabel.repaint();
            //System.out.println(2);
            //JLabel sidesLabel1 = new JLabel("Radius:");
            //SpinnerNumberModel model1 = new SpinnerNumberModel(0, 0, 50, 1);
            //add(sidesLabel1);
        }

        private void nextArc(){
            sidesLabel.setText("Angle:");
            sidesField.setValue(90);
        }
        private void nextPencil(){

        }
    }

