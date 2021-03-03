package com.company;


public class Problem {

    public Source sources[];
    public Destination destinations[];
    public int destNumber,sourceNumber;
    public int distances[][];

    Problem() {
        this.destNumber = 0;
        this.sourceNumber = 0;
    }

    public void setDestinations(Destination[] destinations) {
        /**
         *
         This method sets in the vector destinations new dates.*/
        this.destinations = destinations;
        destNumber = destinations.length;
    }
    public void setSources(Source[] sources) {
        //This method sets in the vector sources new dates.
        this.sources = sources;
        sourceNumber = sources.length;
    }
    public void setDistances(int[][] distances) {
        this.distances = distances;
    }

    @Override
    public String toString() {
        String s = " ";
        for(int i = 0;i<destNumber;i++)
            s = s + destinations[i].getName() + " ";
        s = s + "Suply";
        s = s + '\n';
        for(int i=0;i<sourceNumber;i++) {
            s = s + sources[i].getName() + "  ";
            for (int j = 0; j < destNumber; j++)
                s = s + distances[i][j] + "  ";
            s = s + sources[i].getCapacity() +'\n';
        }
        s = s + "D   ";
        for (int i = 0;i<destNumber;i++)
            s = s + destinations[i].getDemand() + " ";
        return s;
}

}