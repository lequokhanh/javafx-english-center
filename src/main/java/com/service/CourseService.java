package com.service;

import com.models.Course;
import com.utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import java.util.UUID;

public class CourseService {
    public static Integer getNumberOfCourse() throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet result = db.select("select count(*) as number_of_course from course");
        if (result.next()) {
            return result.getInt("number_of_course");
        }
        return 0;
    }

    public static ObservableList<Course> search(String keyWord) throws SQLException, IOException {
        keyWord = keyWord.toUpperCase();
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select course.*, a.number_of_chapter from course, (SELECT course.id, count(course_id) as number_of_chapter from course left join chapter on course.id = course_id group by course.id) a where course.id = a.id and (upper(course.id) like '%%%s%%' or upper(course.name) like '%%%s%%' or upper(course.description) like '%%%s%%' or upper(course.course_level) like '%%%s%%') order by course.id asc", keyWord, keyWord, keyWord, keyWord));
        ObservableList<Course> courses = FXCollections.observableArrayList();
        while (result.next()) {
            courses.add(new Course(
                    result.getString("id"),
                    result.getString("name"),
                    result.getString("description"),
                    result.getString("course_level"),
                    result.getInt("number_of_chapter")
            ));
        }
        courses.add(new Course());
        return courses;
    }

    public static String getNewId() throws SQLException {
        String id = UUID.randomUUID().toString().substring(0, 8);
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select * from course where id = '%s'", id));
        if (result.next()) {
            return getNewId();
        }
        return id;
    }

    public static void InsertNewCourse(String id, String name, String description, String level) throws SQLException {
        DBConnection db = new DBConnection();
        String query = String.format("INSERT INTO course VALUES ('%s', '%s', '%s', '%s')", id, name, description, level);
        db.insert(query);
    }

    public static void UpdateCourse(String id, String name, String description, String level) throws SQLException {
        DBConnection db = new DBConnection();
        String query = String.format("UPDATE course SET name = '%s', description = '%s', course_level = '%s' WHERE id = %s", name, description, level, id);
        db.update(query);
    }

    public static void DeleteCourse(String id) throws SQLException {
        DBConnection db = new DBConnection();
        String query = String.format("DELETE FROM course WHERE id = '%s'", id);
        db.delete(query);
    }
}
