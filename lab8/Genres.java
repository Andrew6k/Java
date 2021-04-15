package com.company;
import java.sql.*;
import com.oracle.jdbc.Driver;

public class Genres {

    int id;
    String name;
    Connection conn;

    public  Genres(Connection conn)
    {
        this.conn=conn;
    }
    public void find_by_id(int id) throws SQLException{
        Statement myStatement = conn.createStatement();
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM genres WHERE id='%d'",id));
        while (myResult.next()) {
            System.out.println("Genre:" + myResult.getString("name"));
        }
    }
    public void create(int id,String name) throws SQLException
    {
        id++;
        Statement myStatement = conn.createStatement();
        myStatement.executeUpdate(String.format("INSERT INTO genres (id, name) VALUES (%d, '%s')",id,name));

    }

    public void findByName(String name) throws SQLException
    {
        Statement myStatement = conn.createStatement();
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM genres WHERE title='%s'",name));
        while (myResult.next()) {
            System.out.println("Genre:" + myResult.getString("name") );

        }

    }
}
