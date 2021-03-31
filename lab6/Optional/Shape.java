package com.company;

import java.awt.*;

public class Shape {
    protected int x;
    protected int y;
    protected int radius;
    protected Color color;
    public int type;
    public int sides;

    public void setSides(int sides) {
        this.sides = sides;
    }

    public int getSides() {
        return sides;
    }

    public Color getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }



    public Shape(int x, int y, int radius, Color color,int type) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.type=type;
    }
}
