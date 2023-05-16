package com.english_center;

import com.utilities.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class DeleteController {
    public AnchorPane dialog;


    public void cancel(ActionEvent actionEvent) {
        dialog.getScene().getWindow().hide();
    }

    public void confirm(ActionEvent actionEvent) {
    }
}
