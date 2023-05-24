package com.controller;

import com.App;
import com.utilities.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Objects;

public class ErrorController {

    public static void show(String message) throws IOException {
        Parent error = FXMLLoader.load(Objects.requireNonNull(SuccessfulController.class.getResource(Constants.FXML_ERROR)));
        App.addPopup(error);
        ((Label) error.lookup("#message")).setText(message);
        Button close = (Button) error.lookup("#close");
        close.setOnMouseClicked(e -> {
            try {
                App.removePopUp(error);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
