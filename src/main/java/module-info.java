module hellofx {
    requires transitive javafx.controls;
    requires jdk.javadoc;
    requires io.cucumber.core;
    requires io.cucumber.messages;
    requires picocontainer;

    exports ui; // Exporting to own application
    exports appLogic to picocontainer;
    opens appLogic to picocontainer;
    exports appLogic.project to picocontainer;
    opens appLogic.project to picocontainer;
    exports appLogic.employee to picocontainer;
    opens appLogic.employee to picocontainer;
    exports appLogic.activity.command to picocontainer;
    opens appLogic.activity.command to picocontainer;
    exports appLogic.activity.exception to picocontainer;
    opens appLogic.activity.exception to picocontainer;
    exports appLogic.activity.impl to picocontainer;
    opens appLogic.activity.impl to picocontainer;
    exports appLogic.activity to picocontainer;
    opens appLogic.activity to picocontainer;
    exports appLogic.report to picocontainer;
    opens appLogic.report to picocontainer;
}
