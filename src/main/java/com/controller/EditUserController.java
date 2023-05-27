package com.controller;

import com.App;
import com.models.User;
import com.service.AccountService;
import com.utilities.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class EditUserController {
    public AnchorPane editPopUp;
    public Label userID;
    public TextField username;
    public Button submit;
    public TextField password;
    public TextField fullname;
    public ComboBox<String> role;

    public static void show(User user, UserController handle) throws SQLException, IOException {
        FXMLLoader fxml = new FXMLLoader(EditUserController.class.getResource(Constants.FXML_EDIT_USER));
        Parent edit = fxml.load();
        EditUserController controller = fxml.getController();
        if (user == null) {
            controller.userID.setText(AccountService.getNewId());
            controller.submit.setOnAction(e -> {
                try {
                    if (controller.username.getText().isEmpty() || controller.password.getText().isEmpty()
                            || controller.fullname.getText().isEmpty() || controller.role.getValue() == null) {
                        ErrorController.show("Please fill all the fields");
                        return;
                    }
                    AccountService.Insert(controller.userID.getText(), controller.username.getText(),
                            controller.password.getText(), controller.fullname.getText(), controller.role.getValue());
                    handle.search("");
                    controller.close();
                } catch (SQLException | IOException ex) {
                    try {
                        ErrorController.show(ex.getMessage());
                    } catch (IOException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            });
        } else {
            controller.userID.setText(user.getId());
            controller.username.setText(user.getName());
            controller.password.setText(user.getPassword());
            controller.fullname.setText(user.getDisplayName());
            controller.role.setValue(user.getRole());
            controller.submit.setOnAction(e -> {
                try {
                    if (controller.username.getText().isEmpty() || controller.password.getText().isEmpty()
                            || controller.fullname.getText().isEmpty() || controller.role.getValue() == null) {
                        ErrorController.show("Please fill all the fields");
                        return;
                    }
                    AccountService.Update(controller.userID.getText(), controller.username.getText(),
                            controller.password.getText(), controller.fullname.getText(), controller.role.getValue());
                    handle.search("");
                    controller.close();
                } catch (SQLException | IOException ex) {
                    try {
                        ErrorController.show(ex.getMessage());
                    } catch (IOException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            });
        }
        App.addPopup(edit);
    }

    public void close() {
        editPopUp.getScene().getWindow().hide();
    }
}
