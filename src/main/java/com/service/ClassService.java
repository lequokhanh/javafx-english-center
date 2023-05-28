package com.service;

import com.models.Class;
import com.models.Course;
import com.models.Room;
import com.models.Teacher;
import com.utilities.DBConnection;
import com.utilities.DateFormat;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ClassService {
    public static ObservableList<Class> search(String keyWord) throws SQLException, IOException {
        DateFormat dateFormat = new DateFormat();
        keyWord = keyWord.toUpperCase();
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select cl.*, co.name as course_name, ac.display_name as teacher_name, ro.name as room_name " +
                "from classes cl " +
                "join account ac on cl.teacher_id = ac.id " +
                "join course co on cl.course_id = co.id " +
                "join room ro on cl.room_id = ro.id " +
                "where upper(cl.id) like '%%%s%%' " +
                "or upper(ac.display_name) like '%%%s%%' " +
                "or upper(co.name) like '%%%s%%' " +
                "or upper(cl.name) like '%%%s%%' " +
                "or upper(cl.date_start) like '%%%s%%' " +
                "or upper(cl.date_end) like '%%%s%%'" +
                "or upper(ro.name) like '%%%s%%'", keyWord, keyWord, keyWord, keyWord, keyWord, keyWord, keyWord));
        ObservableList<Class> classes = javafx.collections.FXCollections.observableArrayList();
        while (result.next()) {
            classes.add(new Class(result.getString("id"),
                    result.getString("name"),
                    new Course(result.getString("course_id"),
                            result.getString("course_name")),
                    new Teacher(result.getString("teacher_id"),
                            result.getString("teacher_name")),
                    new Room(result.getString("room_id"),
                            result.getString("room_name")),
                    result.getString("session_day"),
                    result.getString("session_time"),
                    dateFormat.toString(result.getDate("date_start").toLocalDate()),
                    dateFormat.toString(result.getDate("date_end").toLocalDate())));
        }
        return classes;
    }

    public static ObservableList<Class> searchWithTeacherID(String teacherID, String keyWord) throws SQLException, IOException {
        DateFormat dateFormat = new DateFormat();
        keyWord = keyWord.toUpperCase();
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select cl.*, co.name as course_name, ac.display_name as teacher_name, ro.name as room_name " +
                "from classes cl " +
                "join account ac on cl.teacher_id = ac.id " +
                "join course co on cl.course_id = co.id " +
                "join room ro on cl.room_id = ro.id " +
                "where teacher_id = '%s' " +
                "and (upper(cl.id) like '%%%s%%' " +
                "or upper(ac.display_name) like '%%%s%%' " +
                "or upper(co.name) like '%%%s%%' " +
                "or upper(cl.name) like '%%%s%%' " +
                "or upper(cl.date_start) like '%%%s%%' " +
                "or upper(cl.date_end) like '%%%s%%'" +
                "or upper(ro.name) like '%%%s%%')", teacherID, keyWord, keyWord, keyWord, keyWord, keyWord, keyWord, keyWord));
        ObservableList<Class> classes = javafx.collections.FXCollections.observableArrayList();
        while (result.next()) {
            classes.add(new Class(result.getString("id"),
                    result.getString("name"),
                    new Course(result.getString("course_id"),
                            result.getString("course_name")),
                    new Teacher(result.getString("teacher_id"),
                            result.getString("teacher_name")),
                    new Room(result.getString("room_id"),
                            result.getString("room_name")),
                    result.getString("session_day"),
                    result.getString("session_time"),
                    dateFormat.toString(result.getDate("date_start").toLocalDate()),
                    dateFormat.toString(result.getDate("date_end").toLocalDate())));
        }
        return classes;
    }

    public static ObservableList<Class> searchWithStudentID(String studentID, String keyWord) throws SQLException, IOException {
        DateFormat dateFormat = new DateFormat();
        keyWord = keyWord.toUpperCase();
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select cl.*, co.name as course_name, ac.display_name as teacher_name, ro.name as room_name " +
                "from classes cl " +
                "join student st on st.class_id = cl.id " +
                "join account ac on cl.teacher_id = ac.id " +
                "join course co on cl.course_id = co.id " +
                "join room ro on cl.room_id = ro.id " +
                "where st.user_id ='%s' " +
                "and (upper(cl.id) like '%%%s%%' " +
                "or upper(ac.display_name) like '%%%s%%' " +
                "or upper(co.name) like '%%%s%%' " +
                "or upper(cl.name) like '%%%s%%' " +
                "or upper(cl.date_start) like '%%%s%%' " +
                "or upper(cl.date_end) like '%%%s%%'" +
                "or upper(ro.name) like '%%%s%%')", studentID, keyWord, keyWord, keyWord, keyWord, keyWord, keyWord, keyWord));
        ObservableList<Class> classes = javafx.collections.FXCollections.observableArrayList();
        while (result.next()) {
            classes.add(new Class(result.getString("id"),
                    result.getString("name"),
                    new Course(result.getString("course_id"),
                            result.getString("course_name")),
                    new Teacher(result.getString("teacher_id"),
                            result.getString("teacher_name")),
                    new Room(result.getString("room_id"),
                            result.getString("room_name")),
                    result.getString("session_day"),
                    result.getString("session_time"),
                    dateFormat.toString(result.getDate("date_start").toLocalDate()),
                    dateFormat.toString(result.getDate("date_end").toLocalDate())));
        }
        return classes;
    }


    public static void Insert(String id, String name, String course, String teacher, String room, String session_day, String session_time, String start, String end) throws SQLException {
        DBConnection db = new DBConnection();
        db.insert(String.format("INSERT INTO classes(id,teacher_id,name,course_id,date_start,date_end,session_time,session_day,room_id) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s')", id, teacher, name, course, start, end, session_time, session_day, room));
    }

    public static void Update(String id, String name, String course, String teacher, String room, String session_day, String session_time, String start, String end) throws SQLException {
        DBConnection db = new DBConnection();
        db.update(String.format("update classes set teacher_id = '%s', name = '%s', course_id = '%s', date_start = '%s', date_end = '%s', session_time = '%s', session_day = '%s', room_id = '%s' where id = '%s'", teacher, name, course, start, end, session_time, session_day, room, id));
    }

    public static void Delete(String id) throws SQLException {
        DBConnection db = new DBConnection();
        db.delete(String.format("delete from classes where id = '%s'", id));
    }

    public static String getNewId() throws SQLException {
        String id = UUID.randomUUID().toString().substring(0, 8);
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select id from classes where id = '%s'", id));
        if (result.next()) {
            return getNewId();
        }
        return id;
    }
}
