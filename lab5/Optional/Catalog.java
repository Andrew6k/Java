package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable {
    public String name;
    private String path;
    Catalog(String n, String p){
        name=n;
        path=p;
    }
    public List<Item> items= new ArrayList<>();
    public void addItem(Item item){
        items.add(item);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void list(){
        items.stream()
                .forEach(System.out::println);
    }

    public List<Item> getItems() {
        return items;
    }

    public Item findById(String id){
        for(Item item: items){
            if(item.getId().equals(id))
                return item;
        }
        return null;
    }


}
