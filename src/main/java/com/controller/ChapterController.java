package com.controller;

import com.models.Chapter;
import com.models.Course;
import com.service.ChapterService;
import com.utilities.Constants;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
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
                case "Advanced":
                    level.getStyleClass().add("levelAdvanced");
                    break;
            }
            courseDescription.setText(course.getCourseDescription());
            try {
                search("");
            } catch (IOException | SQLException e) {
                try {
                    ErrorController.show(e.getMessage());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void search(String keyWord) throws IOException, SQLException {
        ObservableList<Chapter> chapters = ChapterService.search(course.getCourseID(), keyWord);
        for (Chapter chapter : chapters) {
            HBox action = chapter.getAction();
            if (action == null) continue;
            action.lookup("#edit").setOnMouseClicked(e -> {
                try {
                    EditChapterController.show(chapter, "", this);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            action.lookup("#delete").setOnMouseClicked(e -> {
                try {
                    DeleteChapter.show(chapter.getChapterID(), this);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
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

    public void add(ActionEvent actionEvent) throws IOException {
        EditChapterController.show(null, course.getCourseID(), this);
    }
}
