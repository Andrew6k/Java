package com.company;

import java.util.*;

public class City {
    private List<Location> nodes= new LinkedList<>();
    String nume;
    City(){
        nodes=new LinkedList<>();
    }
    public List<Location> getNodes() {
        return nodes;
    }

    public void setNodes(List<Location> nodes) {
        this.nodes = nodes;
    }

    public void addLocation(Location node){
        nodes.add(node);
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
    public void printLocationList(){
        for(Location node1 : nodes)
            for(Location node2 : nodes)
                if(node1.getDistanceToLocation(node2)!=-1)
                    System.out.println(node1.getName()+ "->" + node2.getName() + " cost: " +node1.getDistanceToLocation(node2));
    }
    public void visit(){
        List<Location> l1=new LinkedList<>();
        for(Location node1 : nodes)
                if(node1 instanceof Visitable && !(node1 instanceof Payable))
                    l1.add(node1);
        Collections.sort(l1, new Comparator<Location>() {
            @Override
            public int compare(Location o1, Location o2) {
                Visitable v1=(Visitable) o1;
                Visitable v2=(Visitable) o2;
                return v1.getOpeningTime().compareTo(v2.getOpeningTime());
            }
        });
        for(Location node1 : l1)
            System.out.println(node1.getName());
    }
}
