package com.controller;

import com.models.Class;
import com.models.Point;
import com.service.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

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
    public MenuButton menuButton;

    @FXML
    private void initialize() throws SQLException, IOException {
        numberCountingUp(stu_number, StudentService.getNumberOfStudent());
        numberCountingUp(class_number, ClassService.getNumberOfClasses());
        numberCountingUp(course_number, CourseService.getNumberOfCourse());
        numberCountingUp(room_number, RoomService.getNumberOfRoom());
        ObservableList<Class> classes = ClassService.search("");
        this.classes.setItems(classes);
        this.classes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            lineChart.getData().clear();
            try {
                attendanceChart(newValue.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        attendanceChart.setOnAction(e -> {
            if (menuButton.getText().equals(attendanceChart.getText()))
                return;
            this.classes.getSelectionModel().selectFirst();
            lineChart.getData().clear();
            classGroup.setVisible(true);
            menuButton.setText(attendanceChart.getText());
            try {
                attendanceChart(classes.get(0).getId());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        courseChart.setOnAction(e -> {
            if (menuButton.getText().equals(courseChart.getText()))
                return;
            lineChart.getData().clear();
            classGroup.setVisible(false);
            menuButton.setText(courseChart.getText());
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
        generateChart(LessonService.getLessonPresentStudent(classId));
        CategoryAxis xAxis = (CategoryAxis) lineChart.getXAxis();
        Tooltip tooltip = new Tooltip();
        xAxis.setOnMouseMoved(event -> {
            int index = (int) (event.getX() / xAxis.getWidth() * xAxis.getCategories().size());
            tooltip.setText(xAxis.getCategories().get(index));
            tooltip.show(xAxis, event.getScreenX() + 10, event.getScreenY() + 10);
        });
        xAxis.setOnMouseExited(event -> tooltip.hide());
    }

    private void courseChart() throws SQLException {
        ObservableList<Point> points1 = CourseService.getCoursePopulation();
        ObservableList<Point> points2 = points1.stream()
                .map(point -> new Point(point.getX(), point.getY()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        points2.forEach(point -> {
            String courseName = Arrays.stream(point.getX().toUpperCase().split(" "))
                    .map(word -> word.substring(0, 1))
                    .collect(Collectors.joining());
            point.setX(courseName);
        });
        generateChart(points2);
        CategoryAxis xAxis = (CategoryAxis) lineChart.getXAxis();
        Tooltip tooltip = new Tooltip();
        xAxis.setOnMouseMoved(event -> {
            int index = (int) (event.getX() / xAxis.getWidth() * xAxis.getCategories().size());
            tooltip.setText(points1.get(index).getX());
            tooltip.show(xAxis, event.getScreenX() + 10, event.getScreenY() + 10);
        });
        xAxis.setOnMouseExited(event -> tooltip.hide());
    }

    public void generateChart(ObservableList<Point> points) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        int max = points.stream().map(Point::getY).max(Integer::compareTo).orElse(0);
        for (Point point : points) {
            series.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
        }
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();
        yAxis.setAutoRanging(false);
        int tickUnit = max / 10;
        yAxis.setUpperBound(Math.max(tickUnit * 12, 1));
        yAxis.setTickUnit(tickUnit);
        lineChart.getData().add(series);
        CategoryAxis xAxis = (CategoryAxis) lineChart.getXAxis();
        ObservableList<String> categories = FXCollections.observableArrayList();
        for (Point point : points) {
            categories.add(point.getX());
        }
        xAxis.getCategories().clear();
        xAxis.setCategories(categories);
        for (final XYChart.Series<String, Number> series1 : lineChart.getData()) {
            for (final XYChart.Data<String, Number> data : series1.getData()) {
                Node node = data.getNode();
                Tooltip tooltip = new Tooltip(data.getYValue().toString());
                node.setOnMouseMoved(event -> tooltip.show(node, event.getScreenX() + 10, event.getScreenY() + 10));
                node.setOnMouseExited(event -> tooltip.hide());
            }
        }
    }
}
