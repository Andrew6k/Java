package com.company;

public class Main {
    public static int a;
    public static void isConnected(int [][] graph, int start, int n, int []visited)
    {
        visited[start]=a;
        int i;
        for(i=0;i<n;i++)
        {
            if(graph[start][i]==1 && visited[i]==0)
                isConnected(graph,i,n,visited);
        }

    }
    public static void generateTree(int [][]graph, int start, int n, int[]visited, int [][]tree)
    {
        visited[start]=1;
        int i;
        for(i=0;i<n;i++)
        {
            if(graph[start][i]==1 && visited[i]==0)
            {
                tree[start][i]=1;
                generateTree(graph,i,n,visited,tree);
            }
        }
    }
    public static void main(String[] args) {
	    System.out.println(args[0]);
	    long start= System.nanoTime();
        if(args.length < 1 ){
            System.err.println("No argument entered");}
        else {
            int n = Integer.parseInt(args[0]);
            if (n < 1)
                System.err.println("invalid argument");
            System.out.println(n);
            int [][]matrix= new int[n][n];
            int i,j;
            int x,y;
            int m= (int) ((Math.random()*(n*(n-1)/2-1))+1); //edges
            System.out.println(m);
            while(m>0){
                x= (int) ((Math.random()*n));
                y= (int) ((Math.random()*n));
                if(x!=y && matrix[x][y]==0){
                    matrix[x][y]=1;
                    matrix[y][x]=1;
                    m--;
                }
                }
            for(i=0;i<n;i++) {
                for (j = 0; j < n; j++)
                    System.out.print(matrix[i][j] + " ");
                System.out.println();
            }
            int[] visited= new int[n];
            for(i=0;i<n;i++) visited[i]=0;
            a=1;
            isConnected(matrix,0,n,visited);
            int ok=1;
            for(i=0;i<n;i++)
                if(visited[i]==0)
                    ok=0;
            if(ok==0) {
                System.out.println("Not connected");
                a++;
                    for (i = 0; i < n; i++)
                        if (visited[i] == 0) {
                            isConnected(matrix, i, n, visited);
                            a++;
                        }
                for(i=0;i<n;i++)
                    System.out.print(visited[i]+ " ");
                System.out.println();
                for(i=1;i<=a;i++)
                {for(j=0;j<n;j++)
                    if(visited[j]==i)
                            System.out.print(j + " ");
                    System.out.println();}
            }
            if(ok==1) {
                System.out.println("Connected");
                for (i = 0; i < n; i++)
                    visited[i] = 0;
                int[][] tree = new int[n][n];
                for (i = 0; i < n; i++)
                    for (j = 0; j < n; j++)
                        tree[i][j] = 0;
                generateTree(matrix, 0, n, visited, tree);
                for (i = 0; i < n; i++) {
                    for (j = 0; j < n; j++)
                        System.out.print(tree[i][j] + " ");
                    System.out.println();
                }
            }
            long finish=System.nanoTime();
            long Time=finish-start;
            System.out.println(Time);
        }
    }
}
