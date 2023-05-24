package com.service;

import com.models.Chapter;
import com.utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ChapterService {

    public static String getNewId() throws SQLException {
        String id = UUID.randomUUID().toString().substring(0, 8);
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select * from chapter where id = '%s'", id));
        if (result.next()) {
            return getNewId();
        }
        return id;
    }

    public static ObservableList<Chapter> search(String courseID, String keyWord) throws SQLException, IOException {
        keyWord = keyWord.toUpperCase();
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select * from chapter where course_id = '%s' and (upper(id) like '%%%s%%' or upper(name) like '%%%s%%' or upper(description) like '%%%s%%' or upper(category) like '%%%s%%') order by id asc", courseID, keyWord, keyWord, keyWord, keyWord));
        ObservableList<Chapter> chapters = FXCollections.observableArrayList();
        while (result.next()) {
            chapters.add(new Chapter(
                    result.getString("id"),
                    result.getString("name"),
                    result.getString("description"),
                    result.getString("category")
            ));
        }
        chapters.add(new Chapter());
        return chapters;
    }

    public static void InsertNewChapter(String id, String courseID, String name, String description, String category) throws SQLException {
        DBConnection db = new DBConnection();
        String query = String.format("INSERT INTO chapter VALUES ('%s', '%s', '%s', '%s', '%s')", id, courseID, name, category, description);
        db.insert(query);
    }

    public static void UpdateChapter(String id, String name, String description, String category) throws SQLException {
        DBConnection db = new DBConnection();
        String query = String.format("UPDATE chapter SET name = '%s', description = '%s', category = '%s' WHERE id = '%s'", name, description, category, id);
        db.update(query);
    }

    public static void DeleteChapter(String chapterID) throws SQLException {
        DBConnection db = new DBConnection();
        String query = String.format("DELETE FROM chapter WHERE id = '%s'", chapterID);
        db.delete(query);
    }
}
