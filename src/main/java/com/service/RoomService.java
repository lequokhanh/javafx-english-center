package com.service;

import com.models.Room;
import com.utilities.DBConnection;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RoomService {

    public static String getNewID() throws SQLException {
        String id = UUID.randomUUID().toString().substring(0, 8);
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select * from room where id = '%s'", id));
        if (result.next()) {
            return getNewID();
        }
        return id;
    }

    public static ObservableList<Room> search(String keyWord) throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select * from room where upper(id) like '%%%s%%' or upper(name) like '%%%s%%' or upper(capacity) like '%%%s%%'", keyWord.toUpperCase(), keyWord.toUpperCase(), keyWord.toUpperCase()));
        ObservableList<Room> rooms = javafx.collections.FXCollections.observableArrayList();
        while (result.next()) {
            rooms.add(new Room(result.getString("id"), result.getString("name"), result.getString("capacity")));
        }
        return rooms;
    }

    public static void Insert(String id, String name, String capacity) throws SQLException {
        DBConnection db = new DBConnection();
        String query = String.format("INSERT INTO room VALUES ('%s', '%s', '%s')", id, name, capacity);
        db.insert(query);
    }

    public static void Update(String id, String name, String capacity) throws SQLException {
        DBConnection db = new DBConnection();
        String query = String.format("UPDATE room SET name = '%s', capacity = '%s' WHERE id = '%s'", name, capacity, id);
        db.insert(query);
    }

    public static void Delete(String id) throws SQLException {
        DBConnection db = new DBConnection();
        String query = String.format("DELETE FROM room WHERE id = '%s'", id);
        db.insert(query);
    }
}
