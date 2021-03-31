package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
public class DrawingPanel extends JPanel {
    final MainFrame frame;
    final static int W = 800, H = 600;
    BufferedImage image; //the offscreen image
    Graphics2D graphics; //the "tools" needed to draw in the image
    public List<Shape> shapeList= new ArrayList<>();
    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        createOffscreenImage();
        init();
    }

    private void createOffscreenImage() {
        image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        graphics.setColor(Color.WHITE); //fill the image with white
        graphics.fillRect(0, 0, W, H);
    }

    private void init() {
        setPreferredSize(new Dimension(W, H));
        setBorder(BorderFactory.createEtchedBorder()); //for fun
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                drawShape(e.getX(), e.getY());
                repaint();
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                drawShape(e.getX(),e.getY());repaint();
            }
            @Override
            public void mouseDragged(MouseEvent e){
                drawShape(e.getX(),e.getY()); repaint();
            }
            @Override
            public void mouseMoved(MouseEvent e){}
        });
    }

    private void drawShape(int x, int y) {
        String a=frame.shapesPanel.shapesCombo.getSelectedItem().toString();
        if(a=="RegularPolygon"){
        int radius = 50 + (int) (100 * Math.random());
        int sides = (int) frame.configPanel.sidesField.getValue();
        Color color = new Color((int) (100 * Math.random()), 0, 0, 50);
     //   Color color = new Color((Integer) frame.configPanel.colorCombo.getSelectedItem());
            if("Black".equals((String) frame.configPanel.colorCombo.getSelectedItem())) {
                graphics.setColor(Color.BLACK);
            } else {
                graphics.setColor(color);
            }
        graphics.fill(new RegularPolygon(x, y, radius, sides));
        shapeList.add(new Shape(x,y,radius,color,1));
        shapeList.get(shapeList.size()-1).setSides(sides);
        }
        if(a=="Circle"){
            int radius=(int)frame.configPanel.sidesField.getValue();
            Color color = new Color((int) (100 * Math.random()), 0, 0, 50);
            if("Black".equals((String) frame.configPanel.colorCombo.getSelectedItem())) {
                graphics.setColor(Color.BLACK);
            } else {
                graphics.setColor(color);
            }
            graphics.fill(new Ellipse2D.Float(x-20,y-40,radius+80,radius+80));
            shapeList.add(new Shape(x,y,radius,color,2));
        }
        if(a=="Arc"){
            int angle=(int)frame.configPanel.sidesField.getValue();
            Color color = new Color((int) (100 * Math.random()), 0, 0, 50);
            if("Black".equals((String) frame.configPanel.colorCombo.getSelectedItem())) {
                graphics.setColor(Color.BLACK);
            } else {
                graphics.setColor(color);
            }
            graphics.fillArc(x-60,y-20,120,120,5,angle);
            //shapeList.add(new Shape(x,y,radius,color));
        }
        if(a=="Pencil"){
            int radius=10;
            Color color = new Color((int) (100 * Math.random()), 0, 0, 50);
            if("Black".equals((String) frame.configPanel.colorCombo.getSelectedItem())) {
                graphics.setColor(Color.BLACK);
            } else {
                graphics.setColor(color);
            }
            graphics.fill(new Ellipse2D.Float(x,y,radius,radius));
        }
    }

    @Override
    public void update(Graphics g) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(frame.ok==0){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);}
        else
        {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0,0,800,600);
            for(Shape shape:shapeList){
                graphics.setColor(shape.getColor());
                ((Graphics2D) g).fill((java.awt.Shape) shape);
            }
            frame.ok=1;
        }
    }

    public void deleteLastShape() {
        int index=shapeList.size();
        shapeList.remove(index-1);
    }

    public void drawShapeList() {

    }
}





