
package com.controller;


import com.App;
import com.service.AccountService;
import javafx.fxml.FXML;
import com.utilities.*;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    public TextField username;
    public PasswordField password;

    @FXML
    private void login() throws IOException {
        try {
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
