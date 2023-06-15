package com.service;

import com.models.Student;
import com.utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentService {
    public static Integer getNumberOfStudent() throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet resultSet = db.select("select count(distinct user_id) as count from student");
        if (resultSet.next()) {
            return resultSet.getInt("count");
        }
        return 0;
    }

    public static ObservableList<Student> absentStudent(String classID, String lessonID) throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet resultSet = db.select(String.format("SELECT * \n" +
                "from student st\n" +
                "    join account ac on st.user_id = ac.id\n" +
                "where class_id = '%s' and st.id not in (select id\n" +
                "from student\n" +
                "    join class_attendance ca on student.id = ca.student_id\n" +
                "where ca.lesson_id = '%s')", classID, lessonID));
        ObservableList<Student> students = FXCollections.observableArrayList();
        while (resultSet.next()) {
            students.add(new Student(resultSet.getString("id"),
                    resultSet.getString("display_name")));
        }
        return students;
    }

    public static ObservableList<Student> presentStudent(String lessonID) throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet resultSet = db.select(String.format("select *\n" +
                "from student\n" +
                "    join class_attendance ca on student.id = ca.student_id\n" +
                "    join account ac on student.user_id = ac.id\n" +
                "where ca.lesson_id = '%s'", lessonID));
        ObservableList<Student> students = FXCollections.observableArrayList();
        while (resultSet.next()) {
            students.add(new Student(resultSet.getString("id"),
                    resultSet.getString("display_name")));
        }
        return students;
    }

    public static void RollCall(String studentID, String lessonID) throws SQLException {
        DBConnection db = new DBConnection();
        db.insert(String.format("insert into class_attendance(student_id, lesson_id) values ('%s', '%s')", studentID, lessonID));
    }

    public static void removeRollCall(String studentID, String lessonID) throws SQLException {
        DBConnection db = new DBConnection();
        db.delete(String.format("delete from class_attendance where student_id = '%s' and lesson_id = '%s'", studentID, lessonID));
    }

    public static void enrollToClass(String id, String studentID, String classID) throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select * from student where user_id = '%s' and class_id = '%s'", studentID, classID));
        if (result.next()) {
            throw new SQLException("Student already enrolled in this class");
        }
        result = db.select(String.format("select * from student join classes cl on student.class_id = cl.id where student.user_id = '%s' and cl.session_time = (select session_time from classes where id = '%s') and cl.session_day = (select session_day from classes where id = '%s') and cl.date_end >= (select date_start from classes where id = '%s')", studentID, classID, classID, classID));
        if (result.next()) {
            throw new SQLException("Student already enrolled in another class at the same time");
        }
        db.insert(String.format("insert into student values ('%s', '%s', '%s')", id, studentID, classID));
    }
}
