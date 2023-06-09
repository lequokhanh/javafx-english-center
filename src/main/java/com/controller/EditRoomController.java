package com.controller;

import com.App;
import com.models.Room;
import com.service.RoomService;
import com.utilities.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class EditRoomController {

    public AnchorPane editPopUp;
    public Button submit;
    public TextField capacity;
    public TextField roomName;
    public Label roomID;

    public void initialize() {
        capacity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*"))
                    capacity.setText(newValue.replaceAll("[^\\d]", ""));
                if (!capacity.getText().equals("") && Integer.parseInt(capacity.getText()) < 1)
                    capacity.setText("1");
                if (!capacity.getText().equals("") && Integer.parseInt(capacity.getText()) > 50)
                    capacity.setText("50");
            }
        });
    }

    public static void show(Room room, RoomController handle) throws SQLException, IOException {
        FXMLLoader fxml = new FXMLLoader(
                Objects.requireNonNull(EditClassController.class.getResource(Constants.FXML_EDIT_ROOM)));
        Parent editLesson = fxml.load();
        EditRoomController controller = fxml.getController();
        if (room == null) {
            controller.roomID.setText(RoomService.getNewID());
            controller.submit.setOnAction(e -> {
                try {
                    if (controller.roomName.getText().isEmpty() || controller.capacity.getText().isEmpty()) {
                        ErrorController.show("Please fill in all fields");
                        return;
                    }
                    RoomService.Insert(controller.roomID.getText(), controller.roomName.getText(),
                            controller.capacity.getText());
                    handle.search("");
                    controller.close();
                    SuccessfulController.show();
                } catch (SQLException | IOException ex) {
                    try {
                        ErrorController.show(ex.getMessage());
                    } catch (IOException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            });
        } else {
            controller.roomID.setText(room.getId());
            controller.roomName.setText(room.getName());
            controller.capacity.setText(room.getCapacity());
            controller.submit.setOnAction(e -> {
                try {
                    if (controller.roomName.getText().isEmpty() || controller.capacity.getText().isEmpty()) {
                        ErrorController.show("Please fill in all fields");
                        return;
                    }
                    RoomService.Update(room.getId(), controller.roomName.getText(), controller.capacity.getText());
                    handle.search("");
                    controller.close();
                    SuccessfulController.show();
                } catch (SQLException | IOException ex) {
                    try {
                        ErrorController.show(ex.getMessage());
                    } catch (IOException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            });
        }
        App.addPopup(editLesson);
    }

    public void close() {
        editPopUp.getScene().getWindow().hide();
    }
}
