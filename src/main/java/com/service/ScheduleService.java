package com.service;

import com.models.Room;
import com.models.Schedule;
import com.utilities.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ScheduleService {
    private static Schedule addToSchedule(ResultSet session1, ResultSet session2, ResultSet session3, ResultSet session4) throws SQLException {
        List<String> session1List = Arrays.asList("", "", "", "", "", "", "");
        List<String> session2List = Arrays.asList("", "", "", "", "", "", "");
        List<String> session3List = Arrays.asList("", "", "", "", "", "", "");
        List<String> session4List = Arrays.asList("", "", "", "", "", "", "");
        while (session1.next()) {
            if (session1.getString("session_day").equals("Tue - Thu - Sat"))
                for (int i = 1; i < 6; i += 2)
                    session1List.set(i, session1.getString("name"));
            else
                for (int i = 0; i < 6; i += 2)
                    session1List.set(i, session1.getString("name"));
        }
        while (session2.next()) {
            if (session2.getString("session_day").equals("Tue - Thu - Sat"))
                for (int i = 1; i < 6; i += 2)
                    session2List.set(i, session2.getString("name"));
            else
                for (int i = 0; i < 6; i += 2)
                    session2List.set(i, session2.getString("name"));
        }
        while (session3.next()) {
            if (session3.getString("session_day").equals("Tue - Thu - Sat"))
                for (int i = 1; i < 6; i += 2)
                    session3List.set(i, session3.getString("name"));
            else
                for (int i = 0; i < 6; i += 2)
                    session3List.set(i, session3.getString("name"));
        }
        while (session4.next()) {
            if (session4.getString("session_day").equals("Tue - Thu - Sat"))
                for (int i = 1; i < 6; i += 2)
                    session4List.set(i, session4.getString("name"));
            else
                for (int i = 0; i < 6; i += 2)
                    session4List.set(i, session4.getString("name"));
        }
        return new Schedule(session1List, session2List, session3List, session4List);
    }

    public static Schedule getScheduleWithRoom(String id) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        ResultSet session1 = dbConnection.select("SELECT * FROM classes WHERE room_id = '" + id + "' AND session_time = 'Session 1 (7am->9am)'");
        ResultSet session2 = dbConnection.select("SELECT * FROM classes WHERE room_id = '" + id + "' AND session_time = 'Session 2 (9am->11am)'");
        ResultSet session3 = dbConnection.select("SELECT * FROM classes WHERE room_id = '" + id + "' AND session_time = 'Session 3 (1pm->3pm)'");
        ResultSet session4 = dbConnection.select("SELECT * FROM classes WHERE room_id = '" + id + "' AND session_time = 'Session 4 (3pm->5pm)'");
        return addToSchedule(session1, session2, session3, session4);
    }

    public static Schedule getScheduleWithStudent(String id) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        ResultSet session1 = dbConnection.select("SELECT * FROM classes join student on classes.id = student.class_id WHERE student.user_id = '" + id + "' AND session_time = 'Session 1 (7am->9am)'");
        ResultSet session2 = dbConnection.select("SELECT * FROM classes join student on classes.id = student.class_id WHERE student.user_id = '" + id + "' AND session_time = 'Session 2 (9am->11am)'");
        ResultSet session3 = dbConnection.select("SELECT * FROM classes join student on classes.id = student.class_id WHERE student.user_id = '" + id + "' AND session_time = 'Session 3 (1pm->3pm)'");
        ResultSet session4 = dbConnection.select("SELECT * FROM classes join student on classes.id = student.class_id WHERE student.user_id = '" + id + "' AND session_time = 'Session 4 (3pm->5pm)'");
        return addToSchedule(session1, session2, session3, session4);
    }

    public static Schedule getScheduleWithTeacher(String id) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        ResultSet session1 = dbConnection.select("SELECT * FROM classes WHERE teacher_id = '" + id + "' AND session_time = 'Session 1 (7am->9am)'");
        ResultSet session2 = dbConnection.select("SELECT * FROM classes WHERE teacher_id = '" + id + "' AND session_time = 'Session 2 (9am->11am)'");
        ResultSet session3 = dbConnection.select("SELECT * FROM classes WHERE teacher_id = '" + id + "' AND session_time = 'Session 3 (1pm->3pm)'");
        ResultSet session4 = dbConnection.select("SELECT * FROM classes WHERE teacher_id = '" + id + "' AND session_time = 'Session 4 (3pm->5pm)'");
        return addToSchedule(session1, session2, session3, session4);
    }
}