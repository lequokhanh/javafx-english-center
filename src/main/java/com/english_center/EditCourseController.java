package com.english_center;

import com.utilities.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;
//import java.util.function.Consumer;

public class EditCourseController {

    public AnchorPane editPopUp;

    public void initialize() {
    }

    public static void show() throws IOException {
        Parent editCourse = FXMLLoader.load(Objects.requireNonNull(EditCourseController.class.getResource(Constants.FXML_EDIT_COURSE)));
        App.addPopup(editCourse);
    }

    public void submit(ActionEvent actionEvent) {


    }

    public void close(ActionEvent actionEvent) {
        editPopUp.getScene().getWindow().hide();

    }
}
