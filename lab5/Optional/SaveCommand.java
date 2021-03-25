package com.company;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveCommand {
    public static void save(Catalog catalog) throws IOException {
        try (var oos = new ObjectOutputStream(new FileOutputStream(catalog.getPath()))) {
            oos.writeObject(catalog);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
