package com.company;

import java.util.Objects;

/**
 * The class Source
 */
public class Source {

    protected
    String name;
    public int capacity;


    Source(){}

    /**
     *
     * @param n
     * @param a
     */
    Source(String n, int a){

        name=n;
        capacity=a;
    }
    public
    String getName(){
            // This method returns the name
            return name;}

    /**
     *
     * @param s
     */
    void setName(String s){
        name=s;}
    int getCapacity(){
            // This method returns the capacity
            return capacity;}

    /**
     *
     * @param a
     */
    void setCapacity(int a){
        /**
         *This method sets a new capacity
          */
        capacity=a;}

    @Override
    public String toString() {
        return "Source " + name +
                " has capacity=" + capacity;
    }

    @Override
    public boolean equals(Object o) {
        /**
         * This method verifies if 2 objects of class source are equals.
         */

        Source source = (Source) o;
        if(!(source.getName().equals(this.getName())))
            return false;
        if(this.getCapacity()!=source.getCapacity())
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capacity);
    }
}

