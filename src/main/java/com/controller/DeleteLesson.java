package com.controller;

import com.App;
import com.service.LessonService;
import com.utilities.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class DeleteLesson {
    public static void show(String lessonID, LessonController handle) throws IOException {
        Parent delete = FXMLLoader.load(Objects.requireNonNull(EditCourseController.class.getResource(Constants.FXML_DELETE)));
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
                LessonService.Delete(lessonID);
                handle.search("");
                if (handle.lessonTab.isVisible()) {
                    handle.lessonTab.setVisible(false);
                }
                handle.lessonPane.lookup(".emptyTab").setVisible(true);
                App.removePopUp(delete);
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
