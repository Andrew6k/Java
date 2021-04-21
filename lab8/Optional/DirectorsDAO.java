package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DirectorsDAO {

    Connection conn;

    public DirectorsDAO(Connection conn)
    {
        this.conn=conn;
    }
    public void find_by_id(int id) throws SQLException {
        Statement myStatement = conn.createStatement();
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM directors WHERE id='%d'",id));
        while (myResult.next()) {
            System.out.println("Director:" + myResult.getString("name") + " " + myResult.getString("birth_date"));
        }
    }
    public void create(String name,String birthDate,int height) throws SQLException
    {

        Statement myStatement = conn.createStatement();
        myStatement.executeUpdate(String.format("INSERT INTO directors (name, birth_date, height) VALUES ('%s','%s','%d')",name,birthDate,height));


    }

    public void findByName(String name) throws SQLException
    {
        Statement myStatement = conn.createStatement();
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM directors WHERE name='%s'",name));
        while (myResult.next()) {
            System.out.println("Director:" + myResult.getString("name") + " "+ myResult.getString("birth_date"));

        }

    }
}
