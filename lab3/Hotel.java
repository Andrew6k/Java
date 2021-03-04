package com.company;

public class Hotel extends Location implements Payable,Classifiable{

    private int rank;
    private double ticketPrice;

    @Override
    public double getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public int getRank() {
        return rank;
    }
    public void setTicketPrice(double x){
        ticketPrice=x;
    }
    public void setRank(int x){
        rank=x;
    }
}
