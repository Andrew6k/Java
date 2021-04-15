package com.company;
import java.sql.*;
public class Main {


    public static void main(String[] args) throws SQLException {

        Connection conn=Database.Database();

        Movies movie=new Movies(conn);
        movie.create(1,"Pulp Fiction",95,"05-09-1994",120);
        movie.create(2,"Rush",86,"11-12-2013",100);
        movie.create(3,"The Last Samurai",90,"15-06-2003",111);

        movie.findByName("The Last Samurai");
        movie.find_by_id(2);

        Genres genre=new Genres(conn);

        genre.create(1,"Action");
        genre.create(2,"Drama");
        genre.create(3,"Comedy");

        genre.findByName("Drama");
        genre.find_by_id(1);
        conn.close();//inchiderea conexiunii
    }
}
