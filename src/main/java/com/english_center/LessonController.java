package com.english_center;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class LessonController {

    public Class classes;
    public GridPane gridPane;
    public Label className;
    public Label courseName;
    public AnchorPane lessonPane;

    public void initialize() throws IOException {
        Platform.runLater(() -> {
            className.setText(classes.getName());
            courseName.setText(classes.getCourse());
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
                    description = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/DescriptionPopUp.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ((Label) ((AnchorPane) description.lookup("#anchor")).lookup("#description")).setText(lesson.getDescription());
                Node finalDescription = description;
                cardPane.setOnMouseMoved(e -> {
                    homepage.getChildren().removeAll(finalDescription);
                    finalDescription.setId("description");
                    finalDescription.setLayoutX(e.getSceneX() - e.getX() + 200);
                    finalDescription.setLayoutY(e.getSceneY() - e.getY() - 175);
                    homepage.getChildren().add(finalDescription);
                });
                cardPane.setOnMouseExited(e -> {
                    homepage.getChildren().removeAll(finalDescription);
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
        });
    }

    public void backToCourseBtn() throws IOException {
//        AnchorPane homepage = (AnchorPane) chapterPane.getParent();
//        homepage.getChildren().remove(homepage.lookup("#tabpane"));
//        Node general = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_COURSES)));
//        general.setId("tabpane");
//        general.getStyleClass().add("tabpane");
//        homepage.getChildren().add(general);
//        general.setLayoutX(345);
//        general.setLayoutY(20);
//        general.toFront();
    }

    public void add(ActionEvent actionEvent) {
    }
}
