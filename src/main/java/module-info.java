module com.english_center {
    requires transitive java.sql;
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires io.github.cdimascio.dotenv.java;
    requires lombok;

    opens com.controller to javafx.fxml;

    exports com.controller;
    exports com.models;
    opens com.models to javafx.fxml;
    exports com;
    opens com to javafx.fxml;
}
