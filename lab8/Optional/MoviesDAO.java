package com.company;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoviesDAO {

    Connection conn;

    public MoviesDAO(Connection conn)
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
    public void create(String title,int score,String releaseDate,int duration) throws SQLException
    {

        Statement myStatement = conn.createStatement();
        myStatement.executeUpdate(String.format("INSERT INTO movies (title, score, release_date,duration) VALUES ('%s','%d','%s','%d')",title,score,releaseDate,duration));

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
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM movies WHERE title='%s'",name));
        while (myResult.next()) {
            System.out.println("Movie:" + myResult.getString("title") + " "+ myResult.getString("release_date"));

        }

    }
}
