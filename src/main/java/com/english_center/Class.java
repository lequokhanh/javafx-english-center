package com.english_center;

import com.utilities.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class Class {
    private String id;
    private String name;
    private String course;
    private String teacher;
    private String start;
    private String end;
    private HBox action;

    public Class() {

    }

    public Class(String id, String name, String course, String teacher, String start, String end) throws IOException {
        this.id = id;
        this.name = name;
        this.course = course;
        this.teacher = teacher;
        this.start = start;
        this.end = end;
        Node action = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_ACTION)));
        action.setId("action");
        action.getStyleClass().add("action");
        this.action = (HBox) action;
        action.lookup("#edit").setOnMouseClicked(e -> {
            try {
                EditCourseController.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        action.lookup("#delete").setOnMouseClicked(e -> {
            try {
                DeleteCourse.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
}
