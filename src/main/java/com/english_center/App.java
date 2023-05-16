package com.english_center;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import com.utilities.Constants;
import javafx.stage.StageStyle;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML(Constants.FXML_LOGIN), 960, 540);
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResource("/image/logo_app.png")).openStream()));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle(Constants.APP_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml, int width, int height) throws IOException {
        scene.setRoot(loadFXML(fxml));
        scene.getWindow().setWidth(width);
        scene.getWindow().setHeight(height);
        scene.getWindow().centerOnScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void addPopup(Parent root) throws IOException {
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color:transparent");
        stackPane.getChildren().add(root);
        stackPane.setPadding(new Insets(20, 20, 20, 20));
        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(stackPane));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        stage.getScene().setFill(Color.TRANSPARENT);
    }

    public static void main(String[] args) {
        launch();
    }

}