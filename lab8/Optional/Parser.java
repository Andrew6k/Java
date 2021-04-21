package com.company;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
public class Parser {

    private static Actor createActor(String[] metadata) { int id=Integer.parseInt(metadata[0]);
        String name = metadata[1];
        int height = Integer.parseInt(metadata[3]);
        String birthdate = metadata[5]; //
         return new Actor(id,name, birthdate,height);
    }

    public static List<Actor> readActorsFromCSV(String fileName) {
        List<Actor> actors = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                Actor actor = createActor(attributes);
                 actors.add(actor);
                 line = br.readLine(); }
        } catch (IOException ioe)
            { ioe.printStackTrace(); } return actors; }

        }