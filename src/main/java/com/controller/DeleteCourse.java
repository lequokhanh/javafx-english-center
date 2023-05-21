package com.controller;

import com.App;
import com.models.Course;
import com.utilities.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

public class DeleteCourse {
    Consumer<Course> handle;

    public static void show() throws IOException {
        Parent editCourse = FXMLLoader.load(Objects.requireNonNull(EditCourseController.class.getResource(Constants.FXML_DELETE)));
        App.addPopup(editCourse);
    }
}
