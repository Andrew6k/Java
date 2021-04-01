package com.company;

public class Token {
    public String name;
    public int x;
    public int y;
    public int value;

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }
    Token(int x, int y,int value){
        this.x=x;
        this.y=y;
        this.value=value;
    }
    Token(String s){
        this.name=s;
    }
}
