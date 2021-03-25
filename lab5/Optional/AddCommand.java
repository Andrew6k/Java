package com.company;

public class AddCommand {
    public Catalog catalog;

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Catalog getCatalog() {
        return catalog;
    }
    public void addItem(Item item){
        catalog.items.add(item);
    }
}
