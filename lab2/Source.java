package com.company;

public class Source {
    private
    String name;
    public enum SourceType{
        WAREHOUSE, FACTORY;
    }
    public SourceType Type;
    public int capacity;
    Source(){}
    Source(String n, SourceType x, int a){
        name=n;
        Type=x;
        capacity=a;
    }
    public
    String getName(){ return name;}
    void setName(String s){name=s;}
    SourceType getType(){return Type;}
    void setType(SourceType a){Type=a;}
    int getCapacity(){ return capacity;}
    void setCapacity(int a){ capacity=a;}

    @Override
    public String toString() {
        return "Source " + name +
                " of type " + Type +
                " has capacity=" + capacity;
    }
}
