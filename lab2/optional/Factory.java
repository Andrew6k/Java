package com.company;

public class Factory extends Source{
    String Type;
    Factory(){
        /**
         * Class costructor.
         */
        Type="Factory";}
    Factory(String n,int x){
        /**
         * Class constructor with parameters
         */
        Type="Factory";
        name=n;
        capacity=x;
    }
    String getType(){
        /**
         *
        This method returns the type.*/
        return Type;}
}
