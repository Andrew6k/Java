package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ActorsDAO {

    Connection conn;

    public ActorsDAO(Connection conn)
    {
        this.conn=conn;
    }
    public void find_by_id(int id) throws SQLException {
        Statement myStatement = conn.createStatement();
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM actors WHERE id='%d'",id));
        while (myResult.next()) {
            System.out.println("Actor:" + myResult.getString("name") + " " + myResult.getString("birth_date")+ " "+myResult.getInt("height"));
        }
    }
    public void create(String name,String birthDate,int height) throws SQLException
    {

        Statement myStatement = conn.createStatement();
        myStatement.executeUpdate(String.format("INSERT INTO actors (name, birth_date, height) VALUES ('%s','%s','%d')",name,birthDate,height));

        /*
        try(PreparedStatement pstmt = conn.prepareStatement("INSERT INTO movies(title, release_date, duration, score) VALUES (?, ?, ?, ?);")) {
            pstmt.setString(1, title);
            pstmt.setDate(2, releaseDate);
            pstmt.setInt(3, duration);
            pstmt.setInt(4, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
    }

    public void findByName(String name) throws SQLException
    {
        Statement myStatement = conn.createStatement();
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM actors WHERE name='%s'",name));
        while (myResult.next()) {
            System.out.println("Actor:" + myResult.getString("name") + " "+ myResult.getString("birth_date")+ " "+myResult.getInt("height"));

        }

    }
}
