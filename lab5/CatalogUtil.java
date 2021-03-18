package com.company;

import java.awt.*;
import java.io.*;

public class CatalogUtil {
    public static void save(Catalog catalog) throws IOException {
        try (var oos = new ObjectOutputStream(new FileOutputStream(catalog.getPath()))) {
            oos.writeObject(catalog);
        }
    }
    public static Catalog load(String path) throws InvalidCatalogException {
        try {
            var x=new ObjectInputStream(new FileInputStream(path));
            x.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void play(Item item) throws IOException {
        Desktop desktop=Desktop.getDesktop();
        File file=new File(item.getLocation());
        desktop.open(file);
    }

    }



