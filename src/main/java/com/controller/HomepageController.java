package com.controller;

import com.App;
import com.utilities.Constants;

import com.utilities.Manager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class HomepageController {
    public ImageView homePageImage;
    public Button generalBtn;
    public Button coursesBtn;
    public Button classesBtn;
    public Button roomsBtn;
    public Button scheduleBtn;
    public Button usersBtn;
    public AnchorPane homepage;
    public VBox sidebar;

    @FXML
    private void initialize() {
        if (Manager.getAuth().split("/")[2].equals("Student")) {
            sidebar.getChildren().removeAll(roomsBtn, usersBtn, generalBtn);
        } else if (Manager.getAuth().split("/")[2].equals("Teacher")) {
            sidebar.getChildren().removeAll(classesBtn, roomsBtn, scheduleBtn, generalBtn);
        } else if (Manager.getAuth().split("/")[2].equals("Manager")) {
            sidebar.getChildren().removeAll(scheduleBtn);
        }
        generalBtn.setOnAction(e -> {
            try {
                if (homepage.lookup("#tabpane") != null && generalBtn.getStyleClass().contains("tabbtnactive")) {
                    homePageImage.setVisible(true);
                    generalBtn.getStyleClass().remove("tabbtnactive");
                    homepage.getChildren().remove(homepage.lookup("#tabpane"));
                } else {
                    if (sidebar.lookup(".tabbtnactive") != null) {
                        sidebar.lookup(".tabbtnactive").getStyleClass().remove("tabbtnactive");
                        homepage.getChildren().remove(homepage.lookup("#tabpane"));
                    }
                    homePageImage.setVisible(false);
                    generalBtn.getStyleClass().add("tabbtnactive");
                    Node general = FXMLLoader
                            .load(Objects.requireNonNull(getClass().getResource(Constants.FXML_GENERAL)));
                    general.setId("tabpane");
                    general.getStyleClass().add("tabpane");
                    homepage.getChildren().add(general);
                    general.setLayoutX(345);
                    general.setLayoutY(20);
                    general.toFront();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        coursesBtn.setOnAction(e -> {
            try {
                if (homepage.lookup("#tabpane") != null && coursesBtn.getStyleClass().contains("tabbtnactive")) {
                    homePageImage.setVisible(true);
                    coursesBtn.getStyleClass().remove("tabbtnactive");
                    homepage.getChildren().remove(homepage.lookup("#tabpane"));
                } else {
                    if (sidebar.lookup(".tabbtnactive") != null) {
                        sidebar.lookup(".tabbtnactive").getStyleClass().remove("tabbtnactive");
                        homepage.getChildren().remove(homepage.lookup("#tabpane"));
                    }
                    homePageImage.setVisible(false);
                    coursesBtn.getStyleClass().add("tabbtnactive");
                    Node courses = FXMLLoader
                            .load(Objects.requireNonNull(getClass().getResource(Constants.FXML_COURSES)));
                    courses.setId("tabpane");
                    courses.getStyleClass().add("tabpane");
                    homepage.getChildren().add(courses);
                    courses.setLayoutX(345);
                    courses.setLayoutY(20);
                    courses.toFront();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        classesBtn.setOnAction(e -> {
            try {
                if (homepage.lookup("#tabpane") != null && classesBtn.getStyleClass().contains("tabbtnactive")) {
                    homePageImage.setVisible(true);
                    classesBtn.getStyleClass().remove("tabbtnactive");
                    homepage.getChildren().remove(homepage.lookup("#tabpane"));
                } else {
                    if (sidebar.lookup(".tabbtnactive") != null) {
                        sidebar.lookup(".tabbtnactive").getStyleClass().remove("tabbtnactive");
                        homepage.getChildren().remove(homepage.lookup("#tabpane"));
                    }
                    homePageImage.setVisible(false);
                    classesBtn.getStyleClass().add("tabbtnactive");
                    Node classes = FXMLLoader
                            .load(Objects.requireNonNull(getClass().getResource(Constants.FXML_CLASSES)));
                    classes.setId("tabpane");
                    classes.getStyleClass().add("tabpane");
                    homepage.getChildren().add(classes);
                    classes.setLayoutX(345);
                    classes.setLayoutY(20);
                    classes.toFront();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        roomsBtn.setOnAction(e -> {
            try {
                if (homepage.lookup("#tabpane") != null && roomsBtn.getStyleClass().contains("tabbtnactive")) {
                    homePageImage.setVisible(true);
                    roomsBtn.getStyleClass().remove("tabbtnactive");
                    homepage.getChildren().remove(homepage.lookup("#tabpane"));
                } else {
                    if (sidebar.lookup(".tabbtnactive") != null) {
                        sidebar.lookup(".tabbtnactive").getStyleClass().remove("tabbtnactive");
                        homepage.getChildren().remove(homepage.lookup("#tabpane"));
                    }
                    homePageImage.setVisible(false);
                    roomsBtn.getStyleClass().add("tabbtnactive");
                    Node rooms = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_ROOMS)));
                    rooms.setId("tabpane");
                    rooms.getStyleClass().add("tabpane");
                    homepage.getChildren().add(rooms);
                    rooms.setLayoutX(345);
                    rooms.setLayoutY(20);
                    rooms.toFront();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        scheduleBtn.setOnAction(e -> {
            try {
                if (homepage.lookup("#tabpane") != null && scheduleBtn.getStyleClass().contains("tabbtnactive")) {
                    homePageImage.setVisible(true);
                    scheduleBtn.getStyleClass().remove("tabbtnactive");
                    homepage.getChildren().remove(homepage.lookup("#tabpane"));
                } else {
                    if (sidebar.lookup(".tabbtnactive") != null) {
                        sidebar.lookup(".tabbtnactive").getStyleClass().remove("tabbtnactive");
                        homepage.getChildren().remove(homepage.lookup("#tabpane"));
                    }
                    homePageImage.setVisible(false);
                    scheduleBtn.getStyleClass().add("tabbtnactive");
                    Node schedule = FXMLLoader
                            .load(Objects.requireNonNull(getClass().getResource(Constants.FXML_SCHEDULE)));
                    schedule.setId("tabpane");
                    schedule.getStyleClass().add("tabpane");
                    homepage.getChildren().add(schedule);
                    schedule.setLayoutX(345);
                    schedule.setLayoutY(20);
                    schedule.toFront();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        usersBtn.setOnAction(e -> {
            try {
                if (homepage.lookup("#tabpane") != null && usersBtn.getStyleClass().contains("tabbtnactive")) {
                    homePageImage.setVisible(true);
                    usersBtn.getStyleClass().remove("tabbtnactive");
                    homepage.getChildren().remove(homepage.lookup("#tabpane"));
                } else {
                    if (sidebar.lookup(".tabbtnactive") != null) {
                        sidebar.lookup(".tabbtnactive").getStyleClass().remove("tabbtnactive");
                        homepage.getChildren().remove(homepage.lookup("#tabpane"));
                    }
                    homePageImage.setVisible(false);
                    usersBtn.getStyleClass().add("tabbtnactive");
                    Node users = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_USERS)));
                    users.setId("tabpane");
                    users.getStyleClass().add("tabpane");
                    homepage.getChildren().add(users);
                    users.setLayoutX(345);
                    users.setLayoutY(20);
                    users.toFront();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    private void signout() throws Exception {
        App.setRoot(Constants.FXML_LOGIN, 960, 540);
    }
}
