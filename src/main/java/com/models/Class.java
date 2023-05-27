package com.models;

import com.utilities.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Objects;

@Getter
@Setter
public class Class {
    private String id;
    private String name;
    private Course course;
    private Teacher teacher;
    private Room room;
    private String session_day;
    private String session_time;
    private String start;
    private String end;
    private HBox action;

    public Class() {

    }

    public Class(String id, String name, Course course, Teacher teacher, Room room, String session_day, String session_time, String start, String end) throws IOException {
        this.id = id;
        this.name = name;
        this.course = course;
        this.teacher = teacher;
        this.room = room;
        this.session_day = session_day;
        this.session_time = session_time;
        this.start = start;
        this.end = end;
        Node action = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_ACTION)));
        action.setId("action");
        action.getStyleClass().add("action");
        this.action = (HBox) action;
    }

    public String toString() {
        return this.name;
    }

}
