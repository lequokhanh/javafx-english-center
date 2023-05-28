package com.controller;

import com.models.Room;
import com.models.Schedule;
import com.service.RoomService;
import com.service.ScheduleService;
import com.utilities.Constants;
import com.utilities.Manager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class RoomController {

    public GridPane roomGridPane;
    public Room selectedRoom;
    public Label emptyText;
    public AnchorPane roomPane;
    public Button edit;
    public TextField searchBox;
    public HBox action;

    public void initialize() throws SQLException, IOException {
        if (Manager.getAuth().split("/")[2].equals("Teacher"))
            action.setVisible(false);
        search("");
    }

    public void search(String keyWord) throws SQLException, IOException {
        roomGridPane.getChildren().clear();
        ObservableList<Room> rooms = RoomService.search(keyWord);
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            Node roomCard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_ROOM_CARD)));
            ((Label) roomCard.lookup("#name")).setText(room.getName());
            ((Label) roomCard.lookup("#capacity")).setText(room.getCapacity());
            roomCard.setOnMouseClicked(event -> {
                selectedRoom = room;
                if (roomGridPane.lookup(".cardPaneSelected") != null) {
                    roomGridPane.lookup(".cardPaneSelected").getStyleClass().remove("cardPaneSelected");
                }
                roomCard.getStyleClass().add("cardPaneSelected");
                try {
                    if (emptyText.isVisible()) {
                        emptyText.setVisible(false);
                    }
                    reloadGridPane();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            });
            roomGridPane.add(roomCard, 0, i);
        }
    }

    public void reloadGridPane() throws IOException, SQLException {
        GridPane gridPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_SCHEDULE_GRID_PANE)));
        gridPane.setId("gridPane");
        if (roomPane.lookup("#gridPane") != null) {
            roomPane.getChildren().remove(roomPane.lookup("#gridPane"));
        }
        roomPane.getChildren().add(gridPane);
        gridPane.setLayoutX(389);
        gridPane.setLayoutY(161);
        Schedule schedule = ScheduleService.getScheduleWithRoom(selectedRoom.getId());
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

    public void searchAction(KeyEvent keyEvent) throws IOException {
        try {
            search(searchBox.getText());
        } catch (SQLException | IOException e) {
            ErrorController.show(e.getMessage());
        }
    }

    public void add(ActionEvent actionEvent) throws SQLException, IOException {
        EditRoomController.show(null, this);
    }

    public void edit(ActionEvent actionEvent) throws SQLException, IOException {
        if (selectedRoom == null) {
            try {
                ErrorController.show("Please select a room");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        EditRoomController.show(selectedRoom, this);
    }

    public void delete(ActionEvent actionEvent) {
        if (selectedRoom == null) {
            try {
                ErrorController.show("Please select a room");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        try {
            DeleteRoom.show(selectedRoom.getId(), this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
