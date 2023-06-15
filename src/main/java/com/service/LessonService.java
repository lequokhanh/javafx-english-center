package com.service;

import com.models.Chapter;
import com.models.Lesson;
import com.models.Point;
import com.utilities.DBConnection;
import com.utilities.DateFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LessonService {
    public static String getNewId() throws SQLException {
        String id = java.util.UUID.randomUUID().toString().substring(0, 8);
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select * from lesson where id = '%s'", id));
        if (result.next()) {
            return getNewId();
        }
        return id;
    }

    public static ObservableList<Lesson> search(String classID, String keyWord) throws SQLException, IOException {
        DateFormat dateFormat = new DateFormat();
        keyWord = keyWord.toUpperCase();
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select * from lesson join chapter on chapter_id = chapter.id where class_id = '%s' and (upper(lesson.id) like '%%%s%%' or upper(chapter.name) like '%%%s%%' or upper(chapter.category) like '%%%s%%' or upper(learn_date) like '%%%s%%' or upper(chapter.description) like '%%%s%%') order by learn_date asc", classID, keyWord, keyWord, keyWord, keyWord, keyWord));
        ObservableList<Lesson> lessons = javafx.collections.FXCollections.observableArrayList();
        while (result.next()) {
            lessons.add(new Lesson(result.getString("id"),
                    new Chapter(result.getString("chapter_id"),
                            result.getString("name"),
                            result.getString("description"),
                            result.getString("category")),
                    dateFormat.toString(result.getDate("learn_date").toLocalDate())));

        }
        return lessons;
    }

    public static void Insert(String classID, String id, String chapterID, String learnDate) throws SQLException {
        DateFormat dateFormat = new DateFormat();
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select * from classes where id = '%s'", classID));
        if (result.next()) {
            if (dateFormat.fromString(learnDate).isBefore(result.getDate("start").toLocalDate()) || dateFormat.fromString(learnDate).isAfter(result.getDate("end").toLocalDate())) {
                throw new SQLException("Learn date must be between class start and end date");
            }
        } else {
            throw new SQLException("Class was deleted before");
        }
        db.insert(String.format("INSERT INTO lesson VALUES ('%s', '%s', '%s', '%s')", classID, id, learnDate, chapterID));
    }

    public static void Update(String id, String chapterID, String learnDate) throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select * from classes join lesson on classes.id = lesson.class_id where lesson.id = '%s'", id));
        if (result.next()) {
            if (learnDate.compareTo(result.getDate("start").toString()) < 0 || learnDate.compareTo(result.getDate("end").toString()) > 0) {
                throw new SQLException("Learn date must be between class start and end date");
            }
        } else {
            throw new SQLException("Class was deleted before");
        }
        db.update(String.format("UPDATE lesson SET chapter_id = '%s', learn_date = '%s' WHERE id = '%s'", chapterID, learnDate, id));
    }

    public static void Delete(String id) throws SQLException {
        MaterialService.deleteByLesson(id);
        DBConnection db = new DBConnection();
        db.delete(String.format("DELETE FROM class_attendance WHERE lesson_id = '%s'", id));
        db.delete(String.format("DELETE FROM material WHERE lesson_id = '%s'", id));
        db.delete(String.format("DELETE FROM lesson WHERE id = '%s'", id));
    }

    public static ObservableList<Point> getLessonPresentStudent(String classID) throws SQLException {
        DateFormat dateFormat = new DateFormat();
        DBConnection db = new DBConnection();
        ResultSet resultSet = db.select(String.format("SELECT learn_date, count(student_id) as number_of_student\n" +
                "FROM lesson le \n" +
                "LEFT JOIN class_attendance ca ON le.id = ca.lesson_id\n" +
                "WHERE class_id = '%s'\n" +
                "GROUP BY le.id, learn_date\n" +
                "ORDER BY learn_date\n", classID));
        ObservableList<Point> points = FXCollections.observableArrayList();
        while (resultSet.next()) {
            points.add(new Point(
                    dateFormat.toString(resultSet.getDate("learn_date").toLocalDate()),
                    resultSet.getInt("number_of_student")));
        }
        return points;
    }
}
