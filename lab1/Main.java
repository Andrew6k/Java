package com.company;

public class Main {
    public static int sumDigits(int n){
        int sum=0;
        while(n>0 || sum>9)
        {
            if(n==0){
                n=sum;
                sum=0;
            }
            sum=sum+n%10;
            n=n/10;
        }
        return sum;
    }
    public static void main(String[] args) {
	    System.out.println("Hello world!");
	    String[] languages ={"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};
        int n = (int) (Math.random() * 1_000_000);
        System.out.println(n);
        n=n*3;
        String number = "10101";
        n=n+Integer.parseInt(number,2);
        String number2= "FF";
        n=n+Integer.parseInt(number2,16);
        n=n*6;
        int s=sumDigits(n);
        System.out.println(s);
        System.out.println(n);
        System.out.println("Willy-nilly, this semester I will learn " + languages[s]);

    }
}
