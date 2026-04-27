module hellofx {
    requires transitive javafx.controls;
    requires jdk.javadoc;
    requires io.cucumber.core;

    exports ui; // Exporting to own application
}