package com.company;

import javax.lang.model.type.NullType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Location implements Comparable<Location>{
    protected
     String name;
    protected String description;
    private Map<Location,Integer> cost=new HashMap<>();
    private Integer distance=999;
    private List<Location> shortestPath=new LinkedList<>();

    public void setShortestPath(List<Location> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public List<Location> getShortestPath() {
        return shortestPath;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Map<Location, Integer> getCost() {
        return cost;
    }

    public void setCost(Map<Location, Integer> cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(Location o) {
        return this.name.compareTo(o.name);
    }
    public void addNewDistanceToLocation(Location location, int distance){
        cost.put(location,distance);
    }
    public int getDistanceToLocation(Location location){
        if(cost.containsKey(location))
            return cost.get(location);
        return -1;
    }
}
