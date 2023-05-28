package com.controller;

import com.App;
import com.models.Chapter;
import com.service.ChapterService;
import com.utilities.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class EditChapterController {

    public AnchorPane editPopUp;
    public Chapter chapter = null;
    public Label chapterID;
    public TextField chapterName;
    public TextArea chapterDescription;
    public ComboBox<String> category;
    public Button submit;

    public static void show(Chapter chapter, String courseID, ChapterController handle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Objects.requireNonNull(EditChapterController.class.getResource(Constants.FXML_EDIT_CHAPTER)));
        Parent editChapter = fxmlLoader.load();
        EditChapterController controller = fxmlLoader.getController();
        if (chapter == null) {
            try {
                controller.chapterID.setText(ChapterService.getNewId());
                controller.submit.setOnAction(e -> {
                    try {
                        if (controller.chapterName.getText().isEmpty()
                                || controller.chapterDescription.getText().isEmpty()
                                || controller.category.getValue() == null) {
                            ErrorController.show("Please fill all the fields");
                            return;
                        }
                        ChapterService.InsertNewChapter(controller.chapterID.getText(), courseID,
                                controller.chapterName.getText(),
                                controller.chapterDescription.getText(), controller.category.getValue());
                        handle.search("");
                        controller.close();
                        SuccessfulController.show();
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
                return;
            }
        } else {
            controller.setDataToEdit(chapter);
            controller.submit.setOnAction(e -> {
                try {
                    ChapterService.UpdateChapter(controller.chapter.getChapterID(), controller.chapterName.getText(),
                            controller.chapterDescription.getText(), controller.category.getValue());
                    handle.search("");
                    controller.close();
                    SuccessfulController.show();
                } catch (IOException | SQLException ex) {
                    try {
                        ErrorController.show(ex.toString());
                    } catch (IOException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            });
        }
        App.addPopup(editChapter);
    }

    public void setDataToEdit(Chapter chapter) {
        this.chapter = chapter;
        chapterID.setText(chapter.getChapterID());
        chapterName.setText(chapter.getChapterName());
        chapterDescription.setText(chapter.getChapterDescription());
        category.setValue(chapter.getCategory());
    }

    public void close() {
        editPopUp.getScene().getWindow().hide();
    }
}
