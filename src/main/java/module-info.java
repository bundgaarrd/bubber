module hellofx {
    requires transitive javafx.controls;
    requires jdk.javadoc;
    requires io.cucumber.core;
    requires io.cucumber.messages;
    requires picocontainer;

    exports ui; // Exporting to own application
    exports appLogic to picocontainer;
    opens appLogic to picocontainer;
    exports appLogic.activity.impl to picocontainer;
    opens appLogic.activity.impl to picocontainer;
}
