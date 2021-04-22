package com.company;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenresDAO {

    Connection conn;

    public GenresDAO(Connection conn)
    {
        this.conn=conn;
    }
    public Genre find_by_id(int id) throws SQLException{
        Genre found;
        Statement myStatement = conn.createStatement();
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM genres WHERE id='%d'",id));
        while (myResult.next()) {
            System.out.println("Genre:" + myResult.getString("name"));
        }
        found=new Genre(myResult.getInt("id"),myResult.getString("name"));
        return found;
    }
    public void create(String name) throws SQLException
    {
        Statement myStatement = conn.createStatement();
        myStatement.executeUpdate(String.format("INSERT INTO genres (name) VALUES ('%s')",name));

    }

    public Genre findByName(String name) throws SQLException
    {
        Genre found;
        Statement myStatement = conn.createStatement();
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM genres WHERE name='%s'",name));
        while (myResult.next()) {
            System.out.println("Genre:" + myResult.getString("name") );

        }
        found=new Genre(myResult.getInt("id"),myResult.getString("name"));
        return found;

    }
}
