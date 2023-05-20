package com.english_center;

import com.utilities.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class CoursesController {
    @FXML
    public TableColumn<Course, String> courseID;
    public TableColumn<Course, String> courseName;
    public TableColumn<Course, String> description;
    public TableColumn<Course, String> level;
    public TableColumn<Course, HBox> action;
    public AnchorPane emptyPreview;
    public AnchorPane previewPane;
    public AnchorPane coursePane;
    public HBox manageChapterBtn;
    public TableColumn<Course, Integer> chapter;
    public FlowPane flowPane;
    @FXML
    private TableView<Course> CourseTable;

    @FXML
    private void initialize() throws IOException {
        courseID.setPrefWidth(150);
        courseName.setPrefWidth(150);
        description.setPrefWidth(220);
        level.setPrefWidth(110);
        chapter.setPrefWidth(70);
        action.setPrefWidth(100);
        AtomicInteger tblWidth = new AtomicInteger();
        CourseTable.getColumns().forEach(e -> {
            e.setReorderable(false);
            e.setResizable(false);
            tblWidth.addAndGet((int) e.getWidth());
        });
        CourseTable.setPrefWidth(tblWidth.get());
        ObservableList<Course> courses = FXCollections.observableArrayList(
                new Course("#20462", "Introduce to Toeic", "Test of English for International Communication", "Beginner", 10),
                new Course("#41569", "Intermediate Ielts", "International English Language Testing System", "Intermediate", 9),
                new Course("#69321", "Toeic", "Test of English for International Communication", "Advance", 8)
        );
        CourseTable.setItems(courses);
        manageChapterBtn.setOnMouseClicked(e -> {
                    AnchorPane homepage = (AnchorPane) coursePane.getParent();
                    homepage.getChildren().remove(homepage.lookup("#tabpane"));
                    try {
                        FXMLLoader fxml = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Constants.FXML_CHAPTER)));
                        Node chapter = fxml.load();
                        ChapterController controller = fxml.getController();
                        controller.course = CourseTable.getSelectionModel().getSelectedItem();
                        chapter.setId("tabpane");
                        chapter.getStyleClass().add("tabpane");
                        homepage.getChildren().add(chapter);
                        chapter.setLayoutX(345);
                        chapter.setLayoutY(20);
                        chapter.toFront();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
    }

    public void preview(MouseEvent mouseEvent) {
        if (CourseTable.getSelectionModel().getSelectedItem() != null) {
            if (emptyPreview.isVisible()) {
                emptyPreview.setVisible(false);
                previewPane.setVisible(true);
                manageChapterBtn.setVisible(true);
            }
            ((Label) previewPane.lookup(".previewCourseName")).setText(CourseTable.getSelectionModel().getSelectedItem().getCourseName());
            ((Label) previewPane.lookup(".previewCourseDescription")).setText(CourseTable.getSelectionModel().getSelectedItem().getCourseDescription());
            if (CourseTable.getSelectionModel().getSelectedItem().getNumberOfChapter() < 10)
                ((Label) previewPane.lookup(".previewChapterNumber")).setText("0" + CourseTable.getSelectionModel().getSelectedItem().getNumberOfChapter().toString());
            else
                ((Label) previewPane.lookup(".previewChapterNumber")).setText(CourseTable.getSelectionModel().getSelectedItem().getNumberOfChapter().toString());

            Label previewCourseLevel = (Label) previewPane.lookup("#flowPane").lookup(".courseLevel");
            previewCourseLevel.setText(CourseTable.getSelectionModel().getSelectedItem().getCourseLevel().getText());
            previewCourseLevel.getStyleClass().clear();
            previewCourseLevel.getStyleClass().add("courseLevel");
            switch (CourseTable.getSelectionModel().getSelectedItem().getCourseLevel().getText()) {
                case "Beginner":
                    previewCourseLevel.getStyleClass().add("levelBeginner");
                    break;
                case "Intermediate":
                    previewCourseLevel.getStyleClass().add("levelIntermediate");
                    break;
                case "Advance":
                    previewCourseLevel.getStyleClass().add("levelAdvance");
                    break;
            }
        }
    }

    public void add() throws IOException {
        EditCourseController.show();
    }
}
