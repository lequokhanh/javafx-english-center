package com.controller;

import com.App;
import com.models.Class;
import com.models.User;
import com.service.AccountService;
import com.service.ClassService;
import com.service.StudentService;
import com.utilities.Constants;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class EnrollUser {
    public AnchorPane editPopUp;
    public Button submit;
    public Label id;
    public ComboBox<Class> classes;
    public Label userID;
    public Label displayName;

    public void initialize() throws SQLException, IOException {
        ObservableList<Class> classes = ClassService.getAllClassesNotFinished();
        this.classes.setItems(classes);
    }

    public static void show(User user) throws SQLException, IOException {
        FXMLLoader fxml = new FXMLLoader(
                Objects.requireNonNull(EnrollUser.class.getResource(Constants.FXML_ENROLL_USER)));
        Parent edit = fxml.load();
        EnrollUser controller = fxml.getController();
        controller.id.setText(AccountService.getNewStudentId());
        controller.userID.setText(user.getId());
        controller.displayName.setText(user.getDisplayName());
        controller.submit.setOnAction(e -> {
            try {
                if (controller.classes.getValue() == null) {
                    ErrorController.show("Please fill all the fields");
                    return;
                }
                StudentService.enrollToClass(controller.id.getText(), user.getId(),
                        controller.classes.getValue().getId());
                controller.close();
                SuccessfulController.show();
            } catch (SQLException | IOException ex) {
                try {
                    ErrorController.show(ex.getMessage());
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        });
        App.addPopup(edit);
    }

    public void close() {
        editPopUp.getScene().getWindow().hide();
    }
}
