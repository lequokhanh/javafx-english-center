package com.controller;

import com.models.Schedule;
import com.service.ScheduleService;
import com.utilities.Constants;
import com.utilities.Manager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ScheduleController {

    public GridPane gridPane;

    public void initialize() throws SQLException, IOException {
        Schedule schedule = new Schedule();
        if (Manager.getAuth().split("/")[2].equals("Student"))
            schedule = ScheduleService.getScheduleWithStudent(Manager.getAuth().split("/")[0]);
        else if (Manager.getAuth().split("/")[2].equals("Teacher"))
            schedule = ScheduleService.getScheduleWithTeacher(Manager.getAuth().split("/")[0]);
        for (int i = 0; i < 6; i++) {
            if (schedule.getSession1().get(i).equals("")) {
                StackPane empty = new StackPane();
                empty.getStyleClass().add("cell");
                gridPane.add(empty, i + 1, 1);
            } else {
                Node scheduleItem = FXMLLoader
                        .load(Objects.requireNonNull(getClass().getResource(Constants.FXML_SCHEDULE_ITEM)));
                ((Label) scheduleItem.lookup("#name")).setText(schedule.getSession1().get(i));
                gridPane.add(scheduleItem, i + 1, 1);
            }
            if (schedule.getSession2().get(i).equals("")) {
                StackPane empty = new StackPane();
                empty.getStyleClass().add("cell");
                gridPane.add(empty, i + 1, 2);
            } else {
                Node scheduleItem = FXMLLoader
                        .load(Objects.requireNonNull(getClass().getResource(Constants.FXML_SCHEDULE_ITEM)));
                ((Label) scheduleItem.lookup("#name")).setText(schedule.getSession2().get(i));
                gridPane.add(scheduleItem, i + 1, 2);
            }
            if (schedule.getSession3().get(i).equals("")) {
                StackPane empty = new StackPane();
                empty.getStyleClass().add("cell");
                gridPane.add(empty, i + 1, 3);
            } else {
                Node scheduleItem = FXMLLoader
                        .load(Objects.requireNonNull(getClass().getResource(Constants.FXML_SCHEDULE_ITEM)));
                ((Label) scheduleItem.lookup("#name")).setText(schedule.getSession3().get(i));
                gridPane.add(scheduleItem, i + 1, 3);
            }
            if (schedule.getSession4().get(i).equals("")) {
                StackPane empty = new StackPane();
                empty.getStyleClass().add("cell");
                gridPane.add(empty, i + 1, 4);
            } else {
                Node scheduleItem = FXMLLoader
                        .load(Objects.requireNonNull(getClass().getResource(Constants.FXML_SCHEDULE_ITEM)));
                ((Label) scheduleItem.lookup("#name")).setText(schedule.getSession4().get(i));
                gridPane.add(scheduleItem, i + 1, 4);
            }
        }
    }
}
