package com.company;

public class Solution {
    public Problem p;
    Solution(Problem x){
        //Class constructor with parameter of type problem.
        p=x;
    }
    public void Rezolvare(){
        /**
        * This method constructs a random solution for a given instance of type problem.
        * We will use the attributes from the class problem.
        * We will make different tests so the result is correct.
         */
        int i,c;
        int y[][]= new int[p.sourceNumber][p.destNumber];
        for(i=0;i<p.sourceNumber;i++) {
            for (c = 0; c < p.destNumber; c++)
                y[i][c]=0;}
        for(i=0;i<p.destNumber;i++) {
            while (p.destinations[i].demand > 0) {
                int j = (int) (Math.random() * p.sourceNumber);
                if(p.sources[j].capacity>0) {
                    int x = (int) (Math.random() * p.sources[j].capacity)+1;
                    if(x<=p.destinations[i].demand){
                    p.destinations[i].demand=p.destinations[i].demand-x;
                    p.sources[j].capacity=p.sources[j].capacity-x;
                    y[j][i]=y[j][i]+x;}
                }
            }
        }
        for(i=0;i<p.sourceNumber;i++) {
            System.out.print(p.sources[i].getName()+"= ");
            for (c = 0; c < p.destNumber; c++)
                System.out.print(y[i][c] + " ");
            System.out.println();
        }
    }
}
