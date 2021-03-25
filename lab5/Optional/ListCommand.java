package com.company;

public class ListCommand {
    public Catalog catalog;

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public void list() {
        catalog.items.stream()
                .forEach(System.out::println);
    }

    public Item findById(String id) {
        for (Item item : catalog.items) {
            if (item.getId().equals(id))
                return item;
        }
        return null;
    }
}
