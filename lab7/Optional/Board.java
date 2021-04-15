package com.company;


import java.util.Random;

public class Board {
    int n,number,i,j,m;
    Random rand=new Random();

    public Board(int n,int m) {
        this.n=n;
        this.m=m;
    }
    int []tokens=new int[101];

    public void filltheBoard()
    {
        Token token=new Token(m);
        for(i=1;i<=m+1;i++)
            tokens[i]=0;
        j=1;
        while(j<=n)
        { number=token.getToken();
            while(tokens[number]>0)
                number=token.getToken();
            tokens[number]=number;
            j++;
        }
        System.out.print("Board: ");
        i=1;
        while(i<=m+1)
        { if(tokens[i]>0)
            System.out.print(tokens[i] + " ");
            i++;}
        System.out.println();
    }

    public int extractToken()
    {
        int index=rand.nextInt(m);
        if(tokens[index]<1)
            while(tokens[index]<1)
                index=rand.nextInt(m);
        tokens[index]=0;
        return index;
    }


}
