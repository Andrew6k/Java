package com.company;


public class Problem {
    public
    Source s;
    Destination d;
    int cost;
    Problem(Source a, Destination b, int c){
        s=a;
        d=b;
        cost=c;
    }
    public
    void setS(Source x){
        s=x;
    }
    Source getS(){
        return s;
    }
    void setD(Destination x){
        d=x;
    }
    Destination getD(){
        return d;
    }

    @Override
    public String toString() {
        return "Problem " +
                "source " + s.getName() +" of type "+s.getType()+" has capacity "+s.getCapacity()+
                " destination " + d.getName() +" demands "+d.getDemand()+
                " cost=" + cost ;
    }
}