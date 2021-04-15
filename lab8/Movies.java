package com.company;
import java.sql.*;
import com.oracle.jdbc.Driver;

public class Movies {

    int id,score,duration;
    String title,release_date;
    Connection conn;

    public  Movies(Connection conn)
    {
        this.conn=conn;
    }
    public void find_by_id(int id) throws SQLException{
        Statement myStatement = conn.createStatement();
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM movies WHERE id='%d'",id));
        while (myResult.next()) {
            System.out.println("Movie:" + myResult.getString("title") + " " + myResult.getString("release_date"));
        }
        }
    public void create(int id,String title,int score,String release_date,int duration) throws SQLException
    {
        id++;
        Statement myStatement = conn.createStatement();
        myStatement.executeUpdate(String.format("INSERT INTO movies (id, title, score, release_date, duration) VALUES (%d, '%s','%d','%s','%d')",id,title,score,release_date,duration));

    }

    public void findByName(String name) throws SQLException
    {
        Statement myStatement = conn.createStatement();
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM movies WHERE title='%s'",name));
        while (myResult.next()) {
            System.out.println("Movie:" + myResult.getString("title") + " "+ myResult.getString("release_date"));

        }

    }
}
