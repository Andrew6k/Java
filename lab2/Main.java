package com.company;

import static com.company.Source.SourceType.FACTORY;
import static com.company.Source.SourceType.WAREHOUSE;

public class Main {

    public static void main(String[] args) {
    Source s1= new Source();
    s1.setName("S1");
    System.out.println(s1.getName());
    s1.setType(FACTORY);
    System.out.println(s1.getType());
    s1.setCapacity(10);
    Source s2= new Source("S2",WAREHOUSE,35);
    System.out.println(s2.getName());
    System.out.println(s2.getType());
    Source s3= new Source("S3",WAREHOUSE,25);
    Destination d1=new Destination("D1",20);
    Destination d2=new Destination("D2",25);
    Destination d3=new Destination("D3",25);
    System.out.println(s3.toString());
    System.out.println(d1.toString());
    Problem p= new Problem(s1,d1,2);
    System.out.println(p.toString());
    Source[] a=new Source[3];
    Destination[] b=new Destination[3];
    a[0]=s1;
    a[1]=s2;
    a[2]=s3;
    b[0]=d1;
    b[1]=d2;
    b[2]=d3;
    System.out.print(a[0].getName()+ " ");
    int[][] cost={
            {2,3,1},
            {5,4,8},
            {5,6,8}
    };
    System.out.print(cost[0][0]+ " ");
    System.out.print(b[0].getName());
    }
}
