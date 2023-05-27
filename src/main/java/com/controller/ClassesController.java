package com.controller;

import com.models.Class;
import com.service.ClassService;
import com.utilities.Constants;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;
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
    public TableColumn<Class, String> room;

    public void initialize() throws IOException, SQLException {
        classID.setPrefWidth(120);
        className.setPrefWidth(120);
        courseName.setPrefWidth(290);
        teacher.setPrefWidth(180);
        room.setPrefWidth(90);
        start.setPrefWidth(110);
        end.setPrefWidth(110);
        action.setPrefWidth(100);
        classTable.getColumns().forEach(e -> {
            e.setReorderable(false);
            e.setResizable(false);
        });
        search("");
    }

    public void search(String keyWord) throws IOException, SQLException {
        ObservableList<Class> classes = ClassService.search(keyWord);
        for (Class clas : classes) {
            HBox action = clas.getAction();
            if (action != null) {
                action.lookup("#edit").setOnMouseClicked(mouseEvent -> {
                    try {
                        EditClassController.show(clas, this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                action.lookup("#delete").setOnMouseClicked(mouseEvent -> {
                    try {
                        DeleteClass.show(clas.getId(), this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        classTable.setItems(classes);
    }

    public void add() throws IOException {
        EditClassController.show(null, this);
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
