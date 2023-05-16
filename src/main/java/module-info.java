module com.english_center {
    requires transitive java.sql;
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires io.github.cdimascio.dotenv.java;
    requires lombok;

    opens com.english_center to javafx.fxml;

    exports com.english_center;
}
