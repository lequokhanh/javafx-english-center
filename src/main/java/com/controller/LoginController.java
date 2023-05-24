package com.controller;

import com.App;
import com.service.AccountService;
import javafx.event.ActionEvent;
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
            String result = AccountService.checkLogin(username.getText(), password.getText());
            if (result != null) {
                Manager.setAuth(result);
            } else {
                ErrorController.show("Wrong username or password");
            }
        } catch (SQLException e) {
            ErrorController.show(e.getMessage());
        }
        App.setRoot(Constants.FXML_HOMEPAGE, 1616, 939);
    }

    public void onEnter() throws IOException {
        try {
            login();
        } catch (IOException e) {
            ErrorController.show(e.getMessage());
        }
    }
}
