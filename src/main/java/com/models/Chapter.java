package com.models;

import com.utilities.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import lombok.*;

import java.io.IOException;
import java.util.Objects;

@Getter
@Setter
public class Chapter {
    private String chapterID;
    private String chapterName;
    private String chapterDescription;
    private String category;
    private HBox action;

    public Chapter() {
    }

    public Chapter(String chapterID, String chapterName, String chapterDescription, String category) throws IOException {
        this.chapterID = chapterID;
        this.chapterName = chapterName;
        this.chapterDescription = chapterDescription;
        this.category = category;
        Node action = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_ACTION)));
        action.setId("action");
        action.getStyleClass().add("action");
        this.action = (HBox) action;
    }
}
