package com.utilities;

import java.sql.*;

public class DBConnection {

    private static final String DB_URL = Env.get("DB_URL");
    private static final String DB_USER = Env.get("DB_USER");
    private static final String DB_PASSWORD = Env.get("DB_PASSWORD");
    private Connection connection;

    public DBConnection() {
        connect();
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("ERROR connecting to database!");
            System.out.println(e.toString());
        }
    }

    public ResultSet select(String query) {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            return result;
        } catch (SQLException e) {
            System.out.println("ERROR while executing select query!");
            System.out.println(e.toString());
            return null;
        }
    }

    public int update(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("ERROR while executing update query");
            System.out.println(e.toString());
            return -1;
        }
    }

    public int delete(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("ERROR while deleting line!");
            System.out.println(e.toString());
            return -1;
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("ERROR while closing connections!");
            System.out.println(e.toString());
        }
    }

}