package com.controller;

import com.models.Chapter;
import com.models.Course;
import com.service.ChapterService;
import com.utilities.Constants;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ChapterSTController {
    public AnchorPane chapterPane;
    public Course course;
    public Label courseName;
    public Label chapterNumber;
    public Label level;
    public Label courseDescription;
    public GridPane gridPane;
    public TextField searchField;

    public void initialize() {
        Platform.runLater(() -> {
            courseName.setText(course.getCourseName());
            chapterNumber.setText(course.getNumberOfChapter() + " chapters");
            level.setText(course.getCourseLevel().getText());
            switch (course.getCourseLevel().getText()) {
                case "Beginner":
                    level.getStyleClass().add("levelBeginner");
                    break;
                case "Intermediate":
                    level.getStyleClass().add("levelIntermediate");
                    break;
                case "Advanced":
                    level.getStyleClass().add("levelAdvanced");
                    break;
            }
            courseDescription.setText(course.getCourseDescription());
            try {
                reload("");
            } catch (IOException | SQLException e) {
                try {
                    ErrorController.show(e.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void reload(String keyWord) throws IOException, SQLException {
        ObservableList<Chapter> chapters = ChapterService.search(course.getCourseID(), "");
        for (int i = 0; i < chapters.size(); i++) {
            Chapter chapter = chapters.get(i);
            Node chapterCard = FXMLLoader
                    .load(Objects.requireNonNull(getClass().getResource(Constants.FXML_CHAPTER_CARD)));
            ((Label) chapterCard.lookup(".nameChapter")).setText(chapter.getChapterName());
            ((Label) chapterCard.lookup(".descriptionChapter")).setText(chapter.getChapterDescription());
            ((Label) chapterCard.lookup(".categoryChapter")).setText(chapter.getCategory());
            switch (chapter.getCategory()) {
                case "Reading":
                    ((Label) chapterCard.lookup(".categoryChapter")).getStyleClass().add("reading");
                    break;
                case "Listening":
                    ((Label) chapterCard.lookup(".categoryChapter")).getStyleClass().add("listening");
                    break;
                case "Speaking":
                    ((Label) chapterCard.lookup(".categoryChapter")).getStyleClass().add("speaking");
                    break;
                case "Writing":
                    ((Label) chapterCard.lookup(".categoryChapter")).getStyleClass().add("writing");
                    break;
                default:
                    ((Label) chapterCard.lookup(".categoryChapter")).getStyleClass().add("other");
                    break;
            }
            gridPane.add(chapterCard, 0, i);
        }
    }

    public void backToCourseBtn(MouseEvent mouseEvent) throws IOException {
        AnchorPane homepage = (AnchorPane) chapterPane.getParent();
        homepage.getChildren().remove(homepage.lookup("#tabpane"));
        Node general = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_COURSES_ST)));
        general.setId("tabpane");
        general.getStyleClass().add("tabpane");
        homepage.getChildren().add(general);
        general.setLayoutX(345);
        general.setLayoutY(20);
        general.toFront();
    }

    public void search(KeyEvent keyEvent) throws SQLException, IOException {
        reload(searchField.getText());
    }
}
