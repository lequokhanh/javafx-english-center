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
        DBConnection db = new DBConnection();
        db.insert(String.format("INSERT INTO lesson VALUES ('%s', '%s', '%s', '%s')", classID, id, learnDate, chapterID));
    }

    public static void Update(String id, String chapterID, String learnDate) throws SQLException {
        DBConnection db = new DBConnection();
        db.update(String.format("UPDATE lesson SET chapter_id = '%s', learn_date = '%s' WHERE id = '%s'", chapterID, learnDate, id));
    }

    public static void Delete(String id) throws SQLException {
        MaterialService.deleteByLesson(id);
        DBConnection db = new DBConnection();
        db.delete(String.format("DELETE FROM lesson WHERE id = '%s'", id));
    }

    public static ObservableList<Point> getLessonPresentStudent(String classID) throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet resultSet = db.select(String.format("SELECT count(st.id) as number_of_student, ca.name as lesson_name\n" +
                "from lesson le\n" +
                "join classes cl on le.class_id = cl.id\n" +
                "join student st on cl.id = st.class_id\n" +
                "join chapter ca on le.chapter_id = ca.id\n" +
                "where le.class_id = '%s' and st.id not in (select student_id from class_attendance where lesson_id = le.id) \n" +
                "group by ca.name", classID));
        ObservableList<Point> points = FXCollections.observableArrayList();
        while (resultSet.next()) {
            points.add(new Point(resultSet.getString("lesson_name"), resultSet.getInt("number_of_student")));
        }
        return points;
    }
}
