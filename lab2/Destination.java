package com.company;

public class Destination {
    private
    String name;
    public int demand;
    Destination(){}
    Destination(String n,int x){
        name=n;
        demand=x;
    }
    public
    String getName(){ return name;}
    void setName(String s){name=s;}
    int getDemand(){ return demand;}
    void setDemand(int x){demand=x;}

    @Override
    public String toString() {
        return "Destination " + name  +
                " demands " + demand;
    }

}
