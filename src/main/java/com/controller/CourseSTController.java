package com.controller;

import com.models.Course;
import com.utilities.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.util.Objects;

public class CourseSTController {

    public GridPane gridPane;
    public AnchorPane coursePane;

    public void initialize() throws IOException {
        ObservableList<Course> courses = FXCollections.observableArrayList(
                new Course("#20462", "Introduce to Toeic", "Test of English for International Communication", "Beginner", 10),
                new Course("#41569", "Intermediate Ielts", "International English Language Testing System", "Intermediate", 9), new Course("#41569", "Intermediate Ielts", "International English Language Testing System", "Intermediate", 9), new Course("#41569", "Intermediate Ielts", "International English Language Testing System", "Intermediate", 9), new Course("#41569", "Intermediate Ielts", "International English Language Testing System", "Intermediate", 9), new Course("#41569", "Intermediate Ielts", "International English Language Testing System", "Intermediate", 9), new Course("#41569", "Intermediate Ielts", "International English Language Testing System", "Intermediate", 9), new Course("#41569", "Intermediate Ielts", "International English Language Testing System", "Intermediate", 9), new Course("#41569", "Intermediate Ielts", "International English Language Testing System", "Intermediate", 9), new Course("#41569", "Intermediate Ielts", "International English Language Testing System", "Intermediate", 9), new Course("#41569", "Intermediate Ielts", "International English Language Testing System", "Intermediate", 9), new Course("#41569", "Intermediate Ielts", "International English Language Testing System", "Intermediate", 9), new Course("#41569", "Intermediate Ielts", "International English Language Testing System", "Intermediate", 9),
                new Course("#69321", "Toeic", "Test of English for International Communication", "Advanced", 8)
        );
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            Node courseCard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/CourseCard.fxml")));
            courseCard.setOnMouseClicked(event -> {
                AnchorPane homepage = (AnchorPane) coursePane.getParent();
                homepage.getChildren().remove(homepage.lookup("#tabpane"));
                try {
                    FXMLLoader fxml = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Constants.FXML_CHAPTER_ST)));
                    Node chapter = fxml.load();
                    ChapterSTController controller = fxml.getController();
                    controller.course = course;
                    chapter.setId("tabpane");
                    chapter.getStyleClass().add("tabpane");
                    homepage.getChildren().add(chapter);
                    chapter.setLayoutX(345);
                    chapter.setLayoutY(20);
                    chapter.toFront();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            gridPane.add(courseCard, i % 4, i / 4);
            ((Label) courseCard.lookup(".cardCourseName")).setText(course.getCourseName());
            ((Label) courseCard.lookup(".cardCourseDescription")).setText(course.getCourseDescription());
            if (course.getNumberOfChapter() < 10)
                ((Label) courseCard.lookup(".cardChapterNumber")).setText("0" + course.getNumberOfChapter().toString());
            else
                ((Label) courseCard.lookup(".cardChapterNumber")).setText(course.getNumberOfChapter().toString());

            Label cardCourseLevel = (Label) courseCard.lookup(".courseLevel");
            cardCourseLevel.setText(course.getCourseLevel().getText());
            cardCourseLevel.getStyleClass().clear();
            cardCourseLevel.getStyleClass().add("courseLevel");
            switch (course.getCourseLevel().getText()) {
                case "Beginner":
                    cardCourseLevel.getStyleClass().add("levelBeginner");
                    break;
                case "Intermediate":
                    cardCourseLevel.getStyleClass().add("levelIntermediate");
                    break;
                case "Advanced":
                    cardCourseLevel.getStyleClass().add("levelAdvanced");
                    break;
            }
        }
    }
}
