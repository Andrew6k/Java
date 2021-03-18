package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InvalidCatalogException {
        Catalog catalog=new Catalog("Movies","d:/Movies/catalog.ser");
        var song=new Song("Eminem");
        song.setId("Mockingbird");
        var movie=new Movie();
        movie.setName("Pulp Fiction");
        System.out.println(song.getAuthor());
        catalog.addItem(song);
        catalog.addItem(movie);
        CatalogUtil.save(catalog);
        Catalog catalog1=CatalogUtil.load("d:java/catalog.ser");
        CatalogUtil.play(catalog.findById("Mockingbird"));
    }
}
