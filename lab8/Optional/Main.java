package com.company;
import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws SQLException {

        Connection conn=Database.Database().getConnection();

        MoviesDAO movie=new MoviesDAO(conn);
        /*
        movie.create("Pulp Fiction",95,"25-05-1994",120);
        movie.create("Rush",86,"12-11-2013",100);
        movie.create("The Last Samurai",90,"15-06-2003",111);
        */

        Movie a=movie.findByName("The Last Samurai");
        Movie a2=movie.find_by_id(2);

        GenresDAO genre=new GenresDAO(conn);
        /*
        genre.create("Action");
        genre.create("Drama");
        genre.create("Comedy");
        */
        genre.findByName("Drama");
        genre.find_by_id(1);

        ActorsDAO actors=new ActorsDAO(conn);
        /*
        actors.create("Brad Pitt","18-12-1963",180);
        actors.create("Tom Cruise","03-07-1962",170);
        actors.create("Leonardo DiCaprio","11-11-1974",183);

         */
        actors.find_by_id(2);
        actors.findByName("Brad Pitt");

        DirectorsDAO directors=new DirectorsDAO(conn);
        /*
        directors.create("Quentin Tarantino", "27-03-1963",185);
        directors.create("David Fincher", "28-08-1962",184);
        directors.create("Peter Jackson","31-10-1961",169);

         */

        directors.find_by_id(3);
        directors.findByName("David Fincher");
        conn.close();//inchiderea conexiunii

        //List<Actor> actorList = Parser.readActorsFromCSV("d:/Faculty/Java/lab8/a");
        List<Actor> actorList = Parser.readActorsFromCSV("d:/Faculty/Java/lab8/actors.txt");
         for (Actor b : actorList) { System.out.println(b); }


    }
}
