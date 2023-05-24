package com.service;

import com.utilities.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
