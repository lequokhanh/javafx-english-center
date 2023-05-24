module com.english_center {
    requires transitive java.sql;
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires io.github.cdimascio.dotenv.java;
    requires lombok;

    exports com.controller;
    exports com.models;
    exports com;

    opens com.controller to javafx.fxml;
    opens com.models to javafx.fxml;
    opens com to javafx.fxml;
}
