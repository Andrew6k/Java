package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadCommand {
    public static Catalog load(String path) throws InvalidCatalogException {
        try {
            var x=new ObjectInputStream(new FileInputStream(path));
            x.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
