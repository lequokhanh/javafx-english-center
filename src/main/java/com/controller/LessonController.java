package com.controller;

import com.models.Class;
import com.models.Lesson;
import com.models.Material;
import com.models.Student;
import com.service.LessonService;
import com.service.MaterialService;
import com.service.StudentService;
import com.utilities.Constants;
import com.utilities.Manager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.File;

import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
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
    public Tab materialPane;
    public Tab attendancePane;
    public GridPane materialGridPane;
    public GridPane absentGridPane;
    public GridPane presentGridPane;
    public Label absentNumber;
    public Label totalNumber;
    public HBox action;
    public TabPane tabPane;
    public Button uploadBtn;
    public TextField searchMaterial;
    public TextField searchLesson;
    public Label smallTittle;

    public void initialize() throws IOException {
        if (Manager.getAuth().split("/")[2].equals("Student") || Manager.getAuth().split("/")[2].equals("Teacher")) {
            action.setVisible(false);
            smallTittle.setText("Lessons in Class");
        }
        if (Manager.getAuth().split("/")[2].equals("Student")) {
            tabPane.getTabs().remove(attendancePane);
            uploadBtn.setVisible(false);
        }
        Platform.runLater(() -> {
            if (classes.getStatus().equals("Finished")) {
                action.setVisible(false);
                uploadBtn.setVisible(false);
            }
            className.setText(classes.getName());
            courseName.setText(classes.getCourse().getCourseName());
            try {
                search("");
            } catch (SQLException | IOException e) {
                try {
                    ErrorController.show(e.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
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

    public void search(String keyWord) throws SQLException, IOException {
        ObservableList<Lesson> lessons = LessonService.search(classes.getId(), keyWord);
        int row = lessons.size();
        gridPane.getChildren().clear();
        for (int i = 0; i < row; i++) {
            Lesson lesson = lessons.get(i);
            Node lessonCard = FXMLLoader
                    .load(Objects.requireNonNull(getClass().getResource(Constants.FXML_LESSON_CARD)));
            AnchorPane cardPane = (AnchorPane) lessonCard.lookup(".cardPane");
            AnchorPane homepage = (AnchorPane) lessonPane.getParent();
            Node description = FXMLLoader
                    .load(Objects.requireNonNull(getClass().getResource(Constants.FXML_DESCRIPTION_POP_UP)));
            ((Label) ((AnchorPane) description.lookup("#anchor")).lookup("#description"))
                    .setText(lesson.getChapter().getChapterDescription());
            cardPane.setOnMouseMoved(e -> {
                homepage.getChildren().removeAll(description);
                description.setId("description");
                description.setLayoutX(e.getSceneX() - e.getX() + 200);
                description.setLayoutY(max(e.getSceneY() - e.getY() - 175, 69));
                homepage.getChildren().add(description);
            });
            cardPane.setOnMouseExited(e -> {
                homepage.getChildren().removeAll(description);
            });
            cardPane.setOnMouseClicked(e -> {
                if (gridPane.lookup(".cardPaneSelected") != null) {
                    gridPane.lookup(".cardPaneSelected").getStyleClass().remove("cardPaneSelected");
                }
                cardPane.getStyleClass().add("cardPaneSelected");
                selectedLesson = lesson;
                if (lessonPane.lookup(".emptyTab").isVisible()) {
                    lessonPane.lookup(".emptyTab").setVisible(false);
                }
                lessonTab.setVisible(true);
                try {
                    searchMaterialPane("");
                    reloadAttendancePane();
                } catch (IOException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            ((Label) cardPane.lookup("#name")).setText(lesson.getChapter().getChapterName());
            ((Label) cardPane.lookup("#category")).setText(lesson.getChapter().getCategory());
            switch (lesson.getChapter().getCategory()) {
                case "Reading":
                    cardPane.lookup("#category").getStyleClass().add("reading");
                    break;
                case "Listening":
                    cardPane.lookup("#category").getStyleClass().add("listening");
                    break;
                case "Speaking":
                    cardPane.lookup("#category").getStyleClass().add("speaking");
                    break;
                case "Writing":
                    cardPane.lookup("#category").getStyleClass().add("writing");
                    break;
                default:
                    cardPane.lookup("#category").getStyleClass().add("other");
                    break;
            }
            ((Label) lessonCard.lookup("#date")).setText(lesson.getDate());
            gridPane.add(lessonCard, 0, i);
        }
    }

    public void searchMaterialPane(String keyWord) throws IOException, SQLException {
        ObservableList<Material> materials = MaterialService.search(selectedLesson.getId(), keyWord);
        materialGridPane.getChildren().clear();
        for (int i = 0; i < materials.size(); i++) {
            Material material = materials.get(i);
            Node fileItem = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FILE_ITEM)));
            String fileName = material.getPath().split("\\\\")[material.getPath().split("\\\\").length - 1];
            ((Label) fileItem.lookup(".fileName")).setText(fileName);
            fileItem.lookup(".fileName").setOnMouseClicked(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File");
                fileChooser.setInitialFileName(fileName);
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
                fileChooser.getExtensionFilters().add(extFilter);
                File selectedDirectory = fileChooser.showSaveDialog(fileItem.getScene().getWindow());
                if (selectedDirectory != null) {
                    try {
                        Files.copy(Paths.get(material.getPath()), Paths.get(selectedDirectory.getPath()),
                                StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            ((Button) fileItem.lookup(".deleteBtn")).setOnAction(e -> {
                try {
                    DeleteMaterial.show(material.getId(), this);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            if (Manager.getAuth().split("/")[2].equals("Student"))
                fileItem.lookup(".deleteBtn").setVisible(false);
            materialGridPane.add(fileItem, 0, i);
        }
    }

    public void reloadAttendancePane() throws IOException, SQLException {
        ObservableList<Student> absentStudent = StudentService.absentStudent(classes.getId(), selectedLesson.getId());
        ObservableList<Student> presentStudent = StudentService.presentStudent(selectedLesson.getId());
        absentNumber.setText(Integer.toString(absentStudent.size()));
        totalNumber.setText(Integer.toString(absentStudent.size() + presentStudent.size()));
        absentGridPane.getChildren().clear();
        presentGridPane.getChildren().clear();
        for (int i = 0; i < absentStudent.size(); i++) {
            Student student = absentStudent.get(i);
            Node studentItem = FXMLLoader
                    .load(Objects.requireNonNull(getClass().getResource(Constants.FXML_STUDENT_ITEM)));
            ((Label) studentItem.lookup(".name")).setText(student.getName());
            ((Label) studentItem.lookup(".id")).setText(student.getId());
            if (classes.getStatus().equals("Finished"))
                studentItem.lookup("#rollCallBtn").setVisible(false);
            studentItem.lookup("#rollCallBtn").setOnMouseClicked(e -> {
                try {
                    StudentService.RollCall(student.getId(), selectedLesson.getId());
                    reloadAttendancePane();
                } catch (SQLException | IOException ex) {
                    try {
                        ErrorController.show(ex.getMessage());
                    } catch (IOException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            });
            absentGridPane.add(studentItem, 0, i);
        }
        for (int i = 0; i < presentStudent.size(); i++) {
            Student student = presentStudent.get(i);
            Node studentItem = FXMLLoader
                    .load(Objects.requireNonNull(getClass().getResource(Constants.FXML_STUDENT_ITEM)));
            ((Label) studentItem.lookup(".name")).setText(student.getName());
            ((Label) studentItem.lookup(".id")).setText(student.getId());
            studentItem.lookup("#rollCallBtn").setVisible(false);
            if (!classes.getStatus().equals("Finished"))
                studentItem.lookup("#removeBtn").setVisible(true);
            studentItem.lookup("#removeBtn").setOnMouseClicked(e -> {
                try {
                    StudentService.removeRollCall(student.getId(), selectedLesson.getId());
                    reloadAttendancePane();
                } catch (SQLException | IOException ex) {
                    try {
                        ErrorController.show(ex.getMessage());
                    } catch (IOException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            });
            presentGridPane.add(studentItem, 0, i);
        }

    }

    public void add(ActionEvent actionEvent) throws SQLException, IOException {
        EditLessonController.show(null, classes, this);
    }

    public void edit(ActionEvent actionEvent) throws SQLException, IOException {
        if (selectedLesson == null) {
            try {
                ErrorController.show("Please select a lesson");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        EditLessonController.show(selectedLesson, classes, this);
    }

    public void delete(ActionEvent actionEvent) {
        if (selectedLesson == null) {
            try {
                ErrorController.show("Please select a lesson");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        try {
            DeleteLesson.show(selectedLesson.getId(), this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void upload() throws IOException {
        UploadMaterial.show(selectedLesson.getId(), this);
    }

    public void searchMaterial(KeyEvent keyEvent) {
        try {
            searchMaterialPane(searchMaterial.getText());
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchLesson(KeyEvent keyEvent) {
        try {
            search(searchLesson.getText());
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
