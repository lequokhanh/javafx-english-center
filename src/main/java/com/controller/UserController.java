package com.controller;

import com.models.User;
import com.service.AccountService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;

public class UserController {
    public TableView<User> UserTable;
    public TableColumn<User, String> id;
    public TableColumn<User, String> name;
    public TableColumn<User, String> password;
    public TableColumn<User, String> displayName;
    public TableColumn<User, String> role;
    public TableColumn<User, HBox> action;
    public TextField searchField;
    public Button enrollBtn;
    public Button showPasswordBtn;
    public Button hidePasswordBtn;

    public void initialize() throws IOException {
        id.setPrefWidth(130);
        name.setPrefWidth(270);
        password.setPrefWidth(150);
        displayName.setPrefWidth(310);
        role.setPrefWidth(130);
        action.setPrefWidth(130);
        UserTable.getColumns().forEach(e -> {
            e.setReorderable(false);
            e.setResizable(false);
        });
        try {
            search("");
        } catch (SQLException | IOException e) {
            ErrorController.show(e.getMessage());
        }
    }

    public void search(String keyWord) throws SQLException, IOException {
        ObservableList<User> users = AccountService.search(keyWord);
        for (User user : users) {
            HBox action = user.getAction();
            if (action != null) {
                action.lookup("#edit").setOnMouseClicked(mouseEvent -> {
                    try {
                        EditUserController.show(user, this);
                    } catch (IOException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                action.lookup("#delete").setOnMouseClicked(mouseEvent -> {
                    try {
                        DeleteUser.show(user.getId(), this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        enrollBtn.setVisible(false);
        UserTable.setItems(users);
        hidePassword();
    }

    public void hidePassword() {
        hidePasswordBtn.setVisible(false);
        showPasswordBtn.setVisible(true);
        password.setCellFactory(column -> new TableCell<User, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : "**************");
            }
        });
    }

    public void showPassword() {
        hidePasswordBtn.setVisible(true);
        showPasswordBtn.setVisible(false);
        password.setCellFactory(column -> new TableCell<User, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : getItem());
            }
        });
    }

    public void searchAction(KeyEvent keyEvent) throws IOException {
        try {
            search(searchField.getText());
        } catch (SQLException | IOException e) {
            ErrorController.show(e.getMessage());
        }
    }

    public void add(ActionEvent actionEvent) throws SQLException, IOException {
        EditUserController.show(null, this);
    }

    public void enroll(ActionEvent actionEvent) throws IOException {
        try {
            EnrollUser.show(UserTable.getSelectionModel().getSelectedItem());
        } catch (IOException | SQLException e) {
            ErrorController.show(e.getMessage());
        }
    }

    public void clickToEnroll(MouseEvent mouseEvent) {
        if (UserTable.getSelectionModel().getSelectedItem() == null || !UserTable.getSelectionModel().getSelectedItem().getRole().equals("Student")) {
            enrollBtn.setVisible(false);
            return;
        }
        enrollBtn.setVisible(true);
    }
}
