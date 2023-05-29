package com.controller;

import com.models.Class;
import com.models.Point;
import com.service.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class GeneralController {
    public LineChart<String, Number> lineChart;
    public MenuItem courseChart;
    public ComboBox<Class> classes;
    public MenuItem attendanceChart;
    public Label stu_number;
    public Label class_number;
    public Label course_number;
    public Label room_number;
    public VBox classGroup;

    @FXML
    private void initialize() throws SQLException, IOException {
        numberCountingUp(stu_number, StudentService.getNumberOfStudent());
        numberCountingUp(class_number, ClassService.getNumberOfClasses());
        numberCountingUp(course_number, CourseService.getNumberOfCourse());
        numberCountingUp(room_number, RoomService.getNumberOfRoom());
        ObservableList<Class> classes = ClassService.search("");
        this.classes.setItems(classes);
        this.classes.getSelectionModel().selectFirst();
        this.classes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            lineChart.getData().clear();
            try {
                attendanceChart(newValue.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        attendanceChart.setOnAction(e -> {
            lineChart.getData().clear();
            classGroup.setVisible(true);
            try {
                attendanceChart(classes.get(0).getId());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        courseChart.setOnAction(e -> {
            lineChart.getData().clear();
            classGroup.setVisible(false);
            try {
                courseChart();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void numberCountingUp(Label label, int end) {
        label.setText(Integer.toString(0));
        Timeline numberCountingUp = new Timeline(new KeyFrame(Duration.millis(750 / (float) (end)), event -> {
            label.setText(String.valueOf(Integer.parseInt(label.getText()) + 1));
        }));
        numberCountingUp.setCycleCount(end);
        numberCountingUp.play();
    }

    private void attendanceChart(String classId) throws SQLException {
        ObservableList<Point> points = LessonService.getLessonPresentStudent(classId);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Point point : points) {
            series.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
        }
        lineChart.getData().add(series);
        CategoryAxis xAxis = (CategoryAxis) lineChart.getXAxis();
        ObservableList<String> categories = FXCollections.observableArrayList();
        for (Point point : points) {
            categories.add(point.getX());
        }
        xAxis.getCategories().clear();
        xAxis.setCategories(categories);
    }

    private void courseChart() throws SQLException {
        ObservableList<Point> points = ClassService.getClassPopulation();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Point point : points) {
            series.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
        }
        lineChart.getData().add(series);
        CategoryAxis xAxis = (CategoryAxis) lineChart.getXAxis();
        ObservableList<String> categories = FXCollections.observableArrayList();
        for (Point point : points) {
            categories.add(point.getX());
        }
        xAxis.getCategories().clear();
        xAxis.setCategories(categories);
    }
}
