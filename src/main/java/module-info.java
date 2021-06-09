module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires org.apache.logging.log4j;
    requires org.apache.commons.io;

    opens ControllFx to javafx.fxml;
    exports ControllFx;
    exports core;
    exports core.exceptions;
}