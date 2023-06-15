package com.service;

import com.models.Teacher;
import com.models.User;
import com.utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AccountService {
    public static String checkLogin(String account_name, String password) throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select * from account where account_name = '%s'", account_name));
        if (result.next()) {
            if (result.getString("password").equals(password)) {
                return result.getString("id") + "/" + result.getString("display_name") + "/" + result.getString("role");
            }
        }
        return null;
    }

    public static ObservableList<Teacher> getAllTeacher() throws SQLException {
        DBConnection db = new DBConnection();
        ResultSet result = db.select("select * from account where role = 'Teacher'");
        ObservableList<Teacher> teachers = FXCollections.observableArrayList();
        while (result.next()) {
            teachers.add(new Teacher(result.getString("id"), result.getString("display_name")));
        }
        return teachers;
    }

    public static ObservableList<User> search(String keyWord) throws SQLException, IOException {
        keyWord = keyWord.toUpperCase();
        DBConnection db = new DBConnection();
        ResultSet result = db.select("select * from account where upper(id) like '%%" + keyWord
                + "%%' or upper(account_name) like '%%" + keyWord + "%%' or upper(display_name) like '%%" + keyWord
                + "%%' or upper(role) like '%%" + keyWord + "%%' order by role asc");
        ObservableList<User> users = FXCollections.observableArrayList();
        while (result.next()) {
            users.add(new User(result.getString("id"), result.getString("account_name"), result.getString("password"),
                    result.getString("display_name"), result.getString("role")));
        }
        return users;
    }

    public static void Insert(String id, String account_name, String password, String display_name, String role)
            throws SQLException {
        DBConnection db = new DBConnection();
        db.insert(String.format("insert into account values ('%s', '%s', '%s', '%s', '%s')", id, account_name, password,
                role, display_name));
    }

    public static void Update(String id, String account_name, String password, String display_name, String role)
            throws SQLException {
        DBConnection db = new DBConnection();
        db.update(String.format(
                "update account set account_name = '%s', password = '%s', display_name = '%s', role = '%s' where id = '%s'",
                account_name, password, display_name, role, id));
    }

    public static void Delete(String id) throws SQLException {
        DBConnection db = new DBConnection();
        db.delete(String.format("delete from account where id = '%s'", id));
    }

    public static String getNewId() throws SQLException {
        String id = UUID.randomUUID().toString().substring(0, 8);
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select * from account where id = '%s'", id));
        if (result.next()) {
            return getNewId();
        }
        return id;
    }


    public static String getNewStudentId() throws SQLException {
        String id = UUID.randomUUID().toString().substring(0, 8);
        DBConnection db = new DBConnection();
        ResultSet result = db.select(String.format("select * from student where id = '%s'", id));
        if (result.next()) {
            return getNewStudentId();
        }
        return id;
    }

}
