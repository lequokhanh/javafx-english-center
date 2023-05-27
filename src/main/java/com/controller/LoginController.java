
package com.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.App;
import com.service.AccountService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.utilities.*;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    public TextField username;
    public PasswordField password;
    @FXML
    private Button btnLogin;

    @FXML
    private void login() throws IOException {
        try {
            Pattern pattern = Pattern.compile("\\b[a-zA-Z][a-zA-Z0-9\\-._]{3,}\\b");
            Matcher userValidate = pattern.matcher(username.getText());
            Matcher passValidate = pattern.matcher(password.getText());
            if (!userValidate.matches()) {
                ErrorController.show(
                        "Username must be at least 4 characters and contain only letters, numbers, hyphens, periods and underscores");
                return;
            }
            if (!passValidate.matches()) {
                ErrorController.show(
                        "Password must be at least 4 characters and contain only letters, numbers, hyphens, periods and underscores");
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
