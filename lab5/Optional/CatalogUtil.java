package com.company;

import java.awt.*;
import java.io.*;

public class CatalogUtil {
    public static void save(Catalog catalog) throws IOException {
        try (var oos = new ObjectOutputStream(new FileOutputStream(catalog.getPath()))) {
            oos.writeObject(catalog);
        } catch(IOException e){
            e.printStackTrace();
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
        try {
            File file = new File(item.getLocation() + "//" + item.getName() + '.' +item.getExtensie());

            if(!(file.exists())){
                throw new MyCustomException("File does not exist!");
            }

            Desktop.getDesktop().open(file);
        }catch(java.lang.IllegalArgumentException e){
            System.out.println("File not found!");
        }catch (MyCustomException x){
            x.printStackTrace();
        }
    }

    public static void report(Catalog catalog) throws IOException{

    }
    }



