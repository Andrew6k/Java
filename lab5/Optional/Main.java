package com.company;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Formatter;
import java.text.Format;
public class Main {
    public static void validate(String s,String arg,Catalog catalog)throws StringException{
        int ok=0;
        List<String> list = Arrays.asList(new String[]{"add", "list","load","play","report","save"});
        for(String no : list)
            if(no.equals(s))
                ok = 1;
        if(s==null || ok==0)
            throw new StringException("not valid");
        if(s.equals("report")) {
            try {
                ReportCommand.report(catalog);
            } catch (TemplateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException, InvalidCatalogException, TemplateException {
        Catalog catalog=new Catalog("Movies","d:/Movies/catalog.ser");
        var song=new Song("Eminem");
        song.setName("Mockingbird");
        var movie=new Movie();
        movie.setName("Pulp Fiction");
        System.out.println(song.getAuthor());
        catalog.addItem(song);
        catalog.addItem(movie);
        Item j = new Item();
        j.setName("picture");
        j.setExtensie("png");
        j.setLocation("C:\\Users\\cirja\\OneDrive\\Desktop");
        catalog.addItem(j);
        CatalogUtil.save(catalog);
        catalog.list();

        Scanner s= new Scanner(System.in);
        System.out.print("Care este comanda? ");
        String comanda=s.next();
        System.out.print("Argument? ");
        String argument1=s.next();
        //String argument2=s.next();
        s.close();
        System.out.println(comanda);
        System.out.println(argument1);
        try {
            validate(comanda,argument1,catalog);
        } catch (StringException e) {
            e.printStackTrace();
        }
        //ReportCommand.report(catalog);


    }
}

class StringException extends Exception{
    public StringException(String message){
        super(message);
    }
}