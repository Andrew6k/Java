package com.company;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        int n;
        System.out.println("Number of tokens: ");
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(stdin.readLine());

        int m;
        System.out.println ("The max value of a token (m) : ");
        BufferedReader stdin1 = new BufferedReader(new InputStreamReader(System.in));
        m = Integer.parseInt(stdin1.readLine());

        Board board=new Board(n,m);
        board.filltheBoard();

        Game game=new Game();
        Thread t1 = new Thread(new Player("Player1",1,game,board));
        Thread t2 = new Thread(new Player("Player2",2,game,board));
        new Thread(t1).start();
        new Thread(t2).start();
        game.score();


    }
}
