package com.controller;

import com.models.Class;
import com.models.Lesson;
import com.models.Material;
import com.models.Student;
import com.utilities.Constants;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.util.Objects;

import static java.lang.Math.max;

public class LessonController {

    public Class classes;
    public GridPane gridPane;
    public Label className;
    public Label courseName;
    public AnchorPane lessonPane;
    public Lesson selectedLesson;
    public Group lessonTab;
    public AnchorPane materialPane;
    public Tab attendancePane;
    public GridPane materialGridPane;
    public GridPane absentGridPane;
    public GridPane presentGridPane;
    public Label absentNumber;
    public Label totalNumber;

    public void initialize() throws IOException {
        Platform.runLater(() -> {
            className.setText(classes.getName());
            courseName.setText(classes.getCourse());
            reload();
        });
    }

    public void backToCourseBtn() throws IOException {
        AnchorPane homepage = (AnchorPane) lessonPane.getParent();
        homepage.getChildren().remove(homepage.lookup("#tabpane"));
        Node general = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_CLASSES)));
        general.setId("tabpane");
        general.getStyleClass().add("tabpane");
        homepage.getChildren().add(general);
        general.setLayoutX(345);
        general.setLayoutY(20);
        general.toFront();
    }

    public void reload() {
        ObservableList<Lesson> lessons = FXCollections.observableArrayList(
                new Lesson("Introduce to Toeic", "Reading", "Test of English for International Communication", "12/12/2020"),
                new Lesson("Intermediate Ielts", "Intermediate", "International English Language Testing System", "12/12/2020"),
                new Lesson("Introduce to Toeic", "Reading", "Test of English for International Communication", "12/12/2020"),
                new Lesson("Intermediate Ielts", "Intermediate", "International English Language Testing System", "12/12/2020"),
                new Lesson("Introduce to Toeic", "Reading", "Test of English for International Communication", "12/12/2020"),
                new Lesson("Intermediate Ielts", "Intermediate", "International English Language Testing System", "12/12/2020"),
                new Lesson("Introduce to Toeic", "Reading", "Test of English for International Communication", "12/12/2020"),
                new Lesson("Intermediate Ielts", "Intermediate", "International English Language Testing System", "12/12/2020"),
                new Lesson("Introduce to Toeic", "Reading", "Test of English for International Communication", "12/12/2020"),
                new Lesson("Intermediate Ielts", "Intermediate", "International English Language Testing System", "12/12/2020"),
                new Lesson("Introduce to Toeic", "Reading", "Test of English for International Communication", "12/12/2020"),
                new Lesson("Intermediate Ielts", "Intermediate", "International English Language Testing System", "12/12/2020")
        );
        int row = lessons.size();
        gridPane.getChildren().clear();
        for (int i = 0; i < row; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(67);
            gridPane.getRowConstraints().add(rowConst);
        }
        for (int i = 0; i < row; i++) {
            Lesson lesson = lessons.get(i);
            Node lessonCard = null;
            try {
                lessonCard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_LESSON_CARD)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            AnchorPane cardPane = (AnchorPane) lessonCard.lookup(".cardPane");
            AnchorPane homepage = (AnchorPane) lessonPane.getParent();
            Node description = null;
            try {
                description = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_DESCRIPTION_POP_UP)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ((Label) ((AnchorPane) description.lookup("#anchor")).lookup("#description")).setText(lesson.getDescription());
            Node finalDescription = description;
            cardPane.setOnMouseMoved(e -> {
                homepage.getChildren().removeAll(finalDescription);
                finalDescription.setId("description");
                finalDescription.setLayoutX(e.getSceneX() - e.getX() + 200);
                finalDescription.setLayoutY(max(e.getSceneY() - e.getY() - 175, 69));
                homepage.getChildren().add(finalDescription);
            });
            cardPane.setOnMouseExited(e -> {
                homepage.getChildren().removeAll(finalDescription);
            });
            cardPane.setOnMouseClicked(e -> {
                selectedLesson = lesson;
                if (lessonPane.lookup(".emptyTab") != null) {
                    lessonPane.getChildren().remove(lessonPane.lookup(".emptyTab"));
                }
                lessonTab.setVisible(true);
                try {
                    reloadMaterialPane();
                    reloadAttendancePane();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            ((Label) cardPane.lookup("#name")).setText(lesson.getName());
            ((Label) cardPane.lookup("#category")).setText(lesson.getCategory());
            switch (lesson.getCategory()) {
                case "Reading":
                    ((Label) cardPane.lookup("#category")).getStyleClass().add("reading");
                    break;
                case "Listening":
                    ((Label) cardPane.lookup("#category")).getStyleClass().add("listening");
                    break;
                case "Speaking":
                    ((Label) cardPane.lookup("#category")).getStyleClass().add("speaking");
                    break;
                case "Writing":
                    ((Label) cardPane.lookup("#category")).getStyleClass().add("writing");
                    break;
                default:
                    ((Label) cardPane.lookup("#category")).getStyleClass().add("other");
                    break;
            }
            ((Label) lessonCard.lookup("#date")).setText(lesson.getDate());
            gridPane.add(lessonCard, 0, i);
        }
    }

    public void reloadMaterialPane() throws IOException {
        ObservableList<Material> materials = FXCollections.observableArrayList(
                new Material("#2133", "C://Users//Admin//Desktop//Toeic//Reading//Reading 1.pdf"), new Material("#2133", "C://Users//Admin//Desktop//Toeic//Reading//Reading 1.pdf"), new Material("#2133", "C://Users//Admin//Desktop//Toeic//Reading//Reading 1.pdf"), new Material("#2133", "C://Users//Admin//Desktop//Toeic//Reading//Reading 1.pdf")
        );
        for (int i = 0; i < materials.size(); i++) {
            Node fileItem = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FILE_ITEM)));
            materialGridPane.add(fileItem, 0, i);
        }
    }


    public void reloadAttendancePane() throws IOException {
        ObservableList<Student> absentStudent = FXCollections.observableArrayList(
                new Student("1232", "Le asjlkdhasdjk"),
                new Student("1232", "Le asjlkdhasdjk"),
                new Student("1232", "Le asjlkdhasdjk")
        );
        ObservableList<Student> presentStudent = FXCollections.observableArrayList(
                new Student("1232", "Le asjlkdhasdjk"),
                new Student("1232", "Le asjlkdhasdjk"),
                new Student("1232", "Le asjlkdhasdjk")
        );
        absentNumber.setText(Integer.toString(absentStudent.size()));
        totalNumber.setText(Integer.toString(absentStudent.size() + presentStudent.size()));
        for (int i = 0; i < absentStudent.size(); i++) {
            Node studentItem = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_STUDENT_ITEM)));
            absentGridPane.add(studentItem, 0, i);
        }
        for (int i = 0; i < presentStudent.size(); i++) {
            Node studentItem = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_STUDENT_ITEM)));
            studentItem.lookup("#rollCallBtn").setVisible(false);
            studentItem.lookup("#removeBtn").setVisible(true);
            presentGridPane.add(studentItem, 0, i);
        }

    }

    public void add(ActionEvent actionEvent) {
    }
}
