package com.company;

import java.util.*;
import java.util.prefs.Preferences;

public class TravelPlan {
    City c;
    private List<Location> preferences=new ArrayList<>();

    public City getC() {
        return c;
    }

    public void setC(City c) {
        this.c = c;
    }

    public List<Location> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<Location> preferences) {
        this.preferences = preferences;
    }

    public void addLocation(Location node){
        preferences.add(node);
    }
    public Location getPreferenceIndex(int i){
        return preferences.get(i);
    }
    public static Location getLowestDistanceLocation( Set<Location> available){
        Location closestLocation = null;
        int lowestDistance = 999;
        for(Location l: available){
            int distance = l.getDistance();
            if(distance<lowestDistance){
                lowestDistance=distance;
                closestLocation= l;
            }
        }
        return closestLocation;
    }

    private static void calculateMinimumDistance(Location evaluationLocation,
                                                 Integer roadCost, Location startLocation) {
        Integer startDistance = startLocation.getDistance();
        if (startDistance + roadCost < evaluationLocation.getDistance()) {
            evaluationLocation.setDistance(startDistance + roadCost);
            List<Location> shortestPath = new LinkedList<>(startLocation.getShortestPath());
            shortestPath.add(startLocation);
            evaluationLocation.setShortestPath(shortestPath);
        }
    }

    public static City calculate(City c, Location start){
        start.setDistance(0);
        Set<Location> choosed= new HashSet<>();
        Set<Location> available= new HashSet<>();
        available.add(start);
        while (available.size() != 0) {
            Location currentNode = getLowestDistanceLocation(available);
            available.remove(currentNode);
            for (Map.Entry < Location, Integer> adjacencyPair:
                    currentNode.getCost().entrySet()) {
                Location adjacentNode = adjacencyPair.getKey();
                Integer roadCost = adjacencyPair.getValue();
                if (!choosed.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, roadCost, currentNode);
                    available.add(adjacentNode);
                }
            }
            choosed.add(currentNode);
        }
        return c;
    }

    public static void calculateShortestPath(City c,TravelPlan p,Location l1, Location l2){
        List<String> locations=new ArrayList<>();
        TravelPlan.calculate(c,l1);
        for(Location l:p.getPreferences()){
            locations.add(l.getName());
            TravelPlan.calculate(c,l);
        }
        for(String location: locations){
            System.out.print(location + " ");
        }
        System.out.println();
    }
}
