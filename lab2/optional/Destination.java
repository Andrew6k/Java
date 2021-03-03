package com.company;

import java.util.Objects;

public class Destination {
    private
    String name;
    public int demand;
    Destination(){}

    /**
     *
     * @param n
     * @param x
     */
    Destination(String n,int x){
        /**
        * Class constructor with parameters.
         */
        name=n;
        demand=x;
    }
    public
    String getName(){
        /**
         *
         This method returns the name.*/
        return name;}
    void setName(String s){
        //This method sets a new name.
        name=s;}
    int getDemand(){
        //This method returns  the demand.
        return demand;}
    void setDemand(int x){
        //This methods set a new demand.
        demand=x;}

    @Override
    public String toString() {
        return "Destination " + name  +
                " demands " + demand;
    }

    @Override
    public boolean equals(Object o) {
        /**
        * This method verifies if 2 objects of class destination are equal.
         */
        Destination that = (Destination) o;
        if(this.getDemand()!=that.demand)
            return false;
        if(!(this.getName().equals(that.getName())))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, demand);
    }
}
