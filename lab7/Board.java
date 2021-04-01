package com.company;

import java.util.List;
import java.util.Random;

public class Board extends Thread{
    public List<Token> board;
    private boolean available=false;
    public void setBoard(List<Token> board) {
        this.board = board;
    }

    public List<Token> getBoard() {
        return board;
    }
    public void add(Token t){
        board.add(t);
    }
    public synchronized Token get(int index){
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        available = false; notifyAll();
        if(board.get(index)!=null){
            Token t=board.get(index);
            board.remove(index);
            return t;
        }
        else return null;
    }
    private final Buffer buffer;

    public Board(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
            Random randomNum= new Random();
            Token t=this.get(0+randomNum.nextInt(this.board.size()-1));
            buffer.setToken(t);
            System.out.println("Token produced:" + t);
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                System.err.println(e);
            }

    }
}
