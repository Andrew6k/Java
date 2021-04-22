package com.company;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private static Database db=null;

    public  Connection myconn=null;

    private Database() throws SQLException {
        try {

            String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "student";
            String password = "student";

            myconn = DriverManager.getConnection(dbURL, username, password);
            //System.out.println("YES");
            /*
            String sql= "INSERT INTO CURSURI (id,titlu_curs) VALUES (30,'A')";
            Statement statement=myconn.createStatement();
            statement.executeUpdate(sql);
            statement.close();

             */

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static Database Database() throws SQLException {
        if(db==null)
        {
            db=new Database();
        }
        return db;
    }

    public Connection getConnection() {
        return myconn;
    }
}

