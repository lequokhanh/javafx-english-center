package com.controller;

import com.App;
import com.models.Student;
import com.service.ClassService;
import com.service.StudentService;
import com.utilities.Constants;
import com.utilities.Manager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class StudentList {
    public AnchorPane editPopUp;
    public GridPane gridPane;

    public static void show(String classID) throws SQLException, IOException {
        FXMLLoader fxml = new FXMLLoader(
                Objects.requireNonNull(EditClassController.class.getResource(Constants.FXML_STUDENT_LIST)));
        Parent studentList = fxml.load();
        StudentList controller = fxml.getController();
        controller.reload(classID);
        App.addPopup(studentList);
    }

    public void reload(String classID) throws SQLException, IOException {
        ObservableList<Student> students = ClassService.getStudents(classID);
        gridPane.getChildren().clear();
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            Node studentItem = FXMLLoader
                    .load(Objects.requireNonNull(getClass().getResource(Constants.FXML_STUDENT_ITEM)));
            ((Label) studentItem.lookup(".name")).setText(student.getName());
            ((Label) studentItem.lookup(".id")).setText(student.getId());
            studentItem.lookup("#rollCallBtn").setVisible(false);
            if (!Manager.getAuth().split("/")[2].equals("Teacher"))
                studentItem.lookup("#removeBtn").setVisible(true);
            studentItem.lookup("#removeBtn").setOnMouseClicked(e -> {
                try {
                    ClassService.removeStudent(student.getId(), classID);
                    reload(classID);
                } catch (SQLException | IOException ex) {
                    try {
                        ErrorController.show(ex.getMessage());
                    } catch (IOException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            });
            gridPane.add(studentItem, 0, i);
        }
    }

    public void close(ActionEvent actionEvent) {
        editPopUp.getScene().getWindow().hide();
    }
}
