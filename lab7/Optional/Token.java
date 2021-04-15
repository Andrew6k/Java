package com.company;

import java.util.Random;

public class Token {
    int token;
    int m;
    Random rand=new Random();
    public Token(int m)
    { this.m=m;
    }
    public int getToken()
    {

        token=1+rand.nextInt(m);
        return token;
    }
}
