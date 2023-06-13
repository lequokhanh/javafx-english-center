
package com.controller;


import com.App;
import com.service.AccountService;
import javafx.fxml.FXML;
import com.utilities.*;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController {

    public TextField username;
    public PasswordField password;

    @FXML
    private void login() throws IOException {
        try {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\-._]+$");
            Matcher userValidate = pattern.matcher(username.getText());
            Matcher passValidate = pattern.matcher(password.getText());
            if (!userValidate.matches()) {
                ErrorController.show(
                        "Username contains only letters, numbers, hyphens, periods and underscores");
                return;
            }
            if (!passValidate.matches()) {
                ErrorController.show(
                        "Password contains only letters, numbers, hyphens, periods and underscores");
                return;
            }
            String result = AccountService.checkLogin(username.getText(), password.getText());
            if (result != null) {
                Manager.setAuth(result);
                App.setRoot(Constants.FXML_HOMEPAGE, 1616, 939);
            } else {
                ErrorController.show("Wrong username or password");
            }
        } catch (SQLException e) {
            ErrorController.show(e.getMessage());
        }
    }

    public void onEnter() throws IOException {
        try {
            login();
        } catch (IOException e) {
            ErrorController.show(e.getMessage());
        }
    }
}
