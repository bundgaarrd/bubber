module hellofx {
    requires transitive javafx.controls;
    requires jdk.javadoc;
    requires io.cucumber.core;
    requires io.cucumber.messages;

    exports ui; // Exporting to own application
}