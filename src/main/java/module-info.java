module hellofx {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires jdk.javadoc;
    requires io.cucumber.core;

    opens dtu.example.ui to javafx.fxml; // Gives access to fxml files
    exports dtu.example.ui; // Exports the class inheriting from javafx.application.Application
}