module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.junit.jupiter.api;
    requires java.desktop;

    opens gui to javafx.fxml;
    opens main to javafx.fxml;

    exports gui;
    exports main;
}