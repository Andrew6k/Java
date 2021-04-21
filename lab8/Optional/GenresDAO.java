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
    public void find_by_id(int id) throws SQLException{
        Statement myStatement = conn.createStatement();
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM genres WHERE id='%d'",id));
        while (myResult.next()) {
            System.out.println("Genre:" + myResult.getString("name"));
        }
    }
    public void create(String name) throws SQLException
    {
        Statement myStatement = conn.createStatement();
        myStatement.executeUpdate(String.format("INSERT INTO genres (name) VALUES ('%s')",name));

    }

    public void findByName(String name) throws SQLException
    {
        Statement myStatement = conn.createStatement();
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM genres WHERE name='%s'",name));
        while (myResult.next()) {
            System.out.println("Genre:" + myResult.getString("name") );

        }

    }
}
