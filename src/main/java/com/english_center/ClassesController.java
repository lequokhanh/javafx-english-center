package com.english_center;

import com.utilities.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class ClassesController {
    public TableView<Class> classTable;
    public TableColumn<Class, String> classID;
    public TableColumn<Class, String> className;
    public TableColumn<Class, String> courseName;
    public TableColumn<Class, String> teacher;
    public TableColumn<Class, String> start;
    public TableColumn<Class, String> end;
    public TableColumn<Class, String> action;
    public HBox manageLessonBtn;
    public AnchorPane classPane;

    public void initialize() throws IOException {
        classID.setPrefWidth(120);
        className.setPrefWidth(120);
        courseName.setPrefWidth(290);
        teacher.setPrefWidth(180);
        start.setPrefWidth(155);
        end.setPrefWidth(155);
        action.setPrefWidth(100);
        classTable.getColumns().forEach(e -> {
            e.setReorderable(false);
            e.setResizable(false);
        });
        ObservableList<Class> classes = FXCollections.observableArrayList(
                new Class("1", "Class 1", "Course 1", "Teacher 1", "1/1/2021", "1/1/2021"),
                new Class("2", "Class 2", "Course 2", "Teacher 2", "1/1/2021", "1/1/2021"),
                new Class("3", "Class 3", "Course 3", "Teacher 3", "1/1/2021", "1/1/2021")
        );
        classTable.setItems(classes);

    }

    public void add() {

    }

    public void manageBtn(MouseEvent mouseEvent) {
        if (classTable.getSelectionModel().getSelectedItem() != null) {
            if (!manageLessonBtn.isVisible()) {
                manageLessonBtn.setVisible(true);
            }
        }
    }

    public void manageLessons(MouseEvent mouseEvent) throws IOException {
        AnchorPane homepage = (AnchorPane) classPane.getParent();
        homepage.getChildren().remove(homepage.lookup("#tabpane"));
        FXMLLoader fxml = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Constants.FXML_LESSON)));
        Node lesson = fxml.load();
        LessonController controller = fxml.getController();
        controller.classes = classTable.getSelectionModel().getSelectedItem();
        lesson.setId("tabpane");
        lesson.getStyleClass().add("tabpane");
        homepage.getChildren().add(lesson);
        lesson.setLayoutX(345);
        lesson.setLayoutY(20);
        lesson.toFront();
    }
}
