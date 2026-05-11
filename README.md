# Get started

This guide assumes that you have Maven and JavaFX installed.

To run this application, do the following steps:

### Clone this repository. 
There are different ways to do this depending on which git server you use.

#### Github
```bash
git clone https://github.com/bundgaarrd/SoftwareHuset.git
```

#### Gitlab
```bash
git clone https://gitlab.gbar.dtu.dk/s244970/softwarehuset.git
```

### Run the application and tests from the terminal
Navigate to the correct directory and run the Maven project:
```bash
# First clone the project via the steps above
# Change dir to the project directory
cd SoftwareHuset # or whatever the directory is called (depending on which git server you cloned from)

# Run the Maven projet
mvn javafx:run

# Run the tests for the project
mvn test

# Run the cucumber features only
mvn test -Dtest=RunCucumberTest
```

Running the Maven project, will spawn a window from which the application can be used. Running the application can also be done from the IntelliJ IDE, but make sure that the whole project is built before running.

The project can be build in IntelliJ by clicking **Build** > **Build project**