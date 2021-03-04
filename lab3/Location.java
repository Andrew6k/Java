package com.company;

import javax.lang.model.type.NullType;
import java.util.HashMap;
import java.util.Map;

public abstract class Location implements Comparable<Location>{
    protected
     String name;
    protected String description;
    private Map<Location,Integer> cost=new HashMap<>();

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
}
