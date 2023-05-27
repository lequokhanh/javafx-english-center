package com.models;

import com.utilities.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.*;

import java.io.IOException;
import java.util.Objects;

@Getter
@Setter
public class Course {
    private String courseID;
    private String courseName;
    private String courseDescription;
    private Label courseLevel;
    private Integer numberOfChapter;
    private HBox action;

    public Course() {
    }

    public Course(String courseID, String courseName) {
        this.courseID = courseID;
        this.courseName = courseName;
    }

    public Course(String courseID, String courseName, String courseDescription, String courseLevel,
            Integer numberOfChapter) throws IOException {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.numberOfChapter = numberOfChapter;
        this.courseLevel = new Label(courseLevel);
        this.courseLevel.getStyleClass().add("courseLevel");
        switch (courseLevel) {
            case "Beginner":
                this.courseLevel.getStyleClass().add("levelBeginner");
                break;
            case "Intermediate":
                this.courseLevel.getStyleClass().add("levelIntermediate");
                break;
            case "Advanced":
                this.courseLevel.getStyleClass().add("levelAdvanced");
                break;
        }
        Node action = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_ACTION)));
        action.setId("action");
        action.getStyleClass().add("action");
        this.action = (HBox) action;
    }

    public String toString() {
        return courseName;
    }
}
