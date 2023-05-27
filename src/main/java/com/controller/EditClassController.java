package com.controller;

import com.App;
import com.models.Class;
import com.models.Course;
import com.models.Room;
import com.models.Teacher;
import com.service.AccountService;
import com.service.ClassService;
import com.service.CourseService;
import com.service.RoomService;
import com.utilities.Constants;
import com.utilities.DateFormat;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class EditClassController {
    public AnchorPane editPopUp;
    public Label classID;
    public TextField className;
    public ComboBox<Course> course;
    public ComboBox<Teacher> teacher;
    public ComboBox<String> session;
    public ComboBox<String> day;
    public DatePicker startDate;
    public DatePicker finishDate;
    public Button submit;
    public ComboBox<Room> room;

    public void initialize() throws SQLException, IOException {
        ObservableList<Course> courses = CourseService.search("");
        course.setItems(courses);
        ObservableList<Teacher> teachers = AccountService.getAllTeacher();
        teacher.setItems(teachers);
        ObservableList<Room> rooms = RoomService.search("");
        room.setItems(rooms);
        startDate.setConverter(new DateFormat());
        finishDate.setConverter(new DateFormat());
    }

    public static void show(Class clas, ClassesController handle) throws IOException {
        DateFormat df = new DateFormat();
        FXMLLoader fxml = new FXMLLoader(
                Objects.requireNonNull(EditClassController.class.getResource(Constants.FXML_EDIT_CLASS)));
        Parent editClass = fxml.load();
        EditClassController controller = fxml.getController();
        if (clas == null) {
            try {
                controller.classID.setText(ClassService.getNewId());
                controller.submit.setOnAction(e -> {
                    try {
                        if (controller.className.getText().isEmpty() || controller.course.getValue() == null
                                || controller.teacher.getValue() == null || controller.session.getValue() == null
                                || controller.day.getValue() == null || controller.startDate.getValue() == null
                                || controller.finishDate.getValue() == null || controller.room.getValue() == null) {
                            ErrorController.show("Please fill all the fields");
                            return;
                        }
                        ClassService.Insert(controller.classID.getText(), controller.className.getText(),
                                controller.course.getValue().getCourseID(), controller.teacher.getValue().getId(),
                                controller.room.getValue().getId(), controller.day.getValue(),
                                controller.session.getValue(), df.toString(controller.startDate.getValue()),
                                df.toString(controller.finishDate.getValue()));
                        handle.search("");
                        controller.close();
                    } catch (IOException | SQLException ex) {
                        try {
                            ErrorController.show(ex.toString());
                        } catch (IOException exc) {
                            throw new RuntimeException(exc);
                        }
                    }
                });
            } catch (SQLException e) {
                ErrorController.show(e.toString());
            }
        } else {
            controller.setDataToEdit(clas);
            controller.submit.setOnAction(e -> {
                try {
                    if (controller.className.getText().isEmpty() || controller.course.getValue() == null
                            || controller.teacher.getValue() == null || controller.session.getValue() == null
                            || controller.day.getValue() == null || controller.startDate.getValue() == null
                            || controller.finishDate.getValue() == null || controller.room.getValue() == null) {
                        ErrorController.show("Please fill all the fields");
                        return;
                    }
                    ClassService.Update(controller.classID.getText(), controller.className.getText(),
                            controller.course.getValue().getCourseID(), controller.teacher.getValue().getId(),
                            controller.room.getValue().getId(), controller.day.getValue(),
                            controller.session.getValue(), df.toString(controller.startDate.getValue()),
                            df.toString(controller.finishDate.getValue()));
                    handle.search("");
                    controller.close();
                } catch (IOException | SQLException ex) {
                    try {
                        ErrorController.show(ex.toString());
                    } catch (IOException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            });
        }
        App.addPopup(editClass);
    }

    public void setDataToEdit(Class clas) {
        DateFormat dateFormat = new DateFormat();
        classID.setText(clas.getId());
        className.setText(clas.getName());
        course.setValue(clas.getCourse());
        teacher.setValue(clas.getTeacher());
        room.setValue(clas.getRoom());
        session.setValue(clas.getSession_time());
        day.setValue(clas.getSession_day());
        startDate.setValue(dateFormat.fromString(clas.getStart()));
        finishDate.setValue(dateFormat.fromString(clas.getEnd()));
    }

    public void close() {
        editPopUp.getScene().getWindow().hide();
    }
}
