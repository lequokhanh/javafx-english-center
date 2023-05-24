package com.controller;

import com.App;
import com.utilities.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.Objects;

public class SuccessfulController {

    public static void show() throws IOException {
        Parent successful = FXMLLoader.load(Objects.requireNonNull(SuccessfulController.class.getResource(Constants.FXML_SUCCESSFUL)));
        App.addPopup(successful);
        Button close = (Button) successful.lookup("#close");
        close.setOnMouseClicked(e -> {
            try {
                App.removePopUp(successful);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
