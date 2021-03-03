package com.company;

public class Warehouse extends Source{
    String Type;
    Warehouse(){
        /**
         *Class constructor*/
        Type="Warehouse";}
    Warehouse(String n,int x){
        /**
        * Class constructor with parameters
         */
        Type="Warehouse";
        name=n;
        capacity=x;
    }
    String getType (){
        /**
         *
        This method returns the type.*/
        return Type;}
}
