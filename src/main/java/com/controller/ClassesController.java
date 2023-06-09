package com.controller;

import com.models.Class;
import com.models.Text;
import com.service.ClassService;
import com.utilities.Constants;
import com.utilities.Manager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ClassesController {
    public TableView<Class> classTable;
    public TableColumn<Class, String> classID;
    public TableColumn<Class, String> className;
    public TableColumn<Class, String> courseName;
    public TableColumn<Class, String> teacher;
    public TableColumn<Class, String> start;
    public TableColumn<Class, String> end;
    public TableColumn<Class, String> action;
    public HBox manageLessonBtn;
    public AnchorPane classPane;
    public TableColumn<Class, String> room;
    public Label title;
    public Label smallTitle;
    public Button addBtn;
    public Label manageBtn;
    public Button studentListBtn;
    public Button exportReportBtn;
    public TextField searchField;

    public void initialize() throws IOException, SQLException {
        if (Manager.getAuth().split("/")[2].equals("Student") || Manager.getAuth().split("/")[2].equals("Teacher")) {
            manageLessonBtn.setVisible(false);
            title.setText("Classes");
            smallTitle.setText("List of classes");
            classID.setPrefWidth(120);
            className.setPrefWidth(140);
            courseName.setPrefWidth(310);
            teacher.setPrefWidth(200);
            room.setPrefWidth(110);
            start.setPrefWidth(130);
            end.setPrefWidth(110);
            classTable.getColumns().remove(action);
            addBtn.setVisible(false);
            manageBtn.setText("View Lessons");

        } else {
            classID.setPrefWidth(120);
            className.setPrefWidth(120);
            courseName.setPrefWidth(290);
            teacher.setPrefWidth(180);
            room.setPrefWidth(90);
            start.setPrefWidth(110);
            end.setPrefWidth(110);
            action.setPrefWidth(100);
        }
        classTable.getColumns().forEach(e -> {
            e.setReorderable(false);
            e.setResizable(false);
        });
        search("");
    }

    public void search(String keyWord) throws IOException, SQLException {
        exportReportBtn.setVisible(false);
        studentListBtn.setVisible(false);
        manageLessonBtn.setVisible(false);
        ObservableList<Class> classes;
        if (Manager.getAuth().split("/")[2].equals("Student"))
            classes = ClassService.searchWithStudentID(Manager.getAuth().split("/")[0], keyWord);
        else if (Manager.getAuth().split("/")[2].equals("Teacher"))
            classes = ClassService.searchWithTeacherID(Manager.getAuth().split("/")[0], keyWord);
        else
            classes = ClassService.search(keyWord);
        for (Class clas : classes) {
            HBox action = clas.getAction();
            if (action != null) {
                action.lookup("#edit").setOnMouseClicked(mouseEvent -> {
                    try {
                        EditClassController.show(clas, this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                action.lookup("#delete").setOnMouseClicked(mouseEvent -> {
                    try {
                        DeleteClass.show(clas.getId(), this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        classTable.setItems(classes);
    }

    public void add() throws IOException {
        EditClassController.show(null, this);
    }

    public void manageBtn(MouseEvent mouseEvent) {
        if (classTable.getSelectionModel().getSelectedItem() != null) {
            if (!manageLessonBtn.isVisible()) {
                if (Manager.getAuth().split("/")[2].equals("Manager")) {
                    exportReportBtn.setVisible(true);
                    studentListBtn.setVisible(true);
                }
                manageLessonBtn.setVisible(true);
            }
        }
    }

    public void manageLessons(MouseEvent mouseEvent) throws IOException {
        AnchorPane homepage = (AnchorPane) classPane.getParent();
        homepage.getChildren().remove(homepage.lookup("#tabpane"));
        FXMLLoader fxml = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Constants.FXML_LESSON)));
        Node lesson = fxml.load();
        LessonController controller = fxml.getController();
        controller.classes = classTable.getSelectionModel().getSelectedItem();
        lesson.setId("tabpane");
        lesson.getStyleClass().add("tabpane");
        homepage.getChildren().add(lesson);
        lesson.setLayoutX(345);
        lesson.setLayoutY(20);
        lesson.toFront();
    }

    public void studentList(ActionEvent actionEvent) throws SQLException, IOException {
        StudentList.show(classTable.getSelectionModel().getSelectedItem().getId());
    }

    public void exportReport(ActionEvent actionEvent) throws SQLException, URISyntaxException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("Report.pdf");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedDirectory = fileChooser.showSaveDialog(null);
        if (selectedDirectory != null) {
            createPDF(selectedDirectory.getAbsolutePath());
        }
    }

    public void createPDF(String path) throws URISyntaxException, IOException, SQLException {
        Class selectedClass = classTable.getSelectionModel().getSelectedItem();
        int numberOfStudent = ClassService.getNumberOfStudentInClass(selectedClass.getId());
        ArrayList<String[]> data = ClassService.getLessons(selectedClass.getId());
        PDDocument document = new PDDocument();
        PDType0Font headerFont = PDType0Font.load(document, new File(Objects.requireNonNull(getClass().getResource("/fonts/Manrope/Manrope-ExtraBold.ttf")).toURI()));
        PDType0Font semiHeaderFont = PDType0Font.load(document, new File(Objects.requireNonNull(getClass().getResource("/fonts/Manrope/Manrope-SemiBold.ttf")).toURI()));
        PDType0Font textfont = PDType0Font.load(document, new File(Objects.requireNonNull(getClass().getResource("/fonts/Manrope/Manrope-Regular.ttf")).toURI()));
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        Color colortest = new Color(98, 98, 98);
        Color lineColor = new Color(198, 198, 198);
        int pageWidth = (int) page.getTrimBox().getWidth();
        int pageHeight = (int) page.getTrimBox().getHeight();

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        Text myTextClass = new Text(document, contentStream);

        myTextClass.addSingleLineText("ENGLISH CENTER", 26, pageHeight - 40, headerFont, 20, Color.BLACK);
        myTextClass.addSingleLineText("CLASS REPORT", pageWidth - 26 - (int) (semiHeaderFont.getStringWidth("CLASS REPORT") / 1000 * 20), pageHeight - 40, semiHeaderFont, 20, Color.BLACK);

        contentStream.setStrokingColor(lineColor);
        contentStream.setLineWidth(1);
        contentStream.moveTo(26, pageHeight - 65);
        contentStream.lineTo(pageWidth - 26, pageHeight - 65);
        contentStream.stroke();

        myTextClass.addSingleLineText("Class Information ", 26, pageHeight - 95, semiHeaderFont, 16, Color.BLACK);

        myTextClass.addSingleLineText("Class ID ", 26, pageHeight - 130, textfont, 12, colortest);
        myTextClass.addSingleLineText(selectedClass.getId(), pageWidth - 330 - (int) (semiHeaderFont.getStringWidth(selectedClass.getId()) / 1000 * 12), pageHeight - 130, textfont, 12, Color.BLACK);

        myTextClass.addSingleLineText("Class Name ", 26, pageHeight - 160, textfont, 12, colortest);
        myTextClass.addSingleLineText(selectedClass.getName(), pageWidth - 330 - (int) (semiHeaderFont.getStringWidth(selectedClass.getName()) / 1000 * 12), pageHeight - 160, textfont, 12, Color.BLACK);

        myTextClass.addSingleLineText("Course Name ", 26, pageHeight - 190, textfont, 12, colortest);
        String wrappedCourseName = wordWrap(selectedClass.getCourse().getCourseName(), textfont, 12, 200);

        List<String> courseNameLines = Arrays.asList(wrappedCourseName.split("\n"));
        int courseNameLineHeight = 12;

        if (courseNameLines.size() == 1) {
            myTextClass.addSingleLineText(courseNameLines.get(0), pageWidth - 330 - (int) (semiHeaderFont.getStringWidth(courseNameLines.get(0)) / 1000 * 12), pageHeight - 190, textfont, 12, Color.BLACK);
        } else if (courseNameLines.size() == 2) {
            for (int i = 0; i < courseNameLines.size(); i++) {
                String line = courseNameLines.get(i);
                int yPosition = pageHeight - 190 - (i * courseNameLineHeight) + 5;
                int xPosition = pageWidth - 330 - (int) (textfont.getStringWidth(line) / 1000 * 10);

                myTextClass.addSingleLineText(line, xPosition, yPosition, textfont, 10, Color.BLACK);
            }
        } else {
            for (int i = 0; i < courseNameLines.size(); i++) {
                String line = courseNameLines.get(i);
                int yPosition = pageHeight - 190 - (i * courseNameLineHeight) + 10;
                int xPosition = pageWidth - 330 - (int) (textfont.getStringWidth(line) / 1000 * 8);

                myTextClass.addSingleLineText(line, xPosition, yPosition, textfont, 8, Color.BLACK);
            }
        }

        myTextClass.addSingleLineText("Teacher Name ", 26, pageHeight - 220, textfont, 12, colortest);
        myTextClass.addSingleLineText(selectedClass.getTeacher().getName(), pageWidth - 330 - (int) (semiHeaderFont.getStringWidth(selectedClass.getTeacher().getName()) / 1000 * 12), pageHeight - 220, textfont, 12, Color.BLACK);

        myTextClass.addSingleLineText("Room Name ", 360, pageHeight - 130, textfont, 12, colortest);
        myTextClass.addSingleLineText(selectedClass.getRoom().getName(), pageWidth - 26 - (int) (textfont.getStringWidth(selectedClass.getRoom().getName()) / 1000 * 12), pageHeight - 130, textfont, 12, Color.BLACK);

        myTextClass.addSingleLineText("Start Date ", 360, pageHeight - 160, textfont, 12, colortest);
        myTextClass.addSingleLineText(selectedClass.getStart(), pageWidth - 26 - (int) (textfont.getStringWidth(selectedClass.getStart()) / 1000 * 12), pageHeight - 160, textfont, 12, Color.BLACK);

        myTextClass.addSingleLineText("End Date ", 360, pageHeight - 190, textfont, 12, colortest);
        myTextClass.addSingleLineText(selectedClass.getEnd(), pageWidth - 26 - (int) (textfont.getStringWidth(selectedClass.getEnd()) / 1000 * 12), pageHeight - 190, textfont, 12, Color.BLACK);

        myTextClass.addSingleLineText("Number Of Students ", 360, pageHeight - 220, textfont, 12, colortest);
        myTextClass.addSingleLineText(String.valueOf(numberOfStudent), pageWidth - 26 - (int) (textfont.getStringWidth(String.valueOf(numberOfStudent)) / 1000 * 12), pageHeight - 220, textfont, 12, Color.BLACK);

        contentStream.setStrokingColor(lineColor);
        contentStream.setLineWidth(1);
        contentStream.moveTo(26, pageHeight - 65 - 185);
        contentStream.lineTo(pageWidth - 26, pageHeight - 65 - 185);
        contentStream.stroke();

        myTextClass.addSingleLineText("List of Lessons", 26, pageHeight - 285, semiHeaderFont, 16, Color.BLACK);

        myTextClass.addSingleLineText("Lesson Name", 50, pageHeight - 320, textfont, 12, colortest);
        myTextClass.addSingleLineText("Date", 275, pageHeight - 320, textfont, 12, colortest);
        myTextClass.addSingleLineText("Attendance", 400, pageHeight - 320, textfont, 12, colortest);
        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            myTextClass.addSingleLineText((String) row[0], 50, pageHeight - 320 - 30 * (i + 1), textfont, 12, Color.BLACK);
            myTextClass.addSingleLineText((String) row[1], 275, pageHeight - 320 - 30 * (i + 1), textfont, 12, Color.BLACK);
            myTextClass.addSingleLineText((String) row[2], 425, pageHeight - 320 - 30 * (i + 1), textfont, 12, Color.BLACK);
        }
        contentStream.close();
        document.save(path);
        document.close();
        SuccessfulController.show();
    }

    private static String wordWrap(String text, PDFont font, int fontSize, int maxLineWidth) throws IOException {
        StringBuilder wrappedText = new StringBuilder();
        String[] words = text.split(" ");
        float spaceWidth = font.getSpaceWidth() / 1000 * fontSize;
        float currentLineWidth = 0;

        for (String word : words) {
            float wordWidth = font.getStringWidth(word) / 1000 * fontSize;

            if (currentLineWidth + wordWidth + spaceWidth > maxLineWidth) {
                wrappedText.append("\n");
                currentLineWidth = 0;
            }

            wrappedText.append(word).append(" ");
            currentLineWidth += wordWidth + spaceWidth;
        }

        return wrappedText.toString().trim();
    }

    public void searchAction(KeyEvent keyEvent) throws SQLException, IOException {
        search(searchField.getText());
    }
}
