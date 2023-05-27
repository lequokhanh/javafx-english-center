package com.service;

import com.models.Material;
import com.utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialService {
    public static ObservableList<Material> search(String lessonID, String keyWord) throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet resultSet = db.select(String.format("select * from material where lesson_id = '%s' and upper(path) like '%%%s%%'", lessonID, keyWord.toUpperCase()));
        ObservableList<Material> materials = FXCollections.observableArrayList();
        while (resultSet.next()) {
            materials.add(new Material(resultSet.getString("id"),
                    resultSet.getString("path")));
        }
        return materials;
    }

    public static String getNewID() throws SQLException {
        String id = java.util.UUID.randomUUID().toString().substring(0, 8);
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select * from material where id = '%s'", id));
        if (result.next()) {
            return getNewID();
        }
        return id;
    }

    public static void Insert(String path, String lessonID) throws SQLException {
        String id = getNewID();
        DBConnection db = new DBConnection();
        db.insert(String.format("insert into material(id, path, lesson_id) values ('%s', '%s', '%s')", id, path, lessonID));
    }

    public static void Delete(String id) throws SQLException {
        DBConnection db = new DBConnection();
        db.delete(String.format("delete from material where id = '%s'", id));
    }

    public static void deleteByLesson(String lessonID) throws SQLException {
        DBConnection db = new DBConnection();
        db.delete(String.format("delete from material where lesson_id = '%s'", lessonID));
    }
}
