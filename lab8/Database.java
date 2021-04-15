package com.company;
import java.sql.*;
import com.oracle.jdbc.Driver;

public class Database {

    private static Database db=null;

    public static Connection myconn=null;

    private Database() throws SQLException {
        try {

            String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "student";
            String password = "student";

            myconn = DriverManager.getConnection(dbURL, username, password);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static Connection Database() throws SQLException {
        if(db==null)
        {
            db=new Database();
        }
        return myconn;
    }
}

