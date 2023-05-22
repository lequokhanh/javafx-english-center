package com.controller;

import com.models.Chapter;
import com.models.Course;
import com.utilities.Constants;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class ChapterController {
    public AnchorPane chapterPane;
    public TableColumn<Chapter, String> chapterID;
    public TableColumn<Chapter, String> chapterName;
    public TableColumn<Chapter, String> description;
    public TableColumn<Chapter, String> category;
    public TableColumn<Chapter, VBox> action;
    public Course course;
    public TableView<Chapter> ChapterTable;
    public Label courseName;
    public Label chapterNumber;
    public Label level;
    public Label courseDescription;

    public void initialize() {
        chapterID.setPrefWidth(200);
        chapterName.setPrefWidth(230);
        description.setPrefWidth(390);
        category.setPrefWidth(200);
        action.setPrefWidth(100);
        ChapterTable.getColumns().forEach(e -> {
            e.setReorderable(false);
            e.setResizable(false);
        });
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
                case "Advance":
                    level.getStyleClass().add("levelAdvance");
                    break;
            }
            courseDescription.setText(course.getCourseDescription());
            try {
                reload();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public void reload() throws IOException {
        ObservableList<Chapter> chapters = FXCollections.observableArrayList(
                new Chapter("#20462", "Introduce to Toeic", "Test of English for International Communication", "Beginner"),
                new Chapter("#41569", "Intermediate Ielts", "International English Language Testing System", "Intermediate"),
                new Chapter("#69321", "Toeic", "Test of English for International Communication", "Advance")
        );
        ChapterTable.setItems(chapters);
    }

    public void backToCourseBtn() throws IOException {
        AnchorPane homepage = (AnchorPane) chapterPane.getParent();
        homepage.getChildren().remove(homepage.lookup("#tabpane"));
        Node general = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_COURSES)));
        general.setId("tabpane");
        general.getStyleClass().add("tabpane");
        homepage.getChildren().add(general);
        general.setLayoutX(345);
        general.setLayoutY(20);
        general.toFront();
    }

    public void add(ActionEvent actionEvent) {
    }
}
