package com.controller;

import com.App;
import com.utilities.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class EditChapterController {
    public AnchorPane editPopUp;

    public void initialize() {
    }

    public static void show() throws IOException {
        Parent editCourse = FXMLLoader.load(Objects.requireNonNull(EditCourseController.class.getResource(Constants.FXML_EDIT_CHAPTER)));
        App.addPopup(editCourse);
    }

    public void submit(ActionEvent actionEvent) {
        
    }

    public void close(ActionEvent actionEvent) {
        editPopUp.getScene().getWindow().hide();

    }
}
