package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class HelloRunnable implements Runnable {
    public Player p;
    public Board b;
    public HelloRunnable(Player p, Board b) {
        this.p=p;
        this.b=b;
    }

    @Override
    public void run() {
            if(b.getBoard().get(0)!=null){
                Random randomNum= new Random();
                p.add(b.get(0+randomNum.nextInt(b.getBoard().size()-1)));
                while(p.get(p.board.size()-1)==null)
                    p.set(b.get(0+randomNum.nextInt(b.getBoard().size()-1)));
            }
    }
}
