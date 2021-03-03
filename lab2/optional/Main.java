package com.company;

public class Main {

    public static void main(String[] args) {

    Source s1= new Factory("S1",10);
    Source s2= new Warehouse("S2",35);
    Source s3= new Warehouse("S3",25);
    Destination d1=new Destination("D1",20);
    Destination d2=new Destination("D2",25);
    Destination d3=new Destination("D3",25);
    System.out.println(s3.toString());
    System.out.println(d1.toString());
    Source[] a=new Source[3];
    Destination[] b=new Destination[3];
    a[0]=s1;
    a[1]=s2;
    a[2]=s3;
    b[0]=d1;
    b[1]=d2;
    b[2]=d3;
    int[][] cost={
            {2,3,1},
            {5,4,8},
            {5,6,8}
    };
        Problem P = new Problem();
        P.setSources(a);
        P.setDestinations(b);
        P.setDistances(cost);

        System.out.println(P);

        Destination d5=new Destination("D1",20);
        int i;
        for(i=0;i<3;i++)
            if(b[i].equals(d5))
                System.out.println("NU");
        Solution S= new Solution(P);
        S.Rezolvare();
    }
}

