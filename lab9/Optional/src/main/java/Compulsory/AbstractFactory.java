package Compulsory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.function.Supplier;

public class AbstractFactory<T> {
    Connection conn;
    private  Supplier<? extends T> ctor;
    public AbstractFactory(Connection conn,Supplier<? extends T> ctor)
    {
        this.conn=conn;
        this.ctor = Objects.requireNonNull(ctor);
    }
    public void find_by_id(int id,Class<T> cls) throws SQLException {
        Statement myStatement = conn.createStatement();
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM movies WHERE id='%d'",id));
        while (myResult.next()) {
            System.out.println("Movie:" + myResult.getString("title") + " " + myResult.getString("release_date"));
        }
        //found=new ctor.get();
        //T found = new cls(myResult.getInt("id"), myResult.getString("name"), myResult.getString("birth_date"), myResult.getInt("score"), myResult.getInt("duration"));
        //return found;
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
        T found;
        Statement myStatement = conn.createStatement();
        ResultSet myResult = myStatement.executeQuery(String.format("SELECT * FROM movies WHERE title='%s'",name));
        while (myResult.next()) {
            System.out.println("Movie:" + myResult.getString("title") + " "+ myResult.getString("release_date"));

        }
        //found=new T(myResult.getInt("id"),myResult.getString("name"),myResult.getString("birth_date"),myResult.getInt("score"),myResult.getInt("duration"));
        //return found;

    }
}
