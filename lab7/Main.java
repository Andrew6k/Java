package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {

        public static void main(String args[]) {
            Buffer buffer=new Buffer();
            Board b=new Board(buffer);
            int n=4;
            Random randomNum= new Random();
            var tokens = IntStream.rangeClosed(0,15)
                    .mapToObj(i->new Token("t"+i))
                    .toArray(Token[]::new);
            List<Token> tokenList=new ArrayList<>();
            tokenList.addAll(Arrays.asList(tokens));
            b.setBoard(tokenList);
            for( Token t : b.getBoard()){
                t.setX(1+ randomNum.nextInt(n));
                t.setY(1+ randomNum.nextInt(n));
                t.setValue(randomNum.nextInt(10));
            }
            Player p1=new Player("P1",buffer);
            Player p2=new Player("P2",buffer);
            //Runnable runnable = new HelloRunnable(p1,b);
           // Runnable runnable1= new HelloRunnable(p2,b);
            new Thread(b).start();
            new Thread(p1).start();
            new Thread(p2).start();
        }

}


