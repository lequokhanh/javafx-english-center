package com.controller;

import com.App;
import com.service.AccountService;
import com.utilities.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class DeleteUser {
    public static void show(String role, String id, UserController handle) throws IOException {
        Parent delete = FXMLLoader
                .load(Objects.requireNonNull(EditCourseController.class.getResource(Constants.FXML_DELETE)));
        App.addPopup(delete);
        delete.lookup("#cancel").setOnMouseClicked(e -> {
            try {
                App.removePopUp(delete);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        delete.lookup("#confirm").setOnMouseClicked(e -> {
            try {
                if (role.equals("Manager")) {
                    ErrorController.show("Cannot delete manager");
                    return;
                }
                AccountService.Delete(id);
                handle.search("");
                App.removePopUp(delete);
                SuccessfulController.show();
            } catch (IOException | SQLException ex) {
                try {
                    ErrorController.show("Please remove all relate to this user first");
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        });
    }
}
