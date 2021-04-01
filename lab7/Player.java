package com.company;

import java.util.List;

public class Player extends Thread {
    public String nume;
    public List<Token> board;
    private final Buffer buffer;
    boolean available;
    public Player(Buffer buffer) {
        this.buffer = buffer;
    }
    Player(String s,Buffer buffer1){
        this.nume=s;
        this.buffer=buffer1;
    }
    public Token get(int index){
        return board.get(index);
    }
    public synchronized void add(Token t){
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        board.add(t);
        available=true;
        notifyAll();
    }

    public List<Token> getBoard() {
        return board;
    }
    public void set(Token t){
        board.set(board.size()-1,t);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Token value = buffer.getToken();
            System.out.println(
                    "Choosed " + value);
        }
    }
}
