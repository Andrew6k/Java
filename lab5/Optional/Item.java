package com.company;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Item implements Serializable {
    private String id;
    public String name;
    private String location;
    private String extensie;
    private Map<String,Object> tags=new HashMap<>();
    public void addTag(String key, Object obj){
        tags.put(key,obj);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getExtensie() {
        return extensie;
    }

    public void setExtensie(String extensie) {
        this.extensie = extensie;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Item name='" + name + '\'';
    }
}
