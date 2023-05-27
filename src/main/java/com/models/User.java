package com.models;

import com.utilities.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Objects;

@Setter
@Getter
public class User {
    private String id;
    private String name;
    private String password;
    private String displayName;
    private String role;
    private HBox action;

    public User() {
    }

    public User(String id, String name, String password, String displayName, String role) throws IOException {
        this.id = id;
        this.name = name;
        this.password = password;
        this.displayName = displayName;
        this.role = role;
        Node action = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_ACTION)));
        action.setId("action");
        action.getStyleClass().add("action");
        this.action = (HBox) action;
    }
}
