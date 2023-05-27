package com.utilities;

import java.sql.*;

public class DBConnection {

    private static final String DB_URL = Env.get("DB_URL");
    private static final String DB_USER = Env.get("DB_USER");
    private static final String DB_PASSWORD = Env.get("DB_PASSWORD");
    private Connection connection;

    public DBConnection() throws SQLException {
        connect();
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public ResultSet select(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public void insert(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public void update(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public void delete(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public void close() throws SQLException {
        connection.close();
    }


}