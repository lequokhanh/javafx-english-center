package com.controller;

import com.service.CourseService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.sql.SQLException;

public class GeneralController {
    public Group a;
    @FXML
    private Label stu_number;

    @FXML
    private Label class_number;
    @FXML
    private Label course_number;
    @FXML
    private Label room_number;

    @FXML
    private void initialize() throws SQLException {
        numberCountingUp(stu_number, 1234);
        numberCountingUp(class_number, 12);
        numberCountingUp(course_number, CourseService.getNumberOfCourse());
        numberCountingUp(room_number, 10);
    }

    private void numberCountingUp(Label label, int end) {
        label.setText(Integer.toString(0));
        Timeline numberCountingUp = new Timeline(new KeyFrame(Duration.millis(1000 / (float) (end)), event -> {
            label.setText(String.valueOf(Integer.parseInt(label.getText()) + 1));
        }));
        numberCountingUp.setCycleCount(end);
        numberCountingUp.play();
    }
}
