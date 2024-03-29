package com.service;

import com.models.*;
import com.models.Class;
import com.utilities.DBConnection;
import com.utilities.DateFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ClassService {
    public static Integer getNumberOfClasses() throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet result = db.select("select count(*) as number_of_classes from classes");
        if (result.next()) {
            return result.getInt("number_of_classes");
        }
        return 0;
    }

    public static ObservableList<Class> search(String keyWord) throws SQLException, IOException {
        DateFormat dateFormat = new DateFormat();
        keyWord = keyWord.toUpperCase();
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format(
                "select cl.*, co.name as course_name, ac.display_name as teacher_name, ro.name as room_name "
                        +
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
                        "or upper(ro.name) like '%%%s%%'",
                keyWord, keyWord, keyWord, keyWord, keyWord, keyWord, keyWord));
        ObservableList<Class> classes = javafx.collections.FXCollections.observableArrayList();
        while (result.next()) {
            String status = result.getDate("date_start").toLocalDate().isBefore(dateFormat.now())
                    || result.getDate("date_start").toLocalDate().isEqual(dateFormat.now())
                    ? (result.getDate("date_end").toLocalDate().isAfter(dateFormat.now())
                    || result.getDate("date_end").toLocalDate().isEqual(dateFormat.now())
                    ? "In progress"
                    : "Finished")
                    : "Not started";
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
                    dateFormat.toString(result.getDate("date_end").toLocalDate()),
                    status));
        }
        return classes;
    }

    public static ObservableList<Class> getAllClassesNotFinished() throws SQLException, IOException {
        DateFormat dateFormat = new DateFormat();
        DBConnection db = new DBConnection();
        ObservableList<Class> classes = FXCollections.observableArrayList();
        ResultSet result = db.select(
                "select cl.*, co.name as course_name, ac.display_name as teacher_name, ro.name as room_name "
                        +
                        "from classes cl " +
                        "join account ac on cl.teacher_id = ac.id " +
                        "join course co on cl.course_id = co.id " +
                        "join room ro on cl.room_id = ro.id " +
                        "where cl.date_end >= '" + dateFormat.toString(dateFormat.now()) + "'");
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
                    dateFormat.toString(result.getDate("date_end").toLocalDate()), "In progress"));
        }
        return classes;
    }

    public static ObservableList<Class> searchWithTeacherID(String teacherID, String keyWord)
            throws SQLException, IOException {
        DateFormat dateFormat = new DateFormat();
        keyWord = keyWord.toUpperCase();
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format(
                "select cl.*, co.name as course_name, ac.display_name as teacher_name, ro.name as room_name "
                        +
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
                        "or upper(ro.name) like '%%%s%%')",
                teacherID, keyWord, keyWord, keyWord, keyWord, keyWord, keyWord, keyWord));
        ObservableList<Class> classes = javafx.collections.FXCollections.observableArrayList();
        while (result.next()) {
            String status = result.getDate("date_start").toLocalDate().isBefore(dateFormat.now())
                    || result.getDate("date_start").toLocalDate().isEqual(dateFormat.now())
                    ? (result.getDate("date_end").toLocalDate().isAfter(dateFormat.now())
                    || result.getDate("date_end").toLocalDate().isEqual(dateFormat.now())
                    ? "In progress"
                    : "Finished")
                    : "Not started";
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
                    dateFormat.toString(result.getDate("date_end").toLocalDate()), status));
        }
        return classes;
    }

    public static ObservableList<Class> searchWithStudentID(String studentID, String keyWord)
            throws SQLException, IOException {
        DateFormat dateFormat = new DateFormat();
        keyWord = keyWord.toUpperCase();
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format(
                "select cl.*, co.name as course_name, ac.display_name as teacher_name, ro.name as room_name "
                        +
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
                        "or upper(ro.name) like '%%%s%%')",
                studentID, keyWord, keyWord, keyWord, keyWord, keyWord, keyWord, keyWord));
        ObservableList<Class> classes = javafx.collections.FXCollections.observableArrayList();
        while (result.next()) {
            String status = result.getDate("date_start").toLocalDate().isBefore(dateFormat.now())
                    || result.getDate("date_start").toLocalDate().isEqual(dateFormat.now())
                    ? (result.getDate("date_end").toLocalDate().isAfter(dateFormat.now())
                    || result.getDate("date_end").toLocalDate().isEqual(dateFormat.now())
                    ? "In progress"
                    : "Finished")
                    : "Not started";
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
                    dateFormat.toString(result.getDate("date_end").toLocalDate()), status));
        }
        return classes;
    }

    public static void Insert(String id, String name, String course, String teacher, String room,
                              String session_day,
                              String session_time, String start, String end) throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format(
                "select * from classes where session_day = '%s' and session_time = '%s' and room_id = '%s' and id != '%s' and date_start <= '%s' and date_end >='%s'",
                session_day, session_time, room, id, end, start));
        if (result.next()) {
            throw new SQLException("This room is not available for this time");
        }
        ResultSet result2 = db.select(String.format(
                "select * from classes where session_day = '%s' and session_time = '%s' and teacher_id = '%s' and id != '%s' and date_start <= '%s' and date_end >='%s'",
                session_day, session_time, teacher, id, end, start));
        if (result2.next()) {
            throw new SQLException("This teacher is not available for this time");
        }
        db.insert(String.format(
                "INSERT INTO classes(id,teacher_id,name,course_id,date_start,date_end,session_time,session_day,room_id) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                id, teacher, name, course, start, end, session_time, session_day, room));
    }

    public static void Update(String id, String name, String course, String teacher, String room,
                              String session_day,
                              String session_time, String start, String end) throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format(
                "select * from classes where session_day = '%s' and session_time = '%s' and room_id = '%s' and id != '%s'",
                session_day, session_time, room, id));
        if (result.next()) {
            throw new SQLException("This room is not available for this time");
        }
        ResultSet result2 = db.select(String.format(
                "select * from classes where session_day = '%s' and session_time = '%s' and teacher_id = '%s' and id != '%s' and date_start <= '%s' and date_end >= '%s'",
                session_day, session_time, teacher, id, end, start));
        if (result2.next()) {
            throw new SQLException("This teacher is not available for this time");
        }
        db.update(String.format(
                "update classes set teacher_id = '%s', name = '%s', course_id = '%s', date_start = '%s', date_end = '%s', session_time = '%s', session_day = '%s', room_id = '%s' where id = '%s'",
                teacher, name, course, start, end, session_time, session_day, room, id));
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

    public static void removeStudent(String studentID) throws SQLException {
        DBConnection db = new DBConnection();
        db.delete(String.format("delete from class_attendance ca\n" +
                "where student_id = '%s'", studentID));
        db.delete(String.format("delete from student where id = '%s'", studentID));
    }

    public static ObservableList<Student> getStudents(String classID) throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet resultSet = db.select(String.format("select st.id, ac.display_name " +
                "from student st " +
                "join account ac on st.user_id = ac.id " +
                "where st.class_id = '%s'", classID));
        ObservableList<Student> students = FXCollections.observableArrayList();
        while (resultSet.next()) {
            students.add(new Student(resultSet.getString("id"),
                    resultSet.getString("display_name")));
        }
        return students;
    }

    public static int getNumberOfStudentInClass(String classID) throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet resultSet = db.select(String.format("select count(*) as number_of_student " +
                "from student st " +
                "where st.class_id = '%s'", classID));
        if (resultSet.next()) {
            return resultSet.getInt("number_of_student");
        }
        return 0;
    }

    public static ArrayList<String[]> getLessons(String classID) throws SQLException {
        DateFormat dateFormat = new DateFormat();
        DBConnection db = new DBConnection();
        ResultSet resultSet = db
                .select(String.format(
                        "select name, learn_date, count(student_id) as number_of_present_student\n"
                                +
                                "from lesson le\n" +
                                "join chapter ch on chapter_id = ch.id\n" +
                                "left join class_attendance ca on ca.lesson_id = le.id\n"
                                +
                                "where class_id = '%s'\n" +
                                "group by name, learn_date\n" +
                                "order by learn_date",
                        classID));
        ArrayList<String[]> lessons = new ArrayList<>();
        while (resultSet.next()) {
            lessons.add(new String[]{
                    resultSet.getString("name"),
                    dateFormat.toString(resultSet.getDate("learn_date").toLocalDate()),
                    resultSet.getString("number_of_present_student")
            });
        }
        return lessons;
    }
}
