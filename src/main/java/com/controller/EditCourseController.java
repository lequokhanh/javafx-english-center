package com.controller;

import com.App;
import com.models.Course;
import com.service.CourseService;
import com.utilities.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.NClob;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class EditCourseController {

    public AnchorPane editPopUp;
    public Course course = null;
    public Label courseID;
    public TextField courseName;
    public TextArea courseDescription;
    public ComboBox<String> courseLevel;
    public Button submit;

    public static void show(Course course, CoursesController handle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(EditCourseController.class.getResource(Constants.FXML_EDIT_COURSE)));
        Parent editCourse = fxmlLoader.load();
        EditCourseController controller = fxmlLoader.getController();
        if (course == null) {
            try {
                controller.courseID.setText(CourseService.getNewId());
                controller.submit.setOnAction(e -> {
                    try {
                        if (controller.courseName.getText().isEmpty() || controller.courseDescription.getText().isEmpty() || controller.courseLevel.getValue() == null) {
                            ErrorController.show("Please fill all the fields");
                            return;
                        }
                        CourseService.InsertNewCourse(controller.courseID.getText(), controller.courseName.getText(), controller.courseDescription.getText(), controller.courseLevel.getValue());
                        handle.search("");
                        controller.close();
                    } catch (IOException | SQLException ex) {
                        try {
                            ErrorController.show(ex.toString());
                        } catch (IOException exc) {
                            throw new RuntimeException(exc);
                        }
                    }
                });
            } catch (SQLException e) {
                ErrorController.show(e.toString());
                return;
            }
        } else {
            controller.setDataToEdit(course);
            controller.submit.setOnAction(e -> {
                try {
                    CourseService.UpdateCourse(controller.course.getCourseID(), controller.courseName.getText(), controller.courseDescription.getText(), controller.courseLevel.getValue());
                    handle.search("");
                    controller.close();
                } catch (IOException | SQLException ex) {
                    try {
                        ErrorController.show(ex.toString());
                    } catch (IOException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            });
        }
        App.addPopup(editCourse);
    }

    public void setDataToEdit(Course course) {
        this.course = course;
        courseID.setText(course.getCourseID());
        courseName.setText(course.getCourseName());
        courseDescription.setText(course.getCourseDescription());
        courseLevel.setValue(course.getCourseLevel().getText());
    }

    public void close() {
        editPopUp.getScene().getWindow().hide();
    }
}
