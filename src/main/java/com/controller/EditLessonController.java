package com.controller;

import com.App;
import com.models.Chapter;
import com.models.Class;
import com.models.Lesson;
import com.service.ChapterService;
import com.service.LessonService;
import com.utilities.Constants;
import com.utilities.DateFormat;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class EditLessonController {
    public Button submit;
    public DatePicker learnDate;
    public ComboBox<Chapter> chapter;
    public Label lessonID;
    public AnchorPane editPopUp;

    public void initialize() throws IOException {
        learnDate.setConverter(new DateFormat());
    }

    public static void show(Lesson lesson, Class clas, LessonController handle) throws IOException, SQLException {
        DateFormat df = new DateFormat();
        FXMLLoader fxml = new FXMLLoader(Objects.requireNonNull(EditClassController.class.getResource(Constants.FXML_EDIT_LESSON)));
        Parent editLesson = fxml.load();
        EditLessonController controller = fxml.getController();
        ObservableList<Chapter> chapters = ChapterService.search(clas.getCourse().getCourseID(), "");
        controller.chapter.setItems(chapters);
        if (lesson == null) {
            try {
                controller.lessonID.setText(LessonService.getNewId());
                controller.submit.setOnAction(e -> {
                    try {
                        LessonService.Insert(clas.getId(), controller.lessonID.getText(), controller.chapter.getValue().getChapterID(), df.toString(controller.learnDate.getValue()));
                        handle.search("");
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
            } catch (SQLException e) {
                ErrorController.show(e.getMessage());
                return;
            }
        } else {
            controller.setDataToEdit(lesson);
            controller.submit.setOnAction(e -> {
                try {
                    LessonService.Update(lesson.getId(), controller.chapter.getValue().getChapterID(), df.toString(controller.learnDate.getValue()));
                    handle.search("");
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
        }
        App.addPopup(editLesson);
    }

    public void setDataToEdit(Lesson lesson) {
        DateFormat df = new DateFormat();
        lessonID.setText(lesson.getId());
        learnDate.setValue(df.fromString(lesson.getDate()));
        chapter.setValue(lesson.getChapter());
    }

    public void close() {
        editPopUp.getScene().getWindow().hide();
    }
}
