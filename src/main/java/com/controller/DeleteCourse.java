package com.controller;

import com.App;
import com.service.CourseService;
import com.utilities.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class DeleteCourse {
    public static void show(Integer numberOfChapters, String courseID, CoursesController handle) throws IOException {
        Parent delete = FXMLLoader
                .load(Objects.requireNonNull(EditCourseController.class.getResource(Constants.FXML_DELETE)));
        App.addPopup(delete);
        delete.lookup("#cancel").setOnMouseClicked(e -> {
            try {
                App.removePopUp(delete);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        delete.lookup("#confirm").setOnMouseClicked(e -> {
            try {
                if (numberOfChapters > 0) {
                    App.removePopUp(delete);
                    ErrorController.show("Please delete all the chapters first");
                    return;
                }
                CourseService.DeleteCourse(courseID);
                App.removePopUp(delete);
                handle.search("");
                SuccessfulController.show();
            } catch (IOException | SQLException ex) {
                try {
                    ErrorController.show(ex.toString());
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        });
    }
}
