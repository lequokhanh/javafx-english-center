package com.controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

public class DeleteController {
    public AnchorPane dialog;


    public void cancel(ActionEvent actionEvent) {
        dialog.getScene().getWindow().hide();
    }

    public void confirm(ActionEvent actionEvent) {
    }
}
