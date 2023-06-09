package com.controller;

import com.App;
import com.service.MaterialService;
import com.utilities.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UploadMaterial {
    public Button save;
    public AnchorPane uploadPopUp;
    public GridPane gridPane;
    public List<File> files = new java.util.ArrayList<>();

    public static void show(String lessonID, LessonController handle) throws IOException {
        FXMLLoader fxml = new FXMLLoader(UploadMaterial.class.getResource(Constants.FXML_UPLOAD_MATERIAL));
        Parent upload = fxml.load();
        UploadMaterial controller = fxml.getController();
        controller.save.setOnAction(e -> {
            try {
                for (File file : controller.files) {
                    File folder = new File(Constants.MATERIAL_PATH + "/" + lessonID);
                    if (!folder.exists()) {
                        if (!folder.mkdirs())
                            throw new IOException("Cannot create folder");
                    }
                    File dest = new File(Constants.MATERIAL_PATH + "/" + lessonID + "/" + file.getName());
                    Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    MaterialService.Insert(String.valueOf(dest.toPath()), lessonID);
                    handle.searchMaterialPane("");
                    controller.close();
                }
                SuccessfulController.show();
            } catch (SQLException | IOException ex) {
                try {
                    ErrorController.show(ex.getMessage());
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        });
        App.addPopup(upload);
    }

    public void close() {
        uploadPopUp.getScene().getWindow().hide();
    }

    public void chooseFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        ArrayList<File> files = new ArrayList<>();
        files = (ArrayList<File>) fileChooser.showOpenMultipleDialog(uploadPopUp.getScene().getWindow());
        if (files != null) {
            for (File file : files) {
                if (!this.files.contains(file))
                    this.files.add(file);
            }
        }
        reloadGridPane();
    }

    public void reloadGridPane() throws IOException {
        gridPane.getChildren().clear();
        int row = files.size();
        for (int i = 0; i < row; i++) {
            Node fileItem = FXMLLoader
                    .load(Objects.requireNonNull(UploadMaterial.class.getResource(Constants.FXML_SMALL_FILE_ITEM)));
            File file = files.get(i);
            ((Label) fileItem.lookup(".fileName")).setText(file.getName());
            ((Button) fileItem.lookup(".deleteBtn")).setOnAction(e -> {
                files.remove(file);
                try {
                    reloadGridPane();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            gridPane.add(fileItem, 0, i);
        }
    }
}
